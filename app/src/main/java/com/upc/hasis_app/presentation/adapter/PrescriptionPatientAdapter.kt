package com.upc.hasis_app.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.ItemRecipeBinding
import com.upc.hasis_app.domain.entity.Medicine
import com.upc.hasis_app.presentation.ui.medical_recipe.FragmentMedicalRecipeDirections
import com.upc.hasis_app.presentation.ui.medical_recipe.RegisterRecipeFragmentDirections
import com.upc.hasis_app.presentation.ui.patient.PatientConsultFragmentDirections
import com.upc.hasis_app.presentation.ui.patient.PatientDetailFragmentDirections
import com.upc.hasis_app.presentation.view_model.MedicineStatus
import com.upc.hasis_app.presentation.view_model.MedicineViewModel


class PrescriptionPatientAdapter(private val prescriptions: List<Medicine>)
    :RecyclerView.Adapter<PrescriptionPatientAdapter.PrescriptionViewHolder>() {

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
                val days = "$prescribedDays dias"
                binding.tvDays.text = days
                val prescriptionDescription = "$quantity tomas cada $eachHour horas"
                binding.tvPrescriptionDescription.text = prescriptionDescription
                binding.btnUpdate.visibility = View.INVISIBLE
            }
        }
    }

    inner class PrescriptionViewHolder(val binding: ItemRecipeBinding)
        :RecyclerView.ViewHolder(binding.root)

}