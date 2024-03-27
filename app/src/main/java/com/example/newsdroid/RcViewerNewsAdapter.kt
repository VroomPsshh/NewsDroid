package com.example.newsdroid

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Callback

class RcViewerNewsAdapter(private val context: Context, private val articles: List<Article>, private val activity: MainActivity) :
    RecyclerView.Adapter<RcViewerNewsAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_view, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articles.size
    }


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.newsTitle.text = article.title
        holder.newsDescription.text = article.description
        Glide.with(context).load(article.urlToImage).into(holder.newsImage)

        fun loadFrag(fragment: Fragment){
            val fm: FragmentManager = activity.supportFragmentManager
            val ft: FragmentTransaction = fm.beginTransaction()

            val bundle = Bundle()
            bundle.putString("url", article.url)
            fragment.arguments = bundle

            ft.addToBackStack(null)
            ft.replace(R.id.container, fragment)
            ft.commit()
        }


        holder.itemView.setOnClickListener {
            Toast.makeText(context, "i hope you are able to read news clearly", Toast.LENGTH_SHORT)
                .show()
           loadFrag(DetailedNewsFrag())
        }

    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsImage: ImageView = itemView.findViewById(R.id.imageView)
        var newsTitle: TextView = itemView.findViewById(R.id.newsHead_txtView)
        var newsDescription: TextView = itemView.findViewById(R.id.newsDesc_txtView)
    }
}

