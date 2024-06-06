package com.example.apptask.screen.tasks

import androidx.compose.runtime.mutableStateOf
import com.example.apptask.EDIT_TASK_SCREEN
import com.example.apptask.SETTINGS_SCREEN
import com.example.apptask.TASK_ID
import com.example.apptask.model.Task
import com.example.apptask.model.service.ConfigurationService
import com.example.apptask.model.service.LogService
import com.example.apptask.model.service.StorageService
import com.example.apptask.screen.MakeItSoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
  logService: LogService,
  private val storageService: StorageService,
  private val configurationService: ConfigurationService
) : MakeItSoViewModel(logService) {
  val options = mutableStateOf<List<String>>(listOf())

  //val tasks = emptyFlow<List<Task>>()
  val tasks = storageService.tasks
  //Cập nhập lại TaskOption khi tìm nạp được giá trị cấu hình từ máy chủ
  fun loadTaskOptions() {
    // truy xuất giá trị
    val hasEditOption = configurationService.isShowTaskEditButtonConfig
    options.value = TaskActionOption.getOptions(hasEditOption)
  }

  fun onTaskCheckChange(task: Task) {
    launchCatching { storageService.update(task.copy(completed = !task.completed)) }
  }

  fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_TASK_SCREEN)

  fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

  fun onTaskActionClick(openScreen: (String) -> Unit, task: Task, action: String) {
    when (TaskActionOption.getByTitle(action)) {
      TaskActionOption.EditTask -> openScreen("$EDIT_TASK_SCREEN?$TASK_ID={${task.id}}")
      TaskActionOption.ToggleFlag -> onFlagTaskClick(task)
      TaskActionOption.DeleteTask -> onDeleteTaskClick(task)
    }
  }

  private fun onFlagTaskClick(task: Task) {
    launchCatching { storageService.update(task.copy(flag = !task.flag)) }
  }

  private fun onDeleteTaskClick(task: Task) {
    launchCatching { storageService.delete(task.id) }
  }
}
