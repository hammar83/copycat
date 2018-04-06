package me.hammarstrom.copycatlibrary.ui.requestdetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.hammarstrom.copycatlibrary.R
import me.hammarstrom.copycatlibrary.db.CopycatDatabase
import me.hammarstrom.copycatlibrary.models.CopycatRequest

class RequestDetailActivity : AppCompatActivity() {

    companion object {
        private const val BUNDLE_EXTRAS_HASH = "RequestDetailActivity:BUNDLE_EXTRAS_HASH"

        fun getIntent(context: Context, hash: String): Intent {
            val intent = Intent(context, RequestDetailActivity::class.java)
            intent.putExtra(BUNDLE_EXTRAS_HASH, hash)

            return intent
        }
    }

    lateinit var viewModel: RequestDetailViewModel
    val db = CopycatDatabase.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_detail)

        val hash = intent.getStringExtra(BUNDLE_EXTRAS_HASH)

        viewModel = ViewModelProvider(this, RequestDetailViewModelFactory(db.copycatRequestDao())).get(RequestDetailViewModel::class.java)
        viewModel.init(hash)

        viewModel.copycatRequest?.observe(this, Observer {
            populateViewFromRequest(it)
        })
    }

    private fun populateViewFromRequest(request: CopycatRequest?) {
        request?.let {
            supportActionBar?.title = "${it.request.method}: ${it.request.path}"
        }
    }

}
