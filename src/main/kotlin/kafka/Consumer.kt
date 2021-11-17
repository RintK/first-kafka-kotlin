package kafka

import dto.Person
import org.apache.kafka.clients.consumer.KafkaConsumer
import utils.ConfigManager
import java.time.Duration

class Consumer {
    //    private val topic = "quickstart-events"
    private val topic = "object-test-topic"

    fun run() {

        println("Set properties...")
        val prop = ConfigManager.loadKafkaConfig()

        println("Start to subscribe")
        val consumer =
            KafkaConsumer<String, Person?>(prop)
        consumer.subscribe(listOf(topic))

        println("Getting messages")
        for (i in 1..3) {
            val records = consumer.poll(Duration.ofMillis(1000))
            println(records.count())
            records.forEach { println(it.value()) }
        }
    }
}
