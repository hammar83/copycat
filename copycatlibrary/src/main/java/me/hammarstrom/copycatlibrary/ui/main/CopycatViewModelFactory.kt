package me.hammarstrom.copycatlibrary.ui.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import me.hammarstrom.copycatlibrary.db.CopycatRequestDao

class CopycatViewModelFactory(private val dao: CopycatRequestDao) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CopycatViewModel(dao) as T
    }

}