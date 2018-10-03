package com.softwaresaiyajin.navigator.manager

import com.softwaresaiyajin.navigator.model.*

/**
 * Created by geraldadorza on 4/10/2018.
 */
class Navigator {

    companion object {
        val shared = Navigator()
    }

    private var pathNotFoundObserver: PathNotFoundObserver? = null

    private var events: HashMap<String, NavigationEvent> = HashMap()

    val mappedLocations: List<String>
        get() = events.map { String.format("%s => %s", it.key, it.value.detail) }

    fun mapPathNotFound(observer: PathNotFoundObserver?): Navigator {
        pathNotFoundObserver = observer
        return this
    }

    fun <T: LocationInstance> map(ids: List<String>,
                                  location: T,
                                  observer: NavigationEventObserver): Navigator {

        val event = NavigationEvent(location, observer)
        ids.forEach { events[it] = event }
        return this
    }

    fun <T: LocationType> map(ids: List<String>,
                              location: LocationType,
                              observer: NavigationEventObserver): Navigator {

        val event = NavigationEvent(location, observer)
        ids.forEach { events[it] = event }
        return this
    }

    fun navigate(id: String, data: Any? = null) {

        val location = events[id]
        if (location != null && location?.isValid) {
            location.notifyObserver(data)
        }
        else {
            pathNotFoundObserver?.invoke(id, data)
        }
    }
}