package com.example.inviziosolutionsllp

import androidx.lifecycle.ViewModel
import io.objectbox.Box
import io.objectbox.android.ObjectBoxLiveData


class DataViewModel : ViewModel() {
     var boxLiveData: ObjectBoxLiveData<dataHouse>?=null


    fun getBoxLiveData(dataBox: Box<dataHouse>): ObjectBoxLiveData<dataHouse> {
        if (boxLiveData == null) { // query all notes, sorted a-z by their text
            boxLiveData = ObjectBoxLiveData<dataHouse>(dataBox.query().order(dataHouse_.id).build())
        }
        return boxLiveData as ObjectBoxLiveData<dataHouse>
    }

}