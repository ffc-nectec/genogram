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
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import ffc.genogram.Family
import ffc.genogram.FamilyTree
import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.Node.EmptyNode
import ffc.genogram.Person
import ffc.genogram.RelationshipLine.MarriageLine

class GenogramView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr) {

    init {
        setWillNotDraw(false)
        RelationPath.generationMargin = dip(120)
    }

    var personViewHolder: PersonViewHolder = DummyPersonViewHolder()
    private val personViews: MutableMap<Person, View> = hashMapOf()
    private val relationPath: MutableList<RelationPath> = arrayListOf()

    val dummyPersonforLine = mutableListOf<Person>()

    lateinit var drawer: FamilyTreeDrawer

    fun drawFamily(family: Family) {
        drawer = FamilyTree(family).drawGenogram()
        val column = drawer.personFamilyStorage.maxBy { it.size }!!.size

        alignmentMode = GridLayout.ALIGN_BOUNDS
        this.columnCount = column
        this.rowCount = drawer.personFamilyStorage.size

        var count = 0
        drawer.personFamilyStorage.forEachIndexed { row, layer ->
            layer.forEachIndexed { col, node ->
                when (node) {
                    is Person -> {
                        val view = personViewHolder.viewFor(node, context, this)
                        val span = if (node.nodeMargin > 0) 2 else 1
                        addView(view, layoutParamsFor(row, col, colSpan = span))
                        personViews.put(node, view)
                        if (count < 2) {
                            dummyPersonforLine.add(node)
                            count++
                        }
                    }
                    is EmptyNode -> {
                        addView(TextView(context).apply { text = "$row : $col" }, layoutParamsFor(row, col).apply {
                            height = RelationPath.generationMargin / 2
                        })
                    }
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d("geno", "on Drawn ${relationPath.size}")
        drawer.personFamilyStorage.forEachIndexed { row, layer ->
            layer.forEachIndexed { col, node ->
                when (node) {
                    is MarriageLine -> {
                        val fatherRect = personViews[dummyPersonforLine[0]]!!.relativePosition
                        val motherRect = personViews[dummyPersonforLine[1]]!!.relativePosition
                        val line = MarriagePath(fatherRect to motherRect)
                        relationPath.add(line)
                        Log.d("geno", "father ${fatherRect.left}x${fatherRect.top}")
                    }
                }
            }
        }

        relationPath[0].drawOn(canvas!!)
        Toast.makeText(context, "on Draw", Toast.LENGTH_SHORT).show()
    }

    fun layoutParamsFor(row: Int, col: Int, colSpan: Int = 1): GridLayout.LayoutParams {
        return GridLayout.LayoutParams().apply {
            rowSpec = GridLayout.spec(row)
            columnSpec = GridLayout.spec(col, colSpan)
            setMargins(itemMargin, 0, itemMargin, 0)
        }
    }

    val itemMargin: Int = dip(8)
}

val View.relativePosition: Rect
    get() {
        val rect = Rect()
        (parent as ViewGroup).offsetDescendantRectToMyCoords(this, rect)
        rect.right = measuredWidth + rect.left
        rect.bottom = measuredHeight + rect.top
        Log.d("rect", "${rect.left} ${rect.top} ${rect.right} ${rect.bottom}")
        return rect
    }
