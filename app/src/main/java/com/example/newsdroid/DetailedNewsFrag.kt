package com.example.newsdroid

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

class DetailedNewsFrag : Fragment() {
    private lateinit var webView: WebView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =inflater.inflate(R.layout.fragment_detailed_news, container, false)
        webView = view.findViewById(R.id.webView)


        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please wait while data is being fetched")
        progressDialog.show()

        val url = arguments?.getString("url")
        if (url != null){
            webView.settings.javaScriptEnabled = true
            webView.webViewClient = object: WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progressDialog.dismiss()
                    webView.visibility = View.VISIBLE
                }
            }
            webView.loadUrl(url)
        }
        return view
    }
}