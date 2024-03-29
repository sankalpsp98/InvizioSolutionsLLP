package com.example.inviziosolutionsllp

import android.content.Context
import io.objectbox.BoxStore
import io.objectbox.BoxStoreBuilder

object ObjectBox {
    lateinit var boxStore: BoxStore
        private set
    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }

}