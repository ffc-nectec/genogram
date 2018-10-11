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

package ffc.genogram.FamilyTree.SecondGen

import ffc.genogram.FamilyTree
import ffc.genogram.getResourceAs
import org.amshove.kluent.`should equal`
import org.junit.Test

class Drawer2ndGen4ChildrenTest {

    /************************************************************************************
     **  4 Children
     *************************************************************************************/

    @Test
    fun draw4Children() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-4-children.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,---------,----^----,---------,    ]
            [( Lisa ), [  Ed  ], [Teddy ], ( Anne )]
        """.trimIndent()
    }

    @Test
    fun draw4Children2() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-4-children-2.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,---------,----^----,---------,    ]
            [[Chris ], [  Ed  ], [Teddy ], [ Mike ]]
        """.trimIndent()
    }

    @Test
    fun draw4Children3() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-4-children-3.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,---------,----^----,---------,    ]
            [( Lisa ), ( Cara ), ( Lucy ), ( Anne )]
        """.trimIndent()
    }

    @Test
    fun draw4Children4() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-28.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [    ,------------------,-----^--------------,---------,    ]
            [[Chris ], ( Lisa ), [  Ed  ], (Kitty ), [Teddy ], [ Mike ]]
            [    |_________|    ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children5() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-27.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,---------,----^--------------,---------,    ]
            [[ Mike ], [  Ed  ], ( Lisa ), ( Cara ), [ Bill ]]
            [        ,     |_________|    ]
        """.trimIndent()
    }
}
