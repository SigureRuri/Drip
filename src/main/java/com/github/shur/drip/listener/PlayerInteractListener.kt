package com.github.shur.drip.listener

import com.github.shur.drip.Drip
import com.github.shur.drip.ui.trade.TradeUI
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent

class PlayerInteractListener : Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    fun onInteractEntity(event: PlayerInteractEntityEvent) {
        val player = event.player

        Drip.tradeRegistry.getAll().forEach {
            if (!it.hasOwner(event.rightClicked.uniqueId)) return@forEach
            if (it.isUnderMaintenance && !player.hasPermission("drip.bypass.maintenance")) return@forEach
            if (!player.hasPermission("drip.trade.*") && !player.hasPermission("drip.trade.${it.id}")) return@forEach

            TradeUI(it).open(event.player)

            event.isCancelled = true
        }

    }

}