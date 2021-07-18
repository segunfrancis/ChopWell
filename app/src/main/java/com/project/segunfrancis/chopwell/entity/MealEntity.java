package com.project.segunfrancis.chopwell.entity;

import java.io.Serializable;
import java.util.Objects;

public class MealEntity implements Serializable {
    private String id;
    private String category;
    private String mealName;
    private String imageURL;
    private String description;
    private String preparation;
    private String recipe;
    private String userId;
    private String userEmail;
    private String queryMealName;

    public MealEntity() {
    }

    public MealEntity(String id, String category, String mealName,
                      String imageURL, String description,
                      String preparation, String recipe, String queryMealName) {
        this.id = id;
        this.category = category;
        this.mealName = mealName;
        this.imageURL = imageURL;
        this.description = description;
        this.preparation = preparation;
        this.recipe = recipe;
        this.queryMealName = queryMealName;
    }

    public MealEntity(String category, String mealName,
                      String imageURL, String description,
                      String preparation, String recipe) {
        this.category = category;
        this.mealName = mealName;
        this.imageURL = imageURL;
        this.description = description;
        this.preparation = preparation;
        this.recipe = recipe;
    }

    public MealEntity(String userId, String userEmail) {
        this.userId = userId;
        this.userEmail = userEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getQueryMealName() {
        return queryMealName;
    }

    public void setQueryMealName(String queryMealName) {
        this.queryMealName = queryMealName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealEntity that = (MealEntity) o;
        return id.equals(that.id) && category.equals(that.category) && mealName.equals(that.mealName) && imageURL.equals(that.imageURL) && description.equals(that.description) && preparation.equals(that.preparation) && recipe.equals(that.recipe) && userId.equals(that.userId) && userEmail.equals(that.userEmail) && queryMealName.equals(that.queryMealName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, mealName, imageURL, description, preparation, recipe, userId, userEmail, queryMealName);
    }
}
