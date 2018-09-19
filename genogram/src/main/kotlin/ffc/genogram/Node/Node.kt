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

package ffc.genogram.Node

import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.GenderLabel
import ffc.genogram.RelationshipLine.RelationshipLabel

abstract class Node {

    companion object {
        const val borderline = 3.0
        const val color = 0xff888888
        const val nodeSize = 6.0
        const val nodesDistance = 2.0
        const val nodeBorderSize = 2.0
    }

    abstract fun drawNode(relationLabel: RelationshipLabel?, siblings: Boolean): FamilyTreeDrawer

    abstract fun getArea(): Double

    fun setNodePosition(nodeName: String, gender: Int, siblings: Boolean): String {
        val diff = (((nodeSize * 2) - nodeName.length) / 2).toInt() + 2
        val space = " "
        var resultSpace = ""

        if (!siblings)
            for (i in 0 until diff) {
                resultSpace += space
            }

        val newNodeName = if (gender == 0) {
            createGenderBorder(nodeName, GenderLabel.MALE)
        } else {
            createGenderBorder(nodeName, GenderLabel.FEMALE)
        }

        return resultSpace + newNodeName + resultSpace
    }

}
