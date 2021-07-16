package com.github.shur.drip.trade.yaml

import com.github.shur.drip.api.trade.BackPanel
import com.github.shur.drip.api.trade.Product
import com.github.shur.drip.api.trade.TradeContent
import com.github.shur.drip.api.trade.TradeId
import com.github.shur.drip.trade.AbstractTrade
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.MemoryConfiguration

class YamlTrade(
    id: TradeId,
    private val parentConfig: ConfigurationSection
) : AbstractTrade(id) {

    override fun load() {
        val config = parentConfig.getConfigurationSection(id.toString())!!

        name = config.getString("name")!!
        isUnderMaintenance = config.getBoolean("isUnderMaintenance")

        val contentsConfig = config.getConfigurationSection("contents") ?: MemoryConfiguration()
        contentsConfig.getKeys(false).forEach { contentsKey ->
            val keyInt = contentsKey.toIntOrNull() ?: return@forEach
            val contentConfig = contentsConfig.getConfigurationSection(contentsKey)!!
            val content: TradeContent = when (contentConfig.getString("type")!!) {
                "backPanel" -> {
                    val panel = contentConfig.getItemStack("panel")!!
                    BackPanel(panel)
                }
                "product" -> {
                    val buy = contentConfig.getItemStack("buy")!!
                    val sells = contentConfig.getConfigurationSection("sell")!!.let { sellConfig ->
                        sellConfig.getKeys(false)
                            .map { sellConfig.getItemStack(it)!! }
                            .toMutableList()
                    }
                    Product(buy, sells)
                }
                else -> return@forEach
            }

            contents[keyInt] = content
        }
    }

    override fun save() {
        val config = MemoryConfiguration()

        config.set("name", name)
        config.set("isUnderMaintenance", isUnderMaintenance)

        contents.forEach { (contentIndex, content) ->
            val contentConfig = MemoryConfiguration()

            when (content) {
                is BackPanel -> {
                    contentConfig.set("type", "backPanel")
                    contentConfig.set("panel", content.panel)
                }
                is Product -> {
                    contentConfig.set("type", "product")
                    contentConfig.set("buy", content.buy)

                    val sellConfig = MemoryConfiguration()
                    content.sell.forEachIndexed { sellIndex, sell ->
                        sellConfig.set(sellIndex.toString(), sell)
                    }
                    contentConfig.set("sell", sellConfig)
                }
            }

            config.set(contentIndex.toString(), contentConfig)
        }

        parentConfig.set(id.toString(), config)
    }

    companion object {

        fun createAndLoad(parentConfig: ConfigurationSection, id: String): YamlTrade =
            YamlTrade(TradeId(id), parentConfig).apply { load() }

    }

}