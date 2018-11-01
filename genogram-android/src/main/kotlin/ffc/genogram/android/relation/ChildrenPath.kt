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

package ffc.genogram.android.relation

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect

class ChildrenPath(val parent: Pair<Rect, Rect?>, val children: List<Rect>) : RelationPath() {

    private val father = parent.first
    private val mother = parent.second

    override fun drawOn(canvas: Canvas) {
        val right = (mother?.right ?: father.right).toFloat()
        val orgX: Float = father.left + (right - father.left) / 2
        val orgY: Float = if (mother != null)
            father.bottom + (generationMargin * MarriagePath.crossPointRatio)
        else
            father.exactCenterY()
        val mergeY = father.bottom + (generationMargin * mergePointRatio)

        val path = Path().apply {
            moveTo(orgX, orgY)
            lineTo(orgX, mergeY)
            children.forEach {
                moveTo(orgX, mergeY)
                lineTo(it.exactCenterX(), mergeY)
                lineTo(it)
            }
        }
        canvas.drawPath(path, paint)
    }

    companion object {
        const val mergePointRatio = 0.66f
    }
}
