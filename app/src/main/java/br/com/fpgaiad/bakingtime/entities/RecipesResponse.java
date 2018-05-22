
package br.com.fpgaiad.bakingtime.entities;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipesResponse implements Serializable
{

    @SerializedName("recipes")
    @Expose
    private List<Recipe> recipes = null;
    private final static long serialVersionUID = -7274210412365302029L;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

}
