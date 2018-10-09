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

import ffc.genogram.Node.createEmptyNode
import ffc.genogram.RelationshipLine.Relationship.Companion.distanceLine
import ffc.genogram.RelationshipLine.Relationship.Companion.lengthLine
import ffc.genogram.RelationshipLine.RelationshipLabel

class FamilyTreeDrawer {
    private val indentNumb = 4

    // For display
    var nameFamilyLayers: ArrayList<String> = ArrayList()
    var nameFamilyStorage: ArrayList<ArrayList<String>> = ArrayList()

    // For recording
    var personFamilyLayers: ArrayList<Any> = ArrayList()
    var personFamilyStorage: ArrayList<ArrayList<Any>> = ArrayList()

    fun addFamilyNewLayer(name: String) {
        nameFamilyStorage.add(arrayListOf(name))
        personFamilyStorage.add(arrayListOf(name))
    }

    fun addEmptyNewLayer() {
        nameFamilyStorage.add(arrayListOf())
        personFamilyStorage.add(arrayListOf())
    }

    fun addFamilyLayer(name: String, addedPerson: Person) {
        nameFamilyLayers.add(name)
        personFamilyLayers.add(addedPerson)

        if (nameFamilyStorage.isEmpty()) {
            nameFamilyStorage.add(nameFamilyLayers)
            personFamilyStorage.add(personFamilyLayers)
        }
    }

    fun addFamilyAtLayer(layer: Int, element: String, person: Person?) {
        if (layer < findStorageSize()) {
            nameFamilyStorage[layer].add(element)

            if (person != null)
                personFamilyStorage[layer].add(person)
            else
                personFamilyStorage[layer].add(element)
        } else {
            addEmptyNewLayer()
            nameFamilyStorage[layer].add(element)
        }
    }

    fun addFamilyStorageAtIndex(layerNumb: Int, replaceInd: Int, node: String, person: Person) {
        nameFamilyStorage[layerNumb].add(replaceInd, node)
        personFamilyStorage[layerNumb].add(replaceInd, person)
    }

    fun addFamilyStorageReplaceIndex(layerNumb: Int, replaceInd: Int, node: String?, person: Person?) {
        if (node != null && person != null) {
            nameFamilyStorage[layerNumb].add(replaceInd + 1, node)
            personFamilyStorage[layerNumb].add(replaceInd + 1, person)
        } else {
            nameFamilyStorage[layerNumb].add(replaceInd, createEmptyNode())
            personFamilyStorage[layerNumb].add(replaceInd, createEmptyNode())
        }
    }

    fun replaceFamilyStorageLayer(layerNumb: Int, replaceInd: Int, node: String?) {
        val layer = nameFamilyStorage[layerNumb]

        if (node != null)
            layer[replaceInd] = node
        else
            layer[replaceInd] = createEmptyNode()
    }

    fun findStorageSize(): Int = nameFamilyStorage.size

    fun findPersonLayerSize(layerNumb: Int): Int = personFamilyStorage[layerNumb].size

    fun findStorageLayerSize(layerNumb: Int): Int {
        return if (nameFamilyStorage[layerNumb].isNotEmpty())
            nameFamilyStorage[layerNumb].size
        else
            -1
    }

    fun hasPeopleOnTheRight(focusedPerson: Person, layerNumb: Int): Boolean {
        val personInd = findPersonInd(focusedPerson, layerNumb)

        return personInd < findStorageLayerSize(layerNumb) - 1
    }

    fun hasPeopleOnTheLeft(focusedPerson: Person, layerNumb: Int): Boolean {
        val personInd = findPersonInd(focusedPerson, layerNumb)

        return personInd > 0
    }

    // find the person index
    fun findPersonInd(focusedPerson: Person, layerNumb: Int): Int {
        val personId = focusedPerson.idCard
        val nodeList = personFamilyStorage[layerNumb]

        nodeList.forEachIndexed { index, element ->
            if (element is Person)
                if (element.idCard == personId)
                    return index
        }

        return 0
    }

    fun findPersonIndById(personId: Long, layerNumb: Int): Int {
        val nodeList = personFamilyStorage[layerNumb]

        nodeList.forEachIndexed { index, person ->
            if (person is Person)
                if (person.idCard == personId)
                    return index
        }

        return 0
    }

    fun findPersonLayer(focusedPerson: Person): Int {

        personFamilyStorage.forEachIndexed { index, arrayList ->
            arrayList.forEach { element ->
                if (element is Person) {
                    if (focusedPerson.idCard == element.idCard)
                        return index
                }
            }
        }

        return 0
    }

    fun getLineLayer(layerNumb: Int): String {
        return nameFamilyStorage[layerNumb][0]
    }

    fun getLayer(layerNumb: Int): ArrayList<String> {
        return nameFamilyStorage[layerNumb]
    }

