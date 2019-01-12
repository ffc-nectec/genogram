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

}
