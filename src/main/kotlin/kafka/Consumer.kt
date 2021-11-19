package kafka

import dto.Person
import org.apache.kafka.clients.consumer.KafkaConsumer
import repository.sqlite.UserRepositoryImpl
import utils.ConfigManager
import java.time.Duration

class Consumer {
    //    private val topic = "quickstart-events"
    private val topic = "object-save-test-topic"

    fun run() {
        val repo = UserRepositoryImpl()

        println("Set properties...")
        val prop = ConfigManager.loadKafkaConfig()

        println("Start to subscribe")
        val consumer =
            KafkaConsumer<String, Person?>(prop)
        consumer.subscribe(listOf(topic))

        println("Getting messages")
        while (true) {
            val records = consumer.poll(Duration.ofMillis(1000))
            println(records.count())
            records.forEach {
                val p = it.value()
                println(p)
                if (p != null) {
                    if (repo.queryPerson(p.id) != null) {
                        repo.updatePerson(p)
                    } else {
                        repo.createPerson(p)
                    }
                }
            }
        }
    }
}
