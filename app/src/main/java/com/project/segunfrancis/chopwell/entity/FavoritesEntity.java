package com.project.segunfrancis.chopwell.entity;

public class FavoritesEntity {
    private String favoriteId;
    private String favoriteMealName;
    private String favoriteDescription;
    private String favoriteRecipe;
    private String favoriteImageURL;
    private String favoritePreparation;
    private String favoriteUserId;
    private String favoriteUserEmail;

    public FavoritesEntity() {
    }

    public FavoritesEntity(String favoriteId, String favoriteMealName,
                           String favoriteDescription, String favoriteRecipe,
                           String favoriteImageURL, String favoritePreparation,
                           String favoriteUserId) {
        this.favoriteId = favoriteId;
        this.favoriteMealName = favoriteMealName;
        this.favoriteDescription = favoriteDescription;
        this.favoriteRecipe = favoriteRecipe;
        this.favoriteImageURL = favoriteImageURL;
        this.favoritePreparation = favoritePreparation;
        this.favoriteUserId = favoriteUserId;
    }

    public String getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getFavoriteMealName() {
        return favoriteMealName;
    }

    public void setFavoriteMealName(String favoriteMealName) {
        this.favoriteMealName = favoriteMealName;
    }

    public String getFavoriteDescription() {
        return favoriteDescription;
    }

    public void setFavoriteDescription(String favoriteDescription) {
        this.favoriteDescription = favoriteDescription;
    }

    public String getFavoriteRecipe() {
        return favoriteRecipe;
    }

    public void setFavoriteRecipe(String favoriteRecipe) {
        this.favoriteRecipe = favoriteRecipe;
    }

    public String getFavoriteImageURL() {
        return favoriteImageURL;
    }

    public void setFavoriteImageURL(String favoriteImageURL) {
        this.favoriteImageURL = favoriteImageURL;
    }

    public String getFavoritePreparation() {
        return favoritePreparation;
    }

    public void setFavoritePreparation(String favoritePreparation) {
        this.favoritePreparation = favoritePreparation;
    }

    public String getFavoriteUserId() {
        return favoriteUserId;
    }

    public void setFavoriteUserId(String favoriteUserId) {
        this.favoriteUserId = favoriteUserId;
    }

    public String getFavoriteUserEmail() {
        return favoriteUserEmail;
    }

    public void setFavoriteUserEmail(String favoriteUserEmail) {
        this.favoriteUserEmail = favoriteUserEmail;
    }
}
