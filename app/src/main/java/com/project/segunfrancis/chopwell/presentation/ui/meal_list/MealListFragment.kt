package com.project.segunfrancis.chopwell.presentation.ui.meal_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.project.segunfrancis.chopwell.databinding.MealListFragmentBinding
import com.project.segunfrancis.chopwell.presentation.utils.showSuccessMessage

class MealListFragment : Fragment() {

    private val args: MealListFragmentArgs by navArgs()
    private var _binding: MealListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MealListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.showSuccessMessage(args.categoryName, requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}