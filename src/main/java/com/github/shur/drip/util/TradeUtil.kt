package com.github.shur.drip.util

import com.github.shur.drip.api.trade.Product
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType

object TradeUtil {

    @JvmStatic
    fun buy(product: Product, player: Player): Boolean {
        val playerInventory = player.inventory
        val inventoryForChecking = Bukkit.createInventory(null, InventoryType.PLAYER).apply {
            contents = playerInventory.contents
        }

        return if (inventoryForChecking.removeItem(*product.buy.toTypedArray()).isEmpty()) {
            playerInventory.removeItem(*product.buy.toTypedArray())

            if (playerInventory.firstEmpty() == -1) {
                player.world.dropItem(player.location, product.sell)
            } else {
                playerInventory.addItem(product.sell)
            }

            true
        } else {
            player.sendMessage("必要アイテムが不足しています。")
            false
        }
    }

}