
package com.example.apptask.screen.sign_up

import androidx.compose.runtime.mutableStateOf
import com.example.apptask.SETTINGS_SCREEN
import com.example.apptask.SIGN_UP_SCREEN
import com.example.apptask.R.string as AppText
import com.example.apptask.common.ext.isValidEmail
import com.example.apptask.common.ext.isValidPassword
import com.example.apptask.common.ext.passwordMatches
import com.example.apptask.common.snackbar.SnackbarManager
import com.example.apptask.model.service.AccountService
import com.example.apptask.model.service.LogService
import com.example.apptask.screen.MakeItSoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
  private val accountService: AccountService,
  logService: LogService
) : MakeItSoViewModel(logService) {
  var uiState = mutableStateOf(SignUpUiState())
    private set

  private val email
    get() = uiState.value.email
  private val password
    get() = uiState.value.password

  fun onEmailChange(newValue: String) {
    uiState.value = uiState.value.copy(email = newValue)
  }

  fun onPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(password = newValue)
  }

  fun onRepeatPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(repeatPassword = newValue)
  }

  fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
    if (!email.isValidEmail()) {
      SnackbarManager.showMessage(AppText.email_error)
      return
    }

    if (!password.isValidPassword()) {
      SnackbarManager.showMessage(AppText.password_error)
      return
    }

    if (!password.passwordMatches(uiState.value.repeatPassword)) {
      SnackbarManager.showMessage(AppText.password_match_error)
      return
    }

    launchCatching {
      //tiến hành xác thực nếu cuộc gọi thành công, thì sẽ chuyển sang màn hình tiếp theo ( SettingsScreen )
      accountService.linkAccount(email, password)//nếu thất bại ở dòng này thì, bắt lâý ngoại lệ và xử lý, bỏ qua các dòng kế tiếp trong khối launchCatching
      openAndPopUp(SETTINGS_SCREEN, SIGN_UP_SCREEN)
    }
  }
}
