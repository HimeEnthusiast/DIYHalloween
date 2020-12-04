package ca.sheridancollege.diyhalloween

import android.app.Activity
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.io.ByteArrayInputStream
import android.graphics.BitmapFactory

class ListAdapter(private var context: Activity,
                  private val name: Array<String>,
                  private val imageUrl: Array<String>
)
    : ArrayAdapter<String>(context, R.layout.list_item_costume, name) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.list_item_costume, null, true)

        val nameText = rowView.findViewById(R.id.costumeName) as TextView
        val imageView = rowView.findViewById(R.id.costumeImage) as ImageView

        nameText.text = name[position]
//        imageView.setImageURI(Uri.parse(imageUrl[position]))
        imageView.setImageResource(R.drawable.bilingual)

        return rowView
    }
}