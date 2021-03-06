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
import android.os.Handler
import android.support.annotation.RawRes
import com.google.gson.Gson
import ffc.genogram.Family
import ffc.genogram.android.Families
import java.io.BufferedReader
import java.io.InputStreamReader

class RawResourceFamilies(val context: Context, @RawRes val rawId: Int) : Families {

    override fun family(callbackDsl: Families.Callback.() -> Unit) {
        val callback = Families.Callback().apply(callbackDsl)
        Handler().postDelayed({
                callback.onSuccess(context.rawAs<Family>(rawId))
        }, 500)
    }
}

inline fun <reified T> Context.rawAs(@RawRes rawId: Int, gson: Gson = Gson()): T {
    val reader = BufferedReader(InputStreamReader(resources.openRawResource(rawId)))
    return gson.fromJson(reader.readText(), T::class.java)
}

