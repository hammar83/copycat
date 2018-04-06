package me.hammarstrom.copycatlibrary.ui.requestdetail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import me.hammarstrom.copycatlibrary.db.CopycatRequestDao

class RequestDetailViewModelFactory(private val dao: CopycatRequestDao) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RequestDetailViewModel(dao) as T
    }

}