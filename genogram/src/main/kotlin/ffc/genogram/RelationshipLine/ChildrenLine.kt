package ffc.genogram.RelationshipLine

import ffc.genogram.FamilyTreeDrawer
import ffc.genogram.Node.Node
import ffc.genogram.Person

class ChildrenLine : Line() {

    var childrenList: ArrayList<Person> = arrayListOf()
    var parentList: ArrayList<Person> = arrayListOf()
        internal set
    var lineMarkPos: ArrayList<Int> = arrayListOf()
    var centerMarkPos = 0
    var childrenNumb: Int = 1
    var imageLength: Double = 0.0

    fun addLineMarkPos(pos: Int) {
        lineMarkPos.add(pos)
    }

    fun getAllLineMarkPos(): ArrayList<Int> {
        return lineMarkPos
    }

    fun getLineMarkPos(peopleNumb: Int): Int {
        return lineMarkPos[peopleNumb - 1]
    }

    fun drawLine(peopleNumb: Int, mParentList: MutableList<Person>, mChildrenList: MutableList<Person>) {
        childrenNumb = peopleNumb
        parentList = mParentList as ArrayList<Person>
        childrenList = mChildrenList as ArrayList<Person>

        if (childrenNumb == 1) {
            val nodeDistance = ((Relationship.lengthLine / 2) + Node.nodesDistance).toInt()
            addLineMarkPos(nodeDistance + 1)
            imageLength = nodeDistance * 2.0 + 2.0
        } else {
            val startedMark = Relationship.spaceLine.toInt() + 1
            addLineMarkPos(startedMark)

            for (i in 0 until childrenNumb - 1) {
                addLineMarkPos(((startedMark + (i + 1)) + Relationship.distanceLine * (i + 1)).toInt())
                imageLength = ((Relationship.spaceLine * 2) + 1) +
                        (Relationship.distanceLine * (childrenNumb - 1)) + 2 + i
            }

            var markPosition = Math.ceil(imageLength / 2)
            if (childrenNumb % 2 != 0)
                markPosition = (markPosition - ((Node.nodeSize + 1) - Node.nodeBorderSize))
            centerMarkPos = markPosition.toInt()
        }
    }

    fun extendLine(
        familyTreeDrawer: FamilyTreeDrawer,
        extendLayer: Int,
        childrenListInd: MutableList<Int>
    ) {

        childrenNumb = childrenListInd.size

        // Add Children sign spot ','
        val arrayMargin = 2
        val emptyNodeSize = (Node.nodeSize + Node.nodeBorderSize) + arrayMargin
        val halfEmptyNodeSize = (emptyNodeSize / 2) - 1
        lineMarkPos = arrayListOf()
        childrenListInd.forEachIndexed { index, el ->
            var markPos = halfEmptyNodeSize
            var firstIndex = childrenListInd[0]
            if (el != firstIndex) {
                markPos = emptyNodeSize * (el - firstIndex) + (halfEmptyNodeSize)
            }
            lineMarkPos.add(markPos.toInt())
        }
        imageLength = lineMarkPos[lineMarkPos.size - 1] + halfEmptyNodeSize

        // Add Children Sign '^'
        // Find the parent of addedPerson
        var parentChildrenLineInd: Int? = null
        var parentChildrenLine: ChildrenLine? = null

        // Find the the index of the children line above the parent layer (in the extend layer).
        val parentChildrenLineStorage = familyTreeDrawer.getPersonLayer(extendLayer)
        parentChildrenLineStorage.forEachIndexed { index, any ->
            if (any is ChildrenLine && any != this) {
                // For "separateParentSib"
                any.childrenList.forEach { parentSib ->
                    // print("= Parent: ${parentSib.firstname}\n")
                    parentList.forEach { parent ->
                        // print("= Children: ${parent.firstname}\n")
                        if (parentSib.idCard == parent.idCard) {
                            parentChildrenLine = any
                            parentChildrenLineInd = index
                            return@forEachIndexed
                        }
                    }
                }
            } else {
                // For normal move the centerMark
                parentChildrenLine = this
                parentChildrenLineInd = index
            }
        }

        if (parentChildrenLineInd != null) {
            // For "separateParentSib"
            // Find the grandparent's index (the grandparent's index is equal to the grandparent
            // marriage line's index.
            val parent = parentChildrenLine!!.parentList[0]
            val parentLayer = extendLayer - 2
            val parentInd = familyTreeDrawer.findPersonInd(parent, parentLayer)

            // Find the different index of the "parentInd" and "parentChildrenLineInd" and use
            // this different index values to be the factor to find the "centerMark"
            var diffInd = parentInd - parentChildrenLineInd!!

            if (parentChildrenLine!!.parentList.size == 2) {
                // Not a single parent
                diffInd++
            }
            centerMarkPos = (emptyNodeSize * diffInd).toInt() - 1
        }
    }

