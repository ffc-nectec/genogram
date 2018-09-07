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

package ffc.genogram.node

import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.Person
import ffc.genogram.RelationshipLine.RelationshipLabel

class NodeFactory {

    fun getNode(
        familyTreeDrawer: FamilyTreeDrawer,
        person: Person,
        relationLabel: RelationshipLabel?
    ): FamilyTreeDrawer {
        return when (person.gender) {
            0 -> MaleNode(familyTreeDrawer, person.firstname).drawNode(relationLabel)
            else -> FemaleNode(familyTreeDrawer, person.firstname).drawNode(relationLabel)
        }
    }

    fun getNode(
        familyTreeDrawer: FamilyTreeDrawer,
        personList: MutableList<Person>,
        relationLabel: RelationshipLabel?
    ): FamilyTreeDrawer {
        return if (personList.size == 1) {
            when (personList[0].gender) {
                0 -> MaleNode(familyTreeDrawer, personList[0].firstname).drawNode(relationLabel)
                else -> FemaleNode(familyTreeDrawer, personList[0].firstname).drawNode(relationLabel)
            }
        } else
            familyTreeDrawer
    }
}
