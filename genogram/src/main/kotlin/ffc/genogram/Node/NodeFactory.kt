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

package ffc.genogram.Node

import ffc.genogram.Family
import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.Person

class NodeFactory {

    fun getNode(
        familyTreeDrawer: FamilyTreeDrawer,
        focusedPerson: Person?,
        person: Person,
        family: Family
    ): Node {
        var parentName: Person? = null
        if (focusedPerson != null &&
            (focusedPerson.father != null ||
                    focusedPerson.mother != null)
        ) {
            parentName = family.findPerson(focusedPerson.father!!)
            if (parentName == null)
                parentName = family.findPerson(focusedPerson.mother!!)
        }
        return when (person.gender) {
            0 -> MaleNode(familyTreeDrawer, focusedPerson, person.firstname, parentName)
            else -> FemaleNode(familyTreeDrawer, focusedPerson, person.firstname, parentName)
        }
    }
}
