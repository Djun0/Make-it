
package com.example.apptask.model.service.impl

import android.util.Log
import com.example.apptask.model.User
import com.example.apptask.model.service.AccountService
import com.example.apptask.model.service.trace
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth) : AccountService {

  override val currentUserId: String
    get() = auth.currentUser?.uid.orEmpty()

  override val hasUser: Boolean
    get() = auth.currentUser != null

  override val currentUser: Flow<User>
    get() = callbackFlow {
      val listener =
        FirebaseAuth.AuthStateListener { auth ->
          this.trySend(auth.currentUser?.let { User(it.uid, it.isAnonymous) } ?: User())
        }
      auth.addAuthStateListener(listener)
      awaitClose { auth.removeAuthStateListener(listener) }
    }

  override suspend fun authenticate(email: String, password: String) {
    auth.signInWithEmailAndPassword(email, password).await()
  }

  override suspend fun sendRecoveryEmail(email: String) {
    auth.sendPasswordResetEmail(email).await()
  }

  override suspend fun createAnonymousAccount() {
    auth.signInAnonymously().await()
  }

  //Liên kết thông tin xác thực với tài khoản ẩn danh
  //đặt trong khối trace để theo dõi quá trình
  override suspend fun linkAccount(email: String, password: String): Unit = trace(LINK_ACCOUNT_TRACE) {
    try {
      val currentUser = auth.currentUser
      if (currentUser != null) {
        val credential = EmailAuthProvider.getCredential(email, password)
        currentUser.linkWithCredential(credential).await()
      } else {
        throw IllegalStateException("No current user to link with")
      }
    } catch (e: Exception) {
      Log.e("AccountService", "linkAccount failed", e)
      throw e
    }
  }


  override suspend fun deleteAccount() {
    auth.currentUser!!.delete().await()
  }

  override suspend fun signOut() {
    if (auth.currentUser!!.isAnonymous) {
      auth.currentUser!!.delete()
    }
    auth.signOut()

    // Sign the user back in anonymously.
    createAnonymousAccount()
  }

  companion object {
    private const val LINK_ACCOUNT_TRACE = "linkAccount"
  }
}
