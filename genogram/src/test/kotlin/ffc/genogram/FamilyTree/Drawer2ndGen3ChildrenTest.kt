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

package ffc.genogram.FamilyTree

import ffc.genogram.FamilyTree
import ffc.genogram.getResourceAs
import org.amshove.kluent.`should equal`
import org.junit.Test

class Drawer2ndGen3ChildrenTest {

    /************************************************************************************
     **  3 Children
     *************************************************************************************/
    @Test
    fun draw3Children() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-3-children-2.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|    ]
            [    ,----^----,---------,    ]
            [[ Bill ], [  Ed  ], [Teddy ]]
        """.trimIndent()
    }

    @Test
    fun draw3Children2() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-3-children-3.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|    ]
            [    ,----^----,---------,    ]
            [[ Bill ], [  Ed  ], ( Lisa )]
        """.trimIndent()
    }

    @Test
    fun draw3Children3() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-3-children-4.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|    ]
            [    ,----^----,---------,    ]
            [[ Bill ], ( Lisa ), [  Ed  ]]
        """.trimIndent()
    }

    @Test
    fun draw3Children4() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-3-children-5.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|    ]
            [    ,----^----,---------,    ]
            [( Lisa ), [ Bill ], [  Ed  ]]
        """.trimIndent()
    }

    @Test
    fun draw3Children5() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-3-children-6.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|    ]
            [    ,----^----,---------,    ]
            [[  Ed  ], ( Lisa ), (Kitty )]
        """.trimIndent()
    }

    @Test
    fun draw3Children6() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-3-children-7.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|    ]
            [    ,----^----,---------,    ]
            [( Lisa ), [  Ed  ], (Kitty )]
        """.trimIndent()
    }

    @Test
    fun draw3Children7() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-3-children-8.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|    ]
            [    ,----^----,---------,    ]
            [( Lisa ), (Kitty ), [  Ed  ]]
        """.trimIndent()
    }

    @Test
    fun draw3Children8() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-3-children-9.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|    ]
            [    ,----^----,---------,    ]
            [( Lisa ), (Kitty ), (Sarah )]
        """.trimIndent()
    }

    @Test
    fun draw3Children9() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-9.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------------^----,---------,    ]
            [[ Bill ], ( Lisa ), [  Ed  ], [Teddy ]]
            [    |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children10() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-10.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------,-----^--------------,    ]
            [[ Bill ], [  Ed  ], ( Lisa ), [Teddy ]]
            [        ,     |_________|    ]
        """.trimIndent()
    }
}
