package org.vuestock.app.infrastructure.security

data class OAuth2Attribute(
    val name: String,
    val email: String
) {
    companion object {
        fun of(attr: Map<String, Any>): OAuth2Attribute {
            val name: String = attr["name"] as String
            val email: String = attr["email"] as String
            return OAuth2Attribute(name, email)
        }
    }
}