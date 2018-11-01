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

class Drawer2ndGen3ChildrenTest {

    /************************************************************************************
     **  3 Children
     *************************************************************************************/
    @Test
    fun draw3Children() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-3-children-2.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^----,---------,   ]
            [[ Bill ], [  Ed  ], [Teddy ]]
        """.trimIndent()
    }

    @Test
    fun draw3Children2() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-3-children-3.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^----,---------,   ]
            [[ Bill ], [  Ed  ], ( Lisa )]
        """.trimIndent()
    }

    @Test
    fun draw3Children3() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-3-children-4.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^----,---------,   ]
            [[ Bill ], ( Lisa ), [  Ed  ]]
        """.trimIndent()
    }

    @Test
    fun draw3Children4() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-3-children-5.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^----,---------,   ]
            [( Lisa ), [ Bill ], [  Ed  ]]
        """.trimIndent()
    }

    @Test
    fun draw3Children5() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-3-children-6.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^----,---------,   ]
            [[  Ed  ], ( Lisa ), (Kitty )]
        """.trimIndent()
    }

    @Test
    fun draw3Children6() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-3-children-7.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^----,---------,   ]
            [( Lisa ), [  Ed  ], (Kitty )]
        """.trimIndent()
    }

    @Test
    fun draw3Children7() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-3-children-8.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^----,---------,   ]
            [( Lisa ), (Kitty ), [  Ed  ]]
        """.trimIndent()
    }

    @Test
    fun draw3Children8() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/children/family-3-children-9.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^----,---------,   ]
            [( Lisa ), (Kitty ), (Sarah )]
        """.trimIndent()
    }

    @Test
    fun draw3Children9() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-9.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,    ]
            [[ Bill ], ( Lisa ), [  Ed  ], [Teddy ]]
            [    |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children10() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-10.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String Visualization
        /*canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------,-----^--------------,    ]
            [[ Bill ], [  Ed  ], ( Lisa ), [Teddy ]]
            [        ,     |_________|   ]
        """.trimIndent()*/

        // Object Visualization
        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,---------,----^--------------,    ]
            [[ Bill ], [  Ed  ], ( Lisa ), [Teddy ]]
            [        ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children11() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-11.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String Visualization
        /*canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------,-----^----,   ]
            [[ Bill ], [  Ed  ], [Teddy ], ( Lisa )]
            [        ,         ,     |_________|   ]
        """.trimIndent()*/

        // Object Visualization
        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,---------,----^----,   ]
            [[ Bill ], [  Ed  ], [Teddy ], ( Lisa )]
            [        ,         ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children12() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-4.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^--------------,---------,    ]
            [[  Ed  ], ( Mary ), [ Bill ], ( Lisa ), [Teddy ]]
            [    |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children13() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-10.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,    ]
            [[ Bill ], ( Lisa ), [  Ed  ], [Teddy ], (Kitty )]
            [    |_________|   ,         ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children14() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-11.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String Visualization
        /*canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------,-----^--------------,    ]
            [[ Bill ], [  Ed  ], ( Lisa ), [Teddy ], (Kitty )]
            [        ,     |_________|   ,     |_________|   ]
        """.trimIndent()*/

        // Object Visualization
        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,---------,----^--------------,    ]
            [[ Bill ], [  Ed  ], ( Lisa ), [Teddy ], (Kitty )]
            [        ,     |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children15() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-3-spouses-2.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,------------------------^----,---------,    ]
            [[  Ed  ], ( Mary ), [ Bill ], ( Lisa ), [Teddy ], (Sarah )]
            [    |_________|   ,     |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children16() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-12.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,    ]
            [[ Bill ], ( Cara ), [  Ed  ], ( Lisa )]
            [    |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children17() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-13.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String Visualization
        /*canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------,-----^--------------,    ]
            [[ Bill ], [  Ed  ], ( Cara ), ( Lisa )]
            [        ,     |_________|   ]
        """.trimIndent()*/

        // Object Visualization
        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,---------,----^--------------,    ]
            [[ Bill ], [  Ed  ], ( Cara ), ( Lisa )]
            [        ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children18() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-14.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String Visualization
        /*canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------,-----^----,   ]
            [[ Bill ], [  Ed  ], ( Lisa ), [ Mike ]]
            [        ,         ,     |_________|   ]
        """.trimIndent()*/

        // Object Visualization
        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,---------,----^----,   ]
            [[ Bill ], [  Ed  ], ( Lisa ), [ Mike ]]
            [        ,         ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children19() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-12.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,-------------------,    ]
            [[ Bill ], ( Cara ), [  Ed  ], ( Maye ), ( Lisa )]
            [    |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children20() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-13.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,    ]
            [[ Bill ], ( Cara ), [  Ed  ], ( Lisa ), [Chris ]]
            [    |_________|   ,         ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children21() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-14.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String Visualization
        /*canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------,-----^--------------,    ]
            [[ Bill ], [  Ed  ], ( Lisa ), ( Cara ), [Chris ]]
            [        ,     |_________|   ,     |_________|   ]
        """.trimIndent()*/

        // Object Visualization
        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,---------,----^--------------,    ]
            [[ Bill ], [  Ed  ], ( Lisa ), ( Cara ), [Chris ]]
            [        ,     |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children29() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-3-spouses-1.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String Visualization
        /*canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,------------------,-----^--------------,    ]
            [[  Ed  ], ( Mary ), [Chris ], ( Cara ), (Sarah ), [Teddy ]]
            [    |_________|   ,     |_________|   ,     |_________|   ]
        """.trimIndent()*/

        // Object Visualization
        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^--------------,    ]
            [[  Ed  ], ( Mary ), [Chris ], ( Cara ), (Sarah ), [Teddy ]]
            [    |_________|   ,     |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children22() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-15.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,    ]
            [[ Bill ], ( Lisa ), (Sarah ), [Teddy ]]
            [    |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children23() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-16.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,    ]
            [[ Bill ], [Chris ], (Sarah ), [Teddy ]]
            [        ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children24() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-17.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String Visualization
        /*canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------,-----^----,   ]
            [[ Bill ], (Sarah ), [Teddy ], ( Lisa )]
            [        ,         ,     |_________|   ]
        """.trimIndent()*/

        // Object Visualization
        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,---------,----^----,   ]
            [[ Bill ], (Sarah ), [Teddy ], ( Lisa )]
            [        ,         ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children25() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-15.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^--------------,---------,    ]
            [[ Bill ], ( Lisa ), [Chris ], (Sarah ), [Teddy ]]
            [    |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children26() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-16.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,    ]
            [[ Bill ], ( Lisa ), (Sarah ), [Teddy ], ( Cara )]
            [    |_________|   ,         ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children27() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-17.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,    ]
            [[ Bill ], [  Ed  ], (Sarah ), [Teddy ], ( Lisa )]
            [        ,     |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children28() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-3-spouses-3.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,------------------------^----,---------,    ]
            [[ Bill ], ( Lisa ), [  Ed  ], (Sarah ), [Teddy ], ( Lisa )]
            [    |_________|   ,     |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children30() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-18.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,---------,   ]
            [[ Bill ], ( Lisa ), [  Ed  ], [Teddy ]]
            [    |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children31() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-19.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String Visualization
        /*canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------,-----^--------------,    ]
            [( Lisa ), [  Ed  ], (Sarah ), [Teddy ]]
            [        ,     |_________|   ]
        """.trimIndent()*/

        // Object Visualization
        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,---------,----^--------------,    ]
            [( Lisa ), [  Ed  ], (Sarah ), [Teddy ]]
            [        ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children32() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-20.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String Visualization
        /*canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------,-----^----,   ]
            [( Lisa ), [  Ed  ], [Teddy ], (Sarah )]
            [        ,         ,     |_________|   ]
        """.trimIndent()*/

        // Object Visualization
        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,---------,----^----,   ]
            [( Lisa ), [  Ed  ], [Teddy ], (Sarah )]
            [        ,         ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children33() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-18.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,-------------------,    ]
            [[ Bill ], ( Lisa ), [  Ed  ], (Kitty ), [Teddy ]]
            [    |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children34() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-19.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,---------,   ]
            [[ Bill ], ( Lisa ), [  Ed  ], [Teddy ], (Kitty )]
            [    |_________|   ,         ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children35() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-20.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String Visualization
        /*canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------,-----^--------------,    ]
            [( Lisa ), [  Ed  ], ( Cara ), [Teddy ], (Kitty )]
            [        ,     |_________|   ,     |_________|   ]
        """.trimIndent()*/

        // Object Visualization
        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,---------,----^--------------,    ]
            [( Lisa ), [  Ed  ], ( Cara ), [Teddy ], (Kitty )]
            [        ,     |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children36() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-3-spouses-4.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        /*canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------,-----^--------------,    ]
            [[ Bill ], ( Lisa ), [  Ed  ], ( Cara ), [Teddy ], ( Lisa )]
            [    |_________|   ,     |_________|   ,     |_________|   ]
        """.trimIndent()*/

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,---------,----^--------------,    ]
            [[ Bill ], ( Lisa ), [  Ed  ], ( Cara ), [Teddy ], ( Lisa )]
            [    |_________|   ,     |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children37() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-21.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,    ]
            [[  Ed  ], (Sarah ), ( Lisa ), ( Cara )]
            [    |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children38() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-22.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,    ]
            [[  Ed  ], [ Bill ], ( Lisa ), ( Cara )]
            [        ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children39() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-23.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String Visualization
        /*canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------,-----^----,   ]
            [[  Ed  ], ( Lisa ), ( Cara ), [ Bill ]]
            [        ,         ,     |_________|   ]
        """.trimIndent()*/

        // Object Visualization
        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,---------,----^----,   ]
            [[  Ed  ], ( Lisa ), ( Cara ), [ Bill ]]
            [        ,         ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children40() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-21.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^--------------,---------,    ]
            [[  Ed  ], (Sarah ), [ Bill ], ( Lisa ), ( Cara )]
            [    |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children41() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-22.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,    ]
            [[  Ed  ], [ Bill ], ( Lisa ), ( Cara ), [ Mike ]]
            [        ,     |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children42() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-23.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,    ]
            [[  Ed  ], (Sarah ), ( Lisa ), ( Cara ), [ Bill ]]
            [    |_________|   ,         ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children43() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-3-spouses-5.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,------------------------^----,---------,    ]
            [[  Ed  ], (Sarah ), [ Bill ], ( Lisa ), ( Cara ), [ Bill ]]
            [    |_________|   ,     |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children44() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-24.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,---------,   ]
            [[ Bill ], (Sarah ), [  Ed  ], ( Cara )]
            [    |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children45() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-25.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String
        /*canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------,-----^--------------,    ]
            [(Sarah ), [  Ed  ], ( Lisa ), ( Cara )]
            [        ,     |_________|   ]
        """.trimIndent()*/

        // Object
        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,---------,----^--------------,    ]
            [(Sarah ), [  Ed  ], ( Lisa ), ( Cara )]
            [        ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children47() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-1-spouse-26.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String
        /*canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------,-----^----,   ]
            [(Sarah ), [  Ed  ], ( Cara ), [ Bill ]]
            [        ,         ,     |_________|   ]
        """.trimIndent()*/

        // Object
        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,---------,----^----,   ]
            [(Sarah ), [  Ed  ], ( Cara ), [ Bill ]]
            [        ,         ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children48() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-24.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,-------------------,    ]
            [[ Bill ], (Sarah ), [  Ed  ], ( Cara ), ( Cara )]
            [    |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children49() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-25.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String
        /*canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------,-----^--------------,    ]
            [(Sarah ), [  Ed  ], ( Cara ), ( Cara ), [ Bill ]]
            [        ,     |_________|   ,     |_________|   ]
        """.trimIndent()*/

        // Object
        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,---------,----^--------------,    ]
            [(Sarah ), [  Ed  ], ( Cara ), ( Cara ), [ Bill ]]
            [        ,     |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children50() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-26.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,---------,   ]
            [[ Bill ], (Sarah ), [  Ed  ], ( Cara ), [ Bill ]]
            [    |_________|   ,         ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children51() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-3-spouses-6.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,---------,----^--------------,    ]
            [[ Bill ], (Sarah ), [  Ed  ], ( Lisa ), ( Cara ), [ Bill ]]
            [    |_________|   ,     |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children52() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-3-spouses-7.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,---------,    ]
            [[ Bill ], (Sarah ), [  Ed  ], ( Lisa ), ( Cara ), [ Bill ]]
            [    |_________|   ,     |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children53() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-27.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,---------,   ]
            [[ Bill ], (Sarah ), ( Lisa ), ( Cara ), [ Bill ]]
            [    |_________|   ,         ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw3Children54() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/spouses/family-2-spouses-6.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,------------------------^----,---------,-------------------,    ]
            [[  Ed  ], ( Mary ), [ Bill ], ( Lisa ), [Teddy ], (Sarah ), (Kitty )]
            [    |_________|   ,     |_________|   ,     |_________|   ]
        """.trimIndent()
    }
}
