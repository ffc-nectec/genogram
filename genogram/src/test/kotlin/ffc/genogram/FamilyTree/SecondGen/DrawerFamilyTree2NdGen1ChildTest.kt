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

package ffc.genogram.FamilyTree.SecondGen

import ffc.genogram.FamilyTree
import ffc.genogram.Util.displayObjectResult
import ffc.genogram.getResourceAs
import org.amshove.kluent.`should equal`
import org.junit.Test

class DrawerFamilyTree2NdGen1ChildTest {

    /************************************************************************************
     **  1 Child
     *************************************************************************************/
    @Test
    fun draw1Child() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-1-child-2.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [         |        ]
            [     [  Ed  ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child2() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-1-child.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [         |        ]
            [     ( Lisa )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child3() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-2.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [         |        ]
            [     [ Bill ]     , ( Lisa )]
            [         |______________|   ]
        """.trimIndent()
    }

    @Test
    fun draw1Child4() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-1.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,          |        ]
            [[ Bill ],      ( Lisa )     ]
            [    |_____________|   ]
        """.trimIndent()
    }
}
