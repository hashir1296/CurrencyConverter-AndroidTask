package com.hashir.currencyconverter.presentation.charts

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hashir.currencyconverter.R
import com.hashir.currencyconverter.databinding.FragmentChartsBinding
import com.hashir.currencyconverter.presentation.rates.TextChangeCallback
import com.hashir.currencyconverter.utils.Constants
import com.hashir.currencyconverter.utils.convertMillisToFormattedDate
import com.patrykandpatrick.vico.core.axis.Axis
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.values.ChartValuesManager
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ChartFragment : Fragment(), CurrencySelectedCallback {

    private lateinit var binding: FragmentChartsBinding
    private var bottomSheetSupportedCurrencies: BottomSheetSupportedCurrencies? = null

    private val viewModel by lazy {
        ViewModelProvider(this)[ChartsViewModel::class.java]
    }

    companion object {
        fun newInstance() = ChartFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentChartsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeData()
        setupListeners()
    }

    private fun setupViews() {
        binding.tvCurrencyCode.text = Constants.CURRENCY_CODE
        binding.etInputRate.setText(viewModel.chartScreenData.value.currencyValue.toString())
    }

    private fun setChart(list: List<ChartsResponseModel.Result?>) {
        lifecycleScope.launch(Dispatchers.Default) {
            if (list.isEmpty()) {
                Toast.makeText(requireContext(), "No data to show", Toast.LENGTH_SHORT).show()
            } else {
                val listOfEntries = arrayListOf<FloatEntry>()
                list.forEach {
                    listOfEntries.add(entryOf(it?.t!!.toFloat(), it.c!!.toFloat().times(viewModel.chartScreenData.value.currencyValue)?.toFloat()!!))
                }
                val chartEntryModelProducer = ChartEntryModelProducer(listOfEntries)
                binding.chartView.entryProducer = chartEntryModelProducer
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is ChartsViewModel.ChartScreenState.SupportedCodesAPISuccess -> {
                        binding.progressBar.visibility = View.GONE
                    }

                    is ChartsViewModel.ChartScreenState.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.GONE
                    }

                    ChartsViewModel.ChartScreenState.Idle -> {
                        binding.progressBar.visibility = View.GONE
                    }

                    ChartsViewModel.ChartScreenState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ChartsViewModel.ChartScreenState.ChartDataAPISuccess -> {
                        setChart(it.list)
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.chartScreenData.collect {
                binding.tvTargetCurrency.text = it.selectedCurrency
                toggleButtonColors(timePeriod = it.timePeriod)

                viewModel.getChartData()
            }
        }
    }

    private fun setupListeners() {
        binding.cvTargetCurrency.setOnClickListener { showBottomSheetForSupportedCurrencies() }

        binding.btnOneDay.setOnClickListener {
            viewModel.updateSelectedTimePeriod(TimePeriod.ONE_DAY)
        }
        binding.btnOneMonth.setOnClickListener {
            viewModel.updateSelectedTimePeriod(TimePeriod.ONE_MONTH)
        }
        binding.btnOneYear.setOnClickListener {
            viewModel.updateSelectedTimePeriod(TimePeriod.ONE_YEAR)
        }

        binding.etInputRate.addTextChangedListener(object :TextChangeCallback{
            override fun afterTextChanged(p0: Editable?) {
                val input: String? = p0?.toString()
                if (input.isNullOrEmpty().not()) {
                    viewModel.updateInputValue(input?.toDouble())
                }
            }
        })
    }

    private fun showBottomSheetForSupportedCurrencies() {
        if (bottomSheetSupportedCurrencies == null) {
            bottomSheetSupportedCurrencies = BottomSheetSupportedCurrencies.newInstance(
                codesList = viewModel.supportedCodes, currencySelectedCallback = this
            )
        }
        bottomSheetSupportedCurrencies?.show(
            childFragmentManager, BottomSheetSupportedCurrencies::class.java.simpleName
        )
    }

    private fun toggleButtonColors(timePeriod: TimePeriod) {
        when (timePeriod) {
            TimePeriod.ONE_DAY -> {
                binding.btnOneDay.setTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.white
                    )
                )
                binding.btnOneDay.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(), R.color.light_blue
                    )
                )

                binding.btnOneMonth.setTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.black
                    )
                )
                binding.btnOneMonth.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(), R.color.white
                    )
                )
                binding.btnOneYear.setTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.black
                    )
                )
                binding.btnOneYear.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(), R.color.white
                    )
                )
            }

            TimePeriod.ONE_MONTH -> {
                binding.btnOneMonth.setTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.white
                    )
                )
                binding.btnOneMonth.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(), R.color.light_blue
                    )
                )

                binding.btnOneYear.setTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.black
                    )
                )
                binding.btnOneYear.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(), R.color.white
                    )
                )
                binding.btnOneDay.setTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.black
                    )
                )
                binding.btnOneDay.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(), R.color.white
                    )
                )
            }

            TimePeriod.ONE_YEAR -> {
                binding.btnOneYear.setTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.white
                    )
                )
                binding.btnOneYear.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(), R.color.light_blue
                    )
                )

                binding.btnOneDay.setTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.black
                    )
                )
                binding.btnOneDay.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(), R.color.white
                    )
                )
                binding.btnOneMonth.setTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.black
                    )
                )
                binding.btnOneMonth.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(), R.color.white
                    )
                )
            }
        }

    }

    override fun onCurrencySelected(string: String) {
        viewModel.updateSelectedCurrency(string)
        bottomSheetSupportedCurrencies?.dismiss()
    }
}

/* private fun setChart(list: List<ChartsResponseModel.Result?>) {
        lifecycleScope.launch(Dispatchers.Default) {
            if (list.isEmpty()) {
                Toast.makeText(requireContext(), "No data to show", Toast.LENGTH_SHORT).show()
            } else {
                val data = list.map {
                    it?.t to it?.c?.toFloat()
                }.associate { (date, yValue) ->
                    convertMillisToFormattedDate(date!!)  to yValue!!
                }


                val xValuesToDates = data.keys.associateBy { it.toFloat() }
                val chartEntryModel = entryModelOf(xValuesToDates.keys.zip(data.values, ::entryOf))
                val dateTimeFormatter: DateTimeFormatter =
                    DateTimeFormatter.ofPattern("EEE, d MMM yy HH:mm:ss")
                val horizontalAxisValueFormatter =
                    AxisValueFormatter<AxisPosition.Vertical> { value, _ ->
                        val a = xValuesToDates[value]
                        val b = LocalDate.ofEpochDay(value.toLong()).format(dateTimeFormatter)
                        a?.toString() ?: b
                    }

                Axis.Builder<AxisPosition.Vertical>().title = "Some title"
                val chartEntryModelProducer = ChartEntryModelProducer(chartEntryModel.entries)

                binding.chartView.entryProducer = chartEntryModelProducer
            }
        }
    }*/