package com.adityakarang.sampahku.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doAfterTextChanged
import com.adityakarang.sampahku.R
import com.adityakarang.sampahku.utils.sp

class NameEditText : AppCompatEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private var paintTextError: Paint? = null
    private var rectError: Rect? = null
    private var error: String = ""

    private fun init() {
        error = context.getString(R.string.error_email)

        paintTextError = Paint(Paint.FAKE_BOLD_TEXT_FLAG)
            .apply {
                textSize = 36.sp
                color = Color.RED
                this.alpha = 0
            }

        rectError = Rect()

        doAfterTextChanged {
            paintTextError?.alpha = if (it.toString().isValidEmail()) 0 else 255
        }
    }

    private fun String.isValidEmail() =
        !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START

        paintTextError?.getTextBounds(error, 0, error.length, rectError)
        paintTextError?.let {
            canvas.drawText(
                error,
                paddingLeft.toFloat(),
                height.toFloat(),
                it
            )
        }
    }

}
