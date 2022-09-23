package com.upc.hasis_app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.ItemMedicineBinding
import com.upc.hasis_app.databinding.ItemRecipeBinding
import com.upc.hasis_app.domain.entity.Medicine
import com.upc.hasis_app.presentation.ui.medical_recipe.FragmentMedicalRecipeDirections
import com.upc.hasis_app.presentation.ui.medical_recipe.RegisterRecipeFragmentDirections
import com.upc.hasis_app.presentation.ui.patient.PatientConsultFragmentDirections
import com.upc.hasis_app.presentation.ui.patient.PatientDetailFragmentDirections
import com.upc.hasis_app.presentation.view_model.RegisterRecipeViewModel


class MedicineAdapter(private val prescriptions: List<Medicine>, private val viewModel : RegisterRecipeViewModel)
    :RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding = ItemMedicineBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)



        return MedicineViewHolder(binding)
    }

    override fun getItemCount() = prescriptions.size

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        with(holder){
            with(prescriptions[position]) {
                binding.tvMedicineName.text = name
                val medicineWeight = "$weight mg"
                binding.tvMedicineWeight.text = medicineWeight
                val days = "$prescribedDays dias"
                binding.tvDays.text = days
                val prescriptionDescription = "$quantity tomas cada $eachHour horas"
                binding.tvPrescriptionDescription.text = prescriptionDescription

                binding.btnUpdate.setOnClickListener {
                    viewModel.eraseMedicine(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, prescriptions.size)
                }

            }
        }
    }

    inner class MedicineViewHolder(val binding: ItemMedicineBinding)
        :RecyclerView.ViewHolder(binding.root)

}