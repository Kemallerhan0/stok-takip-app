package com.example.stoktakip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : AppCompatActivity() {
    private val stokMap = mutableMapOf<String, Int>()
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scanButton = findViewById<Button>(R.id.scanButton)
        listView = findViewById(R.id.listView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter

        scanButton.setOnClickListener {
            val options = ScanOptions()
            options.setPrompt("Barkod/QR Kodu Okutun")
            options.setBeepEnabled(true)
            barcodeLauncher.launch(options)
        }
    }

    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            val scannedCode = result.contents
            stokMap[scannedCode] = stokMap.getOrDefault(scannedCode, 0) + 1
            updateListView()
        }
    }

    private fun updateListView() {
        val list = stokMap.entries.map { "${it.key} - Adet: ${it.value}" }
        adapter.clear()
        adapter.addAll(list)
        adapter.notifyDataSetChanged()
    }
}
