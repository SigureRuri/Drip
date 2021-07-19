package com.github.shur.drip.ui.editor

import com.github.shur.drip.api.trade.Trade
import com.github.shur.whitebait.dsl.window
import com.github.shur.whitebait.inventory.InventoryUI
import com.github.shur.whitebait.inventory.window.TypedWindowOption
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryType

class ContentTypeSelector(
    val trade: Trade,
    val index: Int
) : InventoryUI {

    override val window by window(TypedWindowOption(InventoryType.HOPPER)) {

        slot(1) {
            icon {
                type = Material.DIAMOND_SWORD
                name = "${ChatColor.GOLD}${ChatColor.BOLD}アイテム"
            }
            onClickFilterNotDoubleClick {
                ProductEditorUI(trade, index).openLater(player)
            }
        }

        slot(3) {
            icon {
                type = Material.GRAY_STAINED_GLASS_PANE
                name = "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}バックパネル"
            }
            onClickFilterNotDoubleClick {
                BackPanelEditorUI(trade, index).openLater(player)
            }
        }

        onClick {
            if (slotType == InventoryType.SlotType.OUTSIDE) {
                ContentsEditorUI(trade).openLater(player)
            }

        }

    }

}