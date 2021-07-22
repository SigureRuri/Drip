package com.github.shur.drip

import com.github.shur.drip.api.trade.TradeRegistry
import com.github.shur.drip.command.DripCommand
import com.github.shur.drip.listener.PlayerInteractListener
import com.github.shur.drip.trade.yaml.YamlTradeRegistry
import com.github.shur.drip.yaml.TradesYaml
import org.bukkit.plugin.java.JavaPlugin

class Drip : JavaPlugin() {

    override fun onEnable() {
        instance = this

        tradeRegistry = YamlTradeRegistry(TradesYaml)
        tradeRegistry.load()

        DripCommand.registerCommand()

        server.pluginManager.registerEvents(PlayerInteractListener(), this)
    }

    override fun onDisable() {
        tradeRegistry.save()
    }

    companion object {

        lateinit var instance: Drip
            private set

        lateinit var tradeRegistry: TradeRegistry
            private set

    }

}