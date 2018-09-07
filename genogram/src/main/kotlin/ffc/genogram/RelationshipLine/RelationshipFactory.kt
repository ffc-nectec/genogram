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

class RelationshipFactory {

    fun getLine(
        focusedPerson: Person,
        familyTreeDrawer: FamilyTreeDrawer,
        relationshipLabel: RelationshipLabel
    ): FamilyTreeDrawer {
        when (relationshipLabel) {
            RelationshipLabel.CHILDREN -> {
            }
            RelationshipLabel.TWIN -> {
            }
            else -> {
                lateinit var labelType: RelationshipLabel
                if (focusedPerson.hasBeenDivorced()) {
                    labelType = RelationshipLabel.DIVORCED
                    // TODO: suspectedPerson could be Ex-spouce or the Person we focuse.
                } else {
                    labelType = RelationshipLabel.MARRIAGE
                }

                if (focusedPerson.gender == 0) {
                    labelType = RelationshipLabel.RIGHT_HAND
                    if (labelType == RelationshipLabel.DIVORCED)
                        return DivorceLine().drawLine()
                    else
                        return MarriageLine(familyTreeDrawer, labelType).drawLine()
                } else {
                    labelType = RelationshipLabel.LEFT_HAND
                    if (labelType == RelationshipLabel.MARRIAGE)
                        return DivorceLine().drawLine()
                    else
                        return MarriageLine(familyTreeDrawer, labelType).drawLine()
                }
            }
        }
        return familyTreeDrawer
    }

    fun getLine(
        focusedListPerson: MutableList<Person>,
        parentsPosition: MutableList<Double>,
        familyTreeDrawer: FamilyTreeDrawer,
        relationshipLabel: RelationshipLabel
    ): FamilyTreeDrawer {
        return when (relationshipLabel) {
            RelationshipLabel.CHILDREN -> {
                // Add new "familyTreeDrawer" layer
                ChildrenLine(focusedListPerson, parentsPosition, familyTreeDrawer).drawLine()
            }
            else -> {
                // RelationshipLabel.TWIN
                TwinLine().drawLine()
            }
        }
    }
}
