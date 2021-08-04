package com.example.testappkotlin

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

class BaseActivity : AppCompatActivity() {

    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        progressDialog = ProgressDialog(this)
    }

    fun showToast(context : Context, text : String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun showProgress(act : Activity, bShow : Boolean) {
        act.runOnUiThread {
            try {
                if(bShow) {
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                    progressDialog.setMessage(getString(R.string.please_wait))
                    progressDialog.setCancelable(false)
                    progressDialog.show()
                }
                else {
                    progressDialog.dismiss()
                }
            }catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }
}