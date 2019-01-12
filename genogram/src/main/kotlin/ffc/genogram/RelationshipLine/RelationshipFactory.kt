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
import ffc.genogram.GenderLabel
import ffc.genogram.Person

class RelationshipFactory {

    fun getLine(
        focusedPerson: Person,
        family: Family,
        familyTreeDrawer: FamilyTreeDrawer,
        relationshipLabel: RelationshipLabel,
        addLayer: Int,
        keepBloodFamily: MutableList<Int>
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
                val fpSibOrder = focusedPerson.getSibOrder(
                    familyTreeDrawer, addLayer - 1
                )
                labelType = if (focusedPerson.gender == GenderLabel.MALE
                || fpSibOrder == RelationshipLabel.YOUNGEST_CHILD)
                    RelationshipLabel.RIGHT_HAND
                else
                    RelationshipLabel.LEFT_HAND
                MarriageLineManager(
                    familyTreeDrawer, labelType, addLayer, focusedPerson, family, keepBloodFamily
                )
            }
        } else {
            // TODO: Other relationship ig. Enemy
            return TwinLine()
        }
    }

    fun getLine(
        focusedListPerson: MutableList<Person>,
        parent: Person,
        keepBloodFamily: MutableList<Int>,
        family: Family,
        familyTreeDrawer: FamilyTreeDrawer
    ): Relationship {
        return ChildrenLineManager(focusedListPerson, parent, keepBloodFamily, family, familyTreeDrawer)
    }
}
