package com.github.shur.drip.ui.trade

import com.github.shur.drip.api.trade.Product
import com.github.shur.drip.api.trade.Trade
import com.github.shur.whitebait.dsl.window
import com.github.shur.whitebait.inventory.InventoryUI
import com.github.shur.whitebait.inventory.window.SizedWindowOption
import org.bukkit.ChatColor
import org.bukkit.Material

class ProductDetailsUI(
    val trade: Trade,
    val product: Product,
    val pageOfBuy: Int = 1
) : InventoryUI {

    val maxPageOfBuy = (product.buy.size + (2 * 9) - 1) / (2 * 9)

    override val window by window(SizedWindowOption(6 * 9)) {

        title = "${ChatColor.BLUE}${ChatColor.BOLD}アイテム詳細  $pageOfBuy/$maxPageOfBuy"

        defaultSlot {
            icon {
                type = Material.GRAY_STAINED_GLASS_PANE
                name = " "
            }
        }

        slot(4) {
            icon {
                type = Material.BOOK
                name = "${ChatColor.RED}${ChatColor.BOLD}アイテム"
            }
        }

        slot(13) {
            icon(product.sell) { }
        }

        slot(18) {
            icon {
                type = Material.NAME_TAG
                name = "${ChatColor.RED}${ChatColor.BOLD}必要アイテム"
            }
        }

        slot(52) {
            icon {
                type = Material.ARROW
                name = "${ChatColor.RED}前のページへ"
            }
            onClickFilterNotDoubleClick {
                if (pageOfBuy > 1) {
                    ProductDetailsUI(trade, product, pageOfBuy - 1).openLater(player)
                }
            }
        }

        slot(53) {
            icon {
                type = Material.ARROW
                name = "${ChatColor.RED}次のページへ"
            }
            onClickFilterNotDoubleClick {
                if (pageOfBuy < maxPageOfBuy) {
                    ProductDetailsUI(trade, product, pageOfBuy + 1).openLater(player)
                }
            }
        }

        slot(45) {
            icon {
                type = Material.BARRIER
                name = "${ChatColor.RED}${ChatColor.BOLD}戻る"
            }
            onClickFilterNotDoubleClick {
                TradeUI(trade).openLater(player)
            }
        }

        val buys = product.buy
        (0 until (2 * 9)).forEach {
            val slotIndex = it + 27
            val buyIndex = it + (((2 * 9) * (pageOfBuy - 1)))

            if (buys.getOrNull(buyIndex) == null) {
                slot(slotIndex) {}
            } else {
                val buy = buys[buyIndex]
                slot(slotIndex) {
                    icon(buy) { }
                }
            }
        }

    }

}