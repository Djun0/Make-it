
package com.example.apptask.screen.settings

import com.example.apptask.LOGIN_SCREEN
import com.example.apptask.SIGN_UP_SCREEN
import com.example.apptask.SPLASH_SCREEN
import com.example.apptask.model.service.AccountService
import com.example.apptask.model.service.LogService
import com.example.apptask.model.service.StorageService
import com.example.apptask.screen.MakeItSoViewModel


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
  logService: LogService,
  private val accountService: AccountService,
  private val storageService: StorageService
) : MakeItSoViewModel(logService) {
  //val uiState = SettingsUiState(isAnonymousAccount = true)
  // cho ViewModel lắng nghe trạng thái của người dùng hiện tại, kiểm tra xem tài khoản có ẩn danh hay không
  val uiState = accountService.currentUser.map {
    SettingsUiState(it.isAnonymous)
  }
  fun onLoginClick(openScreen: (String) -> Unit) = openScreen(LOGIN_SCREEN)

  fun onSignUpClick(openScreen: (String) -> Unit) = openScreen(SIGN_UP_SCREEN)

  fun onSignOutClick(restartApp: (String) -> Unit) {
    launchCatching {
      accountService.signOut()
      restartApp(SPLASH_SCREEN)
    }
  }

  fun onDeleteMyAccountClick(restartApp: (String) -> Unit) {
    launchCatching {
      accountService.deleteAccount()
      restartApp(SPLASH_SCREEN)
    }
  }
}
