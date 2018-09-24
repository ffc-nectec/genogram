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
import ffc.genogram.Node.createGenderBorder
import ffc.genogram.RelationshipLine.Relationship.Companion.distanceLine
import ffc.genogram.RelationshipLine.RelationshipLabel

class FamilyTreeDrawer {

    var familyLayers: ArrayList<String> = ArrayList()
    var familyStorage: ArrayList<ArrayList<String>> = ArrayList()

    fun addFamilyLayer(name: String, storage: ArrayList<ArrayList<String>>) {
        familyLayers.add(name)

        if (storage.isEmpty()) {
            addFamilyStorage(familyLayers)
        }
    }

    fun addFamilyNewLayer(name: String) {
        val newLayers: ArrayList<String> = arrayListOf(name)
        familyStorage.add(newLayers)
    }

    fun addFamilyAtLayer(layer: Int, element: String) {
        familyStorage[layer].add(element)
    }

    fun addEmptyNewLayer() {
        val newLayers: ArrayList<String> = arrayListOf()
        familyStorage.add(newLayers)
    }

    private fun addFamilyStorage(newLayer: ArrayList<String>) {
        familyStorage.add(newLayer)
    }

    fun addFamilyStorageReplaceIndex(layerNumb: Int, replaceInd: Int, node: String?) {
        if (node != null)
            familyStorage[layerNumb].add(replaceInd + 1, node)
        else
            familyStorage[layerNumb].add(replaceInd, createEmptyNode())
    }

    fun replaceFamilyStorageIndex(layerNumb: Int, replaceInd: Int, node: String?) {
        val layer = familyStorage[layerNumb]

        if (node != null)
            layer[replaceInd] = node
        else
            layer[replaceInd] = createEmptyNode()
    }

    fun findStorageSize(): Int = familyStorage.size

    private fun findStorageLayerSize(layerNumb: Int): Int {
        return if (familyStorage[layerNumb].isNotEmpty())
            familyStorage[layerNumb].size
        else
            -1
    }

    fun findParentsPosition(): MutableList<Double> {
        val latestLayer = familyStorage[familyStorage.size - 2]
        val parent1Ind = (latestLayer.size - 1).toDouble()
        val parent2Ind = (latestLayer.size - 2).toDouble()

        return mutableListOf(parent2Ind, parent1Ind)
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
    fun findPersonInd(focusedPerson: Person, storageLayer: Int): Int {
        var personName = focusedPerson.firstname
        val nodeList = familyStorage[storageLayer]

        personName = if (focusedPerson.gender == 0)
            createGenderBorder(personName, GenderLabel.MALE)
        else
            createGenderBorder(personName, GenderLabel.FEMALE)

        nodeList.forEachIndexed { index, string ->
            if (string == personName)
                return index
        }

        return 0
    }

    fun findPersonLayer(focusedPerson: Person): Int {
        var targetLayer = 0
        val gender = if (focusedPerson.gender == 0)
            GenderLabel.MALE else GenderLabel.FEMALE
        val tmp = createGenderBorder(focusedPerson.firstname, gender)

        for (i in (familyStorage.size - 1) downTo 0) {
            familyStorage[i].find { it == tmp }?.let {
                targetLayer = i
            }
        }

        return targetLayer
    }

    fun extendRelationshipLine(layerNumb: Int, index: Int, relation: RelationshipLabel)
            : String {
        val lineLayer = familyStorage[layerNumb + 1]
        val line = lineLayer[index]
        val tmp = StringBuilder()

        val addingLine = createLine(relation, distanceLine.toInt())

        if (relation == RelationshipLabel.MARRIAGE) {

            for (i in 0 until line.length / 2) {
                tmp.append(line[i])
            }

            tmp.append(addingLine)

            for (i in line.length / 2 downTo 0) {
                tmp.append(line[i])
            }
        } else if (relation == RelationshipLabel.CHILDREN) {
            val upSign = "^"
            var signInd = 0

            line.forEachIndexed { ind, c ->
                if (c.toString() == upSign)
                    signInd = ind
            }

            for (i in 0 until signInd) {
                tmp.append(line[i])
            }

            tmp.append(addingLine)
            tmp.append(line[signInd])
            tmp.append(addingLine)

            for (i in signInd + 1 until line.length) {
                tmp.append(line[i])
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
}
