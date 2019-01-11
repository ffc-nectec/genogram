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
import ffc.genogram.RelationshipLine.Relationship
import ffc.genogram.RelationshipLine.RelationshipLabel
import ffc.genogram.Util.findMovingNodeNumb
import kotlin.math.floor

abstract class Node {

    companion object {
        const val nodeSize = 6.0
        const val nodesDistance = 2.0
        const val nodeBorderSize = 2.0
    }

    val nodeMargin = ((Relationship.spaceLine + Relationship.distanceLine) / 2).toInt()

    abstract fun drawNode(relationLabel: RelationshipLabel?, siblings: Boolean): FamilyTreeDrawer?

    abstract fun getArea(): Double

    private fun setSingleNodePosition(nodeName: String, gender: GenderLabel, siblings: Boolean): String {
        val space = " "
        var resultSpace = ""

        val newNodeName = if (gender == GenderLabel.MALE) {
            createGenderBorder(nodeName, GenderLabel.MALE)
        } else {
            createGenderBorder(nodeName, GenderLabel.FEMALE)
        }

        if (!siblings)
            for (i in 0 until nodeMargin - 1) {
                resultSpace += space
            }

        return resultSpace + newNodeName + resultSpace
    }

    fun findAddingEmptyNodesParent(childrenNumber: Int): Int {
        return if (childrenNumber % 2 == 0)
            childrenNumber / 2 - 1
        else
            (floor(childrenNumber / 2.0) - 1).toInt()
    }

    fun addMoreNodes(
        emptyNodeNumber: Int,
        addingEmptyNodes: Int,
        parentLayer: Int,
        familyTreeDrawer: FamilyTreeDrawer
    ): FamilyTreeDrawer {
        val addMore = Math.abs(addingEmptyNodes - emptyNodeNumber)
        if (addMore > 0) {
            for (i in (parentLayer + 1) downTo 0)
                for (j in 1..addMore)
                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                        i, 0, null, null
                    )
        }

