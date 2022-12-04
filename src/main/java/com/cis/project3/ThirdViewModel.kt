package com.cis.project3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThirdViewModel : ViewModel() {
    private var list: MutableLiveData<ArrayList<Days>> = MutableLiveData()
    private var listItems: ArrayList<Days> = ArrayList()

    fun getList(): MutableLiveData<ArrayList<Days>> {
        return list
    }

    fun addItems() {

        //for (){
           // listItems.add(Days(date,temp,icon))
        //}
        listItems.add(Days("date", "temp", "temp_pic")) //date icon temp
        listItems.add(Days("date", "temp", "temp_pic"))
        listItems.add(Days("date", "temp", "temp_pic"))
        listItems.add(Days("date", "temp", "temp_pic"))
        listItems.add(Days("date", "temp", "tem_pic"))
        list.setValue(listItems)
    }

}