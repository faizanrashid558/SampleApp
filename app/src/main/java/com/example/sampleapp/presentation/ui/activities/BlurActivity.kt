package com.example.sampleapp.presentation.ui.activities

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.MotionEvent
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.sampleapp.R
import com.example.sampleapp.data.utils.BlurTransformation
import com.example.sampleapp.data.utils.applyCustomFilter
import com.example.sampleapp.databinding.ActivityBlurBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


class BlurActivity : AppCompatActivity() {
    lateinit var binding: ActivityBlurBinding
    private lateinit var originalBitmap: Bitmap
    private lateinit var originalBitmap2: Bitmap
    private var image1X = 0f
    private var image1Y = 0f
    private var image2X = 0f
    private var image2Y = 0f
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var draggingImage = 0
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlurBinding.inflate(layoutInflater)
        setContentView(binding.root)
        originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)
        originalBitmap2 = BitmapFactory.decodeResource(resources, R.drawable.sample2)
        binding.btnSave.setOnClickListener {
            val blurredBitmap = (binding.imgBlur.drawable as BitmapDrawable).bitmap
            val savedFile = saveImage(blurredBitmap)
            if (savedFile != null) {
                binding.imgBlur.setImageDrawable(null)

                Glide.with(this)
                    .load(savedFile.absolutePath)
                    .into(binding.imgBlur)
            }
        }
        binding.sbBlur.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                var progress = p0?.progress
                progress?.toFloat()?.let { blurImage(it) }
            }

        })

        binding.btnFilterGrey.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val oldBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)
                val filteredBitmap = applyCustomFilter(oldBitmap, "grey")
                binding.imgBlur.setImageBitmap(filteredBitmap)
            }
        }
        binding.btnFilterSepia.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val oldBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)
                val filteredBitmap = applyCustomFilter(oldBitmap, "sepia")
                binding.imgBlur.setImageBitmap(filteredBitmap)
            }
        }
        binding.btnFilterNegative.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val oldBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)
                val filteredBitmap = applyCustomFilter(
                    oldBitmap,
                    "negative"
                )
                binding.imgBlur.setImageBitmap(filteredBitmap)
            }
        }
        binding.btnFilterSepia2.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val oldBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)
                val filteredBitmap = applyCustomFilter(oldBitmap, "sepia2")
                binding.imgBlur.setImageBitmap(filteredBitmap)
            }
        }
        binding.btnFilterBinary.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val oldBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)
                val filteredBitmap = applyCustomFilter(oldBitmap, "binary")
                binding.imgBlur.setImageBitmap(filteredBitmap)
            }
        }
        binding.btnFilterBrightness.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val oldBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)
                val filteredBitmap = applyCustomFilter(oldBitmap, "brightness")
                binding.imgBlur.setImageBitmap(filteredBitmap)
            }
        }
        binding.btnFilterSaturation.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val oldBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)
                val filteredBitmap = applyCustomFilter(oldBitmap, "saturation")
                binding.imgBlur.setImageBitmap(filteredBitmap)
            }
        }
        binding.btnFilterContrast.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val oldBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)
                val filteredBitmap = applyCustomFilter(oldBitmap, "contrast")
                binding.imgBlur.setImageBitmap(filteredBitmap)
            }
        }
        binding.btnFilterPosterize.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val oldBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)
                val filteredBitmap = applyCustomFilter(oldBitmap, "posterize")
                binding.imgBlur.setImageBitmap(filteredBitmap)
            }
        }
        binding.btnFilterRed.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val oldBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)
                val filteredBitmap = applyCustomFilter(oldBitmap, "red")
                binding.imgBlur.setImageBitmap(filteredBitmap)
            }
        }
        binding.btnFilterGreen.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val oldBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)
                val filteredBitmap = applyCustomFilter(oldBitmap, "green")
                binding.imgBlur.setImageBitmap(filteredBitmap)
            }
        }
        binding.btnFilterBlue.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val oldBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)
                val filteredBitmap = applyCustomFilter(oldBitmap, "blue")
                binding.imgBlur.setImageBitmap(filteredBitmap)
            }
        }
        binding.btnFilterEmboss.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val oldBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)
                val filteredBitmap = applyCustomFilter(oldBitmap, "emboss")
                binding.imgBlur.setImageBitmap(filteredBitmap)
            }
        }
        binding.btnFilterSharpen.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val oldBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)
                val filteredBitmap = applyCustomFilter(oldBitmap, "sharpen")
                binding.imgBlur.setImageBitmap(filteredBitmap)
            }
        }
        binding.btnFilterGamma.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val oldBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)
                val filteredBitmap = applyCustomFilter(oldBitmap, "gamma")
                binding.imgBlur.setImageBitmap(filteredBitmap)
            }
        }
        binding.btnFilterVignette.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val oldBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)
                val filteredBitmap = applyCustomFilter(oldBitmap, "vignette")
                binding.imgBlur.setImageBitmap(filteredBitmap)
            }
        }

        binding.btnCollege.setOnClickListener {
//            val collageBitmap = createCollage1(originalBitmap, originalBitmap2)
            val collageBitmap = createDiagonalCollage(originalBitmap, originalBitmap2,binding.imgBlur.width,binding.imgBlur.height)
            binding.imgBlur.setImageBitmap(collageBitmap)
        }
        binding.imgBlur.setOnTouchListener { v, event ->
            handleTouch(event)
            true
        }

    }

    private fun handleTouch(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Determine which image is being dragged
                if (event.x > binding.imgBlur.width / 2) {
                    draggingImage = 2
                    lastTouchX = event.x - image2X
                    lastTouchY = event.y - image2Y
                } else {
                    draggingImage = 1
                    lastTouchX = event.x - image1X
                    lastTouchY = event.y - image1Y
                }
            }
            MotionEvent.ACTION_MOVE -> {
                // Update the position of the dragged image
                if (draggingImage == 1) {
                    image1X = event.x - lastTouchX
                    image1Y = event.y - lastTouchY
                } else if (draggingImage == 2) {
                    image2X = event.x - lastTouchX
                    image2Y = event.y - lastTouchY
                }
                // Update the collage
                updateCollage(binding.imgBlur.width, binding.imgBlur.height)
            }
            MotionEvent.ACTION_UP -> {
                // Reset dragging state
                draggingImage = 0
            }
        }
    }
    private fun updateCollage(targetWidth: Int, targetHeight: Int) {
        val collageBitmap = createDiagonalCollage(originalBitmap,originalBitmap2, targetWidth, targetHeight)
        binding.imgBlur.setImageBitmap(collageBitmap)
    }
    private fun createCollage1(bitmap1: Bitmap, bitmap2: Bitmap): Bitmap {
        // Scale both images to have the same height
        val targetHeight = maxOf(bitmap1.height, bitmap2.height)
        val scaledBitmap1 = scaleBitmapToHeight(bitmap1, targetHeight)
        val scaledBitmap2 = scaleBitmapToHeight(bitmap2, targetHeight)
        val collageWidth = scaledBitmap1.width + scaledBitmap2.width
        val collageHeight = targetHeight
        val collageBitmap = Bitmap.createBitmap(collageWidth, collageHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(collageBitmap)
        canvas.drawBitmap(scaledBitmap1, 0f, 0f, null)
        canvas.drawBitmap(scaledBitmap2, scaledBitmap1.width.toFloat(), 0f, null)
        return collageBitmap
    }

    private fun createDiagonalCollage(bitmap1: Bitmap, bitmap2: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
        // Scale both images to the size of the ImageView
        val scaledBitmap1 = Bitmap.createScaledBitmap(bitmap1, targetWidth, targetHeight, true)
        val scaledBitmap2 = Bitmap.createScaledBitmap(bitmap2, targetWidth, targetHeight, true)

        // Create a new Bitmap with the size of the ImageView
        val collageBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888)

        // Create a Canvas to draw on the new Bitmap
        val canvas = Canvas(collageBitmap)

        val path1 = Path()
        path1.moveTo(targetWidth.toFloat(), 0f)
        path1.lineTo(targetWidth.toFloat(), targetHeight.toFloat())
        path1.lineTo(0f, targetHeight.toFloat())
        path1.close()

        // Clip the canvas and draw the first image
        canvas.save()
        canvas.clipPath(path1)
        canvas.drawBitmap(scaledBitmap1, 0f, 0f, null)
        canvas.restore()

        // Create a path for the second image (top-left to bottom-right)
        val path2 = Path()
        path2.moveTo(0f, 0f)
        path2.lineTo(targetWidth.toFloat(), 0f)
        path2.lineTo(0f, targetHeight.toFloat())
        path2.close()

        // Clip the canvas and draw the second image
        canvas.save()
        canvas.clipPath(path2)
        canvas.drawBitmap(scaledBitmap2, 0f, 0f, null)
        canvas.restore()

        // Draw the white diagonal line from top-right to bottom-left
        val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        linePaint.color = Color.WHITE
        linePaint.strokeWidth = 10f
        linePaint.style = Paint.Style.STROKE
        canvas.drawLine(targetWidth.toFloat(), 0f, 0f, targetHeight.toFloat(), linePaint)

        return collageBitmap
    }
    private fun scaleBitmapToHeight(bitmap: Bitmap, targetHeight: Int): Bitmap {
        val aspectRatio = bitmap.width.toFloat() / bitmap.height
        val targetWidth = (targetHeight * aspectRatio).toInt()
        return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
    }

    private fun saveImage(bitmap: Bitmap): File? {
        val filename = "blurred_image_${System.currentTimeMillis()}.png"
        val file = File(cacheDir, filename)

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            Toast.makeText(this, "Image saved:${file.path}", Toast.LENGTH_SHORT).show()
            return file
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to save image: ${e.message}", Toast.LENGTH_SHORT).show()
            return null
        } finally {
            fos?.close()
        }
    }

    private fun blurImage(percentage: Float) {
        // Copy the original bitmap to apply the blur effect
        val bitmapToBlur = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)

        // Apply the blur using the copied bitmap
        val blurredBitmap = BlurTransformation(this, percentage).transform(bitmapToBlur)

        // Display the blurred image
        binding.imgBlur.setImageBitmap(blurredBitmap)

        Toast.makeText(this, "Blur Done", Toast.LENGTH_SHORT).show()
    }
}