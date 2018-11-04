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
                        append(displayEmptyNode(it, elementSize, index))
                    }
                    is MarriageLine -> {
                        append(displayMarriageLine(it, elementSize, index))
                    }
                    is ChildrenLine -> {
                        append(displayChildrenLine(it, elementSize, index))
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
    nodeName = if (it.gender == GenderLabel.MALE)
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

fun displayEmptyNode(it: EmptyNode, elementSize: Int, index: Int): String {
    /*return if (index in 1..(elementSize - 1) || elementSize == 1)
        "${it.drawEmptyNode()}"
    else
        "${it.drawEmptyNode()}$marginLeft"*/
//    return it.drawEmptyNode()

    val result = it.drawEmptyNode()

    return if (index == 0) {
        result
    } else if (index in 1..(elementSize - 1) || elementSize == 1)
        "$marginLeft$result"
//        result.toString()
//        "$result - ${it.getSpouseList()}"
    else
        "$result$marginLeft"
//        return  "$result$marginLeft - ${it.getSpouseList()}"
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

    return if (index == 0) {
        result.toString()
    } else if (index in 1..(elementSize - 1) || elementSize == 1)
        "$marginLeft$result"
//        result.toString()
//        "$result - ${it.getSpouseList()}"
    else
        "$result$marginLeft"
//        return  "$result$marginLeft - ${it.getSpouseList()}"
}

fun displayChildrenLine(it: ChildrenLine, elementSize: Int, index: Int): String {
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

        // Here
        result.setCharAt(it.centerMarkPos, '^')
    }

//    return result.toString()
//    return "$result - ${it.getParentList()}"
    return if (index == 0) {
        result.toString()
    } else if (index in 1..(elementSize - 1) || elementSize == 1)
        "$marginLeft$result"
//        result.toString()
//        "$result - ${it.getSpouseList()}"
    else
        "$result$marginLeft"
}



