package com.hashir.currencyconverter.presentation.charts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.hashir.currencyconverter.R

class CurrenciesAdapter(
    private var mContext: Context,
    var list: List<String?>,
    var currencySelectedCallback: CurrencySelectedCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private inner class ReferencesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            val parent: LinearLayout = itemView.findViewById(R.id.llParent)
            val tvCurrencyCode: MaterialTextView = itemView.findViewById(R.id.tvCurrencyCode)

            val code = list[position]

            code?.let { currencyCode ->
                tvCurrencyCode.text = currencyCode

                parent.setOnClickListener {
                    currencySelectedCallback.onCurrencySelected(currencyCode)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReferencesViewHolder(
            LayoutInflater.from(mContext)
                .inflate(R.layout.holder_supported_currency_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ReferencesViewHolder).bind(position)
    }

}