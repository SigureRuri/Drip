package com.github.shur.drip.ui.editor

import com.github.shur.drip.api.trade.BackPanel
import com.github.shur.drip.api.trade.Product
import com.github.shur.drip.api.trade.Trade
import com.github.shur.whitebait.dsl.window
import com.github.shur.whitebait.inventory.InventoryUI
import com.github.shur.whitebait.inventory.window.SizedWindowOption
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryType

class ContentsEditorUI(
    val trade: Trade
) : InventoryUI {

    override val window by window(SizedWindowOption(6 * 9)) {

        title = "${ChatColor.RED}${ChatColor.BOLD}アイテム編集${ChatColor.GRAY} - ${ChatColor.WHITE}${trade.name}"

        (0 until (6 * 9)).forEach { index ->
            when (trade.getContent(index)) {
                is BackPanel -> {
                    slot(index) {
                        val panel = trade.getBackPanel(index)!!
                        icon(panel.panel) {
                            lore = (lore + mutableListOf("", "${ChatColor.YELLOW}${ChatColor.BOLD}クリックで編集")).toMutableList()
                        }
                        onClickFilterNotDoubleClick {
                            BackPanelEditorUI(trade, index).openLater(player)
                        }
                    }
                }
                is Product -> {
                    slot(index) {
                        val product = trade.getProduct(index)!!
                        icon(product.sell) {
                            lore = (lore + mutableListOf("", "${ChatColor.YELLOW}${ChatColor.BOLD}クリックで編集")).toMutableList()
                        }
                        onClickFilterNotDoubleClick {
                            ProductEditorUI(trade, index).openLater(player)
                        }
                    }
                }
                else -> {
                    slot(index) {
                        icon {
                            type = Material.GLASS_PANE
                            name = "${ChatColor.YELLOW}${ChatColor.BOLD}クリックで編集"
                        }
                        onClickFilterNotDoubleClick {
                            ContentTypeSelector(trade, index).openLater(player)
                        }
                    }
                }
            }
        }

        onClick {
            if (slotType == InventoryType.SlotType.OUTSIDE) {
                EditorUI(trade).openLater(player)
            }
        }

    }

}