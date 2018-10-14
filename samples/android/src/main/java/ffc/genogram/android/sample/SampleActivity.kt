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
import android.os.Bundle
import android.support.annotation.RawRes
import android.support.v7.app.AppCompatActivity
import ffc.genogram.Family
import ffc.genogram.android.Families
import ffc.genogram.android.GenogramFragment

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        val genogram = GenogramFragment()
        genogram.families = RawResourceFamilies(this, R.raw.family_3_children_3rd_gen)

        supportFragmentManager.beginTransaction().replace(R.id.container, genogram).commit()
    }
}

class RawResourceFamilies(val context: Context, @RawRes val rawId: Int) : Families {

    override fun family(callbackDsl: Families.Callback.() -> Unit) {
        val callback = Families.Callback().apply(callbackDsl)
        try {
            callback.onSuccess(context.rawAs<Family>(rawId))
        } catch (exception: Exception) {
            callback.onFail(exception)
        }
    }
}
