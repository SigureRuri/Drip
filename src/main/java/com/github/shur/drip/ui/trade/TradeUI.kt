package com.github.shur.drip.ui.trade

import com.github.shur.drip.api.trade.BackPanel
import com.github.shur.drip.api.trade.Product
import com.github.shur.drip.api.trade.Trade
import com.github.shur.whitebait.dsl.window
import com.github.shur.whitebait.inventory.InventoryUI
import com.github.shur.whitebait.inventory.window.SizedWindowOption
import org.bukkit.ChatColor

class TradeUI(val trade: Trade) : InventoryUI {

    override val window by window(SizedWindowOption(6 * 9)) {

        title = trade.name

        trade.contents.forEach { (slotIndex, content) ->
            if (slotIndex < 0 && slotIndex > (9 * 9) - 1) return@forEach

            when (content) {
                is BackPanel -> {
                    slot(slotIndex) {
                        icon(content.panel) {}
                    }
                }
                is Product -> {
                    slot(slotIndex) {
                        icon(content.sell) {

                            val buyLorePrefix = mutableListOf(
                                "",
                                "${ChatColor.DARK_GRAY}---------------",
                                "",
                                "${ChatColor.AQUA}${ChatColor.BOLD}必要アイテム"
                            )

                            val buyLore = content.buy
                                .map { buy ->
                                    if (buy.hasItemMeta() && buy.itemMeta!!.hasDisplayName()) {
                                        buy.itemMeta!!.displayName
                                    } else {
                                        buy.type.name.toLowerCase().replace("_", " ").split(" ")
                                            .joinToString(" ") {
                                                it.substring(0, 1).toUpperCase() + it.substring(1).toLowerCase()
                                            }
                                    }
                                }
                                .map { "${ChatColor.RESET}${ChatColor.DARK_AQUA} * ${ChatColor.WHITE}$it" }
                                .toMutableList()

                            val description = mutableListOf(
                                "",
                                "${ChatColor.GRAY}${ChatColor.UNDERLINE}右クリックで詳細を表示",
                                "${ChatColor.YELLOW}${ChatColor.UNDERLINE}左クリックで購入",
                            )

                            lore = (lore + buyLorePrefix + buyLore + description).toMutableList()
                        }

                        onClickFilterNotDoubleClick {
                            if (isRightClick) {
                                ProductDetailsUI(content).openLater(player)
                            } else if (isLeftClick) {
                                val playerInventory = player.inventory

                                content.buy.forEach {
                                    if (!playerInventory.containsAtLeast(it, it.amount)) {
                                        player.sendMessage("必要アイテムが不足しています")
                                        return@onClickFilterNotDoubleClick
                                    }
                                }

                                content.buy.forEach {
                                    playerInventory.removeItem(it)
                                }

                                if (playerInventory.firstEmpty() == -1) {
                                    player.world.dropItem(player.location, content.sell)
                                } else {
                                    playerInventory.addItem(content.sell)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}