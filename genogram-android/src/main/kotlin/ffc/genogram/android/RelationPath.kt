package ffc.genogram.android

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect

abstract class RelationPath {

    var paint: Paint = Paint().apply {
        setAntiAlias(true)
        setStyle(Paint.Style.STROKE)
        setColor(-0x78000000)
        setStrokeWidth(6f)
    }

    abstract fun drawOn(canvas: Canvas)

    companion object {

        var generationMargin = 100
    }
}

class MarriagePath(val rects: Pair<Rect, Rect>) : RelationPath() {

    override fun drawOn(canvas: Canvas) {
        val path = Path()
        val x1 = rects.first.centerX().toFloat()
        val x2 = rects.second.centerX().toFloat()
        val y1 = rects.first.centerY().toFloat()
        val y2 = rects.first.centerY() + rects.first.height() / 2 + generationMargin * 0.35f
        path.moveTo(x1, y1)
        path.lineTo(x1, y2)
        path.lineTo(x2, y2)
        path.lineTo(x2, y1)
        canvas.drawPath(path, paint)
    }
}

class ChildrenPath(val parent: Pair<Rect, Rect?>, children: List<Rect>) : RelationPath() {

    override fun drawOn(canvas: Canvas) {

    }
}
