package ca.sheridancollege.diyhalloween.models

import java.io.ByteArrayOutputStream

class Costume {

    var name: String = ""
    var imageUrl: String = ""
    var steps: String = ""
    var materials: ArrayList<String> = ArrayList()

    constructor(name: String, imageUrl: String, steps: String, materials: ArrayList<String>) {
        this.name = name
        this.imageUrl = imageUrl
        this.steps = steps
        this.materials = materials
    }

    constructor() {}
}