package ca.sheridancollege.diyhalloween.models

class Costume {

    var name: String = ""
    var imageURL: String = ""
    var steps: String = ""
    var materials: ArrayList<String> = ArrayList()

    constructor(name: String, imageURL: String, steps: String, materials: ArrayList<String>) {
        this.name = name
        this.imageURL = imageURL
        this.steps = steps
        this.materials = materials
    }
}