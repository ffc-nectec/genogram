package ffc.genogram.RelationshipLine

class MarriageLine {

    private var lineMarkPos = listOf<Double>()
    var spousesNumb = 1
    var imageLength: Double = 0.0
    var thinkness = 3.0
    var lineColor = 0xff888888

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

    fun drawLine() {
        var startingMarkPos = (Relationship.spaceLine + 1) + 1
        var endingMarkPos = startingMarkPos + Relationship.distanceLine + 1
        imageLength = ((Relationship.spaceLine * 2) + 1) + Relationship.distanceLine + 2
        setLineMarkPos(startingMarkPos, endingMarkPos)
    }

}
