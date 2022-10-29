package com.cash.stocksapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cash.stocksapp.adapters.StocksListAdapter
import com.cash.stocksapp.databinding.FragmentStocksListBinding
import com.cash.stocksapp.viewmodels.StocksFeedUiState
import com.cash.stocksapp.viewmodels.StocksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass that displays list of Stocks mostly.
 */
@AndroidEntryPoint
class StocksListFragment : Fragment() {

    private var _binding: FragmentStocksListBinding? = null
    private val binding get() = _binding!!

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    @Inject
    lateinit var stocksListAdapter: StocksListAdapter

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val viewModel: StocksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStocksListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        /**
         * Click on FAB to re-fetch the stocks and sometimes you might not get data but that's OK.
         * We also have provisioned how to handle Empty Response as well as Errors from backend.
         */
        binding.fetchStocksFab.apply {
            setOnClickListener { viewModel.refreshStocksFeed() }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bindData(viewModel.stocksFeedUiState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Binds the data from the ViewModel.
     * Receives the *StateFlow<StocksFeedUiState>* from ViewModel and *collects* the [StateFlow]
     * to process the underlying UiState.
     * The bindData() methods also determines what to do with all the views and how to render the
     * data into those views.
     * In addition, all the different states of the backend response are handled and based on the
     * response, the views visibility as well as rendering is adjusted.
     */
    private fun FragmentStocksListBinding.bindData(
        stocksFeedUiState: StateFlow<StocksFeedUiState>
    ) {
        // Init Adapter and RecyclerView
        stocksRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = stocksListAdapter
        }

        stocksListAdapter.clickListener.onItemClick = {
            // TODO: handle the onClick
        }

        // supply data to Adapter and also handle Empty, Error cases
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                stocksFeedUiState.collect { uiState ->

                    // when the backend response is malformed or contains error
                    if (!uiState.failureMessage.isNullOrEmpty()) {
                        toggleViewsVisibility(
                            loadingSpinnerVisible = uiState.isLoading,
                            errorTextVisible = true
                        )
                        errorTextView.text = uiState.failureMessage
                    } else if (uiState.stocks.isEmpty() && !uiState.isLoading) {
                        // when the backend response is 200 SUCCESS but data is empty
                        toggleViewsVisibility(
                            loadingSpinnerVisible = uiState.isLoading,
                            emptyListTextVisible = true
                        )
                    } else {
                        // Happy Path --- when the backend response contains the stocks data
                        toggleViewsVisibility(
                            loadingSpinnerVisible = uiState.isLoading,
                            stocksListVisible = true
                        )
                        stocksListAdapter.submitList(uiState.stocks)
                    }
                }
            }
        }
    }

    private fun toggleViewsVisibility(
        loadingSpinnerVisible: Boolean = true,
        stocksListVisible: Boolean = false,
        emptyListTextVisible: Boolean = false,
        errorTextVisible: Boolean = false
    ) {
        binding.apply {
            progressIndicator.isVisible = loadingSpinnerVisible
            stocksRecyclerView.isVisible = stocksListVisible
            emptyList.isVisible = emptyListTextVisible
            errorTextView.isVisible = errorTextVisible
        }
    }
}
