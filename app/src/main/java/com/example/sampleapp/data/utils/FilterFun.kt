package com.example.sampleapp.data.utils

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.pow

suspend fun applyCustomFilter(oldBitmap: Bitmap, type: String): Bitmap =
    withContext(Dispatchers.IO) {
        val newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val imageHeight = newBitmap.height
        val imageWidth = newBitmap.width
        Log.e("Image Size", "Height=$imageHeight Width=$imageWidth")
        for (i in 0 until imageWidth) {
            for (j in 0 until imageHeight) {
                val oldPixel = oldBitmap.getPixel(i, j)
                val oldRed = Color.red(oldPixel)
                val oldGreen = Color.green(oldPixel)
                val oldBlue = Color.blue(oldPixel)
                val oldAlpha = Color.alpha(oldPixel)
                val newPixel = when (type) {
                    "grey" -> {
                        //GREY FILTER
                        val intensity = (oldRed + oldBlue + oldGreen) / 3
                        val newRed = intensity
                        val newBlue = intensity
                        val newGreen = intensity
                        Color.argb(oldAlpha, newRed, newGreen, newBlue)
                    }

                    "sepia" -> {
                        //SEPIA FILTER
                        var newRed = (0.393 * oldRed + 0.769 * oldGreen + 0.189 * oldBlue).toInt()
                        var newGreen = (0.349 * oldRed + 0.686 * oldGreen + 0.168 * oldBlue).toInt()
                        var newBlue = (0.272 * oldRed + 0.534 * oldGreen + 0.131 * oldBlue).toInt()

                        newRed = if (newRed > 255) 255 else newRed
                        newGreen = if (newGreen > 255) 255 else newGreen
                        newBlue = if (newBlue > 255) 255 else newBlue

                        if (i < j - imageHeight / 2) {
                            Color.argb(oldAlpha, newRed, newGreen, newBlue)
                        } else if ((i - (imageHeight / 2)) > j) {
                            Color.argb(oldAlpha, newRed, newGreen, newBlue)
                        } else {
                            oldPixel
                        }
                    }

                    "negative" -> {
                        // Negative Filter
                        val newRed = 255 - oldRed
                        val newGreen = 255 - oldGreen
                        val newBlue = 255 - oldBlue
                        Color.argb(oldAlpha, newRed, newGreen, newBlue)
                    }

                    "sepia2" -> {
                        // Sepia Filter
                        val newRed = (0.393 * oldRed + 0.769 * oldGreen + 0.189 * oldBlue).toInt()
                            .coerceIn(0, 255)
                        val newGreen = (0.349 * oldRed + 0.686 * oldGreen + 0.168 * oldBlue).toInt()
                            .coerceIn(0, 255)
                        val newBlue = (0.272 * oldRed + 0.534 * oldGreen + 0.131 * oldBlue).toInt()
                            .coerceIn(0, 255)
                        Color.argb(oldAlpha, newRed, newGreen, newBlue)
                    }

                    "binary" -> {
                        // Binary (Threshold) Filter
                        val threshold = 128
                        val gray = (oldRed + oldGreen + oldBlue) / 3
                        if (gray > threshold) {
                            Color.argb(oldAlpha, 255, 255, 255) // White
                        } else {
                            Color.argb(oldAlpha, 0, 0, 0) // Black
                        }
                    }

                    "brightness" -> {
                        // Brightness Adjustment Filter
                        val brightness = 50 // Increase brightness by 50
                        val newRed = (oldRed + brightness).coerceIn(0, 255)
                        val newGreen = (oldGreen + brightness).coerceIn(0, 255)
                        val newBlue = (oldBlue + brightness).coerceIn(0, 255)
                        Color.argb(oldAlpha, newRed, newGreen, newBlue)
                    }

                    "saturation" -> {
                        // Saturation Adjustment Filter
                        val saturationFactor = 1.5f // Increase saturation by 50%
                        val gray = (oldRed + oldGreen + oldBlue) / 3
                        val newRed =
                            (gray + (oldRed - gray) * saturationFactor).toInt().coerceIn(0, 255)
                        val newGreen =
                            (gray + (oldGreen - gray) * saturationFactor).toInt().coerceIn(0, 255)
                        val newBlue =
                            (gray + (oldBlue - gray) * saturationFactor).toInt().coerceIn(0, 255)
                        Color.argb(oldAlpha, newRed, newGreen, newBlue)
                    }

                    "contrast" -> {
                        // Contrast Adjustment Filter
                        val contrastFactor = 1.5f // Increase contrast by 50%
                        val newRed =
                            (((oldRed - 128) * contrastFactor) + 128).toInt().coerceIn(0, 255)
                        val newGreen =
                            (((oldGreen - 128) * contrastFactor) + 128).toInt().coerceIn(0, 255)
                        val newBlue =
                            (((oldBlue - 128) * contrastFactor) + 128).toInt().coerceIn(0, 255)
                        Color.argb(oldAlpha, newRed, newGreen, newBlue)
                    }

                    "posterize" -> {
                        // Posterize Filter
                        val levels = 4 // Number of color levels
                        val newRed = (oldRed / levels) * levels
                        val newGreen = (oldGreen / levels) * levels
                        val newBlue = (oldBlue / levels) * levels
                        Color.argb(oldAlpha, newRed, newGreen, newBlue)
                    }

                    "red" -> {
                        // Red Tint Filter
                        val redTint = 100
                        val newRed = (oldRed + redTint).coerceIn(0, 255)
                        Color.argb(oldAlpha, newRed, oldGreen, oldBlue)
                    }

                    "green" -> {
                        // Green Tint Filter
                        val greenTint = 100
                        val newGreen = (oldGreen + greenTint).coerceIn(0, 255)
                        Color.argb(oldAlpha, oldRed, newGreen, oldBlue)
                    }

                    "blue" -> {
                        // Blue Tint Filter
                        val blueTint = 100
                        val newBlue = (oldBlue + blueTint).coerceIn(0, 255)
                        Color.argb(oldAlpha, oldRed, oldGreen, newBlue)
                    }

                    "emboss" -> {
                        // Emboss Filter
                        val embossFactor = 1.5f
                        val newRed = ((oldRed - Color.red(
                            oldBitmap.getPixel(
                                (i + 1).coerceAtMost(imageWidth - 1),
                                (j + 1).coerceAtMost(imageHeight - 1)
                            )
                        )) * embossFactor).toInt().coerceIn(0, 255)
                        val newGreen = ((oldGreen - Color.green(
                            oldBitmap.getPixel(
                                (i + 1).coerceAtMost(imageWidth - 1),
                                (j + 1).coerceAtMost(imageHeight - 1)
                            )
                        )) * embossFactor).toInt().coerceIn(0, 255)
                        val newBlue = ((oldBlue - Color.blue(
                            oldBitmap.getPixel(
                                (i + 1).coerceAtMost(imageWidth - 1),
                                (j + 1).coerceAtMost(imageHeight - 1)
                            )
                        )) * embossFactor).toInt().coerceIn(0, 255)
                        Color.argb(oldAlpha, newRed, newGreen, newBlue)
                    }

                    "sharpen" -> {
                        // Sharpen Filter
                        val sharpness = 3.0f
                        val kernel = arrayOf(
                            floatArrayOf(-1F, -1F, -1F),
                            floatArrayOf(-1F, sharpness, -1F),
                            floatArrayOf(-1F, -1F, -1F)
                        )
                        val newRed = ((oldRed * kernel[1][1]) - (Color.red(
                            oldBitmap.getPixel(
                                (i + 1).coerceAtMost(imageWidth - 1), j
                            )
                        ) * kernel[0][1]) - (Color.red(
                            oldBitmap.getPixel(
                                i,
                                (j + 1).coerceAtMost(imageHeight - 1)
                            )
                        ) * kernel[1][0])).toInt().coerceIn(0, 255)
                        val newGreen = ((oldGreen * kernel[1][1]) - (Color.green(
                            oldBitmap.getPixel(
                                (i + 1).coerceAtMost(imageWidth - 1), j
                            )
                        ) * kernel[0][1]) - (Color.green(
                            oldBitmap.getPixel(
                                i,
                                (j + 1).coerceAtMost(imageHeight - 1)
                            )
                        ) * kernel[1][0])).toInt().coerceIn(0, 255)
                        val newBlue = ((oldBlue * kernel[1][1]) - (Color.blue(
                            oldBitmap.getPixel(
                                (i + 1).coerceAtMost(imageWidth - 1), j
                            )
                        ) * kernel[0][1]) - (Color.blue(
                            oldBitmap.getPixel(
                                i,
                                (j + 1).coerceAtMost(imageHeight - 1)
                            )
                        ) * kernel[1][0])).toInt().coerceIn(0, 255)
                        Color.argb(oldAlpha, newRed, newGreen, newBlue)
                    }

                    "gamma" -> {
                        // Gamma Correction Filter
                        val gamma = 1.8
                        val gammaCorrection = 255.0 / 255.0.pow(gamma)
                        val newRed =
                            (gammaCorrection * oldRed.toDouble().pow(gamma)).toInt()
                                .coerceIn(0, 255)
                        val newGreen =
                            (gammaCorrection * oldGreen.toDouble().pow(gamma)).toInt()
                                .coerceIn(0, 255)
                        val newBlue =
                            (gammaCorrection * oldBlue.toDouble().pow(gamma)).toInt()
                                .coerceIn(0, 255)
                        Color.argb(oldAlpha, newRed, newGreen, newBlue)
                    }

                    "vignette" -> {
                        // Vignette Filter
                        val radius =
                            Math.sqrt((imageWidth * imageWidth + imageHeight * imageHeight).toDouble()) / 2
                        val strength = 150.0
                        val distance =
                            Math.sqrt((i - imageWidth / 2) * (i - imageWidth / 2) + (j - imageHeight / 2) * (j - imageHeight / 2).toDouble())
                        val darkeningFactor = (strength * distance / radius).coerceIn(0.0, 255.0)
                        val newRed = (oldRed - darkeningFactor).toInt().coerceIn(0, 255)
                        val newGreen = (oldGreen - darkeningFactor).toInt().coerceIn(0, 255)
                        val newBlue = (oldBlue - darkeningFactor).toInt().coerceIn(0, 255)
                        Color.argb(oldAlpha, newRed, newGreen, newBlue)
                    }

                    else -> {
                        //GREY FILTER
                        val intensity = (oldRed + oldBlue + oldGreen) / 3
                        val newRed = intensity
                        val newBlue = intensity
                        val newGreen = intensity
                        Color.argb(oldAlpha, newRed, newGreen, newBlue)
                    }
                }
                newBitmap.setPixel(i, j, newPixel)
            }
        }
        newBitmap
    }

suspend fun removeBackground(bitmap: Bitmap, threshold: Int = 150): Bitmap =
    withContext(Dispatchers.IO) {
        val width = bitmap.width
        val height = bitmap.height
        val newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (i in 0 until width) {
            for (j in 0 until height) {
                val pixel = bitmap.getPixel(i, j)
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)

                if (r > threshold && g > threshold && b > threshold) {
                    newBitmap.setPixel(i, j, Color.TRANSPARENT)
                } else {
                    newBitmap.setPixel(i, j, pixel)
                }
            }
        }
        newBitmap
    }
