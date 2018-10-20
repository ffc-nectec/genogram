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

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ffc.genogram.Person

interface PersonViewHolder {

    fun viewFor(person: Person, context: Context, parent: ViewGroup): View
}

class DummyPersonViewHolder : PersonViewHolder {

    override fun viewFor(person: Person, context: Context, parent: ViewGroup): View {
        return Button(context).apply {
            text = "${person.firstname}"
        }
    }
}
