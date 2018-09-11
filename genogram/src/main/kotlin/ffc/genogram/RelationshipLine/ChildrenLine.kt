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
import ffc.genogram.node.Node

class ChildrenLine(
    var childreObjList: MutableList<Person>,
    var parentPosition: MutableList<Double>,
    var familyTreeDrawer: FamilyTreeDrawer
) : Relationship() {

    override fun drawLine(): FamilyTreeDrawer {
        val familyStorage = familyTreeDrawer.familyStorage
        val lastestLayer = familyStorage[familyStorage.size - 1]
        // find Daddy position(index) and Mark at that index
        // find Mummy position(index) and Mark at that index
        // Make line at the position(index) between Daddy and Mummy in the new layer
        familyTreeDrawer.addFamilyNewLayer(createLineDistance())
        familyTreeDrawer.addEmptyNewLayer()
        // Find "childrenObjList" size for make a mark

        return familyTreeDrawer
    }

    override fun createLineDistance(): String {
        val childrenSize = childreObjList.size
        val sign = ","
        val space = " "
        var resultSpace = ""
        val lineSpace = "-"
        var resultSign: String

        if (childrenSize == 1) {
            for (i in 0 until lengthLine.toInt() / 2 + Node.nodesDistance.toInt()) {
                resultSpace += space
            }
            return "$resultSpace|$resultSpace"
        } else {
            // have more than one children
            for (i in 0 until spaceLine.toInt())
                resultSpace += space

            resultSign = resultSpace
            for (i in 0 until childrenSize - 1) {
                resultSign += sign
                for (j in 0 until distanceLine.toInt())
                    resultSign += lineSpace
            }

            resultSign = "$resultSign$sign$resultSpace"

            val tmp = StringBuilder()
            for (i in 0 until resultSign.length) {
                if (i == (resultSign.length) / 2) {
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
