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

import org.amshove.kluent.`should be`
import org.amshove.kluent.`should equal`
import org.junit.Test

class FamilyTreeTest {

    @Test
    fun popBloodFamily() {
        val family = getResourceAs<Family>("family-tree-3-people.json")
//        val familyTree = FamilyTree(family)

        family.popBloodFamily()?.firstname `should equal` "Grandfather"
        family.popBloodFamily()?.firstname `should equal` "Lisa"
        family.popBloodFamily() `should be` null
    }
}
