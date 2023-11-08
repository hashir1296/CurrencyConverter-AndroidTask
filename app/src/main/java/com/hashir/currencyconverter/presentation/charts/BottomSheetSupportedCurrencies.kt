package com.hashir.currencyconverter.presentation.charts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hashir.currencyconverter.R
import com.hashir.currencyconverter.databinding.BottomSheetCurrenciesBinding


class BottomSheetSupportedCurrencies(
    private val currencySelectedCallback: CurrencySelectedCallback,
    private var codesList: List<String?>
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetCurrenciesBinding
    private var adapter: CurrenciesAdapter? = null


    companion object {
        fun newInstance(
            currencySelectedCallback: CurrencySelectedCallback, codesList: List<String?>
        ) = BottomSheetSupportedCurrencies(
            currencySelectedCallback = currencySelectedCallback, codesList = codesList
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppBottomSheetDialogTheme)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetCurrenciesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupClickListener()
    }

    private fun setupViews() {
        if (adapter == null) {
            adapter = CurrenciesAdapter(
                mContext = requireContext(),
                currencySelectedCallback = currencySelectedCallback,
                list = codesList
            )
        }
        binding.rvReferences.adapter = adapter
    }

    private fun setupClickListener() {

    }
}