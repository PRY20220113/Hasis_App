package com.upc.hasis_app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upc.hasis_app.data.model.response.CrearRecipeResponse
import com.upc.hasis_app.databinding.ItemRecipeBinding


class PrescriptionAdapter(private val prescriptions: List<CrearRecipeResponse>)
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
                binding.tvMedicineName.text = product
                val medicineWeight = "$weight mg"
                binding.tvMedicineWeight.text = medicineWeight
                val days = "$cantTomas dias"
                binding.tvDays.text = days
                val prescriptionDescription = "$cant tomas cada $eachHour horas"
                binding.tvPrescriptionDescription.text = prescriptionDescription

                holder.itemView.setOnClickListener {

                }
            }
        }
    }

    inner class PrescriptionViewHolder(val binding: ItemRecipeBinding)
        :RecyclerView.ViewHolder(binding.root)

}