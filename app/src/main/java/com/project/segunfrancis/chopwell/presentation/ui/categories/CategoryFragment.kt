package com.project.segunfrancis.chopwell.presentation.ui.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.segunfrancis.chopwell.R
import com.project.segunfrancis.chopwell.databinding.CategoryFragmentBinding
import com.project.segunfrancis.chopwell.presentation.utils.AppConstants.BEVERAGE_CATEGORY_NAME
import com.project.segunfrancis.chopwell.presentation.utils.AppConstants.BREAKFAST_CATEGORY_NAME
import com.project.segunfrancis.chopwell.presentation.utils.AppConstants.ENTREES_CATEGORY_NAME
import com.project.segunfrancis.chopwell.presentation.utils.AppConstants.MEAT_CATEGORY_NAME
import com.project.segunfrancis.chopwell.presentation.utils.AppConstants.PUDDINGS_CATEGORY_NAME
import com.project.segunfrancis.chopwell.presentation.utils.AppConstants.SIDE_DISHES_CATEGORY_NAME
import com.project.segunfrancis.chopwell.presentation.utils.AppConstants.SNACKS_CATEGORY_NAME
import com.project.segunfrancis.chopwell.presentation.utils.AppConstants.SOUPS_CATEGORY_NAME
import com.project.segunfrancis.chopwell.presentation.utils.loadImage
import com.project.segunfrancis.chopwell.presentation.utils.navigateTo

class CategoryFragment : Fragment(), View.OnClickListener {

    private var _binding: CategoryFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CategoryFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadCategoryImages(binding)
        setClickListeners(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.beveragesCard -> {
                navigateTo(
                    CategoryFragmentDirections.actionCategoryFragmentToMealListFragment(
                        BEVERAGE_CATEGORY_NAME
                    )
                )
            }
            binding.breakfastCard -> {
                navigateTo(
                    CategoryFragmentDirections.actionCategoryFragmentToMealListFragment(
                        BREAKFAST_CATEGORY_NAME
                    )
                )
            }
            binding.entreesCard -> {
                navigateTo(
                    CategoryFragmentDirections.actionCategoryFragmentToMealListFragment(
                        ENTREES_CATEGORY_NAME
                    )
                )
            }
            binding.meatsCard -> {
                navigateTo(
                    CategoryFragmentDirections.actionCategoryFragmentToMealListFragment(
                        MEAT_CATEGORY_NAME
                    )
                )
            }
            binding.puddingsCard -> {
                navigateTo(
                    CategoryFragmentDirections.actionCategoryFragmentToMealListFragment(
                        PUDDINGS_CATEGORY_NAME
                    )
                )
            }
            binding.sideDishesCard -> {
                navigateTo(
                    CategoryFragmentDirections.actionCategoryFragmentToMealListFragment(
                        SIDE_DISHES_CATEGORY_NAME
                    )
                )
            }
            binding.snacksCard -> {
                navigateTo(
                    CategoryFragmentDirections.actionCategoryFragmentToMealListFragment(
                        SNACKS_CATEGORY_NAME
                    )
                )
            }
            binding.soupsCard -> {
                navigateTo(
                    CategoryFragmentDirections.actionCategoryFragmentToMealListFragment(
                        SOUPS_CATEGORY_NAME
                    )
                )
            }
        }
    }

    private fun setClickListeners(listener: View.OnClickListener) {
        binding.beveragesCard.setOnClickListener(listener)
        binding.breakfastCard.setOnClickListener(listener)
        binding.entreesCard.setOnClickListener(listener)
        binding.meatsCard.setOnClickListener(listener)
        binding.puddingsCard.setOnClickListener(listener)
        binding.sideDishesCard.setOnClickListener(listener)
        binding.snacksCard.setOnClickListener(listener)
        binding.soupsCard.setOnClickListener(listener)
    }

    private fun loadCategoryImages(imageBinding: CategoryFragmentBinding) {
        with(imageBinding) {
            imageBeverages.loadImage(R.drawable.chapman)
            imageBreakfast.loadImage(R.drawable.breakfast_image)
            imageEntrees.loadImage(R.drawable.entree_image)
            imageMeats.loadImage(R.drawable.meat_image)
            imagePuddings.loadImage(R.drawable.pudding_image)
            imageSideDishes.loadImage(R.drawable.side_dish)
            imageSnacks.loadImage(R.drawable.snack_image)
            imageSoupsAndStews.loadImage(R.drawable.food_delivery)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
