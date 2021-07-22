package com.github.shur.drip.trade.yaml

import com.github.shur.drip.api.trade.BackPanel
import com.github.shur.drip.api.trade.Product
import com.github.shur.drip.api.trade.TradeContent
import com.github.shur.drip.api.trade.TradeId
import com.github.shur.drip.trade.AbstractTrade
import com.github.shur.drip.yaml.Yaml
import org.bukkit.configuration.MemoryConfiguration
import java.util.*

class YamlTrade(
    id: TradeId,
    private val parentConfig: Yaml
) : AbstractTrade(id) {

    override fun load() {
        val config = parentConfig.getConfigurationSection(id.toString())!!

        name = config.getString("name")!!
        isUnderMaintenance = config.getBoolean("isUnderMaintenance")
        owners = config.getStringList("owners").map { UUID.fromString(it) }.toMutableSet()

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
                    val sell = contentConfig.getItemStack("sell")!!
                    val buys = contentConfig.getConfigurationSection("buy")!!.let { buyConfig ->
                        buyConfig.getKeys(false)
                            .map { buyConfig.getItemStack(it)!! }
                            .toMutableList()
                    }
                    Product(sell, buys)
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
        config.set("owners", owners.map { it.toString() })

        val contentsConfig = MemoryConfiguration()
        contents.forEach { (contentIndex, content) ->
            val contentConfig = MemoryConfiguration()
            when (content) {
                is BackPanel -> {
                    contentConfig.set("type", "backPanel")
                    contentConfig.set("panel", content.panel)
                }
                is Product -> {
                    contentConfig.set("type", "product")
                    contentConfig.set("sell", content.sell)

                    val buyConfig = MemoryConfiguration()
                    content.buy.forEachIndexed { buyIndex, buy ->
                        buyConfig.set(buyIndex.toString(), buy)
                    }
                    contentConfig.set("buy", buyConfig)
                }
            }

            contentsConfig.set(contentIndex.toString(), contentConfig)
        }
        config.set("contents", contentsConfig)

        parentConfig.set(id.toString(), config)

        parentConfig.save()
    }

    companion object {

        fun createAndLoad(parentConfig: Yaml, id: String): YamlTrade =
            YamlTrade(TradeId(id), parentConfig).apply { load() }

    }

}