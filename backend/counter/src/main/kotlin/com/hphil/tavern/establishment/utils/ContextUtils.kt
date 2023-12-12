package com.hphil.tavern.establishment.utils

import io.javalin.Javalin
import io.javalin.http.Context
import kotlin.reflect.KClass

fun <T : Any> Context.getContextAppAttribute(klass: KClass<T>) = appAttribute<T>(klass.java.name)

fun Javalin.setAppAttributes(vararg list: Any): Javalin {
    // TODO validate is this works
    list.forEach { attribute(it::class.java.name, it) }
    return this
}
