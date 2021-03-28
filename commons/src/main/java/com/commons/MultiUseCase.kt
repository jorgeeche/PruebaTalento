package com.commons

import commons.android.Either
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class MultiUseCase<F, S, P> : CoroutineScope {

    abstract suspend fun run(params: List<P>): Map<P, Either<F, S>>

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()


    operator fun invoke(params: List<P>, result: (Map<P, Either<F, S>>) -> Unit) {
        val job: Deferred<Map<P, Either<F, S>>> = async(coroutineContext) { run(params) }
        launch(coroutineContext) { result.invoke(job.await()) }
    }

}