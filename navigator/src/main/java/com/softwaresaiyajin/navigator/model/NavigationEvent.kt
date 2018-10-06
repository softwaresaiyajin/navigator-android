package com.softwaresaiyajin.navigator.model

import java.lang.reflect.Constructor
import kotlin.reflect.KClass

/**
 * Created by geraldadorza on 3/10/2018.
 */

typealias LocationId = String
typealias LocationData = Any
typealias LocationType = String
typealias PathNotFoundObserver = (String, Any?) -> Unit

internal interface IEvent {
    fun notifyObserver(data: LocationData?): Boolean
}

class NavigationEvent<Destination: LocationType>: IEvent {

    internal interface IObserver<T: LocationType> {
        fun onNavigate(event: NavigationEvent<T>)
    }

    private var observer: IObserver<Destination>? = null

    private var instance: Destination? = null

    private var type: KClass<Destination>? = null

    var data: LocationData? = null
        private set

    lateinit var destination: Destination
        private set

    internal constructor(type: KClass<Destination>, observer: IObserver<Destination>) {
        this.type = type
        setObserver(observer)
    }

    internal constructor(instance: Destination, observer: IObserver<Destination>?) {
        this.instance = instance
        setObserver(observer)
    }

    private fun setObserver(observer: IObserver<Destination>?) {
        this.observer = observer
    }

    override fun notifyObserver(data: LocationData?): Boolean {
        val value = instance ?: return false
        destination = value
        this.data = data
        observer?.onNavigate(this)
        return true
    }
}