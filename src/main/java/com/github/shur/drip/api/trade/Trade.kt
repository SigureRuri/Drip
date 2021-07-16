package com.github.shur.drip.api.trade

interface Trade {

    val id: TradeId

    var name: String

    var isUnderMaintenance: Boolean

    var contents: MutableMap<Int, TradeContent>

    fun getProduct(index: Int): Product?

    fun setProduct(index: Int, product: Product)

    fun hasProduct(index: Int): Boolean

    fun getBackPanel(index: Int): BackPanel?

    fun setBackPanel(index: Int, panel: BackPanel)

    fun hasBackPanel(index: Int): Boolean

    fun getContent(index: Int): TradeContent?

    fun setContent(index: Int, content: TradeContent)

    fun hasContent(index: Int): Boolean

    fun save()

    fun load()

}