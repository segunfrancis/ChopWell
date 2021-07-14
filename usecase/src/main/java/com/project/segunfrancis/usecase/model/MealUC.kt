package com.project.segunfrancis.usecase.model

data class MealUC(
    var id: String = "",
    var category: String? = null,
    var mealName: String? = null,
    var imageURL: String? = null,
    var description: String? = null,
    var preparation: String? = null,
    var recipe: String? = null,
    var userId: String = "",
    var userEmail: String? = null,
    var queryMealName: String? = null
) {
    constructor() : this("", "", "", "", "", "", "", "")

    constructor(
        id: String, category: String?, mealName: String?,
        imageURL: String?, description: String?,
        preparation: String?, recipe: String?, queryMealName: String?
    ) :
            this(
                id,
                category,
                mealName,
                imageURL,
                description,
                preparation,
                recipe,
                "",
                "",
                queryMealName
            )

    constructor(
        category: String?, mealName: String?,
        imageURL: String?, description: String?,
        preparation: String?, recipe: String?
    ) :
            this("", category, mealName, imageURL, description, preparation, recipe, "", "", "")

    constructor(userId: String, userEmail: String?) :
            this("", "", "", "", "", "", "", userId, userEmail, "")
}