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

package ffc.genogram.Node

import ffc.genogram.Family
import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.GenderLabel
import ffc.genogram.Person
import ffc.genogram.RelationshipLine.ChildrenLine
import ffc.genogram.RelationshipLine.RelationshipLabel

class MaleNode(
    private var familyTreeDrawer: FamilyTreeDrawer,
    private val addedPerson: Person,
    private var focusedPerson: Person?,
    private var nodeName: String,
    var parent: Person?,
    val family: Family,
    val bloodFamilyId: MutableList<Int>
) : Node() {

    override fun drawNode(relationLabel: RelationshipLabel?, siblings: Boolean): FamilyTreeDrawer {

        if (relationLabel != RelationshipLabel.CHILDREN &&
            relationLabel != RelationshipLabel.TWIN
        ) {
            nodeName = createGenderBorder(nodeName, GenderLabel.MALE)

            if (focusedPerson != null) {
                val addingLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
                val addingInd = familyTreeDrawer.findPersonInd(focusedPerson!!, addingLayer)

                if (focusedPerson!!.gender == GenderLabel.FEMALE) {
                    // Find whether the focusedPerson has any siblings.
                    val leftHandNodes = familyTreeDrawer.hasNodeOnTheLeft(
                        focusedPerson!!, addingLayer
                    )
                    val rightHandNodes = familyTreeDrawer.hasNodeOnTheRight(
                        focusedPerson!!, addingLayer
                    )

                    // Find the focusedPerson sib
                    val focusedSib = focusedPerson!!.findSiblingByDrawer(
                        familyTreeDrawer, addingLayer - 1
                    )
                    val focusedSibObj = focusedSib[0] as MutableList<Person>
                    val hasOlderSib = focusedSibObj[0] == focusedPerson

                    if (!leftHandNodes || hasOlderSib) {
                        // FocusedPerson(the AddedPerson's wife) is the oldest daughter.
                        // Add the AddedPerson at the first index, the male node will be
                        // on the left of the female node, and make indent(s) at the
                        // FocusedPerson's parent layer.
                        if (hasOlderSib && familyTreeDrawer.findPersonInd(focusedPerson!!, addingLayer) != 0) {
                            familyTreeDrawer.replaceFamilyStorageLayer(
                                addingLayer, addingInd - 1, nodeName, addedPerson
                            )
                        } else {
                            familyTreeDrawer.addFamilyStorageAtIndex(
                                addingLayer, addingInd, nodeName, addedPerson
                            )

                            for (i in 0 until addingLayer)
                                familyTreeDrawer.addFamilyStorageReplaceIndex(
                                    i, 0, null, null
                                )
                        }
                    } else if (!rightHandNodes) {
                        // FocusedPerson(the AddedPerson's wife) is the youngest daughter.
                        // (Special case) Add node AddedPerson node on the right of FocusedPerson.
                        familyTreeDrawer.addFamilyAtLayer(addingLayer, nodeName, addedPerson)
                    } else {
                        // FocusedPerson(the AddedPerson's wife) is the middle daughter.
                        // Add husband on the right hand of his wife.
                        familyTreeDrawer.addFamilyStorageReplaceIndex(
                            addingLayer, addingInd - 1, nodeName, addedPerson
                        )

                        // Extend the "MarriageLineManager" of AddedPerson and FocusedPerson.
                        // by adding the empty node(s).
                        // Check AddedPerson's husband index, then check
                        // whether her husband's index is equal to the number of empty node(s).
                        // Find number of marriage line
                        val marriageLineNumb = familyTreeDrawer.findStorageLayerSize(
                            addingLayer + 1
                        )
                        val ownInd = familyTreeDrawer.findPersonInd(addedPerson, addingLayer)
                        if (marriageLineNumb == 1) {
                            // His wife(FocusedPerson) siblings and AddedPerson
                            val emptyNodeNumber = familyTreeDrawer.findNumberOfEmptyNode(addingLayer)
                            val addingEmptyNodes = ownInd - emptyNodeNumber

                            if (ownInd != emptyNodeNumber) {
                                if (addingEmptyNodes > 0) {
                                    for (i in 0..(addingEmptyNodes - 1))
                                        familyTreeDrawer.addFamilyStorageReplaceIndex(
                                            addingLayer + 1,
                                            0, null, null
                                        )
                                }
                            }
                        }
                    }
                    // When the FocusedPerson is the oldest one.
                    // The AddedPerson will be added at the left-hand of the FocusedPerson.
                    // Then we don't change any sign of the line.
                    if (!hasOlderSib) {
                        // Adjust the children line
                        val parentLayer = familyTreeDrawer.findPersonLayer(parent!!)
                        var childrenNumber = familyTreeDrawer.findPersonLayerSize(addingLayer)
                        var childrenLineLayer = addingLayer - 1

                        // Find the focusedPerson's parent
                        val fpParentListInd: MutableList<Int> = mutableListOf()
                        val fpFatherId = focusedPerson?.father
                        val fpMotherId = focusedPerson?.mother
                        var anotherParent: Person? = null

                        if (fpFatherId != null) {
                            val fpFatherInd = familyTreeDrawer.findPersonIndById(fpFatherId, parentLayer)
                            fpParentListInd.add(fpFatherInd)
                        }
                        if (fpMotherId != null) {
                            val fpMotherInd = familyTreeDrawer.findPersonIndById(fpMotherId, parentLayer)
                            fpParentListInd.add(fpMotherInd)
                        }

                        if (parent != null) {
                            if (fpFatherId == parent!!.idCard) {
                                if (fpMotherId != null)
                                    anotherParent = familyTreeDrawer.getPersonById(fpMotherId, parentLayer)
                            } else if (fpMotherId == parent!!.idCard) {
                                if (fpFatherId != null)
                                    anotherParent = familyTreeDrawer.getPersonById(fpFatherId, parentLayer)
                            }
                        }

                        val focusedPersonParent: MutableList<Person> = mutableListOf()
                        focusedPersonParent.add(parent!!)
                        if (anotherParent != null) {
                            focusedPersonParent.add(anotherParent)
                        }

                        // Find children that focusedPerson and anotherParent has together
                        val focusedPersonChildren = focusedPerson!!.children as MutableList<Int>?
                        val addedPersonValues =
                            findPersonSibListIdInd(parent, anotherParent, focusedPersonChildren)
                        val addedPersonSibListId: MutableList<Int> = addedPersonValues[0] as MutableList<Int>
                        val addedPersonParent: MutableList<Person> = addedPersonValues[1] as MutableList<Person>

                        var childrenLine = ChildrenLine()
                        val previousChildrenLine = familyTreeDrawer.findChildrenLine(
                            childrenLineLayer, focusedPerson!!
                        )

                        // Check for update the childrenLine
                        previousChildrenLine?.parentList?.forEach {
                            if (it.idCard != focusedPerson!!.idCard) {
                                return@forEach
                            }
                        }

                        val addedPersonSibList: MutableList<Person> = mutableListOf()
                        if (previousChildrenLine != null) {
                            childrenLine = previousChildrenLine
                        } else {
                            // Create a new children line
                            // Draw a new childrenLine with new parentList, new childrenList, and etc.
                            addedPersonSibListId.forEach {
                                val child = family.findPerson(it)
                                addedPersonSibList.add(child!!)
                            }
                            childrenLine.drawLine(addedPersonSibListId.size, addedPersonParent, addedPersonSibList)
                        }

                        // FocusedPerson's siblings
                        val childrenListId = parent!!.children!!
                        val childrenListInd: MutableList<Int> = mutableListOf()
                        childrenListId.forEach { id ->
                            childrenListInd.add(
                                familyTreeDrawer.findPersonIndById(
                                    id, childrenLineLayer + 1
                                )
                            )
                        }

                        // Extend the MarriageLineManager of AddedPerson's parent.
                        var childrenIndLength = childrenListInd[childrenListInd.size - 1] - childrenListInd[0] + 1
                        if (childrenIndLength > 3) {
                            movingParentPosition(
                                familyTreeDrawer,
                                addedPerson, focusedPerson!!, parent!!, addingLayer, parentLayer, family
                            )

                            var startInd = childrenListInd[0]
                            var parentInd = familyTreeDrawer.findPersonInd(parent!!, parentLayer)
                            if (!rightHandNodes) {
                                // Extend the CHILDREN Line the top layer of the AddedPerson.
                                // When the AddedPerson is added on the left-hand of this wife (FocusedPerson).
                                // Check an empty node.
                                if ((startInd != 0) && (parentInd > startInd)) {
                                    // Moving the children line by adding an empty node.
                                    familyTreeDrawer.replaceFamilyStorageLayer(
                                        childrenLineLayer, 0, null, null
                                    )

                                    // Extend the children line.
                                    childrenNumber = familyTreeDrawer.findPersonLayerSize(addingLayer)
                                    if (addingInd != 0)
                                        childrenNumber -= 2
                                    else
                                        childrenNumber = childrenNumber - childrenListInd.size + 1

                                    // String Visualization
                                    val expectedLength = familyTreeDrawer.childrenLineLength(childrenNumber)
                                    val extendedLine = familyTreeDrawer.extendLine(
                                        expectedLength,
                                        childrenListInd,
                                        parentInd
                                    )

                                    childrenLine.extendLine(
                                        familyTreeDrawer,
                                        addingLayer - 1,
                                        childrenListInd
                                    )

                                    familyTreeDrawer.replaceFamilyStorageLayer(
                                        childrenLineLayer, startInd, extendedLine, childrenLine
                                    )
                                }
                            } else {
                                // Middle Child
                                startInd = childrenListInd[0]
                                parentInd = familyTreeDrawer.findPersonInd(parent!!, parentLayer)

                                if ((startInd != 0) && (parentInd > startInd)) {
                                    startInd = parentInd - startInd

                                    // Moving the children line by adding an empty node.
                                    familyTreeDrawer.replaceFamilyStorageLayer(
                                        childrenLineLayer, 0, null, null
                                    )
                                    childrenNumber--
                                }

                                // Extend the children line.
                                // String Visualization
                                val midEmptyNode = familyTreeDrawer.findNumberOfMidEmptyNodePerson(addingLayer)
                                startInd -= midEmptyNode

                                childrenLineLayer = addingLayer - 1
                                val addedPersonSib = familyTreeDrawer.findParentSibIdInd(
                                    addedPerson, parent!!, parentLayer
                                )

                                // Object Visualization
                                childrenLine.extendLine(
                                    familyTreeDrawer,
                                    childrenLineLayer,
                                    addedPersonSib[1] //childrenListInd
                                )
                            }
                        }
                    }
                }
            } else {
                familyTreeDrawer.addFamilyLayer(nodeName, addedPerson)
            }
        } else {
            // Check
            /*if (addedPerson.firstname == "Sam") {
                print("------ Male 1 ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("-------------\n")
            }*/

            // Children or Twin
            val parentLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
            val parentInd = familyTreeDrawer.findPersonInd(focusedPerson!!, parentLayer)

            // Separate AddedPerson and their cousins by adding empty node(s).
            separateMidChildren(familyTreeDrawer, parentLayer)

            // Check
            /*if (addedPerson.firstname == "Sam") {
                print("------ After separateMidChildren ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("-------------\n")
            }*/

            // Separate AddedPerson's parent from their uncles/aunts by adding empty node(s).
            separateParentSib(
                familyTreeDrawer,
                focusedPerson!!,
                addedPerson,
                parentLayer,
                parentInd,
                family,
                bloodFamilyId
            )

            // Check
            /*if (addedPerson.firstname == "Sam") {
                print("------ After separateParentSib ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("-------------\n")
            }*/

            // Add a single child
            addMiddleChild(
                focusedPerson!!,
                nodeName,
                GenderLabel.MALE,
                addedPerson,
                siblings,
                family,
                familyTreeDrawer,
                bloodFamilyId
            )

            // Check
            /*if (addedPerson.firstname == "Sam") {
                print("------ After Add a single child ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("-------------\n")
            }*/
        }

        return familyTreeDrawer
    }

    override fun getArea(): Double = nodeSize * nodeSize
}
