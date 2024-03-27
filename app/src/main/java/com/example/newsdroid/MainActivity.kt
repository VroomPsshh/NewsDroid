package com.example.newsdroid

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// https://newsapi.org/v2/top-headlines?country=us&category=technology&apiKey=00ac1fb9851f420d8b2e7365de086ed0
// API Key = 00ac1fb9851f420d8b2e7365de086ed0
// https://newsapi.org/v2/top-headlines?apiKey=00ac1fb9851f420d8b2e7365de086ed0&country=us&category=business&page=1


class MainActivity : AppCompatActivity() {
    private lateinit var appBar: AppBarLayout
    private lateinit var toolbar: Toolbar
    private lateinit var rcViewPager: RecyclerView
    private lateinit var btmNavBar: BottomNavigationView
    private var news: Call<News>? = null
    lateinit var adapter: RcViewerNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        appBar = findViewById(R.id.appBar)
        toolbar = findViewById(R.id.toolbar)
        rcViewPager = findViewById(R.id.rcViewer)
        btmNavBar = findViewById(R.id.btmNavBar)

        btmNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.general -> {
                    //codes for that particular nav bar item
                    news = NewsService.newsInstance.getNews("in", categoryLoader(0), 1)
                    getNews()

                    true
                }

                R.id.business -> {
                    // here code for the business btm in navigation item
                    news = NewsService.newsInstance.getNews("in", categoryLoader(1), 1)
                    getNews()
                    true
                }

                R.id.technology -> {
                    //here code for the tech btm in nav bar
                    news = NewsService.newsInstance.getNews("in", categoryLoader(2), 1)
                    getNews()
                    true
                }

                R.id.sports -> {
                    //here code for the sports btm in nav bar
                    news = NewsService.newsInstance.getNews("in", categoryLoader(3), 1)
                    getNews()
                    true
                }

                else -> {
                    //here code for the health btm in nav bar
                    news = NewsService.newsInstance.getNews("in", categoryLoader(4), 1)
                    getNews()
                    true
                }
            }
        }
        news = NewsService.newsInstance.getNews("in", categoryLoader(0), 1)
        getNews()
    }


    private fun getNews() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait while data is being fetched")
        progressDialog.show()


        news?.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val gettingNews = response.body()
                fun loadRcViewer() {
                    adapter = RcViewerNewsAdapter(this@MainActivity, gettingNews!!.articles, this@MainActivity)
                    rcViewPager.adapter = adapter
                    rcViewPager.layoutManager = LinearLayoutManager(this@MainActivity)
                }
                loadRcViewer()
                progressDialog.dismiss()

            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Kindly Check your Internet Connection and try again...",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("Error", "Something is wrong", t)
                progressDialog.dismiss()
            }
        })
    }

    private fun categoryLoader(value: Int): String {
        var category = ""
        when (value) {
            0 -> {
                category = "general"
                return category
            }

            1 -> {
                category = "business"
                return category
            }

            2 -> {
                category = "technology"
                return category
            }

            3 -> {
                category = "sports"
                return category
            }

            else -> {
                category = "health"
                return category
            }
        }
    }

}