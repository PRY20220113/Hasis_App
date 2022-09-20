package com.upc.hasis_app.presentation.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.upc.hasis_app.domain.entity.Speciality

class SpecialitySpinnerAdapter(context: Context, resource: Int, objects: MutableList<Speciality>) :
    ArrayAdapter<Speciality>(context, resource, objects) {

    private var values = objects

    override fun getCount(): Int {
        return values.size
    }

    override fun getItem(position: Int): Speciality? {
        return values[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.text = values[position].name
        if (position == 0){ label.setTextColor(Color.GRAY); }
        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label =  super.getDropDownView(position, convertView, parent) as TextView
        label.text = values[position].name
        if (position == 0){ label.setTextColor(Color.GRAY); }
        return label
    }

    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }
}