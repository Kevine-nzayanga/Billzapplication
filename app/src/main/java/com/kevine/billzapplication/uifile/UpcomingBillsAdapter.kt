package com.kevine.billzapplication.uifile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kevine.billzapplication.databinding.UpcomingBillsListItemBinding
import com.kevine.billzapplication.model.Bill
import com.kevine.billzapplication.model.UpcomingBill
import com.kevine.billzapplication.utils.DateTimeUtils

class UpcomingBillsAdapter(var upcomingBills:List<UpcomingBill>, val onClickBill: OnClickBill):
Adapter<UpcomingBillsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingBillsViewHolder {
        val binding = UpcomingBillsListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UpcomingBillsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return upcomingBills.size

    }

    override fun onBindViewHolder(holder: UpcomingBillsViewHolder, position: Int) {
        val upcomingBill= upcomingBills.get(position)
        holder.binding.apply {
            cbupcoming.isChecked=upcomingBill.paid
            cbupcoming.text=upcomingBill.name
            tvamount.text = upcomingBill.amount.toString()
            tvamount.text = DateTimeUtils.formatCurrency(upcomingBill.amount)
//            tvdueDate.text=upcomingBill.dueDate
            tvdueDate.text=DateTimeUtils.formatDateReadable(upcomingBill.dueDate)

        }


        holder.binding.cbupcoming.setOnClickListener {
            onClickBill.checkPaidBill(upcomingBill)
        }

    }
}

class UpcomingBillsViewHolder(var binding: UpcomingBillsListItemBinding)
    :ViewHolder(binding.root)


interface  OnClickBill{
    fun checkPaidBill(upcomingBill: UpcomingBill)

}