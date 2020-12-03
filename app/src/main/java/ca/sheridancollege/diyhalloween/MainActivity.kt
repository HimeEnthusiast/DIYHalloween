package ca.sheridancollege.diyhalloween

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import ca.sheridancollege.diyhalloween.models.Costume
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

        //Costumes
        var costumes: ArrayList<Costume> = ArrayList()
        costumes.add(Costume("Joker", "android.resource://" + this.packageName + "/" + R.drawable.bilingual, "Do some makeup", ArrayList()))

        loadCostumes(costumes)

        //Add Costume button
        fabButton.setOnClickListener {
            goAddCostume()
        }
    }

    /**
     * Loads all costumes into the list
     */
    private fun loadCostumes(array: ArrayList<Costume>) {
        val adapter = ListAdapter(this, array)
        costumeListView.adapter = adapter
    }

    private fun goAddCostume() {
        val intent = Intent(this@MainActivity, AddCostume::class.java)
        startActivity(intent)
    }
}