package com.adityakarang.sampahku.view.role.user.activities.scan

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.adityakarang.sampahku.databinding.ActivityScanSampahBinding
import com.adityakarang.sampahku.utils.rotateBitmap
import java.io.File

class ScanSampahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanSampahBinding

    companion object {
        const val CAMERA_X_RESULT = 200
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanSampahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar

        startCameraX()

    }

    private fun startCameraX() {
        binding.button.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            launcherIntentCameraX.launch(intent)
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            binding.imageView.setImageBitmap(result)
        }
    }
}