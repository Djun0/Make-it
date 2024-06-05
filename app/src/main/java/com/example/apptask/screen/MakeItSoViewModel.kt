
package com.example.apptask.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptask.common.snackbar.SnackbarManager
import com.example.apptask.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.apptask.model.service.LogService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class MakeItSoViewModel(private val logService: LogService) : ViewModel() {
  fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
    viewModelScope.launch(
      CoroutineExceptionHandler { _, throwable ->
        if (snackbar) {
          SnackbarManager.showMessage(throwable.toSnackbarMessage())
        }
        logService.logNonFatalCrash(throwable)
      },
      block = block
    )
}
