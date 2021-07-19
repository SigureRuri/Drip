package com.github.shur.drip.ui.editor

import com.github.shur.drip.api.trade.BackPanel
import com.github.shur.drip.api.trade.Product
import com.github.shur.drip.api.trade.Trade
import com.github.shur.drip.ui.trade.ProductDetailsUI
import com.github.shur.whitebait.dsl.window
import com.github.shur.whitebait.inventory.InventoryUI
import com.github.shur.whitebait.inventory.window.SizedWindowOption
import org.bukkit.ChatColor
import org.bukkit.Material

class EditorUI(
    val trade: Trade
) : InventoryUI {

    override val window by window(SizedWindowOption(3 * 9)) {

        title = "${ChatColor.RED}${ChatColor.BOLD}編集${ChatColor.GRAY} - ${ChatColor.WHITE}${trade.name}"

        defaultSlot {
            icon {
                type = Material.GRAY_STAINED_GLASS_PANE
                name = " "
            }
        }

        slot(10) {
            icon {
                type = Material.DIAMOND_SWORD
                name = "${ChatColor.GOLD}${ChatColor.BOLD}${ChatColor.UNDERLINE}アイテム編集"
            }
            onClickFilterNotDoubleClick {
                ContentsEditorUI(trade).openLater(player)
            }
        }

        slot(11) {
            icon {
                type = Material.NAME_TAG
                name = "${ChatColor.BLUE}${ChatColor.BOLD}${ChatColor.UNDERLINE}名前編集"
                lore = mutableListOf("", "${ChatColor.RED}※未実装")
            }
        }

        slot(12) {
            icon {
                type = Material.TOTEM_OF_UNDYING
                val maintenanceStatus = when (trade.isUnderMaintenance) {
                    true -> "${ChatColor.RED}${ChatColor.BOLD}${ChatColor.UNDERLINE}ON"
                    false -> "${ChatColor.BLUE}${ChatColor.BOLD}${ChatColor.UNDERLINE}OFF"
                }
                name = "${ChatColor.GREEN}${ChatColor.BOLD}${ChatColor.UNDERLINE}メンテナンスモード${ChatColor.RESET} : $maintenanceStatus"
            }
            onClickFilterNotDoubleClick {
                trade.isUnderMaintenance = !trade.isUnderMaintenance

                EditorUI(trade).openLater(player)
            }
        }

    }

}