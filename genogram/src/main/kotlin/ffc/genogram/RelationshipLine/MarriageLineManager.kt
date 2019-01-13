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
    val bloodFamilyId: MutableList<Int>
) : Relationship() {

    private val sign = '_'
    private val space = ' '
    private val endSign = '|'

    override fun drawLine(): FamilyTreeDrawer {

        /*if (focusedPerson.firstname == "Mike") {
            print("------ MarriageLineM. 45 ------\n")
            print("focusedPerson: ${focusedPerson.firstname}\n")
            print("...............\n")
            val canvasB = displayObjectResult(familyTreeDrawer)
            print(canvasB.toString())
            print("---------------------------------------\n")
        }*/

        val marriageLine = MarriageLine()
        marriageLine.drawLine()

        // Add the focusedPerson's spouses
        val spouseList = if (focusedPerson.gender == GenderLabel.MALE)
            focusedPerson.wife else focusedPerson.husband

        spouseList?.let {
            it.forEach { id ->
                marriageLine.addSpouse(focusedPerson, family.findPerson(id)!!)
            }
        } ?: run {
            val unknownParent = family.createUnknownMember(focusedPerson)
            marriageLine.addSpouse(focusedPerson, unknownParent)
        }

        val lineLayer = personLayer + 1
        val focusedPersonSib = focusedPerson.findSiblingByParent(family)
        val focusedPersonInd = familyTreeDrawer.findPersonInd(focusedPerson, personLayer)
        var focusedPersonIndSize = familyTreeDrawer.findPersonIndSize(
            personLayer, 0, focusedPersonInd - 1
        )

        if (personLayer == 0)
            familyTreeDrawer.addFamilyNewLayer(createLineDistance(), marriageLine)
        else
            // personLayer > 0
            if (handSide == RelationshipLabel.RIGHT_HAND) {
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
                        // Relocate the marriage line's position.
                        val lastInd = familyTreeDrawer.findPersonLayerSize(lineLayer)
                        val lastIndSize = familyTreeDrawer.findMarriageLineIndSize(
                            lineLayer, 0, lastInd - 1
                        )
                        val personIndSize = familyTreeDrawer.findPersonIndSize(
                            personLayer,
                            0,
                            focusedPersonInd - 1
                        )
                        val addingMore = personIndSize - lastIndSize
                        for (i in lastInd until lastInd + addingMore)
                            familyTreeDrawer.addFamilyAtLayer(
                                lineLayer, "", EmptyNode()
                            )

                        // Add a marriage line
                        familyTreeDrawer.addFamilyAtLayer(
                            personLayer + 1,
                            createLineDistance(),
                            marriageLine
                        )
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
                            for (i in 0 until focusedPersonIndSize)
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
                        val personIndSize = familyTreeDrawer.findPersonIndSize(
                            personLayer, 0, personInd - 1
                        )
                        // When we have only one marriage line.
                        // Relocate the marriage line's position.
                        if (personIndSize != 0) {
                            for (i in 0 until personIndSize) {
                                familyTreeDrawer.addFamilyStorageReplaceIndex(
                                    personLayer + 1, 0, null, null
                                )
                            }
                        }
                    }
                }
            } else {
                // Add line on left hand side
                if (personLayer > 0) {
                    val hasLeftNodes = familyTreeDrawer.hasNodeOnTheLeft(
                        focusedPerson, personLayer
                    )

                    if (!hasLeftNodes) {
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
                            if (focusedPersonSib.size == 0)
                                marriageLine.setSingleMarriageLine(handSide)

                            familyTreeDrawer.addFamilyNewLayer(createLineDistance(), marriageLine)
                        }
                    } else {
                        // When husband node is added on the right-hand.
                        // For the 4th Generation, when addingPerson's parent is the only child
                        val storageSize = familyTreeDrawer.findPersonStorageSize()
                        val fpSibOrderSib = focusedPerson.getSibOrder(
                            familyTreeDrawer, personLayer - 1
                        )

                        if (familyTreeDrawer.generationNumber(personLayer) >= 3) {
                            if (storageSize - 1 >= lineLayer) {
                                val marriageLineLayerSize = familyTreeDrawer.findPersonLayerSize(
                                    lineLayer
                                )
                                val marriageLineIndSize = familyTreeDrawer.findMarriageLineIndSize(
                                    lineLayer, 0, marriageLineLayerSize
                                )
                                val addingPersonInd = focusedPersonIndSize - 1

                                // Check the node in front of the focusedPerson if it's an empty node than - 1
                                val previousObj = familyTreeDrawer.getPersonLayerInd(
                                    personLayer, focusedPersonInd - 1
                                )
                                val addingMore = if (previousObj is EmptyNode) {
                                    // Delete 1 because this is the left-hand marriage line
                                    focusedPersonIndSize - marriageLineIndSize - 1
                                } else {
                                    focusedPersonIndSize - marriageLineIndSize
                                }

                                if (addingPersonInd > marriageLineIndSize)
                                    for (i in marriageLineLayerSize - 1 until addingMore)
                                        familyTreeDrawer.addFamilyAtLayer(
                                            lineLayer,
                                            createLineDistance(),
                                            EmptyNode()
                                        )
                            } else {
                                val addingPersonInd = focusedPersonInd - 1
                                val addingPersonIndSize = familyTreeDrawer.findPersonIndSize(
                                    personLayer, 0, addingPersonInd
                                )

                                var movingNumb = when {
                                    familyTreeDrawer.getPersonLayerInd(
                                        personLayer,
                                        addingPersonInd
                                    ) is EmptyNode -> addingPersonIndSize
                                    focusedPersonIndSize != focusedPersonInd -> focusedPersonIndSize + 1
                                    else -> focusedPersonInd
                                }

                                for (i in 0 until movingNumb - 1)
                                    familyTreeDrawer.addFamilyAtLayer(
                                        lineLayer,
                                        createLineDistance(),
                                        EmptyNode()
                                    )
                            }

                            // Create a special marriageLine
                            if (fpSibOrderSib == RelationshipLabel.ONLY_CHILD) {
                                marriageLine.setSingleMarriageLine(handSide)
                                marriageLine.extendLeftHandLine()
                            }
                        }

                        // Add a marriage line
                        familyTreeDrawer.addFamilyAtLayer(
                            lineLayer,
                            createLineDistance(),
                            marriageLine
                        )

                        // Relocate the marriage line's position.
                        if (familyTreeDrawer.findStorageLayerSize(lineLayer) > 0) {
                            // Add empty node(s) between the marriage line.
                            var addingInd = familyTreeDrawer.addMarriageLineInd(
                                lineLayer, focusedPerson, null
                            )

                            val lineLayerSize = familyTreeDrawer.findStorageLayerSize(lineLayer)
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

                                // Here it's all the left-hand
                                // The line will be added at the focusedPersonIndSize
                                val startInd = familyTreeDrawer.findStorageLayerSize(lineLayer) - 1
                                val startIndSize = familyTreeDrawer.findMarriageLineIndSize(
                                    lineLayer, 0, startInd - 1
                                )
                                val addingMore = focusedPersonIndSize - startIndSize

                                // Check the previous node
                                val previousObj = familyTreeDrawer.findPreviousObj(
                                    personLayer,
                                    focusedPersonInd
                                )
                                val marriageLineInd = familyTreeDrawer.findLineInd(
                                    marriageLine, lineLayer
                                )!!

                                if (previousObj is Person) {
                                    for (i in startInd until startInd + addingMore) {
                                        familyTreeDrawer.addFamilyStorageReplaceIndex(
                                            lineLayer, marriageLineInd, null, null
                                        )
                                    }
                                }
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
}
