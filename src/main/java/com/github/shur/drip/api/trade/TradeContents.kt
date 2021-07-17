package com.github.shur.drip.api.trade

import org.bukkit.inventory.ItemStack

sealed class TradeContent

class BackPanel(
    var panel: ItemStack
) : TradeContent()

class Product(
    var sell: ItemStack,
    var buy: MutableList<ItemStack>
) : TradeContent()