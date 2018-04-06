package me.hammarstrom.copycatlibrary.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import me.hammarstrom.copycatlibrary.db.CopycatRequestDao
import me.hammarstrom.copycatlibrary.models.CopycatRequest

class CopycatViewModel(val dao: CopycatRequestDao) : ViewModel() {

    val requests: LiveData<List<CopycatRequest>> = dao.getAll()

}
