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

class FamilyTree(var family: Family) {

    private var familyTreePic: FamilyTreeDrawer = FamilyTreeDrawer()
    private var focusedPerson: Person? = null
    private var personLinkedStack: List<Int>? = null
    private val relationFactory = RelationshipFactory()
    private val nodeFactory = NodeFactory()
    private val addedNodes: MutableList<Int> = mutableListOf()

    fun drawGenogram(): FamilyTreeDrawer {
        focusedPerson = family.popBloodFamily()
//        print("1: familyObj: ${familyObj.bloodFamily}, focusedPerson: ${focusedPerson!!.firstname}\n")
//        print("1: linkedStack: ${focusedPerson!!.linkedStack}, focusedPerson: ${focusedPerson!!.firstname}\n")

        if (focusedPerson == null) {
            print("==== ${family.familyName} Family =====\n")
            return familyTreePic
        } else if (isAdded(focusedPerson) && focusedPerson!!.linkedStack == null) {
            print("==== ${family.familyName} Family | ${focusedPerson!!.firstname} =====\n")
            return familyTreePic
        } else {
            print("LOADING...\n")
            if (!isAdded(focusedPerson))
                addNode(focusedPerson!!, null, null)
            val listFocusedPerson: ArrayList<Person> = ArrayList()
            listFocusedPerson.add(focusedPerson!!)
            personLinkedStack = focusedPerson!!.linkedStack

            while (personLinkedStack != null) {
                personLinkedStack = this.setRelationship(listFocusedPerson, null, null)
            }

//            print("2: familyObj: ${familyObj.bloodFamily}, focusedPerson: ${focusedPerson!!.firstname}\n")
            return drawGenogram()
        }
    }

    private fun isAdded(focusedPerson: Person?): Boolean {
        if (focusedPerson != null) {
            addedNodes.find { it == focusedPerson.idCard.toInt() }?.let {
                return true
            } ?: kotlin.run {
                return false
            }
        } else {
            return false
        }
    }

    private fun setRelationship(
        list1: ArrayList<Person>,
        childrenObjList: ArrayList<Person>?,
        childrenIdList: ArrayList<Int>?
    ): List<Int>? {

        val relatedPerson: Person?

        if (list1.size > 1 && childrenObjList != null && childrenIdList != null) {
            // Draw a children or twins line
            val parentsPosition = familyTreePic.findParentsPosition()
            relationFactory.getLine(childrenObjList, parentsPosition, familyTreePic, RelationshipLabel.CHILDREN)
            addListNode(childrenObjList, RelationshipLabel.CHILDREN)
            return focusedPerson!!.linkedStack
//            return null
        } else {
            // Draw a marriage or divorce line
            relatedPerson = popLinkedStack(list1, null)
            val relatedPersonList: ArrayList<Person> = ArrayList()

            if (relatedPerson != null) {
                relatedPersonList.add(relatedPerson)
                val relationLabel = findRelationship(list1, relatedPersonList)
                print("Relationship: ${focusedPerson!!.firstname} + ${relatedPerson.firstname} = $relationLabel\n")
                // Draw a relationship line
                relationFactory.getLine(
                    focusedPerson!!,
                    familyTreePic,
                    relationLabel,
                    addLayer = familyTreePic.findPersonLayer(focusedPerson!!)
                )
                addNode(relatedPerson, focusedPerson, relationLabel)
                val tmp: MutableList<Int> = mutableListOf(focusedPerson!!.idCard.toInt())
                relatedPerson.removeLinkedStack(tmp)
                val childrenIdList = focusedPerson!!.haveChildren(relatedPerson)

                return if (childrenIdList != null) {
                    focusedPerson!!.removeListLinkedStack(childrenIdList)
                    relatedPerson.removeListLinkedStack(childrenIdList)
                    val childrenObjList = popChildren(childrenIdList, focusedPerson!!, relatedPerson)
                    val focusedParents: ArrayList<Person> = arrayListOf(focusedPerson!!, relatedPerson)
                    setRelationship(focusedParents, childrenObjList, childrenIdList)
                } else {
                    focusedPerson!!.linkedStack
                }
            }
        }
        return focusedPerson!!.linkedStack
    }

    private fun popChildren(childrenList: MutableList<Int>, person1: Person, person2: Person?): ArrayList<Person> {
        val childrenObjList: ArrayList<Person> = arrayListOf()
        for (i in 0 until family.members!!.size) {
            childrenList.find { it == family.members!![i].idCard.toInt() }?.let {
                val child = family.members!![i]
                val tmpChildStack: MutableList<Int> = child.linkedStack as MutableList<Int>
                tmpChildStack.remove(person1.idCard.toInt())
                if (person2 != null)
                    tmpChildStack.remove(person2.idCard.toInt())
                if (tmpChildStack.isEmpty())
                    child.linkedStack = null
                else
                    child.linkedStack = tmpChildStack
                childrenObjList.add(child)
            }
        }

        return childrenObjList
    }

    private fun findRelationship(focusedList: ArrayList<Person>, relatedList: ArrayList<Person>)
            : RelationshipLabel {
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

    private fun popLinkedStack(list1: ArrayList<Person>, list2: ArrayList<Person>?): Person? {

        var relatedPerson: Person? = null
        val relatedPersonId: Int?

        if (list2 == null) {
            relatedPerson = list1[0]
            if (relatedPerson.linkedStack!!.isNotEmpty()) {
                relatedPersonId = relatedPerson.linkedStack!![0]
                family.members!!.find { it.idCard.toInt() == relatedPersonId }?.let {
                    relatedPerson = it
                }

                val tmpPerson: MutableList<Int> = focusedPerson!!.linkedStack as MutableList<Int>
                tmpPerson.removeAt(0)
                if (tmpPerson.isEmpty())
                    focusedPerson!!.linkedStack = null
                else
                    focusedPerson!!.linkedStack = tmpPerson
                family.members!!.forEachIndexed { index, person ->
                    if (person.idCard == focusedPerson!!.idCard)
                        family.members!![index].linkedStack = focusedPerson!!.linkedStack
                }
            } else {
                relatedPerson = null
            }
        }

        return relatedPerson
    }

    private fun addNode(relatedPerson: Person, focusedPerson: Person?, relationLabel: RelationshipLabel?) {
        nodeFactory
            .getNode(familyTreePic, focusedPerson, relatedPerson, relationLabel)
            .drawNode(relationLabel, siblings = false)
        addedNodes.add(relatedPerson.idCard.toInt())
    }

    private fun addListNode(focusedList: MutableList<Person>, relationLabel: RelationshipLabel) {
        focusedList.forEach {
            val node = nodeFactory.getNode(familyTreePic, null, it, relationLabel)
            if (focusedList.size == 1)
                node.drawNode(relationLabel, siblings = false)
            else
                node.drawNode(relationLabel, siblings = true)
        }

        focusedList.forEach {
            addedNodes.add(it.idCard.toInt())
        }
    }

}
