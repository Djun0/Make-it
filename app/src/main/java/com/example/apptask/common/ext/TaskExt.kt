
package com.example.apptask.common.ext

import com.example.apptask.model.Task

fun Task?.hasDueDate(): Boolean {
  return this?.dueDate.orEmpty().isNotBlank()
}

fun Task?.hasDueTime(): Boolean {
  return this?.dueTime.orEmpty().isNotBlank()
}
