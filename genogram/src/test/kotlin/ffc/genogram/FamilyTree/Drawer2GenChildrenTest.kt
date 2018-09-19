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

class Drawer2GenChildrenTest {

    @Test
    fun drawOneChild() {
        val drawer = FamilyTree(getResourceAs("2ndGen/family-1-child.json")).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.familyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|    ]
            [         |        ]
            [      ( Lisa )      ]
        """.trimIndent()
    }

    @Test
    fun drawTwoChildren() {
        val drawer = FamilyTree(getResourceAs("2ndGen/family-2-children.json")).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.familyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|    ]
            [    ,----^----,    ]
            [( Lisa ), [  Ed  ]]
        """.trimIndent()
    }

    @Test
    fun drawThreeChildren() {
        val drawer = FamilyTree(getResourceAs("2ndGen/family-3-children.json")).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.familyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [[Grandf], (Grandm)]
            [    |_________|    ]
            [    ,----^----,---------,    ]
            [( Lisa ), [  Ed  ], [Teddy ]]
        """.trimIndent()
    }

    @Test
    fun drawFourChildren() {
        val drawer = FamilyTree(getResourceAs("2ndGen/family-4-children.json")).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.familyStorage.forEach { append("$it\n") }
        }

        // TODO: Improve 4 children family
        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,---------,----^----,---------,    ]
            [( Lisa ), [  Ed  ], [Teddy ], ( Anne )]
        """.trimIndent()
    }

    @Test
    fun drawFiveChildren() {
        val drawer = FamilyTree(getResourceAs("2ndGen/family-5-children.json")).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.familyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [    ,---------,----^----,---------,---------,    ]
            [( Lisa ), [  Ed  ], [Teddy ], ( Anne ), [Chirst]]
        """.trimIndent()
    }

    @Test
    fun drawSixChildren() {
        val drawer = FamilyTree(getResourceAs("2ndGen/family-6-children.json")).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.familyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [    ,---------,---------,----^----,---------,---------,    ]
            [( Lisa ), [  Ed  ], [Teddy ], ( Anne ), [Chirst], ( May  )]
        """.trimIndent()
    }

    @Test
    fun drawSevenChildren() {
        val drawer = FamilyTree(getResourceAs("2ndGen/family-7-children.json")).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.familyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        ,         , [Grandf], (Grandm)]
            [        ,         ,     |_________|    ]
            [    ,---------,---------,----^----,---------,---------,---------,    ]
            [( Lisa ), [  Ed  ], [Teddy ], ( Anne ), [Chirst], ( Maye ), ( Kaye )]
        """.trimIndent()
    }
}
