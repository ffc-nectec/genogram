package ffc.genogram.Node

import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.RelationshipLine.RelationshipLabel

class EmptyNode: Node() {

    lateinit var nodeString: String
    var nodeSize = Node.nodeSize

    override fun drawNode(relationLabel: RelationshipLabel?, siblings: Boolean): FamilyTreeDrawer? {
        return null
    }

    override fun getArea(): Double {
        return nodeSize + nodeBorderSize
    }

    fun drawEmptyNode(): String {
        nodeString = StringBuilder().toString()

        for (i in 0 until Node.nodeSize.toInt() + nodeBorderSize.toInt()) {
            nodeString += " "
        }

        return nodeString
    }
}
