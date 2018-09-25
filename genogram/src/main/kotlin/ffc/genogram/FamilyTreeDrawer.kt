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
import ffc.genogram.RelationshipLine.RelationshipLabel

class FamilyTreeDrawer {

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
        nameFamilyStorage[layer].add(element)

        if (person != null)
            personFamilyStorage[layer].add(person)
        else
            personFamilyStorage[layer].add(element)
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

    fun replaceFamilyStorageIndex(layerNumb: Int, replaceInd: Int, node: String?) {
        val layer = nameFamilyStorage[layerNumb]

        if (node != null)
            layer[replaceInd] = node
        else
            layer[replaceInd] = createEmptyNode()
    }

    fun findStorageSize(): Int = nameFamilyStorage.size

    fun findPersonStorageSize(): Int = personFamilyStorage.size

    fun findPersonLayerSize(layerNumb: Int): Int = personFamilyStorage[layerNumb].size

    private fun findStorageLayerSize(layerNumb: Int): Int {
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

        nodeList.forEachIndexed { index, person ->
            person as Person
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

    fun extendRelationshipLine(layerNumb: Int, index: Int, relation: RelationshipLabel)
            : String {
        val lineLayer = nameFamilyStorage[layerNumb + 1]
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
