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

class Family(
    var familyId: Long,
    var familyName: String,
    var bloodFamily: List<Int>?,
    var members: List<Person>?
) {

    fun popBloodFamily(): Person? {
        // get first person from the bloodFamily stack
        var person: Person? = null

        if (bloodFamily != null) {
            val personId = bloodFamily!![0]
            for (i in 0 until members!!.size) {
                if (members!![i].idCard.toInt() == personId) {
                    person = members!![i]
                }
            }
            // delete that person from the bloodFamily stack
            val tmp: MutableList<Int> = bloodFamily as MutableList<Int>
            tmp.removeAt(0)
            bloodFamily = tmp
            bloodFamily = if (tmp.isEmpty()) null else tmp
        }

        return person
    }

}
