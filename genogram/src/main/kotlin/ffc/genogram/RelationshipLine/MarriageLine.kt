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

class MarriageLine(
    var familyTreeDrawer: FamilyTreeDrawer,
    var handSide: RelationshipLabel,
    var addingLayer: Int
) : Relationship() {

    override fun drawLine(): FamilyTreeDrawer {
        if (handSide == RelationshipLabel.RIGHT_HAND) {
            if (addingLayer > 0) {
                if (familyTreeDrawer.findStorageSize() > (addingLayer + 1))
                    familyTreeDrawer.addFamilyAtLayer(
                        addingLayer + 1,
                        createLineDistance()
                    )
                else if (familyTreeDrawer.findStorageSize() == (addingLayer + 1))
                    familyTreeDrawer.addFamilyNewLayer(createLineDistance())
            } else
                familyTreeDrawer.addFamilyNewLayer(createLineDistance())
        } else {
            // Left Hand Line
            familyTreeDrawer.addFamilyNewLayer(createLineDistance())
        }

        return familyTreeDrawer
    }

    override fun createLineDistance(): String {
        val sign = '_'
        val space = ' '
        var resultSpace = ""
        var resultSign = ""

        for (i in 0 until spaceLine.toInt() + 1)
            resultSpace += space

        for (i in 0 until distanceLine.toInt())
            resultSign += sign

        return "$resultSpace|$resultSign|$resultSpace"
    }
}
