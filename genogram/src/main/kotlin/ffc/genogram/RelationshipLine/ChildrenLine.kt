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
    var childreObjList: MutableList<Person>,
    var familyTreeDrawer: FamilyTreeDrawer
) : Relationship() {

    override fun drawLine(): FamilyTreeDrawer {
        familyTreeDrawer.addFamilyNewLayer(createLineDistance())
        familyTreeDrawer.addEmptyNewLayer()

        return familyTreeDrawer
    }

    override fun createLineDistance(): String {
        val childrenSize = childreObjList.size
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
                markPosition = (markPosition - (nodeSize - nodeBorderSize)).toInt()

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
