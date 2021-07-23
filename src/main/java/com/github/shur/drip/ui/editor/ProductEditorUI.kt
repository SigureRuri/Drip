package com.github.shur.drip.ui.editor

import com.github.shur.drip.api.trade.Product
import com.github.shur.drip.api.trade.Trade
import com.github.shur.whitebait.dsl.window
import com.github.shur.whitebait.inventory.InventoryUI
import com.github.shur.whitebait.inventory.window.SizedWindowOption
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class ProductEditorUI(
    val trade: Trade,
    val index: Int,
    val pageOfBuy: Int = 1,
    var sell: ItemStack? = null,
    var buys: MutableMap<Int, ItemStack> = mutableMapOf()
) : InventoryUI {

    val product = trade.getProduct(index) ?: Product(ItemStack(Material.AIR), mutableListOf())

    init {
        product.buy.forEachIndexed { buyIndex, buyItemStack ->
            buys[buyIndex] = buyItemStack
        }
    }

    override val window by window(SizedWindowOption(6 * 9)) {

        title = "${ChatColor.BLUE}${ChatColor.BOLD}アイテム編集 / 詳細 $pageOfBuy"

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
            if (sell == null) {
                icon(product.sell) { }
            } else {
                icon(sell!!.clone()) { }
            }
            onClick {
                event.isCancelled = false
            }
        }

        slot(18) {
            icon {
                type = Material.NAME_TAG
                name = "${ChatColor.RED}${ChatColor.BOLD}必要アイテム"
            }
        }

        if (pageOfBuy > 1) {
            slot(52) {
                icon {
                    type = Material.ARROW
                    name = "${ChatColor.RED}前のページへ"
                }
                onClickFilterNotDoubleClick {
                    saveDeliveryValueToThis(inventory)
                    ProductEditorUI(trade, index, pageOfBuy - 1, sell, buys).openLater(player)
                }
            }
        }

        slot(53) {
            icon {
                type = Material.ARROW
                name = "${ChatColor.RED}次のページへ"
            }
            onClickFilterNotDoubleClick {
                saveDeliveryValueToThis(inventory)

                ProductEditorUI(trade, index, pageOfBuy + 1, sell, buys).openLater(player)
            }
        }


        (0 until (2 * 9)).forEach {
            val slotIndex = it + 27
            val buyIndex = it + (((2 * 9) * (pageOfBuy - 1)))

            slot(slotIndex) {
                val buy = buys[buyIndex]
                if (buy != null) {
                    icon(buy) { }
                }

                onClick {
                    event.isCancelled = false
                }
            }
        }

        onClick {
            if (event.clickedInventory?.type == InventoryType.PLAYER) {
                event.isCancelled = false
            }

            if (slotType == InventoryType.SlotType.OUTSIDE) {
                saveDeliveryValueToThis(inventory)

                if (sell == null) {
                    trade.removeContent(index)
                } else {
                    product.sell = sell!!
                    product.buy = buys.values.toMutableList()
                    trade.setProduct(index, product)
                }

                ContentsEditorUI(trade).openLater(player)
            }
        }

    }

    private fun saveDeliveryValueToThis(inventory: Inventory) {
        sell = inventory.getItem(13)

        (0 until (2 * 9)).forEach {
            val slotIndex = it + 27
            val buyIndex = it + (((2 * 9) * (pageOfBuy - 1)))
            val item = inventory.getItem(slotIndex)

            if (item == null) {
                buys.remove(buyIndex)
            } else {
                buys[buyIndex] = item
            }
        }
    }

}