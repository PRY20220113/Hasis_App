package com.upc.hasis_app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.ItemSpecialityBinding
import com.upc.hasis_app.domain.entity.Speciality
import com.upc.hasis_app.presentation.view_model.SpecialityViewModel

class SpecialityAdapter(private val specialities: List<Speciality>, private val navController: NavController) :
    RecyclerView.Adapter<SpecialityAdapter.SpecialityViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialityViewHolder {
        val binding = ItemSpecialityBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecialityViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return specialities.size
    }

    inner class SpecialityViewHolder(val binding: ItemSpecialityBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: SpecialityViewHolder, position: Int) {
        with(holder){
            with(specialities[position]) {
                binding.tvSpecialityName.text = name
                binding.ivSpeciality.setImageResource(R.drawable.ic_eye)
                binding.btnGo.setOnClickListener {
                    //holder.itemView.findNavController().navigate(R.id.go_to_speciality_patient_recipes)
                    val bundle= bundleOf("specialityId" to this.specialityId, "specialityName" to this.name)
                    navController.navigate(R.id.go_to_speciality_patient_recipes, bundle)
          //          navController!!.navigate(R.id.go_to_speciality_patient_recipes)
                }
            }
        }
    }
}