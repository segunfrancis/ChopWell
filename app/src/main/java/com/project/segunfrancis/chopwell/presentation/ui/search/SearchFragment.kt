package com.project.segunfrancis.chopwell.presentation.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.segunfrancis.chopwell.databinding.FragmentSearchBinding
import com.project.segunfrancis.chopwell.presentation.ui.meal_list.MealAdapter
import com.project.segunfrancis.chopwell.presentation.utils.Result
import com.project.segunfrancis.chopwell.presentation.utils.showErrorMessage
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchAdapter: MealAdapter by lazy { MealAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchRecyclerView.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchMeal(newText)
                return false
            }
        })

        viewModel.searchMeal.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Loading -> {  }
                is Result.Error -> {
                    Timber.e(result.error)
                    result.error.localizedMessage?.let { errorMessage ->
                        requireView().showErrorMessage(
                            errorMessage,
                            requireContext()
                        )
                    }
                }
                is Result.Success -> { searchAdapter.submitList(result.data) }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
