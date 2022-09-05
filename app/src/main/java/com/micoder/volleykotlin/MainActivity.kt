package com.micoder.volleykotlin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    // on below line we are creating variables
    // for our text view, image view and progress bar
    lateinit var courseNameTV: TextView
    lateinit var courseDescTV: TextView
    lateinit var courseReqTV: TextView
    lateinit var courseIV: ImageView
    lateinit var visitCourseBtn: Button
    lateinit var loadingPB: ProgressBar

    // on below line we are creating a variable for our url.
    var url = "https://jsonkeeper.com/b/8RFY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // on below line we are initializing our variable with their ids.
        courseNameTV = findViewById(R.id.idTVCourseName)
        courseDescTV = findViewById(R.id.idTVDesc)
        courseReqTV = findViewById(R.id.idTVPreq)
        courseIV = findViewById(R.id.idIVCourse)
        visitCourseBtn = findViewById(R.id.idBtnVisitCourse)
        loadingPB = findViewById(R.id.idLoadingPB)

        // on below line we are creating a variable for our
        // request queue and initializing it.
        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)

        // on below line we are creating a variable for request
        // and initializing it with json object request
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->

            // this method is called when we get a successful response from API.

            // we are setting the visibility of progress bar as gone.
            loadingPB.setVisibility(View.GONE)

            // on below line we are adding a try catch block.
            try {
                // on below line we are getting data from our response
                // and setting it in variables.
                val courseName: String = response.getString("courseName")
                val courseLink: String = response.getString("courseLink")
                val courseImg: String = response.getString("courseimg")
                val courseDesc: String = response.getString("courseDesc")
                val coursePreq: String = response.getString("Prerequisites")

                // on below line we are setting our
                // data to our text view and image view.
                courseReqTV.text = coursePreq
                courseDescTV.text = courseDesc
                courseNameTV.text = courseName

                // on below line we are setting
                // image view from image url.
                Picasso.get().load(courseImg).into(courseIV)

                // on below line we are changing
                // visibility for our button.
                visitCourseBtn.visibility = View.VISIBLE

                // on below line we are adding
                // click listener for our button.
                visitCourseBtn.setOnClickListener {

                    // on below line we are opening
                    // a intent to view the url.
                    val i = Intent(Intent.ACTION_VIEW)
                    i.setData(Uri.parse(courseLink))
                    startActivity(i)
                }

            } catch (e: Exception) {
                // on below line we are
                // handling our exception.
                e.printStackTrace()
            }

        }, { error ->
            // this method is called when we get
            // any error while fetching data from our API
            Log.e("TAG", "RESPONSE IS $error")
            // in this case we are simply displaying a toast message.
            Toast.makeText(this@MainActivity, "Fail to get response", Toast.LENGTH_SHORT)
                .show()
        })
        // at last we are adding
        // our request to our queue.
        queue.add(request)
    }

}