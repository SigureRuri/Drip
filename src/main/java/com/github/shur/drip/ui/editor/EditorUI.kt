package com.github.shur.drip.ui.editor

import com.github.shur.drip.Drip
import com.github.shur.drip.api.trade.Trade
import com.github.shur.renachataccessor.RenaChatAccessor
import com.github.shur.renachataccessor.chataccessor.ChatAccessor
import com.github.shur.whitebait.dsl.window
import com.github.shur.whitebait.inventory.InventoryUI
import com.github.shur.whitebait.inventory.window.SizedWindowOption
import org.bukkit.Bukkit
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
            }
            onClickFilterNotDoubleClick {
                RenaChatAccessor.getChatAccessorManager().register(
                    ChatAccessor(player)
                        .expirationTicks(20L * 60L)
                        .onResponse { input ->
                            val newName = ChatColor.translateAlternateColorCodes('&', input)

                            trade.name = newName

                            player.sendMessage("名前を編集しました。 [${newName}${ChatColor.RESET}]")
                        }
                        .onCancel {
                            player.sendMessage("名前編集をキャンセルしました。")
                        }
                )

                player.sendMessage("チャット欄に名前を入力してください。")
                player.sendMessage("名前にはカラーコード[&]を使用できます。")

                Bukkit.getScheduler().runTask(Drip.instance, Runnable { player.closeInventory() })
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