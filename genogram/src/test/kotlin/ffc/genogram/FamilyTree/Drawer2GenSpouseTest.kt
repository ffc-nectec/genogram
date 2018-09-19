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

class Drawer2GenSpouseTest {

    @Test
    fun draw1Spouses() {
        val drawer = FamilyTree(getResourceAs("2ndGen/family-1-spouse.json")).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.familyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [        ,     ,----^----,    ]
            [ [ Bill ], ( Lisa ), [  Ed  ]]
            [    |_________|    ]
        """.trimIndent()
    }

    @Test
    fun draw2Spouses() {
        val drawer = FamilyTree(getResourceAs("2ndGen/family-2-spouses.json")).drawGenogram()
        val canvas = StringBuilder().apply {
            drawer.familyStorage.forEach { append("$it\n") }
        }

        canvas.toString().trimIndent() `should equal` """
            [        , [Grandf], (Grandm)]
            [        ,     |_________|    ]
            [        ,     ,----^----,    ]
            [ [ Bill ], ( Lisa ), [  Ed  ], ( Mary )]
            [    |_________|    ,     |_________|    ]
        """.trimIndent()
    }

//    @Test
//    fun draw2Spouses2() {
//        val drawer = FamilyTree(getResourceAs("2ndGen/family-2-spouses-2.json")).drawGenogram()
//        val canvas = StringBuilder().apply {
//            drawer.familyStorage.forEach { append("$it\n") }
//        }
//
//        canvas.toString().trimIndent() `should equal` """
//            [        , [Grandf], (Grandm)]
//            [        ,     |_________|    ]
//            [        ,     ,----^----,    ]
//            [ [ Bill ],  Ed ), ( Lisa ), ( Mary )]
//            [    |_________|    ,     |_________|    ]
//        """.trimIndent()
//    }

}