package com.github.shur.drip.api.trade

import java.util.*

interface Trade {

    val id: TradeId

    var name: String

    var isUnderMaintenance: Boolean

    var owners: MutableSet<UUID>

    var contents: MutableMap<Int, TradeContent>

    fun addOwner(uuid: UUID)

    fun removeOwner(uuid: UUID)

    fun hasOwner(uuid: UUID): Boolean

    fun getProduct(index: Int): Product?

    fun setProduct(index: Int, product: Product)

    fun hasProduct(index: Int): Boolean

    fun getBackPanel(index: Int): BackPanel?

    fun setBackPanel(index: Int, panel: BackPanel)

    fun hasBackPanel(index: Int): Boolean

    fun getContent(index: Int): TradeContent?

    fun setContent(index: Int, content: TradeContent)

    fun hasContent(index: Int): Boolean

    fun removeContent(index: Int)

    fun save()

    fun load()

}