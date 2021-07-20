package com.github.shur.drip.command.argument

import com.github.shur.drip.Drip
import com.github.shur.drip.api.trade.TradeId
import dev.jorel.commandapi.arguments.CustomArgument
import org.bukkit.command.CommandSender

object TradeArgument {

    fun tradeArgument(nodeName: String) =
        CustomArgument(nodeName) {
            val tradeId = try {
                TradeId(it)
            } catch (e: IllegalArgumentException) {
                throw CustomArgument.CustomArgumentException(
                    CustomArgument.MessageBuilder("Invalid tradeId: ").appendArgInput()
                )
            }

            Drip.tradeRegistry.get(tradeId) ?: throw CustomArgument.CustomArgumentException(
                CustomArgument.MessageBuilder("Unknown trade: ").appendArgInput()
            )
        }.overrideSuggestions { _: CommandSender ->
            Drip.tradeRegistry.getAll()
                .map { it.id.toString() }
                .toTypedArray()
        }

}