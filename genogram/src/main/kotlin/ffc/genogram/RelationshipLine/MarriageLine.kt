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
    var focusedPerson: Person
) : Relationship() {

    private val sign = '_'
    private val space = ' '
    private val endSign = '|'

    override fun drawLine(): FamilyTreeDrawer {

        val childrenLayer = familyTreeDrawer.findPersonLayer(focusedPerson)
        val childrenNumb = familyTreeDrawer.findPersonLayerSize(childrenLayer)
        val lineLayer = addingLayer + 1

        if (handSide == RelationshipLabel.RIGHT_HAND) {
            if (addingLayer > 0) {
                if (familyTreeDrawer.findStorageSize() > (addingLayer + 1)) {
                    // It works
                    familyTreeDrawer.addFamilyAtLayer(
                        addingLayer + 1,
                        createLineDistance(),
                        null
                    )

                    // Here
                    if (familyTreeDrawer.findStorageLayerSize(lineLayer) > 0) {
                        val addingInd = familyTreeDrawer.addMarriageLineInd(
                            lineLayer, focusedPerson, null
                        )
                        val lineLayerSize = familyTreeDrawer
                            .findStorageLayerSize(lineLayer)
                        val lastLineLayerInd = lineLayerSize - 1

                        if (addingInd > lastLineLayerInd) {
                            // find whether addingInd's element is the marriage line yet
                            if (lineLayerSize < addingInd) {
//                                print("1\n")
                            } else {
                                // Add empty node(s) between the marriage line.
                                val addingMore = addingInd - lineLayerSize
                                for (i in 0 until addingMore + 1) {
                                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                                        addingLayer + 1, addingInd - 1, null, null
                                    )
                                }
                            }
                        }
                    }


                } else if (familyTreeDrawer.findStorageSize() == (addingLayer + 1)) {
                    if (childrenNumb == 1) {
                        familyTreeDrawer.addFamilyNewLayer(
                            singleChildMarriageLine(RelationshipLabel.RIGHT_HAND)
                        )
                    } else {
                        // Find the focusedPerson's index
                        familyTreeDrawer.addFamilyNewLayer(createLineDistance())
                        val personInd = familyTreeDrawer.findPersonIndById(
                            focusedPerson.idCard, addingLayer
                        )
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
                familyTreeDrawer.addFamilyNewLayer(createLineDistance())
            }
        } else {
            // Add line on left hand side
            if (addingLayer > 0) {
                if (addingLayer == 0) {
                    familyTreeDrawer.addFamilyNewLayer(createLineDistance())
                } else {
                    val hasLeftHandSiblings = familyTreeDrawer.hasPeopleOnTheLeft(
                        focusedPerson, addingLayer
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
                        // Add node husband node on the right hand.
                        // or has both siblings on the left and right hands.
                        // Add husband on the right hand by adding empty node(s).
                        familyTreeDrawer.addFamilyAtLayer(
                            addingLayer + 1,
                            createLineDistance(),
                            null
                        )

                        if (familyTreeDrawer.findStorageLayerSize(lineLayer) > 0) {
                            val addingInd = familyTreeDrawer.addMarriageLineInd(
                                lineLayer, focusedPerson, null
                            )
                            val lineLayerSize = familyTreeDrawer
                                .findStorageLayerSize(lineLayer)
                            val lastLineLayerInd = lineLayerSize - 1

                            if (addingInd > lastLineLayerInd) {
                                // find whether addingInd's element is the marriage line yet
                                if (lineLayerSize < addingInd) {
                                    // Add empty node(s)
                                    for (i in 0 until addingInd) {
                                        if (i == (addingInd)) {
                                            familyTreeDrawer.addFamilyAtLayer(
                                                addingLayer + 1,
                                                createLineDistance(),
                                                null
                                            )
                                        } else {
                                            familyTreeDrawer.addFamilyStorageReplaceIndex(
                                                addingLayer + 1, i, null, null
                                            )
                                        }
                                    }
                                } else {
                                    // Add empty node(s) between the marriage line.
                                    val addingMore = addingInd - lineLayerSize
                                    for (i in 0 until addingMore + 1) {
                                        familyTreeDrawer.addFamilyStorageReplaceIndex(
                                            addingLayer + 1, addingInd - 1, null, null
                                        )
                                    }
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
        var resultSpace = ""
        var resultSign = ""

        for (i in 0 until spaceLine.toInt() + 1)
            resultSpace += space

        for (i in 0 until distanceLine.toInt())
            resultSign += sign

        return "$resultSpace$endSign$resultSign$endSign$resultSpace"
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
}
