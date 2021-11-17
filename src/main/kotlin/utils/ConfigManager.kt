package utils

import java.util.*

object ConfigManager {
    private const val kafkaConfigFilePath = "kafka.properties"

    fun loadKafkaConfig(): Properties {
        val configInputStream = this.javaClass.classLoader.getResourceAsStream(kafkaConfigFilePath)

        val prop = Properties().apply {
            load(configInputStream)
        }

        println("load complete")
        println(prop)
        return prop
    }
}
