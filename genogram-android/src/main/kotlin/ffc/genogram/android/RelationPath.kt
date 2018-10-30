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

    init {
        customPaintPath?.let { paint.apply(it) }
    }

    abstract fun drawOn(canvas: Canvas)

    companion object {
        var generationMargin = 100

        var customPaintPath: (Paint.() -> Unit)? = null
    }
}

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
        val mergeY = father.bottom + (generationMargin * ChildrenPath.mergePointRatio)

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

private fun Path.lineTo(rect: Rect) {
    lineTo(rect.exactCenterX(), rect.exactCenterY())
}

private fun Path.moveTo(rect: Rect) {
    moveTo(rect.exactCenterX(), rect.exactCenterY())
}
