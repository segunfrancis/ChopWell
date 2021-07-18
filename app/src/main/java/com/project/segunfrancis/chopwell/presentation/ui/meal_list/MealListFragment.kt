package com.project.segunfrancis.chopwell.presentation.ui.meal_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.segunfrancis.chopwell.databinding.MealListFragmentBinding
import com.project.segunfrancis.chopwell.presentation.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MealListFragment : Fragment() {

    private val args: MealListFragmentArgs by navArgs()
    private val viewModel: MealListViewModel by viewModel()
    private val mealAdapter: MealAdapter by lazy { MealAdapter() }
    private var _binding: MealListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MealListFragmentBinding.inflate(inflater)
        viewModel.getAllMeals(args.categoryName)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mealListRecyclerView.apply {
            adapter = mealAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.allMeals.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.root.makeVisible()
                }
                is Result.Error -> {
                    Timber.e(result.error)
                    binding.progressBar.root.makeGone()
                    result.error.localizedMessage?.let {
                        requireView().showErrorMessage(
                            it, requireContext())
                    }
                }
                is Result.Success -> {
                    binding.progressBar.root.makeGone()
                    mealAdapter.submitList(result.data)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
