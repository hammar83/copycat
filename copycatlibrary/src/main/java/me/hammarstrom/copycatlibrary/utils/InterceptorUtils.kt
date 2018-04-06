package me.hammarstrom.copycatlibrary.utils

import android.net.Uri
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.Request
import okio.Buffer
import java.io.IOException

object InterceptorUtils {

    /**
     * Get the query string from {@link HttpUrl} url
     */
    fun getQueryString(url: HttpUrl): String? {
        if (url.query().isNullOrEmpty()) {
            return null
        }

        val queryList = arrayListOf<String>()
        val names = arrayListOf<String>()

        url.queryParameterNames().forEach {
            names.add(it)
        }

        names.sort()

        names.forEach {
            queryList.add("$it=${url.queryParameter(it)}")
        }

        return queryList.joinToString("&", "", "")
    }

    /**
     * Get the headers as string from {@link Headers} headers
     */
    fun getHeadersString(headers: Headers): String? {
        if (headers.size() == 0) {
            return null
        }

        val headerList = arrayListOf<String>()
        val names = arrayListOf<String>()

        headers.names().forEach { names.add(it) }

        names.sort()

        names.forEach {
            headerList.add("\"$it\": \"$headers.get(it)\"")
        }

        return headerList.joinToString(",", "", "")
    }

    fun getRequestInfo(request: Request, method: String, headers: String, query: String, body: String): String {
        val url = Uri.parse(request.url().toString()).buildUpon().encodedQuery(query).build().toString()

        return String.format("request\":{\n\"method\": \"%s\",\n\"url\": \"%s\",\n\"headers\": {\n%s\n},\n\"body\": %s\n}", method, url, headers, body)
    }

    /**
     * Get request body as string
     */
    @Throws(IOException::class)
    fun getBody(request: Request): String? {
        var body: String? = null

        request.body().let {
            val copy = request.newBuilder().build()
            val buffer = Buffer()

            copy.body()?.writeTo(buffer)
            body = buffer.readUtf8()
        }

        return body
    }

    fun getRequestHash(method: String?, query: String?, body: String?, headers: String?): String? {
        var stringToHash = ""

        if (!method.isNullOrEmpty()) {
            stringToHash += method
        }

        if (!headers.isNullOrEmpty()) {
            stringToHash += headers
        }

        if (!body.isNullOrEmpty()) {
            stringToHash += body
        }

        if (!query.isNullOrEmpty()) {
            stringToHash += query
        }

        val digest = java.security.MessageDigest.getInstance("MD5")
        digest.update(stringToHash.toByteArray())

        val messageDigest = digest.digest()

        val hexString = StringBuffer()
        messageDigest.forEach {
            hexString.append(Integer.toHexString(0xFF and  it.toInt()))
        }

        return hexString.toString()
    }

}