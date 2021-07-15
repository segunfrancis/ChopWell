package com.project.segunfrancis.chopwell.presentation.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.segunfrancis.chopwell.R
import com.project.segunfrancis.chopwell.presentation.utils.Result
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FavoriteFragment : Fragment() {

    val viewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allFavorites.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Loading -> { Timber.d("Loading favorites...") }
                is Result.Success -> { Timber.d("Success: ${result.data}") }
                is Result.Error -> { Timber.d("Error: ${result.error.localizedMessage}") }
            }
        }
    }
}
