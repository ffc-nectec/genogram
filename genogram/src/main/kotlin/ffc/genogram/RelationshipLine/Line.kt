package ffc.genogram.RelationshipLine

import ffc.genogram.Node.Node

abstract class Line {

    companion object {
        var thinkness = 3.0
    }

    val nodeDistance = ((Relationship.lengthLine / 2) + Node.nodesDistance).toInt()
}
