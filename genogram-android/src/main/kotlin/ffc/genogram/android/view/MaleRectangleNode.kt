package ffc.genogram.android.view

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.View
import ffc.genogram.android.R

@TargetApi(Build.VERSION_CODES.M)
class MaleRectangleNode @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Playground
    private var mRectSquare: Rect = Rect()
    private var mPaintSquare: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mSquareColor: Int = 0
    lateinit var nodeName: String

    companion object {
        const val SQUARE_SIZE = 200
    }

    init {
        mPaintSquare.color = resources.getColor(R.color.gray_300, null)

        if (attrs != null) {
            val typedArray = getContext()
                .obtainStyledAttributes(attrs, R.styleable.MaleRectangleNode)
            mSquareColor = typedArray.getColor(R.styleable.MaleRectangleNode_square_color,
                resources.getColor(R.color.gray_300, null))

            typedArray.recycle()
        }

    }

    @SuppressLint("DrawAllocation", "ResourceAsColor")
    override fun onDraw(canvas: Canvas) {
        /*canvas.drawRGB(255, 255, 255) // White color
        canvas.drawColor(Color.BLACK)
        val width = width
        val paint = Paint()
        paint.setARGB(128, 128, 0, 0) // Gray color
        canvas.drawLine(0f, 30f, width.toFloat(), 30f, paint)
        paint.strokeWidth = 4f
        canvas.drawLine(0f, 60f, width.toFloat(), 60f, paint)*/

        // Playground
        mRectSquare.left = 50
        mRectSquare.top = 50
        mRectSquare.right = mRectSquare.left + SQUARE_SIZE
        mRectSquare.bottom = mRectSquare.top + SQUARE_SIZE

        canvas.drawRect(mRectSquare, mPaintSquare)
    }

    @SuppressLint("ResourceAsColor")
    fun swapColor() {
        mPaintSquare.color = if (mPaintSquare.color == mSquareColor)
            Color.RED
        else
            mSquareColor
            //resources.getColor(R.color.gray_300, null)

        postInvalidate()
    }
}
