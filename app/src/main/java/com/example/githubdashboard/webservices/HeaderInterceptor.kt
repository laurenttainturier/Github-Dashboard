package com.example.githubdashboard.webservices

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.githubdashboard.BuildConfig.GITHUB_AT
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class HeaderInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .header("Authorization", "token $GITHUB_AT")
            .method(original.method(), original.body())
            .build()

        return chain.proceed(request)
    }
}
