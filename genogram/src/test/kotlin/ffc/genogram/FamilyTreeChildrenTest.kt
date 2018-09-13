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

import org.amshove.kluent.`should equal`
import org.junit.Test
import java.lang.StringBuilder

class FamilyTreeChildrenTest {

    @Test
    fun drawThreeChildren() {
        val drawer = FamilyTree(getResourceAs("family-3-children.json")).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.familyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [   |_________|   ]
            [   ,---------|---------,   ]
            [( Lisa ), [[  Ed  ]], [[Teddy]]]
        """.trimIndent()
    }

    @Test
    fun drawFourChildren() {
        val drawer = FamilyTree(getResourceAs("family-4-children.json")).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.familyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [   |_________|   ]
            [   ,---------,----^----,---------,   ]
            [( Lisa ), [[  Ed  ]], [[Teddy]], ( Anne )]
        """.trimIndent()
    }
}
