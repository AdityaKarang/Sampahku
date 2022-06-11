package com.adityakarang.sampahku.view.role.user.activities.scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.adityakarang.sampahku.databinding.ActivityScanSampahBinding
import com.adityakarang.sampahku.ml.MyModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.schema.Model
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.lang.reflect.Array.newInstance
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ScanSampahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanSampahBinding
    var imageSize = 224

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanSampahBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button.setOnClickListener(View.OnClickListener {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 3)
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        })

    }

    fun classifyImage(image: Bitmap?) {
        try {
            val model: MyModel = MyModel.newInstance(applicationContext)

            // Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())
            val intValues = IntArray(imageSize * imageSize)
            image!!.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
            var pixel = 0
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val `val` = intValues[pixel++] // RGB
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
                }
            }
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs: MyModel.Outputs = model.process(inputFeature0)
            val outputFeature0: TensorBuffer = outputs.getOutputFeature0AsTensorBuffer()
            val confidences = outputFeature0.floatArray
            // find the index of the class with the biggest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf(
                "Cardboard", "Compost", "Glass", "Metal",
                "Paper", "Plastic"
            )
            binding.result.setText(classes[maxPos])

            // Releases model resources if no longer used.
            model.close()
        } catch (e: IOException) {
            // TODO Handle the exception
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 3) {
                var image = data!!.extras!!["data"] as Bitmap?
                val dimension = Math.min(image!!.width, image.height)
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
                binding.imageView.setImageBitmap(image)
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
                classifyImage(image)
            } else {
                val dat = data!!.data
                var image: Bitmap? = null
                try {
                    image = MediaStore.Images.Media.getBitmap(this.contentResolver, dat)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                binding.imageView.setImageBitmap(image)
                image = Bitmap.createScaledBitmap(image!!, imageSize, imageSize, false)
                classifyImage(image)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}


//    companion object {
//        const val CAMERA_X_RESULT = 200
//    }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityScanSampahBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        this.supportActionBar
//
////        binding.button.setOnClickListener(View.OnClickListener {
////            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
////                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
////                startActivityForResult(cameraIntent, 3)
////            } else {
////                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
////            }
////        })
//
//        startCameraX()
//
//    }
//
//    private fun startCameraX() {
//        binding.button.setOnClickListener {
//            val intent = Intent(this, CameraActivity::class.java)
//            launcherIntentCameraX.launch(intent)
//        }
//    }
//
//
//    private val launcherIntentCameraX = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) {
//        if (it.resultCode == CAMERA_X_RESULT) {
//            val myFile = it.data?.getSerializableExtra("picture") as File
//            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
//            val result = rotateBitmap(
//                BitmapFactory.decodeFile(myFile.path),
//                isBackCamera
//            )
//
//            binding.imageView.setImageBitmap(result)
//        }
//    }
//
//    fun classifyImage(image: Bitmap) {
//        try {
//            val model: Model = Model.newInstance(applicationContext)
//
//            // Creates inputs for reference.
//            val inputFeature0 =
//                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
//            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
//            byteBuffer.order(ByteOrder.nativeOrder())
//            val intValues = IntArray(imageSize * imageSize)
//            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
//            var pixel = 0
//            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
//            for (i in 0 until imageSize) {
//                for (j in 0 until imageSize) {
//                    val `val` = intValues[pixel++] // RGB
//                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
//                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
//                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
//                }
//            }
//            inputFeature0.loadBuffer(byteBuffer)
//
//            // Runs model inference and gets result.
//            val outputs = model.process(inputFeature0)
//            val outputFeature0: TensorBuffer = outputs.outputFeature0AsTensorBuffer
//            val confidences = outputFeature0.floatArray
//            // find the index of the class with the biggest confidence.
//            var maxPos = 0
//            var maxConfidence = 0f
//            for (i in confidences.indices) {
//                if (confidences[i] > maxConfidence) {
//                    maxConfidence = confidences[i]
//                    maxPos = i
//                }
//            }
//            val classes = arrayOf("cardboard", "compost", "glass", "metal",
//                "paper", "plastic")
//            binding.result.text = classes[maxPos]
//
//            // Releases model resources if no longer used.
//            model.close()
//        } catch (e: IOException) {
//            // TODO Handle the exception
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == 10) {
//                var image = data!!.extras!!["data"] as Bitmap?
//                val dimension = Math.min(image!!.width, image.height)
//                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
//                binding.imageView.setImageBitmap(image)
//                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
//                classifyImage(image)
//            } else {
//                val dat = data!!.data
//                var image: Bitmap? = null
//                try {
//                    image = MediaStore.Images.Media.getBitmap(this.contentResolver, dat)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//                binding.imageView.setImageBitmap(image)
//                image = Bitmap.createScaledBitmap(image!!, imageSize, imageSize, false)
//                classifyImage(image)
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }
//}

