package ca.sheridancollege.diyhalloween

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ca.sheridancollege.diyhalloween.models.Costume
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class AddCostumeActivity : AppCompatActivity() {

    private lateinit var material0: TextInputEditText
    private lateinit var material1: TextInputEditText
    private lateinit var material2: TextInputEditText
    private lateinit var material3: TextInputEditText
    private lateinit var material4: TextInputEditText
    private lateinit var photoPreview: ShapeableImageView
    private lateinit var imageURILabel: MaterialTextView
    private lateinit var costumeNameInput: TextInputEditText
    private lateinit var costumeStepsInput: TextInputEditText
    private var materialInputArray: ArrayList<TextInputEditText> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_costume)

        val choosePhotoButton = findViewById<MaterialButton>(R.id.uploadImageButton)
        val addMaterialButton = findViewById<MaterialButton>(R.id.addMaterialButton)
        val costumeSubmitButton = findViewById<MaterialButton>(R.id.costumeSubmitButton)

        material0 = findViewById<TextInputEditText>(R.id.materialNameInput0)
        material1 = findViewById<TextInputEditText>(R.id.materialNameInput1)
        material2 = findViewById<TextInputEditText>(R.id.materialNameInput2)
        material3 = findViewById<TextInputEditText>(R.id.materialNameInput3)
        material4 = findViewById<TextInputEditText>(R.id.materialNameInput4)

        photoPreview = findViewById<ShapeableImageView>(R.id.costumeImagePreview)
        imageURILabel = findViewById<MaterialTextView>(R.id.imageURLLabel)
        costumeNameInput = findViewById(R.id.costumeNameInput)
        costumeStepsInput = findViewById(R.id.costumeStepsInput)

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

        costumeSubmitButton.setOnClickListener {
            saveCostume()
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

    private fun saveCostume() {
        var materialArray: ArrayList<String> = ArrayList()
        materialInputArray.addAll(listOf(material0,
            material1,
            material2,
            material3,
            material4))

        for (i in materialInputArray) {
            if (i.text.toString().isNotEmpty()) {
                materialArray.add(i.text.toString())
            }
        }

        val name = costumeNameInput.text.toString()
        val materials = materialArray
        val steps = costumeStepsInput.text.toString()
        val imageUrl = imageURILabel.text.toString()
        val datebaseHandler = DatebaseHandler(this)

        if(name.trim() != "" && steps.trim() != "" && imageUrl.trim() != "" && !materials.isNullOrEmpty()) {
            val status = datebaseHandler.addCostume(Costume(name, imageUrl, steps, materials))
            if (status > -1) {
                Toast.makeText(applicationContext, "Costume saved successfully.", Toast.LENGTH_LONG).show()
                costumeNameInput.text?.clear()
                costumeStepsInput.text?.clear()
                imageURILabel.text = ""

                for (i in materialInputArray) {
                    i.text?.clear()
                }
            } else {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Error")
                    .setMessage(status.toString())
                    .setNeutralButton("Cancel") { dialog, _ ->
                        dialog.cancel()
                    }
                    .show()
            }
        } else {
            MaterialAlertDialogBuilder(this)
                .setTitle("Error")
                .setMessage("Please fill all fields.")
                .setNeutralButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }
                .show()
        }
    }


}