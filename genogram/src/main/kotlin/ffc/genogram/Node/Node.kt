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
import ffc.genogram.RelationshipLine.ChildrenLine
import ffc.genogram.RelationshipLine.Relationship
import ffc.genogram.RelationshipLine.RelationshipLabel

abstract class Node {

    companion object {
        const val borderline = 3.0
        const val color = 0xff888888
        const val nodeSize = 6.0
        const val nodesDistance = 2.0
        const val nodeBorderSize = 2.0
    }

    val nodeMargin = ((Relationship.spaceLine + Relationship.distanceLine) / 2).toInt()

    abstract fun drawNode(relationLabel: RelationshipLabel?, siblings: Boolean): FamilyTreeDrawer?

    abstract fun getArea(): Double

    private fun setSingleNodePosition(nodeName: String, gender: GenderLabel, siblings: Boolean): String {
        val space = " "
        var resultSpace = ""

        val newNodeName = if (gender == GenderLabel.MALE) {
            createGenderBorder(nodeName, GenderLabel.MALE)
        } else {
            createGenderBorder(nodeName, GenderLabel.FEMALE)
        }

        if (!siblings)
            for (i in 0 until nodeMargin - 1) {
                resultSpace += space
            }

        return resultSpace + newNodeName + resultSpace
    }

    fun findAddingEmptyNodesParent(childrenNumber: Int): Int {
        return if (childrenNumber % 2 == 0)
            childrenNumber / 2 - 1
        else
            Math.floorDiv(childrenNumber, 2) - 1
    }

    fun addMoreNodes(
        emptyNodeNumber: Int,
        addingEmptyNodes: Int,
        parentLayer: Int,
        familyTreeDrawer: FamilyTreeDrawer
    ): FamilyTreeDrawer {

        val addMore = Math.abs(addingEmptyNodes - emptyNodeNumber)
        if (addMore > 0) {
            for (i in (parentLayer + 1) downTo 0)
                for (j in 1..addMore)
                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                        i, 0, null, null
                    )
        }

