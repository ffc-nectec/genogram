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

package ffc.genogram.Node

import ffc.genogram.Family
import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.GenderLabel
import ffc.genogram.Person
import ffc.genogram.RelationshipLine.ChildrenLine
import ffc.genogram.RelationshipLine.RelationshipLabel
import kotlin.math.PI

class FemaleNode(
    var familyTreeDrawer: FamilyTreeDrawer,
    private val addedPerson: Person,
    var focusedPerson: Person?,
    var nodeName: String,
    var parent: Person?,
    val family: Family
) : Node() {

    override fun drawNode(relationLabel: RelationshipLabel?, siblings: Boolean): FamilyTreeDrawer {

        if (relationLabel != RelationshipLabel.CHILDREN &&
            relationLabel != RelationshipLabel.TWIN
        ) {
            val addingLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
            nodeName = createGenderBorder(nodeName, GenderLabel.FEMALE)

            if (focusedPerson != null) {
                // Find layer of AddedPerson's siblings
                val childrenLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
                val childrenLineLayer = childrenLayer - 1

                // Find whether the focusedPerson has any siblings.
                val hasRightHandSib = familyTreeDrawer.hasPeopleOnTheRight(
                    focusedPerson!!,
                    addingLayer
                )

                if (hasRightHandSib) {
                    // FocusedPerson(addedPerson's husband) has older siblings
                    // Reorder the array's position
                    val addingInd = familyTreeDrawer.findPersonInd(
                        focusedPerson!!, addingLayer
                    )
                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                        addingLayer, addingInd, nodeName, addedPerson
                    )

                    if (parent != null) {
                        // AddedPerson has no parents, focus only on draw "MarriageLineManager"
                        // Find AddedPerson's parent index
                        val parentLayer = familyTreeDrawer.findPersonLayer(parent!!)
                        val parentInd = familyTreeDrawer.findPersonIndById(focusedPerson!!.father!!, parentLayer)
                        // Find index of AddedPerson's siblings
                        var childrenNumber = familyTreeDrawer.findPersonLayerSize(childrenLayer)
                        val childrenListId = parent!!.children!!
                        var childrenListInd: MutableList<Int> = mutableListOf()
                        childrenListId.forEach { id ->
                            childrenListInd.add(
                                familyTreeDrawer.findPersonIndById(
                                    id.toLong(), childrenLayer
                                )
                            )
                        }
                        var cInd = childrenListInd.distinct() as MutableList<Int>
                        childrenListInd = cInd

                        // Extend the MarriageLineManager of AddedPerson's parent.
                        if (childrenNumber > 3) {
                            // Extend the MarriageLineManager by adding the empty node(s).
                            movingParentPosition(
                                familyTreeDrawer,
                                addedPerson, focusedPerson!!, parent!!, addingLayer, parentLayer, family
                            )
                        }

                        // TODO: Refactor with "Node.kt"
                        // The same "Node.kt"
                        var startInd = childrenListInd[0]
                        if (startInd != 0) {
                            childrenNumber -= 1
                        } else {
                            startInd = 0
                        }

                        // Find the focusedPerson's parent
                        var fpParentListInd: MutableList<Int> = mutableListOf()
                        val fpFatherId = focusedPerson?.father
                        val fpMotherId = focusedPerson?.mother
                        var anotherParent: Person? = null

                        if (fpFatherId != null) {
                            val fpFatherInd = familyTreeDrawer.findPersonIndById(fpFatherId, parentLayer)
                            fpParentListInd.add(fpFatherInd)
                        }
                        if (fpMotherId != null) {
                            val fpMotherInd = familyTreeDrawer.findPersonIndById(fpMotherId, parentLayer)
                            fpParentListInd.add(fpMotherInd)
                        }

                        if (parent != null) {
                            if (fpFatherId == parent!!.idCard) {
                                if (fpMotherId != null)
                                    anotherParent = familyTreeDrawer.getPersonById(fpMotherId, parentLayer)
                            } else if (fpMotherId == parent!!.idCard) {
                                if (fpFatherId != null)
                                    anotherParent = familyTreeDrawer.getPersonById(fpFatherId, parentLayer)
                            }
                        }

                        var focusedPersonParent: MutableList<Person> = mutableListOf()
                        focusedPersonParent.add(parent!!)
                        if (anotherParent != null) {
                            focusedPersonParent.add(anotherParent)
                        }

                        // Find children that focusedPerson and anotherParent has together
                        val focusedPersonChildren = focusedPerson!!.children as MutableList<Int>?
                        var addedPersonValues =
                            findPersonSibListIdInd(parent, anotherParent, focusedPersonChildren)
                        var addedPersonSibListId: MutableList<Int> = addedPersonValues[0] as MutableList<Int>
                        var addedPersonParent: MutableList<Person> = addedPersonValues[1] as MutableList<Person>

                        // Object Visualization
                        // find the children line that has "addedPerson"'s husband
                        var childrenLine = ChildrenLine()
                        var previousChildrenLine = familyTreeDrawer.findChildrenLine(
                            childrenLineLayer, focusedPerson!!
                        )

                        if (previousChildrenLine != null) {
                            childrenLine = previousChildrenLine
                        } else if (previousChildrenLine == null) {
                            // Create a new children line
                            // Draw a new childrenLine with new parentList, new childrenList, and etc.
                            val addedPersonSibList: MutableList<Person> = mutableListOf()

                            addedPersonSibListId.forEach {
                                val child = family.findPerson(it.toLong())
                                addedPersonSibList.add(child!!)
                            }
                            childrenLine.drawLine(addedPersonSibListId.size, addedPersonParent, addedPersonSibList)
                        }

                        childrenLine.extendLine(
                            familyTreeDrawer,
                            addingLayer - 1,
                            childrenListInd
                        )

                        /*familyTreeDrawer.replaceFamilyStorageLayer(
                            childrenLineLayer, startInd, null, childrenLine
                        )*/
                    }
                } else if (!hasRightHandSib) {
                    // When AddedPerson's husband is the youngest children.
                    // Add AddedPerson at the end of the layer.
                    // No reorder the array's position, only extend the line.
                    familyTreeDrawer.addFamilyAtLayer(addingLayer, nodeName, addedPerson)

                    // Extend "MarriageLineManager" of the FocusedPerson's parents.
                    // Extend by adding the empty node(s).
                    val childrenNumber = familyTreeDrawer.findPersonLayerSize(childrenLayer)
                    if (parent != null) {
                        val parentLayer = familyTreeDrawer.findPersonLayer(parent!!)
                        var emptyParentNodeNumber = familyTreeDrawer.findNumberOfEmptyNode(parentLayer)
                        val emptyNodeNumb = familyTreeDrawer.findNumberOfEmptyNodePerson(addingLayer)
                        val emptyMidNodeNumb = familyTreeDrawer.findNumberOfMidEmptyNodePerson(addingLayer)
                        val emptyFrontNodeNumb = emptyNodeNumb - emptyMidNodeNumb
                        val addingEmptyNodes = findAddingEmptyNodesParent(childrenNumber)

                        if (addingEmptyNodes != emptyFrontNodeNumb)
                            familyTreeDrawer = addMoreNodes(
                                emptyParentNodeNumber, addingEmptyNodes, parentLayer, familyTreeDrawer
                            )

                        // Find index of AddedPerson's siblings
                        val childrenListId = parent!!.children!!
                        val childrenListInd: MutableList<Int> = mutableListOf()
                        childrenListId.forEach { id ->
                            childrenListInd.add(
                                familyTreeDrawer.findPersonIndById(
                                    id.toLong(), childrenLayer
                                )
                            )
                        }

                        // Extend the CHILDREN Line the top layer of the AddedPerson.
                        // When the AddedPerson is added on the left-hand of this wife (FocusedPerson).
                        var startInd = childrenListInd[0]
                        val parentInd = familyTreeDrawer.findPersonInd(parent!!, parentLayer)
                        startInd = if ((startInd != 0) && (parentInd > startInd)) {
                            parentInd - startInd
                        } else {
                            parentLayer
                        }

                        // Move children sign
                        // String Visualization
                        val midEmptyNode = familyTreeDrawer.findNumberOfMidEmptyNodePerson(addingLayer)
                        val childrenLineMidEmptyNode =
                            familyTreeDrawer.findNumberOfMidEmptyNodePerson(childrenLineLayer)
                        if (childrenLineMidEmptyNode > 0)
                            startInd += midEmptyNode
                        else
                            startInd -= midEmptyNode

                        if (startInd < 0)
                            startInd = 0

                        val extraNode = familyTreeDrawer.findNumberOfEmptyNode(addingLayer)
                        val editedLine = familyTreeDrawer.moveChildrenLineSign(
                            childrenLineLayer, addingEmptyNodes, childrenListInd, extraNode
                        )

                        // Object Visualization
                        var line: Any? = getLineType(
                            familyTreeDrawer,
                            childrenLineLayer,
                            addingEmptyNodes,
                            extraNode,
                            childrenListId,
                            focusedPerson!!
                        )

                        familyTreeDrawer.replaceFamilyStorageLayer(
                            childrenLineLayer, startInd, editedLine, line
                        )
                    }
                }
            } else {
                familyTreeDrawer.addFamilyLayer(nodeName, addedPerson)
            }
        } else {
            // Check
            /*if (addedPerson.firstname == "Lisa") {
                print("------ Started ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("---------------------------------------\n")
            }*/

            // Children or Twin
            val parentLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
            val parentInd = familyTreeDrawer.findPersonInd(focusedPerson!!, parentLayer)

            // Separate AddedPerson and their cousins by adding empty node(s).
            separateMidChildren(familyTreeDrawer, addedPerson, focusedPerson!!, parentLayer, parentInd)

            // Check
            /*if (addedPerson.firstname == "Lisa") {
                print("------ After SeparateMidChildren ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("---------------------------------------\n")
            }*/

            // Separate AddedPerson's parent from their uncles/aunts by adding empty node(s).
            separateParentSib(familyTreeDrawer, focusedPerson!!, addedPerson, parentLayer, parentInd, family)

            // Check
            /*if (addedPerson.firstname == "Lisa") {
                print("------ After SeparateParentSib ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("---------------------------------------\n")
            }*/

            // Add a single child
            addMiddleChild(
                focusedPerson!!,
                nodeName,
                GenderLabel.FEMALE,
                addedPerson,
                siblings,
                family,
                familyTreeDrawer
            )

            // Check
            /*if (addedPerson.firstname == "Lisa") {
                print("------ After AddMiddleChild ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("---------------------------------------\n")
            }*/
        }

        return familyTreeDrawer
    }

    override fun getArea(): Double = PI * Math.pow(nodeSize, 2.0)
}
