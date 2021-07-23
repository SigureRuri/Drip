package com.github.shur.drip.externalplugin.harukatrade

import com.github.shur.drip.Drip
import com.github.shur.drip.api.trade.BackPanel
import com.github.shur.drip.api.trade.Product
import com.github.shur.drip.api.trade.Trade
import org.bukkit.configuration.MemoryConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object HarukaFileExporter {

    fun export(trade: Trade) {
        val dataFolder = File("${Drip.instance.dataFolder.absolutePath}/export/harukatrade")
        if (!dataFolder.exists() || !dataFolder.isDirectory) dataFolder.mkdirs()

        val tradeFile = File("${dataFolder.absoluteFile}/${trade.id}.yml")
        YamlConfiguration().apply {
            set("name", trade.name)
            set("owners", trade.owners.map { it.toString() })

            val contentsConfig = MemoryConfiguration()
            trade.contents.forEach { (contentIndex, content) ->
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
            set("contents", contentsConfig)

            save(tradeFile)
        }
    }

    fun exportAll() {
        Drip.tradeRegistry.getAll().forEach {
            export(it)
        }
    }

}