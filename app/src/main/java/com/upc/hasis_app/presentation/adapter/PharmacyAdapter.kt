package com.upc.hasis_app.presentation.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.ItemSpecialityBinding
import com.upc.hasis_app.domain.entity.Pharmacy

class PharmacyAdapter(private val pharmacies: List<Pharmacy>,private val onClickListener: View.OnClickListener) :
    RecyclerView.Adapter<PharmacyAdapter.PharmacyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacyViewHolder {
        val binding = ItemSpecialityBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PharmacyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pharmacies.size
    }

    inner class PharmacyViewHolder(val binding: ItemSpecialityBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: PharmacyViewHolder, position: Int) {
        with(holder){
            with(pharmacies[position]) {
                binding.tvSpecialityName.text = name
                binding.btnGo.setOnClickListener(onClickListener)
//                {
//                    val gmmIntentUri =
//                        Uri.parse("google.navigation:w=Taronga+Zoo,+Sydney+Australia")
//                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//                    mapIntent.setPackage("com.google.android.apps.maps")
//                    startActivity(mapIntent)
//                }
            }
        }
    }
}