package ffc.genogram.FamilyTree

import ffc.genogram.FamilyTree
import ffc.genogram.Util.displayObjectResult
import ffc.genogram.getResourceAs
import org.amshove.kluent.`should equal`
import org.junit.Test

class Drawer4GenTest {

    @Test
    fun draw1Child() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen.json")).drawGenogram()
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

    @Test
    fun draw2Child() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen.json")).drawGenogram()
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
            [        ,     ,----^----,   ]
            [        , [  M9  ], ( F10  )]
        """.trimIndent()
    }

    @Test
    fun draw1Child2() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-2.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[ Bill ], ( Lisa ), [  Ed  ]]
            [    |_________|   ]
            [         |        ]
            [     [River ]     , ( Maye )]
            [         |______________|   ]
            [        ,          |        ]
            [        ,      [ Jeff ]     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child2() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-2.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[ Bill ], ( Lisa ), [  Ed  ]]
            [    |_________|   ]
            [         |        ]
            [     [River ]     , ( Maye )]
            [         |______________|   ]
            [        ,     ,----^----,   ]
            [        , [ Jeff ], [Jimmy ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child3() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-3.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^--------------,   ]
            [[ Bill ], ( Lisa ), ( Anne ), [  Ed  ]]
            [    |_________|   ,     |_________|   ]
            [        ,         ,          |        ]
            [        , [Teddy ],      ( Maye )     ]
            [        ,     |_____________|         ]
            [        ,          |        ]
            [        ,      ( Jane )     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child3() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-3.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|   ]
            [    ,----^--------------,   ]
            [[ Bill ], ( Lisa ), ( Anne ), [  Ed  ]]
            [    |_________|   ,     |_________|   ]
            [        ,         ,          |        ]
            [        , [Teddy ],      ( Maye )     ]
            [        ,     |_____________|         ]
            [        ,     ,----^----,   ]
            [        , ( Jane ), [James ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child4() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-4.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,---------,   ]
            [[ Bill ], [  Ed  ], ( Anne ), [ Ted  ], ( Anne ), [Chris ]]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,         ,         ,         ,          |        ]
            [        ,         ,         ,         ,      [ Mike ]     , (Kitty )]
            [        ,         ,         ,         ,          |______________|   ]
            [        ,         ,         ,         ,         ,          |        ]
            [        ,         ,         ,         ,         ,      [ Mark ]     ]
        """.trimIndent()
    }


    @Test
    fun draw2Child4() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-4.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,---------,   ]
            [[ Bill ], [  Ed  ], ( Anne ), [ Ted  ], ( Anne ), [Chris ]]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,         ,         ,         ,          |        ]
            [        ,         ,         ,         ,      [ Mike ]     , (Kitty )]
            [        ,         ,         ,         ,          |______________|   ]
            [        ,         ,         ,         ,         ,     ,----^----,   ]
            [        ,         ,         ,         ,         , [ Mark ], ( Lisa )]
        """.trimIndent()
    }

    @Test
    fun draw1Child5() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-5.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,---------,----^--------------,---------,   ]
            [[ Bill ], ( Anne ), ( Lisa ), [Chris ], ( Cara ), ( Anne )]
            [    |_________|   ,         ,     |_________|   ]
            [        ,         ,         ,          |        ]
            [        ,         , [ Mike ],      ( Lucy )     ]
            [        ,         ,     |_____________|         ]
            [        ,         ,          |        ]
            [        ,         ,      ( Jane )     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child5() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-5.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,---------,----^--------------,---------,   ]
            [[ Bill ], ( Anne ), ( Lisa ), [Chris ], ( Cara ), ( Anne )]
            [    |_________|   ,         ,     |_________|   ]
            [        ,         ,         ,          |        ]
            [        ,         , [ Mike ],      ( Lucy )     ]
            [        ,         ,     |_____________|         ]
            [        ,         ,     ,----^----,   ]
            [        ,         , ( Jane ), ( Lisa )]
        """.trimIndent()
    }

    @Test
    fun draw1Child6() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-6.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^--------------,---------,   ]
            [( Lisa ), [Chris ], ( Anne ), [Chris ], ( Cara ), ( Anne )]
            [        ,     |_________|   ,     |_________|   ]
            [        ,         ,         ,          |        ]
            [        ,         ,         ,      [ Kaye ]     , (Kitty )]
            [        ,         ,         ,          |______________|   ]
            [        ,         ,         ,         ,          |        ]
            [        ,         ,         ,         ,      [ Jame ]     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child6() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-6.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^--------------,---------,   ]
            [( Lisa ), [Chris ], ( Anne ), [Chris ], ( Cara ), ( Anne )]
            [        ,     |_________|   ,     |_________|   ]
            [        ,         ,         ,          |        ]
            [        ,         ,         ,      [ Kaye ]     , (Kitty )]
            [        ,         ,         ,          |______________|   ]
            [        ,         ,         ,         ,     ,----^----,   ]
            [        ,         ,         ,         , [ Jame ], [ Sam  ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child7() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-7.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[ Bill ], ( Lisa ), [  Ed  ]]
            [    |_________|   ]
            [    ,----^--------------,   ]
            [[River ], ( Jane ), [ Will ], ( Jane )]
            [    |_________|   ,     |_________|   ]
            [         |        ]
            [     [Sarah ]     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child7() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-7.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[ Bill ], ( Lisa ), [  Ed  ]]
            [    |_________|   ]
            [    ,----^--------------,   ]
            [[River ], ( Jane ), [ Will ], ( June )]
            [    |_________|   ,     |_________|   ]
            [         |        ,          |        ]
            [     (Sarah )     ,      [Chris ]     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child8() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-8.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[ Bill ], ( Lisa ), [  Ed  ]]
            [    |_________|   ]
            [    ,----^--------------,   ]
            [[River ], ( Jane ), [ Will ], ( June )]
            [    |_________|   ,     |_________|   ]
            [    ,----^----,   ,          |        ]
            [(Sarah ), [Micky ],      [Chris ]     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child9() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-9.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[ Bill ], ( Lisa ), [  Ed  ]]
            [    |_________|   ]
            [    ,----^--------------,   ]
            [[River ], ( Jane ), [ Will ], ( June )]
            [    |_________|   ,     |_________|   ]
            [    ,----^----,   ,     ,----^----,   ]
            [(Sarah ), [Micky ], [Chris ], [Jimmy ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child8() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-8.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,---------,----^----,-------------------,   ]
            [        , [ Bill ], ( Anne ), ( Lisa ), [Chris ], ( Cara ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,          |        ]
            [[ Jim  ],      (Kitty )     ,         ,      [ Mike ]     , ( Kim  )]
            [    |_____________|         ,         ,          |______________|   ]
            [    ,----^----,   ,         ,         ,         ,          |        ]
            [( Jane ), (Sarah ),         ,         ,         ,      [  Ed  ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child9() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-9.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,---------,----^----,-------------------,   ]
            [        , [ Bill ], ( Anne ), ( Lisa ), [Chris ], ( Cara ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,          |        ]
            [[ Jim  ],      (Kitty )     ,         ,      [ Mike ]     , ( Kim  )]
            [    |_____________|         ,         ,          |______________|   ]
            [         |        ,         ,         ,         ,          |        ]
            [     ( Jane )     ,         ,         ,         ,      [  Ed  ]     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child10() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-10.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,---------,----^----,-------------------,   ]
            [        , [ Bill ], ( Anne ), ( Lisa ), [Chris ], ( Cara ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,          |        ]
            [[ Jim  ],      (Kitty )     ,         ,      [ Mike ]     , ( Kim  )]
            [    |_____________|         ,         ,          |______________|   ]
            [    ,----^----,   ,         ,         ,         ,     ,----^----,   ]
            [( Jane ), (Sarah ),         ,         ,         , [  Ed  ], [ Tim  ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child10() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-10.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^------------------------,---------,   ]
            [[ Bill ], [Chris ], ( Lisa ),         , [  Ed  ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,          |        ]
            [        ,      [ Mike ]     , ( Ruby ),      [ Kaye ]     , (Lilly )]
            [        ,          |______________|   ,          |______________|   ]
            [        ,         ,          |        ]
            [        ,         ,      ( Cara )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child11() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-11.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^------------------------,---------,   ]
            [[ Bill ], [Chris ], ( Lisa ),         , [  Ed  ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,          |        ]
            [        ,      [ Mike ]     , ( Ruby ),      [ Kaye ]     , (Lilly )]
            [        ,          |______________|   ,          |______________|   ]
            [        ,         ,          |        ,         ,          |        ]
            [        ,         ,      ( Cara )     ,         ,      [ Sam  ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child12() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-12.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^------------------------,---------,   ]
            [[ Bill ], [Chris ], ( Lisa ),         , [  Ed  ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,          |        ]
            [        ,      [ Mike ]     , ( Ruby ),      [ Kaye ]     , (Lilly )]
            [        ,          |______________|   ,          |______________|   ]
            [        ,         ,         ,         ,         ,          |        ]
            [        ,         ,         ,         ,         ,      [ Sam  ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child13() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-13.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^------------------------,---------,   ]
            [[ Bill ], [Chris ], ( Lisa ),         , [  Ed  ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,          |        ]
            [        ,      [ Mike ]     , ( Ruby ),      [ Kaye ]     , (Lilly )]
            [        ,          |______________|   ,          |______________|   ]
            [        ,         ,          |        ,         ,     ,----^----,   ]
            [        ,         ,      ( Cara )     ,         , [ Sam  ], [ Will ]]
        """.trimIndent()
    }

    @Test
    fun draw2Child12() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-12.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^------------------------,---------,   ]
            [[ Bill ], [Chris ], ( Lisa ),         , [  Ed  ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,          |        ]
            [        ,      [ Mike ]     , ( Ruby ),      [ Kaye ]     , (Lilly )]
            [        ,          |______________|   ,          |______________|   ]
            [        ,         ,     ,----^----,   ,         ,     ,----^----,   ]
            [        ,         , ( Cara ), [ Andy ],         , [ Sam  ], [ Will ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child14() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-14.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,---------,   ]
            [[ Bill ], [Chris ], ( Lisa ), (Kitty ), [  Ed  ], ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,          |        ]
            [[ Jon  ],      ( Maye )     , [ Mike ],      ( Lucy )     ]
            [    |_____________|         ,     |_____________|         ]
            [        ,         ,         ,          |        ]
            [        ,         ,         ,      [ Anne ]     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child13() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-13.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,---------,   ]
            [[ Bill ], [Chris ], ( Lisa ), (Kitty ), [  Ed  ], ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,          |        ]
            [[ Jon  ],      ( Maye )     , [ Mike ],      ( Lucy )     ]
            [    |_____________|         ,     |_____________|         ]
            [        ,         ,         ,     ,----^----,   ]
            [        ,         ,         , [Billy ], ( Anne )]
        """.trimIndent()
    }


    @Test
    fun draw1Child15() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-15.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,---------,   ]
            [[ Bill ], [Chris ], ( Lisa ), (Kitty ), [  Ed  ], ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,          |        ]
            [[ Jon  ],      ( Maye )     , [ Mike ],      ( Lucy )     ]
            [    |_____________|         ,     |_____________|         ]
            [         |        ,         ,          |        ]
            [     ( Cara )     ,         ,      [Billy ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child16() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-16.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,---------,   ]
            [[ Bill ], [Chris ], ( Lisa ), (Kitty ), [  Ed  ], ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,          |        ]
            [[ Jon  ],      ( Maye )     , [ Mike ],      ( Lucy )     ]
            [    |_____________|         ,     |_____________|         ]
            [         |        ]
            [     ( Cara )     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child14() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-14.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,---------,   ]
            [[ Bill ], [Chris ], ( Lisa ), (Kitty ), [  Ed  ], ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,          |        ]
            [[ Jon  ],      ( Maye )     , [ Mike ],      ( Lucy )     ]
            [    |_____________|         ,     |_____________|         ]
            [    ,----^----,   ]
            [( Cara ), [Micky ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child17() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-17.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,--------------^----,---------,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Ted  ], ( Lucy ), [  Ed  ]]
            [        ,     |_________|   ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[James ], ( Cara ), [ Mike ], (Kitty )]
            [    |_________|   ,     |_________|   ]
            [         |        ]
            [     ( Maye )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child18() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-18.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,--------------^----,---------,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Ted  ], ( Lucy ), [  Ed  ]]
            [        ,     |_________|   ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[James ], ( Cara ), (Kitty ), [ Mike ]]
            [    |_________|   ,     |_________|   ]
            [         |        ]
            [     ( Maye )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child22() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-22.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,--------------^----,---------,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Ted  ], ( Lucy ), [  Ed  ]]
            [        ,     |_________|   ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[James ], ( Cara ), (Kitty ), [ Mike ]]
            [    |_________|   ,     |_________|   ]
            [        ,         ,          |        ]
            [        ,         ,      ( Maye )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child23() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-23.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,--------------^----,---------,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Ted  ], ( Lucy ), [  Ed  ]]
            [        ,     |_________|   ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[James ], ( Cara ), (Kitty ), [ Mike ]]
            [    |_________|   ,     |_________|   ]
            [         |        ,          |        ]
            [     [Andrew]     ,      ( Maye )     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child18() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-18.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,--------------^----,---------,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Ted  ], ( Lucy ), [  Ed  ]]
            [        ,     |_________|   ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[James ], ( Cara ), (Kitty ), [ Mike ]]
            [    |_________|   ,     |_________|   ]
            [         |        ,     ,----^----,   ]
            [     [Andrew]     , ( Maye ), ( Lucy )]
        """.trimIndent()
    }

    @Test
    fun draw2Child19() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-19.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,--------------^----,---------,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Ted  ], ( Lucy ), [  Ed  ]]
            [        ,     |_________|   ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[James ], ( Cara ), (Kitty ), [ Mike ]]
            [    |_________|   ,     |_________|   ]
            [    ,----^----,   ,     ,----^----,   ]
            [[Andrew], (Sarah ), ( Maye ), ( Lucy )]
        """.trimIndent()
    }

    @Test
    fun draw1Child19() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-19.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,---------,---------,   ]
            [[ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Ted  ], ( Lucy ), [  Ed  ]]
            [    |_________|   ,     |_________|   ,         ,     |_________|   ]
            [    ,----^--------------,   ]
            [[James ], ( Cara ), [ Mike ], (Kitty )]
            [    |_________|   ,     |_________|   ]
            [        ,         ,          |        ]
            [        ,         ,      ( Maye )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child24() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-24.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,---------,---------,   ]
            [[ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Ted  ], ( Lucy ), [  Ed  ]]
            [    |_________|   ,     |_________|   ,         ,     |_________|   ]
            [    ,----^--------------,   ]
            [[James ], ( Cara ), [ Mike ], (Kitty )]
            [    |_________|   ,     |_________|   ]
            [         |        ,          |        ]
            [     ( Maye )     ,      [Chris ]     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child20() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-20.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,---------,---------,   ]
            [[ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Ted  ], ( Lucy ), [  Ed  ]]
            [    |_________|   ,     |_________|   ,         ,     |_________|   ]
            [    ,----^--------------,   ]
            [[James ], ( Cara ), [ Mike ], (Kitty )]
            [    |_________|   ,     |_________|   ]
            [        ,         ,     ,----^----,   ]
            [        ,         , [Chris ], ( Maye )]
        """.trimIndent()
    }

    @Test
    fun draw2Child21() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-21.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,---------,---------,   ]
            [[ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Ted  ], ( Lucy ), [  Ed  ]]
            [    |_________|   ,     |_________|   ,         ,     |_________|   ]
            [    ,----^--------------,   ]
            [[James ], ( Cara ), [ Mike ], (Kitty )]
            [    |_________|   ,     |_________|   ]
            [         |        ,     ,----^----,   ]
            [     [ Marc ]     , [Chris ], ( Maye )]
        """.trimIndent()
    }

    @Test
    fun draw2Child22() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-22.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,---------,---------,   ]
            [[ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Ted  ], ( Lucy ), [  Ed  ]]
            [    |_________|   ,     |_________|   ,         ,     |_________|   ]
            [    ,----^--------------,   ]
            [[James ], ( Cara ), [ Mike ], (Kitty )]
            [    |_________|   ,     |_________|   ]
            [    ,----^----,   ,     ,----^----,   ]
            [[ Marc ], (Molly ), [Chris ], ( Maye )]
        """.trimIndent()
    }

    @Test
    fun draw1Child20() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-20.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,--------------^----,---------,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Ted  ], ( Lucy ), [  Ed  ]]
            [        ,     |_________|   ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[James ], ( Cara ), [ Mike ], (Kitty )]
            [    |_________|   ,     |_________|   ]
            [        ,         ,          |        ]
            [        ,         ,      ( Maye )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child21() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-21.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,--------------^----,---------,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Ted  ], ( Lucy ), [  Ed  ]]
            [        ,     |_________|   ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[James ], ( Cara ), [ Mike ], (Kitty )]
            [    |_________|   ,     |_________|   ]
            [         |        ,          |        ]
            [     [ Jeff ]     ,      ( Maye )     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child15() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-15.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,--------------^----,---------,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Ted  ], ( Lucy ), [  Ed  ]]
            [        ,     |_________|   ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[James ], ( Cara ), [ Mike ], (Kitty )]
            [    |_________|   ,     |_________|   ]
            [         |        ,     ,----^----,   ]
            [     [ Jeff ]     , ( Maye ), [ Sam  ]]
        """.trimIndent()
    }

    @Test
    fun draw2Child16() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-16.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,--------------^----,---------,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Ted  ], ( Lucy ), [  Ed  ]]
            [        ,     |_________|   ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[James ], ( Cara ), [ Mike ], (Kitty )]
            [    |_________|   ,     |_________|   ]
            [    ,----^----,   ,          |        ]
            [[ Jeff ], [ Sam  ],      ( Maye )     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child17() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-17.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,--------------^----,---------,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Ted  ], ( Lucy ), [  Ed  ]]
            [        ,     |_________|   ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[James ], ( Cara ), [ Mike ], (Kitty )]
            [    |_________|   ,     |_________|   ]
            [    ,----^----,   ,     ,----^----,   ]
            [[ Jeff ], [ Sam  ], ( Maye ), [ Bill ]]
        """.trimIndent()
    }

}
