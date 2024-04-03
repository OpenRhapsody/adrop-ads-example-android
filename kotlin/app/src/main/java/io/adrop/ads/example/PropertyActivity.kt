package io.adrop.ads.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import io.adrop.ads.metrics.AdropMetrics
import io.adrop.ads.model.AdropKey
import io.adrop.ads.model.AdropValue

class PropertyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)

        findViewById<Button>(R.id.male).setOnClickListener { gender(AdropValue.AdropGender.MALE) }
        findViewById<Button>(R.id.female).setOnClickListener { gender(AdropValue.AdropGender.FEMALE) }
        findViewById<Button>(R.id.other).setOnClickListener { gender(AdropValue.AdropGender.OTHER) }
        findViewById<Button>(R.id.g_unknown).setOnClickListener { gender(AdropValue.UNKNOWN) }

        findViewById<Button>(R.id.fixed_18).setOnClickListener { age(18) }
        findViewById<Button>(R.id.fixed_35).setOnClickListener { age(35) }
        findViewById<Button>(R.id.fixed_47).setOnClickListener { age(47) }

        findViewById<Button>(R.id.d2010111).setOnClickListener { birth("2010111") }
        findViewById<Button>(R.id.d2005).setOnClickListener { birth("2005") }
        findViewById<Button>(R.id.d199101).setOnClickListener { birth("199101") }
    }

    private fun gender(value: String) {
        AdropMetrics.setProperty(AdropKey.GENDER, value)
    }

    private fun age(value: Int) {
        AdropMetrics.setProperty(AdropKey.AGE, value.toString())
    }

    private fun birth(value: String) {
        AdropMetrics.setProperty(AdropKey.BIRTH, value)
    }
}
