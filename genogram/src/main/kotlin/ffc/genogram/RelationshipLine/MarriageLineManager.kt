/*
 * Copyright 2018 NECTEC
 *   National Electronics and Computer Technology Center, Thailand
 *
 * Licensed under the Apache License, Version FamilyTree2.0 (the "License");
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

import ffc.genogram.Family
import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.GenderLabel
import ffc.genogram.Node.EmptyNode
import ffc.genogram.Person

class MarriageLineManager(
    private var familyTreeDrawer: FamilyTreeDrawer,
    private var handSide: RelationshipLabel,
    private var personLayer: Int,
    var focusedPerson: Person,
    var family: Family,
    val bloodFamily: MutableList<Int>
) : Relationship() {

    private val sign = '_'
    private val space = ' '
    private val endSign = '|'

    override fun drawLine(): FamilyTreeDrawer {

        val marriageLine = MarriageLine()
        marriageLine.drawLine()

        // Add the focusedPerson's spouses
        if (focusedPerson.gender == GenderLabel.MALE) {
            focusedPerson.wife?.forEach {
                marriageLine.addSpouse(focusedPerson, family.findPerson(it)!!)
            }
        } else {
            focusedPerson.husband?.forEach {
                marriageLine.addSpouse(focusedPerson, family.findPerson(it)!!)
            }
        }

        val lineLayer = personLayer + 1
        val focusedPersonSib = focusedPerson.findSiblingByParent(family)
        val focusedPersonInd = familyTreeDrawer.findPersonInd(focusedPerson, personLayer)

        if (handSide == RelationshipLabel.RIGHT_HAND) {
            if (personLayer > 0) {
                if (familyTreeDrawer.findStorageSize() > (personLayer + 1)) {
                    if (focusedPersonSib.size == 0) {
                        marriageLine.setSingleMarriageLine(handSide)
                        familyTreeDrawer.addFamilyAtLayer(
                            lineLayer,
                            singleChildMarriageLine(handSide),
                            marriageLine
                        )

                        // For the 4th Generation, when addingPerson's parent is the only child
                        val marriageLineInd = familyTreeDrawer.findLineInd(marriageLine, lineLayer)!!
                        val marriageLineIndSize = familyTreeDrawer.findMarriageLineIndSize(
                            lineLayer, 0, marriageLineInd - 1
                        )
                        val personIndSize = familyTreeDrawer.findPersonIndSize(
                            personLayer,
                            0,
                            focusedPersonInd - 1
                        )
                        val addingMore = personIndSize - marriageLineIndSize
                        if (focusedPersonInd >= 3 && focusedPersonInd > marriageLineInd) {
                            for (i in 0 until addingMore)
                                familyTreeDrawer.addFamilyStorageReplaceIndex(
                                    lineLayer, marriageLineInd, null, null
                                )
                        }
                    } else {
                        // Add a marriage line
                        familyTreeDrawer.addFamilyAtLayer(
                            personLayer + 1,
                            createLineDistance(),
                            marriageLine
                        )

                        // Relocate the marriage line's position.
                        if (familyTreeDrawer.findStorageLayerSize(lineLayer) > 0) {
                            addEmptyNode(lineLayer)
                        }
                    }
                } else if (familyTreeDrawer.findStorageSize() == (personLayer + 1)) {
                    if (focusedPersonSib.size == 0) {
                        marriageLine.setSingleMarriageLine(handSide)
                        familyTreeDrawer.addFamilyNewLayer(
                            singleChildMarriageLine(handSide),
                            marriageLine
                        )

                        // For the 4th Generation, when addingPerson's parent is the only child
                        val marriageLineInd = familyTreeDrawer.findLineInd(marriageLine, lineLayer)!!
                        if (focusedPersonInd > marriageLineInd) {
                            for (i in 0 until focusedPersonInd)
                                familyTreeDrawer.addFamilyStorageReplaceIndex(
                                    lineLayer, marriageLineInd, null, null
                                )
                        }
                    } else {
                        // Find the focusedPerson's index
                        familyTreeDrawer.addFamilyNewLayer(createLineDistance(), marriageLine)
                        val personInd = familyTreeDrawer.findPersonIndById(
                            focusedPerson.idCard, personLayer
                        )
                        // When we have only one marriage line.
                        // Relocate the marriage line's position.
                        if (personInd != 0) {
                            for (i in 0 until personInd) {
                                familyTreeDrawer.addFamilyStorageReplaceIndex(
                                    personLayer + 1, 0, null, null
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
            if (personLayer > 0) {
                if (personLayer == 0) {
                    familyTreeDrawer.addFamilyNewLayer(createLineDistance(), marriageLine)
                } else {
                    val hasLeftHandSiblings = familyTreeDrawer.hasNodeOnTheLeft(
                        focusedPerson, personLayer
                    )

                    if (!hasLeftHandSiblings) {
                        // Add node husband node on the left hand.
                        // Check whether FocusedPerson has any siblings.
                        val focusedPersonSib = focusedPerson.findSiblingByParent(family)
                        if (focusedPersonSib.size == 0) {
                            marriageLine.drawLine()
                            marriageLine.setSingleMarriageLine(handSide)
                            marriageLine.extendLeftHandLine()
                            familyTreeDrawer.addFamilyNewLayer(
                                singleChildMarriageLine(handSide),
                                marriageLine
                            )
                        } else {
                            if (familyTreeDrawer.generationNumber(personLayer) >= 3
                                && focusedPerson.isBloodFamily(bloodFamily)
                            )
                                marriageLine.setSingleMarriageLine(handSide)

                            familyTreeDrawer.addFamilyNewLayer(createLineDistance(), marriageLine)
                        }
                    } else {
                        // When husband node is added on the right-hand.
                        val marriageLineLayer = personLayer + 1
                        // For the 4th Generation, when addingPerson's parent is the only child
                        val parentSib = focusedPerson.findSiblingByParent(family)
                        if (familyTreeDrawer.generationNumber(personLayer) >= 3 && parentSib.size == 0) {
                            val focusedPersonInd = familyTreeDrawer.findPersonInd(
                                focusedPerson, personLayer
                            )

                            for (i in 0 until focusedPersonInd - 1)
                                familyTreeDrawer.addFamilyAtLayer(
                                    marriageLineLayer,
                                    createLineDistance(),
                                    EmptyNode()
                                )

                            // Create a special marriageLine
                            marriageLine.setSingleMarriageLine(handSide)
                            marriageLine.extendLeftHandLine()
                        }

                        // Add a marriage line
                        familyTreeDrawer.addFamilyAtLayer(
                            marriageLineLayer,
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
        val tmp = StringBuilder()

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
                        personLayer + 1,
                        createLineDistance(),
                        marriageLine
                    )
                } else {
                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                        personLayer + 1, i, null, null
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
                    personLayer + 1, addingInd - 1, null, null
                )
            }
        }
    }
}
