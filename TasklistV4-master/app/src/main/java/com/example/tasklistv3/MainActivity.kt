package com.example.tasklistv3

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {
    private var datos: ArrayList<String>? = null
    private var adaptador1: ArrayAdapter<String>? = null
    private var lv1: ListView? = null
    private var et1: EditText? = null
    private var et2: EditText? = null
    private var prefe1: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        datos = ArrayList()
        leerSharedPreferences()
        adaptador1 = ArrayAdapter(this, android.R.layout.simple_list_item_1, datos!!)
        lv1 = findViewById<View>(R.id.list1) as ListView
        lv1!!.adapter = adaptador1
        et1 = findViewById<View>(R.id.et1) as EditText
        et2 = findViewById<View>(R.id.et2) as EditText
        lv1!!.onItemLongClickListener =
            OnItemLongClickListener { adapterView, view, i, l ->
                val dialogo1 = AlertDialog.Builder(this@MainActivity)
                dialogo1.setTitle("Importante")
                dialogo1.setMessage("¿ Elimina esta tarea ?")
                dialogo1.setCancelable(false)
                dialogo1.setPositiveButton(
                    "Confirmar"
                ) { dialogo1, id ->
                    val s = datos!![i]
                    val tok1 = StringTokenizer(s, ":")
                    val nom = tok1.nextToken().trim { it <= ' ' }
                    val elemento = prefe1!!.edit()
                    elemento.remove(nom)
                    elemento.commit()
                    datos!!.removeAt(i)
                    adaptador1!!.notifyDataSetChanged()
                }
                dialogo1.setNegativeButton(
                    "Cancelar"
                ) { dialogo1, id -> }
                dialogo1.show()
                false
            }
    }

    private fun leerSharedPreferences() {
        prefe1 = getSharedPreferences("Información", MODE_PRIVATE)
        val claves = prefe1!!.getAll()
        for ((key, value) in claves) {
            datos!!.add(key + " : " + value.toString())
        }
    }

    fun agregar(v: View?) {
        datos!!.add(et1!!.text.toString() + " : " + et2!!.text.toString())
        adaptador1!!.notifyDataSetChanged()
        val elemento = prefe1!!.edit()
        elemento.putString(et1!!.text.toString(), et2!!.text.toString())
        elemento.commit()
        et1!!.setText("")
        et2!!.setText("")
    }
}