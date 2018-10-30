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
import ffc.genogram.Family

class GenogramFragment : Fragment() {

    lateinit var families: Families
    var personViewHolder: PersonViewHolder? = null
    lateinit var view: GenogramView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.genogram_fragment, container, false).apply {
            view = findViewById(R.id.genogram)
        }
    }

    override fun onActivityCreated(savedState: Bundle?) {
        super.onActivityCreated(savedState)
        getFamilyAndDraw()
    }

    private fun getFamilyAndDraw() {
        families.family {
            onSuccess { drawFamily(it) }
            onFail { handleError(it) }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun drawFamily(family: Family) {
        personViewHolder?.let { view.personViewHolder = it }
        view.drawFamily(family)
    }

    private fun handleError(t: Throwable?) {
        //TODO implement
    }
}
