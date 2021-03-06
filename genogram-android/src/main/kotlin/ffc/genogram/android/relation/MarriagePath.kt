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

class MarriagePath(val rects: Pair<Rect, Rect>) : RelationPath() {

    override fun drawOn(canvas: Canvas) {
        val path = Path()
        val x1 = rects.first.centerX().toFloat()
        val x2 = rects.second.centerX().toFloat()
        val y2 = rects.first.centerY() + rects.first.height() / 2 + generationMargin * crossPointRatio
        path.moveTo(rects.first)
        path.lineTo(x1, y2)
        path.lineTo(x2, y2)
        path.lineTo(rects.second)
        canvas.drawPath(path, paint)
    }

    companion object {
        const val crossPointRatio = 0.33f
    }
}
