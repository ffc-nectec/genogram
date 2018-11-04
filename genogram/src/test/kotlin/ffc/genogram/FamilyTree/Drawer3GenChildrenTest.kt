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
import ffc.genogram.Util.displayObjectResult
import ffc.genogram.getResourceAs
import org.amshove.kluent.`should equal`
import org.junit.Test

class Drawer3GenChildrenTest {

    @Test
    fun draw1Child() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-1-child-3rd-gen.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[ Bill ], ( Lisa ), [  Ed  ]]
            [    |_________|   ]
            [         |        ]
            [     [River ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child2() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-1-child-3rd-gen-2.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,    ]
            [[ Bill ], ( Lisa ), ( Anne ), [  Ed  ]]
            [    |_________|   ,     |_________|   ]
            [        ,         ,          |        ]
            [        ,         ,      ( Maye )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child3() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-1-child-3rd-gen-3.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String
        /*canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,------------------,-----^----,---------,    ]
            [[ Bill ], [  Ed  ], ( Anne ), [ Ted  ], ( Anne ), [Chris ]]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,         ,         ,         ,          |        ]
            [        ,         ,         ,         ,      [ Mike ]     ]
        """.trimIndent()*/

        // Object
        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^----,---------,    ]
            [[ Bill ], [  Ed  ], ( Anne ), [ Ted  ], ( Anne ), [Chris ]]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,         ,         ,         ,          |        ]
            [        ,         ,         ,         ,      [ Mike ]     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child4() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-1-child-3rd-gen-4.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,---------,----^--------------,---------,    ]
            [[ Bill ], ( Anne ), ( Lisa ), [Chris ], ( Cara ), ( Anne )]
            [    |_________|   ,         ,     |_________|   ]
            [        ,         ,         ,          |        ]
            [        ,         ,         ,      ( Lucy )     ]
        """.trimIndent()
    }

    @Test
    fun draw1Child5() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-1-child-3rd-gen-5.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^--------------,---------,    ]
            [( Bill ), [Chris ], ( Anne ), [Chris ], ( Cara ), ( Anne )]
            [        ,     |_________|   ,     |_________|   ]
            [        ,         ,         ,          |        ]
            [        ,         ,         ,      [ Kaye ]     ]
        """.trimIndent()
    }

    @Test
    fun draw2Children() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-2-children-3rd-gen.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[ Bill ], ( Lisa ), [  Ed  ]]
            [    |_________|   ]
            [    ,----^----,   ]
            [[River ], [ Will ]]
        """.trimIndent()
    }

    @Test
    fun draw2Children2() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-2-children-3rd-gen-2.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,---------,----^----,-------------------,    ]
            [[ Bill ], ( Anne ), ( Lisa ), [Chris ], ( Cara ), ( Anne )]
            [    |_________|   ,         ,     |_________|   ]
            [         |        ,         ,          |        ]
            [     (Kitty )     ,         ,      [ Mike ]     ]
        """.trimIndent()
    }

    @Test
    fun draw2Children3() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-2-children-3rd-gen-3.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^--------------,---------,    ]
            [[ Bill ], [Chris ], ( Lisa ), [  Ed  ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,     |_________|   ]
            [        ,          |        ,          |        ]
            [        ,      [ Mike ]     ,      [ Kaye ]     ]
        """.trimIndent()
    }

    @Test
    fun draw2Children4() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-2-children-3rd-gen-4.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String
        /*canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,------------------,-----^----,---------,    ]
            [[ Bill ], [Chris ], ( Lisa ), (Kitty ), [  Ed  ], ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,          |        ]
            [        ,      ( Maye )     ,         ,      ( Lucy )     ]
        """.trimIndent()*/

        // Object
        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^----,---------,    ]
            [[ Bill ], [Chris ], ( Lisa ), (Kitty ), [  Ed  ], ( Anne )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,          |        ,         ,          |        ]
            [        ,      ( Maye )     ,         ,      ( Lucy )     ]
        """.trimIndent()
    }

    @Test
    fun draw2Children5() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-2-children-3rd-gen-5.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,---------,---------,    ]
            [[ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Ted  ], ( Lucy ), [  Ed  ]]
            [    |_________|   ,     |_________|   ,         ,     |_________|   ]
            [    ,----^----,   ]
            [[ Mike ], ( Cara )]
        """.trimIndent()
    }

    @Test
    fun draw3Children() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-3-children-3rd-gen.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^----,   ]
            [[ Bill ], ( Lisa ), [  Ed  ]]
            [    |_________|   ]
            [    ,----^----,---------,   ]
            [[River ], [ Will ], (Sarah )]
        """.trimIndent()
    }

    @Test
    fun draw3Children2() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-3-children-3rd-gen-2.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^--------------,    ]
            [[ Bill ], ( Lisa ), [Chris ], ( Anne ), ( Lucy ), [  Ed  ]]
            [    |_________|   ,     |_________|   ,     |_________|   ]
            [         |        ,          |        ,          |        ]
            [     [ Mike ]     ,      (Kitty )     ,      ( Lucy )     ]
        """.trimIndent()
    }

    @Test
    fun draw3Children3() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-3-children-3rd-gen-3.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,---------,---------,    ]
            [[ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Todd ], [  Ed  ], ( Lucy )]
            [    |_________|   ,     |_________|   ,         ,     |_________|   ]
            [         |        ,          |        ,         ,          |        ]
            [     ( Cara )     ,      [ Mike ]     ,         ,      [ Jeff ]     ]
        """.trimIndent()
    }

    @Test
    fun draw3Children4() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-3-children-3rd-gen-4.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^--------------,---------,    ]
            [[ Bill ], [Chris ], ( Lisa ), [ Ted  ], ( Anne ), ( Lucy ), [  Ed  ]]
            [        ,     |_________|   ,     |_________|   ,     |_________|   ]
            [        ,          |        ,          |        ,          |        ]
            [        ,      [ Mike ]     ,      ( Cara )     ,      [ Jeff ]     ]
        """.trimIndent()
    }

    @Test
    fun draw3Children5() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-3-children-3rd-gen-5.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,    ]
            [[ Bill ], ( Lisa ), [Chris ], [ Ted  ], (Kitty )]
            [    |_________|   ,         ,     |_________|   ]
            [    ,----^----,   ,         ,          |        ]
            [( Cara ), [ Mike ],         ,      ( Lucy )     ]
        """.trimIndent()
    }

    @Test
    fun draw3Children6() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-3-children-3rd-gen-6.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^--------------,---------,    ]
            [( Lisa ), [ Bill ], ( Cara ), [Chris ], (Kitty ), ( Anne )]
            [        ,     |_________|   ,     |_________|   ]
            [        ,          |        ,     ,----^----,   ]
            [        ,      ( Lucy )     , [ Mike ], ( Maye )]
        """.trimIndent()
    }

    @Test
    fun draw3Children7() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-3-children-3rd-gen-7.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^----,-------------------,    ]
            [[ Bill ], [Chris ], ( Lisa ), [  Ed  ], ( Lucy ), ( Anne )]
            [        ,     |_________|   ,     |_________|   ]
            [        ,     ,----^----,   ,          |        ]
            [        , [ Mike ], ( Maye ),      [ Ted  ]     ]
        """.trimIndent()
    }

    @Test
    fun draw3Children8() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-3-children-3rd-gen-8.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,---------,----^--------------,---------,    ]
            [[ Bill ], ( Lisa ), ( Cara ), [Chris ], (Kitty ), ( Anne )]
            [    |_________|   ,         ,     |_________|   ]
            [         |        ,         ,     ,----^----,   ]
            [     ( Lucy )     ,         , [ Mike ], ( Maye )]
        """.trimIndent()
    }

    @Test
    fun draw4Children() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-4-children-3rd-gen.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,         ,     ,----^----,   ]
            [        , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,     |_________|   ]
            [    ,---------,----^----,---------,   ]
            [[River ], [ Will ], (Sarah ), [Teddy ]]
        """.trimIndent()
    }

    @Test
    fun draw4Children2() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-4-children-3rd-gen-2.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [    ,--------------^----,---------,    ]
            [[ Bill ], ( Lisa ), [Chris ], [ Ted  ], (Kitty )]
            [    |_________|   ,         ,     |_________|   ]
            [    ,----^----,   ,         ,     ,----^----,   ]
            [( Cara ), [ Mike ],         , ( Lucy ), ( Maye )]
        """.trimIndent()
    }

    @Test
    fun draw4Children3() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-4-children-3rd-gen-3.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^----,-------------------,    ]
            [[ Bill ], [Chris ], ( Lisa ), [  Ed  ], ( Lucy ), ( Anne )]
            [        ,     |_________|   ,     |_________|   ]
            [        ,     ,----^----,   ,     ,----^----,   ]
            [        , [ Mike ], ( Maye ), [ Ted  ], [ Jeff ]]
        """.trimIndent()
    }

    @Test
    fun draw4Children4() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-4-children-3rd-gen-4.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,---------,---------,    ]
            [[ Bill ], ( Lisa ), [Chris ], ( Anne ), [ Todd ], ( Lucy ), [  Ed  ]]
            [    |_________|   ,     |_________|   ,         ,     |_________|   ]
            [    ,----^----,---------,   ,         ,         ,          |        ]
            [[ Mike ], ( Cara ), [James ],         ,         ,      [ Jeff ]     ]
        """.trimIndent()
    }

    @Test
    fun draw4Children5() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-4-children-3rd-gen-5.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|   ]
            [        ,     ,----^--------------,    ]
            [[ Bill ], ( Lisa ),         , [Chris ], ( Anne )]
            [    |_________|   ,         ,     |_________|   ]
            [    ,----^----,---------,   ,          |        ]
            [[ Mike ], ( Cara ), [James ],      ( Lisa )     ]
        """.trimIndent()
    }

    @Test
    fun draw4Children6() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-4-children-3rd-gen-6.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,     ,--------------^----,-------------------,    ]
            [[ Bill ], ( Lisa ),         , [Chris ], ( Anne ), [  Ed  ]]
            [    |_________|   ,         ,     |_________|   ]
            [    ,----^----,---------,   ,          |        ]
            [[ Mike ], ( Cara ), [James ],      ( Lisa )     ]
        """.trimIndent()
    }

    @Test
    fun draw4Children7() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-4-children-3rd-gen-7.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String
        /*canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,-----^-------------,-------------------,    ]
            [[ Lucy ], [ Bill ], ( Lisa ),         , [Chris ], ( Anne ), ( Lisa )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,---------,   ,          |        ]
            [        , [ Mike ], ( Cara ), [James ],      ( Lisa )     ]
        """.trimIndent()*/

        // Object
        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^--------------,-------------------,    ]
            [[ Lucy ], [ Bill ], ( Lisa ),         , [Chris ], ( Anne ), ( Lisa )]
            [        ,     |_________|   ,         ,     |_________|   ]
            [        ,     ,----^----,---------,   ,          |        ]
            [        , [ Mike ], ( Cara ), [James ],      ( Lisa )     ]
        """.trimIndent()
    }

    @Test
    fun draw5Children() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-5-children-3rd-gen.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [        ,         ,     ,----^----,   ]
            [        , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,     |_________|   ]
            [    ,---------,----^----,---------,---------,   ]
            [[River ], [ Will ], (Sarah ), [Teddy ], ( Anne )]
        """.trimIndent()
    }

    @Test
    fun draw5Children2() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-5-children-3rd-gen-2.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,---------,--------------^--------------,---------,    ]
            [( Lisa ), [ Mike ], ( Lucy ), [  Ed  ], ( Cara ), ( Maye ), [ Bill ]]
            [        ,     |_________|   ,     |_________|   ,     |_________|   ]
            [        ,     ,----^----,---------,   ,         ,     ,----^----,   ]
            [        , [River ], [ Will ], (Sarah ),         , [ Jeff ], (Kitty )]
        """.trimIndent()
    }

    @Test
    fun draw5Children3() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-5-children-3rd-gen-3.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        // String
        /*canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,------------------,-----^--------------,---------,    ]
            [[  M1  ], (  F1  ), [  M2  ], (  F2  ), (  F3  ), (  F4  ), [  M3  ]]
            [    |_________|   ,     |_________|   ,         ,     |_________|   ]
            [         |        ,     ,----^----,---------,   ,          |        ]
            [     [  M4  ]     , [  M5  ], [  M6  ], (  F5  ),      (  F6  )     ]
        """.trimIndent()*/

        // Object
        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|   ]
            [    ,-------------------,----^--------------,---------,    ]
            [[  M1  ], (  F1  ), [  M2  ], (  F2  ), (  F3  ), (  F4  ), [  M3  ]]
            [    |_________|   ,     |_________|   ,         ,     |_________|   ]
            [         |        ,     ,----^----,---------,   ,          |        ]
            [     [  M4  ]     , [  M5  ], [  M6  ], (  F5  ),      (  F6  )     ]
        """.trimIndent()
    }

    @Test
    fun draw6Children() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-6-children-3rd-gen.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,         ,     ,----^----,   ]
            [        ,         , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,         ,     |_________|   ]
            [    ,---------,---------,----^----,---------,---------,   ]
            [[River ], [ Will ], (Sarah ), [Teddy ], ( Anne ), [Chirst]]
        """.trimIndent()
    }

    @Test
    fun draw7Children() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-7-children-3rd-gen.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,         ,         ,     ,----^----,   ]
            [        ,         , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,         ,     |_________|   ]
            [    ,---------,---------,----^----,---------,---------,---------,   ]
            [[River ], [ Will ], (Sarah ), [Teddy ], ( Anne ), [Chirst], ( Maye )]
        """.trimIndent()
    }

    @Test
    fun draw7Children2() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-7-children-3rd-gen-2.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [        ,     ,-------------------,----^--------------,-------------------,    ]
            [[  M1  ], (  F1  ),         , [  M2  ], ( F12  ), [  M3  ], (  F3  ), (  F4  ), [ F14  ]]
            [    |_________|   ,         ,     |_________|   ,     |_________|   ,     |_________|   ]
            [    ,----^----,---------,   ,     ,----^----,---------,   ,         ,          |        ]
            [[  M5  ], (  F6  ), (  F7  ), [  M8  ], [  M9  ], ( M26  ),         ,      (  F8  )     ]
        """.trimIndent()
    }

    @Test
    fun draw8Children() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-8-children-3rd-gen.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|   ]
            [    ,-------------------,--------------^----,-------------------,    ]
            [[  M1  ], (  F1  ), [  M2  ], [  M3  ], (  F3  ),         , [  M4  ], (  F3  )]
            [    |_________|   ,         ,     |_________|   ,         ,     |_________|   ]
            [    ,----^----,   ,         ,     ,----^----,---------,   ,     ,----^----,---------,   ]
            [[  M5  ], (  F4  ),         , [  M7  ], (  F5  ), (  F6  ), (  F7  ), (  F8  ), (  F9  )]
        """.trimIndent()
    }

    @Test
    fun draw8Children2() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-8-children-3rd-gen-2.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,         ,     |_________|   ]
            [        ,     ,-------------------,--------------^--------------,-------------------,    ]
            [[  M1  ], (  F1  ),         , [  M2  ], ( F12  ),         , [  M3  ], (  F3  ), (  F4  ), [ F14  ]]
            [    |_________|   ,         ,     |_________|   ,         ,     |_________|   ,     |_________|   ]
            [    ,----^----,---------,   ,     ,----^----,---------,   ,          |        ,          |        ]
            [[  M5  ], (  F6  ), (  F7  ), [  M8  ], [  M9  ], ( M26  ),      ( F20  )     ,      (  F8  )     ]
        """.trimIndent()
    }

    @Test
    fun draw9Children() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-9-children-3rd-gen.json")).drawGenogram()
        val canvas = displayObjectResult(drawer)

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,         ,     |_________|   ]
            [        ,     ,-------------------,--------------^--------------,-------------------,    ]
            [[  M1  ], (  F1  ),         , [  M2  ], ( F12  ),         , [  M3  ], (  F3  ), (  F4  ), [ F14  ]]
            [    |_________|   ,         ,     |_________|   ,         ,     |_________|   ,     |_________|   ]
            [    ,----^----,---------,   ,     ,----^----,---------,   ,     ,----^----,   ,          |        ]
            [[  M5  ], (  F6  ), (  F7  ), [  M8  ], [  M9  ], ( M26  ), ( F20  ), ( F21  ),      (  F8  )     ]
        """.trimIndent()
    }
}
