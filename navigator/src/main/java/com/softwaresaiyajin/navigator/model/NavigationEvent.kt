package com.softwaresaiyajin.navigator.model

import java.lang.reflect.Constructor
import kotlin.reflect.KClass

/**
 * Created by geraldadorza on 3/10/2018.
 */


typealias LocationInstance = String
typealias LocationType = KClass<LocationInstance>
typealias NavigationEventObserver = (NavigationEvent) -> Unit
typealias PathNotFoundObserver = (String, Any?) -> Unit

class NavigationEvent {

    private var observer: NavigationEventObserver? = null

    private var instance: LocationInstance? = null

    private var type: LocationType? = null

    private val value: LocationInstance?
        get() = instance ?: null

    val destination: LocationInstance
        get() = value!!

    private var _data: Any? = null

    val data: Any? get() = _data

    internal constructor(type: LocationType, observer: NavigationEventObserver?) {
        this.type = type
        setObserver(observer)
    }

    internal constructor(instance: LocationInstance, observer: NavigationEventObserver?) {
        this.instance = instance
        setObserver(observer)
    }

    private fun setObserver(observer: NavigationEventObserver?) {
        this.observer = observer
    }

    internal val detail: Any?
        get() = value?.toString()

    internal val isValid: Boolean
        get() = value != null

    internal fun notifyObserver(data: Any?) {
        _data = data
        observer?.invoke(this)
    }
}