/*
 * Copyright (c) 2018 NECTEC
 *   National Electronics and Computer Technology Center, Thailand
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ffc.genogram.android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ffc.genogram.Family
import ffc.genogram.FamilyTree
import ffc.genogram.Person
import ffc.genogram.parseTo
import ffc.genogram.toJson

class GenogramFragment : Fragment() {

    lateinit var families: Families
    private var family: Family? = null //For saveState only

    /*private lateinit var maleNode: MaleRectangleNode
    private lateinit var imageContainer: FrameLayout
    private lateinit var btnSwapColor: Button*/
    private lateinit var btn1: Button
    private lateinit var btn2: Button

    override fun onActivityCreated(savedState: Bundle?) {
        super.onActivityCreated(savedState)

        val savedFamily = savedState?.family
        if (savedFamily != null) {
            drawFamily(savedFamily)
        } else {
            getFamilyAndDraw()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view = inflater.inflate(R.layout.fragment_geonogram, container, false)
        val view = inflater.inflate(R.layout.test_layout, container, false)
        initInstances(view, savedInstanceState)

        return view
    }

    private fun initInstances(view: View, savedInstanceState: Bundle?) {
//        val imageViewContainer = view.findViewById<View>(R.id.imageViewContainer)
        /*imageContainer = view.findViewById(R.id.imageContainer)
        btnSwapColor = view.findViewById(R.id.btnSwapColor)
        btnSwapColor.setOnClickListener(clickListener)*/
        btn1 = view.findViewById(R.id.btn1)

    }

    private fun getFamilyAndDraw() {
        families.family {
            onSuccess { drawFamily(it) }
            onFail { handleError(it) }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.family = family
        super.onSaveInstanceState(outState)
    }

    private fun drawFamily(family: Family) {
        this.family = family
        btn1.text = "1"
//        Toast.makeText(context, "Family: ${family.familyId}", Toast.LENGTH_LONG).show()

        // TODO: Logic Here
//        maleNode = MaleRectangleNode(context!!)
//        imageContainer.addView(maleNode)
        val familyPic = FamilyTree(family)
        val familyTreePic = familyPic.drawGenogram()

        familyTreePic.personFamilyStorage.forEach { layer ->
            layer.forEach {
                if (it is Person)
                    btn1.text = it.firstname
//                else
//                    print(it)
            }
//            print("\n")
        }

    }

    private fun handleError(t: Throwable?) {
        //TODO implement
    }

    private var Bundle.family: Family?
        set(value) {
            if (value != null)
                putString("family", value.toJson())
            else
                putString("family", null)
        }
        get() = getString("family")?.parseTo()

    /******************
     **   Listener    **
     *******************/
    private val clickListener = View.OnClickListener { v ->
        /*if (v == btnSwapColor) {
            Toast.makeText(context, "Swap Color", Toast.LENGTH_SHORT).show()
            maleNode.swapColor()
            imageContainer.removeAllViews()
            imageContainer.addView(maleNode)
        }*/
    }

}
