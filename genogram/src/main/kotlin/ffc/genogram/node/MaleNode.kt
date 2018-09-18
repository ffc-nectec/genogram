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

package ffc.genogram.node

import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.GenderLabel
import ffc.genogram.Person
import ffc.genogram.RelationshipLine.RelationshipLabel

class MaleNode(
    var familyTreeDrawer: FamilyTreeDrawer,
    var focusedPerson: Person?,
    var nodeName: String
) : Node() {

    override fun drawNode(relationLabel: RelationshipLabel?, siblings: Boolean): FamilyTreeDrawer {

        nodeName = createGenderBorder(nodeName, GenderLabel.MALE)

        if (relationLabel != RelationshipLabel.CHILDREN &&
            relationLabel != RelationshipLabel.TWIN
        ) {
            if (focusedPerson != null) {
                val addLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
                val addInd =
                    findPersonPosition(familyTreeDrawer.familyStorage[addLayer], focusedPerson!!)
                familyTreeDrawer.familyStorage[addLayer].add(addInd, nodeName)

                if (focusedPerson!!.gender == 1) {
                    // Female is always on the right hand of the male node then
                    // the empty nodes to the previous layer to make a balance picture.
                    for (i in 0 until addLayer)
                        familyTreeDrawer.familyStorage[i].add(0, createEmptyNode())
                }
            } else {
                familyTreeDrawer.addFamilyLayer(nodeName, familyTreeDrawer.familyStorage)
            }
        } else {
            // Children or Twin
            val familyGen = familyTreeDrawer.familyStorage.size - 1
            val currentLayer = familyTreeDrawer.familyStorage[familyGen]
            currentLayer.add(setNodePosition(nodeName, 0, siblings))
        }

        return familyTreeDrawer
    }

    override fun getArea(): Double = nodeSize * nodeSize
}
