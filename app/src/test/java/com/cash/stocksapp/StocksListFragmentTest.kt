package com.cash.stocksapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Unit test for the [StocksListFragment].
 */
@RunWith(AndroidJUnit4::class)
class StocksListFragmentTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Throws(Exception::class)
    @Before
    fun setup() {
    }

    @Throws(Exception::class)
    @After
    fun tearDown() {
    }

    @Test
    fun changingViewModelValue_ShouldSetListViewItems() {
        val scenario = launchFragmentInContainer()
        scenario.onFragment {
            // perform UI tests here
        }
    }

    private fun launchFragmentInContainer(): FragmentScenario<StocksListFragment> {
        val mockNavController = mockk<NavController>()
        val scenario = launchFragment<StocksListFragment>(
            fragmentArgs = null,
            themeResId = R.style.Theme_CashStocksApp
        ).onFragment { fragment ->
            fragment.viewLifecycleOwnerLiveData.observeForever { viewLifeCycleOwner ->
                viewLifeCycleOwner?.let {
                    Navigation.setViewNavController(fragment.requireView(), mockNavController)
                }
            }
        }.moveToState(Lifecycle.State.RESUMED)
        return scenario
    }
}
