package repository.sqlite

import dto.Person
import repository.UserRepository
import java.sql.Connection
import java.sql.DriverManager
import kotlin.system.exitProcess

class UserRepositoryImpl : UserRepository {
    private var c: Connection

    init {
        try {
            Class.forName("org.sqlite.JDBC")
            c = DriverManager.getConnection("jdbc:sqlite:kafkaData.db")
            createUserTable()
        } catch (e: Exception) {
            println("Connection failed!!! ${e.message}")
            exitProcess(1)
        }
    }

    override fun createPerson(person: Person) {
        c.createStatement().use {
            val sql = """
                INSERT INTO USER
                    (ID, NAME, AGE, ASSET, COUNTRY)
                VALUES
                    (${person.id}, '${person.name}', ${person.age}, ${person.asset}, '${person.country}')
            """.trimIndent()
            it.executeUpdate(sql)
        }
    }

    override fun queryPerson(id: Int): Person? {
        var result: Person? = null
        c.createStatement().use {
            val sql = """
                SELECT *
                  FROM USER
                 WHERE ID = $id
            """.trimIndent()
            it.executeQuery(sql).use { res ->
                if (res.next()) {
                    result = Person(res.getInt("id"),
                        res.getString("name"),
                        res.getInt("age"),
                        res.getInt("asset"),
                        res.getString("country"))
                }
            }
        }
        return result
    }

    override fun updatePerson(person: Person) {
        c.createStatement().use {
            val sql = """
                UPDATE USER
                   SET NAME = '${person.name}',
                       AGE = ${person.age},
                       ASSET = ${person.asset},
                       COUNTRY = '${person.country}'
                 WHERE ID = ${person.id}
            """.trimIndent()
            it.executeUpdate(sql)
        }
    }

    override fun deletePerson(id: Int) {
        c.createStatement().use {
            val sql = """
                DELETE FROM USER
                 WHERE ID = $id
            """.trimIndent()
            it.executeUpdate(sql)
        }
    }

    private fun createUserTable() {
        var tableIsExist: Boolean
        c.createStatement().use { stmt ->
            val sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='USER'"
            stmt.executeQuery(sql).use { rs ->
                tableIsExist = rs.next()
            }
        }

        if (!tableIsExist) {
            println("User table doesn't exist. Create table...")
            c.createStatement().use {
                val sql = """
                    CREATE TABLE USER(
                        ID      INT PRIMARY KEY NOT NULL,
                        NAME    TEXT            NOT NULL,
                        AGE     INT             NOT NULL,
                        ASSET   INT,
                        COUNTRY TEXT
                    )
                """.trimIndent()
                it.executeUpdate(sql)
            }
            println("Create table success!!!")
        } else {
            println("User table exist!!!")
        }
    }

}