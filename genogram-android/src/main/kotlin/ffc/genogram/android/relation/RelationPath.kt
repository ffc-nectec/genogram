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
import android.graphics.Paint

abstract class RelationPath {

    var paint: Paint = Paint().apply {
        setAntiAlias(true)
        setStyle(Paint.Style.STROKE)
        setColor(-0x78000000)
        setStrokeWidth(6f)
    }

    init {
        customPaintPath?.let { paint.apply(it) }
    }

    abstract fun drawOn(canvas: Canvas)

    companion object {
        var generationMargin = 100

        var customPaintPath: (Paint.() -> Unit)? = null
    }
}
