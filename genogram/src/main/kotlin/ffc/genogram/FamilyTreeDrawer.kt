/*
 * Copyright 2018 NECTEC
 *   National Electronics and Computer Technology Center, Thailand
 *
 * Licensed under the Apache License, Version FamilyTree2.0 (the "License");
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

import ffc.genogram.Node.EmptyNode
import ffc.genogram.Node.Node
import ffc.genogram.RelationshipLine.ChildrenLine
import ffc.genogram.RelationshipLine.MarriageLine
import ffc.genogram.RelationshipLine.Relationship.Companion.distanceLine
import ffc.genogram.RelationshipLine.Relationship.Companion.lengthLine
import ffc.genogram.RelationshipLine.RelationshipLabel
import ffc.genogram.Util.findMovingNodeNumb
import java.util.Collections
import kotlin.math.floor

class FamilyTreeDrawer {
    private val indentNumb = 4

    // For display
    var nameFamilyLayers: ArrayList<String> = ArrayList()
    var nameFamilyStorage: ArrayList<ArrayList<String>> = ArrayList()

    // For recording
    var personFamilyLayers: ArrayList<Any> = ArrayList()
    var personFamilyStorage: ArrayList<ArrayList<Any>> = ArrayList()

    private var emptyNode: EmptyNode = EmptyNode()

    fun addFamilyNewLayer(s: String?, line: Any?) {
        if (s != null)
            nameFamilyStorage.add(arrayListOf(s))

        if (line != null)
            addLineFamilyNewLayer(line)
    }

    private fun addLineFamilyNewLayer(line: Any?) {
        if (line != null)
            personFamilyStorage.add(arrayListOf(line))
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

    fun addFamilyAtLayer(layer: Int, s: String, e: Any) {
        if (layer >= findStorageSize())
            addEmptyNewLayer()
        nameFamilyStorage[layer].add(s)

        if (layer >= findPersonStorageSize())
            addEmptyNewLayer()
        personFamilyStorage[layer].add(e)
    }

    fun addFamilyStorageAtIndex(layerNumb: Int, replaceInd: Int, node: String?, person: Person?) {
        if (node == null) {
            nameFamilyStorage[layerNumb].add(replaceInd, emptyNode.drawEmptyNode())
        } else {
            nameFamilyStorage[layerNumb].add(replaceInd, node)
        }

        if (person == null) {
            personFamilyStorage[layerNumb].add(replaceInd, emptyNode)
        } else {
            personFamilyStorage[layerNumb].add(replaceInd, person)
        }
    }

    fun addFamilyStorageReplaceIndex(layerNumb: Int, replaceInd: Int, node: String?, person: Person?) {
        if (node != null && person != null) {
            nameFamilyStorage[layerNumb].add(replaceInd + 1, node)
            personFamilyStorage[layerNumb].add(replaceInd + 1, person)
        } else {
            nameFamilyStorage[layerNumb].add(replaceInd, emptyNode.drawEmptyNode())
            personFamilyStorage[layerNumb].add(replaceInd, emptyNode)
        }
    }

    fun replaceFamilyStorageLayer(layerNumb: Int, replaceInd: Int, node: String?, line: Any?) {
        val nameLayer = nameFamilyStorage[layerNumb]
        val personLayer = personFamilyStorage[layerNumb]

        if (node == null || line == null) {
            nameLayer[replaceInd] = emptyNode.drawEmptyNode()
            personLayer[replaceInd] = emptyNode
        }

        if (node !== null)
            nameLayer[replaceInd] = node

        if (line != null)
            personLayer[replaceInd] = line
    }

    fun findStorageSize(): Int = nameFamilyStorage.size

    fun findPersonStorageSize(): Int = personFamilyStorage.size

    fun findPersonLayerSize(layerNumb: Int): Int = personFamilyStorage[layerNumb].size

    fun findPersonNumb(layerNumb: Int): Int {
        var count = 0

        personFamilyStorage[layerNumb].forEach {
            if (it is Person) {
                count++
            }
        }

        return count
    }

    fun findStorageLayerSize(layerNumb: Int): Int {
        return if (nameFamilyStorage[layerNumb].isNotEmpty())
            nameFamilyStorage[layerNumb].size
        else
            0
    }

    fun hasNodeOnTheRight(focusedPerson: Person, layerNumb: Int): Boolean {
        val personInd = findPersonInd(focusedPerson, layerNumb)

        return personInd < findStorageLayerSize(layerNumb) - 1
    }

    fun hasNodeOnTheLeft(focusedPerson: Person, layerNumb: Int): Boolean {
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

    fun findPersonIndById(personId: Int, layerNumb: Int): Int {
        val nodeList = personFamilyStorage[layerNumb]

        nodeList.forEachIndexed { index, person ->
            if (person is Person)
                if (person.idCard == personId)
                    return index
        }

        return 0
    }

    fun getPersonById(personId: Int, layerNumb: Int): Person? {
        val nodeList = personFamilyStorage[layerNumb]

        nodeList.forEachIndexed { index, person ->
            if (person is Person)
                if (person.idCard == personId)
                    return person
        }

        return null
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

    fun findPersonLayerById(personId: Int): Int {
        personFamilyStorage.forEachIndexed { index, arrayList ->
            arrayList.forEach { element ->
                if (element is Person) {
                    if (personId == element.idCard)
                        return index
                }
            }
        }

        return 0
    }

    fun findPerson(focusedPerson: Person, layerNumb: Int): Person? {
        personFamilyStorage[layerNumb].forEach {
            if (it is Person) {
                if (focusedPerson.idCard == it.idCard) {
                    return focusedPerson
                }
            }
        }
        return null
    }

    fun findLineNumber(layerNumb: Int): Int {
        val marriageLine = nameFamilyStorage[layerNumb]
        var count = 0

        marriageLine.forEach {
            if (it != emptyNode.drawEmptyNode())
                count++
        }

        return count
    }

    fun findChildrenLineIndSize(childrenLineLayer: Int, ind1: Int, ind2: Int): Int {
        var singleChildNumb = 0
        var childrenNumb = 0
        var emptyNodeNumb = 0

        if (findPersonStorageSize() - 1 >= childrenLineLayer) {
            val childrenLineStorage = personFamilyStorage[childrenLineLayer]
            childrenLineStorage.forEachIndexed { index, any ->
                if (index in ind1..ind2)
                    if (any is ChildrenLine) {
                        when (any.childrenNumb) {
                            1 -> {
                                singleChildNumb++
                            }
                            else -> {
//                                childrenNumb += any.childrenNumb
                                childrenNumb += (any.imageLength / (Node.nodeSize + Node.nodeBorderSize)).toInt()
                            }
                        }
                    } else if (any is EmptyNode) {
                        emptyNodeNumb++
                    }
            }
        }

        return (singleChildNumb * 2) + childrenNumb + emptyNodeNumb
    }

    fun findMarriageLineIndSize(marriageLineLayer: Int, ind1: Int, ind2: Int): Int {
        val marriageLineStorage = personFamilyStorage[marriageLineLayer]
        var count = 0

        marriageLineStorage.forEachIndexed { index, any ->
            if (index in ind1..ind2) {
                if (any is MarriageLine) {
                    count += (any.imageLength / (Node.nodeSize + Node.nodeBorderSize)).toInt()
                } else if (any is EmptyNode) {
                    count++
                }
            }
        }

        return count
    }

    fun findPersonIndSize(personLayer: Int, ind1: Int, ind2: Int): Int {
        val parsonStorage = personFamilyStorage[personLayer]
        var count = 0

        parsonStorage.forEachIndexed { index, any ->
            if (index in ind1..ind2) {
                if (any is EmptyNode) {
                    count++
                } else if (any is Person) {
                    if (any.nodeMargin > 0)
                        count += 2
                    else
                        count++
                }
            }
        }

        return count
    }

    fun getPersonLayer(layerNumb: Int): ArrayList<Any>? {
        return if (layerNumb < findPersonStorageSize()) personFamilyStorage[layerNumb] else null
    }

    fun setPersonLayer(arry: MutableList<Any>, layerNumb: Int) {
        personFamilyStorage[layerNumb] = arry as java.util.ArrayList<Any>
    }

    fun getPersonLayerInd(layerNumb: Int, index: Int): Any? {
        return personFamilyStorage[layerNumb][index]
    }

    fun addMarriageLineInd(layerNumb: Int, focusedPerson: Person, addedPerson: Person?): Int {
        // Check the person gender, and find the person's
        val allPeople: MutableList<Int> = mutableListOf()
        personFamilyStorage[layerNumb - 1].forEach {
            if (it is Person)
                allPeople.add(it.idCard)
        }

        // find the focusedPerson and addedPerson's index
        var focusedPersonInd = 0
        var addedPersonInd = 0
        allPeople.forEachIndexed { index, l ->
            if (l == focusedPerson.idCard)
                focusedPersonInd = index
            else if (addedPerson != null) {
                if (l == addedPerson.idCard)
                    addedPersonInd = index
            }
        }

        // find number of marriage line
        val marriageLineNumb = findLineNumber(layerNumb)

        return if (focusedPersonInd > addedPersonInd)
            (focusedPersonInd - marriageLineNumb) + 1
        else
            (addedPersonInd - marriageLineNumb) + 1
    }

    fun findNumberOfEmptyNode(layerNumb: Int): Int {
        var count = 0

        if (nameFamilyStorage.isNotEmpty())
            if (layerNumb < findStorageSize()) {
                val lineLayer = nameFamilyStorage[layerNumb]
                lineLayer.forEach {
                    if (it == emptyNode.drawEmptyNode())
                        count++
                }
            }

        return count
    }

    fun findNumberOfEmptyNodePerson(layerNumb: Int): Int {
        var count = 0

        if (personFamilyStorage.isNotEmpty())
            if (layerNumb < findStorageSize()) {
                val lineLayer = personFamilyStorage[layerNumb]
                lineLayer.forEach {
                    if (it is EmptyNode)
                        count++
                }
            }

        return count
    }

    fun findNumberOfMidEmptyNode(layerNumb: Int): Int {
        var count = 0

        if (nameFamilyStorage.isNotEmpty())
            if (layerNumb < findStorageSize()) {
                val lastLineLayerInd = findStorageLayerSize(layerNumb) - 1
                val lineLayer = nameFamilyStorage[layerNumb]
                lineLayer.forEachIndexed { index, s ->
                    if ((index != 0) && (index != lastLineLayerInd)
                        && (s == emptyNode.drawEmptyNode())
                    )
                        count++
                }
            }

        return count
    }

    fun findNumberOfMidEmptyNodePerson(layerNumb: Int): Int {
        var count = 0

        if (personFamilyStorage.isNotEmpty())
            if (layerNumb < findStorageSize()) {
                val lastLineLayerInd = findStorageLayerSize(layerNumb) - 1
                val lineLayer = personFamilyStorage[layerNumb]
                lineLayer.forEachIndexed { index, s ->
                    if ((index != 0) && (index != lastLineLayerInd) && (s is EmptyNode))
                        count++
                }
            }

        return count
    }

    fun findNumberOfMidEmptyNode(personLayer: Int, pInd1: Int, pInd2: Int): Int {
        // pInd1 < pInd2
        var count = 0

        if (personFamilyStorage.isNotEmpty()) {
            if (personLayer < findStorageSize()) {
                val lineLayer = personFamilyStorage[personLayer]
                lineLayer.forEachIndexed { index, any ->
                    if (index in (pInd1 + 1)..(pInd2 - 1) && any is EmptyNode) {
                        count++
                    }
                }
            }
        }

        return count
    }

    fun findNumberOfFrontEmptyNode(personLayer: Int): Int {
        var count = 0
        var stop = false

        if (personFamilyStorage.isNotEmpty()) {
            if (personLayer < findStorageSize()) {
                val lineLayer = personFamilyStorage[personLayer]
                lineLayer.forEachIndexed { index, any ->
                    if (any is EmptyNode && !stop) {
                        count++
                    } else {
                        stop = true
                        return@forEachIndexed
                    }

                }
            }
        }

        return count
    }

    fun findNumberOfFrontEmptyNode(personLayer: Int, pInd: Int): Int {
        var count = 0

        if (personFamilyStorage.isNotEmpty()) {
            if (personLayer < findStorageSize()) {
                val lineLayer = personFamilyStorage[personLayer]
                lineLayer.forEachIndexed { index, any ->
                    if (index < (pInd + 1) && any is EmptyNode) {
                        count++
                    }
                }
            }
        }

        return count
    }

    fun findSingleChildNumb(childrenLineLayer: Int): Int {
        val lineList = personFamilyStorage[childrenLineLayer]
        var singleChildNumb = 0

        lineList.forEach {
            if (it is ChildrenLine) {
                if (it.childrenNumb == 1)
                    singleChildNumb++
            }
        }

        return singleChildNumb
    }

    fun findSingleChildNumb(childrenLineLayer: Int, ind1: Int, ind2: Int): Int {
        val lineList = personFamilyStorage[childrenLineLayer]
        var singleChildNumb = 0

        lineList.forEachIndexed { index, any ->
            if (index in ind1 until ind2)
                if (any is ChildrenLine) {
                    if (any.childrenNumb == 1)
                        singleChildNumb++
                }
        }

        return singleChildNumb
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

        // String Visualization
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

    private fun createChildrenLineSign(
        line: String, childrenListInd: MutableList<Int>,
        parentInd: Int
    ): String {
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
        // Check Children Sign before adding
        for (i in 0 until tmp.length) {
            childrenSignInd.forEach {
                if (childrenCenterSignInd == it) {
                    childrenCenterSignInd++
                }
            }
        }
        tmp.setCharAt(childrenCenterSignInd, childrenCenterSign)

        return tmp.toString()
    }

    fun childrenLineLength(childrenNumb: Int)
            : Int = (4 + (11 * (childrenNumb - 1)) - (childrenNumb - 1)) + 5

    fun findChildrenLine(childrenLineLayer: Int, child: Person): ChildrenLine? {
        val childrenLineStorage = personFamilyStorage[childrenLineLayer]
        var childrenLine: ChildrenLine? = null
        childrenLineStorage.forEachIndexed { index, any ->
            if (any is ChildrenLine) {
                val childrenList = any.childrenList
                childrenList.forEach { it ->
                    if (it == child) {
                        childrenLine = childrenLineStorage[index] as ChildrenLine
                    }
                }
            }
        }

        return childrenLine
    }

    fun findMarriageLine(parentMarriageLineLayer: Int, parent1: Person, parent2: Person?):
            MarriageLine? {
        val marriageLineStorage = personFamilyStorage[parentMarriageLineLayer]
        var marriageLine: MarriageLine? = null

        marriageLineStorage.forEachIndexed { index, any ->
            if (any is MarriageLine) {
                val spousesStorage = any.getSpouseList()
                spousesStorage.forEach {
                    val person1 = it[0]
                    val person2 = it[1]

                    // The Unknow's id is always "0"
                    if (person2.idCard != 0) {
                        if ((person1 == parent1 && person2 == parent2) ||
                            (person1 == parent2 && person2 == parent1)
                        )
                            marriageLine = marriageLineStorage[index] as MarriageLine
                    } else {
                        // Single parent
                        if (parent1 == it[0])
                            marriageLine = marriageLineStorage[index] as MarriageLine
                    }
                }
            }
        }

        return marriageLine
    }

    fun findChildrenLineInd(childrenLine: ChildrenLine, childrenLineLayer: Int): Int? {
        val childrenLineStorage = personFamilyStorage[childrenLineLayer]
        var lineInd: Int? = null

        childrenLineStorage.forEachIndexed { index, any ->
            if (any == childrenLine)
                lineInd = index
        }

        return lineInd
    }

    fun findLineInd(line: Any, lineLayer: Int): Int? {
        val lineStorage = personFamilyStorage[lineLayer]
        var lineInd: Int? = null

        lineStorage.forEachIndexed { index, any ->
            if (any == line)
                lineInd = index
        }

        return lineInd
    }

    fun generationNumber(layerNumb: Int) = (layerNumb / 3) + 1

    fun getGrandParentInd(grandParentLayer: Int, focusedPerson: Person): Int? {
        val grandFatherId = focusedPerson.father
        val grandMotherId = focusedPerson.mother
        var grandFatherInd: Int? = null
        var grandMotherInd: Int? = null
        var targetParentInd: Int? = null

        if (grandFatherId != null)
            grandFatherInd = findPersonIndById(grandFatherId, grandParentLayer)

        if (grandMotherId != null)
            grandMotherInd = findPersonIndById(grandMotherId, grandParentLayer)

        if (grandFatherInd != null && grandMotherInd != null)
            targetParentInd = Math.min(grandFatherInd, grandMotherInd)
        else if (grandFatherInd != null)
            targetParentInd = grandFatherInd
        else if (grandMotherInd != null)
            targetParentInd = grandMotherInd

        return targetParentInd
    }

    fun findParentSibIdInd(addedPerson: Person, parent: Person, parentLayer: Int)
            : MutableList<MutableList<Int>> {
        val drawParentSibList: MutableList<MutableList<Int>> = mutableListOf()
        val drawParentSibListId: MutableList<Int> = mutableListOf()
        val drawParentSibListInd: MutableList<Int> = mutableListOf()
        val parentLayer1: Int = when (parentLayer) {
            0 -> parentLayer
            else -> parentLayer - 3
        }
        val parentLayer2: Int = when (parentLayer) {
            0 -> parentLayer + 3
            else -> parentLayer
        }

        var targetParentInd: Int? = getGrandParentInd(parentLayer1, parent)

        if (targetParentInd == null && parentLayer1 > 0) {
            val addPersonFather = addedPerson.father
            val addPersonMother = addedPerson.mother
            // targetParentId = new FocusedPerson's Id
            val targetParentId = if (parent.idCard == addPersonFather) {
                addPersonMother
            } else {
                addPersonFather
            }

            // targetParent = new FocusedPerson
            val targetPerson = getPersonById(targetParentId!!, parentLayer)
            targetParentInd = getGrandParentInd(parentLayer1, targetPerson!!)
        } else if (targetParentInd == null && parentLayer1 == 0) {
            // Use in "FemaleNode"
            targetParentInd = findPersonInd(parent, parentLayer)
        }

        val grandParent = getPersonLayerInd(parentLayer1, targetParentInd!!)

        if (grandParent is Person) {
            val parentSibListId = grandParent.children
            val childrenParentLayer = getPersonLayer(parentLayer2)
            childrenParentLayer?.forEachIndexed { index, element ->
                if (element is Person) {
                    parentSibListId?.forEach { id ->
                        if (element.idCard == id) {
                            drawParentSibListId.add(id)
                        }
                    }
                }
            }
        }

        drawParentSibListId.forEach { id ->
            drawParentSibListInd.add(findPersonIndById(id, parentLayer2))
        }

        drawParentSibList.add(drawParentSibListId)
        drawParentSibList.add(drawParentSibListInd)

        return drawParentSibList
    }

    // When parent has more than 3 sib
    fun adjustUpperLayerPos(
        person: Person,
        bloodFParent: Person,
        parentLayer: Int,
        family: Family,
        bloodFamilyId: MutableList<Int>
    ) {
        // Adjust the children line above the parent layer
        val parentSib = findParentSibIdInd(person, bloodFParent, parentLayer)
        val parentSibInd = parentSib[1]
        val parentChildrenLine = findChildrenLine(parentLayer - 1, bloodFParent)!!

        // Adjust the grandparent layer and grandparent's marriage line above the parent parentLayer
        val parentSibNumb = parentSibInd.size
        val parentSibIndLength = parentSibInd[parentSibNumb - 1] - parentSibInd[0] + 1
        var expectingMoreNode = findMovingNodeNumb(parentSibIndLength)

        // Find children line above the parent layer
        val grandParentLayer = parentLayer - 3
        val leftGrandPa = parentChildrenLine.parentList[0]
        val leftGrandPaInd = findPersonInd(leftGrandPa, grandParentLayer)

        if (expectingMoreNode > 0) {
            // Find expecting the parent of the parent position
            val expectingPos = parentSibInd[0] + expectingMoreNode
            val addingEmptyNodes = Math.abs(leftGrandPaInd - expectingPos)

            // Moving the parent position
            if (addingEmptyNodes > 0) {
                for (i in 0 until addingEmptyNodes) {
                    for (j in grandParentLayer + 1 downTo 0) {
                        addFamilyStorageReplaceIndex(
                            j,
                            leftGrandPaInd,
                            null,
                            null
                        )
                    }
                }
            }
        }

        if (generationNumber(parentLayer) < 3)
            parentChildrenLine.extendLine(
                this,
                parentLayer - 1,
                parentSibInd,
                family,
                bloodFamilyId
            )
    }

    fun moveInd(
        child: Person, // focusedPerson
        movingPerson: Person,
        movingPersonLayer: Int,
        family: Family,
        addingMoreNode: Int,
        bloodFamilyId: MutableList<Int>
    ) {
        /*if (child.firstname == "Sindy") {
            print("------ Node 406 ------\n")
            print("child: ${child.firstname}\n")
            print("...............\n")
            val canvasB = displayObjectResult(this)
            print(canvasB.toString())
            print("---------------------------------------\n")
        }*/

        var focusedLayer = movingPersonLayer

        if (focusedLayer == 0) {
            val movingPersonInd = findPersonInd(movingPerson, movingPersonLayer)
            for (i in 0 until addingMoreNode) {
                for (layer in movingPersonLayer..movingPersonLayer + 1)
                    addFamilyStorageReplaceIndex(layer, movingPersonInd, null, null)
            }
        } else if (focusedLayer >= 3) {
            // Move 2 layers; the layer above and the layer below movingParentLayer
            // MovingPerson must be on the left-hand
            val parentChildrenLine = findChildrenLine(
                movingPersonLayer + 2, child
            )!!
            val leftHandPerson = parentChildrenLine.parentList[0]
            val leftHandPersonInd = findPersonInd(leftHandPerson, movingPersonLayer)
            val anotherParent = child.findAnotherParent(movingPerson, family)
            // The marriage line below the movingPersonLayer
            val marriageLineLayer = movingPersonLayer + 1
            val marriageLine = findMarriageLine(
                marriageLineLayer, movingPerson, anotherParent
            )!!
            val marriageLineInd = findLineInd(marriageLine, marriageLineLayer)!!

            // The children line above the movingPersonLayer
            val childrenLineLayer = movingPersonLayer - 1
            val childrenLine = findChildrenLine(childrenLineLayer, movingPerson)!!
            val childrenLineInd = findChildrenLineInd(childrenLine, childrenLineLayer)!!

            for (i in 0 until addingMoreNode) {
                addFamilyStorageReplaceIndex(childrenLineLayer, childrenLineInd, null, null)
                addFamilyStorageReplaceIndex(movingPersonLayer, leftHandPersonInd, null, null)
                addFamilyStorageReplaceIndex(marriageLineLayer, marriageLineInd, null, null)
            }

            focusedLayer -= 3
            val targetParent = movingPerson.getTargetParent(family, bloodFamilyId)
            val targetParentLayer = findPersonLayer(targetParent)

            moveInd(
                movingPerson,
                targetParent,
                targetParentLayer,
                family,
                addingMoreNode,
                bloodFamilyId
            )
        }
    }

    fun moveRightParentnLineLayer(
        movingNumb: Int,
        addingPersonIndSize: Int,
        parent1: Person,
        parent2: Person,
        parentLayer: Int
    ) {
        // Find the uncle/aunt who will be moved
        val storageSize = findStorageLayerSize(parentLayer)
        if (storageSize > addingPersonIndSize) {
            // Prepare for moving the line that related to the movedPerson;
            // MarriageLine and childrenLine if he/she has.
            val movedPerson = getPersonLayerInd(parentLayer, addingPersonIndSize) as Person
            val movingPSpouseId = if (storageSize > addingPersonIndSize + 1) {
                val nextPerson = getPersonLayerInd(parentLayer, addingPersonIndSize + 1) as Person
                if (movedPerson.gender == GenderLabel.MALE) {
                    movedPerson.wife?.firstOrNull { it == nextPerson.idCard }
                } else {
                    // movedPerson.gender == FEMALE
                    movedPerson.husband?.firstOrNull { it == nextPerson.idCard }
                }
            } else null
            val movingPSpouse = movingPSpouseId?.let {
                getPersonById(movingPSpouseId, parentLayer)
            }

            // Moving process
            for (i in 0 until movingNumb) {
                // Move the movedPerson layer first
                addFamilyStorageReplaceIndex(
                    parentLayer, addingPersonIndSize, null, null
                )

                // Move the line that related to the movedPerson; MarriageLine and childrenLine if he/she has.
                if (movingPSpouse != null) {
                    val mPersonMarriageLineLayer = parentLayer + 1
                    val mPersonMarriageLine = findMarriageLine(
                        mPersonMarriageLineLayer, movedPerson, movingPSpouse
                    )!!
                    val mPersonMarriageLineInd = findLineInd(
                        mPersonMarriageLine, mPersonMarriageLineLayer
                    )!!
                    // Move the marriage line
                    addFamilyStorageReplaceIndex(
                        mPersonMarriageLineLayer, mPersonMarriageLineInd, null, null
                    )
                    // Move the children line under the movedPerson
                    val childrenLineLayer = mPersonMarriageLineLayer + 1
                    val childrenLine = findChildrenLineByParent(
                        movedPerson, movingPSpouse, childrenLineLayer
                    )

                    childrenLine?.let {
                        val childrenLineInd = findChildrenLineInd(childrenLine, childrenLineLayer)!!
                        val childrenLineIndSize = findChildrenLineIndSize(
                            childrenLineLayer, 0, childrenLineInd - 1
                        )
                        val movedPersonInd = findPersonInd(movedPerson, parentLayer)
                        val movedPersonIndSize = findPersonIndSize(parentLayer, 0, movedPersonInd - 1)
                        if (childrenLineIndSize < movedPersonIndSize)
                            addFamilyStorageReplaceIndex(
                                childrenLineLayer, childrenLineInd, null, null
                            )
                    }
                }
            }
        }
    }

    fun findLeftParent(child: Person, parent: Person, parentLayer: Int, family: Family): Person {
        val anotherParent = child.findAnotherParent(parent, family)
        val parentInd = findPersonInd(parent, parentLayer)

        return if (anotherParent != null) {
            val anotherParentInd = anotherParent.let {
                findPersonInd(anotherParent, parentLayer)
            }
            if (parentInd < anotherParentInd)
                parent
            else
                anotherParent
        } else {
            parent
        }
    }

    fun findChildrenLineByParent(
        parent1: Person, parent2: Person?, childrenLineLayer: Int
    ): ChildrenLine? {
        val lineStorage = getPersonLayer(childrenLineLayer)

        lineStorage?.forEachIndexed { index, line ->
            if (line is ChildrenLine) {
                if (line.parentList.size == 2) {
                    var hasParent1 = line.parentList.firstOrNull {
                        it == parent1
                    } != null
                    var hasParent2 = line.parentList.firstOrNull {
                        it == parent2
                    } != null
                    if (hasParent1 && hasParent2) return lineStorage[index] as ChildrenLine
                } else {
                    var hasParent1 = line.parentList.firstOrNull { person ->
                        person == parent1
                    } != null
                    if (hasParent1) return lineStorage[index] as ChildrenLine
                }
            }
        }

        return null
    }

    fun findNextPerson(layerNumb: Int, afterInd: Int): Person? {
        val storage = getPersonLayer(layerNumb)

        storage?.forEachIndexed { index, any ->
            if (any is Person) {
                if (index > afterInd) {
                    return any
                }
            }
        }

        return null
    }

    fun findPreviousObj(layerNumb: Int, afterInd: Int): Any? {
        return if (afterInd > 0 && layerNumb < findPersonStorageSize() - 1) {
            val storage = getPersonLayer(layerNumb)
            storage?.get(afterInd - 1)
        } else null
    }

    fun moveNodeToRightHand(addingLayer: Int, startingInd: Int, endingInd: Int) {
        val myArray = getPersonLayer(addingLayer)!!

        if (endingInd < findStorageLayerSize(addingLayer))
            for (i in endingInd downTo startingInd + 1) {
                Collections.swap(myArray, i, i - 1)
            }
        else {
            addFamilyStorageReplaceIndex(
                addingLayer, startingInd, null, null
            )
        }
    }

    fun movingParentToRightHand(
        parent: Person,
        expectingInd: Int,
        parentInd: Int,
        parentLayer: Int,
        marriageLine: MarriageLine,
        pMarriageLineLayer: Int,
        bloodFamilyId: MutableList<Int>
    ) {

        var endingInd = pMarriageLineLayer + 1

        for (layer in parentLayer until endingInd) {
            val movingInd = if (layer == parentLayer) {
                parentInd
            } else {
                // Marriage Line Index
                findLineInd(marriageLine, pMarriageLineLayer)
            }

            for (index in parentInd until expectingInd) {
                if (movingInd != null) {
                    addFamilyStorageReplaceIndex(
                        layer,
                        movingInd,
                        null,
                        null
                    )
                }
            }
        }
    }

    fun separateAddedPersonParentLayer(
        addedPerson: Person,
        parent: Person,
        addingMoreNode: Int,
        parentInd: Int,
        parentLayer: Int,
        family: Family,
        bloodFamilyId: MutableList<Int>
    ) {
        if (parentLayer == 0) {
            val childrenLineLayer = parentLayer + 2
            val addedPersonSib = addedPerson.findSiblingByDrawer(
                this, childrenLineLayer
            )
            val addingPersonSibInd = addedPersonSib?.get(1) as MutableList<Int>
            val firstChildInd = addingPersonSibInd[0]
            val childrenNumb = (addingPersonSibInd[addingPersonSibInd.size - 1] - firstChildInd) + 1
            val addingMore = findAddingEmptyNodesParent(childrenNumb)
            val shouldAddMoreNode = firstChildInd + addingMore > parentInd
            if (shouldAddMoreNode)
                for (i in 0 until addingMoreNode) {
                    for (layer in 0 until parentLayer + 2)
                        addFamilyStorageReplaceIndex(layer, parentInd, null, null)
                }

            updateChildrenLineAtLayer(addedPerson, childrenLineLayer, family, bloodFamilyId)
        } else if (parentLayer >= 3) {
            // find the marriage line of the parent
            val marriageLineLayer = parentLayer + 1
            val anotherPerson = addedPerson.findAnotherParent(parent, family)
            val marriageLine = findMarriageLine(
                marriageLineLayer, parent, anotherPerson
            )!!
            val marriageLineInd = findLineInd(marriageLine, marriageLineLayer)!!
            val bloodFParent = addedPerson.getBloodFParent(family, bloodFamilyId)
            val childrenLineLayer = parentLayer - 1
            val childrenLine = findChildrenLine(childrenLineLayer, bloodFParent)!!
            val childrenLineInd = findChildrenLineInd(childrenLine, childrenLineLayer)!!

            // Whether the oldest sib in the children line is the left-parent
            val oldestSib = childrenLine.childrenList[0]
            val isOldestSibLeftParent = bloodFParent == oldestSib

            if (isOldestSibLeftParent) {
                // Move the children line above the parent layer
                for (i in 0 until addingMoreNode) {
                    addFamilyStorageReplaceIndex(childrenLineLayer, childrenLineInd, null, null)
                }
                // Move the the parent layer and the marriage line below the parent layer
                for (i in 0 until addingMoreNode) {
                    addFamilyStorageReplaceIndex(parentLayer, parentInd, null, null)
                    addFamilyStorageReplaceIndex(marriageLineLayer, marriageLineInd, null, null)
                }
                updateChildrenLineAtLayer(parent, childrenLineLayer, family, bloodFamilyId)

                val grandParentLayer = parentLayer - 3
                val grandParent = bloodFParent.getLeftHandParent(this, childrenLineLayer)
                val grandParentInd = findPersonInd(grandParent, grandParentLayer)

                separateAddedPersonParentLayer(
                    bloodFParent,
                    grandParent,
                    addingMoreNode,
                    grandParentInd,
                    grandParentLayer,
                    family,
                    bloodFamilyId
                )
            } else {
                // Move the marriage line below the parent layer
                addFamilyStorageReplaceIndex(marriageLineLayer, marriageLineInd, null, null)
                // Move the the parent layer
                addFamilyStorageReplaceIndex(parentLayer, parentInd, null, null)
                // Move the children line next to the parent's children line
                addFamilyStorageReplaceIndex(childrenLineLayer, childrenLineInd + 1, null, null)

                adjustUpperLayerPos(parent, bloodFParent, parentLayer, family, bloodFamilyId)
                updateChildrenLineAtLayer(parent, childrenLineLayer, family, bloodFamilyId)
                childrenLine.moveChildrenLineSign(this, childrenLineLayer)
            }
        }
    }

    fun separateAddedPersonParentLayerByLeftNode(
        addedPerson: Person,
        parent: Person,
        nextPerson: Person,
        expectingInd: Int,
        parentInd: Int,
        parentLayer: Int,
        family: Family,
        bloodFamilyId: MutableList<Int>
    ) {
        val addingEmptyNode = expectingInd - parentInd

        if (parentLayer >= 3) {
            // move Mike and Cara (addingPerson's parents)
            // move Mike and Cara marriage line
            val marriageLineLayer = parentLayer + 1 // Parents' marriage line
            val anotherParent = addedPerson.findAnotherParent(parent, family)
            val marriageLine = findMarriageLine(
                marriageLineLayer, parent, anotherParent
            )

            // find the person next to Cara => Lucy
            // use Lucy to find the children line above Lucy
            // move the children line above Lucy
            val nextPChildrenLineLayer = parentLayer - 1
            val nextPChildrenLine = findChildrenLine(nextPChildrenLineLayer, nextPerson)!!
            val nextPChildrenLineInd = findChildrenLineInd(nextPChildrenLine, nextPChildrenLineLayer)!!
            addFamilyStorageReplaceIndex(
                nextPChildrenLineLayer, nextPChildrenLineInd, null, null
            )
            // find Lucy(nextPerson)'s left-parent
            val npLeftParent = nextPerson.getLeftHandParent(this, nextPChildrenLineLayer)
            val anotherNPLeftParent = nextPerson.findAnotherParent(npLeftParent, family)
            val npBloodFParent = nextPerson.getBloodFParent(family, bloodFamilyId)
            val npLeftParentLayer = findPersonLayer(npLeftParent)
            val npLeftParentInd = findPersonInd(npLeftParent, npLeftParentLayer)
            val npLeftParentMarriageLineLayer = npLeftParentLayer + 1
            val npLeftParentMarriageLine = findMarriageLine(
                npLeftParentMarriageLineLayer, npLeftParent, anotherNPLeftParent
            )!!
            val npLeftParentMarriageLineInd = findLineInd(
                npLeftParentMarriageLine, npLeftParentMarriageLineLayer
            )!!

            for (i in 0 until addingEmptyNode) {
                movingParentToRightHand(
                    parent,
                    expectingInd,
                    parentInd,
                    parentLayer,
                    marriageLine!!,
                    marriageLineLayer,
                    bloodFamilyId
                )
                addFamilyStorageReplaceIndex(
                    npLeftParentLayer, npLeftParentInd, null, null
                )
                addFamilyStorageReplaceIndex(
                    npLeftParentMarriageLineLayer, npLeftParentMarriageLineInd, null, null
                )
            }

            // Update the children line
            updateChildrenLineAtLayer(parent, parentLayer - 1, family, bloodFamilyId)
            updateChildrenLineAtLayer(nextPerson, nextPChildrenLineLayer, family, bloodFamilyId)
            updateChildrenLineAtLayer(npBloodFParent, npLeftParentLayer - 1, family, bloodFamilyId)

            /*if (addedPerson.firstname == "Sindy") {
                print("------ Node 274 ------\n")
                print("add: ${addedPerson.firstname}\n")
                print("nextPerson: ${nextPerson.firstname}\n")
                print("...............\n")
                val canvasB = displayObjectResult(this)
                print(canvasB.toString())
                print("---------------------------------------\n")
            }*/
        }
    }

    fun updateChildrenLineAtLayer(
        addedPerson: Person,
        childrenLineLayer: Int,
        family: Family,
        bloodFamilyId: MutableList<Int>
    ) {
        val addedPersonSib = addedPerson.findSiblingByDrawer(
            this, childrenLineLayer
        )
        val addingPersonSibInd = addedPersonSib?.get(1) as MutableList<Int>

        val childrenLine = findChildrenLine(childrenLineLayer, addedPerson)
        childrenLine?.let {
            childrenLine.extendLine(
                this,
                childrenLineLayer,
                addingPersonSibInd,
                family,
                bloodFamilyId
            )
        }
    }

    fun findAddingEmptyNodesParent(childrenNumber: Int): Int {
        return if (childrenNumber % 2 == 0)
            childrenNumber / 2 - 1
        else
            (floor(childrenNumber / 2.0) - 1).toInt()
    }
}
