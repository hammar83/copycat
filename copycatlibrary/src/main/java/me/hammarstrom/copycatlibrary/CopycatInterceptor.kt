package me.hammarstrom.copycatlibrary

import android.content.Context
import android.os.AsyncTask
import me.hammarstrom.copycatlibrary.db.CopycatDatabase
import me.hammarstrom.copycatlibrary.models.CopycatRequest
import me.hammarstrom.copycatlibrary.models.Header
import me.hammarstrom.copycatlibrary.models.Request
import me.hammarstrom.copycatlibrary.utils.InterceptorUtils
import okhttp3.Interceptor
import okhttp3.Response

class CopycatInterceptor(
        val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url()
        val method = request.method()
        val host = url.host()
        val path = url.encodedPath()

        val query = InterceptorUtils.getQueryString(url)
        val body = InterceptorUtils.getBody(request)
        val headers = InterceptorUtils.getHeadersString(request.headers())
        val requestHash = InterceptorUtils.getRequestHash(method, query, body, headers)

        val response = chain.proceed(request)

        val bufferedSource = response.body()?.source()
        bufferedSource?.request(Long.MAX_VALUE)

        val buffer = bufferedSource?.buffer()
        val responseBodyString = buffer?.clone()?.readUtf8()

        requestHash?.let {
            val mock = CopycatRequest(
                    it,
                    Request(
                            host,
                            path,
                            method,
                            url.toString(),
                            Header("", "", "")
                    ),
                    responseBodyString ?: ""
            )

            SaveRequestTask(
                    CopycatDatabase.getInstance(context),
                    mock
            ).execute()
        }

        return response
    }

    class SaveRequestTask(val db: CopycatDatabase, val request: CopycatRequest) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg p0: Void?): Void? {
            db.copycatRequestDao().insert(request)
            return null
        }

    }

}
