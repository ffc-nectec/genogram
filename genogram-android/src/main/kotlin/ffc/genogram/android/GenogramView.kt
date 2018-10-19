/*
 * Copyright (c) 2018 NECTEC
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

package ffc.genogram.android

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewParent
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import ffc.genogram.Family
import ffc.genogram.FamilyTree
import ffc.genogram.Person


class GenogramView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr) {

    val personViewHolder: PersonViewHolder = DummyPersonViewHolder()

    fun drawFamily(family: Family) {
        val drawer = FamilyTree(family).drawGenogram()
        val column = drawer.personFamilyStorage.maxBy { it.size }!!.size


        setAlignmentMode(GridLayout.ALIGN_MARGINS)
        this.columnCount = column
        this.rowCount = drawer.personFamilyStorage.size
        Toast.makeText(context, "$rowCount:$columnCount", Toast.LENGTH_SHORT).show()

        println("======================")
        drawer.personFamilyStorage.forEachIndexed { row, layer ->
            layer.forEachIndexed { col, node ->
                when (node) {
                    is Person -> {
                        print(node.firstname)
                        val view = personViewHolder.onCreateView(context, this)
                        personViewHolder.onBind(view, node)
                        addView(view, childLayoutParams(row, col))
                    }
                    else -> {
                        print(node)
                        addView(TextView(context).apply { text = "$row : $col" }, childLayoutParams(row, col))
                    }
                }
            }
        }

    }

    fun childLayoutParams(row: Int, col: Int): GridLayout.LayoutParams {
        return GridLayout.LayoutParams().apply {
            rowSpec = GridLayout.spec(row)
            columnSpec = GridLayout.spec(col)
            setMargins(0, itemMargin, itemMargin, 0)
        }
    }

    val itemMargin: Int
        get() = dip(8)

    interface PersonViewHolder {

        fun onCreateView(context: Context, parent: ViewParent): View

        fun onBind(view: View, person: Person)
    }

    class DummyPersonViewHolder : PersonViewHolder {

        override fun onCreateView(context: Context, parent: ViewParent): View {
            return Button(context)
        }

        override fun onBind(view: View, person: Person) {
            view as Button
            view.text = "${person.firstname}"
        }
    }

}

internal fun View.dip(dipValue: Int): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue.toFloat(), resources.displayMetrics).toInt()
