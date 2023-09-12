package com.kevine.billzapplication.uifile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kevine.billzapplication.databinding.UpcomingBillsListItemBinding
import com.kevine.billzapplication.model.UpcomingBill

class UpcomingBillsAdapter(var upcomingBills:List<UpcomingBill>):
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
            cbupcoming.text=upcomingBill.name
            tvamount.text = upcomingBill.amount.toString()
            tvdueDate.text=upcomingBill.dueDate
        }
    }
}

class UpcomingBillsViewHolder(var binding: UpcomingBillsListItemBinding)
    :ViewHolder(binding.root)