package com.hashir.currencyconverter.presentation.rates

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hashir.currencyconverter.databinding.FragmentRatesBinding
import com.hashir.currencyconverter.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RatesFragment : Fragment() {
    private lateinit var binding: FragmentRatesBinding
    private lateinit var resultingRatesAdapter: ResultingRatesAdapter

    private val viewModel by lazy {
        ViewModelProvider(this)[RatesViewModel::class.java]
    }

    companion object {
        fun newInstance() = RatesFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRatesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observerData()
        setupClickListeners()
    }

    private fun setupView() {
        binding.tvCurrencyCode.text = Constants.CURRENCY_CODE
        resultingRatesAdapter = ResultingRatesAdapter(requireContext())
        binding.rvCurrencies.adapter = resultingRatesAdapter
        binding.etInputRate.setText(viewModel.inputAmount.toString())
    }

    private fun observerData() {
        lifecycleScope.launch {
            viewModel.state.collectLatest {
                when (it) {
                    is RatesViewModel.CurrencyScreenState.APISuccess -> {
                        binding.progressBar.visibility = View.GONE
                        resultingRatesAdapter.setData(it.listOfCurrencies)
                    }

                    is RatesViewModel.CurrencyScreenState.Error -> {
                        //Show error state
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.GONE
                    }

                    RatesViewModel.CurrencyScreenState.Idle -> {
                        //Screen is idle
                        binding.progressBar.visibility = View.GONE
                    }

                    RatesViewModel.CurrencyScreenState.Loading -> {
                        //Show loading
                        binding.progressBar.visibility = View.VISIBLE

                    }
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.etInputRate.addTextChangedListener(object : TextChangeCallback {
            override fun afterTextChanged(p0: Editable?) {
                //Hit api
                val input: String? = p0?.toString()
                if (input.isNullOrEmpty().not()) {
                        viewModel.updateInputAmount(input?.toDouble())
                }
            }

        })
    }
}