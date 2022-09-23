package com.upc.hasis_app.presentation.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.ItemPharmacyBinding
import com.upc.hasis_app.databinding.ItemSpecialityBinding
import com.upc.hasis_app.domain.entity.Pharmacy

class PharmacyAdapter(private val pharmacies: List<Pharmacy>,val context: Context) :
    RecyclerView.Adapter<PharmacyAdapter.PharmacyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacyViewHolder {
        val binding = ItemPharmacyBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PharmacyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pharmacies.size
    }

    inner class PharmacyViewHolder(val binding: ItemPharmacyBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: PharmacyViewHolder, position: Int) {
        with(holder){
            with(pharmacies[position]) {
                binding.tvSpecialityName.text = name
                binding.btnGo.setOnClickListener {
                    val gmmIntentUri = Uri.parse("google.navigation:q=${this.location}&mode=w")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    context.startActivity(mapIntent)
                }
            }
        }
    }
}