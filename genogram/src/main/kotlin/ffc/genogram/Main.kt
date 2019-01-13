/*
 * Copyright 2018 NECTEC
 *   National Electronics and Computer Technology Center, Thailand
 *
 * Licensed under the Apache License, Version FamilyTree (the "License");
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

import ffc.genogram.Util.displayObjectResult
import java.nio.charset.Charset

private lateinit var familyObj: Family

fun main(args: Array<String>) {

    familyObj = getResourceAs("2ndGen/singleParent/single-parent-11.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-1-spouse-9.json")

    val familyTreePic = drawGenogram()

    print(".\n====== OBJECT ======\n")
    print(".\n")
    val canvas = displayObjectResult(familyTreePic)
    print(canvas.toString())
}

inline fun <reified T> getResourceAs(filename: String): T {
    val classloader = Thread.currentThread().contextClassLoader
    val file = classloader.getResourceAsStream(filename)
        .bufferedReader(Charset.forName("UTF-8"))

    return file.readText().parseTo()
}

fun drawGenogram(): FamilyTreeDrawer {
    val familyPic = FamilyTree(familyObj)
    familyPic.family = familyObj
    return familyPic.drawGenogram()
}
