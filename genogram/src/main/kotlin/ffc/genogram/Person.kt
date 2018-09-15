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

class Person(
    var idCard: Long,
    var firstname: String,
    var lastname: String,
//    private var birthDate: Date,
    var birthDate: String?,
    var gender: Int,
    var father: Int?,
    var mother: Int?,
    var twin: List<Int>?,
    var exHusband: List<Int>?,
    var exWife: List<Int>?,
    var husband: List<Int>?,
    var wife: List<Int>?,
    var children: List<Int>?,
    var linkedStack: List<Int>?
) {

    fun hasBeenDivorced(): Boolean {
        return exHusband != null || exWife != null
    }

    fun haveChildren(relatedPerson: Person): ArrayList<Int>? {
        var childrenList: ArrayList<Int>? = arrayListOf()
        // find the focusedPerson's children
        // find the focusedPerson's children who also is the relatedPerson's children
        val fChildren: List<Int>? = children
        val rChildren: List<Int>? = relatedPerson.children
        if (fChildren != null) {
            for (i in 0 until fChildren.size) {
                rChildren!!.find { it == fChildren[i] }?.let {
                    childrenList!!.add(it)
                }
            }
        } else {
            childrenList = null
        }

        // TODO: delete focusedPerson and relatedPerson out of children'stack

        return childrenList
    }

    fun removeLinkedStack(removeList: MutableList<Int>) {

        val tmp: MutableList<Int> = linkedStack as MutableList<Int>
        if (linkedStack!!.size == 1 && removeList.size == 1) {
            if (linkedStack!![0] == removeList[0])
                linkedStack = null
        } else {
            for (i in 0 until removeList.size) {
                removeList.find { it == tmp[i] }?.let {
                    tmp.removeAt(i)
                }
            }
            linkedStack = tmp
        }
    }

    fun removeListLinkedStack(removeList: MutableList<Int>) {
        var tmp: MutableList<Int>? = mutableListOf()

        if (linkedStack != null) {
            tmp = linkedStack as MutableList<Int>
            for (i in 0 until removeList.size) {
                tmp.find { it == removeList[i] }?.let {
                    tmp!!.remove(it)
                }
            }
        }

        if (tmp!!.isEmpty())
            tmp = null
        linkedStack = tmp
    }

    fun popChildren(childrenList: MutableList<Int>, person2: Person?, familyMembers: List<Person>?)
            : ArrayList<Person> {
        val childrenObjList: ArrayList<Person> = arrayListOf()
        for (i in 0 until familyMembers!!.size) {
            childrenList.find { it == familyMembers!![i].idCard.toInt() }?.let {
                val child = familyMembers[i]
                val tmpChildStack: MutableList<Int> = child.linkedStack as MutableList<Int>
                tmpChildStack.remove(idCard.toInt())
                if (person2 != null)
                    tmpChildStack.remove(person2.idCard.toInt())
                if (tmpChildStack.isEmpty())
                    child.linkedStack = null
                else
                    child.linkedStack = tmpChildStack
                childrenObjList.add(child)
            }
        }
        return childrenObjList
    }
}
