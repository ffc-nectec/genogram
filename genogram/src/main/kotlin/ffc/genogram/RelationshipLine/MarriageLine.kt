package ffc.genogram.RelationshipLine

import ffc.genogram.Person

class MarriageLine : Line() {

    private var spousesStorage: ArrayList<List<Person>> = arrayListOf()
    private var lineMarkPos = listOf<Double>()
    var spousesNumb = 1
    var imageLength: Double = 0.0

    fun addSpouse(p1: Person, p2: Person) {
        var spouseList = listOf(p1, p2)
        spousesStorage.add(spouseList)
    }

    fun getSpouseList(): ArrayList<List<Person>> {
        return spousesStorage
    }

    fun setLineMarkPos(startingMarkPos: Double, endingMarkPos: Double) {
        // Marriage line form is " |_________| "
        // Mark means "|"
        // startingMarkPos and startingMarkPos starts at position 1.
        lineMarkPos = listOf(startingMarkPos, endingMarkPos)
    }

    fun getLineMarkPos(): List<Double> {
        return lineMarkPos
    }

    fun getStartingMarkPos(): Double {
        return lineMarkPos[0]
    }

    fun getEndingMarkPos(): Double {
        return lineMarkPos[lineMarkPos.size - 1]
    }

    fun setStartingMarkPos(v: Double) {
        val tmp: MutableList<Double> = lineMarkPos as MutableList<Double>
        tmp[0] = v
        lineMarkPos = tmp
    }

    fun setEndingMarkPos(v: Double) {
        val tmp: MutableList<Double> = lineMarkPos as MutableList<Double>
        tmp[tmp.size - 1] = v
        lineMarkPos = tmp
    }

    fun drawLine() {
        var startingMarkPos = (Relationship.spaceLine + 1)
        var endingMarkPos = startingMarkPos + Relationship.distanceLine
        imageLength = ((Relationship.spaceLine * 2) + 1) + Relationship.distanceLine
        setLineMarkPos(startingMarkPos, endingMarkPos)
    }

    fun setSingleMarriageLine(side: RelationshipLabel) {
        // Delete FamilyTree2 left margin units
        val addMore = (Relationship.lengthLine / 2).toInt() - 2

        imageLength = if (side == RelationshipLabel.LEFT_HAND) {
            setEndingMarkPos(getEndingMarkPos() + addMore)
            imageLength + addMore
        } else {
            val startingMark = getStartingMarkPos() + addMore + 1
            val endingMark = (startingMark + 1) + (imageLength - Relationship.indent.toInt()) + 1
            setStartingMarkPos(startingMark)
            setEndingMarkPos(endingMark)
            // Delete FamilyTree2 left margin units
            imageLength + (imageLength - Relationship.indent.toInt()) - 2
        }
    }
}
