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

import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.GenderLabel
import ffc.genogram.Person
import ffc.genogram.RelationshipLine.RelationshipLabel
import kotlin.math.PI

class FemaleNode(
    var familyTreeDrawer: FamilyTreeDrawer,
    private val addedPerson: Person,
    var focusedPerson: Person?,
    var nodeName: String,
    var parent: Person?
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
                        val childrenListInd: MutableList<Int> = mutableListOf()
                        childrenListId.forEach { id ->
                            childrenListInd.add(
                                familyTreeDrawer.findPersonIndById(
                                    id.toLong(), childrenLayer
                                )
                            )
                        }

                        val addingEmptyNodes = findAddingEmptyNodesParent(childrenNumber)
                        var emptyNodeNumber = familyTreeDrawer.findNumberOfEmptyNode(parentLayer)

                        // Extend the MarriageLineManager of AddedPerson's parent.
                        if (childrenNumber > 3) {
                            // Extend the MarriageLineManager by adding the empty node(s).
                            familyTreeDrawer = addMoreNodes(
                                emptyNodeNumber, addingEmptyNodes, parentLayer, familyTreeDrawer
                            )
                        }

                        // Extend the CHILDREN Line the top layer of the AddedPerson.
                        // When the AddedPerson is added on the left-hand of this wife (FocusedPerson).
                        var startInd = childrenListInd[0]
                        var addingInd = parentInd - 1
                        if (startInd != 0) {
                            childrenNumber -= 1
                        } else {
                            startInd = 0
                            addingInd = 0
                        }

                        val expectedLength = familyTreeDrawer.childrenLineLength(childrenNumber)
                        val extendedLine = familyTreeDrawer.extendLine(
                            expectedLength,
                            childrenListInd,
                            parentInd
                        )
                        familyTreeDrawer.replaceFamilyStorageLayer(
                            childrenLineLayer, startInd, extendedLine
                        )

                        // Move the children sign
                        val editedLine = familyTreeDrawer.moveChildrenLineSign(
                            childrenLineLayer, addingEmptyNodes, childrenListInd
                        )
                        familyTreeDrawer.replaceFamilyStorageLayer(
                            childrenLineLayer, addingInd, editedLine
                        )

                        // Extend the MarriageLineManager of AddedPerson and FocusedPerson
                        // by adding the empty node(s).
                        // Check AddedPerson's husband index, then check
                        // whether her husband's index is equal to the number of empty node(s).
                        val husbandInd = familyTreeDrawer.findPersonInd(focusedPerson!!, childrenLayer)
                        val marriageLineNumb = familyTreeDrawer.findPersonLayerSize(childrenLayer + 1)

                        if ((husbandInd != emptyNodeNumber) && (marriageLineNumb == 1)) {
                            val addMore = Math.abs(addingEmptyNodes - emptyNodeNumber)
                            if (addMore > 0) {
                                for (i in 0..(addMore - 1))
                                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                                        childrenLayer + 1,
                                        0, null, null
                                    )
                            }
                        }
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
                        var emptyNodeNumber = familyTreeDrawer.findNumberOfEmptyNode(parentLayer)
                        val addingEmptyNodes = findAddingEmptyNodesParent(childrenNumber)
                        familyTreeDrawer = addMoreNodes(
                            emptyNodeNumber, addingEmptyNodes, parentLayer, familyTreeDrawer
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
                            val editedLine = familyTreeDrawer.moveChildrenLineSign(
                                childrenLineLayer, addingEmptyNodes, childrenListInd
                            )
                            familyTreeDrawer.replaceFamilyStorageLayer(
                                childrenLineLayer, startInd, editedLine
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
                GenderLabel.FEMALE,
                addedPerson,
                siblings,
                familyTreeDrawer
            )
        }

        return familyTreeDrawer
    }

    override fun getArea(): Double = PI * Math.pow(nodeSize, 2.0)
}
