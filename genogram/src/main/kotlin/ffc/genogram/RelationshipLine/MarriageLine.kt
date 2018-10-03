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

package ffc.genogram.RelationshipLine

import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.Person

class MarriageLine(
    private var familyTreeDrawer: FamilyTreeDrawer,
    private var handSide: RelationshipLabel,
    private var addingLayer: Int,
    var person: Person
) : Relationship() {

    override fun drawLine(): FamilyTreeDrawer {

        val childrenLayer = familyTreeDrawer.findPersonLayer(person)
        val childrenNumb = familyTreeDrawer.findPersonLayerSize(childrenLayer)

        if (handSide == RelationshipLabel.RIGHT_HAND) {

            if (addingLayer > 0) {
                if (familyTreeDrawer.findStorageSize() > (addingLayer + 1)) {
                    familyTreeDrawer.addFamilyAtLayer(
                        addingLayer + 1,
                        createLineDistance(),
                        null
                    )
                } else if (familyTreeDrawer.findStorageSize() == (addingLayer + 1)) {
                    // HERE!!
                    if (childrenNumb == 1) {
                        familyTreeDrawer.addFamilyNewLayer(
                            singleChildMarriageLine(RelationshipLabel.RIGHT_HAND)
                        )
                    } else {
                        familyTreeDrawer.addFamilyNewLayer(createLineDistance())
                    }
                }
            } else
                familyTreeDrawer.addFamilyNewLayer(createLineDistance())
        } else {
            // Add line on left hand side
            if (addingLayer > 0) {

                if (addingLayer == 0) {
                    familyTreeDrawer.addFamilyNewLayer(createLineDistance())
                } else {

                    val hasLeftHandSiblings = familyTreeDrawer.hasPeopleOnTheLeft(
                        person, addingLayer
                    )

                    if (!hasLeftHandSiblings) {
                        // Add node husband node on the left hand.
                        // Check whether FocusedPerson has any siblings.
                        if (childrenNumb == 1) {
                            familyTreeDrawer.addFamilyNewLayer(
                                singleChildMarriageLine(RelationshipLabel.LEFT_HAND)
                            )
                        } else {
                            familyTreeDrawer.addFamilyNewLayer(createLineDistance())
                        }
                    } else {
                        // add node husband node on the right hand.
                        // or has both siblings on the left and right hands.
                        // add husband on the right hand.
                        // and make an empty node.
                        familyTreeDrawer.addFamilyAtLayer(
                            addingLayer + 1,
                            createLineDistance(),
                            null
                        )
                    }
                }
            }
        }

        return familyTreeDrawer
    }

    override fun createLineDistance(): String {
        val sign = '_'
        val space = ' '
        var resultSpace = ""
        var resultSign = ""

        for (i in 0 until spaceLine.toInt() + 1)
            resultSpace += space

        for (i in 0 until distanceLine.toInt())
            resultSign += sign

        return "$resultSpace|$resultSign|$resultSpace"
    }

    private fun singleChildMarriageLine(side: RelationshipLabel): String {
        val line = createLineDistance()
        val addMore = (lengthLine / 2).toInt()
        var tmp = StringBuilder()
        val sign = '_'
        val endSign = '|'
        val space = ' '

        if (side == RelationshipLabel.LEFT_HAND) {
            for (i in 0 until (line.length - indent.toInt() - 1)) {
                tmp.append(line[i])
            }

            for (i in 0 until addMore - 1) {
                tmp.append(sign)
            }
        } else {
            for (i in 0 until addMore - 1) {
                tmp.append(space)
            }

            for (i in 0 until (line.length - indent.toInt() - 1)) {
                tmp.append(line[i])
            }

            for (i in 0 until addMore) {
                tmp.append(sign)
            }
        }

        tmp.append(endSign)
        for (i in 0 until indent.toInt() - 1) {
            tmp.append(space)
        }

        return tmp.toString()
    }
}
