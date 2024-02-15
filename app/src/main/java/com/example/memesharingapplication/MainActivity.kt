package com.example.memesharingapplication

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currenturl: String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme(){
        val progress:ProgressBar= findViewById(R.id.progress)
        progress.visibility = View.VISIBLE
        val url = "https://meme-api.com/gimme"
        val queue= Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                              currenturl= response.getString("url")
                val image:ImageView= findViewById(R.id.memeView)
                image.visibility= View.VISIBLE
                Glide.with(this).load(currenturl).listener(
                    object : RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>,
                            isFirstResource: Boolean
                        ): Boolean {
                            progress.visibility=View.GONE
                            return false;
                        }

                        override fun onResourceReady(
                            resource: Drawable,
                            model: Any,
                            target: Target<Drawable>?,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            progress.visibility= View.GONE;
                            image.visibility=View.VISIBLE
                            return false;

                        }

                    }
                ).into(image)


            },
            { error ->

            }
        )
        queue.add(jsonObjectRequest)
    }

    fun nextMemeclick(view: View) {
        loadMeme()
    }
    fun shareclick(view: View) {
        val intent= Intent(Intent.ACTION_SEND)
        intent.type= "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey I got this amazing meme $currenturl")
        val chooser = Intent.createChooser(intent,"Send Image")
        startActivity(chooser);

    }
}