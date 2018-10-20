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

    abstract fun drawNode(relationLabel: RelationshipLabel?, siblings: Boolean): FamilyTreeDrawer?

    abstract fun getArea(): Double

    private fun setSingleNodePosition(nodeName: String, gender: GenderLabel, siblings: Boolean): String {
        val diff = ((Relationship.spaceLine + Relationship.distanceLine) / 2).toInt()
        val space = " "
        var resultSpace = ""

        val newNodeName = if (gender == GenderLabel.MALE) {
            createGenderBorder(nodeName, GenderLabel.MALE)
        } else {
            createGenderBorder(nodeName, GenderLabel.FEMALE)
        }

        if (!siblings)
            for (i in 0 until diff - 1) {
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
        val childrenLineInd = addingLayerSize - 1

        if (addingLayerSize < addingInd) {
            // Add empty node(s), and move the node
            for (i in childrenLineInd until addingInd) {
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
            val addingMore = addingInd - addingLayerSize
            for (i in 0 until addingMore + 1) {
                familyTreeDrawer.addFamilyStorageReplaceIndex(
                    addingLayer, addingInd - 1, null, null
                )
            }
        }
    }

    fun separateMidChildren(familyTreeDrawer: FamilyTreeDrawer, focusedPerson: Person) {
        val parentLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
        val childrenLayer = parentLayer + 3
        var parentInd = familyTreeDrawer.findPersonInd(focusedPerson!!, parentLayer)
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
}
