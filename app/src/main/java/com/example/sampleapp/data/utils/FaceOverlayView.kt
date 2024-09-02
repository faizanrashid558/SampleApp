package com.example.sampleapp.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceLandmark

class FaceOverlayView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }

    private val landmarkPaint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.FILL
        strokeWidth = 3f
    }

    private var faces: List<Face> = emptyList()
    private var bitmap: Bitmap? = null

    fun setFaces(faces: List<Face>) {
        this.faces = faces
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
            // Scale the bitmap to fit the view
            canvas.drawBitmap(it, null, Rect(0, 0, width, height), null)
        }

        // Calculate the scale factors for drawing
        val scaleX = width.toFloat() / (bitmap?.width ?: width)
        val scaleY = height.toFloat() / (bitmap?.height ?: height)

        // Draw each face's bounding box and landmarks
        for (face in faces) {
            // Scale the bounding box
            val bounds = face.boundingBox
            val left = bounds.left * scaleX
            val top = bounds.top * scaleY
            val right = bounds.right * scaleX
            val bottom = bounds.bottom * scaleY
            val scaledBounds = Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())

            // Draw scaled bounding box
            canvas.drawRect(scaledBounds, paint)

            // Draw scaled landmarks (if available)
            drawLandmark(canvas, face.getLandmark(FaceLandmark.LEFT_EYE), scaleX, scaleY)
            drawLandmark(canvas, face.getLandmark(FaceLandmark.RIGHT_EYE), scaleX, scaleY)
            drawLandmark(canvas, face.getLandmark(FaceLandmark.NOSE_BASE), scaleX, scaleY)
            drawLandmark(canvas, face.getLandmark(FaceLandmark.MOUTH_LEFT), scaleX, scaleY)
            drawLandmark(canvas, face.getLandmark(FaceLandmark.MOUTH_RIGHT), scaleX, scaleY)
        }
    }

    private fun drawLandmark(canvas: Canvas, landmark: FaceLandmark?, scaleX: Float, scaleY: Float) {
        landmark?.position?.let { position ->
            val cx = position.x * scaleX
            val cy = position.y * scaleY
            canvas.drawCircle(cx, cy, 10f, landmarkPaint)
        }
    }
}