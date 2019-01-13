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
import ffc.genogram.Util.addDescendentOf

class FamilyTree(var family: Family) {

    private var familyTreePic: FamilyTreeDrawer = FamilyTreeDrawer()
    private var focusedPerson: Person? = null
    private var personLinkedStack: List<Int>? = null
    private val relationFactory = RelationshipFactory()
    private val nodeFactory = NodeFactory()
    private val addedNodes: MutableList<Int> = mutableListOf()
    private var keepBloodFamily: MutableList<Int>

    init {
        var rootPerson = family.members[0]
        keepBloodFamily = mutableListOf(rootPerson.idCard)
        keepBloodFamily.addDescendentOf(rootPerson, family)
    }

    fun drawGenogram(): FamilyTreeDrawer {
        focusedPerson = family.popBloodFamily()

        return when {
            focusedPerson == null -> {
                familyTreePic
            }

            isDrawn(focusedPerson) && (focusedPerson!!.linkedStack == null) && (family.bloodFamily == null) -> {
                familyTreePic
            }

            else -> {
                focusedPerson?.let {
                    if (!isDrawn(it)) drawNode(it, null, null)

                    val listFocusedPerson: ArrayList<Person> = arrayListOf(it)
                    // Get the relative person from the focusedPerson's linkedStack
                    personLinkedStack = it.linkedStack
                    while (personLinkedStack != null) {
                        personLinkedStack = setRelationship(
                            listFocusedPerson, null, null
                        )
                    }
                }
                drawGenogram()
            }
        }
    }

    private fun setRelationship(
        personList: ArrayList<Person>, // list of focusedPerson
        childrenList: ArrayList<Person>?,
        childrenIdList: ArrayList<Int>?
    ): List<Int>? {
        val focusedPerson = personList[0]

        if ((personList.size > 1) || ((childrenList != null) && (childrenIdList != null))) {
            // Draw a children or twins line
            return setChildrenRelationship(personList, childrenList!!)
        } else {
            // Draw a marriage / single parent / divorce line
            val relatedPerson = popLinkedStack(personList, null)

            relatedPerson?.let { spouse ->
                val relatedPersonList: ArrayList<Person> = arrayListOf(spouse)
                val relationLabel = findRelationship(personList, relatedPersonList)

                when (relationLabel) {
                    RelationshipLabel.CHILDREN -> {
                        // Draw a marriage (Single parent) / a relationship line and Unknown Node.
                        // Delete other children in the focusedPerson's linkedStack
                        focusedPerson.deleteChildrenFromLinkedStack()

                        val relationLabel =
                            if (focusedPerson.gender == GenderLabel.MALE)
                                RelationshipLabel.SINGLE_PARENT_MALE
                            else
                                RelationshipLabel.SINGLE_PARENT_FEMALE

                        setMarriageRelationship(
                            focusedPerson, spouse, relationLabel
                        )
                    }
                    else -> {
                        // Draw a marriage
                        // Draw a relationship line and Node.
                        setMarriageRelationship(
                            focusedPerson, spouse, relationLabel
                        )
                    }
                }
            }
        }
        return focusedPerson.linkedStack
    }

    private fun setChildrenRelationship(
        personList: ArrayList<Person>, // list of focusedPerson
        childrenList: ArrayList<Person>
    ): List<Int>? {
        val parent = findLeftHandParent(personList, family)
        // Draw a children line
        val line = relationFactory.getLine(
            childrenList,
            parent,
            keepBloodFamily,
            family,
            familyTreePic
        )
        line.drawLine()
        // Draw children nodes
        drawListNode(childrenList, personList, RelationshipLabel.CHILDREN)

        return personList[0].linkedStack // BloodParent's linkedStack
    }

    private fun setMarriageRelationship(
        focusedPerson: Person, // list of focusedPerson
        spouse: Person,
        relationLabel: RelationshipLabel
    ): List<Int>? {
        val line = relationFactory.getLine(
            focusedPerson,
            family,
            familyTreePic,
            relationLabel,
            familyTreePic.findPersonLayer(focusedPerson),
            keepBloodFamily
        )
        line.drawLine()

        var mSpouse = spouse
        if (relationLabel == RelationshipLabel.SINGLE_PARENT_MALE ||
            relationLabel == RelationshipLabel.SINGLE_PARENT_FEMALE
        ) {
            mSpouse = family.createUnknownMember(focusedPerson)
        }

        drawNode(mSpouse, focusedPerson, relationLabel)

        var childrenId: ArrayList<Int>?
        var focusedParents: ArrayList<Person> = arrayListOf(focusedPerson)

        childrenId = if (mSpouse == spouse) {
            focusedParents.add(mSpouse)
            val tmp: MutableList<Int> = mutableListOf(focusedPerson.idCard)
            spouse.removeLinkedStack(tmp)
            focusedPerson.getChildrenId(mSpouse)
        } else {
            focusedPerson.children as ArrayList<Int>?
        }

        childrenId?.let { idList ->
            val children = focusedPerson.popChildren(
                idList,
                spouse,
                family.members
            )
            return setRelationship(focusedParents, children, idList)
        }

        return null
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
                val relatedPersonId = relatedList[0].idCard

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
        val node = nodeFactory.getNode(
            familyTreePic, focusedPerson, relatedPerson, family, keepBloodFamily
        )

        /*if (relatedPerson!!.firstname == "Cara") {
            print("------ FF 240 ------\n")
            print("add: ${relatedPerson!!.firstname}\n")
            print("...............\n")
            val canvasB = displayObjectResult(familyTreePic)
            print(canvasB.toString())
            print("---------------------------------------\n")
        }*/


        node.drawNode(relationLabel, siblings = false)

        var addNode = true
        if (relationLabel == RelationshipLabel.SINGLE_PARENT_MALE ||
            relationLabel == RelationshipLabel.SINGLE_PARENT_FEMALE
        )
            addNode = false

        if (addNode)
            addedNodes.add(relatedPerson.idCard)

        // Check
        /*if (focusedPerson?.firstname == "F10") {
            print("------ Started ------\n")
            print("focusedPerson: ${focusedPerson?.firstname}\n")
            print("focusedPerson: ${focusedPerson?.linkedStack}\n")
            print("...............\n")
            val canvasB = displayObjectResult(familyTreePic)
            print(canvasB.toString())
            print("---------------------------------------\n")
        }*/

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
            val node = nodeFactory.getNode(familyTreePic, parent, it, family, keepBloodFamily)
            if (childrenNumber == 1)
                node.drawNode(relationLabel, siblings = false)
            else
                node.drawNode(relationLabel, siblings = true)
        }

        // Adjust the parent layer when the more than three children were added.
        // Add indent for the previous layers if the number of children is even number.
        // The number of empty nodes will be the number of children / FamilyTree.
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
