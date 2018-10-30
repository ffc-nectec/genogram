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

package ffc.genogram.Util

import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.GenderLabel
import ffc.genogram.Node.EmptyNode
import ffc.genogram.Node.createGenderBorder
import ffc.genogram.Person
import ffc.genogram.RelationshipLine.ChildrenLine
import ffc.genogram.RelationshipLine.MarriageLine

fun cleanUpEmptyStack(stack: MutableList<Int>) = if (stack.isEmpty()) null else stack

fun displayObjectResult(drawer: FamilyTreeDrawer): StringBuilder {
    val canvas = StringBuilder().apply {
        drawer.personFamilyStorage.forEach { layer ->
            append("[")
            val elementSize = layer.size
            layer.forEachIndexed { index, it ->
                when (it) {
                    is Person -> {
                        append(displayPerson(it, elementSize, index))
                    }
                    is EmptyNode -> {
                        append(displayEmptyNode(it, layer[index + 1]))
                    }
                    is MarriageLine -> {
                        append(displayMarriageLine(it, elementSize, index))
                    }
                    is ChildrenLine -> {
                        append(displayChildrenLine(it))
                    }
                    else -> {
                        append("else")
                    }
                }
            }
            append("]")
            append("\n")
        }
    }

    return canvas
}

private const val marginLeft = ", "

fun displayPerson(it: Person, elementSize: Int, index: Int): String {
    var nodeName = it.firstname
    nodeName = if (it.getGender() == GenderLabel.MALE)
        createGenderBorder(nodeName, GenderLabel.MALE)
    else
        createGenderBorder(nodeName, GenderLabel.FEMALE)

    var margin = StringBuilder().toString()
    for (i in 0 until it.nodeMargin)
        margin += " "

    return if (index in 1..(elementSize - 1))
        "$marginLeft$margin$nodeName$margin"
    else
        "$margin$nodeName$margin"
}

fun displayEmptyNode(it: EmptyNode, element: Any): String {
    return if (element !is Person)
        "${it.drawEmptyNode()}$marginLeft"
    else
        "${it.drawEmptyNode()}"
}

fun displayMarriageLine(it: MarriageLine, elementSize: Int, index: Int): String {
    var result = StringBuilder()

    for (i in 0 until it.imageLength.toInt()) {
        if (i == it.getStartingMarkPos().toInt() ||
            i == it.getEndingMarkPos().toInt()
        )
            result.append("|")
        if (i >= it.getStartingMarkPos().toInt() &&
            i < it.getEndingMarkPos().toInt()
        )
            result.append("_")
        else
            result.append(" ")
    }

    if (index in 1..(elementSize - 1) || elementSize == 1)
        return result.toString()
//        return "$result - ${it.getSpouseList()}"
    else
        return  "$result$marginLeft"
//        return  "$result$marginLeft - ${it.getSpouseList()}"
}

fun displayChildrenLine(it: ChildrenLine): String {
    var result = StringBuilder()

    if (it.childrenNumb == 1) {
        for (i in 0 until it.imageLength.toInt()) {
            if (i == it.getLineMarkPos(1))
                result.append('|')
            else
                result.append(" ")
        }
    } else {
        var childrenMarks = it.getAllLineMarkPos()

        for (i in 0 until it.imageLength.toInt()) {
            if (i > childrenMarks[0] && i < childrenMarks[childrenMarks.size - 1]) {
                result.append("-")
            } else {
                result.append(" ")
            }
        }

        childrenMarks.forEach {
            result.setCharAt(it, ',')
        }
        result.setCharAt(it.centerMarkPos, '^')
    }

    return result.toString()
//    return "$result - ${it.getChildrenList()}"
}



