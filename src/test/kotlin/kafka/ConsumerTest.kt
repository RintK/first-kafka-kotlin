package kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

private val defaultKafkaConfig: Properties = Properties().apply {
    put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
    put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
    put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    put(ConsumerConfig.GROUP_ID_CONFIG, "test-group")
}

internal class ConsumerTest {
    @Test
    fun runTest() {
        val consumer = Consumer()
        consumer.run()
    }
}
