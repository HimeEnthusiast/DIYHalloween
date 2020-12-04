package ca.sheridancollege.diyhalloween

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class CostumeViewActivity : AppCompatActivity() {

    private lateinit var costumeNameView: MaterialTextView
    private lateinit var costumeMaterialsView: MaterialTextView
    private lateinit var costumeStepsView: MaterialTextView
    private lateinit var costumeImageView: ShapeableImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_costume_view)

        costumeNameView = findViewById(R.id.costumeNameView)
        costumeMaterialsView = findViewById(R.id.costumeMaterialsView)
        costumeStepsView = findViewById(R.id.costumeStepsView)
        costumeImageView = findViewById(R.id.costumeImageView)

        val bundle : Bundle ?= intent.extras
        val id = bundle!!.getString("itemId")?.toInt()

        if (id != null) {
            loadCostume(id + 1)
        }
    }

    private fun loadCostume(id: Int) {
        val databaseHandler = DatebaseHandler(this)
        val costume = databaseHandler.viewCostumeById(id)
        var materialsList = ""
        var count = 1
        costume.materials.forEach {
            materialsList += "$count. $it\n"
            count++
        }

        costumeNameView.text = costume.name
        costumeMaterialsView.text = materialsList
        costumeStepsView.text = costume.steps
        costumeImageView.setImageResource(R.drawable.bilingual)
    }
}