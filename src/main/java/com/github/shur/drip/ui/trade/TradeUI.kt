package com.github.shur.drip.ui.trade

import com.github.shur.drip.api.trade.BackPanel
import com.github.shur.drip.api.trade.Product
import com.github.shur.drip.api.trade.Trade
import com.github.shur.drip.util.TradeUtil
import com.github.shur.whitebait.dsl.window
import com.github.shur.whitebait.inventory.InventoryUI
import com.github.shur.whitebait.inventory.window.SizedWindowOption
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.PlayerInventory

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

                            val buy = content.buy
                                .map { buy ->
                                    val displayName =
                                        if (buy.hasItemMeta() && buy.itemMeta!!.hasDisplayName()) {
                                            buy.itemMeta!!.displayName
                                        } else {
                                            buy.type.name.toLowerCase().replace("_", " ").split(" ")
                                                .joinToString(" ") {
                                                    it.substring(0, 1).toUpperCase() + it.substring(1).toLowerCase()
                                                }
                                        }
                                    "${ChatColor.RESET}${ChatColor.DARK_AQUA} * ${ChatColor.WHITE}$displayName x ${buy.amount}"
                                }
                                .toMutableList()
                            val buyLore =
                                if (buy.isEmpty()) {
                                    listOf("${ChatColor.WHITE} 無し")
                                } else {
                                    buy
                                }

                            val descriptionLore = listOf(
                                "",
                                "${ChatColor.GRAY}${ChatColor.UNDERLINE}右クリックで詳細を表示",
                                "${ChatColor.YELLOW}${ChatColor.UNDERLINE}左クリックで購入",
                            )

                            lore = (lore + buyLorePrefix + buyLore + descriptionLore).toMutableList()
                        }

                        onClickFilterNotDoubleClick {
                            if (isRightClick) {
                                ProductDetailsUI(trade, content).openLater(player)
                            } else if (isLeftClick) {
                                if (isShiftClick) {
                                    TradeUtil.buy(content, player)
                                } else {
                                    // 2回クリックで3回実行されるが仕様上しかたがない
                                    PurchaseConfirmationUI(trade, content).openLater(player)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}