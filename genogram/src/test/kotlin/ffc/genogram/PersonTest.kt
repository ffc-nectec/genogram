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

package ffc.genogram

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.amshove.kluent.`should equal`
import org.junit.Test
import java.lang.reflect.Type

class PersonTest {

    @Test
    fun customDeserializeForProperties() {
        val grandPa = getResourceAs<Person>("person.json").apply {
            properties = Properties(
                    idCard.toInt(),
                    "${firstname} ${lastname}",
                    "https://upload.wikimedia.org/wikipedia/commons/9/9a/Angry_Grandpa_-_2015.jpg")
        }
        val json = grandPa.toJson()

        val grandPaFromJson = json.parseTo<Person>(GsonBuilder()
                .registerTypeAdapter(Person::class.java, CustomPersonDeserializer()).create())

        grandPaFromJson.properties!! as Properties `should equal` grandPa.properties
    }

    data class Properties(val id: Int, val name: String, val avartarUrl: String)

    class CustomPersonDeserializer : JsonDeserializer<Person> {

        override fun deserialize(json: JsonElement,
                                 typeOfT: Type?,
                                 context: JsonDeserializationContext): Person {
            val person = gson.fromJson<Person>(json, typeOfT)
            person.properties = context.deserialize<Properties>(
                    json.asJsonObject.get("properties"),
                    Properties::class.java)
            return person
        }

        companion object {
            private val gson by lazy { GsonBuilder().create() }
        }
    }

    private fun Person.toJson(gson: Gson = genogramGson) = gson.toJson(this)
}
