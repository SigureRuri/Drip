package com.github.shur.drip.command.argument

import com.github.shur.drip.api.trade.TradeId
import dev.jorel.commandapi.arguments.CustomArgument

object TradeIdArgument {

    fun tradeIdArgument(nodeName: String) =
        CustomArgument(nodeName) {
            try {
                TradeId(it)
            } catch (e: IllegalArgumentException) {
                throw CustomArgument.CustomArgumentException(
                    CustomArgument.MessageBuilder("Invalid tradeId: ").appendArgInput()
                )
            }
        }

}