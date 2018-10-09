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

    @Test
    fun draw3Children11() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-11.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------,-----^----,    ]
            [[ Bill ], [  Ed  ], [Teddy ], ( Lisa )]
            [        ,         ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children12() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-4.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------------^--------------,---------,    ]
            [[  Ed  ], ( Mary ),  [ Bill ], ( Lisa ), [Teddy ]]
            [    |_________|    ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children13() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-10.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------------^----,---------,    ]
            [[ Bill ], ( Lisa ), [  Ed  ], [Teddy ], (Kitty )]
            [    |_________|    ,         ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children14() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-11.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------,-----^--------------,    ]
            [[ Bill ], [  Ed  ], ( Lisa ), [Teddy ], (Kitty )]
            [        ,     |_________|    ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children15() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-3-spouses-2.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [    ,------------------------^----,---------,    ]
            [[  Ed  ], ( Mary ),  [ Bill ], ( Lisa ), [Teddy ], (Sarah )]
            [    |_________|    ,     |_________|    ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children16() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-12.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------------^----,---------,    ]
            [[ Bill ], ( Cara ), [  Ed  ], ( Lisa )]
            [    |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children17() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-13.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------,-----^--------------,    ]
            [[ Bill ], [  Ed  ], ( Cara ), ( Lisa )]
            [        ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children18() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-14.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------,-----^----,    ]
            [[ Bill ], [  Ed  ], ( Lisa ), [ Mike ]]
            [        ,         ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children19() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-12.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------------^----,-------------------,    ]
            [[ Bill ], ( Cara ), [  Ed  ], ( Maye ), ( Lisa )]
            [    |_________|    ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children20() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-13.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------------^----,---------,    ]
            [[ Bill ], ( Cara ), [  Ed  ], ( Lisa ), [Chris ]]
            [    |_________|    ,         ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children21() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-14.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------,-----^--------------,    ]
            [[ Bill ], [  Ed  ], ( Lisa ), ( Cara ), [Chris ]]
            [        ,     |_________|    ,     |_________|    ]
        """.trimIndent()
    }

    fun draw3Children29() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-3-spouses-1.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [    ,------------------,-----^--------------,    ]
            [[  Ed  ], ( Mary ), [Chris ], ( Cara ), (Sarah ), [Teddy ]]
            [    |_________|    ,     |_________|    ,     |_________|    ]
        """.trimIndent()
    }




    @Test
    fun draw3Children22() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-15.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------------^----,---------,    ]
            [[ Bill ], ( Lisa ), (Sarah ), [Teddy ]]
            [    |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children23() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-16.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------------^----,---------,    ]
            [[ Bill ],  [Chris ], (Sarah ), [Teddy ]]
            [        ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children24() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-17.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------,-----^----,    ]
            [[ Bill ], (Sarah ), [Teddy ], ( Lisa )]
            [        ,         ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children25() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-15.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------------^--------------,---------,    ]
            [[ Bill ], ( Lisa ),  [Chris ], (Sarah ), [Teddy ]]
            [    |_________|    ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children26() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-16.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------------^----,---------,    ]
            [[ Bill ], ( Lisa ), (Sarah ), [Teddy ], ( Cara )]
            [    |_________|    ,         ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children27() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-17.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------------^----,---------,    ]
            [[ Bill ],  [  Ed  ], (Sarah ), [Teddy ], ( Lisa )]
            [        ,     |_________|    ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children28() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-3-spouses-3.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [    ,------------------------^----,---------,    ]
            [[ Bill ], ( Lisa ),  [  Ed  ], (Sarah ), [Teddy ], ( Lisa )]
            [    |_________|    ,     |_________|    ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children30() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-18.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [        ,     ,----^----,---------,    ]
            [[ Bill ], ( Lisa ), [  Ed  ], [Teddy ]]
            [    |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children31() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-19.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------,-----^--------------,    ]
            [( Lisa ), [  Ed  ], (Sarah ), [Teddy ]]
            [        ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children32() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-20.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,--------,-----^----,    ]
            [( Lisa ), [  Ed  ], [Teddy ], (Sarah )]
            [        ,         ,     |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw3Children33() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-18.json")
        ).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [        ,     ,----^----,-------------------,    ]
            [[ Bill ], ( Lisa ), [  Ed  ], (Kitty ), [Teddy ]]
            [    |_________|    ,     |_________|    ]
        """.trimIndent()
    }
}
