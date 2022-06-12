package com.adityakarang.sampahku.view.role.user.activities.editprofile

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.adityakarang.sampahku.R
import com.adityakarang.sampahku.databinding.ActivityEditProfileBinding
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth
    private var imageUri: Uri?= null
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Mohon Tunggu")
        progressDialog.setCanceledOnTouchOutside(false)

        auth = FirebaseAuth.getInstance()
        loadUser()

        binding.tvProfile.setOnClickListener {
            showImageAttachMenu()
        }

        binding.updatebtn.setOnClickListener {
            validateData()
        }
    }


    private var name = ""
    private fun validateData() {
        name = binding.nameET.text.toString().trim()
        if (name.isEmpty()) {
            Toast.makeText(this, "Masukan Nama", Toast.LENGTH_SHORT).show()
        }
        else{
            if (imageUri == null){
                updateProfile("")
            }
            else{
                upImage()
            }
        }
    }

    private fun upImage() {
        progressDialog.setMessage("Upload image profile")
        progressDialog.show()

        val filePathAndName = "ImgProfile/" +auth.uid

        val ref = FirebaseStorage.getInstance().getReference(filePathAndName)
        ref.putFile(imageUri!!)
            .addOnSuccessListener { taskSnapshot ->
                val uriTask: Task<Uri> = taskSnapshot.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val uploadedImageUrl = "${uriTask.result}"

                updateProfile(uploadedImageUrl)
            }
            .addOnFailureListener{e->
                Toast.makeText(this, "failed upload image due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateProfile(uploadedImageUrl: String) {
        progressDialog.setMessage("update profile...")

        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["name"] = "$name"
        if (imageUri != null){
            hashMap["imgProfil"] = uploadedImageUrl
        }

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(auth.uid!!)
            .updateChildren(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener{e->
                Toast.makeText(this, "failed update profile due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUser() {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(auth.uid!!)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val email = "${snapshot.child("email").value}"
                    val name = "${snapshot.child("name").value}"
                    val imgProfile = "${snapshot.child("imgProfil").value}"
                    val timestamp = "${snapshot.child("timestamp").value}"
                    val uid = "${snapshot.child("uid").value}"
                    val userType = "${snapshot.child("userType").value}"

                    binding.nameET.setText(name)


                    try {
                        Glide.with(this@EditProfileActivity).load(imgProfile).placeholder(R.drawable.ic_person).into(binding!!.tvProfile)
                    }
                    catch (e: Exception){

                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun showImageAttachMenu(){
        val popupMenu = PopupMenu(this, binding.tvProfile)
        popupMenu.menu.add(Menu.NONE, 0, 0, "Camera")
        popupMenu.menu.add(Menu.NONE, 1, 1, "Gallery")
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener { item->
            val id = item.itemId
            if (id == 0){
                imageCamera()
            }
            else if (id == 1){
                imageGalery()
            }
            true
        }
    }

    private fun imageCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Temp_Title")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Description")


        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        cameraActivityResultLauncher.launch(intent)
    }

    private fun imageGalery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galeryActivityResultLauncher.launch(intent)
    }

    private val cameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult>{result->
            if (result.resultCode == Activity.RESULT_OK){
                val data = result.data
//                imageUri = data!!.data

                binding.tvProfile.setImageURI(imageUri)
            }
            else{
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    )


    private val galeryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback { result->
            if (result.resultCode == Activity.RESULT_OK){
                val data = result.data
                imageUri = data!!.data

                binding.tvProfile.setImageURI(imageUri)
            }
            else{
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    )
}