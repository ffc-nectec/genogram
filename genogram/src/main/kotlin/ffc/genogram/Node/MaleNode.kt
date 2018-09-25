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
    var familyTreeDrawer: FamilyTreeDrawer,
    private val addedPerson: Person,
    private var relatedPerson: Person?,
    var nodeName: String,
    var parentName: Person?
) : Node() {

    override fun drawNode(relationLabel: RelationshipLabel?, siblings: Boolean): FamilyTreeDrawer {

        if (relationLabel != RelationshipLabel.CHILDREN &&
            relationLabel != RelationshipLabel.TWIN
        ) {
            nodeName = createGenderBorder(nodeName, GenderLabel.MALE)

            if (relatedPerson != null) {
                val addingLayer = familyTreeDrawer.findPersonLayer(relatedPerson!!)
                val addingInd = familyTreeDrawer.findPersonInd(relatedPerson!!, addingLayer)

                if (relatedPerson!!.gender == 1) {

                    val leftHandSiblings = familyTreeDrawer.hasPeopleOnTheLeft(
                        relatedPerson!!, addingLayer
                    )
                    val rightHandSiblings = familyTreeDrawer.hasPeopleOnTheRight(
                        relatedPerson!!, addingLayer
                    )

                    if (!leftHandSiblings) {
                        // add node husband node on the left hand.
                        familyTreeDrawer.addFamilyStorageAtIndex(
                            addingLayer, addingInd, " $nodeName", addedPerson
                        )
                        for (i in 0 until addingLayer)
                            familyTreeDrawer.addFamilyStorageReplaceIndex(
                                i, 0, null, null
                            )
                    } else if (!rightHandSiblings) {
                        // add node husband node on the right hand.
                        familyTreeDrawer.addFamilyAtLayer(addingLayer, nodeName, addedPerson)
                    } else {
                        // has both siblings on the left and right hands.
                        // add husband on the right hand.
                        // and make an empty node.
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
