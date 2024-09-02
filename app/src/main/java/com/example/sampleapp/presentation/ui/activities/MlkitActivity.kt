package com.example.sampleapp.presentation.ui.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.sampleapp.data.mapper.toast
import com.example.sampleapp.databinding.ActivityMlkitBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import com.google.mlkit.vision.facemesh.FaceMeshDetection
import com.google.mlkit.vision.facemesh.FaceMeshDetectorOptions
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.InputStream

class MlkitActivity : AppCompatActivity() {
    lateinit var binding: ActivityMlkitBinding
    lateinit var recognizer: TextRecognizer
    var type = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMlkitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initContent()
        initClicks()
    }

    private fun initContent() {
        binding.apply {
            recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        }
    }

    private fun initClicks() {
        binding.apply {
            extract.setOnClickListener {
                type = 1
                checkPermission()
            }
            face.setOnClickListener {
                type = 2
                checkPermission()
            }
            facemesh.setOnClickListener {
                type = 3
                checkPermission()
            }
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                checkSelfPermission(android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED -> {
                    pickImageFromGallery()
                }

                else -> {
                    requestPermissions(arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES), 100)
                }
            }
        } else {
            when {
                checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                    pickImageFromGallery()
                }

                else -> {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        200
                    )
                }
            }
        }
    }

    private fun pickImageFromGallery() {
        binding.apply {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 300)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.data?.let { uri ->

            if (type == 1) {
                binding.image.setImageURI(uri)
                binding.image.isVisible = true
                binding.faceimage.isVisible = false
                binding.facemeshimage.isVisible = false
                val image = InputImage.fromFilePath(this@MlkitActivity, uri)
                val result = recognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        toast("Done")
                        binding.text.isVisible = true
                        binding.text.setText(visionText.text)
                    }
                    .addOnFailureListener { e ->
                        toast("Failed:${e.message}")
                    }
            } else if (type == 2) {
                val inputStream: InputStream? = contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)

                // Set the bitmap to the FaceOverlayView
                binding.faceimage.setBitmap(bitmap)
                binding.image.isVisible = false
                binding.faceimage.isVisible = true
                binding.facemeshimage.isVisible = false
                var faceText = ""
                val highAccuracyOpts = FaceDetectorOptions.Builder()
                    .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                    .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                    .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                    .build()
                val detector = FaceDetection.getClient(highAccuracyOpts)
                val image = InputImage.fromFilePath(this@MlkitActivity, uri)
                val result = detector.process(image)
                    .addOnSuccessListener { faces ->
                        toast("Done")
                        for (face in faces) {
                            val bounds = face.boundingBox
                            val leftEye = face.getLandmark(FaceLandmark.LEFT_EYE)
                            val rightEye = face.getLandmark(FaceLandmark.RIGHT_EYE)
                            val nose = face.getLandmark(FaceLandmark.NOSE_BASE)
                            val leftCheek = face.getLandmark(FaceLandmark.LEFT_CHEEK)
                            val rightCheek = face.getLandmark(FaceLandmark.RIGHT_CHEEK)

                            // You can get the position of each landmark if it's not null
                            leftEye?.let {
                                val leftEyePosition = it.position
                                faceText += "Left Eye Position: $leftEyePosition"
                                // Do something with the left eye position
                            }

                            rightEye?.let {
                                val rightEyePosition = it.position
                                faceText += "\nRight Eye Position: $rightEyePosition"
                                // Do something with the right eye position
                            }

                            // Get classification probabilities (if classification mode is enabled)
                            if (face.smilingProbability != null) {
                                val smileProb = face.smilingProbability
                                // Do something with smile probability (e.g., check if the face is smiling)
                                if (smileProb != null) {
                                    if (smileProb > 0.5) {
                                        faceText += "\nSmiling Prob: Smiling at $smileProb"
                                    } else {
                                        faceText += "\nSmiling Prob: Normal at $smileProb"
                                    }
                                }
                            }

                            if (face.leftEyeOpenProbability != null && face.rightEyeOpenProbability != null) {
                                val leftEyeOpenProb = face.leftEyeOpenProbability
                                val rightEyeOpenProb = face.rightEyeOpenProbability
                                // Check if eyes are open or closed
                                if (leftEyeOpenProb != null) {
                                    if (rightEyeOpenProb != null) {
                                        if (leftEyeOpenProb > 0.5 && rightEyeOpenProb > 0.5) {
                                            faceText += "\nEyes Open: Yes Left at $leftEyeOpenProb, Right at $rightEyeOpenProb"
                                        } else {
                                            faceText += "\nEyes Open: No Left at $leftEyeOpenProb, Right at $rightEyeOpenProb"
                                        }
                                    }
                                }
                            }
                            binding.text.setText(faceText)

                            // Draw the bounding box or landmarks on a canvas (optional)
                            // This could be done by overlaying a custom view on your activity
                            binding.faceimage.setFaces(faces)
                        }
                    }
                    .addOnFailureListener { e ->
                        toast("Failed:${e.message}")
                    }
            } else if (type == 3) {
                val inputStream: InputStream? = contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                binding.facemeshimage.setBitmap(bitmap)
                binding.image.isVisible = false
                binding.faceimage.isVisible = false
                binding.facemeshimage.isVisible = true

                val boundingBoxDetector = FaceMeshDetection.getClient(
                    FaceMeshDetectorOptions.Builder()
                        .setUseCase(FaceMeshDetectorOptions.FACE_MESH)
                        .build()
                )
                val image = InputImage.fromFilePath(this@MlkitActivity, uri)
                val result = boundingBoxDetector.process(image)
                    .addOnSuccessListener { faceMeshes ->
                        toast("Done")
                        binding.facemeshimage.setFaceMeshes(faceMeshes)
                    }
                    .addOnFailureListener { e ->
                        toast("Failed:${e.message}")
                    }


            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        pickImageFromGallery()
    }
}