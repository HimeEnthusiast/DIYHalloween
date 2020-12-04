package ca.sheridancollege.diyhalloween

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    //View Widgets
    private lateinit var costumeListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //View Widgets
        costumeListView = findViewById<ListView>(R.id.costumeListView)
        val fabButton = findViewById<FloatingActionButton>(R.id.fabAdd)

        loadCostumes()

        //Add Costume button
        fabButton.setOnClickListener {
            goAddCostume()
        }

        costumeListView.setOnItemClickListener { _, _, _, id ->
            val intent = Intent(this@MainActivity, CostumeViewActivity::class.java)
            intent.putExtra("itemId", id.toString())
            startActivity(intent)
        }
    }

    private fun loadCostumes() {
        val databaseHandler = DatebaseHandler(this)
        val costumeList = databaseHandler.viewAllCostumes()
        val costumeArrayName = Array<String>(costumeList.size){"null"}
        val costumeArrayImageUrl = Array<String>(costumeList.size){"null"}


        for ((index, c) in costumeList.withIndex()) {
            costumeArrayName[index] = c.name
            costumeArrayImageUrl[index] = c.imageUrl
        }

        val adapter = ListAdapter(this, costumeArrayName, costumeArrayImageUrl)
        costumeListView.adapter = adapter
    }

    private fun goAddCostume() {
        val intent = Intent(this@MainActivity, AddCostumeActivity::class.java)
        startActivity(intent)
    }
}