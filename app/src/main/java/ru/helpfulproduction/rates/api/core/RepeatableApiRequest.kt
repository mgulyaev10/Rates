package ru.helpfulproduction.rates.api.core

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.helpfulproduction.rates.utils.Executors
import java.util.concurrent.TimeUnit

abstract class RepeatableApiRequest<T: ApiResponse>(private val periodMs: Long,
                                                                                                           method: String): ApiRequest<T>(method) {

    override fun toObservable(): Observable<T> {
        return Observable.interval(periodMs, TimeUnit.SECONDS, Schedulers.computation())
            .flatMap { super.toObservable() }
            .subscribeOn(Schedulers.from(Executors.networkExecutor))
    }

}