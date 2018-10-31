package ffc.genogram.RelationshipLine

import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.Node.EmptyNode
import ffc.genogram.Node.Node
import ffc.genogram.Person

class ChildrenLine : Line() {

    var childrenList: ArrayList<Person> = arrayListOf()
    var parentList: ArrayList<Person> = arrayListOf()
        internal set
    private var lineMarkPos: ArrayList<Int> = arrayListOf()
    var centerMarkPos = 0
    var childrenNumb: Int = 1
    var imageLength: Double = 0.0
    var emptyLine = false

    fun addLineMarkPos(pos: Int) {
        lineMarkPos.add(pos)
    }

    fun getAllLineMarkPos(): ArrayList<Int> {
        return lineMarkPos
    }

    fun getLineMarkPos(peopleNumb: Int): Int {
        return lineMarkPos[peopleNumb - 1]
    }

    fun drawLine(peopleNumb: Int) {
        childrenNumb = peopleNumb

        if (childrenNumb == 1) {
            val nodeDistance = ((Relationship.lengthLine / 2) + Node.nodesDistance).toInt()
            addLineMarkPos(nodeDistance + 1)
            imageLength = nodeDistance * 2.0 + 2.0
        } else {
            val startedMark = Relationship.spaceLine.toInt() + 1
            addLineMarkPos(startedMark)

            for (i in 0 until childrenNumb - 1) {
                addLineMarkPos(((startedMark + (i+1)) + Relationship.distanceLine * (i + 1)).toInt())
                imageLength = ((Relationship.spaceLine * 2) + 1) +
                        (Relationship.distanceLine * (childrenNumb - 1)) + 2 + i
            }

            var markPosition = Math.ceil(imageLength / 2)
            if (childrenNumb % 2 != 0)
                markPosition = (markPosition - ((Node.nodeSize + 1) - Node.nodeBorderSize))
            centerMarkPos = markPosition.toInt()
        }
    }

    fun extendLine(childrenListInd: MutableList<Int>, parentInd: Int) {
        val indentNumb = 4
        var length = (Relationship.lengthLine - 1).toInt()
        val tmp = StringBuilder()
        var startInd = childrenListInd[0]

        // Find Children sign spot
        childrenListInd.forEach { index ->
            var shiftInd = index
            var ind: Int
            if (startInd != 0) {
                shiftInd = index - startInd
            }
            ind = (indentNumb + (length * shiftInd)) - (shiftInd)
            lineMarkPos.add(ind)
        }

        // Add Children Sign '^'
        centerMarkPos = if (startInd == 0) {
            (Relationship.distanceLine.toInt() + 1) * (parentInd + 1) - 1
        } else {
            ((Relationship.distanceLine.toInt() + 1) * (parentInd + 1) - 1) / 2
        }
        // Check Children Sign before adding
        for (i in 0 until tmp.length) {
            lineMarkPos.forEach {
                if (centerMarkPos == it) {
                    centerMarkPos++
                }
            }
        }

        imageLength = (indentNumb * 2.0) + lineMarkPos[lineMarkPos.size - 1] - 3
        childrenNumb = childrenListInd.size
    }

    fun moveChildrenLineSign(
        familyTreeDrawer: FamilyTreeDrawer,
        lineLayer: Int, step: Int, ChildInd: List<Int>
    ) {
        val extraStep = step + 1
        // Delete 1 left margin
        centerMarkPos = ((Relationship.distanceLine.toInt() * extraStep) + extraStep) - 1

        val parentLayer = lineLayer - 1
        val childrenLayer = lineLayer + 1
        var parentEmptyNodeNumber = familyTreeDrawer.findNumberOfEmptyNode(parentLayer)
        var childrenFrontEmptyNodeNumber = familyTreeDrawer.findNumberOfEmptyNode(lineLayer)
        var childrenMidEmptyNodeNumber = familyTreeDrawer.findNumberOfMidEmptyNode(childrenLayer)
        var line = familyTreeDrawer.personFamilyStorage[lineLayer][0]
        if (line is ChildrenLine) {
            // Copy the previous object attributions.
            lineMarkPos = line.lineMarkPos
            imageLength = line.imageLength
            childrenNumb = line.childrenNumb
            childrenList = line.childrenList
            parentList = line.parentList
            emptyLine = line.emptyLine
        }

        if (((Math.abs(parentEmptyNodeNumber - childrenFrontEmptyNodeNumber) > 0 &&
                    childrenFrontEmptyNodeNumber > 0) ||
                    ((parentEmptyNodeNumber == childrenFrontEmptyNodeNumber) &&
                            (line is EmptyNode) &&
                            childrenMidEmptyNodeNumber != 0))
        ) {
            line = familyTreeDrawer.personFamilyStorage[lineLayer][ChildInd[0]]
            if (line is ChildrenLine) {
            }
            centerMarkPos = (centerMarkPos - Relationship.lengthLine).toInt() + parentEmptyNodeNumber
        }

    }
}
