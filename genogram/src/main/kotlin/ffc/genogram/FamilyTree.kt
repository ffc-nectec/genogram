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
    private var bloodFamily: List<Int> = familyObj.bloodFamily
    private var focusedPerson: Person? = null
    var personLinkedStack: List<Int>? = listOf()

    fun drawGenogram(): FamilyTreeDrawer {

        focusedPerson = popBloodFamily()
        addNode(focusedPerson!!)

        if (focusedPerson == null)
            return familyTreePic
        else {
            val listFocusedPerson: ArrayList<Person> = ArrayList()
            listFocusedPerson.add(focusedPerson!!)
            personLinkedStack = focusedPerson!!.linkedStack!!

//            while (personLinkedStack.isNotEmpty())
            personLinkedStack = this.setRelationship(listFocusedPerson, null)
        }

        return familyTreePic
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
            val relationFactory = RelationshipFactory()
            relationFactory.getLine(focusedPerson!!, familyTreePic, relationLabel)
            addNode(relatedPerson)

//            print(relatedPerson.firstname)
        }

        return null
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
            familyObj.member.find { it.idCard.toInt() == relatedPersonId }?.let {
                relatedPerson = it
            }
            // remove idCard = 10 out of the linkedStack's list1
            val tmpPerson: MutableList<Int> = focusedPerson!!.linkedStack as MutableList<Int>
            tmpPerson.removeAt(0)
            if (tmpPerson.isEmpty())
                focusedPerson!!.linkedStack = null
            else
                focusedPerson!!.linkedStack = tmpPerson
            familyObj.member.forEachIndexed { index, person ->
                if (person.idCard == focusedPerson!!.idCard)
                    familyObj.member[index].linkedStack = focusedPerson!!.linkedStack
            }
        }

        return relatedPerson!!
    }

    private fun addNode(focusedPerson: Person) {
        // draw the node's focusPerson on the "familyTreePic"
        val nodeFactory = NodeFactory()
        nodeFactory.getNode(familyTreePic, focusedPerson)
    }

    fun popBloodFamily(): Person {
        // TODO: Add delete after pop
        return familyObj.member[bloodFamily[0]]
    }
}
