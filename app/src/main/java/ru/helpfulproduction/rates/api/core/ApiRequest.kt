package ru.helpfulproduction.rates.api.core

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.helpfulproduction.rates.network.OkHttpMethod
import ru.helpfulproduction.rates.utils.Executors

abstract class ApiRequest<T: ApiResponse>(
    private val method: String
) {

    private val params = LinkedHashMap<String, String>()

    protected fun param(name: String, value: String) { params[name] = value }

    fun toUiObservable(): Observable<T> {
        return toObservable()
            .observeOn(AndroidSchedulers.mainThread())
    }

    protected open fun toObservable(): Observable<T> {
        return Observable.create<T> { emitter ->
            try {
                val response = execute()
                if (!emitter.isDisposed) {
                    emitter.onNext(parse(response))
                    emitter.onComplete()
                }
            } catch (e: Throwable) {
                emitter.tryOnError(e)
            }
        }.subscribeOn(Schedulers.from(Executors.networkExecutor))
    }

    private fun execute(): String {
        val method = OkHttpMethod(
            DEFAULT_SCHEME,
            DEFAULT_HOST,
            createPath(),
            getParams()
        )
        return ApiManager.execute(method)
    }

    private fun createPath(): String {
        return DEFAULT_PATH.plus(method)
    }

    private fun getParams(): Map<String, String> {
        updateParams()
        return params
    }

    open fun updateParams() {}

    abstract fun parse(body: String): T

    private companion object {
        private const val DEFAULT_SCHEME = "https"
        private const val DEFAULT_HOST = "hiring.revolut.codes"
        private const val DEFAULT_PATH = "api/android/"
    }

}