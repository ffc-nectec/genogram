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

package ffc.genogram

import ffc.genogram.Util.cleanUpEmptyStack

class Family(
        var familyId: Long,
        var familyName: String,
        var members: List<Person>,
        rootPerson: Person = members[0]
) {
    var bloodFamily: MutableList<Int>?

    init {
        require(members.isNotEmpty()) { "member should not empty" }

        bloodFamily = mutableListOf(rootPerson.idCard)
        bloodFamily!!.addDescendentOf(rootPerson)
    }

    private fun MutableList<Int>.addDescendentOf(head: Person) {
        head.children?.let { addAll(it) }
        head.children?.forEach { addDescendentOf(findPerson(it)!!) }
    }

    // Return a person who is the first person in the blood family stack at the time.
    fun popBloodFamily(): Person? {
        var person: Person? = null
        bloodFamily?.let { bloodFamily ->
            person = members.firstOrNull { it.idCard == bloodFamily[0] }
            // delete that person from the bloodFamily stack
            val tmp = bloodFamily
            tmp.removeAt(0)
            this.bloodFamily = cleanUpEmptyStack(tmp)
        }
        return person
    }

    fun findPerson(id: Int): Person? {
        return members.firstOrNull { it.idCard == id }
    }
}
