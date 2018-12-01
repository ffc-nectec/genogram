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

package ffc.genogram.android.sample

import android.content.Context
import android.content.res.Resources.Theme
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RawRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ThemedSpinnerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import com.otaliastudios.zoom.ZoomLayout
import ffc.genogram.android.GenogramView
import kotlinx.android.synthetic.main.activity_main.spinner
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.drop_down_item.view.text1

class MainActivity : AppCompatActivity() {

    val familyResources: Map<String, Int> = mapOf(
            "3 gen 3 child" to R.raw.family_3_children_3rd_gen,
            "3 gen 4 child #7" to R.raw.family_4_children_3rd_gen_7,
            "3 gen 7 child" to R.raw.family_7_children_3rd_gen,
            "2 gen 2 spouses #6" to R.raw.family_2_spouses_6,
            "2 gen 2 spouses #13" to R.raw.family_2_spouses_13,
            "In Family we trust 2018 (easy-1)" to R.raw.in_family_we_trust_2018_easy_1,
            "In Family we trust 2018 (easy-2)" to R.raw.in_family_we_trust_2018_easy_2,
            "In Family we trust 2018 (Simple)" to R.raw.in_family_we_trust_2018_simple,
            "In Family we trust 2018" to R.raw.in_family_we_trust_2018
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        spinner.adapter = DropDownAdapter(toolbar.context, familyResources.keys.toTypedArray())
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val key = familyResources.keys.toList()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, GenogramFragment.newInstance(familyResources[key[position]]!!))
                        .commit()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private class DropDownAdapter(context: Context, objects: Array<String>) : ArrayAdapter<String>(context, R.layout.drop_down_item, objects), ThemedSpinnerAdapter {
        private val mDropDownHelper: ThemedSpinnerAdapter.Helper = ThemedSpinnerAdapter.Helper(context)

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: View
            if (convertView == null) {
                val inflater = mDropDownHelper.dropDownViewInflater
                view = inflater.inflate(R.layout.drop_down_item, parent, false)
            } else {
                view = convertView
            }
            view.text1.text = getItem(position)
            return view
        }

        override fun getDropDownViewTheme(): Theme? {
            return mDropDownHelper.dropDownViewTheme
        }

        override fun setDropDownViewTheme(theme: Theme?) {
            mDropDownHelper.dropDownViewTheme = theme
        }
    }

    class GenogramFragment : Fragment() {

        lateinit var container: ZoomLayout

        override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.genogram_fragment, parent, false)
            container = rootView.findViewById(R.id.zoomLayout)
            return rootView
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)

            val families = RawResourceFamilies(context!!, arguments!!.getInt(ARG_RAW))
            families.family {
                onSuccess {
                    try {
                        val view = GenogramView(context!!)
                        view.nodeBuilder = SampleNodeBuilder()
                        view.drawFamily(it)
                        container.addView(view)
                        Handler().postDelayed({ container.zoomOut() }, 150)
                    } catch (it: Exception) {
                        Toast.makeText(context, "${it.javaClass.name}: ${it.message}", Toast.LENGTH_SHORT).show()
                        it.printStackTrace()
                    }
                }
                onFail {
                    Toast.makeText(context, it?.message, Toast.LENGTH_SHORT).show()
                    it?.printStackTrace()
                }
            }
        }

        companion object {
            private val ARG_RAW = "raw"

            fun newInstance(@RawRes rawRes: Int): GenogramFragment {
                val fragment = GenogramFragment()
                val args = Bundle()
                args.putInt(ARG_RAW, rawRes)
                fragment.arguments = args
                return fragment
            }
        }
    }
}
