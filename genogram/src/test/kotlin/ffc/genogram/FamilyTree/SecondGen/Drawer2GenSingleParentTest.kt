package ffc.genogram.FamilyTree.SecondGen

import ffc.genogram.FamilyTree
import ffc.genogram.Util.displayObjectResult
import ffc.genogram.getResourceAs
import org.amshove.kluent.`should equal`
import org.junit.Test

class Drawer2GenSingleParentTest {

    @Test
    fun draw1Child() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/singleParent/single-parent.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[  M0  ], (   ?  )]
            [    |_________|   ]
            [         |        ]
            [     [  M1  ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child2() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/singleParent/single-parent-2.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[  M0  ], (   ?  )]
            [    |_________|   ]
            [    ,----^----,   ]
            [(  F2  ), [  M3  ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child3() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/singleParent/single-parent-3.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[   ?  ], (  F1  )]
            [    |_________|   ]
            [         |        ]
            [     (  F2  )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child4() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/singleParent/single-parent-4.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[   ?  ], (  F1  )]
            [    |_________|   ]
            [    ,----^----,   ]
            [[  M2  ], (  F3  )]
        """.trimIndent()
    }

    @Test
    fun draw1Child5() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/singleParent/single-parent-5.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (   ?  )]
            [    |_________|   ]
            [    ,----^--------------,   ]
            [[Teddy ], ( Cara ), [  Ed  ]]
            [    |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw1Child6() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/singleParent/single-parent-6.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[   ?  ], (Grandm)]
            [    |_________|   ]
            [    ,----^----,   ]
            [[Teddy ], [Feddy ], ( Cara )]
            [        ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw1Child7() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/singleParent/single-parent-7.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (   ?  )]
            [    |_________|   ]
            [    ,----^--------------,   ]
            [[Teddy ], (Kitty ), [  Ed  ], ( Cara )]
            [    |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw1Child8() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/singleParent/single-parent-8.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [   ?  ], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[ Bill ], ( Lisa ), [  Ed  ]]
            [    |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw1Child9() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/singleParent/single-parent-9.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [   ?  ], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[ Bill ], ( Lisa ), [  Ed  ], ( Mary )]
            [    |_________|   ,     |_________|   ]
        """.trimIndent()
    }

    @Test
    fun draw1Child10() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/singleParent/single-parent-10.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (   ?  )]
            [    |_________|   ]
            [    ,----^----,---------,   ]
            [( Lisa ), [  Ed  ], (Kitty )]
        """.trimIndent()
    }

    @Test
    fun draw1Child11() {
        val drawer = FamilyTree(
            getResourceAs("2ndGen/singleParent/single-parent-11.json")
        ).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [   ?  ], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,   ]
            [[ Bill ], ( Lisa ), [  Ed  ], [Teddy ]]
            [    |_________|   ]
        """.trimIndent()
    }

}
