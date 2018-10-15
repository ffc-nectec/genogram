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

    @Test
    fun draw4Children6() {
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

    @Test
    fun draw4Children7() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-28.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,---------,----^----,---------,    ]
            [[ Mike ], [  Ed  ], ( Cara ), [ Bill ], ( Lisa )]
            [        ,         ,         ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children8() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-29.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [    ,-------------------,----^--------------,-------------------,    ]
            [[Chris ], ( Lisa ), [  Ed  ], (Kitty ), [Teddy ], (Kitty ), [ Mike ]]
            [    |_________|    ,     |_________|    ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children9() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-30.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|    ]
            [    ,-------------------,--------------^----,-------------------,    ]
            [[Chris ], ( Lisa ), [  Ed  ], (Kitty ), [Teddy ], (Kitty ), [ Mike ], (Kitty )]
            [    |_________|    ,     |_________|    ,     |_________|    ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children10() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-31.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [        ,     ,--------------^----,---------,---------,    ]
            [[  Ed  ], ( Lisa ),  [ Bill ], ( Cara ), ( Lucy ), ( Anne )]
            [    |_________|    ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children11() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-3-spouses-8.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [        ,     ,--------------^----,-------------------,---------,    ]
            [[  Ed  ], ( Lisa ),  [ Bill ], ( Cara ),  [Chris ], ( Lucy ), ( Anne )]
            [    |_________|    ,     |_________|    ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children12() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-4-spouses-1.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|    ]
            [        ,     ,------------------,------^-------------,---------,    ]
            [[  Ed  ], ( Lisa ),  [ Bill ], ( Cara ),  [Chris ], ( Lucy ), ( Anne ), [ Mike ]]
            [    |_________|    ,     |_________|    ,     |_________|    ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children13() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-3-spouses-9.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [        ,     ,---------,----^--------------,---------,    ]
            [[  Ed  ], ( Lisa ), ( Cara ),  [Chris ], ( Lucy ), ( Anne ), [ Bill ]]
            [    |_________|    ,         ,     |_________|    ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children14() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-3-spouses-10.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [        ,     ,--------------^----,---------,---------,    ]
            [[  Ed  ], ( Lisa ),  [Chris ], ( Cara ), ( Lucy ), ( Anne ), [ Bill ]]
            [    |_________|    ,     |_________|    ,         ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children15() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-3-spouses-11.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [    ,------------------,-----^--------------,---------,    ]
            [[ Bill ], ( Lisa ), [Chris ], ( Cara ), ( Lucy ), [  Ed  ], ( Anne )]
            [    |_________|    ,     |_________|    ,         ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children16() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-32.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [        ,     ,---------,----^----,---------,    ]
            [[ Bill ], ( Lisa ), [Chris ], ( Lucy ), ( Anne ), [  Ed  ]]
            [    |_________|    ,         ,         ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children17() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-33.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [    ,------------------,-----^----,---------,    ]
            [[  Ed  ], ( Lisa ), [Chris ], ( Lucy ), [ Bill ], ( Anne )]
            [    |_________|    ,         ,         ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children18() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-34.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [        ,     ,---------,----^--------------,---------,    ]
            [[  Ed  ], ( Lisa ), [Chris ],  [ Bill ], ( Lucy ), ( Anne )]
            [    |_________|    ,         ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children19() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-35.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [    ,------------------,-----^----,-------------------,    ]
            [[  Ed  ],  [Chris ], ( Lisa ), [ Bill ], ( Lucy ), [ Mike ]]
            [        ,     |_________|    ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children20() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-36.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [    ,---------,--------------^----,---------,    ]
            [[  Ed  ], [Chris ], ( Lisa ), ( Lucy ), ( Anne ), [ Bill ]]
            [        ,     |_________|    ,         ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children21() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-29.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------------^----,---------,---------,    ]
            [[ Bill ],  [  Ed  ], ( Lisa ), [ Ted  ], ( Anne )]
            [        ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children22() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-37.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [    ,------------------,-----^----,---------,    ]
            [[ Bill ],  [  Ed  ], ( Lisa ), [ Ted  ], ( Anne ), [Chris ]]
            [        ,     |_________|    ,         ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children23() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-38.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [        ,     ,---------,----^----,-------------------,    ]
            [[Chris ], ( Anne ), ( Lisa ), [ Bill ], ( Lucy ), [  Ed  ]]
            [    |_________|    ,         ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw4Children24() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-39.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [    ,---------,--------------^--------------,---------,    ]
            [[ Bill ], [Chris ], ( Anne ),  [Chris ], ( Cara ), ( Anne )]
            [        ,     |_________|    ,     |_________|    ]
        """.trimIndent()
    }
}
