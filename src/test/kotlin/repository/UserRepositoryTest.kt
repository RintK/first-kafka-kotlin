package repository

import dto.Person
import org.junit.jupiter.api.*

import repository.sqlite.UserRepositoryImpl

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class UserRepositoryTest {
    lateinit var repo: UserRepository

    private val testPerson1 = Person(1, "Rudy", 17, 12000, "kr")
    private val testPerson2 = Person(1, "Rudy", 20, 12000, "kr")

    @BeforeAll
    fun initTest() {
        repo = UserRepositoryImpl()
    }

    @Test
    @Order(1)
    fun createUserTest() {
        repo.createPerson(testPerson1)
    }

    @Test
    @Order(2)
    fun queryUserTest() {
        val person = repo.queryPerson(1)
        assert(testPerson1 == person)
    }

    @Test
    @Order(3)
    fun updateUserTest() {
        repo.updatePerson(testPerson2)
        val person = repo.queryPerson(1)
        assert(testPerson2 == person)
    }

    @Test
    @Order(4)
    fun deleteUserTest() {
        repo.deletePerson(1)
    }
}