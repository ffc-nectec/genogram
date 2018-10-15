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
import ffc.genogram.Node.Node
import ffc.genogram.Node.Node.Companion.nodeBorderSize
import ffc.genogram.Node.Node.Companion.nodeSize
import ffc.genogram.Person

class ChildrenLine(
    var childrenList: MutableList<Person>,
    var parent: Person,
    var familyTreeDrawer: FamilyTreeDrawer
) : Relationship() {

    override fun drawLine(): FamilyTreeDrawer {
        // Add the children line
        familyTreeDrawer.addFamilyNewLayer(createLineDistance())
        familyTreeDrawer.addEmptyNewLayer()

        val parentLayer = familyTreeDrawer.findPersonLayer(parent)
        val addingInd = familyTreeDrawer.findPersonInd(parent, parentLayer)
        val addingLayer = parentLayer + 2
        val childrenLineSize = familyTreeDrawer.findStorageLayerSize(addingLayer)
        val childrenLineInd = childrenLineSize - 1

        // The number of empty node(s) should be equal to the parentInd
        // Add the empty node(s), move the line
        if (childrenLineSize < addingInd) {
            for (i in childrenLineInd until addingInd) {
                if (i == addingInd) {
                    familyTreeDrawer.addFamilyAtLayer(
                        addingLayer,
                        createLineDistance(),
                        null
                    )
                } else {
                    familyTreeDrawer.addFamilyStorageReplaceIndex(
                        addingLayer, i, null, null
                    )
                }
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

            for (i in 0 until spaceLine.toInt() + 1)
                resultSpace += space
            resultSign = resultSpace

            for (i in 0 until childrenSize - 1) {
                resultSign += sign
                for (j in 0 until distanceLine.toInt())
                    resultSign += line
            }

            resultSign = "$resultSign$sign$resultSpace"

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