        return familyTreeDrawer
    }

    fun addMiddleChild(
        parent: Person,
        nodeName: String,
        gender: GenderLabel,
        addedPerson: Person,
        siblings: Boolean,
        family: Family,
        familyTreeDrawer: FamilyTreeDrawer,
        bloodFamilyId: MutableList<Int>
    ) {
        // Children or Twin
        val addingLayer = familyTreeDrawer.findStorageSize() - 1
        val parentLayer = familyTreeDrawer.findPersonLayer(parent)

        // Set Person node's indent
        addedPerson.setNodeMargin(siblings)

        // Edit the addingIndex
        val childrenLineLayer = addingLayer - 1
        val childrenLineStorage = familyTreeDrawer.getPersonLayer(childrenLineLayer)
        var childrenLine: ChildrenLine? = null
        childrenLineStorage?.forEachIndexed { index, line ->
            if (line is ChildrenLine) {
                val childrenList: MutableList<Person> = line.childrenList
                childrenList.forEachIndexed { pos, list ->
                    if (list.idCard == addedPerson.idCard) {
                        childrenLine = childrenLineStorage[index] as ChildrenLine
                    }
                }
            }
        }
        val childrenLineInd = familyTreeDrawer.findChildrenLineInd(childrenLine!!, childrenLineLayer)
        // For the 4th Generation, when addingPerson's parent is the only child
        val addingLayerSize = familyTreeDrawer.findStorageLayerSize(addingLayer)

        var sibOrder = parent.getSibOrder(familyTreeDrawer, parentLayer - 1)

        if (familyTreeDrawer.generationNumber(parentLayer) >= 3
            || sibOrder == RelationshipLabel.ONLY_CHILD
        ) {
            childrenLineInd?.let { childrenLineInd ->
                val childrenLineIndSize = familyTreeDrawer.findChildrenLineIndSize(
                    childrenLineLayer, 0, childrenLineInd - 1
                )
                val personLineIndSize = familyTreeDrawer.findPersonIndSize(
                    addingLayer, 0, addingLayerSize
                )
                val lastAddingInd = childrenLineIndSize - (personLineIndSize - addingLayerSize)

                if (childrenLineInd > addingLayerSize + 1) {
                    for (i in addingLayerSize until lastAddingInd)
                        familyTreeDrawer.addFamilyStorageReplaceIndex(
                            addingLayer, i, null, null
                        )
                } else {
                    val anotherParent = addedPerson.findAnotherParent(
                        parent, family
                    )
                    val parentChildren = parent.getChildrenId(anotherParent)
                    var isOldestChild: Boolean
                    if (parentChildren != null && parentChildren.isNotEmpty()) {
                        isOldestChild = parentChildren[0] == addedPerson.idCard
                        val bloodFParent = addedPerson.getBloodFParent(family, bloodFamilyId)
                        val leftParent = childrenLine!!.parentList[0]

                        // Number of person's index in the adding layer
                        var childrenLineIndSize = familyTreeDrawer.findChildrenLineIndSize(
                            childrenLineLayer, 0, childrenLineInd - 1
                        )
                        val personLayerIndSize = familyTreeDrawer.findPersonIndSize(
                            addingLayer, 0, addingLayerSize - 1
                        )

                        var replaceInd =
                            if (leftParent.gender == GenderLabel.MALE) childrenLineIndSize else childrenLineIndSize - 1
                        val addingLayerSizeInd = familyTreeDrawer.findPersonIndSize(addingLayer, 0, addingLayerSize)
                        val endingInd = addingLayerSize + (replaceInd - addingLayerSizeInd)
                        if (isOldestChild && leftParent == bloodFParent) {
                            for (i in addingLayerSize until endingInd)
                                familyTreeDrawer.addFamilyStorageReplaceIndex(
                                    addingLayer, i, null, null
                                )
                        } else if (isOldestChild && leftParent != bloodFParent) {
                            val addingMore = childrenLineIndSize - personLayerIndSize
                            for (i in 0 until addingMore)
                                familyTreeDrawer.addFamilyStorageAtIndex(
                                    addingLayer, addingLayerSize, null, null
                                )
                        }
                    }
                }
            }
        }

        // Add a single child
        familyTreeDrawer.addFamilyAtLayer(
            addingLayer,
            setSingleNodePosition(nodeName, gender, siblings),
            addedPerson
        )

        // Find the indexSize of the children line before compare to the person node size
        // Before adding the addingPerson's childrenLine
        val addingPersonSibList = childrenLine!!.childrenList
        val addingPersonChildrenLine = familyTreeDrawer.findChildrenLine(addingLayer - 1, addedPerson)
        val addingPersonChildrenLineInd = familyTreeDrawer.findChildrenLineInd(
            addingPersonChildrenLine!!, addingLayer - 1
        )
        var childrenLineIndSize = familyTreeDrawer.findChildrenLineIndSize(
            addingLayer - 1, 0, addingPersonChildrenLineInd!! - 1
        )

        // Moving the addingPerson position
        val lastAddedPersonSib = addingPersonSibList[addingPersonSibList.size - 1]
        val firstChildInd = familyTreeDrawer.findPersonInd(addingPersonSibList[0], addingLayer)
        val lastChildInd =
            familyTreeDrawer.findPersonInd(lastAddedPersonSib, addingLayer)
        val addingPersonSibNumb = (lastChildInd - firstChildInd) + 1

        // Adjust the grandparent position
        val leftParentInd = familyTreeDrawer.findPersonInd(parent, parentLayer)
        val addingPersonInd = familyTreeDrawer.findPersonInd(addedPerson, addingLayer)
        val addingMore = leftParentInd - childrenLineIndSize
        var expectingPos: Int

        if (addingPersonSibNumb < 3 && addingMore >= 0) {
            childrenLine = familyTreeDrawer.findChildrenLine(
                addingLayer - 1, addedPerson
            )
            val childrenLineInd = familyTreeDrawer.findChildrenLineInd(
                childrenLine!!, addingLayer - 1
            )
            childrenLineIndSize = familyTreeDrawer.findChildrenLineIndSize(
                addingLayer - 1, 0, childrenLineInd!! - 1
            )
            val singleChildNumb = familyTreeDrawer.findSingleChildNumb(
                addingLayer - 1, 0, childrenLineInd
            )

            expectingPos = if (singleChildNumb == 0)
                childrenLineIndSize
            else
                addingPersonInd + (childrenLineInd - addingPersonInd)

            // Moving the addingPerson Index
            for (i in addingPersonInd until expectingPos) {
                familyTreeDrawer.addFamilyStorageReplaceIndex(
                    addingLayer,
                    i,
                    null,
                    null
                )
            }
        }
    }

    fun separateMidChildren(
        familyTreeDrawer: FamilyTreeDrawer,
        parentLayer: Int
    ) {
        val childrenLineLayer = parentLayer + 2
        val childrenLineSize = familyTreeDrawer.findPersonLayerSize(childrenLineLayer)
        val lineChildren = familyTreeDrawer.getPersonLayer(childrenLineLayer)
        var childrenNumb = 0
        var childNumb = 0
        lineChildren?.forEachIndexed { index, any ->
            if (index < childrenLineSize - 1)
                if (any is ChildrenLine) {
                    if (any.childrenNumb == 1)
                        childNumb++
                    else
                        childrenNumb += any.childrenNumb
                }
        }
    }

    fun separateParentSib(
        familyTreeDrawer: FamilyTreeDrawer,
        parent: Person, // Parent
        addedPerson: Person, // Child
        parentLayer: Int,
        parentInd: Int,
        family: Family,
        bloodFamilyId: MutableList<Int>
    ) {
        val familyTreeDrawer = familyTreeDrawer
        val parentChildren = parent.children
        val isAddedPersonOldest = parentChildren!![0] == addedPerson.idCard

        // Find index the addedPerson will be added.
        val addingLayer = familyTreeDrawer.findStorageSize() - 1
        val addedPersonInd = familyTreeDrawer.findPersonLayerSize(addingLayer)
        var addedPersonIndSize = familyTreeDrawer.findPersonIndSize(
            addingLayer, 0, addedPersonInd - 1
        )
        val addPersonSibOrder = addedPerson.getSibOrder(
            familyTreeDrawer, addingLayer - 1
        )

        // When the addedPerson's parent node is on the left-hand of the addedPerson,
        // the addedPerson's parent's siblings (addedPerson's uncles/aunts) have
        // children equal to less than their parent nodes.
        // Replace an empty node at the addedPerson's parent index.

        // Move the addedPerson's parent index
        val pMarriageLineLayer = parentLayer + 1 // Parents' marriage line
        val pMarriageLineLayerNumb = familyTreeDrawer.findLineNumber(pMarriageLineLayer) - 1

        // Find marriageLineInd
        val anotherParent = addedPerson.findAnotherParent(parent, family)
        val marriageLine = familyTreeDrawer.findMarriageLine(
            pMarriageLineLayer, parent, anotherParent
        )
        val emptyNodeNumbLine = familyTreeDrawer.findNumberOfEmptyNode(pMarriageLineLayer)
        val emptyMidNodeNumbLine = familyTreeDrawer.findNumberOfMidEmptyNode(pMarriageLineLayer)
        val emptyFrontMidNodeNumbLine = emptyNodeNumbLine - emptyMidNodeNumbLine
        val childrenLineLayer = addingLayer - 1
        val lastChildrenLineInd = familyTreeDrawer.findPersonLayerSize(childrenLineLayer) - 1
        var addingInd = (pMarriageLineLayerNumb * 2) + emptyFrontMidNodeNumbLine - 1
        var childrenLineInd = familyTreeDrawer.findChildrenLineIndSize(
            childrenLineLayer, 0, lastChildrenLineInd - 1
        )
        var expectingInd = if (parentChildren!!.size > 3)
            parentInd + addingInd
        else
            parentInd + Math.abs(childrenLineInd - parentInd)

        val leftParent = familyTreeDrawer.findLeftParent(addedPerson, parent, parentLayer, family)
        val leftParentInd = familyTreeDrawer.findPersonInd(leftParent, parentLayer)
        val leftParentIndSize = familyTreeDrawer.findPersonIndSize(
            parentLayer, 0, leftParentInd - 1
        )
        val childrenLine = familyTreeDrawer.findChildrenLine(childrenLineLayer, addedPerson)!!
        childrenLineInd = familyTreeDrawer.findChildrenLineInd(childrenLine, childrenLineLayer)!!
        val childrenLineIndSize =
            familyTreeDrawer.findChildrenLineIndSize(childrenLineLayer, 0, childrenLineInd - 1)

        if (parentInd < addedPersonIndSize
            && (addPersonSibOrder == RelationshipLabel.OLDEST_CHILD
                    || addPersonSibOrder == RelationshipLabel.ONLY_CHILD)
        ) {
            if (leftParentIndSize < childrenLineIndSize
                && familyTreeDrawer.generationNumber(parentLayer) < 3
            ) {
                // move the parent layer
                familyTreeDrawer.movingParentToRightHand(
                    parent,
                    expectingInd,
                    parentInd,
                    parentLayer,
                    marriageLine!!,
                    pMarriageLineLayer,
                    bloodFamilyId
                )
            } else {
                // children line above the addingPerson
                val singleChildNumb = familyTreeDrawer.findSingleChildNumb(
                    addingLayer - 1, 0, childrenLineInd
                )
                val addingTargetInd = addedPersonInd + 1
                val addingTargetIndSize = familyTreeDrawer.findPersonIndSize(
                    addingLayer, 0, addingTargetInd - 1
                )
                var checkingInd = if (addingTargetInd == addingTargetIndSize)
                    leftParentIndSize
                else {
                    addingTargetIndSize - singleChildNumb
                }

                // At parent layer
                val parentLayerSize = familyTreeDrawer.findPersonLayerSize(parentLayer)
                val parentSingleChildNumb = familyTreeDrawer.findSingleChildNumb(
                    parentLayer - 1, 0, parentLayerSize
                )
                checkingInd = when {
                    (parentSingleChildNumb == 0 && singleChildNumb == 0) ->
                        parentInd
                    (parentSingleChildNumb == 0 && singleChildNumb > 0) ->
                        checkingInd - singleChildNumb
                    else ->
                        checkingInd
                }

                // Check the node in the addingPerson at the left-hand parent's ind
                // if it empty, we don't have to move anything
                val layerSize = familyTreeDrawer.findPersonLayerSize(addingLayer)
                var targetObj: Any? = null
                if (checkingInd < layerSize) {
                    targetObj = familyTreeDrawer.getPersonLayerInd(
                        addingLayer, checkingInd
                    )
                }

                if (targetObj is Person) {
                    // move the parent layer
                    val addingEmptyNode = expectingInd - (addedPersonInd - 1)
                    val previousNode = familyTreeDrawer.findPreviousObj(
                        parentLayer, leftParentInd
                    )
                    var preNodeIsSibling = false
                    if (previousNode is Person)
                        preNodeIsSibling = leftParent.isSibling(previousNode, family, familyTreeDrawer)

                    if (previousNode is Person && !preNodeIsSibling) {
                        familyTreeDrawer.separateAddedPersonParentLayer(
                            addedPerson,
                            parent,
                            addingEmptyNode,
                            parentInd,
                            parentLayer,
                            family,
                            bloodFamilyId
                        )
                        /*if (addedPerson.firstname == "Sindy") {
                            print("------ Node 292 ------\n")
                            print("add: ${addedPerson.firstname}\n")
                            print("previousNode: $previousNode\n")
                            print("preNodeIsSibling: $preNodeIsSibling\n")
                            print("...............\n")
                            val canvasB = displayObjectResult(familyTreeDrawer)
                            print(canvasB.toString())
                            print("---------------------------------------\n")
                        }*/

                    } else {
                        // Check the next node
                        val rightParentInd = anotherParent?.let {
                            familyTreeDrawer.findPersonInd(
                                anotherParent, parentLayer
                            )
                        } ?: leftParentInd
                        val nextNode = familyTreeDrawer.findNextPerson(
                            parentLayer, rightParentInd
                        )

                        if (nextNode is Person) {
                            val nextNodeChildrenLine = familyTreeDrawer.findChildrenLine(
                                parentLayer - 1, nextNode
                            )!!
                            val parentChildrenLineInd = familyTreeDrawer.findLineInd(
                                nextNodeChildrenLine, parentLayer - 1
                            )!!
                            /*familyTreeDrawer.moveNodeToRightHand(
                                parentLayer - 1,
                                parentChildrenLineInd,
                                parentChildrenLineInd + 1
                            )*/

                            familyTreeDrawer.separateAddedPersonParentLayer2(
                                addedPerson,
                                parent,
                                nextNode,
                                expectingInd,
                                parentInd,
                                parentLayer,
                                family,
                                bloodFamilyId
                            )
                        } else {
                            familyTreeDrawer.movingParentToRightHand(
                                parent,
                                expectingInd,
                                parentInd,
                                parentLayer,
                                marriageLine!!,
                                pMarriageLineLayer,
                                bloodFamilyId
                            )

                            // Extend parent's children line
                            familyTreeDrawer.updateChildrenLineAtLayer(
                                parent, parentLayer - 1, family, bloodFamilyId
                            )
                        }
                        /*// Check
                        if (addedPerson.firstname == "June") {
                            print("------ Node 441 ------\n")
                            print("add: ${addedPerson.firstname}\n")
                            print("parent: ${parent.firstname}\n")
                            print("...............\n")
                            val canvasB = displayObjectResult(familyTreeDrawer)
                            print(canvasB.toString())
                            print("-------------\n")
                        }*/
                    }
                }
            }

            // AddedPerson's parent adjusting zone.
            // Extend addedPerson's parent children line and grandparent's line
            // Extend the MarriageLineManager of AddedPerson's parent.
            val targetParent: Person = if (anotherParent != null)
                findTargetParent(
                    parentLayer - 1, anotherParent, parent, familyTreeDrawer
                )
            else
                parent

            // Move the parent of parent's position (the layers are above the parent layer)
            // Find whether the addingPerson has grandparent or not
            val hasGrandParent = parentLayer >= 3
            if (hasGrandParent) {
                familyTreeDrawer.adjustUpperLayerPos(
                    addedPerson,
                    targetParent,
                    parentLayer,
                    family,
                    bloodFamilyId
                )
            }
        } else if (isAddedPersonOldest && parentInd >= addedPersonInd) {
            val addingPersonChildrenLineLayer = parentLayer + 2

            // Find the ChildrenLine above the addingPerson
            val childrenLineAddingPerson = familyTreeDrawer.findChildrenLine(
                addingPersonChildrenLineLayer, addedPerson
            )

            // Find the addingPerson's siblings
            val addingPersonSib = childrenLineAddingPerson!!.childrenList!!
            val addingPersonSibNumb = addingPersonSib.size

            var expectingMoreNode = 0
            if (addingPersonSibNumb > 3) {
                expectingMoreNode = findMovingNodeNumb(addingPersonSibNumb)
            }
            if (childrenLineIndSize + expectingMoreNode == parentInd) {
                expectingMoreNode = 0
            }

            // Moving the parent position
            if (expectingMoreNode > 0) {
                for (i in 0 until expectingMoreNode) {
                    for (j in parentLayer + 1 downTo 0) {
                        familyTreeDrawer.addFamilyStorageReplaceIndex(
                            j,
                            parentInd,
                            null,
                            null
                        )
                    }
                }
            }
        }
    }

    fun separateParent4Gen(
        familyTreeDrawer: FamilyTreeDrawer,
        parent: Person,
        focusedPerson: Person,
        addedPerson: Person,
        parentLayer: Int,
        family: Family,
        bloodFamilyId: MutableList<Int>
    ) {
        val childrenLineLayer = parentLayer + 2
        val childrenLine = familyTreeDrawer.findChildrenLine(childrenLineLayer, focusedPerson)!!
        val childrenLineInd = familyTreeDrawer.findChildrenLineInd(childrenLine, childrenLineLayer)!!

        // Check the right-hand element of the childrenLine
        if (childrenLineInd + 1 < familyTreeDrawer.findStorageLayerSize(childrenLineLayer)) {
            val nextElement = familyTreeDrawer.getPersonLayerInd(
                childrenLineLayer, childrenLineInd + 1
            )
            if (nextElement !is EmptyNode) {
                // Rearrange all the layers above the children line
                // Move the parent's children line and the parent's marriage line
                var movingInd = childrenLineInd + 1
                for (i in childrenLineLayer downTo childrenLineLayer - 2) {
                    if (i == parentLayer)
                        movingInd = familyTreeDrawer.findChildrenLineIndSize(
                            childrenLineLayer, 0, childrenLineInd
                        )

                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                        i, movingInd, null, null
                    )
                }

                val bloodParent = focusedPerson.getBloodFParent(family, bloodFamilyId)
                // Adjust all the layer above the parent layer
                familyTreeDrawer.adjustUpperLayerPos(
                    addedPerson,
                    bloodParent,
                    parentLayer,
                    family,
                    bloodFamilyId
                )
            }
        }
    }

    fun moveChildrenLine(
        familyTreeDrawer: FamilyTreeDrawer,
        childrenLineLayer: Int,
        addingEmptyNodes: Int,
        focusedPerson: Person
    ) {
        val emptyNodeCount = familyTreeDrawer.findNumberOfEmptyNodePerson(childrenLineLayer)
        var line: Any? = null
        if (addingEmptyNodes == 0 || emptyNodeCount == 0) {
            line = ChildrenLine()
            val previousChildrenLine = familyTreeDrawer.findChildrenLine(
                childrenLineLayer, focusedPerson!!
            )

            if (previousChildrenLine != null)
                line = previousChildrenLine
        } else if ((Math.abs(addingEmptyNodes - emptyNodeCount) > 0)
            && (emptyNodeCount > 0)
            && (addingEmptyNodes != emptyNodeCount)
            && (addingEmptyNodes > emptyNodeCount)
        ) {
            val preLine = familyTreeDrawer.personFamilyStorage[childrenLineLayer]
            line = preLine[preLine.size - 1] as ChildrenLine
        }

        if (line is ChildrenLine) {
            line.moveChildrenLineSign(
                familyTreeDrawer,
                childrenLineLayer
            )
        }
    }

    fun findPersonSibListIdInd(
        parent: Person?,
        anotherParent: Person?,
        focusedPersonChildren: MutableList<Int>?
    ): MutableList<Any> {
        val result: MutableList<Any> = mutableListOf()
        var addedPersonSibListId: MutableList<Int> = mutableListOf()
        val addedPersonParent: MutableList<Person> = mutableListOf()

        if (parent != null)
            addedPersonParent.add(parent)

        if (focusedPersonChildren != null)
            if (anotherParent != null) {
                addedPersonParent.add(anotherParent)
                val anotherParentChildren = anotherParent.children as MutableList<Int>
                focusedPersonChildren.forEach { fpChild ->
                    anotherParentChildren.forEach { apChild ->
                        if (apChild == fpChild) {
                            addedPersonSibListId.add(apChild)
                        }
                    }
                }
            } else {
                addedPersonSibListId = focusedPersonChildren
            }

        result.add(addedPersonSibListId.toMutableList())
        result.add(addedPersonParent.toMutableList())

        return result
    }

    fun findParentSibList(drawParentSibListId: MutableList<Int>, family: Family): MutableList<Person> {
        val parentSibList: MutableList<Person> = mutableListOf()
        drawParentSibListId.forEach {
            val parentSib = family.findPerson(it)
            parentSibList.add(parentSib!!)
        }

        return parentSibList
    }

    private fun findTargetParentOfParent(
        parentLayer: Int,
        child: Person, // child
        parent: Person, // parent
        family: Family,
        familyTreeDrawer: FamilyTreeDrawer
    ): Person? {
        val targetParent: Person?
        val targetLayer: Int = when (parentLayer) {
            0 -> parentLayer
            else -> parentLayer - 3
        }

        var targetParentInd: Int? = familyTreeDrawer.getGrandParentInd(targetLayer, parent)
        val anotherParent: Person?
        if (targetParentInd == null) {
            anotherParent = child.findAnotherParent(parent, family)
            if (anotherParent != null)
                targetParentInd = familyTreeDrawer.getGrandParentInd(targetLayer, anotherParent)
        }

        targetParent = if (targetParentInd == null) {
            val addPersonFather = child.father
            val addPersonMother = child.mother
            // targetParentId = new FocusedPerson's Id
            val targetParentId = if (parent.idCard == addPersonFather) {
                addPersonMother
            } else {
                addPersonFather
            }

            // targetParent = new FocusedPerson
            familyTreeDrawer.getPersonById(targetParentId!!, targetLayer)
        } else {
            familyTreeDrawer.getPersonLayerInd(targetLayer, targetParentInd) as Person?
        }

        return targetParent
    }

    private fun findTargetParent(
        childrenLineLayer: Int, // ChildrenLine under the parent 1 and parent 2
        parent1: Person, // Parent 1
        parent2: Person, // Parent 2
        familyTreeDrawer: FamilyTreeDrawer
    ): Person {
        var targetPerson: Person = parent1

        // Find the children line
        val childrenLineStorage = familyTreeDrawer.getPersonLayer(childrenLineLayer)

        // Who is in the children line
        childrenLineStorage?.forEachIndexed { index, any ->
            if (any is ChildrenLine)
                any.childrenList.forEach {
                    when (it) {
                        parent2 -> {
                            targetPerson = parent2
                            return@forEach
                        }
                        parent1 -> {
                            targetPerson = parent1
                            return@forEach
                        }
                    }
                }
        }

        return targetPerson
    }

    private fun adjustParentChildrenLinePos(
        familyTreeDrawer: FamilyTreeDrawer,
        addedPerson: Person,
        focusedPerson: Person,
        parent: Person,
        anotherParent: Person?,
        addingEmptyNodes: Int,
        adjustLayer: Int,
        leftParentInd: Int,
        childrenLine: ChildrenLine,
        family: Family,
        bloodFamilyId: MutableList<Int>,
        funLabel: FunLabel
    ) {
        if (addingEmptyNodes > 1) {
            val parentList = childrenLine.parentList
            val targetParent = findTargetParent(
                adjustLayer - 1,
                parentList[0], parentList[1], familyTreeDrawer
            )
            val targetParentInd = familyTreeDrawer.findPersonInd(targetParent, adjustLayer)
            val parentChildrenLine = familyTreeDrawer.findChildrenLine(
                adjustLayer - 1, targetParent
            )
            // Find the parent's sib who is the one that was born before the parent
            var olderSibRank: Int? = null
            parentChildrenLine!!.childrenList.forEachIndexed { index, person ->
                if (person == targetParent)
                    olderSibRank = index - 1
            }

            var movingNode = addingEmptyNodes
            val olderSib: Person?
            if (olderSibRank != null) {
                olderSib = parentChildrenLine.childrenList[olderSibRank!!]
                val olderSibInd = familyTreeDrawer.findPersonInd(olderSib, adjustLayer)
                movingNode = (targetParentInd - olderSibInd) - addingEmptyNodes
            }

            // When the children line of the parent index is greater than the the grandparent's index
            for (i in 0 until movingNode) {
                for (j in adjustLayer downTo adjustLayer - 3)
                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                        adjustLayer,
                        leftParentInd,
                        null,
                        null
                    )
            }
        } else if (addingEmptyNodes == 1) {
            // When the children line of the parent is the same index of the grandparent index.
            when (funLabel) {
                FunLabel.MOVE_PARENT_POSITION -> {
                    // find targetParent for each layer for moving all the layer above
                    val targetPerson = focusedPerson.getTargetParent(family, bloodFamilyId)
                    familyTreeDrawer.moveInd(
                        focusedPerson,
                        targetPerson,
                        adjustLayer,
                        family,
                        addingEmptyNodes,
                        bloodFamilyId
                    )
                }
                FunLabel.ADD_MID_CHILD -> {
                    // adjustLayer = parentLayer
                    val startingLayer = adjustLayer + 3
                    val endingLayer = adjustLayer + 4
                    for (layer in endingLayer - 1 downTo startingLayer) {
                        var movingInd = familyTreeDrawer.findPersonInd(
                            addedPerson, adjustLayer + 2
                        )
                        familyTreeDrawer.addFamilyStorageReplaceIndex(
                            layer,
                            movingInd,
                            null,
                            null
                        )
                    }
                }
            }
        }
    }

    fun movingParentPosition(
        familyTreeDrawer: FamilyTreeDrawer,
        addedPerson: Person,
        focusedPerson: Person,
        parent: Person,
        addingLayer: Int,
        parentLayer: Int,
        family: Family,
        bloodFamilyId: MutableList<Int>
    ) {
        val childrenLineLayer = addingLayer - 1
        // Find the target parent
        val targetPerson = findTargetParent(childrenLineLayer, addedPerson, focusedPerson!!, familyTreeDrawer)
        // Find the children line above the parent layer
        val parentChildrenLine =
            familyTreeDrawer.findChildrenLine(childrenLineLayer, targetPerson) as ChildrenLine

        // Find that line index for using to move the children line parent
        val childrenLineInd: Int?
        val leftParentInd: Int?

        if (parentChildrenLine != null) {
            childrenLineInd = familyTreeDrawer.findChildrenLineInd(parentChildrenLine, childrenLineLayer)

            // Find the parent index
            val leftParent = parentChildrenLine.parentList[0]
            leftParentInd = familyTreeDrawer.findPersonInd(leftParent, parentLayer)

            // Find the focusedPerson sib
            val anotherParent = focusedPerson.findAnotherParent(parent, family)
            val childrenLayer = parentLayer + 2
            val fpSibInd = focusedPerson.findSiblingByDrawer(
                familyTreeDrawer, childrenLayer
            )
            val focusedPersonSibInd = fpSibInd!![1] as MutableList<Int>
            val oldestFocusedInd = focusedPersonSibInd[0]
            val youngestFocusedInd = focusedPersonSibInd[focusedPersonSibInd.size - 1]
            val addingPersonSibNumb = youngestFocusedInd - oldestFocusedInd + 1

            var expectingMoreNode = 0
            if (addingPersonSibNumb > 3) {
                expectingMoreNode = if (addingPersonSibNumb % 2 == 0) (addingPersonSibNumb / 2) - 1
                else
                    (floor(addingPersonSibNumb / 2.0) - 1).toInt()
            }

            // Adjust the above layer position
            val expectingPos = childrenLineInd!! + expectingMoreNode

            if (expectingPos != leftParentInd) {
                val addingEmptyNodes = Math.abs(leftParentInd!! - expectingPos)
                val isAdjustPos = (leftParentInd - childrenLineInd) - 1 != expectingMoreNode

                if (isAdjustPos) {
                    adjustParentChildrenLinePos(
                        familyTreeDrawer,
                        addedPerson,
                        focusedPerson,
                        parent,
                        anotherParent,
                        addingEmptyNodes,
                        parentLayer,
                        leftParentInd,
                        parentChildrenLine,
                        family,
                        bloodFamilyId,
                        FunLabel.MOVE_PARENT_POSITION
                    )
                }

                // Update the children line above the focusedPerson
                parentChildrenLine.extendLine(
                    familyTreeDrawer,
                    childrenLineLayer, // the childrenLine above the parent layer
                    focusedPersonSibInd,
                    family,
                    bloodFamilyId
                )
            }
        }
    }
}
