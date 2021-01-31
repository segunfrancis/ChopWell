package com.project.segunfrancis.chopwell.presentation.ui.meal_list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.segunfrancis.chopwell.R

class MealListFragment : Fragment() {

    companion object {
        fun newInstance() = MealListFragment()
    }

    private lateinit var viewModel: MealListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.meal_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MealListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}