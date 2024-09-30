package io.adrop.ads.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import io.adrop.ads.metrics.AdropEventParam
import io.adrop.ads.metrics.AdropMetrics
import io.adrop.ads.model.AdropKey
import io.adrop.ads.model.AdropValue

class PropertyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)

        findViewById<Button>(R.id.set_property).setOnClickListener { sendProperty() }
        findViewById<Button>(R.id.custom_event).setOnClickListener { sendEvent() }
        findViewById<Button>(R.id.custom_event_name).setOnClickListener { sendEventNameOnly() }
    }

    private fun sendProperty() {
        val key = findViewById<EditText>(R.id.key_edit).text.toString()
        val value = findViewById<EditText>(R.id.value_edit).text.toString()

        if (value.equals("true", ignoreCase = true) || value.equals("false", ignoreCase = true)) {
            AdropMetrics.setProperty(key, value.toBoolean())
        } else if (value.toIntOrNull() != null) {
            AdropMetrics.setProperty(key, value.toInt())
        } else if (value.toDoubleOrNull() != null) {
            AdropMetrics.setProperty(key, value.toDouble())
        } else {
            AdropMetrics.setProperty(key, value)
        }
    }

    private fun sendEvent() {
        val name = "event_name"
        val params = AdropEventParam.Builder()
            .putInt("data_key_1", 1)
            .putFloat("data_key_2", 1.2f)
            .putBoolean("data_key_3", true)
            .putString("data_key_4", "value_text")
            .build()
        Log.d("Adrop", "Send Custom Event - name: $name, params: $params")
        AdropMetrics.logEvent(name, params)
    }

    private fun sendEventNameOnly() {
        val name = "event_name"
        Log.d("Adrop", "Send Custom Event - name: $name")
        AdropMetrics.logEvent(name)
    }
}
