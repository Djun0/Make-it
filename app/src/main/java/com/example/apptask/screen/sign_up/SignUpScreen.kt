

package com.example.apptask.screen.sign_up

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apptask.R.string as AppText
import com.example.apptask.common.composable.*
import com.example.apptask.common.ext.basicButton
import com.example.apptask.common.ext.fieldModifier
import com.example.apptask.ui.theme.MakeItSoTheme


@Composable
fun SignUpScreen(
  openAndPopUp: (String, String) -> Unit,
  viewModel: SignUpViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState

  SignUpScreenContent(
    uiState = uiState,
    onEmailChange = viewModel::onEmailChange,
    onPasswordChange = viewModel::onPasswordChange,
    onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
    onSignUpClick = { viewModel.onSignUpClick(openAndPopUp) }
  )
}

@Composable
fun SignUpScreenContent(
  modifier: Modifier = Modifier,
  uiState: SignUpUiState,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onRepeatPasswordChange: (String) -> Unit,
  onSignUpClick: () -> Unit
) {
  val fieldModifier = Modifier.fieldModifier()

  BasicToolbar(AppText.create_account)

  Column(
    modifier = modifier.fillMaxWidth().fillMaxHeight().verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    EmailField(uiState.email, onEmailChange, fieldModifier)
    PasswordField(uiState.password, onPasswordChange, fieldModifier)
    RepeatPasswordField(uiState.repeatPassword, onRepeatPasswordChange, fieldModifier)

    BasicButton(AppText.create_account, Modifier.basicButton()) {
      onSignUpClick()
    }
  }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
  val uiState = SignUpUiState(
    email = "email@test.com"
  )

  MakeItSoTheme {
    SignUpScreenContent(
      uiState = uiState,
      onEmailChange = { },
      onPasswordChange = { },
      onRepeatPasswordChange = { },
      onSignUpClick = { }
    )
  }
}
