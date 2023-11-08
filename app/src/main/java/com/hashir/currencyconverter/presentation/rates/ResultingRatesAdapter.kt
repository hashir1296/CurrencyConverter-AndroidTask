package com.hashir.currencyconverter.presentation.rates

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hashir.currencyconverter.R
import com.hashir.currencyconverter.databinding.HolderCurrencyItemBinding

class ResultingRatesAdapter(
    var context: Context,
) : RecyclerView.Adapter<ResultingRatesAdapter.ViewHolder>() {

    private var list = arrayListOf<CurrencyDomainModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderCurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(newList: List<CurrencyDomainModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: HolderCurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currency: CurrencyDomainModel) {
            binding.tvCurrency.text = currency.name

            binding.tvExchangedValue.text = currency.rate.toString()
            binding.tvBaseToTargetCurrency.text = currency.baseCurrencyToTarget
            binding.tvTargetToBaseCurrency.text = currency.targetCurrencyToBase
        }

    }
}