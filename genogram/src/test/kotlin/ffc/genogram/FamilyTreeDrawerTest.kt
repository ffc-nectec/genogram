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

package ffc.genogram

import org.junit.Before
import org.junit.Test

class FamilyTreeDrawerTest {

    lateinit var familyObj: Family
    lateinit var familyPic: FamilyTree

    @Before
    fun setUp() {
        familyObj = getResourceAs("family-empty.json")
        familyPic = FamilyTree(familyObj)
    }

    @Test
    fun drawFirstGen() {
        val drawer = familyPic.drawGenogram()

        val excepted = mutableListOf(
            "[Grandf]",
            "(Gradmo)"
        )
        assert(drawer.familyLayers == excepted)
    }

    fun drawGrandMother() {
    }
}
