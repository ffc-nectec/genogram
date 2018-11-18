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

package ffc.genogram.FamilyTree

import ffc.genogram.FamilyTree
import ffc.genogram.Util.displayObjectResult
import ffc.genogram.getResourceAs
import org.amshove.kluent.`should equal`
import org.junit.Test

class Drawer1GenTest {


    @Test
    fun drawGrandFObj() {
        val drawer = FamilyTree(getResourceAs("1stGen/family-1-person.json")).drawGenogram()
        /*val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }*/
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf]]
        """.trimIndent()
    }

    @Test
    fun drawGrandFaAndMa() {
        val drawer = FamilyTree(getResourceAs("1stGen/family-2-people.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
        """.trimIndent()
    }
}
