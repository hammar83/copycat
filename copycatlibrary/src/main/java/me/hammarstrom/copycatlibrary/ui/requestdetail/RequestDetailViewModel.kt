package me.hammarstrom.copycatlibrary.ui.requestdetail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import me.hammarstrom.copycatlibrary.db.CopycatRequestDao
import me.hammarstrom.copycatlibrary.models.CopycatRequest

class RequestDetailViewModel(
        private val requestDao: CopycatRequestDao
) : ViewModel() {

    var copycatRequest: LiveData<CopycatRequest>? = null

    fun init(hash: String) {
        copycatRequest = requestDao.getRequestByHash(hash)
    }

}
