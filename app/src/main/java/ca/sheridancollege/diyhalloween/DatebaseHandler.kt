package ca.sheridancollege.diyhalloween

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import ca.sheridancollege.diyhalloween.models.Costume
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream

class DatebaseHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "CostumeDB"

        private const val TABLE_COSTUMES = "Costumes"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_STEPS = "steps"
        private const val KEY_MATERIALS = "materials"
        private const val KEY_IMAGE = "image_url"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createCostumesTable = ("CREATE TABLE " + TABLE_COSTUMES + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + " TEXT," +
                KEY_STEPS + " TEXT," +
                KEY_MATERIALS + " TEXT," +
                KEY_IMAGE + " TEXT" + ")")

        db?.execSQL(createCostumesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_COSTUMES")
        onCreate(db)
    }

    fun addCostume(costume: Costume): Long {
        val costumeMaterials = costume.materials
        val gson = Gson()
        val materialsJson: String = gson.toJson(costumeMaterials)

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, costume.name)
        contentValues.put(KEY_MATERIALS, materialsJson)
        contentValues.put(KEY_STEPS, costume.steps)
        contentValues.put(KEY_IMAGE, costume.imageUrl)

        val success = db.insert(TABLE_COSTUMES, null, contentValues)
        db.close()
        return success
    }

    fun viewCostumeById(id: Int): Costume {
        var costume = Costume()
        val selectQuery = "SELECT * FROM $TABLE_COSTUMES WHERE id=$id"
        val db = this.readableDatabase
        val gson = Gson()
        val turnsType = object: TypeToken<ArrayList<String>>() {}.type
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return costume
        }

        var costumeName: String
        var costumeMaterials: ArrayList<String>
        var costumeSteps: String
        var costumeImageUrl: String

        if (cursor.moveToFirst()) {
            costumeName = cursor.getString(cursor.getColumnIndex("name"))
            costumeMaterials = gson.fromJson(cursor.getString(cursor.getColumnIndex("materials")), turnsType)
            costumeSteps = cursor.getString(cursor.getColumnIndex("steps"))
            costumeImageUrl = cursor.getString(cursor.getColumnIndex("image_url"))
            costume = Costume(name = costumeName, imageUrl = costumeImageUrl, steps = costumeSteps, materials = costumeMaterials)
        } else {
            Log.d("Log:", "Ahhhhh")
        }

        return costume
    }

    fun viewAllCostumes(): List<Costume> {
        val costumeList: ArrayList<Costume> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_COSTUMES"
        val db = this.readableDatabase
        val gson = Gson()
        val turnsType = object: TypeToken<ArrayList<String>>() {}.type
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var costumeName: String
        var costumeMaterials: ArrayList<String>
        var costumeSteps: String
        var costumeImageUrl: String

        if (cursor.moveToFirst()) {
            do {
                costumeName = cursor.getString(cursor.getColumnIndex("name"))
                costumeMaterials = gson.fromJson(cursor.getString(cursor.getColumnIndex("materials")), turnsType)
                costumeSteps = cursor.getString(cursor.getColumnIndex("steps"))
                costumeImageUrl = cursor.getString(cursor.getColumnIndex("image_url"))
                val costume = Costume(name = costumeName, imageUrl = costumeImageUrl, steps = costumeSteps, materials = costumeMaterials)
                costumeList.add(costume)
            } while (cursor.moveToNext())
        }

        return costumeList
    }
}