    fun moveChildrenLineSign(
        familyTreeDrawer: FamilyTreeDrawer,
        lineLayer: Int,
        step: Int,
        emptyNodeNumb: Int,
        childrenId: List<Int>,
        focusedPerson: Person
    ) {
        val parentLayer = lineLayer - 2
        val parentLineLayer = lineLayer - 1
        val childrenLayer = lineLayer + 1
        val marriageChildrenLineLayer = childrenLayer + 1

        /*val parentLayer = familyTreeDrawer.findPersonLayer(focusedPerson)
        val parentLineLayer = parentLayer + 1
        val childrenLayer = parentLayer + 3
        val marriageChildrenLineLayer = parentLayer + 4*/

        familyTreeDrawer.findNumberOfMidEmptyNode(marriageChildrenLineLayer)
        var line = familyTreeDrawer.personFamilyStorage[lineLayer][0]
        if (line is ChildrenLine) {
            // Copy the previous object attributions.
            var previousChildrenLine = familyTreeDrawer.findChildrenLine(
                lineLayer, focusedPerson!!
            )
            if (previousChildrenLine != null) {
                line = previousChildrenLine
            }
        }

        var parent1Ind: Int? = null
        var parent2Ind: Int? = null
        var parent1Id: Long? = null
        var parent2Id: Long? = null
        if (parentList.size > 0) {
            parent1Ind = familyTreeDrawer.findPersonInd(parentList[0], parentLayer)
        }
        if (parentList.size > 1) {
            parent2Ind = familyTreeDrawer.findPersonInd(parentList[1], parentLayer)
        }

        /*print("****** ChildrenLine *******\n")
        print("parentLayer: $parentLayer\n")
        print("parentList[0]: ${parentList[0].firstname}\n")
        print("parentList[1]: ${parentList[1].firstname}\n")
        print("parent1Ind: $parent1Ind\n")
        print("parent2Ind: $parent2Ind\n")
        print("childrenId: $childrenId\n")
        print("focusedPerson: ${focusedPerson.firstname}\n")
        print("...............\n")
        val canvasB = displayObjectResult(familyTreeDrawer)
        print(canvasB.toString())
        print("**************\n")*/

        if (parent1Ind != null) {
            val parent1IdObj = familyTreeDrawer.getPersonLayerInd(parentLayer, parent1Ind) as Person
            parent1Id = parent1IdObj.idCard
        }
        if (parent2Ind != null) {
            val parent2IdObj = familyTreeDrawer.getPersonLayerInd(parentLayer, parent2Ind) as Person
            parent2Id = parent2IdObj.idCard
        }

        // Get the parent marriage line
        // When addedPerson has two parents.
        val marriageLineStorage = familyTreeDrawer.getPersonLayer(parentLineLayer)
        var marriageLine: MarriageLine? = null
        var marriageLineInd = 0
        marriageLineStorage.forEachIndexed { index, any ->
            if (any is MarriageLine) {
                any.getSpouseList().forEach { coupleList ->
                    val parentId = coupleList[1].idCard
                    if (parentId == parent1Id || parentId == parent2Id) {
                        marriageLine = marriageLineStorage[index] as MarriageLine
                        marriageLineInd = index
                    }
                }
            }
        }
        // Find the children line index
        val childrenLineStorage = familyTreeDrawer.getPersonLayer(lineLayer)
        var childrenLineInd = 0
        childrenLineStorage.forEachIndexed { index, any ->
            if (any is ChildrenLine) {
                any.childrenList.forEach {
                    if (it.idCard == childrenList[0].idCard) {
                        childrenLineInd = index
                    }
                }
            }
        }

        val extraSpace = Math.abs(childrenLineInd - marriageLineInd)
        val arrayMargin = 2
        val emptyNodeSize = (Node.nodeSize + Node.nodeBorderSize) + arrayMargin
        val parentEmptyFrontNode = familyTreeDrawer.findNumberOfMidEmptyNodePerson(parentLayer)
        var marriageLineCenter = marriageLine!!.imageLength / 2 + 1
        if (marriageLineInd > childrenLineInd) {
            centerMarkPos = ((emptyNodeSize * extraSpace) + marriageLineCenter).toInt()
        } else if (marriageLineInd == childrenLineInd) {
            centerMarkPos = marriageLineCenter.toInt()
        }
    }
}
