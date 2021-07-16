package com.github.shur.drip.trade.yaml

import com.github.shur.drip.trade.AbstractTradeRegistry
import com.github.shur.drip.yaml.Yaml

class YamlTradeRegistry(
    private val yaml: Yaml
) : AbstractTradeRegistry() {

    override fun load() {
        trades.clear()

        yaml.getKeys(false).forEach {
            val trade = YamlTrade.createAndLoad(yaml, it)

            register(trade)
        }
    }

    override fun save() {
        yaml.getKeys(false).forEach {
            yaml.set(it, null)
        }

        trades.values.forEach {
            it.save()
        }

        yaml.save()
    }

}