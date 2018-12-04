package ffc.genogram.FamilyTree

import ffc.genogram.FamilyTree
import ffc.genogram.Util.displayObjectResult
import ffc.genogram.getResourceAs
import org.amshove.kluent.`should equal`
import org.junit.Test

class Drawer4GenTest {

    @Test
    fun draw1Child() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4rd-gen.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^----,-------------------,   ]
            [[  M1  ], ( F21  ), [  M2  ], [  M3  ], ( F22  ), [  M4  ]]
            [    |_________|   ,         ,     |_________|   ]
            [    ,----^----,   ,         ,     ,----^----,   ]
            [(  F5  ), [  M6  ], ( F23  ), (  F7  ), (  F8  )]
            [        ,     |_________|   ]
            [        ,          |        ]
            [        ,      [  M9  ]     ]
        """.trimIndent()
    }
}
