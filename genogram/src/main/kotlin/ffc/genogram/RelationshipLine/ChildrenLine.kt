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
        val sign = "'"
        val space = " "
        var resultSpace = ""
        var resultSign = ""

        if (childreObjList.size == 1) {
            for (i in 0 until lengthLine.toInt() / 2 + 2) {
                resultSpace += space
            }
            return "$resultSpace|$resultSpace"
        } else {
            // have more than one children
            return "|"
        }
    }
}
