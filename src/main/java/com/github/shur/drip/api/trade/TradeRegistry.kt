package com.github.shur.drip.api.trade

interface TradeRegistry {

    fun get(id: TradeId): Trade?

    fun getAll(): List<Trade>

    fun has(id: TradeId): Boolean

    fun register(trade: Trade)

    fun unregister(id: TradeId)

    fun load()

    fun save()

}