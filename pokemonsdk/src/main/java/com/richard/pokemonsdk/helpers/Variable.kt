package com.richard.pokemonsdk.helpers

import io.reactivex.rxjava3.subjects.BehaviorSubject

class Variable<T>(private val defaultValue: T) {
    var value: T = defaultValue
        set(value) {
            field = value
            observable.onNext(value)
        }
    val observable = BehaviorSubject.createDefault(value)
}