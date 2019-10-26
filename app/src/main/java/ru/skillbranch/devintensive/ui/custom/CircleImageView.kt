package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import ru.skillbranch.devintensive.R

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : ImageView(context, attrs, defStyleAttr){

    companion object {
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
        private const val DEFAULT_BORDER_WIDTH = 2
    }

    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = DEFAULT_BORDER_WIDTH

    private var mStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init{
        if (attrs != null){
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)

            borderColor = typedArray.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDER_COLOR)
            borderWidth = typedArray.getInt(R.styleable.CircleImageView_cv_borderWidth, DEFAULT_BORDER_WIDTH)

            typedArray.recycle()
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val size = Math.min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }


    fun getBorderWidth():Int = borderWidth


    fun setBorderWidth(@Dimension dp:Int){
        borderWidth = dp
        invalidate()
    }

    fun getBorderColor():Int = borderColor


    fun setBorderColor(hex:String){
        borderColor = Color.parseColor(hex)
        invalidate()
    }

    fun setBorderColor(@ColorRes colorId: Int){
        borderColor = ContextCompat.getColor(context, colorId)
        invalidate()
    }
/*
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawBitmap(canvas)
        drawStroke(canvas)
    }

    protected fun drawStroke(canvas: Canvas?) {
        if (mStrokePaint.getStrokeWidth() > 0f) {
            canvas!!.drawOval(mStrokeBounds, mStrokePaint)
        }
    }

    protected fun drawBitmap(canvas: Canvas?) {
        canvas!!.drawOval(mBitmapDrawBounds, mBitmapPaint)
    }
*/

}