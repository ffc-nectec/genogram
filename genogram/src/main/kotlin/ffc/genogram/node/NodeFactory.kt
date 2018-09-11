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
        val siblings = false
        return when (person.gender) {
            0 -> MaleNode(familyTreeDrawer, person.firstname).drawNode(relationLabel, siblings)
            else -> FemaleNode(familyTreeDrawer, person.firstname).drawNode(relationLabel, siblings)
        }
    }

    fun getNode(
        familyTreeDrawer: FamilyTreeDrawer,
        personList: MutableList<Person>,
        relationLabel: RelationshipLabel?
    ): FamilyTreeDrawer {
        var siblings = false

        return if (personList.size == 1) {
            when (personList[0].gender) {
                0 -> MaleNode(familyTreeDrawer, personList[0].firstname).drawNode(relationLabel, siblings)
                else -> FemaleNode(familyTreeDrawer, personList[0].firstname).drawNode(relationLabel, siblings)
            }
        } else {
            siblings = true
            for (i in 0 until personList.size) {
                when (personList[i].gender) {
                    0 -> MaleNode(familyTreeDrawer, personList[i].firstname).drawNode(relationLabel, siblings)
                    else -> FemaleNode(familyTreeDrawer, personList[i].firstname).drawNode(relationLabel, siblings)
                }
            }
            familyTreeDrawer
        }
    }
}
