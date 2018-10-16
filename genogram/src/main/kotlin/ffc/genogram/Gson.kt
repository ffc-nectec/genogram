package ffc.genogram

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

fun Family.toJson(gson: Gson = genogramGson) = gson.toJson(this)

inline fun <reified T> String.parseTo(gson: Gson = genogramGson): T = gson.fromJson(this, object : TypeToken<T>() {}.type)

private inline fun <reified T> GsonBuilder.adapterFor(adapter: Any): GsonBuilder {
    return registerTypeAdapter(object : TypeToken<T>() {}.type, adapter)
}

val genogramGson by lazy { GsonBuilder().create() }
