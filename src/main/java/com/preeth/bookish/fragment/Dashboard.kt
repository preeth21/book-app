package com.preeth.bookish.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.preeth.bookish.R
import com.preeth.bookish.adapter.DashboardRecyclerAdapter
import com.preeth.bookish.model.Book
import com.preeth.bookish.util.ConnectionManager
import org.json.JSONException
import org.json.JSONObject


class Dashboard : Fragment() {
    lateinit var recyclerDashboard:RecyclerView
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var recyclerAdapter: DashboardRecyclerAdapter
    lateinit var  bookInfoList :ArrayList<Book>
    lateinit var bookObject:Book
    lateinit var progressBar: ProgressBar
    lateinit var progressBarLayout: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerDashboard=view.findViewById(R.id.dashboardRecycler)
        progressBar=view.findViewById(R.id.progressBar)
        progressBar.visibility=View.VISIBLE
        progressBarLayout=view.findViewById(R.id.progressBarLayout)
        progressBarLayout.visibility=View.VISIBLE
        layoutManager=LinearLayoutManager(activity)

        val queue=Volley.newRequestQueue(activity as Context)
        val  url="http://13.235.250.119/v1/book/fetch_books/"


            if (ConnectionManager().checkConnectivity(activity as Context))
            {

                val jsonObjectRequest= object :JsonObjectRequest(Request.Method.GET,url,null,Response.Listener
                {
                    try {
                        progressBarLayout.visibility = View.GONE
                        val success = it.getBoolean("success")

                        if (success) {

                            val data = it.getJSONArray("data")
                            for (i in 0 until data.length()) {
                                var bookJsonObject = data.getJSONObject(i)
                                 bookObject=Book(

                                        bookJsonObject.getString("book_id"),
                                        bookJsonObject.getString("name"),
                                        bookJsonObject.getString("author"),
                                        bookJsonObject.getString("rating"),
                                        bookJsonObject.getString("price"),
                                        bookJsonObject.getString("image")
                                    )
                                bookInfoList.add(bookObject)
                                recyclerAdapter = DashboardRecyclerAdapter(activity as Context, bookInfoList)
                                recyclerDashboard.adapter = recyclerAdapter
                                recyclerDashboard.layoutManager = layoutManager



                            }


                        } else {
                            Toast.makeText(
                                activity as Context,
                                "Error Occurred",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }catch(e:JSONException){
                        Toast.makeText(activity as Context,"Some unexpected error occured !",Toast.LENGTH_SHORT).show()

                    }
                },
                    Response.ErrorListener
                {
                    Toast.makeText(activity as Context,"Volley Error Occurred",Toast.LENGTH_SHORT).show()
                }
            ){
                    override fun getHeaders(): MutableMap<String, String>
                    {
                        val headers=HashMap<String, String>()
                        headers["Content-type"]="application/json"
                        headers["token"]="8a118f32eece48"
                        return headers
                    }
                }

               queue.add(jsonObjectRequest)
            }
            else
            {
                var dialogue = AlertDialog.Builder(activity as Context)
                dialogue.setTitle("Error")
                dialogue.setMessage("Internet connection not found")
                dialogue.setPositiveButton("Open Settings")
                {text, listener ->
                    var settingIntent=Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingIntent)
                    activity?.finish()
                }
                dialogue.setNegativeButton("Exit")
                {text, listener ->
                    ActivityCompat.finishAffinity(activity as Activity)
                }
                dialogue.create()
                dialogue.show()

            }

        return view
    }


}
