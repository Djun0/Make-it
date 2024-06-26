package com.example.apptask.screen.splash

import androidx.compose.runtime.mutableStateOf
import com.example.apptask.SPLASH_SCREEN
import com.example.apptask.TASKS_SCREEN
import com.example.apptask.model.service.AccountService
import com.example.apptask.model.service.ConfigurationService
import com.example.apptask.model.service.LogService
import com.example.apptask.screen.MakeItSoViewModel
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  configurationService: ConfigurationService,
  private val accountService: AccountService,
  logService: LogService
) : MakeItSoViewModel(logService) {
  val showError = mutableStateOf(false)

  init {
    launchCatching { configurationService.fetchConfiguration() }
  }

  fun onAppStart(openAndPopUp: (String, String) -> Unit) {

    showError.value = false
    if (accountService.hasUser) openAndPopUp(TASKS_SCREEN, SPLASH_SCREEN)
    else createAnonymousAccount(openAndPopUp)
  }

  private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
    launchCatching(snackbar = false) {
      try {
        accountService.createAnonymousAccount()
      } catch (ex: FirebaseAuthException) {
        showError.value = true
        throw ex
      }
      openAndPopUp(TASKS_SCREEN, SPLASH_SCREEN)
    }
  }
}