        return familyTreeDrawer
    }

    fun addMiddleChild(
        focusedPerson: Person,
        nodeName: String,
        gender: GenderLabel,
        addedPerson: Person,
        siblings: Boolean,
        familyTreeDrawer: FamilyTreeDrawer
    ) {
        // Children or Twin
        val addingLayer = familyTreeDrawer.findStorageSize() - 1
        val focusedPersonLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
        val focusedPersonInd = familyTreeDrawer.findPersonInd(
            focusedPerson!!, focusedPersonLayer
        )
        val childrenNumb = familyTreeDrawer.findLineNumber(addingLayer)

        // Set Person node's indent
        addedPerson.setNodeMargin(siblings)
        // Add a single child
        familyTreeDrawer.addFamilyAtLayer(
            addingLayer,
            setSingleNodePosition(nodeName, gender, siblings),
            addedPerson
        )

        // "focusedPerson" = parent, when the generation is greater than 2.
        // "parent" = grandparent
        // Find parent index, and add the addedPerson node at the index.
        // Move the addedPerson node.
        val addingLayerSize = familyTreeDrawer.findPersonLayerSize(addingLayer)
        val addingInd = focusedPersonInd - childrenNumb

        if (addingLayerSize < addingInd) {
            // Add empty node(s), and move the node
            for (i in focusedPersonInd until addingInd) {
                if (i == addingInd) {
                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                        addingLayer, i,
                        setSingleNodePosition(nodeName, gender, siblings),
                        addedPerson
                    )
                } else {
                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                        addingLayer, i, null, null
                    )
                }
            }
        } else {
            // When the layer's is enough for moving the child node
            // to the "parent"'s index.
            // Move the child node to "parent"'s index.
            var addingMore = addingInd - addingLayerSize
            for (i in 0 until addingMore + 1) {
                familyTreeDrawer.addFamilyStorageReplaceIndex(
                    addingLayer, addingInd - 1, null, null
                )
            }
        }
    }

    fun separateMidChildren(
        familyTreeDrawer: FamilyTreeDrawer,
        focusedPerson: Person,
        parentLayer: Int,
        parentInd: Int
    ) {
        val childrenLayer = parentLayer + 3
        var parentInd = parentInd
        val emptyNodeNumb = familyTreeDrawer.findNumberOfEmptyNode(childrenLayer)
        val cousinsLayerElNumb = familyTreeDrawer.findPersonLayerSize(childrenLayer)
        val cousinsNumb = Math.abs(cousinsLayerElNumb - emptyNodeNumb)
        val addingEmptyNode = parentInd - cousinsLayerElNumb

        if (addingEmptyNode > 0) {
            if (cousinsLayerElNumb != addingEmptyNode) {
                if (cousinsLayerElNumb < addingEmptyNode) {
                    parentInd -= cousinsNumb
                }
                if (cousinsNumb != cousinsLayerElNumb - 1) {
                    for (i in cousinsLayerElNumb until parentInd) {
                        familyTreeDrawer.addFamilyStorageReplaceIndex(
                            childrenLayer,
                            i, null, null
                        )
                    }
                }
            }
        }
    }

    fun separateParentSib(
        familyTreeDrawer: FamilyTreeDrawer,
        focusedPerson: Person,
        addedPerson: Person,
        parentLayer: Int,
        parentInd: Int
    ) {

        var familyTreeDrawer = familyTreeDrawer
        val parentChildren = focusedPerson!!.children
        val isAddedPersonOldest = parentChildren!![0] == addedPerson.idCard.toInt()

        // Find index the addedPerson will be added.
        val addingLayer = familyTreeDrawer.findStorageSize() - 1
        var addedPersonInd = familyTreeDrawer.findPersonLayerSize(addingLayer)

        // addedPerson's parent index should be equal to or
        // greater than addedPerson's index.
        // Whether addedPerson is the oldest children.
        // Find the number of empty nodes for separate the layer.
        var emptyNodeNumb = Math.abs(addedPersonInd - parentInd)
        val childrenLineNumb = familyTreeDrawer.findLineNumber(parentLayer + 2) - 1
        val personNumb = familyTreeDrawer.findPersonNumbLayer(addingLayer)

        if (parentInd < addedPersonInd && isAddedPersonOldest) {
            // When the addedPerson's parent node is on the left-hand of the addedPerson,
            // the addedPerson's parent's siblings (addedPerson's uncles/aunts) have
            // children equal to less than their parent nodes.

            // Replace an empty node at the addedPerson's parent index.
            for (i in parentInd until parentInd + emptyNodeNumb) {
                familyTreeDrawer.addFamilyStorageReplaceIndex(
                    parentLayer,
                    i,
                    null,
                    null
                )
            }

            // Move the addedPerson's parent index
            val addingParentMarriageLineInd = familyTreeDrawer.addMarriageLineInd(
                parentLayer + 1, focusedPerson!!, null
            )
            for (i in addingParentMarriageLineInd until
                    addingParentMarriageLineInd + emptyNodeNumb) {
                familyTreeDrawer.addFamilyStorageReplaceIndex(
                    parentLayer + 1,
                    i,
                    null,
                    null
                )
            }

            // AddedPerson's parent adjusting zone.
            // Extend addedPerson's parent children line and grandparent's line
            // Extend the MarriageLineManager of AddedPerson's parent.
            var grandParentLayer = parentLayer - 3
            val grandFatherId = focusedPerson!!.father
            val grandMotherId = focusedPerson!!.mother
            var grandFatherInd = 0
            var grandMotherInd = 0

            if (grandFatherId != null)
                grandFatherInd = familyTreeDrawer.findPersonIndById(
                    grandFatherId, grandParentLayer
                )
            if (grandMotherId != null)
                grandMotherInd = familyTreeDrawer.findPersonIndById(
                    grandMotherId, grandParentLayer
                )

            val minParentInd = Math.min(grandFatherInd, grandMotherInd)
            val grandParent = familyTreeDrawer.getPersonLayerInd(
                grandParentLayer, minParentInd
            )

            val childrenListId = grandParent!!.children!!
            val drawSibListId: MutableList<Int> = mutableListOf()
            val childrenPersonLayer = familyTreeDrawer.getPersonLayer(parentLayer)
            childrenPersonLayer.forEachIndexed { index, element ->
                if (element is Person) {
                    childrenListId.forEach { id ->
                        if (element.idCard.toInt() == id) {
                            drawSibListId.add(id)
                            childrenPersonLayer.remove(index)
                        }
                    }
                }
            }

            val drawSibListInd: MutableList<Int> = mutableListOf()
            drawSibListId.forEach { id ->
                drawSibListInd.add(
                    familyTreeDrawer.findPersonIndById(
                        id.toLong(), parentLayer
                    )
                )
            }

            val nodeBetweenSibNumb = (drawSibListInd[drawSibListInd.size - 1] - drawSibListInd[0]) + 1
            var childrenNumber = familyTreeDrawer.findPersonLayerSize(parentLayer)
            var emptyNodeNumber = familyTreeDrawer.findNumberOfEmptyNode(grandParentLayer)
            val addingEmptyNodes = findAddingEmptyNodesParent(childrenNumber)

            if (childrenNumber > 3) {
                // Extend the MarriageLineManager by adding the empty node(s).
                familyTreeDrawer = addMoreNodes(
                    emptyNodeNumber, addingEmptyNodes, grandParentLayer, familyTreeDrawer
                )
            }

            // Extend the CHILDREN Line the top layer of the AddedPerson.
            // When the AddedPerson is added on the left-hand of this wife (FocusedPerson).
            var startInd = drawSibListInd[0]
            var addingInd = parentInd - 1
            if (startInd != 0) {
                childrenNumber -= 1
            } else {
                startInd = 0
                addingInd = 0
            }

            val expectedLength = familyTreeDrawer.childrenLineLength(nodeBetweenSibNumb)
            val extendedLine = familyTreeDrawer.extendLine(
                expectedLength,
                drawSibListInd,
                parentInd
            )
            val parentLineLayer = parentLayer - 1
            // Object Visualization
            val childrenLine = ChildrenLine()
            childrenLine.extendLine(drawSibListInd, parentInd)

            familyTreeDrawer.replaceFamilyStorageLayer(
                parentLineLayer, startInd, extendedLine, childrenLine
            )
            /*familyTreeDrawer.replaceFamilyStorageLayer(
                parentLineLayer, startInd, extendedLine
            )*/

            // Move the children sign
            val editedLine = familyTreeDrawer.moveChildrenLineSign(
                parentLineLayer, addingEmptyNodes, drawSibListInd
            )
            familyTreeDrawer.replaceFamilyStorageLayer(
                parentLineLayer, addingInd, editedLine
            )
        } else if (addedPersonInd < parentInd
            && isAddedPersonOldest
            && focusedPerson!!.children!!.size > 1
            && personNumb > childrenLineNumb
        ) {

            // When the addedPerson's parent node is on the right-hand of the addedPerson,
            // the addedPerson's parent's siblings (addedPerson's uncles/aunts) have children more than their parent node.
            // This case we don't need to separate the addedPerson's parent layer.

            // Replace an empty node at the addedPerson's parent index at the addedPerson's layer.
            for (i in addedPersonInd until parentInd) {
                familyTreeDrawer.addFamilyStorageReplaceIndex(
                    addingLayer,
                    i,
                    null,
                    null
                )
            }

            // Move the children line above the addedPerson.
            val childrenLineLayer = addingLayer - 1
            val childrenLineLastInd = familyTreeDrawer.findStorageLayerSize(childrenLineLayer) - 1

            for (i in childrenLineLastInd until childrenLineLastInd + emptyNodeNumb) {
                familyTreeDrawer.addFamilyStorageReplaceIndex(
                    childrenLineLayer,
                    i,
                    null,
                    null
                )
            }
        }
    }

    fun getLineType(
        familyTreeDrawer: FamilyTreeDrawer,
        childrenLineLayer: Int, addingEmptyNodes: Int, childrenListInd: MutableList<Int>
    ): Any? {
        var emptyNodeCount = familyTreeDrawer.findNumberOfEmptyNodePerson(childrenLineLayer)
        var midEmptyNodeCount = familyTreeDrawer.findNumberOfMidEmptyNodePerson(childrenLineLayer)
        emptyNodeCount = Math.abs(emptyNodeCount - midEmptyNodeCount)

        var line: Any? = null
        if (addingEmptyNodes == 0 || emptyNodeCount == 0) {
            line = ChildrenLine()
            line.moveChildrenLineSign(
                familyTreeDrawer, childrenLineLayer, addingEmptyNodes, childrenListInd
            )
        }

        return line
    }
}
