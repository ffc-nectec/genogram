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
        focusedPerson: Person,
        nodeName: String,
        gender: GenderLabel,
        addedPerson: Person,
        siblings: Boolean,
        family: Family,
        familyTreeDrawer: FamilyTreeDrawer
    ) {
        // Children or Twin
        val addingLayer = familyTreeDrawer.findStorageSize() - 1
        val parentLayer = familyTreeDrawer.findPersonLayer(focusedPerson)

        // Set Person node's indent
        addedPerson.setNodeMargin(siblings)

        // Test Here
        // Edit the addingIndex
        val childrenLineLayer = addingLayer - 1
        val childrenLineStorage = familyTreeDrawer.getPersonLayer(childrenLineLayer)
        var childrenLine: ChildrenLine? = null
        childrenLineStorage.forEachIndexed { index, line ->
            if (line is ChildrenLine) {
                val childrenList: MutableList<Person> = line.childrenList
                childrenList.forEachIndexed { pos, list ->
                    if (list.idCard == addedPerson.idCard) {
                        childrenLine = childrenLineStorage[index] as ChildrenLine
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
        val leftParentInd = familyTreeDrawer.findPersonInd(focusedPerson, parentLayer)
        val addingPersonInd = familyTreeDrawer.findPersonInd(addedPerson, addingLayer)
        val addingMore = leftParentInd - childrenLineIndSize
        var expectingPos = addingPersonInd + addingMore

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
        lineChildren.forEachIndexed { index, any ->
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
        focusedPerson: Person,
        addedPerson: Person,
        parentLayer: Int,
        parentInd: Int,
        family: Family
    ) {
        val familyTreeDrawer = familyTreeDrawer
        val parentChildren = focusedPerson.children
        val isAddedPersonOldest = parentChildren!![0] == addedPerson.idCard.toInt()

        // Find index the addedPerson will be added.
        val addingLayer = familyTreeDrawer.findStorageSize() - 1
        val addedPersonInd = familyTreeDrawer.findPersonLayerSize(addingLayer)

        if (parentInd < addedPersonInd && isAddedPersonOldest) {
            // When the addedPerson's parent node is on the left-hand of the addedPerson,
            // the addedPerson's parent's siblings (addedPerson's uncles/aunts) have
            // children equal to less than their parent nodes.
            // Replace an empty node at the addedPerson's parent index.

            // Move the addedPerson's parent index
            val marriageLineLayer = parentLayer + 1
            val marriageLineNumb = familyTreeDrawer.findLineNumber(marriageLineLayer) - 1

            // Find marriageLineInd
            val anotherParent = addedPerson.findAnotherParent(focusedPerson, family)
            val marriageLine = familyTreeDrawer.findMarriageLine(
                marriageLineLayer, focusedPerson, anotherParent
            )
            val marriageLineInd = familyTreeDrawer.findLineInd(marriageLine!!, marriageLineLayer)

            val emptyNodeNumbLine = familyTreeDrawer.findNumberOfEmptyNode(marriageLineLayer)
            val emptyMidNodeNumbLine = familyTreeDrawer.findNumberOfMidEmptyNode(marriageLineLayer)
            val emptyFrontMidNodeNumbLine = emptyNodeNumbLine - emptyMidNodeNumbLine
            val childrenLineLayer = addingLayer - 1
            val lastChildrenLineInd = familyTreeDrawer.findPersonLayerSize(childrenLineLayer) - 1
            val addingInd = (marriageLineNumb * 2) + emptyFrontMidNodeNumbLine - 1
            val childrenLineInd = familyTreeDrawer.findChildrenLineIndSize(
                childrenLineLayer, 0, lastChildrenLineInd - 1
            )
            val expectingInd = if (parentChildren.size > 3)
                parentInd + addingInd
            else
                parentInd + Math.abs(childrenLineInd - parentInd)

            for (layer in parentLayer until marriageLineLayer + 1) {
                val movingInd = if (layer == parentLayer) {
                    parentInd
                } else {
                    marriageLineInd
                }

                for (index in parentInd until expectingInd) {
                    if (movingInd != null) {
                        familyTreeDrawer.addFamilyStorageReplaceIndex(
                            layer,
                            movingInd,
                            null,
                            null
                        )
                    }
                }
            }

            // AddedPerson's parent adjusting zone.
            // Extend addedPerson's parent children line and grandparent's line
            // Extend the MarriageLineManager of AddedPerson's parent.
            val parentLineLayer = parentLayer - 1

            // Find the targetParent between parent1 and parent2
            // val anotherParent = findAnotherParent(addedPerson, focusedPerson, family)
            val targetPerson: Person = if (anotherParent != null)
                findTargetParent(
                    parentLayer - 1, anotherParent!!, focusedPerson!!, familyTreeDrawer
                )
            else
                focusedPerson

            // Object Visualization
            var childrenLine = ChildrenLine()
            val previousChildrenLine = familyTreeDrawer.findChildrenLine(
                parentLineLayer, targetPerson
            )

            if (previousChildrenLine != null) {
                childrenLine = previousChildrenLine
            }

            // Update drawParentSibListInd
            val childrenParentLineLayer = parentLayer - 1
            val parentSib = findParentSibIdInd(
                familyTreeDrawer,
                addedPerson,
                targetPerson!!,
                parentLayer
            )

            val parentSibId = parentSib[0]
            val parentSibInd = parentSib[1]

            // Move the parent of parent's position (the layers are above the parent layer)
            // Find whether the addingPerson has grandparent or not
            val hasGrandParent = parentLayer >= 3

            if (hasGrandParent) {
                // Find the left-hand parent of parent's addingPerson
                val grandParentLayer = parentLayer - 3
                val leftGrandPa = childrenLine.parentList[0]
                val leftGrandPaInd = familyTreeDrawer.findPersonInd(leftGrandPa, grandParentLayer)

                // Find how many index leftGrandPaInd has to move
                val parentSibNumb = parentSibId.size
                val parentSibIndLength = parentSibInd[parentSibNumb - 1] - parentSibInd[0] + 1
                var expectingMoreNode = 0
                if (parentSibIndLength > 3)
                    expectingMoreNode = if (parentSibIndLength % 2 == 0) (parentSibIndLength / 2) - 1
                    else
                        (floor(parentSibIndLength / 2.0) - 1).toInt()

                // Find expecting the parent of the parent position
                val expectingPos = parentSibInd[0] + expectingMoreNode
                val addingEmptyNodes = Math.abs(leftGrandPaInd - expectingPos)

                // Moving the parent position
                if (addingEmptyNodes > 0) {
                    for (i in 0 until addingEmptyNodes) {
                        for (j in grandParentLayer + 1 downTo 0) {
                            familyTreeDrawer.addFamilyStorageReplaceIndex(
                                j,
                                leftGrandPaInd,
                                null,
                                null
                            )
                        }
                    }
                }

                childrenLine.extendLine(
                    familyTreeDrawer,
                    childrenParentLineLayer, // the childrenLine above the parent layer
                    parentSibInd
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
                expectingMoreNode = if (addingPersonSibNumb % 2 == 0) (addingPersonSibNumb / 2) - 1
                else
                    (floor(addingPersonSibNumb / 2.0) - 1).toInt()
            }

            // Find parent index
            val childrenLineLayer = addingLayer - 1
            val parentInd = familyTreeDrawer.findPersonInd(focusedPerson, parentLayer)
            val childrenLine = familyTreeDrawer.findChildrenLine(childrenLineLayer, addedPerson)
            val childrenLineInd = familyTreeDrawer.findChildrenLineInd(childrenLine!!, childrenLineLayer)
            val childrenLineIndSize = familyTreeDrawer.findChildrenLineIndSize(
                addingLayer - 1, 0, childrenLineInd!!
            ) - addingPersonSib.size
            if (childrenLineIndSize + expectingMoreNode == parentInd) {
                expectingMoreNode = 0
            }

            // Moving the parent position
            if (expectingMoreNode > 0) {
                for (i in 0 until expectingMoreNode) {
                    for (j in parentLayer + 1 downTo 0) {
                        // TODO: Find index for moving
                        var movingInd = parentInd
                        if (j % 2 == 0) {
                            // TODO: Find the parent of the
                        }

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

    fun getLineType(
        familyTreeDrawer: FamilyTreeDrawer,
        childrenLineLayer: Int,
        addingEmptyNodes: Int,
        extraNode: Int,
        childrenListId: List<Int>,
        focusedPerson: Person
    ): Any? {
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

        return line
    }

    private fun getGrandParentInd(
        familyTreeDrawer: FamilyTreeDrawer,
        grandParentLayer: Int,
        focusedPerson: Person
    ): Int? {
        val grandFatherId = focusedPerson.father
        val grandMotherId = focusedPerson.mother
        var grandFatherInd: Int? = null
        var grandMotherInd: Int? = null
        var targetParentInd: Int? = null

        if (grandFatherId != null)
            grandFatherInd = familyTreeDrawer.findPersonIndById(grandFatherId, grandParentLayer)

        if (grandMotherId != null)
            grandMotherInd = familyTreeDrawer.findPersonIndById(grandMotherId, grandParentLayer)

        if (grandFatherInd != null && grandMotherInd != null)
            targetParentInd = Math.min(grandFatherInd, grandMotherInd)
        else if (grandFatherInd != null)
            targetParentInd = grandFatherInd
        else if (grandMotherInd != null)
            targetParentInd = grandMotherInd

        return targetParentInd
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

    fun findParentSibIdInd(
        familyTreeDrawer: FamilyTreeDrawer,
        addedPerson: Person,
        parent: Person,
        parentLayer: Int
    ): MutableList<MutableList<Int>> {
        val drawParentSibList: MutableList<MutableList<Int>> = mutableListOf()
        val drawParentSibListId: MutableList<Int> = mutableListOf()
        val drawParentSibListInd: MutableList<Int> = mutableListOf()
        val parentLayer1: Int = when (parentLayer) {
            0 -> parentLayer
            else -> parentLayer - 3
        }
        val parentLayer2: Int = when (parentLayer) {
            0 -> parentLayer + 3
            else -> parentLayer
        }

        var targetParentInd: Int? = getGrandParentInd(familyTreeDrawer, parentLayer1, parent)

        if (targetParentInd == null && parentLayer1 > 0) {
            val addPersonFather = addedPerson.father
            val addPersonMother = addedPerson.mother
            // targetParentId = new FocusedPerson's Id
            val targetParentId = if (parent.idCard == addPersonFather) {
                addPersonMother
            } else {
                addPersonFather
            }

            // targetParent = new FocusedPerson
            val targetPerson = familyTreeDrawer.getPersonById(targetParentId!!, parentLayer)
            targetParentInd = getGrandParentInd(familyTreeDrawer, parentLayer1, targetPerson!!)
        } else if (targetParentInd == null && parentLayer1 == 0) {
            // Use in "FemaleNode"
            targetParentInd = familyTreeDrawer.findPersonInd(parent, parentLayer)
        }

        val grandParent: Person?
        grandParent = familyTreeDrawer.getPersonLayerInd(
            parentLayer1, targetParentInd!!
        ) as Person

        val parentSibListId = grandParent!!.children!!
        val childrenParentLayer = familyTreeDrawer.getPersonLayer(parentLayer2)
        childrenParentLayer.forEachIndexed { index, element ->
            if (element is Person) {
                parentSibListId.forEach { id ->
                    if (element.idCard == id) {
                        drawParentSibListId.add(id)
                    }
                }
            }
        }

        drawParentSibListId.forEach { id ->
            drawParentSibListInd.add(
                familyTreeDrawer.findPersonIndById(
                    id, parentLayer2
                )
            )
        }
        drawParentSibList.add(drawParentSibListId)
        drawParentSibList.add(drawParentSibListInd)

        return drawParentSibList
    }

    fun findParentSibList(drawParentSibListId: MutableList<Int>, family: Family): MutableList<Person> {
        val parentSibList: MutableList<Person> = mutableListOf()
        drawParentSibListId.forEach {
            val parentSib = family.findPerson(it)
            parentSibList.add(parentSib!!)
        }

        return parentSibList
    }

    private fun findTargetParent(
        parentLayer: Int,
        addedPerson: Person, // child
        focusedPerson: Person, // parent
        family: Family,
        familyTreeDrawer: FamilyTreeDrawer
    ): Person? {
        val targetParent: Person?
        val targetLayer: Int = when (parentLayer) {
            0 -> parentLayer
            else -> parentLayer - 3
        }

        var targetParentInd: Int? = getGrandParentInd(familyTreeDrawer, targetLayer, focusedPerson)
        val anotherParent: Person?
        if (targetParentInd == null) {
            anotherParent = addedPerson.findAnotherParent(focusedPerson, family)
            if (anotherParent != null)
                targetParentInd = getGrandParentInd(familyTreeDrawer, targetLayer, anotherParent)
        }

        targetParent = if (targetParentInd == null) {
            val addPersonFather = addedPerson.father
            val addPersonMother = addedPerson.mother
            // targetParentId = new FocusedPerson's Id
            val targetParentId = if (focusedPerson.idCard == addPersonFather) {
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
        addedPerson: Person, // Parent 1
        focusedPerson: Person, // Parent 2
        familyTreeDrawer: FamilyTreeDrawer
    ): Person {
        var targetPerson: Person = addedPerson

        // Find the children line
        val childrenLineStorage = familyTreeDrawer.getPersonLayer(childrenLineLayer)

        // Who is in the children line
        childrenLineStorage.forEachIndexed { index, any ->
            if (any is ChildrenLine)
                any.childrenList.forEach {
                    when (it) {
                        focusedPerson -> {
                            targetPerson = focusedPerson
                            return@forEach
                        }
                        addedPerson -> {
                            targetPerson = addedPerson
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
        addingEmptyNodes: Int,
        adjustLayer: Int,
        leftParentInd: Int,
        childrenLine: ChildrenLine,
        family: Family,
        funLabel: FunLabel
    ) {

        if (addingEmptyNodes > 1) {
            // Find the first parent sib index
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
            // When the children line of the parent is the same index of the grandparent index
            // Then more than one layer will be moved.
            // Find the parent who is the blood family

            // adjustLayer = parentLayer when it's from addMiddleChild()
            val targetPerson: Person? = when (funLabel) {
                FunLabel.MOVE_PARENT_POSITION -> {
                    findTargetParent(
                        adjustLayer, addedPerson, focusedPerson, family, familyTreeDrawer
                    )
                }
                FunLabel.ADD_MID_CHILD -> {
                    focusedPerson
                }
            }

            // Find the children line that has the targetParent
//            val childrenLine: ChildrenLine?
            var childrenLineInd: Int? = null
            if (targetPerson != null) {
                val findingPerson: Person?
                val parentChildrenLine: Int
                when (adjustLayer) {
                    0 -> {
                        parentChildrenLine = adjustLayer + 2
                        findingPerson = focusedPerson
                    }
                    else -> {
                        parentChildrenLine = adjustLayer - 1
                        findingPerson = targetPerson
                    }
                }
                val childrenLineTmp = familyTreeDrawer.findChildrenLine(
                    parentChildrenLine,
                    findingPerson
                )

                if (childrenLineTmp != null)
                    childrenLineInd = familyTreeDrawer.findChildrenLineInd(
                        childrenLineTmp, parentChildrenLine
                    )
            }

            val startingLayer: Int
            val endingLayer: Int
            when (funLabel) {
                FunLabel.MOVE_PARENT_POSITION -> {
                    startingLayer = when (adjustLayer) {
                        0 -> adjustLayer
                        else -> adjustLayer - 3
                    }
                    endingLayer = when (adjustLayer) {
                        0 -> adjustLayer + 2
                        else -> adjustLayer + 2
                    }
                }

                FunLabel.ADD_MID_CHILD -> {
                    // adjustLayer = parentLayer
                    startingLayer = adjustLayer + 3
                    endingLayer = adjustLayer + 4
                }
            }

            for (j in startingLayer until endingLayer) {
                var movingInd: Int = leftParentInd

                when (funLabel) {
                    FunLabel.MOVE_PARENT_POSITION -> {
                        movingInd = leftParentInd
                        if (j >= adjustLayer)
                            movingInd = leftParentInd
                        else if (j < adjustLayer && j >= adjustLayer - 3 && childrenLineInd != null)
                            movingInd = childrenLineInd!!
                    }
                    FunLabel.ADD_MID_CHILD -> {
                        movingInd = familyTreeDrawer.findPersonInd(addedPerson, adjustLayer + 2)
                    }
                }


                familyTreeDrawer.addFamilyStorageReplaceIndex(
                    j,
                    movingInd,
                    null,
                    null
                )
            }
        }
    }

    fun findPersonSib(
        familyTreeDrawer: FamilyTreeDrawer,
        addedPerson: Person,
        childrenLineLayer: Int
    ): MutableList<Person>? {
        var childrenList: ArrayList<Person>?
        val childrenLineStorage = familyTreeDrawer.getPersonLayer(childrenLineLayer)
        var childrenLine: ChildrenLine? = null
        childrenLineStorage.forEachIndexed { index, line ->
            if (line is ChildrenLine) {
                childrenList = line.childrenList
                childrenList!!.forEachIndexed { pos, list ->
                    if (list.idCard == addedPerson.idCard) {
                        childrenLine = childrenLineStorage[index] as ChildrenLine
                    }
                }
            }
        }

        return childrenLine!!.childrenList
    }

    fun movingParentPosition(
        familyTreeDrawer: FamilyTreeDrawer,
        addedPerson: Person,
        focusedPerson: Person,
        parent: Person,
        addingLayer: Int,
        parentLayer: Int,
        family: Family
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
            val focusedPersonSib = findParentSibIdInd(
                familyTreeDrawer, focusedPerson!!, parent!!, parentLayer
            )
            val focusedPersonSibInd = focusedPersonSib[1]
            val oldestFocusedInd = focusedPersonSibInd[0]
            val youngestFocusedInd = focusedPersonSibInd[focusedPersonSibInd.size - 1]
            val addingPersonSibNumb = youngestFocusedInd - oldestFocusedInd + 1

            var expectingMoreNode = 0
            if (addingPersonSibNumb > 3)
                expectingMoreNode = if (addingPersonSibNumb % 2 == 0) (addingPersonSibNumb / 2) - 1
                else
                    (floor(addingPersonSibNumb / 2.0) - 1).toInt()


            // Adjust the grandparent position
            val expectingPos = childrenLineInd!! + expectingMoreNode
            if (expectingPos != leftParentInd) {
                val addingEmptyNodes = Math.abs(leftParentInd!! - expectingPos)
                val isAdjustPos = (leftParentInd - childrenLineInd) - 1 != expectingMoreNode

                if (isAdjustPos) {
                    adjustParentChildrenLinePos(
                        familyTreeDrawer,
                        addedPerson,
                        focusedPerson!!,
                        addingEmptyNodes,
                        parentLayer,
                        leftParentInd,
                        parentChildrenLine,
                        family,
                        FunLabel.MOVE_PARENT_POSITION
                    )
                }

                // Find the parent sib
                val leftGrandparent = parentChildrenLine.parentList[0]
                val parentSibList = findParentSibIdInd(
                    familyTreeDrawer, parent, leftGrandparent, parentLayer
                )

                // Update ParentSibListInd
                val parentSibListInd = parentSibList[1]

                parentChildrenLine.extendLine(
                    familyTreeDrawer,
                    childrenLineLayer, // the childrenLine above the parent layer
                    parentSibListInd
                )

                familyTreeDrawer.replaceFamilyStorageLayer(
                    childrenLineLayer, childrenLineInd, null, parentChildrenLine
                )
            }
        }
    }
}
