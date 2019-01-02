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
import ffc.genogram.RelationshipLine.RelationshipLabel
import kotlin.math.PI

class FemaleNode(
    var familyTreeDrawer: FamilyTreeDrawer,
    private val addedPerson: Person,
    var focusedPerson: Person?,
    var nodeName: String,
    var parent: Person?,
    val family: Family,
    val bloodFamilyId: MutableList<Int>
) : Node() {

    override fun drawNode(relationLabel: RelationshipLabel?, siblings: Boolean): FamilyTreeDrawer {

        if (relationLabel != RelationshipLabel.CHILDREN &&
            relationLabel != RelationshipLabel.TWIN
        ) {
            val addingLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
            nodeName = createGenderBorder(nodeName, GenderLabel.FEMALE)

            // Check
            /*if (addedPerson.firstname == "Cara") {
                print("------ Female ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("---------------------------------------\n")
            }*/

            if (focusedPerson != null) {
                // Find layer of AddedPerson's siblings
                val childrenLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
                val childrenLineLayer = childrenLayer - 1

                // Find whether the focusedPerson has any siblings.
                val hasRightHandPeople = familyTreeDrawer.hasNodeOnTheRight(
                    focusedPerson!!,
                    addingLayer
                )

                // Find the focusedPerson sib
                val focusedSib = focusedPerson!!.findSiblingByDrawer(
                    familyTreeDrawer, childrenLineLayer
                )
                var sibInd: MutableList<Int>? = null
                focusedSib?.let {
                    focusedSib?.let {
                        sibInd = focusedSib[1] as MutableList<Int>
                    }
                }

                // focusedPerson isn't a youngest children
                if (hasRightHandPeople) {
                    // FocusedPerson(addedPerson's husband) has older siblings
                    // Reorder the array's position
                    var addingInd = familyTreeDrawer.findPersonInd(
                        focusedPerson!!, addingLayer
                    )

                    // If the addingInd is the empty node, it'll use replaceFamilyStorageLayer()
                    val isEmptyNode =
                        familyTreeDrawer.getPersonLayerInd(addingLayer, addingInd + 1) is EmptyNode

                    if (isEmptyNode) {
                        familyTreeDrawer.replaceFamilyStorageLayer(
                            addingLayer, addingInd + 1, nodeName, addedPerson
                        )
                    } else {
                        // The next person of the focusedPerson's youngest sib
                        val youngestSibInd = sibInd?.let {
                            sibInd!![sibInd!!.size - 1]
                        }!!
                        val nextPerson = familyTreeDrawer.findNextPerson(addingLayer, youngestSibInd)
                        if (nextPerson != null) {
                            if (youngestSibInd < familyTreeDrawer.findStorageLayerSize(addingLayer)) {

                                // Work!
                                /*familyTreeDrawer.addFamilyStorageReplaceIndex(
                                    addingLayer, addingInd, nodeName, addedPerson
                                )*/

                                val emptyNodeInd = youngestSibInd + 1
                                val isEmptyNode =
                                    familyTreeDrawer.getPersonLayerInd(addingLayer, emptyNodeInd) is EmptyNode
                                if (isEmptyNode) {
                                    // move the younger sib of the focusedPerson
                                    familyTreeDrawer.moveNodeToRightHand(
                                        addingLayer, addingInd + 1, emptyNodeInd
                                    )
                                    // replace the addedPerson at the emptyNode swapped
                                    familyTreeDrawer.replaceFamilyStorageLayer(
                                        addingLayer, addingInd + 1, nodeName, addedPerson
                                    )
                                } else {
                                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                                        addingLayer, addingInd, nodeName, addedPerson
                                    )
                                }
                            }
                        } else {
                            familyTreeDrawer.addFamilyStorageReplaceIndex(
                                addingLayer, addingInd, nodeName, addedPerson
                            )
                        }
                    }

                    // Arrange the previous layer position
                    if (familyTreeDrawer.generationNumber(addingLayer) >= 3) {
                        val parentLayer = addingLayer - 3

                        // Separate AddedPerson's parent from their uncles/aunts by adding empty node(s).
                        // When the person in the 4th generation get married
                        if (parent != null && focusedPerson != null) {
                            // Extend only their parent layer
                            separateParent4Gen(
                                familyTreeDrawer,
                                parent!!,
                                focusedPerson!!,
                                addedPerson,
                                parentLayer,
                                family,
                                bloodFamilyId
                            )
                        }
                    }

                    // AddedPerson has no parents, focus only on draw "MarriageLineManager"
                    // Find AddedPerson's parent index
                    val parentLayer = familyTreeDrawer.findPersonLayer(parent!!)

                    // Find the focusedPerson sib
                    val focusedSib = focusedPerson!!.findSiblingByDrawer(
                        familyTreeDrawer, childrenLineLayer
                    )
                    var sibInd: MutableList<Int> = focusedSib?.let {
                        focusedSib[1] as MutableList<Int>
                    }!!

                    var childrenNumber = if (sibInd.size == 1) 1
                    else
                        sibInd[sibInd.size - 1] - sibInd[0] + 1

                    if (childrenNumber >= 3) {
                        // Extend the MarriageLineManager of AddedPerson's parent.
                        movingParentPosition(
                            familyTreeDrawer,
                            addedPerson,
                            focusedPerson!!,
                            parent!!,
                            addingLayer,
                            parentLayer,
                            family,
                            bloodFamilyId
                        )

                        // Extend the children line
                        var childrenLine = familyTreeDrawer.findChildrenLine(
                            childrenLineLayer, focusedPerson!!
                        )!!
                        val childrenLineInd = familyTreeDrawer.findChildrenLineInd(
                            childrenLine, childrenLineLayer
                        )!!

                        /*if (addedPerson.firstname == "Cara") {
                            print("------ Female 114 ------\n")
                            print("add: ${addedPerson.firstname}\n")
                            print("...............\n")
                            val canvasB = displayObjectResult(familyTreeDrawer)
                            print(canvasB.toString())
                            print("---------------------------------------\n")
                        }*/

                        childrenLine.extendLine(
                            familyTreeDrawer,
                            addingLayer - 1,
                            sibInd,
                            family,
                            bloodFamilyId
                        )

                        /*if (addedPerson.firstname == "Cara") {
                            print("------ Female 114 ------\n")
                            print("add: ${addedPerson.firstname}\n")
                            print("...............\n")
                            val canvasB = displayObjectResult(familyTreeDrawer)
                            print(canvasB.toString())
                            print("---------------------------------------\n")
                        }*/
                    }
                } else if (!hasRightHandPeople) {
                    // When AddedPerson's husband is the youngest children.
                    // Add AddedPerson at the end of the layer.
                    // No reorder the array's position, only extend the line.
                    familyTreeDrawer.addFamilyAtLayer(addingLayer, nodeName, addedPerson)

                    // Extend "MarriageLineManager" of the FocusedPerson's parents.
                    // Extend by adding the empty node(s).
                    val childrenNumber = familyTreeDrawer.findPersonLayerSize(childrenLayer)
                    val childrenNumber2 = focusedPerson!!.findSiblingByParent(family).size

                    if (parent != null) {
                        val parentLayer = familyTreeDrawer.findPersonLayer(parent!!)
                        val emptyParentNodeNumber = familyTreeDrawer.findNumberOfEmptyNode(parentLayer)
                        val addingEmptyNodes = findAddingEmptyNodesParent(childrenNumber)

                        if (childrenNumber2 > 3)
                            familyTreeDrawer = addMoreNodes(
                                emptyParentNodeNumber, addingEmptyNodes, parentLayer, familyTreeDrawer
                            )

                        // Find index of AddedPerson's siblings
                        val childrenListId = parent!!.children!!
                        val childrenListInd: MutableList<Int> = mutableListOf()
                        childrenListId.forEach { id ->
                            childrenListInd.add(
                                familyTreeDrawer.findPersonIndById(
                                    id, childrenLayer
                                )
                            )
                        }

                        // TODO: Delete
                        // Extend the CHILDREN Line the top layer of the AddedPerson.
                        // When the AddedPerson is added on the left-hand of this wife (FocusedPerson).
                        // Move children sign
                        moveChildrenLine(
                            familyTreeDrawer,
                            childrenLineLayer,
                            addingEmptyNodes,
                            focusedPerson!!
                        )
                    }
                }
            } else {
                familyTreeDrawer.addFamilyLayer(nodeName, addedPerson)
            }
        } else {
            // Check
            /*if (addedPerson.firstname == "Jane") {
                print("------ Started ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("---------------------------------------\n")
            }*/

            // Children or Twin
            val parentLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
            val parentInd = familyTreeDrawer.findPersonInd(focusedPerson!!, parentLayer)

            // Separate AddedPerson and their cousins by adding empty node(s).
            separateMidChildren(familyTreeDrawer, parentLayer)

            // Check
            /*if (addedPerson.firstname == "Jane") {
                print("------ After SeparateMidChildren ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("---------------------------------------\n")
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
            /*if (addedPerson.firstname == "Kitty") {
                print("------ After SeparateParentSib ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("---------------------------------------\n")
            }*/

            // Add a single child
            addMiddleChild(
                focusedPerson!!,
                nodeName,
                GenderLabel.FEMALE,
                addedPerson,
                siblings,
                family,
                familyTreeDrawer,
                bloodFamilyId
            )

            // Check
            /*if (addedPerson.firstname == "Lucy") {
                print("------ After AddMiddleChild ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("---------------------------------------\n")
            }*/
        }

        return familyTreeDrawer
    }

    override fun getArea(): Double = PI * Math.pow(nodeSize, 2.0)
}
