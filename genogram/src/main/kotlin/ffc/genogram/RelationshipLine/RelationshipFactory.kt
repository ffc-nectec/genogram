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
        relationshipLabel: RelationshipLabel,
        addLayer: Int
    ): Relationship {

        var labelType: RelationshipLabel?

        if (relationshipLabel == RelationshipLabel.WIFE ||
            relationshipLabel == RelationshipLabel.EX_WIFE ||
            relationshipLabel == RelationshipLabel.HUSBAND ||
            relationshipLabel == RelationshipLabel.EX_HUSBAND
        ) {
            labelType = if (focusedPerson.hasBeenDivorced()) {
                // TODO: suspectedPerson could be Ex-spouce or the Person we focuse.
                RelationshipLabel.DIVORCED
            } else
                RelationshipLabel.MARRIAGE

            return if (labelType == RelationshipLabel.DIVORCED)
                DivorceLine()
            else {
                labelType = if (focusedPerson.gender == 0)
                    RelationshipLabel.RIGHT_HAND
                else
                    RelationshipLabel.LEFT_HAND
                MarriageLine(familyTreeDrawer, labelType, addLayer, focusedPerson)
            }
        } else {
            // TODO: Other relationship ig. Enemy
            return TwinLine()
        }
    }

    fun getLine(
        focusedListPerson: MutableList<Person>,
        parent: Person,
        familyTreeDrawer: FamilyTreeDrawer,
        relationshipLabel: RelationshipLabel
    ): Relationship {
        return when (relationshipLabel) {
            RelationshipLabel.CHILDREN -> {
                ChildrenLine(focusedListPerson, parent, familyTreeDrawer)
            }
            else -> {
                TwinLine()
            }
        }
    }
}
