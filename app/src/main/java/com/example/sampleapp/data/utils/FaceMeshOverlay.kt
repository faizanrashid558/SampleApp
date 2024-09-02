package com.example.sampleapp.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.google.mlkit.vision.facemesh.FaceMesh

class FaceMeshOverlay(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }

    private val meshPointPaint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.FILL
        strokeWidth = 2f
    }

    private var faceMeshes: List<FaceMesh> = emptyList()
    private var bitmap: Bitmap? = null

    fun setFaceMeshes(faceMeshes: List<FaceMesh>) {
        this.faceMeshes = faceMeshes
        invalidate() // Trigger a redraw of the view
    }

    fun setBitmap(bitmap: Bitmap) {
        this.bitmap = bitmap
        invalidate() // Trigger a redraw of the view
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the image if available
        bitmap?.let {
            // Calculate aspect ratio scaling
            val viewAspectRatio = width.toFloat() / height
            val bitmapAspectRatio = it.width.toFloat() / it.height
            val scale: Float
            val offsetX: Float
            val offsetY: Float

            if (bitmapAspectRatio > viewAspectRatio) {
                scale = width.toFloat() / it.width
                offsetX = 0f
                offsetY = (height - it.height * scale) / 2
            } else {
                scale = height.toFloat() / it.height
                offsetX = (width - it.width * scale) / 2
                offsetY = 0f
            }

            val destRect = RectF(offsetX, offsetY, offsetX + it.width * scale, offsetY + it.height * scale)
            canvas.drawBitmap(it, null, destRect, null)

            // Draw each face's mesh points
            for (faceMesh in faceMeshes) {
                for (point in faceMesh.allPoints) {
                    val cx = point.position.x * scale + offsetX
                    val cy = point.position.y * scale + offsetY
                    canvas.drawCircle(cx, cy, 4f, meshPointPaint)
                }
            }
        }
    }
}