    fun findNumberOfEmptyNode(layerNumb: Int): Int {
        var count = 0

        if (layerNumb < findStorageSize()) {
            val lineLayer = nameFamilyStorage[layerNumb]
            lineLayer.forEach {
                if (it == createEmptyNode())
                    count++
            }
        }

        return count
    }

    fun addEmptyNodeMarriageLine(partnerInd: Int, layerNumb: Int): String {
        val marriageLineIndList = nameFamilyStorage[layerNumb]
        val tmp = StringBuilder()

        if (partnerInd % 2 != 0) {
            if (marriageLineIndList.size < partnerInd) {
                // Add the previous index of partner with an empty node
                val addingInd = partnerInd - (1 * 2)
                addFamilyStorageReplaceIndex(layerNumb, addingInd, null, null)
            }
        }

        return tmp.toString()
    }

    private fun createLine(relation: RelationshipLabel, length: Int): String {
        var addingLine = ""
        var underScoreSign = "_"
        var lengthLine = length

        if (relation == RelationshipLabel.CHILDREN) {
            underScoreSign = "-"
            lengthLine /= 2
        }

        for (i in 0 until lengthLine + 1)
            addingLine += underScoreSign

        return addingLine
    }

    fun extendLine(
        expectedLength: Int, childrenListInd: MutableList<Int>, parentInd: Int
    ): String {
        val tmp = StringBuilder()
        val lineSign = '-'
        val indent = ' '
        var indentSpace = ""

        // Create Line
        for (i in 0 until (indentNumb)) {
            indentSpace += indent
        }

        tmp.append(indentSpace)
        for (i in 0 until expectedLength - (indentNumb * 2)) {
            tmp.append(lineSign)
        }
        tmp.append(indentSpace)

        return createChildrenLineSign(tmp.toString(), childrenListInd, parentInd)
    }

    private fun createChildrenLineSign(line: String, childrenListInd: MutableList<Int>, parentInd: Int): String {
        val childrenSign = ','
        val childrenCenterSign = '^'
        var length = (lengthLine - 1).toInt()
        val tmp = StringBuilder()
        var startInd = childrenListInd[0]
        var childrenSignInd = mutableListOf<Int>()
        var childrenCenterSignInd: Int

        // Find Children sign spot
        childrenListInd.forEach { index ->
            var shiftInd = index
            var ind: Int
            if (startInd != 0) {
                shiftInd = index - startInd
            }
            ind = (indentNumb + (length * shiftInd)) - (shiftInd)
            childrenSignInd.add(ind)
        }
        tmp.append(line)

        // Add Children Sign ','
        for (i in 0 until tmp.length) {
            childrenSignInd.forEach {
                if (i == it) {
                    tmp.setCharAt(it, childrenSign)
                }
            }
        }

        // Add Children Sign '^'
        childrenCenterSignInd = if (startInd == 0) {
            (distanceLine.toInt() + 1) * (parentInd + 1) - 1
        } else {
            ((distanceLine.toInt() + 1) * (parentInd + 1) - 1) / 2
        }
        tmp.setCharAt(childrenCenterSignInd, childrenCenterSign)

        return tmp.toString()
    }

    fun extendRelationshipLineAtPosition(lineLayer: Int, addingInd: Int, childrenInd: MutableList<Int>)
            : String {

        val addAt = addingInd * lengthLine.toInt()
        val orgLine = nameFamilyStorage[lineLayer][0]
        val tmp = StringBuilder()

        val childrenLine = createLine(
            RelationshipLabel.CHILDREN,
            lengthLine.toInt() + 6
        )

        if (addAt <= orgLine.length)
            for (i in 0 until orgLine.length) {
                if (i == addAt)
                    tmp.append(childrenLine)
                tmp.append(orgLine[i])
            }

        return tmp.toString()
    }

    fun moveChildrenLineSign(lineLayer: Int, step: Int): String {
        val tmp = StringBuilder()
        val extraStep = step + 1
        var moveSteps = (distanceLine.toInt() * extraStep) + extraStep
        var count = 1

        val parentLayer = lineLayer - 1
        var parentEmptyNodeNumber = findNumberOfEmptyNode(parentLayer)
        var childrenEmptyNodeNumber = findNumberOfEmptyNode(lineLayer)
        var line = nameFamilyStorage[lineLayer][0]

        if ((parentEmptyNodeNumber - childrenEmptyNodeNumber > 0) && (childrenEmptyNodeNumber > 0)) {
            line = nameFamilyStorage[lineLayer][childrenEmptyNodeNumber]
            moveSteps = (moveSteps - lengthLine).toInt() + parentEmptyNodeNumber
        }

        for (i in 0 until line.length) {
            if (i == moveSteps && count == 0)
                tmp.append("^")

            if (line[i] == '^') {
                tmp.append("")
                count--
            } else {
                tmp.append(line[i])
            }
        }

        print("")
        return tmp.toString()
    }

    fun childrenLineLength(childrenNumb: Int)
            : Int = (4 + (11 * (childrenNumb - 1)) - (childrenNumb - 1)) + 5
}
