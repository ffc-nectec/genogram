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

import org.junit.Test

class FamilyTest {

    //    private val familyObj = getResourceAs<Family>("family.json")
    private val familyObj = getResourceAs<Family>("family-empty.json")
    private val familyTreePic: FamilyTreeDrawer = FamilyTreeDrawer()
    private val familyTree = FamilyTree(familyObj)

    @Test
    fun read() {
        // Get the focused Person
        val focusedPerson = familyTree.popBloodFamily()
        assert(focusedPerson.firstname == "Grandfather")
    }

    @Test
    fun drawGenogram() {
        val famPic = familyTree.drawGenogram()
        assert(famPic.familyStorage.isEmpty())
    }
}
