package ffc.genogram

import org.amshove.kluent.`should equal`
import org.junit.Test

/**
 * test data from 3rdGen/family-3-children-3rd-gen-6.json
 */
class PersonLinkedStackTest {

    @Test
    fun linkStack() {
        val person = Person(0, "Grandpa", "Smith",
                wife = listOf(10),
                children = listOf(1, 2, 3, 4)
        )

        person.linkedStack `should equal` listOf(10, 1, 2, 3, 4)
    }

    @Test
    fun linkStack2() {
        val person = Person(12, "Chris", "Smith",
                father = 0,
                mother = 10,
                wife = listOf(11),
                children = listOf(5, 6)
        )

        person.linkedStack `should equal` listOf(0, 10, 11, 5, 6)
    }

    @Test
    fun linkStack3() {
        val person = Person(3, "Kitty", "Smith", GenderLabel.FEMALE,
                father = 0,
                mother = 10,
                husband = listOf(12),
                children = listOf(6, 7)
        )

        person.linkedStack `should equal` listOf(0, 10, 12, 6, 7)
    }
}
