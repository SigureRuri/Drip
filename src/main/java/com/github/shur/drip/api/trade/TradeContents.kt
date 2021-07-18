package com.github.shur.drip.api.trade

import org.bukkit.inventory.ItemStack

sealed class TradeContent

class BackPanel(
    panel: ItemStack
) : TradeContent() {

    var panel: ItemStack = panel
        get() = field.clone()
        set(value) { field = value.clone() }

}

class Product(
    sell: ItemStack,
    buy: MutableList<ItemStack>
) : TradeContent() {

    var sell: ItemStack = sell
        get() = field.clone()
        set(value) { field = value.clone() }

    var buy: MutableList<ItemStack> = buy
        get() = field.map { it.clone() }.toMutableList()
        set(value) { field = value.map { it.clone() }.toMutableList() }

}