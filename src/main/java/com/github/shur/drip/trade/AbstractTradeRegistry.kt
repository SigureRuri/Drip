package com.github.shur.drip.trade

import com.github.shur.drip.api.trade.Trade
import com.github.shur.drip.api.trade.TradeId
import com.github.shur.drip.api.trade.TradeRegistry

abstract class AbstractTradeRegistry : TradeRegistry {

    protected val trades: MutableMap<TradeId, Trade> = mutableMapOf()

    override fun get(id: TradeId): Trade? =
        trades[id]

    override fun getAll(): List<Trade> =
        trades.values.toList()

    override fun has(id: TradeId): Boolean =
        trades.contains(id)

    override fun register(trade: Trade) {
        if (trades.contains(trade.id)) throw IllegalArgumentException()

        trades[trade.id] = trade
    }

    override fun unregister(id: TradeId) {
        trades.remove(id)
    }

}