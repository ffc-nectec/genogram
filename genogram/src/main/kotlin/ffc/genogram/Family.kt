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

package ffc.genogram

import ffc.genogram.Util.cleanUpEmptyStack

class Family(
    var familyId: Int,
    var familyName: String,
    var bloodFamily: List<Int>?,
    var members: List<Person>?
) {

    fun copy(
        familyId: Int = this.familyId,
        familyName: String = this.familyName,
        bloodFamily: List<Int>? = this.bloodFamily,
        members: List<Person>? = this.members) = Family(familyId, familyName, bloodFamily, members)

    // Return a person who is the first person in the blood family stack at the time.
    fun popBloodFamily(): Person? {

        var person: Person? = null

        if (bloodFamily != null) {
            val personId = bloodFamily!![0]
            members!!.forEach {
                if (it.idCard.toInt() == personId)
                    person = it
            }

            // delete that person from the bloodFamily stack
            val tmp: MutableList<Int> = bloodFamily as MutableList<Int>
            tmp.removeAt(0)
            bloodFamily = cleanUpEmptyStack(tmp)
        }

        return person
    }

    fun findPerson(id: Int): Person? {
        members!!.forEach { person ->
            if (person.idCard == id)
                return person
        }

        return null
    }

    fun findPersonList(idList: List<Int>): ArrayList<Person> {
        var childrenList: ArrayList<Person> = arrayListOf()

        idList.forEach {
            val childId = findPerson(it)
            if (childId != null)
                childrenList.add(childId)
        }

        return childrenList
    }
}
