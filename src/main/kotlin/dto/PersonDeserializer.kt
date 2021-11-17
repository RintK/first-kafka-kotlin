package dto

import com.google.gson.Gson
import org.apache.kafka.common.serialization.Deserializer

class PersonDeserializer : Deserializer<Person?> {
    override fun deserialize(topic: String?, data: ByteArray?): Person? {
        if (data == null) {
            println("Null received")
            return null
        }

        val parser = Gson()
        return parser.fromJson(data.toString(Charsets.UTF_8), Person::class.java)
    }
}
