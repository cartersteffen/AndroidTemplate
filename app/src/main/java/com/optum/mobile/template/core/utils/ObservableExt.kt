package com.optum.mobile.template.core.utils

import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate

fun <T : Any> Observable<T>.blockWithin(thresholdMs: Long): Observable<T> {
    return filter(SpamFilterPredicate(thresholdMs))
}

/**
 * Each emission from upstream is passed downstream in a [Window] with the previous emission,
 * seeded with the [initialValue].
 */
fun <T : Any> Observable<T>.pairWithPrevious(initialValue: T): Observable<Window<T>> {
    return map(PairWithPreviousEmissionFunction(initialValue))
}

/**
 * Convenience for filtering a stream by a type, and casting those emissions.
 */
inline fun <reified R : Any> Observable<*>.filterInstance(): Observable<R> {
    return filter { it is R }.cast(R::class.java)
}

/**
 * For use with [Observable.map], this function will take each emission and pair it with
 * it's immediate predecessor as a [Window].
 */
private class PairWithPreviousEmissionFunction<T>(initialValue: T) : Function<T, Window<T>> {
    private var buffer = initialValue

    override fun apply(t: T): Window<T> {
        val next = Window(buffer, t)
        buffer = t
        return next
    }
}

data class Window<T>(
    val previous: T,
    val next: T
)

private class SpamFilterPredicate<T>(private val thresholdMs: Long) : Predicate<T> {
    private var lastEmissionTime: Long = 0

    @Throws(Exception::class)
    override fun test(t: T): Boolean {
        val now = System.currentTimeMillis()
        val timeElapsedSinceLastEmission = now - lastEmissionTime
        return if (timeElapsedSinceLastEmission > thresholdMs) {
            lastEmissionTime = now
            true
        } else {
            false
        }
    }
}
