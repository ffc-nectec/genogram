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

package ffc.genogram.node

import ffc.genogram.Person
import ffc.genogram.node.Node.Companion.nodeBorderSize

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

// find the person index
fun findPersonPosition(personLayer: ArrayList<String>, focusedPerson: Person): Int {

    personLayer.forEachIndexed { index, element ->
        if (element == setNodeSize(focusedPerson.firstname))
            return index
    }

    return 0
}

fun createEmptyNode(): String {

    var result = ""

    for (i in 0 until Node.nodeSize.toInt() + nodeBorderSize.toInt()) {
        result += " "
    }

    return result
}
