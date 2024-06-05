
package com.example.apptask.model.service

interface LogService {
  fun logNonFatalCrash(throwable: Throwable)
}
