
package com.example.apptask.model.service

import com.google.firebase.perf.trace
import com.google.firebase.perf.metrics.Trace

inline fun <T> trace(name: String, block: Trace.() -> T): T = Trace.create(name).trace(block)
