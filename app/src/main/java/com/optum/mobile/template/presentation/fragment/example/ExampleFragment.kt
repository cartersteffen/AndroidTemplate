package com.optum.mobile.template.presentation.fragment.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.optum.mobile.template.R
import com.optum.mobile.template.core.utils.gone
import com.optum.mobile.template.core.utils.visible
import com.optum.mobile.template.data.model.ExampleResponse
import com.optum.mobile.template.di.component.ExampleComponent
import com.optum.mobile.template.presentation.fragment.base.BaseFragment
import com.optum.mobile.template.presentation.fragment.base.NavigationBarFragment
import com.optum.mobile.template.presentation.state.DataState
import kotlinx.android.synthetic.main.fragment_example.*
import javax.inject.Inject

class ExampleFragment : NavigationBarFragment() {

    @Inject
    lateinit var viewModelFactory: ExampleViewModelFactory
    private lateinit var viewModel: ExampleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.getComponent(ExampleComponent::class.java).inject(this)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(ExampleViewModel::class.java)

        viewModel.fetchExampleData().observe(this, Observer {
            if (it != null) {
                handleExampleDataState(it.status, it.data, it.message)
            }
        })

        return inflater.inflate(R.layout.fragment_example, container, false)
    }

    private fun handleExampleDataState(
        status: DataState.Status,
        data: ExampleResponse?,
        message: String?
    ) {
        when (status) {
            DataState.Status.LOADING -> {
                example_title.gone()
                example_message.gone()
            }
            DataState.Status.SUCCESS -> {
                example_title.text = data?.title
                example_message.text = data?.message
                example_title.visible()
                example_message.visible()
            }
            DataState.Status.ERROR -> {
                example_title.text = "ERROR"
                example_message.text = message
                example_title.visible()
                example_message.visible()
            }
        }
    }

    override fun getNavigationFeature(): NavigationFeature {
        TODO("Not yet implemented")
    }

    override fun getPageName(): String {
        return "Example Fragment"
    }
}