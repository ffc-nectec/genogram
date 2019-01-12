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

    @Test
    fun draw1Child25() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-25.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,         ,     ,----^----,   ]
            [        , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,     |_________|   ]
            [    ,---------,----^--------------,   ]
            [[River ], [ Will ], (Sandy ), (Sarah ), ( Cole )]
            [        ,     |_________|   ,     |_________|   ]
            [        ,     ,----^----,   ,          |        ]
            [        , [Micky ], [ Mike ],      [ Mike ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child26() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-26.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,         ,     ,----^----,   ]
            [        , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,     |_________|   ]
            [    ,--------------^----,-------------------,   ]
            [[River ], ( Anne ), [ Will ], (Sandy ), (Sarah ), ( Cole )]
            [    |_________|   ,     |_________|   ,     |_________|   ]
            [         |        ,     ,----^----,   ,     ,----^----,   ]
            [     (Lilly )     , [Micky ], [ Mike ], [ Mike ], ( Lucy )]
        """.trimIndent()
    }

    @Test
    fun draw2Child23() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-23.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,         ,     ,----^----,   ]
            [        , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,     |_________|   ]
            [    ,--------------^----,-------------------,   ]
            [[River ], ( Anne ), [ Will ], (Sandy ), (Sarah ), ( Cole )]
            [    |_________|   ,     |_________|   ,     |_________|   ]
            [    ,----^----,   ,     ,----^----,   ,     ,----^----,   ]
            [(Lilly ), (Kitty ), [Micky ], [ Mike ], [ Mike ], ( Lucy )]
        """.trimIndent()
    }

    @Test
    fun draw2Child24() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-24.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,         ,     ,----^----,   ]
            [        , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,     |_________|   ]
            [    ,--------------^----,-------------------,   ]
            [[River ], ( Anne ), [ Will ], (Sandy ), (Sarah ), ( Cole )]
            [    |_________|   ,     |_________|   ,     |_________|   ]
            [    ,----^----,   ,          |        ,     ,----^----,   ]
            [(Lilly ), (Kitty ),      [Micky ]     , [ Mike ], ( Lucy )]
        """.trimIndent()
    }

    @Test
    fun draw2Child25() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-25.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,         ,     ,----^----,   ]
            [        , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,     |_________|   ]
            [    ,--------------^----,-------------------,   ]
            [[River ], ( Anne ), [ Will ], (Sandy ), (Sarah ), ( Cole )]
            [    |_________|   ,     |_________|   ,     |_________|   ]
            [    ,----^----,   ,         ,         ,     ,----^----,   ]
            [(Lilly ), (Kitty ),         ,         , [ Mike ], ( Lucy )]
        """.trimIndent()
    }

    @Test
    fun draw2Child26() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-26.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,         ,     ,----^----,   ]
            [        , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,     |_________|   ]
            [    ,--------------^----,-------------------,   ]
            [[River ], ( Anne ), [ Will ], (Sandy ), (Sarah ), ( Cole )]
            [    |_________|   ,     |_________|   ,     |_________|   ]
            [         |        ,         ,         ,     ,----^----,   ]
            [     (Kitty )     ,         ,         , [ Mike ], ( Lucy )]
        """.trimIndent()
    }

    @Test
    fun draw2Child27() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-27.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,         ,     ,----^----,   ]
            [        , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,     |_________|   ]
            [    ,--------------^----,-------------------,   ]
            [[River ], ( Anne ), [ Will ], (Sandy ), (Sarah ), ( Cole )]
            [    |_________|   ,     |_________|   ,     |_________|   ]
            [        ,         ,         ,         ,     ,----^----,   ]
            [        ,         ,         ,         , [ Mike ], ( Lucy )]
        """.trimIndent()
    }

    @Test
    fun draw2Child28() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-28.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^------------------------,   ]
            [[ Bill ], ( Lisa ), [Chris ], ( Anne ),         , ( Lucy ), [  Ed  ]]
            [    |_________|   ,     |_________|   ,         ,     |_________|   ]
            [         |        ,          |        ,         ,          |        ]
            [     [ Mike ]     ,      (Kitty )     , [ Jon  ],      (Lilly )     ]
            [        ,         ,         ,         ,     |_____________|         ]
            [        ,         ,         ,         ,     ,----^----,   ]
            [        ,         ,         ,         , ( Jane ), [ Jame ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child27() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-27.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,------------------------^----,-----------------------------,   ]
            [[ Bill ], ( Lisa ),         , [Chris ], ( Anne ),         , ( Lucy ), [  Ed  ]]
            [    |_________|   ,         ,     |_________|   ,         ,     |_________|   ]
            [         |        ,         ,          |        ,         ,          |        ]
            [     [ Mike ]     , ( Jane ),      (Kitty )     , [ Jon  ],      (Lilly )     ]
            [         |______________|   ,         ,         ,     |_____________|         ]
            [        ,          |        ,         ,         ,          |        ]
            [        ,      ( Kim  )     ,         ,         ,      ( Jane )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child30() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-30.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,------------------------^----,-----------------------------,   ]
            [[ Bill ], ( Lisa ),         , [Chris ], ( Anne ),         , ( Lucy ), [  Ed  ]]
            [    |_________|   ,         ,     |_________|   ,         ,     |_________|   ]
            [         |        ,         ,          |        ,         ,          |        ]
            [     [ Mike ]     , ( Jane ),      (Kitty )     , [ Jon  ],      (Lilly )     ]
            [         |______________|   ,         ,         ,     |_____________|         ]
            [        ,     ,----^----,   ,         ,         ,          |        ]
            [        , ( Kim  ), [Micky ],         ,         ,      ( Jane )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child31() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-31.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,------------------------^----,-----------------------------,   ]
            [[ Bill ], ( Lisa ),         , [Chris ], ( Anne ),         , ( Lucy ), [  Ed  ]]
            [    |_________|   ,         ,     |_________|   ,         ,     |_________|   ]
            [         |        ,         ,          |        ,         ,          |        ]
            [     [ Mike ]     , ( Jane ),      (Kitty )     , [ Jon  ],      (Lilly )     ]
            [         |______________|   ,         ,         ,     |_____________|         ]
            [        ,          |        ,         ,         ,     ,----^----,   ]
            [        ,      ( Kim  )     ,         ,         , ( Jane ), [Steph ]]
        """.trimIndent()
    }

    @Test
    fun draw2Child29() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-29.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,------------------------^----,-----------------------------,   ]
            [[ Bill ], ( Lisa ),         , [Chris ], ( Anne ),         , ( Lucy ), [  Ed  ]]
            [    |_________|   ,         ,     |_________|   ,         ,     |_________|   ]
            [         |        ,         ,          |        ,         ,          |        ]
            [     [ Mike ]     , ( Jane ),      (Kitty )     , [ Jon  ],      (Lilly )     ]
            [         |______________|   ,         ,         ,     |_____________|         ]
            [        ,     ,----^----,   ,         ,         ,     ,----^----,   ]
            [        , ( Kim  ), [ Nick ],         ,         , ( Jane ), [Steph ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child28() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-28.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,------------------------^----,-----------------------------,   ]
            [[ Bill ], ( Lisa ),         , [Chris ], ( Anne ),         , ( Lucy ), [  Ed  ]]
            [    |_________|   ,         ,     |_________|   ,         ,     |_________|   ]
            [         |        ,         ,          |        ,         ,          |        ]
            [     [ Mike ]     , [ Jeff ],      (Kitty )     , [ Jon  ],      (Lilly )     ]
            [        ,         ,     |_____________|         ,     |_____________|         ]
            [        ,         ,     ,----^----,   ,         ,          |        ]
            [        ,         , ( Max  ), [Chris ],         ,      [Billy ]     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child30() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-30.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,------------------------^----,-----------------------------,   ]
            [[ Bill ], ( Lisa ),         , [Chris ], ( Anne ),         , ( Lucy ), [  Ed  ]]
            [    |_________|   ,         ,     |_________|   ,         ,     |_________|   ]
            [         |        ,         ,          |        ,         ,          |        ]
            [     [ Mike ]     , [ Jeff ],      (Kitty )     , [ Jon  ],      (Lilly )     ]
            [        ,         ,     |_____________|         ,     |_____________|         ]
            [        ,         ,     ,----^----,   ,         ,     ,----^----,   ]
            [        ,         , ( Max  ), [Chris ],         , [Billy ], [ Nick ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child29() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-29.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [    ,----------------------------------^----,-----------------------------,   ]
            [[ Bill ], ( Lisa ),         ,         , [Chris ], ( Anne ),         , ( Lucy ), [  Ed  ]]
            [    |_________|   ,         ,         ,     |_________|   ,         ,     |_________|   ]
            [         |        ,         ,         ,          |        ,         ,          |        ]
            [     [ Mike ]     , (Marry ), [ Tom  ],      (Kitty )     , [ Ted  ],      (Lilly )     ]
            [         |______________|   ,     |_____________|         ,     |_____________|         ]
            [        ,          |        ,         ,         ,         ,          |        ]
            [        ,      [ Jeff ]     ,         ,         ,         ,      [ Mike ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child32() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-32.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [    ,----------------------------------^----,-----------------------------,   ]
            [[ Bill ], ( Lisa ),         ,         , [Chris ], ( Anne ),         , ( Lucy ), [  Ed  ]]
            [    |_________|   ,         ,         ,     |_________|   ,         ,     |_________|   ]
            [         |        ,         ,         ,          |        ,         ,          |        ]
            [     [ Mike ]     , (Marry ), [ Tom  ],      (Kitty )     , [ Ted  ],      (Lilly )     ]
            [         |______________|   ,     |_____________|         ,     |_____________|         ]
            [        ,     ,----^----,   ,         ,         ,         ,          |        ]
            [        , [ Jeff ], [ Tim  ],         ,         ,         ,      [ Mike ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child33() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-33.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [    ,----------------------------------^----,-----------------------------,   ]
            [[ Bill ], ( Lisa ),         ,         , [Chris ], ( Anne ),         , ( Lucy ), [  Ed  ]]
            [    |_________|   ,         ,         ,     |_________|   ,         ,     |_________|   ]
            [         |        ,         ,         ,          |        ,         ,          |        ]
            [     [ Mike ]     , (Marry ), [ Tom  ],      (Kitty )     , [ Ted  ],      (Lilly )     ]
            [         |______________|   ,     |_____________|         ,     |_____________|         ]
            [        ,          |        ,          |        ,         ,          |        ]
            [        ,      [ Jeff ]     ,      [ Tim  ]     ,         ,      [ Mike ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child34() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-34.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [    ,----------------------------------^----,-----------------------------,   ]
            [[ Bill ], ( Lisa ),         ,         , [Chris ], ( Anne ),         , ( Lucy ), [  Ed  ]]
            [    |_________|   ,         ,         ,     |_________|   ,         ,     |_________|   ]
            [         |        ,         ,         ,          |        ,         ,          |        ]
            [     [ Mike ]     , (Marry ), [ Tom  ],      (Kitty )     , [ Ted  ],      (Lilly )     ]
            [         |______________|   ,     |_____________|         ,     |_____________|         ]
            [        ,          |        ,     ,----^----,   ,         ,          |        ]
            [        ,      [ Jeff ]     , [ Tim  ], [ Tom  ],         ,      [ Mike ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child35() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-35.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [    ,----------------------------------^----,-----------------------------,   ]
            [[ Bill ], ( Lisa ),         ,         , [Chris ], ( Anne ),         , ( Lucy ), [  Ed  ]]
            [    |_________|   ,         ,         ,     |_________|   ,         ,     |_________|   ]
            [         |        ,         ,         ,          |        ,         ,          |        ]
            [     [ Mike ]     , (Marry ), [ Tom  ],      (Kitty )     , [ Ted  ],      (Lilly )     ]
            [         |______________|   ,     |_____________|         ,     |_____________|         ]
            [        ,     ,----^----,   ,          |        ,         ,          |        ]
            [        , [ Jeff ], [Tom II],      [ Tim  ]     ,         ,      [ Mike ]     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child31() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-31.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [    ,----------------------------------^----,-----------------------------,   ]
            [[ Bill ], ( Lisa ),         ,         , [Chris ], ( Anne ),         , ( Lucy ), [  Ed  ]]
            [    |_________|   ,         ,         ,     |_________|   ,         ,     |_________|   ]
            [         |        ,         ,         ,          |        ,         ,          |        ]
            [     [ Mike ]     , (Marry ), [ Tom  ],      (Kitty )     , [ Ted  ],      (Lilly )     ]
            [         |______________|   ,     |_____________|         ,     |_____________|         ]
            [        ,     ,----^----,   ,     ,----^----,   ,         ,     ,----^----,   ]
            [        , [ Jeff ], [Tom II], [ Tim  ], [Sarah ],         , (Molly ), [ Mike ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child36() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-36.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,         ,     |_________|   ]
            [        ,         ,     ,-------------------,----^----,-------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Todd ],         , [  Ed  ], ( Lucy )]
            [        ,     |_________|   ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,          |        ,          |        ,         ,         ,          |        ]
            [[ Jim  ],      ( Cara )     ,      [ Mike ]     , ( Judy ), [ Jeff ],      (Susan )     ]
            [    |_____________|         ,          |______________|   ,     |_____________|         ]
            [         |        ,         ,         ,          |        ,          |        ]
            [     ( June )     ,         ,         ,      [ Ted  ]     ,      (James )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child40() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-40.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,         ,     |_________|   ]
            [        ,         ,     ,-------------------,----^----,-------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Todd ],         , [  Ed  ], ( Lucy )]
            [        ,     |_________|   ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,          |        ,          |        ,         ,         ,          |        ]
            [[ Jim  ],      ( Cara )     ,      [ Mike ]     , ( Judy ), [ Jeff ],      (Susan )     ]
            [    |_____________|         ,          |______________|   ,     |_____________|         ]
            [         |        ,         ,         ,     ,----^----,   ,          |        ]
            [     ( June )     ,         ,         , [ Ted  ], [ Tim  ],      (James )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child41() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-41.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,         ,     |_________|   ]
            [        ,         ,     ,-------------------,----^----,-------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Todd ],         , [  Ed  ], ( Lucy )]
            [        ,     |_________|   ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,          |        ,          |        ,         ,         ,          |        ]
            [[ Jim  ],      ( Cara )     ,      [ Mike ]     , ( Judy ), [ Jeff ],      (Susan )     ]
            [    |_____________|         ,          |______________|   ,     |_____________|         ]
            [    ,----^----,   ,         ,         ,     ,----^----,   ,          |        ]
            [( June ), [ Tom  ],         ,         , [ Ted  ], [ Tim  ],      [James ]     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child33() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-33.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,         ,     |_________|   ]
            [        ,         ,     ,-------------------,----^----,-------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Todd ],         , [  Ed  ], ( Lucy )]
            [        ,     |_________|   ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,          |        ,          |        ,         ,         ,          |        ]
            [[ Jim  ],      ( Cara )     ,      [ Mike ]     , ( Judy ), [ Jeff ],      (Susan )     ]
            [    |_____________|         ,          |______________|   ,     |_____________|         ]
            [    ,----^----,   ,         ,         ,     ,----^----,   ,     ,----^----,   ]
            [( June ), [ Tom  ],         ,         , [ Ted  ], [ Tim  ], [James ], ( Kat  )]
        """.trimIndent()
    }

    @Test
    fun draw1Child39() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-39.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,         ,     |_________|   ]
            [        ,         ,     ,-------------------,----^----,-------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Todd ],         , [  Ed  ], ( Lucy )]
            [        ,     |_________|   ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,          |        ,          |        ,         ,         ,          |        ]
            [[ Jim  ],      ( Cara )     ,      [ Mike ]     , ( Judy ), [ Jeff ],      (Susan )     ]
            [    |_____________|         ,          |______________|   ,     |_____________|         ]
            [         |        ,         ,         ,         ,         ,          |        ]
            [     ( June )     ,         ,         ,         ,         ,      (James )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child42() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-42.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,         ,     |_________|   ]
            [        ,         ,     ,-------------------,----^----,-------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Todd ],         , [  Ed  ], ( Lucy )]
            [        ,     |_________|   ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,          |        ,          |        ,         ,         ,          |        ]
            [[ Jim  ],      ( Cara )     ,      [ Mike ]     , ( Judy ), [ Jeff ],      (Susan )     ]
            [    |_____________|         ,          |______________|   ,     |_____________|         ]
            [    ,----^----,   ,         ,         ,         ,         ,          |        ]
            [( June ), ( Jane ),         ,         ,         ,         ,      (James )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child43() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-43.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,         ,     |_________|   ]
            [        ,         ,     ,-------------------,----^----,-------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Todd ],         , [  Ed  ], ( Lucy )]
            [        ,     |_________|   ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,          |        ,          |        ,         ,         ,          |        ]
            [[ Jim  ],      ( Cara )     ,      [ Mike ]     , ( Judy ), [ Jeff ],      (Susan )     ]
            [    |_____________|         ,          |______________|   ,     |_____________|         ]
            [    ,----^----,   ,         ,         ,         ,         ,     ,----^----,   ]
            [( June ), ( Jane ),         ,         ,         ,         , (James ), [ Tim  ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child44() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-44.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,         ,     |_________|   ]
            [        ,         ,     ,-------------------,----^----,-------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Todd ],         , [  Ed  ], ( Lucy )]
            [        ,     |_________|   ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,          |        ,          |        ,         ,         ,          |        ]
            [[ Jim  ],      ( Cara )     ,      [ Mike ]     , ( Judy ), [ Jeff ],      (Susan )     ]
            [    |_____________|         ,          |______________|   ,     |_____________|         ]
            [    ,----^----,   ,         ,         ,          |        ,     ,----^----,   ]
            [( June ), ( Jane ),         ,         ,      ( Cara )     , (James ), [ Tim  ]]
        """.trimIndent()
    }

    @Test
    fun draw2Child34() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-34.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,         ,     |_________|   ]
            [        ,         ,     ,-------------------,----^----,-------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Todd ],         , [  Ed  ], ( Lucy )]
            [        ,     |_________|   ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,          |        ,          |        ,         ,         ,          |        ]
            [[ Jim  ],      ( Cara )     ,      [ Mike ]     , ( Judy ), [ Jeff ],      (Susan )     ]
            [    |_____________|         ,          |______________|   ,     |_____________|         ]
            [    ,----^----,   ,         ,         ,     ,----^----,   ,     ,----^----,   ]
            [( June ), ( Jane ),         ,         , ( Cara ), [ Cole ], (James ), [ Tim  ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child37() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-37.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,--------------^----,---------,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Todd ], [  Ed  ], ( Lucy )]
            [        ,     |_________|   ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,          |        ,         ,          |        ]
            [[ Tim  ],      ( Cara )     ,      [ Mike ]     , ( Judy ),      [ Jeff ]     , ( Jane )]
            [    |_____________|         ,          |______________|   ,          |______________|   ]
            [        ,         ,         ,         ,         ,         ,         ,          |        ]
            [        ,         ,         ,         ,         ,         ,         ,      (Kitty )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child45() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-45.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,--------------^----,---------,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Todd ], [  Ed  ], ( Lucy )]
            [        ,     |_________|   ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,          |        ,         ,          |        ]
            [[ Tim  ],      ( Cara )     ,      [ Mike ]     , ( Judy ),      [ Jeff ]     , ( Jane )]
            [    |_____________|         ,          |______________|   ,          |______________|   ]
            [         |        ,         ,         ,          |        ,         ,     ,----^----,   ]
            [     (Lilly )     ,         ,         ,      [ Cole ]     ,         , (Kitty ), [ Tom  ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child46() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-46.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,--------------^----,---------,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Todd ], [  Ed  ], ( Lucy )]
            [        ,     |_________|   ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,          |        ,         ,          |        ]
            [[ Tim  ],      ( Cara )     ,      [ Mike ]     , ( Judy ),      [ Jeff ]     , ( Jane )]
            [    |_____________|         ,          |______________|   ,          |______________|   ]
            [         |        ,         ,         ,     ,----^----,   ,         ,     ,----^----,   ]
            [     (Lilly )     ,         ,         , [ Cole ], [Dylan ],         , (Kitty ), [ Tom  ]]
        """.trimIndent()
    }

    @Test
    fun draw2Child35() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-35.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,--------------^----,---------,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Todd ], [  Ed  ], ( Lucy )]
            [        ,     |_________|   ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,          |        ,         ,          |        ]
            [[ Tim  ],      ( Cara )     ,      [ Mike ]     , ( Judy ),      [ Jeff ]     , ( Jane )]
            [    |_____________|         ,          |______________|   ,          |______________|   ]
            [    ,----^----,   ,         ,         ,     ,----^----,   ,         ,     ,----^----,   ]
            [(Lilly ), [ Cole ],         ,         , [ Cole ], [Dylan ],         , (Kitty ), [ Tom  ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child38() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-38.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,     ,------------------------^--------------,---------,---------,   ]
            [[ Bill ], ( Lisa ),         ,         , [Chris ], ( Anne ), [ Todd ], [  Ed  ], ( Lucy )]
            [    |_________|   ,         ,         ,     |_________|   ,         ,     |_________|   ]
            [         |        ,         ,         ,          |        ,         ,          |        ]
            [     [ Carl ]     , ( June ), [ Jack ],      (Molly )     ,         ,      [ Jeff ]     , ( Jane )]
            [         |______________|   ,     |_____________|         ,         ,          |______________|   ]
            [        ,          |        ,         ,         ,         ,         ,         ,          |        ]
            [        ,      [ Paul ]     ,         ,         ,         ,         ,         ,      [ Mike ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child47() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-47.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,     ,------------------------^--------------,---------,---------,   ]
            [[ Bill ], ( Lisa ),         ,         , [Chris ], ( Anne ), [ Todd ], [  Ed  ], ( Lucy )]
            [    |_________|   ,         ,         ,     |_________|   ,         ,     |_________|   ]
            [         |        ,         ,         ,          |        ,         ,          |        ]
            [     [ Carl ]     , ( June ), [ Jack ],      (Molly )     ,         ,      [ Jeff ]     , ( Jane )]
            [         |______________|   ,     |_____________|         ,         ,          |______________|   ]
            [        ,     ,----^----,   ,     ,----^----,   ,         ,         ,         ,     ,----^----,   ]
            [        , [ Paul ], [ Pete ], (Lilly ), [ Cole ],         ,         ,         , [ Mike ], (Molly )]
        """.trimIndent()
    }

    @Test
    fun draw1Child48() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-48.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,-------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ],         , [ Ted  ], (Kitty )]
            [        ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,     ,----^----,   ,         ,         ,          |        ]
            [[ Liam ], ( Cara ), [ Mike ], ( Kim  ), [ Liam ],      ( Lucy )     ]
            [    |_________|   ,     |_________|   ,     |_____________|         ]
            [        ,         ,          |        ,     ,----^----,   ]
            [        ,         ,      [Micky ]     , ( Sue  ), (Molly )]
        """.trimIndent()
    }

    @Test
    fun draw1Child51() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-51.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,-------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ],         , [ Ted  ], (Kitty )]
            [        ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,     ,----^----,   ,         ,         ,          |        ]
            [[ Liam ], ( Cara ), [ Mike ], ( Kim  ), [ Liam ],      ( Lucy )     ]
            [    |_________|   ,     |_________|   ,     |_____________|         ]
            [    ,----^----,   ,          |        ,     ,----^----,   ]
            [(Kelly ), [ Tom  ],      [Micky ]     , ( Sue  ), (Molly )]
        """.trimIndent()
    }

    @Test
    fun draw1Child52() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-52.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,-------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ],         , [ Ted  ], (Kitty )]
            [        ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,     ,----^----,   ,         ,         ,          |        ]
            [[ Liam ], ( Cara ), [ Mike ], ( Kim  ), [ Liam ],      ( Lucy )     ]
            [    |_________|   ,     |_________|   ,     |_____________|         ]
            [         |        ,     ,----^----,   ,     ,----^----,   ]
            [     (Kelly )     , [Micky ], [ Tom  ], ( Sue  ), (Molly )]
        """.trimIndent()
    }

    @Test
    fun draw2Child36() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-36.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,-------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ],         , [ Ted  ], (Kitty )]
            [        ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,     ,----^----,   ,         ,         ,          |        ]
            [[ Liam ], ( Cara ), [ Mike ], ( Kim  ), [ Liam ],      ( Lucy )     ]
            [    |_________|   ,     |_________|   ,     |_____________|         ]
            [    ,----^----,   ,         ,         ,     ,----^----,   ]
            [(Kelly ), (Thomas),         ,         , ( Sue  ), (Molly )]
        """.trimIndent()
    }

    @Test
    fun draw1Child49() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-49.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^------------------------,   ]
            [[ Bill ], ( Lisa ), [Chris ],         ,         , [ Ted  ], (Kitty )]
            [    |_________|   ,         ,         ,         ,     |_________|   ]
            [    ,----^--------------,   ,         ,         ,          |        ]
            [[ Liam ], ( Cara ), ( Kim  ), [ Mike ], [Andrew],      ( Lucy )     ]
            [    |_________|   ,     |_________|   ,     |_____________|         ]
            [        ,         ,     ,----^----,   ,          |        ]
            [        ,         , [Andrew], [ Mark ],      ( Cara )     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child37() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-37.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^------------------------,   ]
            [[ Bill ], ( Lisa ), [Chris ],         ,         , [ Ted  ], (Kitty )]
            [    |_________|   ,         ,         ,         ,     |_________|   ]
            [    ,----^--------------,   ,         ,         ,          |        ]
            [[ Liam ], ( Cara ), ( Kim  ), [ Mike ], [Andrew],      ( Lucy )     ]
            [    |_________|   ,     |_________|   ,     |_____________|         ]
            [        ,         ,     ,----^----,   ,     ,----^----,   ]
            [        ,         , [Andrew], [ Mark ], ( Cara ), (Sarah )]
        """.trimIndent()
    }

    @Test
    fun draw1Child53() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-53.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^------------------------,   ]
            [[ Bill ], ( Lisa ), [Chris ],         ,         , [ Ted  ], (Kitty )]
            [    |_________|   ,         ,         ,         ,     |_________|   ]
            [    ,----^--------------,   ,         ,         ,          |        ]
            [[ Liam ], ( Cara ), ( Kim  ), [ Mike ], [Andrew],      ( Lucy )     ]
            [    |_________|   ,     |_________|   ,     |_____________|         ]
            [         |        ,     ,----^----,   ,          |        ]
            [     [ Dan  ]     , [Andrew], [ Mark ],      ( Cara )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child54() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-54.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^------------------------,   ]
            [[ Bill ], ( Lisa ), [Chris ],         ,         , [ Ted  ], (Kitty )]
            [    |_________|   ,         ,         ,         ,     |_________|   ]
            [    ,----^--------------,   ,         ,         ,          |        ]
            [[ Liam ], ( Cara ), ( Kim  ), [ Mike ], [Andrew],      ( Lucy )     ]
            [    |_________|   ,     |_________|   ,     |_____________|         ]
            [    ,----^----,   ,         ,         ,          |        ]
            [[ Dan  ], [Andrew],         ,         ,      ( Cara )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child55() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-55.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,-------------------,   ]
            [[ Bill ], ( Lisa ), [Chris ],         , [ Ted  ], (Kitty )]
            [    |_________|   ,         ,         ,     |_________|   ]
            [    ,----^--------------,   ,         ,          |        ]
            [[ Liam ], ( Cara ), ( Kim  ), [  Ed  ],      ( Lucy )     ]
            [    |_________|   ,         ,     |_____________|         ]
            [    ,----^----,   ,         ,          |        ]
            [[ Dan  ], [Andrew],         ,      (Marry )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child56() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-56.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,-------------------,   ]
            [[ Bill ], ( Lisa ), [Chris ],         , [ Ted  ], (Kitty )]
            [    |_________|   ,         ,         ,     |_________|   ]
            [    ,----^----,   ,         ,         ,          |        ]
            [[ Liam ], ( Kim  ), [ Mike ], [Andrew],      ( Lucy )     ]
            [        ,     |_________|   ,     |_____________|         ]
            [        ,     ,----^----,   ,          |        ]
            [        , [Andrew], [ Mark ],      ( Cara )     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child38() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-38.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,-------------------,   ]
            [[ Bill ], ( Lisa ), [Chris ],         , [ Ted  ], (Kitty )]
            [    |_________|   ,         ,         ,     |_________|   ]
            [    ,----^----,   ,         ,         ,          |        ]
            [[ Liam ], ( Kim  ), [ Mike ], [Andrew],      ( Lucy )     ]
            [        ,     |_________|   ,     |_____________|         ]
            [        ,     ,----^----,   ,     ,----^----,   ]
            [        , [Andrew], [ Mark ], ( Cara ), [Carol ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child57() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-57.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^--------------,---------,   ]
            [( Lisa ), [ Bill ], ( Cara ), [Chris ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,     |_________|   ]
            [        ,          |        ,     ,----^----,   ]
            [[  Ed  ],      ( Lucy )     , [ Mike ], ( Maye ), [ Tony ]]
            [    |_____________|         ,         ,     |_________|   ]
            [        ,         ,         ,         ,          |        ]
            [        ,         ,         ,         ,      [Micky ]     ]
        """.trimIndent()
    }


    @Test
    fun draw1Child62() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-62.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^--------------,---------,   ]
            [( Lisa ), [ Bill ], ( Cara ), [Chris ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,     |_________|   ]
            [        ,          |        ,     ,----^----,   ]
            [[  Ed  ],      ( Lucy )     , [ Mike ], ( Maye ), [ Tony ]]
            [    |_____________|         ,         ,     |_________|   ]
            [         |        ,         ,         ,     ,----^----,   ]
            [     (Sarah )     ,         ,         , [Micky ], [ Tim  ]]
        """.trimIndent()
    }

    @Test
    fun draw2Child39() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-39.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^--------------,---------,   ]
            [( Lisa ), [ Bill ], ( Cara ), [Chris ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,     |_________|   ]
            [        ,          |        ,     ,----^----,   ]
            [[  Ed  ],      ( Lucy )     , [ Mike ], ( Maye ), [ Tony ]]
            [    |_____________|         ,         ,     |_________|   ]
            [    ,----^----,   ,         ,         ,     ,----^----,   ]
            [(Sarah ), [ Sam  ],         ,         , [Micky ], [ Tim  ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child58() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-58.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^--------------,---------,   ]
            [( Lisa ), [ Bill ], ( Cara ), [Chris ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,     |_________|   ]
            [        ,          |        ,     ,----^--------------,   ]
            [[  Ed  ],      ( Lucy )     , [ Mike ], (Nancy ), ( Maye ), [ Tony ]]
            [    |_____________|         ,     |_________|   ,     |_________|   ]
            [         |        ,         ,     ,----^----,   ,          |        ]
            [     [Micky ]     ,         , (Sarah ), [ Sam  ],      [ Tim  ]     ]
        """.trimIndent()
    }


    @Test
    fun draw1Child63() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-63.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^--------------,---------,   ]
            [( Lisa ), [ Bill ], ( Cara ), [Chris ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,     |_________|   ]
            [        ,          |        ,     ,----^--------------,   ]
            [[  Ed  ],      ( Lucy )     , [ Mike ], (Nancy ), ( Maye ), [ Tony ]]
            [    |_____________|         ,     |_________|   ,     |_________|   ]
            [    ,----^----,   ,         ,     ,----^----,   ,          |        ]
            [[Micky ], [Carlo ],         , (Sarah ), [ Sam  ],      [ Tim  ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child59() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-59.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^--------------,---------,   ]
            [( Lisa ), [ Bill ], ( Cara ), [Chris ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,     |_________|   ]
            [        ,          |        ,     ,----^--------------,   ]
            [[  Ed  ],      ( Lucy )     , [ Mike ], (Nancy ), ( Maye )]
            [    |_____________|         ,     |_________|   ]
            [         |        ,         ,          |        ]
            [     (Sarah )     ,         ,      [ Tim  ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child64() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-64.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^--------------,---------,   ]
            [( Lisa ), [ Bill ], ( Cara ), [Chris ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,     |_________|   ]
            [        ,          |        ,     ,----^--------------,   ]
            [[  Ed  ],      ( Lucy )     , [ Mike ], (Nancy ), ( Maye )]
            [    |_____________|         ,     |_________|   ]
            [    ,----^----,   ,         ,          |        ]
            [(Sarah ), ( June ),         ,      [ Tim  ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child60() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-60.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^--------------,---------,   ]
            [( Lisa ), [ Bill ], ( Cara ), [Chris ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,     |_________|   ]
            [        ,          |        ,     ,----^--------------,   ]
            [        ,      ( Lucy )     , [ Mike ], (Nancy ), ( Maye ), [ Tony ]]
            [        ,         ,         ,     |_________|   ,     |_________|   ]
            [        ,         ,         ,          |        ,     ,----^----,   ]
            [        ,         ,         ,      [ Andy ]     , (Sarah ), [ Tim  ]]
        """.trimIndent()
    }


    @Test
    fun draw2Child40() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-40.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^--------------,---------,   ]
            [( Lisa ), [ Bill ], ( Cara ), [Chris ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,     |_________|   ]
            [        ,          |        ,     ,----^--------------,   ]
            [        ,      ( Lucy )     , [ Mike ], (Nancy ), ( Maye ), [ Tony ]]
            [        ,         ,         ,     |_________|   ,     |_________|   ]
            [        ,         ,         ,     ,----^----,   ,     ,----^----,   ]
            [        ,         ,         , [ Andy ], [ Sam  ], (Sarah ), [ Tim  ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child61() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-61.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^------------------------,---------,   ]
            [( Lisa ), [ Bill ], ( Cara ),         , [Chris ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,     ,----^----,   ]
            [        ,      ( Lucy )     , [ Mike ], (Nancy ), ( Maye ), [ Tony ]]
            [        ,         ,         ,     |_________|   ,     |_________|   ]
            [        ,         ,         ,     ,----^----,   ,          |        ]
            [        ,         ,         , [ Finn ], (Sarah ),      [Andrew]     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child41() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-41.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^------------------------,---------,   ]
            [( Lisa ), [ Bill ], ( Cara ),         , [Chris ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,     ,----^----,   ]
            [        ,      ( Lucy )     , [ Mike ], (Nancy ), ( Maye ), [ Tony ]]
            [        ,         ,         ,     |_________|   ,     |_________|   ]
            [        ,         ,         ,     ,----^----,   ,     ,----^----,   ]
            [        ,         ,         , [ Finn ], (Sarah ), ( June ), [Andrew]]
        """.trimIndent()
    }

    @Test
    fun draw1Child65() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-65.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^------------------------,---------,   ]
            [( Lisa ), [ Bill ], ( Cara ),         , [Chris ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,     ,----^----,   ]
            [        ,      ( Lucy )     , [ Mike ], (Nancy ), ( Maye )]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,         ,     ,----^----,   ]
            [        ,         ,         , [ Finn ], (Sarah )]
        """.trimIndent()
    }

    @Test
    fun draw1Child66() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-66.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^--------------,-------------------,   ]
            [[ Bill ], [Chris ], ( Lisa ),         , [  Ed  ], ( Lucy ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^--------------,   ,          |        ]
            [        , [ Mike ], (Sarah ), ( Maye ),      [ Ted  ]     , ( Lucy )]
            [        ,     |_________|   ,         ,          |______________|   ]
            [        ,          |        ,         ,         ,          |        ]
            [        ,      ( June )     ,         ,         ,      [ Sam  ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child72() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-72.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^--------------,-------------------,   ]
            [[ Bill ], [Chris ], ( Lisa ),         , [  Ed  ], ( Lucy ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^--------------,   ,          |        ]
            [        , [ Mike ], (Sarah ), ( Maye ),      [ Ted  ]     , ( Lucy )]
            [        ,     |_________|   ,         ,          |______________|   ]
            [        ,          |        ,         ,         ,     ,----^----,   ]
            [        ,      ( June )     ,         ,         , [ Sam  ], [ Tim  ]]
        """.trimIndent()
    }


    @Test
    fun draw2Child42() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-42.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^--------------,-------------------,   ]
            [[ Bill ], [Chris ], ( Lisa ),         , [  Ed  ], ( Lucy ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^--------------,   ,          |        ]
            [        , [ Mike ], (Sarah ), ( Maye ),      [ Ted  ]     , ( Lucy )]
            [        ,     |_________|   ,         ,          |______________|   ]
            [        ,     ,----^----,   ,         ,         ,     ,----^----,   ]
            [        , ( June ), [Micky ],         ,         , [ Sam  ], [ Tim  ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child70() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-70.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [    ,---------,------------------------^--------------,-------------------,   ]
            [[ Bill ], [Chris ], ( Lisa ),         ,         , [  Ed  ], ( Lucy ), ( Anne )]
            [        ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,     ,----^--------------,   ,         ,          |        ]
            [        , [ Mike ], (Sarah ), ( Maye ), [Andrew],      [ Ted  ]     , ( Lucy )]
            [        ,     |_________|   ,     |_________|   ,          |______________|   ]
            [        ,         ,         ,     ,----^----,   ,         ,          |        ]
            [        ,         ,         , ( June ), [ Tim  ],         ,      ( Kim  )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child73() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-73.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [    ,---------,------------------------^--------------,-------------------,   ]
            [[ Bill ], [Chris ], ( Lisa ),         ,         , [  Ed  ], ( Lucy ), ( Anne )]
            [        ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,     ,----^--------------,   ,         ,          |        ]
            [        , [ Mike ], (Sarah ), ( Maye ), [Andrew],      [ Ted  ]     , ( Lucy )]
            [        ,     |_________|   ,     |_________|   ,          |______________|   ]
            [        ,          |        ,     ,----^----,   ,         ,          |        ]
            [        ,      [ Sam  ]     , ( June ), [ Tim  ],         ,      ( Kim  )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child74() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-74.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [    ,---------,------------------------^--------------,-------------------,   ]
            [[ Bill ], [Chris ], ( Lisa ),         ,         , [  Ed  ], ( Lucy ), ( Anne )]
            [        ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,     ,----^--------------,   ,         ,          |        ]
            [        , [ Mike ], (Sarah ), ( Maye ), [Andrew],      [ Ted  ]     , ( Lucy )]
            [        ,     |_________|   ,     |_________|   ,          |______________|   ]
            [        ,     ,----^----,   ,         ,         ,         ,          |        ]
            [        , [ Tim  ], [ Sam  ],         ,         ,         ,      ( Kim  )     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child43() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-43.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [    ,---------,------------------------^--------------,-------------------,   ]
            [[ Bill ], [Chris ], ( Lisa ),         ,         , [  Ed  ], ( Lucy ), ( Anne )]
            [        ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,     ,----^--------------,   ,         ,          |        ]
            [        , [ Mike ], (Sarah ), ( Maye ), [Andrew],      [ Ted  ]     , ( Lucy )]
            [        ,     |_________|   ,     |_________|   ,          |______________|   ]
            [        ,     ,----^----,   ,     ,----^----,   ,         ,     ,----^----,   ]
            [        , [ Sam  ], [ Tony ], ( June ), [ Tim  ],         , ( Kim  ), ( Sue  )]
        """.trimIndent()
    }

    @Test
    fun draw1Child67() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-67.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^--------------,-------------------,   ]
            [[ Bill ], [Chris ], ( Lisa ),         , [  Ed  ], ( Lucy ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,   ,         ,          |        ]
            [[Andrew], ( Maye ), [ Mike ], ( Nina ),      [ Ted  ]     ]
            [    |_________|   ,     |_________|   ]
            [    ,----^----,   ,          |        ]
            [[ Tim  ], (Sarah ),      ( June )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child68() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-68.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [    ,---------,------------------------^--------------,-------------------,   ]
            [[ Bill ], [Chris ], ( Lisa ),         ,         , [  Ed  ], ( Lucy ), ( Anne )]
            [        ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,     ,----^----,   ,         ,         ,          |        ]
            [[Andrew], ( Maye ), [ Mike ], ( Nina ), [ Sam  ],      (Sarah )     ]
            [    |_________|   ,     |_________|   ,     |_____________|         ]
            [        ,         ,     ,----^----,   ,          |        ]
            [        ,         , [ Tim  ], [Thomas],      ( June )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child75() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-75.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [    ,---------,------------------------^--------------,-------------------,   ]
            [[ Bill ], [Chris ], ( Lisa ),         ,         , [  Ed  ], ( Lucy ), ( Anne )]
            [        ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,     ,----^----,   ,         ,         ,          |        ]
            [[Andrew], ( Maye ), [ Mike ], ( Nina ), [ Sam  ],      (Sarah )     ]
            [    |_________|   ,     |_________|   ,     |_____________|         ]
            [         |        ,     ,----^----,   ,          |        ]
            [     ( Jane )     , [ Tim  ], [Thomas],      ( June )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child71() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-71.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^--------------,-------------------,   ]
            [[ Bill ], [Chris ], ( Lisa ),         , [  Ed  ], ( Lucy ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,   ,         ,          |        ]
            [[Andrew], ( Maye ), [ Mike ], [ Sam  ],      (Sarah )     ]
            [    |_________|   ,         ,     |_____________|         ]
            [    ,----^----,   ,         ,          |        ]
            [( June ), [Thomas],         ,      [ Tim  ]     ]
        """.trimIndent()
    }

    @Test
    fun draw2Child44() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-2-children-4th-gen-44.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [    ,---------,------------------------^--------------,-------------------,   ]
            [[ Bill ], [Chris ], ( Lisa ),         ,         , [  Ed  ], ( Lucy ), ( Anne )]
            [        ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,     ,----^----,   ,         ,         ,          |        ]
            [        , ( Maye ), ( June ), [ Sam  ], [ Tim  ],      (Sarah )     ]
            [        ,         ,     |_________|   ,     |_____________|         ]
            [        ,         ,     ,----^----,   ,     ,----^----,   ]
            [        ,         , ( June ), (Kitty ), ( Sue  ), [ Tom  ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child76() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-76.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,---------,----^--------------,---------,   ]
            [        , [ Bill ], ( Lisa ), ( Cara ), [Chris ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,     ,----^--------------,   ]
            [[ Tim  ],      ( Lucy )     ,         , [ Mike ], (Nancy ), ( Maye ), [ Sam  ]]
            [    |_____________|         ,         ,     |_________|   ,     |_________|   ]
            [    ,----^----,   ,         ,         ,         ,         ,          |        ]
            [( Jane ), ( June ),         ,         ,         ,         ,      [ Tom  ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child79() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-79.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,---------,----^--------------,---------,   ]
            [        , [ Bill ], ( Lisa ), ( Cara ), [Chris ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,     ,----^--------------,   ]
            [[ Tim  ],      ( Lucy )     ,         , [ Mike ], (Nancy ), ( Maye ), [ Sam  ]]
            [    |_____________|         ,         ,     |_________|   ,     |_________|   ]
            [    ,----^----,   ,         ,         ,     ,----^----,   ,          |        ]
            [( Jane ), ( June ),         ,         , ( Cara ), [Billy ],      [ Tom  ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child77() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-77.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,     ,---------,----^--------------,---------,   ]
            [        , [ Bill ], ( Lisa ), ( Cara ), [Chris ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,     ,----^----,   ]
            [[ Tim  ],      ( Lucy )     ,         , [ Mike ], ( Maye ), [ Sam  ]]
            [    |_____________|         ,         ,         ,     |_________|   ]
            [         |        ,         ,         ,         ,     ,----^----,   ]
            [     [Carlo ]     ,         ,         ,         , ( June ), [ Tom  ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child78() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-78.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,---------,----^--------------,---------,   ]
            [[ Bill ], ( Lisa ), ( Cara ), [Chris ], (Kitty ), ( Anne )]
            [    |_________|   ,         ,     |_________|   ]
            [         |        ,         ,     ,----^----,   ]
            [     ( Lucy )     ,         , [ Mike ], ( Maye ), [ Sam  ]]
            [        ,         ,         ,         ,     |_________|   ]
            [        ,         ,         ,         ,          |        ]
            [        ,         ,         ,         ,      ( June )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child80() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-80.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,         ,     ,----^----,   ]
            [        ,         , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^--------------,---------,   ]
            [[River ], (Kitty ), [ Will ], ( Lucy ), (Sarah ), [Teddy ]]
            [    |_________|   ,     |_________|   ]
            [    ,----^----,   ,          |        ]
            [[ Kyle ], ( Kim  ),      ( June )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child81() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-81.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,         ,     ,----^----,   ]
            [        ,         , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^------------------------,---------,   ]
            [[River ], (Kitty ), [ Will ], ( Lucy ), [ Tim  ], (Sarah ), [Teddy ]]
            [    |_________|   ,     |_________|   ,     |_________|   ]
            [         |        ,          |        ,          |        ]
            [     ( June )     ,      [ Jim  ]     ,      [ Tom  ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child86() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-86.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,         ,     ,----^----,   ]
            [        ,         , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^------------------------,---------,   ]
            [[River ], (Kitty ), [ Will ], ( Lucy ), [ Tim  ], (Sarah ), [Teddy ]]
            [    |_________|   ,     |_________|   ,     |_________|   ]
            [    ,----^----,   ,          |        ,     ,----^----,   ]
            [( June ), ( Jane ),      [ Jim  ]     , [ Tom  ], [ Sam  ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child87() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-87.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,         ,     ,----^----,   ]
            [        ,         , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^------------------------,---------,   ]
            [[River ], (Kitty ), [ Will ], ( Lucy ), [ Tim  ], (Sarah ), [Teddy ]]
            [    |_________|   ,     |_________|   ,     |_________|   ]
            [         |        ,         ,         ,     ,----^----,   ]
            [     ( June )     ,         ,         , [ Tom  ], [ Sam  ]]
        """.trimIndent()
    }

    @Test
    fun draw1Child82() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-82.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,         ,     ,----^----,   ]
            [        ,         , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^------------------------,---------,   ]
            [[River ], (Kitty ), [ Will ], ( Lucy ), [ Tim  ], (Sarah ), [Teddy ], ( Tim  )]
            [    |_________|   ,     |_________|   ,     |_________|   ,     |_________|   ]
            [         |        ,          |        ,          |        ,          |        ]
            [     [ Kyle ]     ,      [ Sam  ]     ,      ( Kim  )     ,      ( June )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child88() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-88.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,         ,     ,----^----,   ]
            [        ,         , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^------------------------,---------,   ]
            [[River ], (Kitty ), [ Will ], ( Lucy ), [ Tim  ], (Sarah ), [Teddy ], ( Tim  )]
            [    |_________|   ,     |_________|   ,     |_________|   ,     |_________|   ]
            [    ,----^----,   ,          |        ,     ,----^----,   ,          |        ]
            [[ Kyle ], [Mandy ],      [ Sam  ]     , ( Kim  ), [ Mike ],      ( June )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child89() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-89.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,         ,     ,----^----,   ]
            [        ,         , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^------------------------,---------,   ]
            [[River ], (Kitty ), [ Will ], ( Lucy ), [ Tim  ], (Sarah ), [Teddy ], ( Tim  )]
            [    |_________|   ,     |_________|   ,     |_________|   ,     |_________|   ]
            [    ,----^----,   ,         ,         ,          |        ,          |        ]
            [[ Kyle ], [Mandy ],         ,         ,      ( Kim  )     ,      ( June )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child90() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-90.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,         ,     ,----^----,   ]
            [        ,         , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^------------------------,---------,   ]
            [[River ], (Kitty ), [ Will ], ( Lucy ), [ Tim  ], (Sarah ), [Teddy ], ( Tim  )]
            [    |_________|   ,     |_________|   ,     |_________|   ,     |_________|   ]
            [    ,----^----,   ,         ,         ,         ,         ,          |        ]
            [[ Kyle ], [Mandy ],         ,         ,         ,         ,      ( June )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child83() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-83.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,         ,     |_________|   ]
            [        ,         ,         ,         ,     ,----^----,   ]
            [        ,         ,         , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,         ,         ,     |_________|   ]
            [        ,     ,-------------------,----^----,-------------------,   ]
            [[River ], (Kitty ), [ Luke ], (Mandy ), [ Tim  ], ( Kim  ), (Sindy ), [ Tom  ]]
            [    |_________|   ,     |_________|   ,     |_________|   ,     |_________|   ]
            [         |        ,         ,         ,     ,----^----,   ]
            [     ( June )     ,         ,         , [ Kyle ], ( Kim  )]
        """.trimIndent()
    }

    @Test
    fun draw1Child91() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-91.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,         ,     ,----^----,   ]
            [        ,         , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^----,-------------------,   ]
            [(Kitty ), [ Luke ], (Mandy ), [ Tim  ], ( Kim  ), (Sindy ), [ Tom  ]]
            [        ,     |_________|   ,     |_________|   ,     |_________|   ]
            [        ,         ,         ,     ,----^----,   ]
            [        ,         ,         , [ Kyle ], ( Kim  )]
        """.trimIndent()
    }

    @Test
    fun draw1Child84() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-84.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,         ,     ,----^----,   ]
            [        ,         , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,         ,     |_________|   ]
            [        ,     ,---------,----^----,-------------------,   ]
            [[River ], (Kitty ), (Mandy ), [ Tim  ], ( Kim  ), (Sindy ), [ Tom  ]]
            [    |_________|   ,         ,     |_________|   ,     |_________|   ]
            [    ,----^----,   ,         ,         ,         ,          |        ]
            [( June ), [Jimmy ],         ,         ,         ,      (James )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child85() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-1-children-4th-gen-85.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,         ,     ,----^----,   ]
            [        , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,---------,   ]
            [[River ], (Kitty ), [ Will ], (Sarah ), [Teddy ], ( Tim  )]
            [    |_________|   ,         ,         ,     |_________|   ]
            [         |        ,         ,         ,     ,----^----,   ]
            [     [James ]     ,         ,         , ( June ), ( Sam  )]
        """.trimIndent()
    }

    /******************************
     ********  3 Children  ********
     ******************************/

    @Test
    fun draw3Child() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-3-children-4th-gen.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], [ Ted  ], (Kitty )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,   ,         ,     ,----^--------------,   ]
            [[ Sam  ], ( Rose ), [ Mike ], [ Luke ], ( Lucy ),         , ( Maye ), [Carlo ]]
            [    |_________|   ,         ,     |_________|   ,         ,     |_________|   ]
            [         |        ,         ,     ,----^----,---------,   ,          |        ]
            [     [ Jon  ]     ,         , ( June ), [James ], ( Jane ),      [ Tim  ]     ]
        """.trimIndent()
    }

    @Test
    fun draw3Child5() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-3-children-4th-gen-5.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], [ Ted  ], (Kitty )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,   ,         ,     ,----^--------------,   ]
            [[ Sam  ], ( Rose ), [ Mike ], [ Luke ], ( Lucy ),         , ( Maye ), [Carlo ]]
            [    |_________|   ,         ,     |_________|   ,         ,     |_________|   ]
            [    ,----^----,   ,         ,     ,----^----,---------,   ,     ,----^----,---------,   ]
            [[ Jon  ], ( Sue  ),         , ( June ), [James ], ( Jane ), [ Tim  ], [ Tom  ], [ Tony ]]
        """.trimIndent()
    }

    @Test
    fun draw3Child6() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-3-children-4th-gen-6.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], [ Ted  ], (Kitty )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,   ,         ,     ,----^--------------,   ]
            [[ Sam  ], ( Rose ), [ Mike ], [ Luke ], ( Lucy ),         , ( Maye ), [Carlo ]]
            [    |_________|   ,         ,     |_________|   ,         ,     |_________|   ]
            [    ,----^----,---------,   ,     ,----^----,---------,   ,     ,----^----,---------,   ]
            [[ Jon  ], ( Sue  ), ( Beck ), ( June ), [James ], ( Jane ), [ Tim  ], [ Tom  ], [ Tony ]]
        """.trimIndent()
    }

    @Test
    fun draw3Child2() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-3-children-4th-gen-2.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,     ,-------------------,----^------------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ],         ,         , [ Ted  ], (Kitty )]
            [        ,     |_________|   ,         ,         ,         ,     |_________|   ]
            [        ,     ,----^----,   ,         ,         ,         ,     ,----^----,   ]
            [[ Sam  ], ( Rose ), [ Mike ], ( Cara ),         , [ Luke ], ( Lucy ), ( Maye ), [Carlo ]]
            [    |_________|   ,     |_________|   ,         ,     |_________|   ,     |_________|   ]
            [        ,         ,     ,----^----,---------,   ,     ,----^----,   ]
            [        ,         , ( June ), [ Tim  ], [ Tom  ], (Sindy ), [James ]]
        """.trimIndent()
    }

    @Test
    fun draw3Child7() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-3-children-4th-gen-7.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,     ,-------------------,----^------------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ],         ,         , [ Ted  ], (Kitty )]
            [        ,     |_________|   ,         ,         ,         ,     |_________|   ]
            [        ,     ,----^----,   ,         ,         ,         ,     ,----^--------------,   ]
            [[ Sam  ], ( Rose ), [ Mike ], ( Cara ),         , [ Luke ], ( Lucy ),         , ( Maye ), [Carlo ]]
            [    |_________|   ,     |_________|   ,         ,     |_________|   ,         ,     |_________|   ]
            [        ,         ,     ,----^----,---------,   ,     ,----^----,---------,   ,     ,----^----,---------,   ]
            [        ,         , ( June ), [ Tim  ], [ Tom  ], (Sindy ), [James ], [ Kyle ], ( Jane ), ( Sue  ), [ Jim  ]]
        """.trimIndent()
    }

    @Test
    fun draw3Child8() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-3-children-4th-gen-8.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,     ,-------------------,----^----------------------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ],         ,         ,         , [ Ted  ], (Kitty )]
            [        ,     |_________|   ,         ,         ,         ,         ,     |_________|   ]
            [        ,     ,----^--------------,   ,         ,         ,         ,     ,----^--------------,   ]
            [[ Sam  ], ( Rose ),         , [ Mike ], ( Cara ),         , [ Luke ], ( Lucy ),         , ( Maye ), [Carlo ]]
            [    |_________|   ,         ,     |_________|   ,         ,     |_________|   ,         ,     |_________|   ]
            [    ,----^----,---------,   ,     ,----^----,---------,   ,     ,----^----,---------,   ,     ,----^----,---------,   ]
            [[ Jim  ], ( Kim  ), [Thomas], ( June ), [ Tim  ], [ Tom  ], (Sindy ), [James ], [ Kyle ], ( Jane ), ( Sue  ), [ Jim  ]]
        """.trimIndent()
    }

    @Test
    fun draw3Child10() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-3-children-4th-gen-10.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,-------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ],         , [ Ted  ], (Kitty )]
            [        ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,     ,----^--------------,   ,         ,     ,----^----,   ]
            [[ Sam  ], ( Rose ),         , [ Mike ], ( Cara ), ( Lucy ), ( Maye ), [Carlo ]]
            [    |_________|   ,         ,     |_________|   ,         ,     |_________|   ]
            [    ,----^----,---------,   ,     ,----^----,---------,   ,     ,----^----,---------,   ]
            [[ Jim  ], ( Kim  ), [Thomas], (Sindy ), [ Tim  ], [ Tom  ], ( Jane ), ( Sue  ), [ Jim  ]]
        """.trimIndent()
    }

    @Test
    fun draw3Child1() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-3-children-4th-gen-1.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,-------------------,   ]
            [[ Bill ], ( Lisa ), [Chris ],         , [ Ted  ], (Kitty )]
            [    |_________|   ,         ,         ,     |_________|   ]
            [    ,----^--------------,   ,         ,     ,----^----,   ]
            [[ Mike ], ( June ), ( Cara ), [ Jim  ], ( Lucy ), ( Maye )]
            [    |_________|   ,         ,     |_________|   ]
            [    ,----^----,---------,   ,     ,----^----,---------,   ]
            [( Sin  ), ( Kim  ), [Thomas], [ Kyle ], [ Tim  ], ( Jane )]
        """.trimIndent()
    }

    @Test
    fun draw3Child9() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-3-children-4th-gen-9.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,---------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ], [ Ted  ], (Kitty )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,   ,         ,     ,----^----,   ]
            [[ Mike ], ( June ), ( Cara ), [ Jim  ], ( Lucy ), ( Maye )]
            [    |_________|   ,         ,     |_________|   ]
            [    ,----^----,---------,   ,     ,----^----,---------,   ]
            [( Sin  ), ( Kim  ), [Thomas], [ Kyle ], [ Tim  ], ( Jane )]
        """.trimIndent()
    }

    @Test
    fun draw3Child3() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-3-children-4th-gen-3.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,-------------------,   ]
            [[ Bill ], ( Lisa ), [Chris ],         , [ Ted  ], (Kitty )]
            [    |_________|   ,         ,         ,     |_________|   ]
            [    ,----^--------------,   ,         ,     ,----^----,   ]
            [[ Mike ], ( June ), ( Cara ), [ Jim  ], ( Lucy ), ( Maye ), [ Tom  ]]
            [    |_________|   ,         ,     |_________|   ,     |_________|   ]
            [    ,----^----,---------,   ,         ,         ,     ,----^----,---------,   ]
            [[ Sam  ], [ Ryan ], ( Rita ),         ,         , ( Sin  ), ( Kim  ), [ Kay  ]]
        """.trimIndent()
    }

    @Test
    fun draw3Child13() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-3-children-4th-gen-13.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,-------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ],         , [ Ted  ], (Kitty )]
            [        ,     |_________|   ,         ,         ,     |_________|   ]
            [        ,     ,----^--------------,   ,         ,     ,----^----,   ]
            [        , [ Mike ], ( June ), ( Cara ), [ Jim  ], ( Lucy ), ( Maye ), [ Tom  ]]
            [        ,     |_________|   ,         ,     |_________|   ,     |_________|   ]
            [    ,---------,----^----,---------,   ,         ,         ,     ,----^----,---------,   ]
            [[ Sam  ], [ Ryan ], ( Rita ), [ Liam ],         ,         , ( Sin  ), ( Kim  ), [ Kay  ]]
        """.trimIndent()
    }

    @Test
    fun draw3Child11() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-3-children-4th-gen-11.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,-------------------,   ]
            [[ Bill ], ( Lisa ), [Chris ],         , [ Ted  ], (Kitty )]
            [    |_________|   ,         ,         ,     |_________|   ]
            [    ,----^--------------,   ,         ,     ,----^----,   ]
            [[ Mike ], ( June ), ( Cara ), [ Jim  ], ( Lucy ), ( Maye ), [ Tom  ]]
            [    |_________|   ,         ,     |_________|   ,     |_________|   ]
            [    ,----^----,---------,   ,          |        ,     ,----^----,---------,   ]
            [[ Sam  ], [ Ryan ], ( Rita ),      [ Kay  ]     , ( Sin  ), ( Kim  ), [ Kay  ]]
        """.trimIndent()
    }

    @Test
    fun draw3Child12() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-3-children-4th-gen-12.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,-------------------,   ]
            [[ Bill ], ( Lisa ), [Chris ],         , [ Ted  ], (Kitty )]
            [    |_________|   ,         ,         ,     |_________|   ]
            [    ,----^--------------,   ,         ,     ,----^--------------,   ]
            [[ Mike ], ( June ), ( Cara ), [ Jim  ], ( Lucy ),         , ( Maye ), [ Tom  ]]
            [    |_________|   ,         ,     |_________|   ,         ,     |_________|   ]
            [    ,----^----,---------,   ,     ,----^----,---------,   ,     ,----^----,---------,   ]
            [[ Sam  ], [ Ryan ], ( Rita ), [ Kay  ], [ Tim  ], ( Sue  ), ( Sin  ), ( Kim  ), [ Kay  ]]
        """.trimIndent()
    }

    @Test
    fun draw3Child4() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-3-children-4th-gen-4.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^----------------------------------,   ]
            [[ Bill ], ( Lisa ), [Chris ],         ,         ,         , [ Ted  ], (Kitty )]
            [    |_________|   ,         ,         ,         ,         ,     |_________|   ]
            [    ,----^--------------,   ,         ,         ,         ,     ,----^----,   ]
            [[ Mike ], ( June ), ( Cara ), [ Tim  ],         , [ Jim  ], ( Lucy ), ( Maye ), [ Tom  ]]
            [    |_________|   ,     |_________|   ,         ,     |_________|   ,     |_________|   ]
            [         |        ,     ,----^----,---------,   ,          |        ,     ,----^----,   ]
            [     [Austin]     , ( Sin  ), [Thomas], [ Kyle ],      [ Liam ]     , [ Ryan ], [ Tony ]]
        """.trimIndent()
    }

    @Test
    fun draw3Child14() {
        val drawer = FamilyTree(getResourceAs("4thGen/family-3-children-4th-gen-14.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,     ,-------------------,----^----------------------------------,   ]
            [        , [ Bill ], ( Lisa ), [Chris ],         ,         ,         , [ Ted  ], (Kitty )]
            [        ,     |_________|   ,         ,         ,         ,         ,     |_________|   ]
            [    ,--------------^--------------,   ,         ,         ,         ,     ,----^----,   ]
            [[ Mike ], ( June ),         , ( Cara ), [ Tim  ],         , [ Jim  ], ( Lucy ), ( Maye ), [ Tom  ]]
            [    |_________|   ,         ,     |_________|   ,         ,     |_________|   ,     |_________|   ]
            [    ,----^----,---------,   ,     ,----^----,---------,   ,          |        ,     ,----^----,   ]
            [[Austin], (Sindy ), (Susan ), ( Sin  ), [Thomas], [ Kyle ],      [ Liam ]     , [ Ryan ], [ Tony ]]
        """.trimIndent()
    }
}
