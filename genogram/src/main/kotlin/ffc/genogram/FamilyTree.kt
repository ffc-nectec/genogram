/*
 * Copyright 2018 NECTEC
 *   National Electronics and Computer Technology Center, Thailand
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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

import ffc.genogram.RelationshipLine.RelationshipFactory
import ffc.genogram.RelationshipLine.RelationshipLabel
import ffc.genogram.node.NodeFactory

class FamilyTree(var familyObj: Family) {

    private var familyTreePic: FamilyTreeDrawer = FamilyTreeDrawer()
    private var focusedPerson: Person? = null
    private var personLinkedStack: List<Int>? = null
    private val relationFactory = RelationshipFactory()
    private val nodeFactory = NodeFactory()

    fun drawGenogram(): FamilyTreeDrawer {
        focusedPerson = popBloodFamily()

        if (focusedPerson == null) {
            print("==== ${familyObj.familyName} Family =====\n")
            return familyTreePic
        } else {
            print("LOADING...\n")
            addNode(focusedPerson!!, null)
            val listFocusedPerson: ArrayList<Person> = ArrayList()
            listFocusedPerson.add(focusedPerson!!)
            personLinkedStack = focusedPerson!!.linkedStack

            while (personLinkedStack != null) {
                personLinkedStack = this.setRelationship(listFocusedPerson, null)
            }

            familyObj.removeBloodFamily(focusedPerson!!.idCard)
//            return drawGenogram()
            return familyTreePic
        }
    }

    private fun setRelationship(list1: ArrayList<Person>, list2: ArrayList<Person>?): List<Int>? {

        lateinit var relatedPerson: Person

        if (list1.size > 1) {
            // Draw a children or twins line
        } else {
            // Draw a marriage or divorce line
            relatedPerson = popLinkedStack(list1, null)
            val relatedPersonList: ArrayList<Person> = ArrayList()
            relatedPersonList.add(relatedPerson)
            val relationLabel = findRelationship(list1, relatedPersonList)
            // Draw a relationship line
            relationFactory.getLine(focusedPerson!!, familyTreePic, relationLabel)
            addNode(relatedPerson, relationLabel)
            val tmp: MutableList<Int> = mutableListOf(focusedPerson!!.idCard.toInt())
            relatedPerson.removeLinkedStack(tmp)

            // Check whether they have children together
            val childrenList = popChildren(focusedPerson!!, relatedPerson)
            if (childrenList != null) {
                // TODO: Children Line
                val childrenObjList = findChildrenObj(childrenList)
                val parentsPosition = findParentsPosition()
                relationFactory.getLine(childrenObjList, parentsPosition, familyTreePic, RelationshipLabel.CHILDREN)
                focusedPerson!!.removeListLinkedStack(childrenList)
                relatedPerson.removeListLinkedStack(childrenList)
                // Add Children Node
                addListNode(childrenObjList, RelationshipLabel.CHILDREN)
            } else {
//                print("out: ${focusedPerson!!.linkedStack}")
//                return focusedPerson!!.linkedStack
            }
        }
        // temp
        return null
    }

    private fun findParentsPosition(): MutableList<Double> {
        val familyStorage = familyTreePic.familyStorage
        val latestLayer = familyStorage[familyStorage.size - 2]
        val parent1Inx = (latestLayer.size - 1).toDouble()
        val parent2Inx = (latestLayer.size - 2).toDouble()

        return mutableListOf(parent2Inx, parent1Inx)
    }

    private fun findChildrenObj(childrenList: MutableList<Int>): MutableList<Person> {
        val childrenObjList: MutableList<Person> = mutableListOf()
        for (i in 0 until familyObj.member!!.size) {
            childrenList.find { it == familyObj.member!![i].idCard.toInt() }?.let {
                childrenObjList.add(familyObj.member!![i])
            }
        }
        return childrenObjList
    }

    private fun popChildren(focusedPerson: Person, relatedPerson: Person): MutableList<Int>? {
        var childrenList: MutableList<Int>? = mutableListOf()
        // find the focusedPerson's children
        // find the focusedPerson's children who also is the relatedPerson's children
        val fChildren: List<Int>? = focusedPerson.children
        val rChildren: List<Int>? = relatedPerson.children
        if (fChildren != null) {
            for (i in 0 until fChildren.size) {
                rChildren!!.find { it == fChildren[i] }?.let {
                    childrenList!!.add(it)
                }
            }
        } else {
            childrenList = null
        }
        // delete children out of focusedPerson and relatedPerson's linkedStack
        return childrenList
    }

    private fun updatePersonLinkStack(person: Person, removeList: MutableList<Int>): Person {
        var tmp: MutableList<Int>? = null
        if (person.linkedStack != null)
            tmp = person.linkedStack as MutableList<Int>
        for (i in 0 until person.linkedStack!!.size) {
            removeList.find { it == tmp!![i] }?.let {
                tmp!!.removeAt(i)
            }
        }
        person.linkedStack = tmp

        return person
    }

    private fun findRelationship(focusedList: ArrayList<Person>, relatedList: ArrayList<Person>): RelationshipLabel {
        when (focusedList.size) {
            1 -> {
                // most cases the relatedList.size == 1 in this case
                if (relatedList.size == 1) {
                    val focusedPerson = focusedList[0]
                    val relatedPersonId = relatedList[0].idCard.toInt()

                    if (relatedList[0].gender == 1) {
                        // Can be "ex-wife, or wife" of focusedPerson
                        focusedPerson.exWife?.find { it == relatedPersonId }?.let {
                            return RelationshipLabel.EX_WIFE
                        }
                        focusedPerson.wife?.find { it == relatedPersonId }?.let {
                            return RelationshipLabel.WIFE
                        }
                    } else {
                        // Can be "ex-husband, or husband" of focusedPerson
                        focusedPerson.exHusband?.find { it == relatedPersonId }?.let {
                            return RelationshipLabel.EX_HUSBAND
                        }
                        focusedPerson.husband?.find { it == relatedPersonId }?.let {
                            return RelationshipLabel.HUSBAND
                        }
                    }
                    // Whether the relatedPerson is male or female, he/she can be children or twin of focusedPerson
                    focusedPerson.children?.find { it == relatedPersonId }?.let {
                        return RelationshipLabel.CHILDREN
                    }
                    focusedPerson.twin?.find { it == relatedPersonId }?.let {
                        return RelationshipLabel.TWIN
                    }
                } else {
                    // TODO: relatedList > 1
                }
            }
            else -> {
                // TODO: focusedList > 1
            }
        }
        return RelationshipLabel.SINGLE
    }

    private fun popLinkedStack(list1: ArrayList<Person>, list2: ArrayList<Person>?): Person {

        var relatedPerson: Person? = null

        if (list2 == null) {
            // list1 always has one element.
            relatedPerson = list1[0]

            // list1 = [grandfather]
            // list1.linkedStack = [10]
            val relatedPersonId = list1[0].linkedStack!![0]
            // relatedPerson = find person who has idCard = 10 = Grandmother
            familyObj.member!!.find { it.idCard.toInt() == relatedPersonId }?.let {
                relatedPerson = it
            }
            // remove idCard = 10 out of the linkedStack's list1
            val tmpPerson: MutableList<Int> = focusedPerson!!.linkedStack as MutableList<Int>
            tmpPerson.removeAt(0)
            if (tmpPerson.isEmpty())
                focusedPerson!!.linkedStack = null
            else
                focusedPerson!!.linkedStack = tmpPerson
            familyObj.member!!.forEachIndexed { index, person ->
                if (person.idCard == focusedPerson!!.idCard)
                    familyObj.member!![index].linkedStack = focusedPerson!!.linkedStack
            }
        }

        return relatedPerson!!
    }

    private fun addNode(focusedPerson: Person, relationLabel: RelationshipLabel?) {
        nodeFactory.getNode(familyTreePic, focusedPerson, relationLabel)
    }

    private fun addListNode(focusedList: MutableList<Person>, relationLabel: RelationshipLabel) {
        nodeFactory.getNode(familyTreePic, focusedList, relationLabel)
    }

    fun popBloodFamily(): Person? {
        // TODO: Add delete after pop
        val bloodFamily = familyObj.bloodFamily
        if (bloodFamily != null) {
            return familyObj.member!![bloodFamily[0]]
        }
        return null
    }
}
