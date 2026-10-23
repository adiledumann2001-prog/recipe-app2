package com.student.recipes

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val container = findViewById<LinearLayout>(R.id.recipeContainer)

        try {
            val inputStream: InputStream = assets.open("recipes.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val recipe = jsonArray.getJSONObject(i)
                val name = recipe.getString("name")
                val category = recipe.getString("category")
                val calories = recipe.getInt("calories") // Kalori verisini çekiyoruz
                val ingredients = recipe.getJSONArray("ingredients")
                
                var ingredientsText = ""
                for (j in 0 until ingredients.length()) {
                    ingredientsText += "- ${ingredients.getString(j)}\n"
                }

                // Tarif kartı tasarımı
                val recipeView = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(40, 40, 40, 40)
                    setBackgroundColor(Color.WHITE)
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply { setMargins(0, 0, 0, 30) }
                }

                val nameText = TextView(this).apply {
                    text = name
                    textSize = 22f
                    setTextColor(Color.BLACK)
                    setTypeface(null, android.graphics.Typeface.BOLD)
                }

                // Hocanın istediği kalori göstergesi
                val categoryCalText = TextView(this).apply {
                    text = "Category: $category | Calories: $calories kcal"
                    textSize = 16f
                    setTextColor(Color.parseColor("#E64A19")) // Turuncu renk
                    setPadding(0, 10, 0, 10)
                }

                val ingredientsTitle = TextView(this).apply {
                    text = "Ingredients:"
                    textSize = 16f
                    setTypeface(null, android.graphics.Typeface.BOLD)
                }

                val ingredientsList = TextView(this).apply {
                    text = ingredientsText
                    textSize = 14f
                    setTextColor(Color.DKGRAY)
                }

                recipeView.addView(nameText)
                recipeView.addView(categoryCalText)
                recipeView.addView(ingredientsTitle)
                recipeView.addView(ingredientsList)

                container.addView(recipeView)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
