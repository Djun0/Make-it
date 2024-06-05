
package com.example.apptask.model.service

import com.example.apptask.model.Task
import kotlinx.coroutines.flow.Flow

interface StorageService {
  val tasks: Flow<List<Task>>
  suspend fun getTask(taskId: String): Task?
  suspend fun save(task: Task): String
  suspend fun update(task: Task)
  suspend fun delete(taskId: String)
}
