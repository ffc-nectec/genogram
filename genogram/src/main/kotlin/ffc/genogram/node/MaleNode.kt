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

import ffc.genogram.FamilyTreeDrawer

class MaleNode(
    var familyTreeDrawer: FamilyTreeDrawer,
    var nodeName: String
) : Node() {

    override fun drawNode(): FamilyTreeDrawer {
        // TODO: draw node
        familyTreeDrawer.addFamilyLayer("[$nodeName]")
        return familyTreeDrawer
    }

    override fun getArea(): Double = nodeSize * nodeSize
}
