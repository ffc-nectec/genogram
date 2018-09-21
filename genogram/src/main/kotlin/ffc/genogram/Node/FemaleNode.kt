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
    var focusedPerson: Person?,
    var nodeName: String,
    var parent: Person?
) : Node() {

    override fun drawNode(relationLabel: RelationshipLabel?, siblings: Boolean): FamilyTreeDrawer {

        if (relationLabel != RelationshipLabel.CHILDREN &&
            relationLabel != RelationshipLabel.TWIN
        ) {
            nodeName = createGenderBorder(nodeName, GenderLabel.FEMALE)

            if (focusedPerson != null) {
                val addingLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)

                // find whether the focusedPerson has any siblings.
                // if he/she has we'll reorder the array.
                val rightHandSib = familyTreeDrawer.hasPeopleOnTheRight(
                    focusedPerson!!, addingLayer
                )
                if (rightHandSib) {
                    val addingInd = familyTreeDrawer.findPersonInd(
                        focusedPerson!!, addingLayer
                    )
                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                        addingLayer, addingInd, nodeName
                    )

                    // extend the previous generation layer
                    val parentsLayer = familyTreeDrawer.findPersonLayer(parent!!)
                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                        parentsLayer, 1, null
                    )

                    if (parent != null) {
                        // extend the marriage line
                        val parentInd = familyTreeDrawer.findPersonInd(parent!!, parentsLayer)
                        val editingLayer = familyTreeDrawer.findPersonLayer(parent!!)
                        val marriageLine = familyTreeDrawer.extendRelationshipLine(
                            editingLayer, parentInd, RelationshipLabel.MARRIAGE
                        )
                        familyTreeDrawer.replaceFamilyStorageIndex(
                            editingLayer + 1, parentInd, marriageLine
                        )
                        // extend the children line
                        val childrenLine = familyTreeDrawer.extendRelationshipLine(
                            editingLayer + 1, parentInd, RelationshipLabel.CHILDREN
                        )
                        familyTreeDrawer.replaceFamilyStorageIndex(
                            editingLayer + 2, parentInd, childrenLine
                        )
                    }
                } else
                    familyTreeDrawer.addFamilyAtLayer(addingLayer, nodeName)
            } else {
                familyTreeDrawer.addFamilyLayer(nodeName, familyTreeDrawer.familyStorage)
            }
        } else {
            // Children or Twin
            val familyGen = familyTreeDrawer.findStorageSize() - 1
            val currentLayer = familyTreeDrawer.familyStorage[familyGen]
            currentLayer.add(setNodePosition(nodeName, 1, siblings))
        }

        return familyTreeDrawer
    }

    override fun getArea(): Double = PI * Math.pow(nodeSize, 2.0)
}
