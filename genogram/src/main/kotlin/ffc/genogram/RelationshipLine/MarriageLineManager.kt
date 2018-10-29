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

class MarriageLineManager(
    private var familyTreeDrawer: FamilyTreeDrawer,
    private var handSide: RelationshipLabel,
    private var addingLayer: Int,
    var focusedPerson: Person
) : Relationship() {

    private val sign = '_'
    private val space = ' '
    private val endSign = '|'

    override fun drawLine(): FamilyTreeDrawer {

        val marriageLine = MarriageLine()
        marriageLine.drawLine()

        val childrenLayer = familyTreeDrawer.findPersonLayer(focusedPerson)
        val childrenNumb = familyTreeDrawer.findPersonLayerSize(childrenLayer)
        val lineLayer = addingLayer + 1

        if (handSide == RelationshipLabel.RIGHT_HAND) {
            if (addingLayer > 0) {
                if (familyTreeDrawer.findStorageSize() > (addingLayer + 1)) {
                    // Add a marriage line
                    familyTreeDrawer.addFamilyAtLayer(
                        addingLayer + 1,
                        createLineDistance(),
                        marriageLine
                    )

                    // Relocate the marriage line's position.
                    if (familyTreeDrawer.findStorageLayerSize(lineLayer) > 0) {
                        addEmptyNode(lineLayer)
                    }
                } else if (familyTreeDrawer.findStorageSize() == (addingLayer + 1)) {
                    if (childrenNumb == 1) {
                        marriageLine.drawLine()
                        marriageLine.setSingleMarriageLine(handSide)
                        familyTreeDrawer.addFamilyNewLayer(
                            singleChildMarriageLine(handSide),
                            marriageLine
                        )
                    } else {
                        // Find the focusedPerson's index
                        familyTreeDrawer.addFamilyNewLayer(createLineDistance(), marriageLine)
                        val personInd = familyTreeDrawer.findPersonIndById(
                            focusedPerson.idCard, addingLayer
                        )
                        // When we have only one marriage line.
                        // Relocate the marriage line's position.
                        if (personInd != 0) {
                            for (i in 0 until personInd) {
                                familyTreeDrawer.addFamilyStorageReplaceIndex(
                                    addingLayer + 1, 0, null, null
                                )
                            }
                        }
                    }
                }
            } else {
                familyTreeDrawer.addFamilyNewLayer(createLineDistance(), marriageLine)
            }
        } else {
            // Add line on left hand side
            if (addingLayer > 0) {
                if (addingLayer == 0) {
                    familyTreeDrawer.addFamilyNewLayer(createLineDistance(), marriageLine)
                } else {
                    val hasLeftHandSiblings = familyTreeDrawer.hasPeopleOnTheLeft(
                        focusedPerson, addingLayer
                    )

                    if (!hasLeftHandSiblings) {
                        // Add node husband node on the left hand.
                        // Check whether FocusedPerson has any siblings.
                        if (childrenNumb == 1) {
                            marriageLine.drawLine()
                            marriageLine.setSingleMarriageLine(handSide)
                            familyTreeDrawer.addFamilyNewLayer(
                                singleChildMarriageLine(handSide),
                                marriageLine
                            )
                        } else {
                            familyTreeDrawer.addFamilyNewLayer(createLineDistance(), marriageLine)
                        }
                    } else {
                        // When husband node is added on the right-hand.
                        // Add a marriage line
                        familyTreeDrawer.addFamilyAtLayer(
                            addingLayer + 1,
                            createLineDistance(),
                            marriageLine
                        )

                        // Relocate the marriage line's position.
                        if (familyTreeDrawer.findStorageLayerSize(lineLayer) > 0) {
                            addEmptyNode(lineLayer)
                        }
                    }
                }
            }
        }

        return familyTreeDrawer
    }

    override fun createLineDistance(): String {
        var firstSpacePart: String
        var secondSpacePart = ""
        var resultSign = ""

        for (i in 0 until spaceLine.toInt())
            secondSpacePart += space

        firstSpacePart = "$secondSpacePart "

        for (i in 0 until distanceLine.toInt())
            resultSign += sign

        return "$firstSpacePart$endSign$resultSign$endSign$secondSpacePart"
    }

    private fun singleChildMarriageLine(side: RelationshipLabel): String {
        val line = createLineDistance()
        val addMore = (lengthLine / 2).toInt()
        var tmp = StringBuilder()

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

    private fun addEmptyNode(lineLayer: Int) {
        // Add empty node(s) between the marriage line.
        val addingInd = familyTreeDrawer.addMarriageLineInd(
            lineLayer, focusedPerson, null
        )
        val lineLayerSize = familyTreeDrawer
            .findStorageLayerSize(lineLayer)
        val lastLineLayerInd = lineLayerSize - 1

        if (lineLayerSize < addingInd) {
            // When the layer's is less than the index the new marriage line expect.
            // Add empty node(s), and move the marriage line to "focusedPerson"'s index.
            val marriageLine = MarriageLine()
            marriageLine.drawLine()

            for (i in lastLineLayerInd until addingInd) {
                if (i == addingInd) {
                    familyTreeDrawer.addFamilyAtLayer(
                        addingLayer + 1,
                        createLineDistance(),
                        marriageLine
                    )
                } else {
                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                        addingLayer + 1, i, null, null
                    )
                }
            }
        } else {
            // When the layer's is enough for moving the new marriage line
            // to the "focusedPerson"'s index.
            // Move the marriage line to "focusedPerson"'s index.
            val addingMore = addingInd - lineLayerSize
            for (i in 0 until addingMore + 1) {
                familyTreeDrawer.addFamilyStorageReplaceIndex(
                    addingLayer + 1, addingInd - 1, null, null
                )
            }
        }
    }
}
