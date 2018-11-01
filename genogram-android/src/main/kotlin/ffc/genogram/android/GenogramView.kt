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

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import ffc.genogram.Family
import ffc.genogram.FamilyTree
import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.Person
import ffc.genogram.RelationshipLine.ChildrenLine
import ffc.genogram.RelationshipLine.MarriageLine
import ffc.genogram.android.relation.ChildrenPath
import ffc.genogram.android.relation.MarriagePath
import ffc.genogram.android.relation.RelationPath

class GenogramView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr) {

    init {
        setWillNotDraw(false)
        RelationPath.generationMargin = dip(120)
    }

    var onDrawFinished: (() -> Unit)? = null
    var nodeBuilder: GenogramNodeBuilder = defaultNodeBuilder
    private val personViews: MutableMap<Person, View> = hashMapOf()
    private val relationPath: MutableList<RelationPath> = arrayListOf()

    private var drawer : FamilyTreeDrawer? = null

    fun drawFamily(family: Family) {
        drawer = FamilyTree(family).drawGenogram()

        val column = drawer!!.personFamilyStorage.maxBy { it.size }!!.size

        alignmentMode = GridLayout.ALIGN_BOUNDS
        this.columnCount = column
        this.rowCount = drawer!!.personFamilyStorage.size

        drawer!!.personFamilyStorage.forEachIndexed { row, layer ->
            layer.forEachIndexed { col, node ->
                when (node) {
                    is Person -> {
                        val view = nodeBuilder.viewFor(node, context, this)
                        val span = if (node.nodeMargin > 0) 2 else 1
                        addView(view, layoutParamsFor(row, col, colSpan = span))
                        personViews.put(node, view)
                    }
                    else -> {
                        addView(View(context), layoutParamsFor(row, col).apply {
                            height = RelationPath.generationMargin / 2
                        })
                    }
                }
            }
        }
        invalidate()
        onDrawFinished?.invoke()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawer?.personFamilyStorage?.forEachIndexed { _, layer ->
            layer.forEachIndexed { _, relation ->
                when (relation) {
                    is MarriageLine -> {
                        relation.getSpouseList().forEach { pair ->
                            val first = personViews[pair[0]]!!.relativePosition
                            val second = personViews[pair[1]]!!.relativePosition
                            val line = MarriagePath(first to second)
                            relationPath.add(line)
                        }
                    }
                    is ChildrenLine -> {
                        //TODO remove check
                        if (relation.parentList.isNotEmpty() && relation.childrenList.isNotEmpty()) {
                            val first = personViews[relation.parentList[0]]!!.relativePosition
                            val second = personViews[relation.parentList[1]]?.relativePosition
                            val line = ChildrenPath(
                                    parent = first to second,
                                    children = relation.childrenList.map { personViews.get(it)!!.relativePosition })
                            relationPath.add(line)
                        }
                    }
                }
            }
        }
        relationPath.forEach { it.drawOn(canvas!!) }
    }

    private fun layoutParamsFor(row: Int, col: Int, colSpan: Int = 1): GridLayout.LayoutParams {
        return GridLayout.LayoutParams().apply {
            rowSpec = GridLayout.spec(row)
            columnSpec = GridLayout.spec(col, colSpan)
            setMargins(itemMargin, 0, itemMargin, 0)
        }
    }

    var itemMargin: Int = dip(8)

    companion object {
        var defaultNodeBuilder: GenogramNodeBuilder = DummyGenogramNodeBuilder()
    }
}

private val View.relativePosition: Rect
    get() {
        return Rect().apply {
            (parent as ViewGroup).offsetDescendantRectToMyCoords(this@relativePosition, this)
            right = measuredWidth + left
            bottom = measuredHeight + top
            Log.d("rect", "left=$left top=$top right=$right bottom=$bottom")
        }
    }

internal fun View.dip(dipValue: Int): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue.toFloat(), resources.displayMetrics).toInt()
