package com.kevine.billzapplication.uifile


import android.content.Context
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.kevine.billzapplication.databinding.ActivityAddBillBinding
import com.kevine.billzapplication.model.Bill
import com.kevine.billzapplication.utils.Constants
import com.kevine.billzapplication.utils.DateTimeUtils
import com.kevine.billzapplication.viewmodel.BillsViewModel
import java.util.UUID

class AddBillActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBillBinding
    var selectedMonth = 0
    var selectedDay = 0
    val billsViewModel: BillsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBillBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNav()

    }

    fun setUpNav() {
        setSupportActionBar(binding.tbarAddBill)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowCustomEnabled(true)

    }

    override fun onResume() {
        super.onResume()
        setupFreqSpinner()
        binding.btnsavebill.setOnClickListener {
            validateBill()

        }

    }

    private fun setupFreqSpinner() {
        val frequencies = arrayOf("Weekly", "Monthly", "Annual")
        val freqAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, frequencies)
        freqAdapter
            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spFrequency.adapter = freqAdapter

//    fun
        binding.spFrequency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (binding.spFrequency.selectedItem.toString()) {
                    "Weekly" -> {
                        setUpDueDateSpinner(IntArray(7) { it + 1 }.toTypedArray())
                    }

                    "Monthly" -> {

                        ShowSpinner()
                        setUpDueDateSpinner(IntArray(31) { it + 1 }.toTypedArray())

                    }

                    "Annual" -> {
                        Showdatepicker()
                        SetupDpDueDate()

                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }


    }




    fun setUpDueDateSpinner(dates: Array<Int>) {
        val dueDateAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dates)
        dueDateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.spDueDate.adapter = dueDateAdapter

    }

    fun ShowSpinner() {
        binding.dpDueDate.hide()
        binding.spDueDate.show()
    }

    fun Showdatepicker() {
        binding.dpDueDate.show()
        binding.spDueDate.hide()
    }

    fun SetupDpDueDate() {
        val calendar = Calendar.getInstance()
        binding.dpDueDate.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, date ->
            selectedMonth = month + 1
            selectedDay = date
        }
    }


    fun validateBill() {
        val billName = binding.etName.text.toString()
        val amount = binding.etAmount.text.toString()
        val frequency = binding.spFrequency.selectedItem.toString()
        var dueDate = Constants.EMPTY_STRING
        if (frequency == Constants.YEARLY) {
            dueDate=DateTimeUtils.createDateFromDayAndMonth(selectedDay,selectedMonth)
                .substring(5)
        } else {
            dueDate = binding.spDueDate.selectedItem.toString()
        }
        var error = false
        if (billName.isBlank()) {
            error = true
            binding.etName.error = "Bill Name required"
        }
        if (amount.isBlank()) {
            error = true
            binding.etAmount.error = "amount required"
        }
        if (!error) {
            val prefs = getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
            val userId = prefs.getString(Constants.USER_ID, Constants.EMPTY_STRING)
            val bill = Bill(
                billId = UUID.randomUUID().toString(),
                name = billName,
                amount = amount.toDouble(),
                userId = userId.toString(),
                frequency = frequency,
                dueDate = dueDate,
                synced= false
            )
            billsViewModel.saveBill(bill)
            resetForm()
        }

    }

    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.hide() {
        this.visibility = View.GONE
    }

    fun resetForm() {
        binding.etName.setText(Constants.EMPTY_STRING)
        binding.etAmount.setText(Constants.EMPTY_STRING)
        binding.spFrequency.setSelection(0)
        ShowSpinner()
        binding.spDueDate.setSelection(0)
    }
}


