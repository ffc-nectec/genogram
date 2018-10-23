package ffc.genogram.RelationshipLine

class MarriageLine {

    private var lineMarkPosStorage: ArrayList<List<Double>> = ArrayList()
    private var lineMarkPos = listOf<Double>()
    var spousesNumb = 0
    var imageLength: Double = 0.0
    var thinkness = 3.0
    var lineColor = 0xff888888

    init {
        spousesNumb = 1
        lineMarkPosStorage.add(lineMarkPos)
    }

    fun setLineMarkPos(startingMarkPos: Double, endingMarkPos: Double) {
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

}
