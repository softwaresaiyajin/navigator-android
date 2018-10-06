package com.softwaresaiyajin.navigator.manager

import com.softwaresaiyajin.navigator.model.*
import kotlin.reflect.KClass

/**
 * Created by geraldadorza on 4/10/2018.
 */
class Navigator {

    companion object {
        val shared = Navigator()
    }

    private var pathNotFoundObserver: PathNotFoundObserver? = null

    private var events: HashMap<LocationId, IEvent> = HashMap()

    private fun <T: LocationType> createInterfaceFromLambdaExpression(
            observer: (NavigationEvent<T>) -> Unit): NavigationEvent.IObserver<T> {
        return object: NavigationEvent.IObserver<T> {
            override fun onNavigate(event: NavigationEvent<T>) {
                observer.invoke(event)
            }
        }
    }

    fun mapPathNotFound(observer: PathNotFoundObserver?): Navigator {
        pathNotFoundObserver = observer
        return this
    }

    fun <T: LocationType> map(ids: List<LocationId>,
                              location: KClass<T>,
                              observer: (NavigationEvent<T>) -> Unit): Navigator {

        val event = NavigationEvent(location, createInterfaceFromLambdaExpression(observer))
        ids.forEach { events[it] = event }
        return this
    }

    fun <T: LocationType> map(ids: List<LocationId>,
                              location: T,
                              observer: (NavigationEvent<T>) -> Unit): Navigator {

        val event = NavigationEvent(location, createInterfaceFromLambdaExpression(observer))
        ids.forEach { events[it] = event }
        return this
    }

    fun navigate(id: LocationId, data: LocationData? = null) {

        val location = events[id]
        val isSuccess = location?.notifyObserver(data) ?: false
        if (!isSuccess) {
            pathNotFoundObserver?.invoke(id, data)
        }
    }
}