package com.example.sampleapp.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.squareup.picasso.Transformation


class BlurTransformation(context: Context?, private val percentage: Float) : Transformation {
    private val rs: RenderScript = RenderScript.create(context)

    override fun transform(bitmap: Bitmap): Bitmap {
        val blurredBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)

        // Apply the blur effect multiple times for a stronger effect
        for (i in 0 until 10) {  // Adjust the loop count as needed for the desired effect
            val input = Allocation.createFromBitmap(
                rs,
                blurredBitmap,
                Allocation.MipmapControl.MIPMAP_FULL,
                Allocation.USAGE_SHARED
            )
            val output = Allocation.createTyped(rs, input.type)
            val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
            script.setInput(input)
            script.setRadius(percentage)
            script.forEach(output)
            output.copyTo(blurredBitmap)
        }

        bitmap.recycle() // Recycle the original bitmap to free up memory

        return blurredBitmap
    }

    override fun key(): String {
        return "blur_$percentage"
    }
}