package com.github.shur.drip.ui.editor

import com.github.shur.drip.api.trade.BackPanel
import com.github.shur.drip.api.trade.Trade
import com.github.shur.whitebait.dsl.window
import com.github.shur.whitebait.inventory.InventoryUI
import com.github.shur.whitebait.inventory.window.SizedWindowOption
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

class BackPanelEditorUI(
    val trade: Trade,
    val index: Int
) : InventoryUI {

    val panel = trade.getBackPanel(index) ?: BackPanel(ItemStack(Material.AIR))

    override val window by window(SizedWindowOption(3 * 9)) {

        title = "${ChatColor.BLUE}${ChatColor.BOLD}アイテム編集 / 詳細"

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
            icon(panel.panel) {  }
            onClick {
                event.isCancelled = false
            }
        }

        onClick {
            if (event.clickedInventory?.type == InventoryType.PLAYER) {
                event.isCancelled = false
            }
            if (slotType == InventoryType.SlotType.OUTSIDE) {
                val item = inventory.getItem(13)
                if (item == null) {
                    trade.removeContent(index)
                } else {
                    panel.panel = item
                    trade.setBackPanel(index, panel)
                }

                ContentsEditorUI(trade).openLater(player)
            }
        }

    }

}