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

import ffc.genogram.RelationshipLine.ChildrenLine
import ffc.genogram.RelationshipLine.Relationship
import ffc.genogram.RelationshipLine.RelationshipLabel
import ffc.genogram.Util.cleanUpEmptyStack

class Person(
    var idCard: Int,
    var firstname: String,
    var lastname: String,
    var gender: GenderLabel = GenderLabel.MALE,
    var father: Int? = null,
    var mother: Int? = null,
    var twin: List<Int>? = null,
    var exHusband: List<Int>? = null,
    var exWife: List<Int>? = null,
    var husband: List<Int>? = null,
    var wife: List<Int>? = null,
    var children: List<Int>? = null
) {
    var linkedStack: List<Int>? = null

    init {
        val stack = mutableListOf<Int>()
        father?.let { stack.add(it) }
        mother?.let { stack.add(it) }
        val links = listOf(twin, exHusband, exWife, husband, wife, children)
        links.forEach { link ->
            link?.let { stack.addAll(it) }
        }
        linkedStack = if (stack.isEmpty()) null else stack
    }

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
    fun getChildrenId(relatedPerson: Person?): ArrayList<Int>? {
        return if (children != null) {
            val childrenList: ArrayList<Int>? = arrayListOf()
            children!!.forEach { child ->
                relatedPerson?.let {
                    it.children!!.find { it == child }?.let {
                        childrenList!!.add(it)
                    }

                    removeLinkedStack(childrenList!!)
                    relatedPerson.removeLinkedStack(childrenList)
                }
            }

            childrenList
        } else
            null
    }

    fun findChildrenListIdInd(
        relatedPerson: Person?,
        parentLayer: Int,
        family: Family,
        familyTreeDrawer: FamilyTreeDrawer
    ): MutableList<Any>? {
        val childrenLayer = parentLayer + 3
        val idIndList = mutableListOf<Any>()
        val idList = mutableListOf<Int>()
        val indList = mutableListOf<Int>()
        val personList = mutableListOf<Person>()

        // Identify a father and mother
        var father: Person? = null
        var mother: Person? = null
        when {
            relatedPerson == null -> {
                when (gender) {
                    GenderLabel.MALE -> father = this
                    GenderLabel.FEMALE -> mother = this
                }
            }
            relatedPerson != null -> {
                when (gender) {
                    GenderLabel.MALE -> {
                        father = this
                        mother = relatedPerson
                    }
                    GenderLabel.FEMALE -> {
                        father = relatedPerson
                        mother = this
                    }
                }
            }
        }

        // Find children list
        when {
            father != null && mother == null -> {
                father.children?.forEach { childId ->
                    val child = family.findPerson(childId)!!
                    val childInd = familyTreeDrawer.findPersonInd(child, childrenLayer)
                    idList.add(childId)
                    indList.add(childInd)
                    personList.add(child)
                }
            }
            father != null && mother != null -> {
                father.children?.forEach { childId ->
                    val child = family.findPerson(childId)
                    child?.let {
                        val childInd = familyTreeDrawer.findPersonInd(child, childrenLayer)
                        if (child.mother == mother.idCard) {
                            idList.add(childId)
                            indList.add(childInd)
                            personList.add(child)
                        }
                    }
                }
            }
            father == null && mother != null -> {
                mother.children?.forEach { childId ->
                    val child = family.findPerson(childId)!!
                    val childInd = familyTreeDrawer.findPersonInd(child, childrenLayer)
                    idList.add(childId)
                    indList.add(childInd)
                    personList.add(child)
                }
            }
        }

        idIndList.add(idList)
        idIndList.add(indList)
        idIndList.add(personList)

        return idIndList
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
                it.idCard == relatedPersonId
            }?.let {
                person = it
            }

            val tmp: MutableList<Int> = linkedStack as MutableList<Int>
            tmp.removeAt(0)
            linkedStack = cleanUpEmptyStack(tmp)
        }

        return person
    }

    // Return children whose has the same parents and remove children's id out of the parents' link stack
    fun popChildren(childrenIdList: MutableList<Int>, person2: Person?, familyMembers: List<Person>?)
            : ArrayList<Person> {

        val childrenList: ArrayList<Person> = arrayListOf()

        familyMembers!!.forEach { child ->
            childrenIdList.find { it == child.idCard }?.let {
                val tmp = child.linkedStack as MutableList<Int>
                // remove the child father/mother
                tmp.remove(idCard)
                if (person2 != null)
                    tmp.remove(person2.idCard)

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

    fun findAnotherParent(focusingParent: Person, family: Family): Person? {
        if (father != null && father == focusingParent.idCard) {
            if (mother != null) {
                return family.findPerson(mother!!)
            }
        } else if (mother != null && mother == focusingParent.idCard) {
            if (father != null) {
                return family.findPerson(father!!)
            }
        }

        return null
    }

    fun findSiblingByParent(family: Family): MutableList<Person> {
        val siblingsList = mutableListOf<Person>()

        if (father != null || mother != null)
            family.members.forEach {
                if (it != this && father == it.father && mother == it.mother) {
                    siblingsList.add(it)
                }
            }

        return siblingsList
    }

    fun findSiblingByDrawer(familyTreeDrawer: FamilyTreeDrawer, childrenLineLayer: Int)
            : MutableList<MutableList<out Any>> {
        var childrenList: MutableList<Person>?
        var childrenIndList: MutableList<Int> = mutableListOf()
        val childrenLineStorage = familyTreeDrawer.getPersonLayer(childrenLineLayer)
        var childrenLine: ChildrenLine? = null
        childrenLineStorage.forEachIndexed { index, line ->
            if (line is ChildrenLine) {
                childrenList = line.childrenList
                childrenList!!.forEachIndexed { pos, list ->
                    if (list.idCard == idCard) {
                        childrenLine = childrenLineStorage[index] as ChildrenLine
                        childrenIndList.add(pos)
                    }
                }
            }
        }

        return mutableListOf(childrenLine!!.childrenList, childrenIndList)
    }

    fun isBloodFamily(bloodFamilyId: MutableList<Int>): Boolean {
        return bloodFamilyId.firstOrNull { idCard == it } != null
    }

    fun getFather(family: Family): Person? = if (father != null) family.findPerson(father!!) else null

    fun getMother(family: Family): Person? = if (mother != null) family.findPerson(mother!!) else null

    fun getBloodFParent(family: Family, bloodFamilyId: MutableList<Int>): Person {
        val father = getFather(family)
        val mother = getMother(family)

        return if (father != null) {
            if (father.isBloodFamily(bloodFamilyId)) father else mother!!
        } else {
            mother!!
        }
    }

    fun getLeftHandParent(familyTreeDrawer: FamilyTreeDrawer, childrenLineLayer: Int): Person {
        val childrenLine = familyTreeDrawer.findChildrenLine(
            childrenLineLayer, this
        )!!
        return childrenLine.parentList[0]
    }

    fun getTargetParent(family: Family, bloodFamilyId: MutableList<Int>): Person {
        val isFather = bloodFamilyId.find { id -> id == father }
        return if (isFather != null) family.findPerson(father!!)!! else family.findPerson(mother!!)!!

    }

}
