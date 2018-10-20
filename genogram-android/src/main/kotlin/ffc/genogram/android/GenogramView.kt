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
import android.widget.GridLayout
import android.widget.TextView
import ffc.genogram.Family
import ffc.genogram.FamilyTree
import ffc.genogram.Person


class GenogramView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr) {

    var personViewHolder: PersonViewHolder = DummyPersonViewHolder()

    fun drawFamily(family: Family) {
        val drawer = FamilyTree(family).drawGenogram()
        val column = drawer.personFamilyStorage.maxBy { it.size }!!.size

        alignmentMode = GridLayout.ALIGN_BOUNDS
        this.columnCount = column
        this.rowCount = drawer.personFamilyStorage.size

        drawer.personFamilyStorage.forEachIndexed { row, layer ->
            layer.forEachIndexed { col, node ->
                when (node) {
                    is Person -> {
                        val view = personViewHolder.viewFor(node, context, this)
                        addView(view, layoutParamsFor(row, col))
                    }
                    else -> {
                        addView(TextView(context).apply { text = "$row : $col" }, layoutParamsFor(row, col))
                    }
                }
            }
        }
    }

    fun layoutParamsFor(row: Int, col: Int): GridLayout.LayoutParams {
        return GridLayout.LayoutParams().apply {
            rowSpec = GridLayout.spec(row)
            columnSpec = GridLayout.spec(col)
            setMargins(0, itemMargin, itemMargin, 0)
        }
    }

    val itemMargin: Int = dip(8)
}
