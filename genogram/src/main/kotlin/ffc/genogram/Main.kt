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
//    familyObj = getResourceAs("1stGen/family-1-person.json")
//    familyObj = getResourceAs("1stGen/family-2-people.json")

//    familyObj = getResourceAs("2ndGen/children/family-1-child.json")
//    familyObj = getResourceAs("2ndGen/children/family-1-child-2.json")
//    familyObj = getResourceAs("2ndGen/children/family-2-children.json")
//    familyObj = getResourceAs("2ndGen/children/family-2-children-2.json")
//    familyObj = getResourceAs("2ndGen/children/family-2-children-3.json")
//    familyObj = getResourceAs("2ndGen/children/family-2-children-4.json")
//    familyObj = getResourceAs("2ndGen/children/family-3-children.json")
//    familyObj = getResourceAs("2ndGen/children/family-3-children-2.json")
//    familyObj = getResourceAs("2ndGen/children/family-3-children-3.json")
//    familyObj = getResourceAs("2ndGen/children/family-3-children-4.json")
//    familyObj = getResourceAs("2ndGen/children/family-3-children-5.json")
//    familyObj = getResourceAs("2ndGen/children/family-3-children-6.json")
//    familyObj = getResourceAs("2ndGen/children/family-3-children-7.json")
//    familyObj = getResourceAs("2ndGen/children/family-3-children-8.json")
    familyObj = getResourceAs("2ndGen/children/family-3-children-9.json")
//    familyObj = getResourceAs("2ndGen/children/family-4-children.json")
//    familyObj = getResourceAs("2ndGen/children/family-5-children.json")
//    familyObj = getResourceAs("2ndGen/children/family-6-children.json")
//    familyObj = getResourceAs("2ndGen/children/family-7-children.json")

//    familyObj = getResourceAs("2ndGen/spouses/family-1-spouse.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-1-spouse-1.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-1-spouse-2.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-1-spouse-3.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-1-spouse-4.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-1-spouse-5.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-1-spouse-6.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-1-spouse-7.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-1-spouse-8.json")

//    familyObj = getResourceAs("2ndGen/spouses/family-2-spouses.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-2-spouses-2.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-2-spouses-3.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-2-spouses-4.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-2-spouses-5.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-2-spouses-4.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-2-spouses-5.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-2-spouses-6.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-2-spouses-7.json")
//    familyObj = getResourceAs("2ndGen/spouses/family-2-spouses-8.json")

//    familyObj = getResourceAs("3rdGen/family-1-child-3rd-gen.json")
//    familyObj = getResourceAs("3rdGen/family-2-children-3rd-gen.json")
//    familyObj = getResourceAs("3rdGen/family-3-children-3rd-gen.json")
//    familyObj = getResourceAs("3rdGen/family-4-children-3rd-gen.json")
//    familyObj = getResourceAs("3rdGen/family-5-children-3rd-gen.json")
//    familyObj = getResourceAs("3rdGen/family-6-children-3rd-gen.json")
//    familyObj = getResourceAs("3rdGen/family-7-children-3rd-gen.json")

    val familyTreePic = drawGenogram()
    for (i in 0 until familyTreePic.findStorageSize()) {
        print("${familyTreePic.nameFamilyStorage[i]}\n")
    }

//    familyTreePic.nameFamilyStorage.forEachIndexed { index, arrayList ->
//        arrayList.forEach {
//            print("$it  ")
//        }
//
//        if (index < familyTreePic.findStorageSize() - 1)
//            print("\n")
//    }

    /*  print("\n\n///////////////////////\n\n")

      familyTreePic.personFamilyStorage.forEach { layer ->
          layer.forEach {
              if (it is Person)
                  print("${it.firstname}  ")
              else
                  print(it)
          }

          print("\n")
      }*/
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
