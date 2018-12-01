/*
 * Copyright 2018 NECTEC
 *   National Electronics and Computer Technology Center, Thailand
 *
 * Licensed under the Apache License, Version FamilyTree2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ffc.genogram

import ffc.genogram.RelationshipLine.Relationship
import ffc.genogram.RelationshipLine.RelationshipLabel
import ffc.genogram.Util.cleanUpEmptyStack

class Person(
        var idCard: Int,
        var firstname: String,
        var lastname: String,
        var gender: GenderLabel = GenderLabel.MALE,
        var father: Int?,
        var mother: Int?,
        var twin: List<Int>?,
        var exHusband: List<Int>?,
        var exWife: List<Int>?,
        var husband: List<Int>?,
        var wife: List<Int>?,
        var children: List<Int>?,
        var linkedStack: List<Int>?
) {
    var properties: Any? = null
    var nodeMargin = 0

    // Whether the person has been divorced.
    fun hasBeenDivorced() = exHusband != null || exWife != null

    fun hasBeenMarriedWith(relatedPersonId: Int): RelationshipLabel {

        if (gender == GenderLabel.MALE) {
            exWife?.find { it == relatedPersonId }?.let {
                return RelationshipLabel.EX_WIFE
            }
            wife?.find { it == relatedPersonId }?.let {
                return RelationshipLabel.WIFE
            }
        } else {
            exHusband?.find { it == relatedPersonId }?.let {
                return RelationshipLabel.EX_HUSBAND
            }
            husband?.find { it == relatedPersonId }?.let {
                return RelationshipLabel.HUSBAND
            }
        }

        return RelationshipLabel.SINGLE
    }

    fun hasChildOrTwin(relatedPersonId: Int): RelationshipLabel {

        children?.find { it == relatedPersonId }?.let {
            return RelationshipLabel.CHILDREN
        }
        twin?.find { it == relatedPersonId }?.let {
            return RelationshipLabel.TWIN
        }

        return RelationshipLabel.NO_CHILD
    }

    // Whether the person has any child with the relatedPerson,
    // and remove the parents (person and the related person) out of children'stack
    fun haveChildren(relatedPerson: Person): ArrayList<Int>? {

        return if (children != null) {
            val childrenList: ArrayList<Int>? = arrayListOf()
            children!!.forEach { child ->
                relatedPerson.children!!.find { it == child }?.let {
                    childrenList!!.add(it)
                }
            }

            removeLinkedStack(childrenList!!)
            relatedPerson.removeLinkedStack(childrenList)
            childrenList
        } else
            null
    }

    // Remove the person(s) out of the stack
    fun removeLinkedStack(removeList: MutableList<Int>) {
        val tmp = linkedStack as MutableList<Int>?

        if (tmp != null) {
            removeList.forEach { element ->
                Int
                tmp.find { it == element }?.let {
                    tmp.remove(it)
                }
            }

            linkedStack = cleanUpEmptyStack(tmp)
        }
    }

    // Return a person and remove the person out of the stack
    fun popLinkedStack(familyMembers: List<Person>?): Person? {

        var person: Person? = null

        if (linkedStack != null) {
            val relatedPersonId = linkedStack!![0]
            familyMembers!!.find {
                it.idCard.toInt() == relatedPersonId
            }?.let {
                person = it
            }

            val tmp: MutableList<Int> = linkedStack as MutableList<Int>
            tmp.removeAt(0)
            linkedStack = cleanUpEmptyStack(tmp)
        }

        return person
    }

    // Return children whose has the same parents
    // and remove children's id out of the parents' link stack
    fun popChildren(childrenIdList: MutableList<Int>, person2: Person?, familyMembers: List<Person>?)
            : ArrayList<Person> {

        val childrenList: ArrayList<Person> = arrayListOf()

        familyMembers!!.forEach { child ->
            childrenIdList.find { it == child.idCard.toInt() }?.let {
                val tmp = child.linkedStack as MutableList<Int>
                // remove the child father/mother
                tmp.remove(idCard.toInt())
                if (person2 != null)
                    tmp.remove(person2.idCard.toInt())

                child.linkedStack = cleanUpEmptyStack(tmp)
                childrenList.add(child)
            }
        }

        return childrenList
    }

    fun setNodeMargin(siblings: Boolean) {
        if (!siblings)
            nodeMargin = ((Relationship.spaceLine + Relationship.distanceLine) / 2).toInt() - 1
    }
}
