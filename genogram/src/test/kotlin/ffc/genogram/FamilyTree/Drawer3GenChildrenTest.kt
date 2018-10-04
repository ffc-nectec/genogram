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

class Drawer3GenChildrenTest {

    @Test
    fun draw1Child() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-1-child-3rd-gen.json")).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [        ,     ,----^----,    ]
            [[ Bill ], ( Lisa ), [  Ed  ]]
            [    |_________|    ]
            [         |        ]
            [     [River ]     ]
        """.trimIndent()
    }

    @Test
    fun draw2Children() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-2-children-3rd-gen.json")).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [        ,     ,----^----,    ]
            [[ Bill ], ( Lisa ), [  Ed  ]]
            [    |_________|    ]
            [    ,----^----,    ]
            [[River ], [ Will ]]
        """.trimIndent()
    }

    @Test
    fun draw3Children() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-3-children-3rd-gen.json")).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [        ,     ,----^----,    ]
            [[ Bill ], ( Lisa ), [  Ed  ]]
            [    |_________|    ]
            [    ,----^----,---------,    ]
            [[River ], [ Will ], (Sarah )]
        """.trimIndent()
    }

    @Test
    fun draw5Children() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-5-children-3rd-gen.json")).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [        ,         ,     ,----^----,    ]
            [        , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,     |_________|    ]
            [    ,---------,----^----,---------,---------,    ]
            [[River ], [ Will ], (Sarah ), [Teddy ], ( Anne )]
        """.trimIndent()
    }

    @Test
    fun draw6Children() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-6-children-3rd-gen.json")).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|    ]
            [        ,         ,         ,     ,----^----,    ]
            [        ,         , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,         ,     |_________|    ]
            [    ,---------,---------,----^----,---------,---------,    ]
            [[River ], [ Will ], (Sarah ), [Teddy ], ( Anne ), [Chirst]]
        """.trimIndent()
    }

    @Test
    fun draw7Children() {
        val drawer = FamilyTree(getResourceAs("3rdGen/family-7-children-3rd-gen.json")).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.nameFamilyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         ,         , [Grandf], (Grandm)]
            [        ,         ,         ,     |_________|    ]
            [        ,         ,         ,     ,----^----,    ]
            [        ,         , [ Bill ], ( Lisa ), [  Ed  ]]
            [        ,         ,     |_________|    ]
            [    ,---------,---------,----^----,---------,---------,---------,    ]
            [[River ], [ Will ], (Sarah ), [Teddy ], ( Anne ), [Chirst], ( Maye )]
        """.trimIndent()
    }
}
