package me.hammarstrom.copycatlibrary.utils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import me.hammarstrom.copycatlibrary.db.CopycatRequestDao
import me.hammarstrom.copycatlibrary.ui.CopycatViewModel

class CustomViewModelFactory(private val dao: CopycatRequestDao) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CopycatViewModel(dao) as T
    }

}
