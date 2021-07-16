package com.github.shur.drip.trade

import com.github.shur.drip.api.trade.BackPanel
import com.github.shur.drip.api.trade.Product
import com.github.shur.drip.api.trade.Trade
import com.github.shur.drip.api.trade.TradeContent
import com.github.shur.drip.api.trade.TradeId

abstract class AbstractTrade(
    override val id: TradeId
) : Trade {

    override var name: String = "Trade"

    override var isUnderMaintenance: Boolean = false

    override var contents: MutableMap<Int, TradeContent> = mutableMapOf()

    override fun getProduct(index: Int): Product? =
        contents[index] as? Product

    override fun setProduct(index: Int, product: Product) {
        contents[index] = product
    }

    override fun hasProduct(index: Int): Boolean =
        contents[index] is Product

    override fun getBackPanel(index: Int): BackPanel? =
        contents[index] as? BackPanel

    override fun setBackPanel(index: Int, panel: BackPanel) {
        contents[index] = panel
    }

    override fun hasBackPanel(index: Int): Boolean =
        contents[index] is BackPanel

    override fun getContent(index: Int): TradeContent? =
        contents[index]

    override fun setContent(index: Int, content: TradeContent) {
        contents[index] = content
    }

    override fun hasContent(index: Int): Boolean =
        contents.contains(index)

}