package ca.sheridancollege.diyhalloween

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import ca.sheridancollege.diyhalloween.models.Costume

class ListAdapter(private var activity: MainActivity, private var costumes: ArrayList<Costume>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var name: TextView? = null
        var image: ImageView? = null

        init {
            this.name = row?.findViewById(R.id.costumeName)
            this.image = row?.findViewById(R.id.costumeImage)
        }
    }

    override fun getCount(): Int {
        return costumes.size
    }

    override fun getItem(position: Int): Any {
        return costumes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if(convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_item_costume, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = ViewHolder(view) as ViewHolder
        }

        var costume = costumes[position]

        viewHolder.name?.text = costume.name
        viewHolder.image?.setImageURI(Uri.parse(costume.imageURL))

        return view as View
    }
}