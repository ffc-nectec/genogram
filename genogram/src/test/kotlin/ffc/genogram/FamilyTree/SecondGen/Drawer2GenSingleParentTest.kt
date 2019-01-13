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

}
