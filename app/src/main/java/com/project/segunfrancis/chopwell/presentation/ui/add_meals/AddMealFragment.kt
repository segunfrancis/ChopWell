package com.project.segunfrancis.chopwell.presentation.ui.add_meals

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.segunfrancis.chopwell.R

class AddMealFragment : Fragment() {

    companion object {
        fun newInstance() = AddMealFragment()
    }

    private lateinit var viewModel: AddMealViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_meal_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddMealViewModel::class.java)
        // TODO: Use the ViewModel
    }

}