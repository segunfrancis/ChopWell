package com.example.computer.chopwell.model;

public class MealModel {
    private String id;
    private String category;
    private String mealName;
    private String imageURL;
    private String description;
    private String preparation;
    private String recipe;
    private String userId;
    private String userEmail;

    public MealModel() {
    }

    public MealModel(String id, String category, String mealName,
                     String imageURL, String description,
                     String preparation, String recipe) {
        this.id = id;
        this.category = category;
        this.mealName = mealName;
        this.imageURL = imageURL;
        this.description = description;
        this.preparation = preparation;
        this.recipe = recipe;
    }

    public MealModel(String category, String mealName,
                     String imageURL, String description,
                     String preparation, String recipe) {
        this.category = category;
        this.mealName = mealName;
        this.imageURL = imageURL;
        this.description = description;
        this.preparation = preparation;
        this.recipe = recipe;
    }

    public MealModel(String id, String category, String mealName,
                     String imageURL, String description,
                     String preparation, String recipe, String userId) {
        this.id = id;
        this.category = category;
        this.mealName = mealName;
        this.imageURL = imageURL;
        this.description = description;
        this.preparation = preparation;
        this.recipe = recipe;
        this.userId = userId;
    }

    public MealModel(String userId, String userEmail) {
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
}
