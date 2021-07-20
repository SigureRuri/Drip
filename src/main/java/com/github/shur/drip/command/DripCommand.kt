package com.github.shur.drip.command

import com.github.shur.drip.Drip
import com.github.shur.drip.api.trade.Trade
import com.github.shur.drip.api.trade.TradeId
import com.github.shur.drip.command.argument.TradeArgument
import com.github.shur.drip.command.argument.TradeIdArgument
import com.github.shur.drip.trade.yaml.YamlTrade
import com.github.shur.drip.ui.editor.ContentsEditorUI
import com.github.shur.drip.ui.editor.EditorUI
import com.github.shur.drip.ui.trade.TradeUI
import com.github.shur.drip.yaml.TradesYaml
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import org.bukkit.command.Command

object DripCommand {

    private val create = CommandAPICommand("create")
        .withPermission("drip.command.create")
        .withArguments(TradeIdArgument.tradeIdArgument("id"))
        .executesPlayer(PlayerCommandExecutor { sender, args ->
            val tradeId = args[0] as TradeId
            if (Drip.tradeRegistry.has(tradeId)) CommandAPI.fail("That trade has already existed").let { return@PlayerCommandExecutor }
            val trade = YamlTrade(tradeId, TradesYaml)

            Drip.tradeRegistry.register(trade)

            Command.broadcastCommandMessage(sender, "Created trade [${tradeId}]")
        })

    private val remove = CommandAPICommand("remove")
        .withPermission("drip.command.remove")
        .withArguments(TradeArgument.tradeArgument("trade"))
        .executesPlayer(PlayerCommandExecutor { sender, args ->
            val trade = args[0] as Trade

            Drip.tradeRegistry.unregister(trade.id)

            Command.broadcastCommandMessage(sender, "Removed team [${trade.id}]")
        })

    private val edit = CommandAPICommand("edit")
        .withPermission("drip.command.edit")
        .withArguments(TradeArgument.tradeArgument("trade"))
        .executesPlayer(PlayerCommandExecutor { sender, args ->
            val trade = args[0] as Trade

            EditorUI(trade).open(sender)

            Command.broadcastCommandMessage(sender, "Opened trade editor [${trade.id}]")
        })

    private val open = CommandAPICommand("open")
        .withPermission("drip.command.open")
        .withArguments(TradeArgument.tradeArgument("trade"))
        .executesPlayer(PlayerCommandExecutor { sender, args ->
            val trade = args[0] as Trade

            TradeUI(trade).open(sender)

            Command.broadcastCommandMessage(sender, "Opened trade [${trade.id}]")
        })

    private val drip = CommandAPICommand("drip")
        .withAliases("dtrade")
        .withSubcommand(create)
        .withSubcommand(remove)
        .withSubcommand(edit)
        .withSubcommand(open)

    internal fun registerCommand() {
        drip.register()
    }

}