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
import ffc.genogram.Node.Node
import ffc.genogram.Node.Node.Companion.nodeBorderSize
import ffc.genogram.Node.Node.Companion.nodeSize
import ffc.genogram.Person

class ChildrenLineManager(
    var childrenList: MutableList<Person>,
    var parent: Person,
    var keepBloodFamily: MutableList<Int>,
    var family: Family,
    var familyTreeDrawer: FamilyTreeDrawer
) : Relationship() {

    override fun drawLine(): FamilyTreeDrawer {

        val childrenLine = ChildrenLine()
        childrenLine.childrenList = childrenList as ArrayList<Person>
        val parent1 = childrenList[0].father
        val parent2 = childrenList[0].mother
        val parentList = arrayListOf<Person>()

        if (parent1 != null)
            parentList.add(family.findPerson(parent1)!!)
        if (parent2 != null)
            parentList.add(family.findPerson(parent2)!!)

        childrenLine.parentList = parentList

        val parentLayer = familyTreeDrawer.findPersonLayer(parent)
        val addingLayer = parentLayer + 2
        val parentInd = familyTreeDrawer.findPersonInd(parent, parentLayer)
        val generationSize = familyTreeDrawer.findStorageSize()
        val childrenNumber = childrenList.size
        childrenLine.drawLine(childrenNumber, parentList, childrenList)

        if (addingLayer >= generationSize) {
            familyTreeDrawer.addFamilyNewLayer(createLineDistance(), childrenLine)
            familyTreeDrawer.addEmptyNewLayer()
        } else {
            familyTreeDrawer.addFamilyAtLayer(
                addingLayer,
                createLineDistance(),
                childrenLine
            )
        }

        // Find the indexSize of the children line before compare to the person node size
        val addingLineInd = familyTreeDrawer.findPersonLayerSize(addingLayer)
        val childrenLineInd = addingLineInd - 1
        val childrenLineIndSize = familyTreeDrawer.findChildrenLineIndSize(
            addingLayer, 0, addingLineInd - 2
        )
        // The marriage line below the parent layer
        val anotherParent = childrenList[0].findAnotherParent(parent, family)
        val anotherParentInd = anotherParent?.let {
            familyTreeDrawer.findPersonInd(it, parentLayer)
        }
        val parentMarriageLine = familyTreeDrawer.findMarriageLine(
            parentLayer + 1, parent, anotherParent
        )!!
        val parentMarriageLineIndSize = familyTreeDrawer.findMarriageLineIndSize(
            parentLayer + 1,
            0,
            familyTreeDrawer.findLineInd(parentMarriageLine, parentLayer + 1)!! - 1
        )
        var addingMore = parentInd - childrenLineIndSize
        var leftParent: Person = parent
        if (anotherParentInd != null && anotherParentInd < parentInd)
            leftParent = anotherParent
        val bloodParent: Person = anotherParent?.let {
            if (keepBloodFamily.firstOrNull { it -> it == parent.idCard } == null) {
                anotherParent
            } else {
                parent
            }
        }.run {
            parent
        }

        val parentSib = leftParent.findSiblingByParent(family)
        if (childrenNumber <= 3 && bloodParent == leftParent) {
            // Delete 1; the addingPerson's children line
            val previousLine = familyTreeDrawer.findLineNumber(addingLayer) - 1
            if (previousLine == 0) {
                addingMore = (parentMarriageLineIndSize - addingLineInd) + 1
            }
            for (i in 0 until addingMore) {
                familyTreeDrawer.addFamilyStorageReplaceIndex(
                    addingLayer, childrenLineInd, null, null
                )
            }

            // For the 4th Generation, when addingPerson's parent is the only child
            if (familyTreeDrawer.generationNumber(parentLayer) >= 3 && parentSib.size == 0) {
                anotherParentInd?.let {
                    val layerSize = familyTreeDrawer.findPersonLayerSize(addingLayer)
                    if (anotherParentInd > layerSize) {
                        familyTreeDrawer.addFamilyStorageAtIndex(
                            addingLayer, layerSize - 1, null, null
                        )
                    }
                }

                familyTreeDrawer.addFamilyStorageReplaceIndex(
                    addingLayer, childrenLineInd, null, null
                )
            }
        } else {
            // For the 4th Generation, when addingPerson's parent is the only child
            if (familyTreeDrawer.generationNumber(parentLayer) >= 3 && parentSib.size == 0) {
                val leftParentInd = familyTreeDrawer.findPersonInd(leftParent, parentLayer)
                for (i in 0 until leftParentInd)
                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                        addingLayer, childrenLineInd, null, null
                    )
            }
        }

        return familyTreeDrawer
    }

    override fun createLineDistance(): String {
        val childrenSize = childrenList.size
        val sign = ","
        val space = " "
        var resultSpace = ""
        val line = "-"
        var resultSign: String

        // Make an indent
        if (childrenSize == 1) {
            val nodeDistance = ((lengthLine / 2) + Node.nodesDistance).toInt()
            for (i in 0 until nodeDistance) {
                resultSpace += space
            }

            return "$resultSpace |$resultSpace"
        } else {
            for (i in 0 until spaceLine.toInt())
                resultSpace += space
            resultSign = resultSpace

            for (i in 0 until childrenSize - 1) {
                resultSign += sign
                for (j in 0 until distanceLine.toInt())
                    resultSign += line
            }

            resultSign = " $resultSign$sign$resultSpace"

            var markPosition = resultSign.length / 2
            if (childrenSize % 2 != 0)
                markPosition = (markPosition - ((nodeSize + 1) - nodeBorderSize)).toInt()

            val tmp = StringBuilder()
            for (i in 0 until resultSign.length) {
                if (i == markPosition) {
                    if (resultSign[i].toString() != sign)
                        tmp.append("^")
                    else
                        tmp.append("|")
                } else {
                    tmp.append(resultSign[i])
                }
            }
            resultSign = tmp.toString()

            return resultSign
        }
    }
}
