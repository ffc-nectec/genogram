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
    val family: Family
) : Node() {

    override fun drawNode(relationLabel: RelationshipLabel?, siblings: Boolean): FamilyTreeDrawer {

        if (relationLabel != RelationshipLabel.CHILDREN &&
            relationLabel != RelationshipLabel.TWIN
        ) {
            nodeName = createGenderBorder(nodeName, GenderLabel.MALE)

            if (focusedPerson != null) {
                val addingLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
                val addingInd = familyTreeDrawer.findPersonInd(focusedPerson!!, addingLayer)

                if (focusedPerson!!.gender == GenderLabel.FEMALE) {
                    // Find whether the focusedPerson has any siblings.
                    val leftHandSiblings = familyTreeDrawer.hasPeopleOnTheLeft(
                        focusedPerson!!, addingLayer
                    )
                    val rightHandSiblings = familyTreeDrawer.hasPeopleOnTheRight(
                        focusedPerson!!, addingLayer
                    )

                    if (!leftHandSiblings) {
                        // FocusedPerson(the AddedPerson's wife) is the oldest daughter.
                        // Add the AddedPerson at the first index, the male node will be
                        // on the left of the female node, and make indent(s) at the
                        // FocusedPerson's parent layer.
                        familyTreeDrawer.addFamilyStorageAtIndex(
                            addingLayer, addingInd, nodeName, addedPerson
                        )
                        for (i in 0 until addingLayer)
                            familyTreeDrawer.addFamilyStorageReplaceIndex(
                                i, 0, null, null
                            )
                    } else if (!rightHandSiblings) {
                        // FocusedPerson(the AddedPerson's wife) is the youngest daughter.
                        // (Special case) Add node AddedPerson node on the right of FocusedPerson.
                        familyTreeDrawer.addFamilyAtLayer(addingLayer, nodeName, addedPerson)
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
                        val ownInd = familyTreeDrawer.findPersonInd(addedPerson!!, addingLayer)
                        if (marriageLineNumb == 1) {
                            // His wife(FocusedPerson) siblings and AddedPerson
                            var emptyNodeNumber = familyTreeDrawer.findNumberOfEmptyNode(addingLayer)
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
                        } else {
                            // Add an empty node between the marriage line
//                            familyTreeDrawer.addEmptyNodeMarriageLine(
//                                ownInd, addingLayer + 1
//                            )
                        }
                    }

                    // When the FocusedPerson is the oldest one.
                    // The AddedPerson will be added at the left-hand of the FocusedPerson.
                    // Then we don't change any sign of the line.
                    if (leftHandSiblings) {
                        // Adjust the children line
                        val childrenLine = ChildrenLine()
                        val parentLayer = familyTreeDrawer.findPersonLayer(parent!!)
                        var childrenNumber = familyTreeDrawer.findPersonLayerSize(addingLayer)
                        val childrenLineLayer = addingLayer - 1
                        val childrenListId = parent!!.children!!
                        val childrenListInd: MutableList<Int> = mutableListOf()
                        childrenListId.forEach { id ->
                            childrenListInd.add(
                                familyTreeDrawer.findPersonIndById(
                                    id.toLong(), childrenLineLayer
                                )
                            )
                        }

                        // Extend the MarriageLineManager of AddedPerson's parent.
                        var emptyNodeNumber = familyTreeDrawer.findNumberOfEmptyNode(parentLayer)
                        val addingEmptyNodes = findAddingEmptyNodesParent(childrenNumber)
                        if (childrenNumber > 3) {
                            // Extend the MarriageLineManager by adding the empty node(s).
                            familyTreeDrawer = addMoreNodes(
                                emptyNodeNumber, addingEmptyNodes, parentLayer, familyTreeDrawer
                            )
                            // Find index of AddedPerson's siblings
                            val childrenListInd: MutableList<Int> = mutableListOf()
                            childrenListId.forEach { id ->
                                childrenListInd.add(
                                    familyTreeDrawer.findPersonIndById(
                                        id.toLong(), addingLayer
                                    )
                                )
                            }

                            // Extend the ChildrenLineManager the top layer of the AddedPerson
                            val addingMore = Math.abs(childrenNumber - (addingInd + 1))
                            if (addingMore > 1) {
                                val extendedLine = familyTreeDrawer.extendRelationshipLineAtPosition(
                                    childrenLineLayer, addingInd, null, childrenListInd
                                )

                                familyTreeDrawer.replaceFamilyStorageLayer(
                                    childrenLineLayer, parentLayer, extendedLine, childrenLine
                                )
                            }

                            var startInd = childrenListInd[0]
                            val parentInd = familyTreeDrawer.findPersonInd(parent!!, parentLayer)
                            if (!rightHandSiblings) {
                                // Extend the CHILDREN Line the top layer of the AddedPerson.
                                // When the AddedPerson is added on the left-hand of this wife (FocusedPerson).
                                // Check an empty node.
                                if ((startInd != 0) && (parentInd > startInd)) {
                                    // Moving the children line by adding an empty node.
                                    familyTreeDrawer.replaceFamilyStorageLayer(
                                        childrenLineLayer, 0, null, null
                                    )
                                    // Extend the children line.
                                    var childrenNumber = familyTreeDrawer.findPersonLayerSize(addingLayer)
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

                                    // Object Visualization
                                    val childrenLine = ChildrenLine()
                                    childrenLine.extendLine(childrenListInd, parentInd)

                                    familyTreeDrawer.replaceFamilyStorageLayer(
                                        childrenLineLayer, startInd, extendedLine, childrenLine
                                    )
                                } else {
                                    startInd = parentLayer

                                    // Object Visualization
                                    childrenLine.extendLine(childrenListInd, parentInd)
                                }
                            } else {
                                // Middle Child
                                var startInd = childrenListInd[0]
                                val parentInd = familyTreeDrawer.findPersonInd(parent!!, parentLayer)

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
                                childrenNumber = familyTreeDrawer.findPersonLayerSize(addingLayer)
                                val expectedLength = familyTreeDrawer.childrenLineLength(childrenNumber - midEmptyNode)
                                val extendedLine = familyTreeDrawer.extendLine(
                                    expectedLength,
                                    childrenListInd,
                                    parentInd
                                )
                                // Object Visualization
                                childrenLine.extendLine(childrenListInd, parentInd)

                                familyTreeDrawer.replaceFamilyStorageLayer(
                                    childrenLineLayer, startInd, extendedLine, childrenLine
                                )
                            }

                            // Move children sign
                            // Object Visualization
                            val extraNode = familyTreeDrawer.findNumberOfEmptyNode(addingLayer)
                            var line: Any? = getLineType(
                                familyTreeDrawer,
                                childrenLineLayer, addingEmptyNodes, childrenListInd, extraNode
                            )

                            val editedLine = familyTreeDrawer.moveChildrenLineSign(
                                childrenLineLayer, addingEmptyNodes, childrenListInd, extraNode
                            )
                            familyTreeDrawer.replaceFamilyStorageLayer(
                                childrenLineLayer, startInd, editedLine, line
                            )
                        }
                    }
                }
            } else {
                familyTreeDrawer.addFamilyLayer(nodeName, addedPerson)
            }
        } else {
            // Children or Twin
            val parentLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
            val parentInd = familyTreeDrawer.findPersonInd(focusedPerson!!, parentLayer)

            // Separate AddedPerson and their cousins by adding empty node(s).
            separateMidChildren(familyTreeDrawer, focusedPerson!!, parentLayer, parentInd)

            // Separate AddedPerson's parent from their uncles/aunts by adding empty node(s).
            separateParentSib(familyTreeDrawer, focusedPerson!!, addedPerson, parentLayer, parentInd)

            // Add a single child
            addMiddleChild(
                focusedPerson!!,
                nodeName,
                GenderLabel.MALE,
                addedPerson,
                siblings,
                familyTreeDrawer
            )
        }

        return familyTreeDrawer
    }

    override fun getArea(): Double = nodeSize * nodeSize
}
