package io.guthix.proto.builder

import kotlin.reflect.KClass

abstract class Property(var name: String) {
    abstract val type: KClass<out Any>

    open val cast: String = ""
}

abstract class PrimitiveProperty(name: String) : Property(name) {
    abstract override val type: KClass<out Number>
}