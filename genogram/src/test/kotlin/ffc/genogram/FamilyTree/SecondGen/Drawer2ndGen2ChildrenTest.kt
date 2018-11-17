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
import ffc.genogram.Util.displayObjectResult
import ffc.genogram.getResourceAs
import org.amshove.kluent.`should equal`
import org.junit.Test

class Drawer2ndGen2ChildrenTest {

    /************************************************************************************
     **  2 Children
     *************************************************************************************/
    @Test
    fun draw2Children() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-2-children-2.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^----,   ]
            [[James ], [  Ed  ]]
        """.trimIndent()
    }

    @Test
    fun draw2Children2() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-2-children-3.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^----,   ]
            [[  Ed  ], ( Lisa )]
        """.trimIndent()
    }

    @Test
    fun draw2Children3() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-2-children.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^----,   ]
            [( Lisa ), [  Ed  ]]
        """.trimIndent()
    }

    @Test
    fun draw2Children4() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-2-children-4.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^----,   ]
            [(Sarah ), ( Lisa )]
        """.trimIndent()
    }

    @Test
    fun draw2Children5() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-3.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^--------------,   ]
            [[Teddy ], ( Cara ), [  Ed  ]]
            [    |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw2Children6() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-4.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^----,   ]
            [[Teddy ], [Feddy ], ( Cara )]
            [        ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw2Children7() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-5.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,   ]
            [[Teddy ], (Kitty ), [  Ed  ], ( Cara )]
            [    |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw2Children8() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-2.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^--------------,   ]
            [[  Ed  ], ( Mary ), ( Lisa )]
            [    |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw2Children9() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-7.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^----,   ]
            [[  Ed  ], ( Lisa ), [ Bill ]]
            [        ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw2Children10() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-3.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,   ]
            [[  Ed  ], ( Mary ), ( Lisa ), [ Bill ]]
            [    |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw2Children11() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[ Bill ], ( Lisa ), [  Ed  ]]
            [    |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw2Children12() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-6.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^----,   ]
            [( Lisa ), [  Ed  ], (Marry )]
            [        ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw2Children13() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[ Bill ], ( Lisa ), [  Ed  ], ( Mary )]
            [    |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw2Children14() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-7.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[  Ed  ], ( Lisa ), (Marry )]
            [    |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw2Children15() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-8.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^----,   ]
            [( Lisa ), (Marry ), [  Ed  ]]
            [        ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw2Children16() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-8.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[  Ed  ], ( Lisa ), (Marry ), [ Bill ]]
            [    |_________|   ,     |_________|   ]
        """.trimIndent()
    }
}
