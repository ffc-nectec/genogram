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

package ffc.genogram.util

import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.Person
import ffc.genogram.node.Node

fun setNodeSize(nodeName: String): String {
    return if (nodeName.length > Node.nodeSize) {
        nodeName.subSequence(0, Node.nodeSize.toInt()) as String
    } else {
        var tmp = ""
        val diff = Math.abs(nodeName.length - Node.nodeSize.toInt())
        for (i in 0 until diff / 2) {
            tmp += " "
        }
        tmp + nodeName + tmp
    }
}

fun setNodeSizeWithGender(person: Person): String {
    val nodeName = person.firstname
    var tmp = ""
    if (nodeName.length > Node.nodeSize) {
        nodeName.subSequence(0, Node.nodeSize.toInt()) as String
    } else {
        val diff = Math.abs(nodeName.length - Node.nodeSize.toInt())
        for (i in 0 until diff / 2) {
            tmp += " "
        }
        tmp = tmp + nodeName + tmp
    }

    return if (person.gender == 0) "[$tmp]" else "($tmp)"
}

fun findPersonLayer(familyTreePic: FamilyTreeDrawer, focusedPerson: Person): Int {
    val familyTree = familyTreePic.familyStorage
    var targetLayer = 0
    var tmp = setNodeSize(focusedPerson.firstname)
    tmp = if (focusedPerson.gender == 0) "[$tmp]" else "($tmp)"

    for (i in (familyTree.size - 1) downTo 0) {
        familyTree[i].find { it == tmp }?.let {
            targetLayer = i
        }
    }

    return targetLayer
}

fun findPersonPosition(personLayer: ArrayList<String>, focusedPerson: Person): Int {
    // find the person index
    var personInd = 0
    for (i in 0 until personLayer.size) {
        if (personLayer[i] == setNodeSize(focusedPerson.firstname))
            personInd = i
    }

    return personInd
}
