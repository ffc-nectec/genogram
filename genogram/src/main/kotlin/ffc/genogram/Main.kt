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

import java.nio.charset.Charset

private lateinit var familyObj: Family

fun main(args: Array<String>) {
//    familyObj = getResourceAs("family-1-person.json")
//    familyObj = getResourceAs("family-2-people.json")

//    familyObj = getResourceAs("family-1-child.json")
//    familyObj = getResourceAs("family-2-children.json")
//    familyObj = getResourceAs("family-3-children.json")
//    familyObj = getResourceAs("family-4-children.json")
//    familyObj = getResourceAs("family-5-children.json")
//    familyObj = getResourceAs("family-6-children.json")
//    familyObj = getResourceAs("family-7-children.json")

//    familyObj = getResourceAs("3rdGen/family-1-child-3rd-gen.json")
//    familyObj = getResourceAs("3rdGen/family-2-children-3rd-gen.json")
//    familyObj = getResourceAs("3rdGen/family-3-children-3rd-gen.json")
//    familyObj = getResourceAs("3rdGen/family-4-children-3rd-gen.json")
//    familyObj = getResourceAs("3rdGen/family-5-children-3rd-gen.json")
//    familyObj = getResourceAs("3rdGen/family-6-children-3rd-gen.json")
//    familyObj = getResourceAs("3rdGen/family-7-children-3rd-gen.json")

//    familyObj = getResourceAs("2ndGen/family-1-spouse.json")
//    familyObj = getResourceAs("2ndGen/family-2-spouses.json")
    familyObj = getResourceAs("2ndGen/family-2-spouses-3.json")

    val familyTreePic = drawGenogram()
    for (i in 0 until familyTreePic.findStorageSize()) {
        print("${familyTreePic.familyStorage[i]}\n")
    }
}

inline fun <reified T> getResourceAs(filename: String): T {
    val classloader = Thread.currentThread().contextClassLoader
    val file = classloader.getResourceAsStream(filename)
        .bufferedReader(Charset.forName("UTF-8"))

    return file.readText().parseTo()
}

fun drawGenogram(): FamilyTreeDrawer {
    val familyPic = FamilyTree(familyObj)
    return familyPic.drawGenogram()
}
