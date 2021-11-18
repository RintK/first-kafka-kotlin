package repository

import dto.Person

interface UserRepository {
    fun createPerson(person: Person)
    fun queryPerson(id: Int): Person
    fun updatePerson(person: Person)
    fun deletePerson(id: Int)
}