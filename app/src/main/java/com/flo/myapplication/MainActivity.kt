package com.flo.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENTAGE = "24"
private const val INITIAL_TIP_AMOUNT = 19.96
private const val INITIAL_TOTAL_AMOUNT = 103.11

class MainActivity : AppCompatActivity() {
    private lateinit var etBaseInput: EditText
    private lateinit var tvTipPercentage: TextView
    private lateinit var seekBarTipPercentageInput: SeekBar
    private lateinit var tvTipAmountOutput: TextView
    private lateinit var tvTotalOutput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseInput = findViewById(R.id.etBaseInputLabel)
        tvTipPercentage = findViewById(R.id.tvTipPercentageLabel)
        seekBarTipPercentageInput = findViewById(R.id.seekBarTipPercentageInputLabel)
        tvTipAmountOutput = findViewById(R.id.tvTipAmountOutputLabel)
        tvTotalOutput = findViewById(R.id.tvTotalOutputLabel)

        tvTipAmountOutput.setText("$INITIAL_TIP_AMOUNT")
        tvTotalOutput.setText("$INITIAL_TOTAL_AMOUNT")

        seekBarTipPercentageInput.setProgress(Integer.valueOf(INITIAL_TIP_PERCENTAGE))
        tvTipPercentage.setText(INITIAL_TIP_PERCENTAGE + "%")
        seekBarTipPercentageInput.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tvTipPercentage.setText("$progress%")
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        etBaseInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.i(TAG, "onTextChanged string $s")
                Log.i(TAG, "onTextChanged start $start")
                Log.i(TAG, "onTextChanged before $before")
            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal()
            }
        })

    }

    private fun computeTipAndTotal() {
        if (etBaseInput.text.isEmpty()) {
            Log.i(TAG, "etBaseInput is empty")
            return
        }

        //compute tip
        val baseAmount = etBaseInput.text.toString().toDouble()
        val tipAmountPercentage = 0.01 * seekBarTipPercentageInput.progress
        val tipAmount = tipAmountPercentage * baseAmount
        tvTipAmountOutput.setText("$tipAmount")

        //compute total
        val totalAmount = baseAmount + tipAmount
        tvTotalOutput.setText("%.2f".format(totalAmount))
    }
}