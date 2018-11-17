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

package ffc.genogram.Node

import ffc.genogram.Family
import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.GenderLabel
import ffc.genogram.Person
import ffc.genogram.RelationshipLine.ChildrenLine
import ffc.genogram.RelationshipLine.Relationship
import ffc.genogram.RelationshipLine.RelationshipLabel
import ffc.genogram.Util.displayObjectResult

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
            Math.floorDiv(childrenNumber, 2) - 1
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
        familyTreeDrawer: FamilyTreeDrawer
    ) {
        // Children or Twin
        val addingLayer = familyTreeDrawer.findStorageSize() - 1
        val focusedPersonLayer = familyTreeDrawer.findPersonLayer(focusedPerson)
        val focusedPersonInd = familyTreeDrawer.findPersonInd(
            focusedPerson!!, focusedPersonLayer
        )
        val childrenNumb = familyTreeDrawer.findLineNumber(addingLayer)
        val parentLayer = familyTreeDrawer.findPersonLayer(focusedPerson)

        // Set Person node's indent
        addedPerson.setNodeMargin(siblings)

        // Test Here
        // Edit the addingIndex
        val childrenLineLayer = addingLayer - 1
        var siblingsNumb = 0
        var childPosition = 0
        val childrenLineStorage = familyTreeDrawer.getPersonLayer(childrenLineLayer)
        var childrenLine: ChildrenLine? = null
        childrenLineStorage.forEachIndexed { index, line ->
            if (line is ChildrenLine) {
                val childrenList: MutableList<Person> = line.childrenList
                childrenList.forEachIndexed { pos, list ->
                    if (list.idCard == addedPerson.idCard) {
                        childrenLine = childrenLineStorage[index] as ChildrenLine
                        childPosition = pos
                    }
                }
            }
        }
        if (childrenLine != null)
            siblingsNumb = childrenLine!!.childrenNumb

        // Add a single child
        familyTreeDrawer.addFamilyAtLayer(
            addingLayer,
            setSingleNodePosition(nodeName, gender, siblings),
            addedPerson
        )

        // Here
        // Adjust the parent layer when the more than three children were added.
        // Add indent for the previous layers if the number of children is even number.
        // The number of empty nodes will be the number of children / 2.
        if (addingLayer > 5 && childPosition == 0 && siblingsNumb > 3) {
            val addingEmptyNodes = if (siblingsNumb % 2 == 0)
                siblingsNumb / 2 - 1
            else
                Math.floorDiv(siblingsNumb, 2) - 1

            for (i in (childrenLineLayer - 1) downTo 0)
                for (j in 1..addingEmptyNodes)
                    familyTreeDrawer.addFamilyStorageReplaceIndex(i, 0, null, null)
        }

        // "focusedPerson" = parent, when the generation is greater than 2.
        // "parent" = grandparent
        // Find parent index, and add the addedPerson node at the index.
        // Move the addedPerson node.
        val addingLayerSize = familyTreeDrawer.findPersonLayerSize(addingLayer)
        var addingInd = focusedPersonInd - childrenNumb

        if (addingLayerSize < addingInd) {
            // Add empty node(s), and move the node
            for (i in focusedPersonInd until addingInd) {
                if (i == addingInd) {
                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                        addingLayer, i,
                        setSingleNodePosition(nodeName, gender, siblings),
                        addedPerson
                    )
                } else {
                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                        addingLayer, i, null, null
                    )
                }
            }
        } else {
            // When the layer's is enough for moving the child node
            // to the "parent"'s index.
            // Move the child node to "parent"'s index.
            var addingMore = addingInd - addingLayerSize

            for (i in 0 until addingMore + 1) {
                familyTreeDrawer.addFamilyStorageReplaceIndex(
                    addingLayer, addingInd - 1, null, null
                )
            }
        }
    }

    fun separateMidChildren(
        familyTreeDrawer: FamilyTreeDrawer,
        focusedPerson: Person,
        parentLayer: Int,
        parentInd: Int
    ) {

        val childrenLineLayer = parentLayer + 2
        val childrenLayer = parentLayer + 3
        var parentInd = parentInd
        val emptyNodeNumb = familyTreeDrawer.findNumberOfEmptyNode(childrenLayer)
        var cousinsLayerElNumb = familyTreeDrawer.findPersonLayerSize(childrenLayer)
        val cousinsNumb = Math.abs(cousinsLayerElNumb - emptyNodeNumb)
        val addingEmptyNode = parentInd - cousinsLayerElNumb
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

        val addingInd = (childNumb * 2) + childrenNumb
        if (addingEmptyNode > 0 && addingInd != parentInd) {
            if (cousinsLayerElNumb != addingEmptyNode) {
                if (cousinsLayerElNumb < addingEmptyNode) {
                    parentInd -= cousinsNumb
                }
                if (cousinsNumb != cousinsLayerElNumb - 1) {
                    for (i in cousinsLayerElNumb until parentInd) {
                        familyTreeDrawer.addFamilyStorageReplaceIndex(
                            childrenLayer,
                            i, null, null
                        )
                    }
                }
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
        var familyTreeDrawer = familyTreeDrawer
        val parentChildren = focusedPerson.children
        val isAddedPersonOldest = parentChildren!![0] == addedPerson.idCard.toInt()

        // Find index the addedPerson will be added.
        val addingLayer = familyTreeDrawer.findStorageSize() - 1
        val addedPersonInd = familyTreeDrawer.findPersonLayerSize(addingLayer)

        // addedPerson's parent index should be equal to or
        // greater than addedPerson's index.
        // Whether addedPerson is the oldest children.
        // Find the number of empty nodes for separate the layer.
        val emptyNodeNumb = Math.abs(addedPersonInd - parentInd)
        val childrenLineNumb = familyTreeDrawer.findLineNumber(parentLayer + 2) - 1
        val personNumb = familyTreeDrawer.findPersonNumbLayer(addingLayer)

        if (parentInd < addedPersonInd && isAddedPersonOldest) {
            // When the addedPerson's parent node is on the left-hand of the addedPerson,
            // the addedPerson's parent's siblings (addedPerson's uncles/aunts) have
            // children equal to less than their parent nodes.
            // Replace an empty node at the addedPerson's parent index.
            for (i in parentInd until parentInd + emptyNodeNumb) {
                familyTreeDrawer.addFamilyStorageReplaceIndex(
                    parentLayer,
                    i,
                    null,
                    null
                )
            }

            // Move the addedPerson's parent index
            val marriageLineLayer = parentLayer + 1
            val marriageLineNumb = familyTreeDrawer.findLineNumber(marriageLineLayer) - 1
            val emptyNodeNumbLine = familyTreeDrawer.findNumberOfEmptyNode(marriageLineLayer)
            val emptyMidNodeNumbLine = familyTreeDrawer.findNumberOfMidEmptyNode(marriageLineLayer)
            val emptyFrontMidNodeNumbLine = emptyNodeNumbLine - emptyMidNodeNumbLine
            val addingInd = (marriageLineNumb * 2) + emptyFrontMidNodeNumbLine - 1

            // Extend the MarriageLineManager by adding the empty node(s).
            for (i in 0 until emptyNodeNumb) {
                familyTreeDrawer.addFamilyStorageReplaceIndex(
                    parentLayer + 1,
                    addingInd,
                    null,
                    null
                )
            }

            // AddedPerson's parent adjusting zone.
            // Extend addedPerson's parent children line and grandparent's line
            // Extend the MarriageLineManager of AddedPerson's parent.
            var grandParentLayer = parentLayer - 3
            var grandParent: Person?
            var targetParentInd: Int? = getGrandParentInd(familyTreeDrawer, grandParentLayer, focusedPerson)
            if (targetParentInd == null) {
                var addPersonFather = addedPerson.father
                var addPersonMother = addedPerson.mother
                // targetParentId = new FocusedPerson's Id
                var targetParentId = if (focusedPerson.idCard == addPersonFather) {
                    addPersonMother
                } else {
                    addPersonFather
                }

                // targetParent = new FocusedPerson
                val targetPerson = familyTreeDrawer.getPersonById(targetParentId!!, parentLayer)
                targetParentInd = getGrandParentInd(familyTreeDrawer, grandParentLayer, targetPerson!!)
            }

            grandParent = familyTreeDrawer.getPersonLayerInd(
                grandParentLayer, targetParentInd!!
            ) as Person

            val parentSibListId = grandParent!!.children!!
            val drawParentSibListId: MutableList<Int> = mutableListOf()
            val childrenParentLayer = familyTreeDrawer.getPersonLayer(parentLayer)
            childrenParentLayer.forEachIndexed { index, element ->
                if (element is Person) {
                    parentSibListId.forEach { id ->
                        if (element.idCard.toInt() == id) {
                            drawParentSibListId.add(id)
                        }
                    }
                }
            }

            val drawParentSibListInd: MutableList<Int> = mutableListOf()
            drawParentSibListId.forEach { id ->
                drawParentSibListInd.add(
                    familyTreeDrawer.findPersonIndById(
                        id.toLong(), parentLayer
                    )
                )
            }

            // Find the parent index
            var parentListInd: MutableList<Int> = mutableListOf()
            val newFocusedPersonInd = familyTreeDrawer.findPersonInd(focusedPerson, parentLayer)
            parentListInd.add(newFocusedPersonInd)

            // Find addedPerson's siblings
            val addedPersonFather = addedPerson.father
            val addedPersonMother = addedPerson.mother
            var anotherParent: Person? = null

            if (addedPersonFather == focusedPerson.idCard) {
                if (addedPersonMother != null)
                    anotherParent = familyTreeDrawer.getPersonById(addedPersonMother, parentLayer)
            } else if (addedPersonMother == focusedPerson.idCard) {
                if (addedPersonFather != null)
                    anotherParent = familyTreeDrawer.getPersonById(addedPersonFather, parentLayer)
            }

            var addedPersonParent: MutableList<Person> = mutableListOf()
            addedPersonParent.add(focusedPerson)
            if (anotherParent != null) {
                addedPersonParent.add(anotherParent)
                var anotherParentInd = familyTreeDrawer.findPersonInd(anotherParent, parentLayer)
                parentListInd.add(anotherParentInd)
            }

            // Find children that focusedPerson and anotherParent has together
            val focusedPersonChildren = focusedPerson.children as MutableList<Int>
            var addedPersonSibListId: MutableList<Int> = mutableListOf()

            if (anotherParent != null) {
                val anotherParentChildren = anotherParent.children as MutableList<Int>
                if (anotherParentChildren != null) {
                    focusedPersonChildren.forEach { fpChild ->
                        anotherParentChildren.forEach { apChild ->
                            if (apChild == fpChild) {
                                addedPersonSibListId.add(apChild)
                            }
                        }
                    }
                }
            } else {
                addedPersonSibListId = focusedPersonChildren
            }

            val nodeBetweenSibNumb = (drawParentSibListInd[drawParentSibListInd.size - 1] - drawParentSibListInd[0]) + 1
            var childrenNumber = familyTreeDrawer.findPersonLayerSize(parentLayer)
            var emptyNodeNumber = familyTreeDrawer.findNumberOfEmptyNode(grandParentLayer)
            val addingEmptyNodes = findAddingEmptyNodesParent(childrenNumber)

            if (childrenNumber > 3) {
                familyTreeDrawer = addMoreNodes(
                    emptyNodeNumber, addingEmptyNodes, grandParentLayer, familyTreeDrawer
                )
            }

            // Extend the CHILDREN Line the top layer of the AddedPerson.
            // When the AddedPerson is added on the left-hand of this wife (FocusedPerson).
            var startInd = drawParentSibListInd[0]
            if (startInd != 0) {
                childrenNumber -= 1
            } else {
                startInd = 0
            }

            val expectedLength = familyTreeDrawer.childrenLineLength(nodeBetweenSibNumb)
            val extendedLine = familyTreeDrawer.extendLine(
                expectedLength,
                drawParentSibListInd,
                parentInd
            )
            val parentLineLayer = parentLayer - 1

            // Object Visualization
            var childrenLine = ChildrenLine()
            // childrenLine.extendLine(drawSibListInd, parentInd)
            var previousChildrenLine = familyTreeDrawer.findChildrenLine(
                parentLineLayer, focusedPerson!!
            )

            // Check for update the childrenLine
            var updateLine = false
            previousChildrenLine?.parentList?.forEach {
                if (it.idCard != focusedPerson.idCard) {
                    updateLine = true
                    return@forEach
                }
            }

            /*// Check
            if (addedPerson.firstname == "M8") {
                print("------Node------\n")
                print("add: ${addedPerson.firstname}\n")
                print("focusedPerson: ${focusedPerson!!.firstname}\n")
                print("previousChildrenLine: $previousChildrenLine\n")
                print("parentLayer: $parentLayer\n")
                print("parentLineLayer: $parentLineLayer\n")
                print("updateLine: $updateLine\n")
                if (childrenLine is ChildrenLine) {
                    print("this-childrenList:\n")
                    childrenLine.childrenList.forEach { it ->
                        print(" - ${it.firstname}\n")
                    }
                    print("this-parentList:\n")
                    childrenLine.parentList.forEach { it ->
                        print(" - ${it.firstname}\n")
                    }
                }
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("-------------\n")
            }*/

//            if (previousChildrenLine != null && !updateLine) {
            if (previousChildrenLine != null) {
                childrenLine = previousChildrenLine
            } else if (previousChildrenLine == null || updateLine) {
                // Create a new children line
                // Draw a new childrenLine with new parentList, new childrenList, and etc.
                val addedPersonSibList: MutableList<Person> = mutableListOf()
                addedPersonSibListId.forEach {
                    val child = family.findPerson(it.toLong())
                    addedPersonSibList.add(child!!)
                }
                childrenLine.drawLine(addedPersonSibListId.size, addedPersonParent, addedPersonSibList)
            }
            val childrenParentLineLayer = parentLayer - 1

            // Extend the ChildrenLine and move the childrenLine sign
            childrenLine.extendLine(
                familyTreeDrawer,
                childrenParentLineLayer, // the childrenLine above the parent layer
                drawParentSibListInd,
                targetParentInd, // GrandParentInd
                parentListInd
            )

            familyTreeDrawer.replaceFamilyStorageLayer(
                childrenParentLineLayer, startInd, extendedLine, childrenLine
            )

            // Check
            if (addedPerson.firstname == "M8") {
                print("------Node------\n")
                val childrenLayer = familyTreeDrawer.findPersonLayer(focusedPerson!!)
                val childrenLineLayer = childrenLayer - 1
                var childrenLine = ChildrenLine()
                var previousChildrenLine2 = familyTreeDrawer.findChildrenLine(
                    childrenLineLayer, focusedPerson!!
                )

                print("add: ${addedPerson.firstname}\n")
                print("focusedPerson: ${focusedPerson!!.firstname}\n")
                print("previousChildrenLine2: $previousChildrenLine2\n")
                print("childrenLineLayer: $childrenLineLayer\n")
                print("parentLayer: $parentLayer\n")
                if (previousChildrenLine2 is ChildrenLine) {
                    print("this-childrenList:\n")
                    previousChildrenLine2.childrenList.forEach { it ->
                        print(" - ${it.firstname}\n")
                    }
                    print("this-parentList:\n")
                    previousChildrenLine2.parentList.forEach { it ->
                        print(" - ${it.firstname}\n")
                    }
                }
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("-------------\n")
            }

            /*// Check
            if (addedPerson.firstname == "F20") {
                print("------Female 1------\n")
                print("add: ${addedPerson.firstname}\n")
                print("focused: ${focusedPerson!!.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("-------------\n")
            }*/

            // Move the children sign
            // String Visualization
            /*val extraNode = familyTreeDrawer.findNumberOfEmptyNode(parentLayer)
            val editedLine = familyTreeDrawer.moveChildrenLineSign(
                parentLineLayer, addingEmptyNodes, drawParentSibListInd, extraNode
            )*/

            // Comment out
            /*var line: Any? = getLineType(
                familyTreeDrawer,
                parentLineLayer,
                addingEmptyNodes,
                extraNode - 1,
                addedPersonSibListId,
                focusedPerson
            )

            if (addingEmptyNodes > 0 && line == null) {
                childrenLine.moveChildrenLineSign(
                    familyTreeDrawer,
                    parentLineLayer,
                    addingEmptyNodes,
                    extraNode,
                    addedPersonSibListId,
                    focusedPerson!!
                )
                line = childrenLine
            }

            familyTreeDrawer.replaceFamilyStorageLayer(
                parentLineLayer, startInd, editedLine, line
            )*/

            /*// Check
            if (addedPerson.firstname == "M8") {
                print("------Node 2------\n")
                print("add: ${addedPerson.firstname}\n")
                print("childrenLine: ${childrenLine.centerMarkPos}\n")
                print("parentListInd: $parentListInd\n")
                print("...............\n")
                val canvasB = displayObjectResult(familyTreeDrawer)
                print(canvasB.toString())
                print("-------------\n")
            }*/
        } else if (addedPersonInd < parentInd
            && isAddedPersonOldest
            && focusedPerson!!.children!!.size > 1
            && personNumb > childrenLineNumb
        ) {
            // When the addedPerson's parent node is on the right-hand of the addedPerson,
            // the addedPerson's parent's siblings (addedPerson's uncles/aunts) have children more than their parent node.
            // This case we don't need to separate the addedPerson's parent layer.

            // Replace an empty node at the addedPerson's parent index at the addedPerson's layer.
            for (i in addedPersonInd until parentInd) {
                familyTreeDrawer.addFamilyStorageReplaceIndex(
                    addingLayer,
                    i,
                    null,
                    null
                )
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
        var emptyNodeCount = familyTreeDrawer.findNumberOfEmptyNodePerson(childrenLineLayer)
        var line: Any? = null
        if (addingEmptyNodes == 0 || emptyNodeCount == 0) {
            line = ChildrenLine()
            var previousChildrenLine = familyTreeDrawer.findChildrenLine(
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
            // TODO: here
            line.moveChildrenLineSign(
                familyTreeDrawer,
                childrenLineLayer,
                addingEmptyNodes,
                extraNode,
                childrenListId,
                focusedPerson
            )
        }

        return line
    }

    private fun getGrandParentInd(
        familyTreeDrawer: FamilyTreeDrawer,
        grandParentLayer: Int,
        focusedPerson: Person
    ): Int? {

        var grandFatherId = focusedPerson.father
        var grandMotherId = focusedPerson.mother
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
        var addedPersonParent: MutableList<Person> = mutableListOf()

        if (parent != null)
            addedPersonParent.add(parent!!)

        if (focusedPersonChildren != null)
            if (anotherParent != null) {
                addedPersonParent.add(anotherParent)
                val anotherParentChildren = anotherParent.children as MutableList<Int>
                if (anotherParentChildren != null) {
                    focusedPersonChildren!!.forEach { fpChild ->
                        anotherParentChildren.forEach { apChild ->
                            if (apChild == fpChild) {
                                addedPersonSibListId.add(apChild)
                            }
                        }
                    }
                }
            } else {
                addedPersonSibListId = focusedPersonChildren!!
            }

        result.add(addedPersonSibListId.toMutableList())
        result.add(addedPersonParent.toMutableList())

        return result
    }
}
