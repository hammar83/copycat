package me.hammarstrom.copycatlibrary.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import me.hammarstrom.copycatlibrary.R
import me.hammarstrom.copycatlibrary.db.CopycatDatabase
import me.hammarstrom.copycatlibrary.db.CopycatRequestDao
import me.hammarstrom.copycatlibrary.models.CopycatRequest
import me.hammarstrom.copycatlibrary.ui.adapter.RequestAdapter
import me.hammarstrom.copycatlibrary.utils.CustomViewModelFactory

class CopycatActivity : AppCompatActivity() {

    private lateinit var viewModel: CopycatViewModel
    lateinit var db: CopycatDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_copycat)

        db = CopycatDatabase.getInstance(this)

        val adapter = RequestAdapter()

        viewModel = ViewModelProvider(this, CustomViewModelFactory(db.copycatRequestDao())).get(CopycatViewModel::class.java)

        viewModel.requests.observe(this, Observer<List<CopycatRequest>> {
            adapter.submitList(it)
        })

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = adapter
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_delete_all -> {
                deleteAllRequests()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllRequests() {
        DeleteAllTask(db.copycatRequestDao()).execute()
    }

    class DeleteAllTask(val requstDao: CopycatRequestDao) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg p0: Void?): Void? {
            requstDao.deleteAll()
            return null
        }

    }
}
