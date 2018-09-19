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

    fun addFamilyStorageReplaceIndex(index: Int, replaceInd: Int) {
        familyStorage[index].add(replaceInd, createEmptyNode())
    }

    fun findStorageSize(): Int = familyStorage.size

    fun findParentsPosition(): MutableList<Double> {
        val latestLayer = familyStorage[familyStorage.size - 2]
        val parent1Ind = (latestLayer.size - 1).toDouble()
        val parent2Ind = (latestLayer.size - 2).toDouble()

        return mutableListOf(parent2Ind, parent1Ind)
    }

    // find the person index
    fun findPersonInd(focusedPerson: Person, storageLayer: Int): Int {

        val personName = focusedPerson.firstname
        val nodeList = familyStorage[storageLayer]

        nodeList.forEachIndexed { index, string ->
            if (string == personName)
                return index
        }

        return 0
    }

    fun findPersonLayer(focusedPerson: Person): Int {
        var targetLayer = 0
//        var tmp = setNodeSize(focusedPerson.firstname)
//        tmp = if (focusedPerson.gender == 0) "[$tmp]" else "($tmp)"
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
}
