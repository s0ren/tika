/*
 * Copyright 2015 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tika.metadata.microdata;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Property;
import static org.apache.tika.metadata.microdata.mdThing.PREFIX_MD;

/**
 *
 * @author sm
 * 
 * @see http://schema.org/Recipe
 */
public interface mdRecipe extends mdCreativeWork {
  
    //@overwrite
    public static final String NAMESPACE_URI_MD = "http://schema.org/Recipe";
    
    /**
     * The time it takes to actually cook the dish, in ISO 8601 duration format.
     */
    Property COOK_TIME = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "cookTime");

    /**
     * The method of cooking, such as Frying, Steaming, ...
     */
    Property COOKING_METHOD = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "cookingMethod");

    /**
     * Nutrition information about the recipe.
     */
    Property NUTRITION = Property.internalText(                                     //TODO Nutrition info
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "nutrition");

    /**
     * The length of time it takes to prepare the recipe, in ISO 8601 duration
     * format.
     */
    Property PREP_TIME = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "prepTime");

    /**
     * The category of the recipe—for example, appetizer, entree, etc.
     */
    Property RECIPE_CATEGORY = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "recipeCategory");

    /**
     * The cuisine of the recipe (for example, French or Ethiopian).
     */
    Property RECIPE_CUSINE = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "recipeCuisine");

    /**
     * A single ingredient used in the recipe, e.g. sugar, flour or garlic.
     * Supersedes ingredients.
     */
    Property RECIPE_INGREDIENT = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "recipeIngredient");

    /**
     * A step or instruction involved in making the recipe.
     */
    Property RECIPE_INSTRUCTIONS = Property.internalTextBag(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "recipeInstructions");

    /**
     * The quantity produced by the recipe (for example, number of people
     * served, number of servings, etc).
     */
    Property RECIPE_YIELD = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "recipeYield");

    /**
     * The total time it takes to prepare and cook the recipe, in ISO 8601
     * duration format.
     */
    Property TOTAL_TIME = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "totalTime");

}
