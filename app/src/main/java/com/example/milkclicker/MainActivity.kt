package com.example.milkclicker

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var txtTotal: TextView
    private lateinit var perClick: TextView
    private lateinit var imageMilk: ImageView
    private lateinit var cookieRow: TableRow
    private lateinit var oreoRow: TableRow
    private lateinit var chocmilkRow: TableRow
    private var myInt: Int? = 0
    private var multiplier: Int = 0
    private var chocmilk: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)

        sp = getSharedPreferences("saved_game", MODE_PRIVATE)
        editor = sp.edit()

        txtTotal = findViewById(R.id.txtTotal)
        perClick = findViewById(R.id.perClick)
        imageMilk = findViewById(R.id.imgMilk)
        cookieRow = findViewById(R.id.cookieRow)
        oreoRow = findViewById(R.id.oreoRow)
        chocmilkRow = findViewById(R.id.chocmilkRow)

        clicker()
    }

    private fun clicker() {
        imageMilk.setOnClickListener {
            myInt = myInt?.inc()?.plus(multiplier)
            txtTotal.text = "$myInt"
            if (myInt!! > 99) {
                cookieRow.visibility = View.VISIBLE
            }
            if (myInt!! > 999) {
                oreoRow.visibility = View.VISIBLE
            }
            if (myInt!! > 9999) {
                if (chocmilk == 0) {
                    chocmilkRow.visibility = View.VISIBLE
                }
            }
        }
    }

    fun cookieClicks(view: View) {
        if (myInt!! > 99) {
            multiplier = multiplier.inc()
            txtTotal.text = (myInt?.minus(100)).toString()
            (myInt?.minus(100)).also { myInt = it }
            perClick.text = (multiplier+1).toString()
            if (myInt!! < 100) {
                cookieRow.visibility = View.INVISIBLE
            }
            if (myInt!! < 1000) {
                oreoRow.visibility = View.INVISIBLE
            }
            if (myInt!! < 10000) {
                if (chocmilk == 0) {
                    chocmilkRow.visibility = View.INVISIBLE
                }
            }
        }
    }

    fun oreoClicks(view: View) {
        if (myInt!! > 999) {
            multiplier = multiplier.inc()+9
            txtTotal.text = (myInt?.minus(1000)).toString()
            (myInt?.minus(1000)).also { /*it ->*/ myInt = it }
            perClick.text = (multiplier+1).toString()
            if (myInt!! < 100) {
                cookieRow.visibility = View.INVISIBLE
            }
            if (myInt!! < 1000) {
                oreoRow.visibility = View.INVISIBLE
            }
            if (myInt!! < 10000) {
                if (chocmilk == 0) {
                    chocmilkRow.visibility = View.INVISIBLE
                }
            }
        }
    }

    fun chocMilk(view: View) {
        if (myInt!! > 9999) {
            txtTotal.text = (myInt?.minus(10000)).toString()
            (myInt?.minus(10000)).also {myInt = it }
            imageMilk.setImageResource(R.drawable.chocmilk)
            chocmilk = 1
            chocmilkRow.visibility = View.INVISIBLE
            if (myInt!! < 100) {
                cookieRow.visibility = View.INVISIBLE
            }
            if (myInt!! < 1000) {
                oreoRow.visibility = View.INVISIBLE
            }
        }
    }

    override fun onPause() {
        super.onPause()

        val score = "$myInt"
        val multi = "$multiplier"
        val choc = "$chocmilk"
        editor.apply {
            putString("current_score", score)
            putString("click_multiplier", multi)
            putString("choc_milk", choc)
            commit()
        }
    }

    override fun onResume() {
        super.onResume()

        val score1 = sp.getString("current_score", null)
        val multi1 = sp.getString("click_multiplier", null)
        val choc1 = sp.getString("choc_milk", null)
        if (score1 != null) {
            myInt= score1.toInt()
            txtTotal.text = score1
        }
        if (multi1 != null) {
            multiplier = multi1.toInt()
            perClick.text = (multiplier+1).toString()
        }
        if (choc1 != null) {
            chocmilk = choc1.toInt()
        }
        if (myInt!! > 99) {
            cookieRow.visibility = View.VISIBLE
        }
        if (myInt!! > 999) {
            oreoRow.visibility = View.VISIBLE
        }
        if (chocmilk == 1) {
            imageMilk.setImageResource(R.drawable.chocmilk)
        }
    }
}