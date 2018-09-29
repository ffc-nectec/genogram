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
    val addedPerson: Person,
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
                        addingLayer, addingInd, nodeName, addedPerson
                    )

                    if (parent != null) {
                        // check number of children with their spouses
                        val parentLayer = familyTreeDrawer.findPersonLayer(parent!!)
                        val parentInd = familyTreeDrawer.findPersonIndById(focusedPerson!!.father!!, parentLayer)
                        val childrenLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
                        val childrenLineLayer = childrenLayer - 1
                        val childrenNumber = familyTreeDrawer.findPersonLayerSize(childrenLayer)
                        val childrenListId = parent!!.children!!
                        val childrenListInd: MutableList<Int> = mutableListOf()
                        childrenListId.forEach { id ->
                            childrenListInd.add(
                                familyTreeDrawer.findPersonIndById(
                                    id.toLong(), childrenLineLayer
                                )
                            )
                        }

                        if (childrenNumber > 3) {
                            // extend parent line
                        }
                        // extend children line
                        val extendedLine = familyTreeDrawer.extendRelationshipLineAtPosition(
                            childrenLineLayer, addingInd + 1, childrenListInd
                        )
                        familyTreeDrawer.replaceFamilyStorageIndex(
                            childrenLineLayer, parentLayer, extendedLine
                        )
                    }
                } else
                    familyTreeDrawer.addFamilyAtLayer(addingLayer, nodeName, addedPerson)
            } else {
                familyTreeDrawer.addFamilyLayer(nodeName, addedPerson)
            }
        } else {
            // Children or Twin
            val familyGen = familyTreeDrawer.findStorageSize() - 1
            familyTreeDrawer.addFamilyAtLayer(
                familyGen,
                setNodePosition(nodeName, GenderLabel.FEMALE, siblings),
                addedPerson
            )
        }

        return familyTreeDrawer
    }

    override fun getArea(): Double = PI * Math.pow(nodeSize, 2.0)
}
