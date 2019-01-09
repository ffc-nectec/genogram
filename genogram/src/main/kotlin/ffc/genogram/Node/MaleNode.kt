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

class MaleNode(
    private var familyTreeDrawer: FamilyTreeDrawer,
    private val addedPerson: Person,
    private var focusedPerson: Person?,
    private var nodeName: String,
    var parent: Person?,
    val family: Family,
    val bloodFamilyId: MutableList<Int>
) : Node() {

    override fun drawNode(relationLabel: RelationshipLabel?, siblings: Boolean): FamilyTreeDrawer {

        if (relationLabel != RelationshipLabel.CHILDREN &&
            relationLabel != RelationshipLabel.TWIN
        ) {

            // Check
            /*if (addedPerson.firstname == "Teddy") {
                print("------ MaleNode 46 ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("---------------------------------------\n")
            }*/

            nodeName = createGenderBorder(nodeName, GenderLabel.MALE)
            if (focusedPerson != null) {
                val addingLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
                var addingInd = familyTreeDrawer.findPersonInd(focusedPerson!!, addingLayer)
                var isReplace = false
                var doExtend = false

                if (focusedPerson!!.gender == GenderLabel.FEMALE) {
                    // Find whether the focusedPerson has any siblings.
                    val leftHandNodes = familyTreeDrawer.hasNodeOnTheLeft(
                        focusedPerson!!, addingLayer
                    )
                    val rightHandNodes = familyTreeDrawer.hasNodeOnTheRight(
                        focusedPerson!!, addingLayer
                    )

                    // Find the focusedPerson sib
                    var isOldestSib = false
                    var isYoungestSib = false
                    var focusedSib = focusedPerson!!.findSiblingByDrawer(
                        familyTreeDrawer, addingLayer - 1
                    )
                    focusedSib?.let {
                        val focusedSibObj = focusedSib!![0] as MutableList<Person>
                        isOldestSib = focusedSibObj[0] == focusedPerson
                        isYoungestSib = focusedSibObj[focusedSibObj.size - 1] == focusedPerson
                    }

                    if (!leftHandNodes || isOldestSib) {
                        // FocusedPerson(the AddedPerson's wife) is the oldest daughter.
                        // Add the AddedPerson at the first index, the male node will be
                        // on the left of the female node, and make indent(s) at the
                        // FocusedPerson's parent layer.
                        if (isOldestSib && familyTreeDrawer.findPersonInd(focusedPerson!!, addingLayer) != 0) {
                            if (familyTreeDrawer.getPersonLayerInd(addingLayer, addingInd - 1) is EmptyNode) {
                                isReplace = true
                                familyTreeDrawer.replaceFamilyStorageLayer(
                                    addingLayer, addingInd - 1, nodeName, addedPerson
                                )
                            } else {
                                isReplace = false
                                familyTreeDrawer.addFamilyStorageReplaceIndex(
                                    addingLayer, addingInd - 1, nodeName, addedPerson
                                )
                            }
                        } else {
                            familyTreeDrawer.addFamilyStorageAtIndex(
                                addingLayer, addingInd, nodeName, addedPerson
                            )

                            for (i in 0 until addingLayer)
                                familyTreeDrawer.addFamilyStorageReplaceIndex(
                                    i, 0, null, null
                                )
                        }
                    } else if (!rightHandNodes) {
                        // FocusedPerson(the AddedPerson's wife) is the youngest daughter.
                        // (Special case) Add node AddedPerson node on the right of FocusedPerson.
                        familyTreeDrawer.addFamilyAtLayer(addingLayer, nodeName, addedPerson)
                    } else if (rightHandNodes && isYoungestSib) {
                        // If the addingInd is the empty node, it'll use replaceFamilyStorageLayer()
                        val isEmptyNode =
                            familyTreeDrawer.getPersonLayerInd(addingLayer, addingInd + 1) is EmptyNode

                        if (isEmptyNode) {
                            isReplace = true
                            familyTreeDrawer.replaceFamilyStorageLayer(
                                addingLayer, addingInd + 1, nodeName, addedPerson
                            )
                        } else {
                            isReplace = false
                            // The node will be added on the right-hand side
                            familyTreeDrawer.addFamilyStorageReplaceIndex(
                                addingLayer, addingInd, nodeName, addedPerson
                            )

                            val parentLayer = addingLayer - 3
                            val addedPersonInd = familyTreeDrawer.findPersonInd(addedPerson, addingLayer)
                            val addedPersonIndSize = familyTreeDrawer.findPersonIndSize(
                                addingLayer, 0, addedPersonInd - 1
                            )
                            val anotherParent = focusedPerson!!.findAnotherParent(parent!!, family)

                            // If it has the node on the right-hand side
                            // We'll move that right-hand node's parent
                            familyTreeDrawer.moveRightParentnLineLayer(
                                1, addedPersonIndSize, parent!!, anotherParent!!, parentLayer
                            )
                            // Children line above the parent layer
                            familyTreeDrawer.adjustUpperLayerPos(
                                focusedPerson!!, parent!!, parentLayer, family, bloodFamilyId
                            )
                        }
                    } else {
                        // FocusedPerson(the AddedPerson's wife) is the middle daughter.
                        // Add husband on the right hand of his wife.
                        familyTreeDrawer.addFamilyStorageReplaceIndex(
                            addingLayer, addingInd - 1, nodeName, addedPerson
                        )

                        // Extend the "MarriageLineManager" of AddedPerson and FocusedPerson.
                        // by adding the empty node(s).
                        // Check AddedPerson's husband index, then check
                        // whether her husband's index is equal to the number of empty node(s).
                        // Find number of marriage line
                        val marriageLineNumb = familyTreeDrawer.findStorageLayerSize(
                            addingLayer + 1
                        )
                        val ownInd = familyTreeDrawer.findPersonInd(addedPerson, addingLayer)
                        if (marriageLineNumb == 1) {
                            // His wife(FocusedPerson) siblings and AddedPerson
                            val emptyNodeNumber = familyTreeDrawer.findNumberOfEmptyNode(addingLayer)
                            val addingEmptyNodes = ownInd - emptyNodeNumber

                            if (ownInd != emptyNodeNumber) {
                                if (addingEmptyNodes > 0) {
                                    for (i in 0..(addingEmptyNodes - 1))
                                        familyTreeDrawer.addFamilyStorageReplaceIndex(
                                            addingLayer + 1,
                                            0, null, null
                                        )
                                }
                            }
                        }
                    }

                    // When the FocusedPerson is the oldest one.
                    // The AddedPerson will be added at the left-hand of the FocusedPerson.
                    // Then we don't change any sign of the line.
                    if (!isOldestSib) {
                        // Adjust the children line
                        val parentLayer = familyTreeDrawer.findPersonLayer(parent!!)
                        var childrenNumber = familyTreeDrawer.findPersonLayerSize(addingLayer)
                        var childrenLineLayer = addingLayer - 1

                        // Find children that focusedPerson and anotherParent has together
                        val focusedPersonChildren = focusedPerson!!.children as MutableList<Int>?
                        var anotherParent = focusedPerson!!.findAnotherParent(parent!!, family)
                        val addedPersonValues =
                            findPersonSibListIdInd(parent, anotherParent, focusedPersonChildren)
                        val addedPersonSibListId = addedPersonValues[0] as MutableList<Int>
                        val addedPersonParent = addedPersonValues[1] as MutableList<Person>

                        var childrenLine = ChildrenLine()
                        val previousChildrenLine = familyTreeDrawer.findChildrenLine(
                            childrenLineLayer, focusedPerson!!
                        )

                        val addedPersonSibList: MutableList<Person> = mutableListOf()
                        if (previousChildrenLine != null) {
                            childrenLine = previousChildrenLine
                        } else {
                            // Create a new children line
                            // Draw a new childrenLine with new parentList, new childrenList, and etc.
                            addedPersonSibListId.forEach {
                                val child = family.findPerson(it)
                                addedPersonSibList.add(child!!)
                            }
                            childrenLine.drawLine(addedPersonSibListId.size, addedPersonParent, addedPersonSibList)
                        }

                        // FocusedPerson's siblings
                        val childrenListId = parent!!.children!!
                        val childrenListInd: MutableList<Int> = mutableListOf()
                        childrenListId.forEach { id ->
                            childrenListInd.add(
                                familyTreeDrawer.findPersonIndById(
                                    id, childrenLineLayer + 1
                                )
                            )
                        }

                        // Extend the MarriageLineManager of AddedPerson's parent.
                        var childrenIndLength = childrenListInd[childrenListInd.size - 1] - childrenListInd[0] + 1
                        if (childrenIndLength > 3) {
                            movingParentPosition(
                                familyTreeDrawer,
                                addedPerson,
                                focusedPerson!!,
                                parent!!,
                                addingLayer,
                                parentLayer,
                                family,
                                bloodFamilyId
                            )

                            var startInd = childrenListInd[0]
                            var parentInd = familyTreeDrawer.findPersonInd(parent!!, parentLayer)

                            if (!rightHandNodes) {
                                // Extend the CHILDREN Line the top layer of the AddedPerson.
                                // When the AddedPerson is added on the left-hand of this wife (FocusedPerson).
                                // Check an empty node.
                                if ((startInd != 0) && (parentInd > startInd)) {
                                    // Moving the children line by adding an empty node.
                                    familyTreeDrawer.replaceFamilyStorageLayer(
                                        childrenLineLayer, 0, null, null
                                    )

                                    // Extend the children line.
                                    childrenNumber = familyTreeDrawer.findPersonLayerSize(addingLayer)
                                    if (addingInd != 0)
                                        childrenNumber -= 2
                                    else
                                        childrenNumber = childrenNumber - childrenListInd.size + 1

                                    // String Visualization
                                    val expectedLength = familyTreeDrawer.childrenLineLength(childrenNumber)
                                    val extendedLine = familyTreeDrawer.extendLine(
                                        expectedLength,
                                        childrenListInd,
                                        parentInd
                                    )

                                    childrenLine.extendLine(
                                        familyTreeDrawer,
                                        addingLayer - 1,
                                        childrenListInd,
                                        family,
                                        bloodFamilyId
                                    )

                                    familyTreeDrawer.replaceFamilyStorageLayer(
                                        childrenLineLayer, startInd, extendedLine, childrenLine
                                    )
                                }
                            } else {
                                // Youngest Child
                                // Middle Child
                                startInd = childrenListInd[0]
                                parentInd = familyTreeDrawer.findPersonInd(parent!!, parentLayer)

                                if ((startInd != 0) && (parentInd > startInd)) {
                                    startInd = parentInd - startInd

                                    // Moving the children line by adding an empty node.
                                    familyTreeDrawer.replaceFamilyStorageLayer(
                                        childrenLineLayer, 0, null, null
                                    )
                                    childrenNumber--
                                }

                                // Extend the children line.
                                // String Visualization
                                val midEmptyNode = familyTreeDrawer.findNumberOfMidEmptyNodePerson(addingLayer)
                                startInd -= midEmptyNode

                                // Update parentSib index
                                childrenLineLayer = addingLayer - 1
                                focusedSib = focusedPerson!!.findSiblingByDrawer(
                                    familyTreeDrawer, addingLayer - 1
                                )
                                childrenLine.extendLine(
                                    familyTreeDrawer,
                                    childrenLineLayer,
                                    focusedSib!![1] as MutableList<Int>, //parentSibInd
                                    family,
                                    bloodFamilyId
                                )
                            }
                        }
                    } else {
                        // When the addingPerson is added in the front of the focusedPerson.
                        // The layers above the focusedPerson will be changed the position.
                        val addingPersonInd = familyTreeDrawer.findPersonInd(
                            addedPerson, addingLayer
                        )
                        val leftHandNodes = familyTreeDrawer.hasNodeOnTheLeft(
                            addedPerson, addingLayer
                        )
                        if (leftHandNodes) {
                            // Extend only their parent layer
                            val addingPersonIndSize = familyTreeDrawer.findPersonIndSize(
                                addingLayer, 0, addingInd - 1
                            )
                            val parentLayer = addingLayer - 3
                            val anotherParent = focusedPerson!!.findAnotherParent(parent!!, family)!!
                            val previousObj = familyTreeDrawer.getPersonLayerInd(
                                addingLayer, addingPersonInd - 1
                            )

                            if (previousObj !is EmptyNode && !isReplace) {
                                familyTreeDrawer.moveRightParentnLineLayer(
                                    1, addingPersonIndSize, parent!!, anotherParent, parentLayer
                                )
                            }
                            // Adjust the parent position after adding the addingPerson
                            val bloodParent = focusedPerson?.getBloodFParent(family, bloodFamilyId)!!
                            val bloodParentLayer = familyTreeDrawer.findPersonLayer(bloodParent)
                            familyTreeDrawer.adjustUpperLayerPos(
                                focusedPerson!!, bloodParent, bloodParentLayer, family, bloodFamilyId
                            )
                        }
                    }
                }
            } else {
                familyTreeDrawer.addFamilyLayer(nodeName, addedPerson)
            }
        } else {
            // Check
            /*if (addedPerson.firstname == "Dan") {
                print("------ Male 1 ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("-------------\n")
            }*/

            // Children or Twin
            val parentLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
            val parentInd = familyTreeDrawer.findPersonInd(focusedPerson!!, parentLayer)

            // Separate AddedPerson and their cousins by adding empty node(s).
            separateMidChildren(familyTreeDrawer, parentLayer)

            // Check
            /*if (addedPerson.firstname == "James") {
                print("------ After separateMidChildren ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("-------------\n")
            }*/

            // Separate AddedPerson's parent from their uncles/aunts by adding empty node(s).
            separateParentSib(
                familyTreeDrawer,
                focusedPerson!!,
                addedPerson,
                parentLayer,
                parentInd,
                family,
                bloodFamilyId
            )

            // Check
            /*if (addedPerson.firstname == "James") {
                print("------ After separateParentSib ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("-------------\n")
            }*/

            // Add a single child
            addMiddleChild(
                focusedPerson!!,
                nodeName,
                GenderLabel.MALE,
                addedPerson,
                siblings,
                family,
                familyTreeDrawer,
                bloodFamilyId
            )

            /*// Check
            if (addedPerson.firstname == "Andrew") {
                print("------ After Add a single child ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("-------------\n")
            }*/
        }

        return familyTreeDrawer
    }

    override fun getArea(): Double = nodeSize * nodeSize
}
