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

    fun removeLinkedStack(removeList: MutableList<Int>) {
        val tmp: MutableList<Int> = (linkedStack as MutableList<Int>?)!!
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
//
//        if (linkedStack != null) {
//            var tmp: MutableList<Int>? = linkedStack as MutableList<Int>
//
//            if (tmp!!.isNotEmpty() && tmp.size > 1) {
//                for (i in 0 until tmp.size) {
//                    removeList.find { it == tmp!![i] }?.let {
//                        tmp!!.removeAt(i)
//                    }
//                }
//            }
//            if (tmp.size == 1)
//                tmp = null
//
//            /*if (tmp!!.size == 1)
//                tmp = null
//            else
//                for (i in 0 until tmp.size) {
//                    if (tmp[i] == personId.toInt()) {
//                        tmp.removeAt(i)
//                    }
//                }*/
//
//            linkedStack = tmp
//        }
    }

    fun removeListLinkedStack(removeList: MutableList<Int>) {
        var tmp: MutableList<Int>? = null
        if (linkedStack != null)
            tmp = linkedStack as MutableList<Int>
        for (i in 0 until tmp!!.size) {
            removeList.find { it == tmp[i] }?.let {
                tmp.removeAt(i)
            }
        }
        linkedStack = tmp
    }
}
