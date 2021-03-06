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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import ffc.genogram.GenderLabel
import ffc.genogram.Person
import ffc.genogram.android.GenogramNodeBuilder

class SampleNodeBuilder : GenogramNodeBuilder {

    override fun viewFor(person: Person, context: Context, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.node_item, parent, false)
        val icon = view.findViewById<Button>(R.id.icon)
        when (person.gender) {
            GenderLabel.MALE -> icon.setBackgroundResource(R.drawable.male_node_icon)
            GenderLabel.FEMALE -> icon.setBackgroundResource(R.drawable.female_node_icon)
        }
        icon.setOnClickListener {
            Toast.makeText(context, "Click ${person.firstname} ${person.lastname}", Toast.LENGTH_SHORT).show()
        }
        val name = view.findViewById<TextView>(R.id.name)
        name.text = person.firstname
        return view
    }
}
