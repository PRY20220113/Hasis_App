package com.upc.hasis_app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upc.hasis_app.databinding.ItemRecipeBinding
import com.upc.hasis_app.domain.entity.Medicine


class PrescriptionAdapter(private val prescriptions: List<Medicine>)
    :RecyclerView.Adapter<PrescriptionAdapter.PrescriptionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescriptionViewHolder {
        val binding = ItemRecipeBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PrescriptionViewHolder(binding)
    }

    override fun getItemCount() = prescriptions.size

    override fun onBindViewHolder(holder: PrescriptionViewHolder, position: Int) {
        with(holder){
            with(prescriptions[position]) {
                binding.tvMedicineName.text = name
                val medicineWeight = "$weight mg"
                binding.tvMedicineWeight.text = medicineWeight
                val days = "$prescriptions dias"
                binding.tvDays.text = days
                val prescriptionDescription = "$quantity tomas cada $eachHour horas"
                binding.tvPrescriptionDescription.text = prescriptionDescription

                holder.itemView.setOnClickListener {

                }
            }
        }
    }

    inner class PrescriptionViewHolder(val binding: ItemRecipeBinding)
        :RecyclerView.ViewHolder(binding.root)

}