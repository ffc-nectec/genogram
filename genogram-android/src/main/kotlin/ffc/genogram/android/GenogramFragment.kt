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
import android.widget.Toast
import ffc.genogram.Family
import ffc.genogram.parseTo
import ffc.genogram.toJson

class GenogramFragment : Fragment() {

    lateinit var families: Families

    private var family: Family? = null //For saveState only

    override fun onActivityCreated(savedState: Bundle?) {
        super.onActivityCreated(savedState)

        val savedFamily = savedState?.family
        if (savedFamily != null) {
            drawFamily(savedFamily)
        } else {
            getFamilyAndDraw()
        }
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
        Toast.makeText(context, "$family", Toast.LENGTH_LONG).show()
        //TODO implement
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
}
