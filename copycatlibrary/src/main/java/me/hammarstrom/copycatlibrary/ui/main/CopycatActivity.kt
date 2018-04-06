package me.hammarstrom.copycatlibrary.ui.main

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
import me.hammarstrom.copycatlibrary.ui.main.adapter.RequestAdapter
import me.hammarstrom.copycatlibrary.ui.requestdetail.RequestDetailActivity

class CopycatActivity : AppCompatActivity() {

    private lateinit var viewModel: CopycatViewModel
    lateinit var db: CopycatDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_copycat)

        val adapter = RequestAdapter(getRequestClickListener())
        val recycler = findViewById<RecyclerView>(R.id.recycler)

        db = CopycatDatabase.getInstance(this)

        viewModel = ViewModelProvider(this, CopycatViewModelFactory(db.copycatRequestDao())).get(CopycatViewModel::class.java)

        viewModel.requests.observe(this, Observer<List<CopycatRequest>> {
            adapter.submitList(it)
        })

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

    private fun getRequestClickListener(): RequestAdapter.OnRequestClickListener {
        return object : RequestAdapter.OnRequestClickListener {
            override fun onRequestClick(hash: String) {
                startActivity(
                        RequestDetailActivity.getIntent(this@CopycatActivity, hash)
                )
            }

        }
    }

    /**
     * AsyncTask to handle database deletion
     */
    class DeleteAllTask(private val requestDao: CopycatRequestDao) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg p0: Void?): Void? {
            requestDao.deleteAll()
            return null
        }

    }
}
