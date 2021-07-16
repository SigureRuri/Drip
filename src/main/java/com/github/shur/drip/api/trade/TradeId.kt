package com.github.shur.drip.api.trade

data class TradeId(private val id: String) {

    init {
        if (!id.matches(REGEX)) throw IllegalArgumentException("id must match [a-z_0-9]+")
    }

    override fun toString(): String {
        return id
    }

    companion object {
        private val REGEX = "[a-z_0-9]+".toRegex()
    }

}