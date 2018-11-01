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

import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.otaliastudios.zoom.ZoomLayout
import ffc.genogram.android.GenogramView
import ffc.genogram.android.relation.RelationPath

class SimpleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        RelationPath.customPaintPath = {
            color = ContextCompat.getColor(applicationContext, R.color.colorPrimary)
        }

        val families = RawResourceFamilies(this, R.raw.family_3_children_3rd_gen)
        families.family {
            onSuccess {
                val view = GenogramView(this@SimpleActivity)
                view.nodeBuilder = SampleNodeBuilder()
                view.drawFamily(it)

                val container = findViewById<ZoomLayout>(R.id.container)
                container.addView(view)
                Handler().postDelayed({ container.zoomOut() }, 150)
            }
        }
    }
}
