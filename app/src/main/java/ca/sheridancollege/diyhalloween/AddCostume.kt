package ca.sheridancollege.diyhalloween

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class AddCostume : AppCompatActivity() {

    lateinit var material0: TextInputEditText
    lateinit var material1: TextInputEditText
    lateinit var material2: TextInputEditText
    lateinit var material3: TextInputEditText
    lateinit var material4: TextInputEditText
    lateinit var photoPreview: ShapeableImageView
    lateinit var imageURILabel: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_costume)

        val choosePhotoButton = findViewById<MaterialButton>(R.id.uploadImageButton)
        val addMaterialButton = findViewById<MaterialButton>(R.id.addMaterialButton)
        material0 = findViewById<TextInputEditText>(R.id.materialNameInput0)
        material1 = findViewById<TextInputEditText>(R.id.materialNameInput1)
        material2 = findViewById<TextInputEditText>(R.id.materialNameInput2)
        material3 = findViewById<TextInputEditText>(R.id.materialNameInput3)
        material4 = findViewById<TextInputEditText>(R.id.materialNameInput4)
        photoPreview = findViewById<ShapeableImageView>(R.id.costumeImagePreview)
        imageURILabel = findViewById<MaterialTextView>(R.id.imageURLLabel)

        var button: Int = 1

        addMaterialButton.setOnClickListener {
            showButton(button)
            button++
        }

        choosePhotoButton.setOnClickListener {
            if (allPermissionsGranted()) {
                val options = arrayOf("Take Photo", "Choose from Gallery")

                MaterialAlertDialogBuilder(this)
                    .setTitle("Upload Image")
                    .setNeutralButton("Cancel") { dialog, _ ->
                        dialog.cancel()
                    }
                    .setItems(options) { _, which ->
                        when (which) {
                            0 -> {
                                // Go to Camera
                            }
                            1 -> {
                                openImageGallery()
                            }
                        }
                    }
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
            }
        }
    }

    private fun showButton(button: Int) {
        when(button) {
            1 -> material1.visibility = View.VISIBLE
            2 -> material2.visibility = View.VISIBLE
            3 -> material3.visibility = View.VISIBLE
            4 -> material4.visibility = View.VISIBLE
            else -> Toast.makeText(this, "Reached maximum", Toast.LENGTH_LONG).show()
        }
    }

    companion object { // Permissions
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val IMAGE_PICK_CODE = 11
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun openImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_PICK_CODE -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    photoPreview.setImageURI(data?.data)
                    imageURILabel.setText(data?.data.toString(), TextView.BufferType.NORMAL)
                }
            }
        }
    }
}