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

class MaleNode(
    private var familyTreeDrawer: FamilyTreeDrawer,
    private val addedPerson: Person,
    private var focusedPerson: Person?,
    private var nodeName: String,
    var parent: Person?
) : Node() {

    override fun drawNode(relationLabel: RelationshipLabel?, siblings: Boolean): FamilyTreeDrawer {

        if (relationLabel != RelationshipLabel.CHILDREN &&
            relationLabel != RelationshipLabel.TWIN
        ) {
            nodeName = createGenderBorder(nodeName, GenderLabel.MALE)

            if (focusedPerson != null) {
                val addingLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
                val addingInd = familyTreeDrawer.findPersonInd(focusedPerson!!, addingLayer)

                if (focusedPerson!!.gender == 1) {

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
                            addingLayer, addingInd, "$nodeName", addedPerson
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
                            addingLayer, addingInd - 1, " $nodeName", addedPerson
                        )
                    }

                    // Adjust the children line
                    val parentLayer = familyTreeDrawer.findPersonLayer(parent!!)
                    val childrenLayer = familyTreeDrawer.findPersonLayer(addedPerson)
                    val childrenNumber = familyTreeDrawer.findPersonLayerSize(childrenLayer)
                    val childrenLineLayer = childrenLayer - 1
                    val childrenListId = parent!!.children!!
                    val childrenListInd: MutableList<Int> = mutableListOf()
                    childrenListId.forEach { id ->
                        childrenListInd.add(
                            familyTreeDrawer.findPersonIndById(
                                id.toLong(), childrenLineLayer
                            )
                        )
                    }

                    // Extend the MarriageLine of AddedPerson's parent.
                    if (childrenNumber > 3) {
                        // Extend the MarriageLine by adding the empty node(s).
                        var emptyNodeNumber = familyTreeDrawer.findNumberOfEmptyNode(parentLayer)
                        val addingEmptyNodes = findAddingEmptyNodes(childrenNumber)
                        familyTreeDrawer = addMoreNodes(
                            emptyNodeNumber, addingEmptyNodes, parentLayer, familyTreeDrawer
                        )

                        // Extend the ChildrenLine the top layer of the AddedPerson
                        val addingMore = Math.abs(childrenNumber - (addingInd + 1))
                        if (addingMore > 1) {
                            for (i in 0 until addingMore - 1) {
                                val extendedLine = familyTreeDrawer.extendRelationshipLineAtPosition(
                                    childrenLineLayer, addingInd, childrenListInd
                                )
                                familyTreeDrawer.replaceFamilyStorageIndex(
                                    childrenLineLayer, parentLayer, extendedLine
                                )
                            }
                        }

                        // Move children sign
                        val editedLine = familyTreeDrawer.moveChildrenLineSign(
                            childrenLineLayer, addingEmptyNodes
                        )
                        familyTreeDrawer.replaceFamilyStorageIndex(
                            childrenLineLayer, parentLayer, editedLine
                        )
                    }
                }
            } else {
                familyTreeDrawer.addFamilyLayer(nodeName, addedPerson)
            }
        } else {
            // Children or Twin
            val familyGen = familyTreeDrawer.findStorageSize() - 1
            familyTreeDrawer.addFamilyAtLayer(
                familyGen,
                setNodePosition(nodeName, GenderLabel.MALE, siblings),
                addedPerson
            )
        }

        return familyTreeDrawer
    }

    override fun getArea(): Double = nodeSize * nodeSize
}
