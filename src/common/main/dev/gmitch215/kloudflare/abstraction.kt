package dev.gmitch215.kloudflare

/**
 * Represents an entity that has a unique identifier.
 * @property id The unique identifier of the entity.
 */
interface Identifiable {
    val id: String
}

/**
 * Represents an entity that has a name.
 * @property name The name of the entity.
 */
interface Nameable {
    val name: String
}

/**
 * Represents an entity that has a type property.
 * @property type The type of the entity, typically used for categorization or identification.
 */
interface Typed {
    val type: String
}