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

package ffc.genogram.Node

import ffc.genogram.Family
import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.GenderLabel
import ffc.genogram.Person

open class NodeFactory {

    fun getNode(
        familyTreeDrawer: FamilyTreeDrawer,
        focusedPerson: Person?,
        addedPerson: Person,
        family: Family,
        bloodFamilyId: MutableList<Int>
    ): Node {
        // focusedPerson = parent
        var parent: Person? = null

        if (focusedPerson != null) {
            focusedPerson.father?.let {
                parent = family.findPerson(it)
            }
            if (parent == null)
                focusedPerson.mother?.let {
                    parent = family.findPerson(it)
                }
        }

        return when (addedPerson.gender) {
            GenderLabel.MALE -> MaleNode(
                familyTreeDrawer,
                addedPerson,
                focusedPerson,
                addedPerson.firstname,
                parent,
                family,
                bloodFamilyId
            )
            else -> FemaleNode(
                familyTreeDrawer,
                addedPerson,
                focusedPerson,
                addedPerson.firstname,
                parent,
                family,
                bloodFamilyId
            )
        }
    }
}
