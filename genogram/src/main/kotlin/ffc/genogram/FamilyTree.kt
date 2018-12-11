/*
 * Copyright 2018 NECTEC
 *   National Electronics and Computer Technology Center, Thailand
 *
 * Licensed under the Apache License, Version FamilyTree.0 (the "License");
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

import ffc.genogram.Node.NodeFactory
import ffc.genogram.RelationshipLine.RelationshipFactory
import ffc.genogram.RelationshipLine.RelationshipLabel

class FamilyTree(var family: Family) {

    private var familyTreePic: FamilyTreeDrawer = FamilyTreeDrawer()
    private var focusedPerson: Person? = null
    private var personLinkedStack: List<Int>? = null
    private val relationFactory = RelationshipFactory()
    private val nodeFactory = NodeFactory()
    private val addedNodes: MutableList<Int> = mutableListOf()
    var keepBloodFamily: MutableList<Int>

    init {
        var rootPerson = family.members[0]
        keepBloodFamily = mutableListOf(rootPerson.idCard)
        rootPerson.children?.let { keepBloodFamily?.addAll(rootPerson.children!!)}
    }

    fun drawGenogram(): FamilyTreeDrawer {

        focusedPerson = family.popBloodFamily()

        return if (focusedPerson == null) {
            familyTreePic
        } else if (isDrawn(focusedPerson) && (focusedPerson!!.linkedStack == null) &&
            (family.bloodFamily == null)
        ) {
            familyTreePic
        } else {
            print("LOADING...\n")
            if (!isDrawn(focusedPerson))
                drawNode(focusedPerson!!, null, null)
            val listFocusedPerson: ArrayList<Person> = ArrayList()
            listFocusedPerson.add(focusedPerson!!)
            personLinkedStack = focusedPerson!!.linkedStack

            while (personLinkedStack != null) {
                personLinkedStack = setRelationship(listFocusedPerson, null, null)
            }
            drawGenogram()
        }
    }

    private fun setRelationship(
        list1: ArrayList<Person>,
        childrenList: ArrayList<Person>?,
        childrenIdList: ArrayList<Int>?
    ): List<Int>? {

        if ((list1.size > 1) &&
            (childrenList != null) &&
            (childrenIdList != null)
        ) {
            // Draw a children or twins line
            val parent = findLeftHandParent(list1, family)
            val line = relationFactory.getLine(
                childrenList,
                parent,
                keepBloodFamily,
                family,
                familyTreePic
            )
            line.drawLine()
            drawListNode(childrenList, list1, RelationshipLabel.CHILDREN)

            return focusedPerson!!.linkedStack
        } else {
            // Draw a marriage or divorce line
            val relatedPerson = popLinkedStack(list1, null)

            if (relatedPerson != null) {
                val relatedPersonList: ArrayList<Person> = ArrayList()
                relatedPersonList.add(relatedPerson)
                val relationLabel = findRelationship(list1, relatedPersonList)

                // Draw a relationship line and Node.
                val line = relationFactory.getLine(
                    focusedPerson!!,
                    family,
                    familyTreePic,
                    relationLabel,
                    addLayer = familyTreePic.findPersonLayer(focusedPerson!!)
                )
                line.drawLine()
                drawNode(relatedPerson, focusedPerson, relationLabel)

                val tmp: MutableList<Int> = mutableListOf(focusedPerson!!.idCard.toInt())
                relatedPerson.removeLinkedStack(tmp)
                val childrenId = focusedPerson!!.haveChildren(relatedPerson)

                if (childrenId != null) {
                    val children = focusedPerson!!.popChildren(
                        childrenId,
                        relatedPerson,
                        family.members
                    )
                    val focusedParents: ArrayList<Person> =
                        arrayListOf(focusedPerson!!, relatedPerson)

                    return setRelationship(focusedParents, children, childrenId)
                }
            }

            return focusedPerson!!.linkedStack
        }
    }

    private fun isDrawn(focusedPerson: Person?): Boolean {
        if (focusedPerson != null)
            addedNodes.find { it == focusedPerson.idCard }?.let {
                return true
            } ?: kotlin.run {
                return false
            }
        else
            return false
    }

    private fun findRelationship(focusedList: ArrayList<Person>, relatedList: ArrayList<Person>)
            : RelationshipLabel {

        if (focusedList.size == 1) {
            if (relatedList.size == 1) {
                val focusedPerson = focusedList[0]
                val relatedPersonId = relatedList[0].idCard.toInt()

                val relationshipType = focusedPerson.hasBeenMarriedWith(relatedPersonId)
                return if (relationshipType != RelationshipLabel.SINGLE) {
                    relationshipType
                } else {
                    focusedPerson.hasChildOrTwin(relatedPersonId)
                }
            } else {
                // TODO: relatedList > 1
            }
        } else {
            // TODO: focusedList > 1
        }

        return RelationshipLabel.SINGLE
    }

    private fun popLinkedStack(list1: ArrayList<Person>, list2: ArrayList<Person>?): Person? {

        return if (list2 == null) {
            // list1 = focused person
            list1[0].popLinkedStack(family.members)
        } else {
            // TODO: popLinkedStack parents and children list
            // - list1 is a list of parents
            // - list2 is a list of list1's children
            null
        }
    }

    private fun drawNode(relatedPerson: Person, focusedPerson: Person?, relationLabel: RelationshipLabel?) {
        val node = nodeFactory
            .getNode(familyTreePic, focusedPerson, relatedPerson, family)
        node.drawNode(relationLabel, siblings = false)
        addedNodes.add(relatedPerson.idCard)
    }

    private fun drawListNode(
        focusedList: MutableList<Person>,
        parents: ArrayList<Person>,
        relationLabel: RelationshipLabel
    ) {
        val childrenNumber = focusedList.size
        focusedList.forEach {
            // Find the left-hand parent
            val parent = findLeftHandParent(parents, family)
            val node = nodeFactory.getNode(familyTreePic, parent, it, family)

            if (childrenNumber == 1)
                node.drawNode(relationLabel, siblings = false)
            else
                node.drawNode(relationLabel, siblings = true)
        }

        // Adjust the parent layer when the more than three children were added.
        // Add indent for the previous layers if the number of children is even number.
        // The number of empty nodes will be the number of children / FamilyTree.
        familyTreePic.findPersonLayer(focusedPerson!!)
        focusedList.forEach {
            addedNodes.add(it.idCard)
        }
    }

    private fun findLeftHandParent(parents: ArrayList<Person>, family: Family): Person {
        var parent = parents[0]
        family.bloodFamily?.forEach { bloodId ->
            parents.forEach { person ->
                if (bloodId == person.idCard) {
                    parent = person
                }
            }
        }

        if (parents.size > 1) {
            if (parent.father != null) {
                var grandParent = family.findPerson(parent.father!!)
                if (grandParent == null)
                    grandParent = family.findPerson(parent.mother!!)

                var parentSib = listOf<Int>()
                if (grandParent != null) {
                    parentSib = grandParent.children!!
                }

                if ((parent.gender == GenderLabel.FEMALE) &&
                    (parent.idCard != parentSib[parentSib.size - 1])
                ) {
                    val husbandList = parent.husband
                    husbandList!!.forEach {
                        if (it == parents[1].idCard)
                            parent = family.findPerson(it)!!
                    }
                }
            }
        }

        return parent
    }
}
