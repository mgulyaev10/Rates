package ru.helpfulproduction.rates.utils

import ru.helpfulproduction.rates.log.Tracker
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object Executors {
    private const val NETWORK_THREADS_COUNT = 1
    private val uncaughtExceptionHandler = Thread.UncaughtExceptionHandler {
            _, e -> Tracker.logException(e)
    }

    val networkExecutor: ExecutorService by lazy {
        Executors.newFixedThreadPool(NETWORK_THREADS_COUNT) { runnable ->
            val thread = Thread(runnable)
            thread.uncaughtExceptionHandler = uncaughtExceptionHandler
            thread
        }
    }

}