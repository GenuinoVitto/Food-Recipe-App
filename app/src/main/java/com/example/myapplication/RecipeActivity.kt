package com.example.myapplication

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityRecipeBinding

class RecipeActivity : ComponentActivity() {
    private lateinit var binding: ActivityRecipeBinding
    var imgCrop = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemImage)
        binding.tittle.text = intent.getStringExtra("tittle")
        binding.stepData.text = intent.getStringExtra("des")

        var ing = intent.getStringExtra("ing")?.split("\n".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        binding.time.text = ing?.get(0)
        for (i in 1 until ing!!.size){
            binding.ingData.text =
                """${binding.ingData.text} 🟢 ${ing[i]}
                    
                """.trimIndent()

        }
        binding.step.background = null
        binding.step.setTextColor(getColor(R.color.black))
        binding.step.setOnClickListener {
            binding.step.setBackgroundResource(R.drawable.btn_ing)
            binding.step.setTextColor(getColor(R.color.white))
            binding.ing.setTextColor(getColor(R.color.black))
            binding.ing.background = null
            binding.stepScroll.visibility = View.VISIBLE
            binding.ingScroll.visibility = View.GONE
        }

        binding.ing.setOnClickListener {
            binding.ing.setBackgroundResource(R.drawable.btn_ing)
            binding.ing.setTextColor(getColor(R.color.white))
            binding.step.setTextColor(getColor(R.color.black))
            binding.step.background = null
            binding.stepScroll.visibility = View.GONE
            binding.ingScroll.visibility = View.VISIBLE
        }

        binding.fullScreen.setOnClickListener {
            if (imgCrop){
                binding.itemImage.scaleType = ImageView.ScaleType.FIT_CENTER
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemImage)
                binding.fullScreen.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
                binding.shade.visibility = View.GONE
                imgCrop = !imgCrop
            } else {
                binding.itemImage.scaleType = ImageView.ScaleType.CENTER_CROP
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemImage)
                binding.fullScreen.setColorFilter(null)
                binding.shade.visibility = View.GONE
                imgCrop = !imgCrop
            }
        }

//        binding.btnDeleteRecipe.setOnClickListener {
//            // Show a confirmation dialog before deleting the recipe
//            AlertDialog.Builder(this)
//                .setTitle("Delete Recipe")
//                .setMessage("Are you sure you want to delete this recipe?")
//                .setPositiveButton("Delete") { dialog, _ ->
//                    // Perform the delete operation
//                    deleteRecipe(intent.getParcelableExtra("recipe")!!)
//                    // Close the activity after deleting the recipe
//                    finish()
//                }
//                .setNegativeButton("Cancel") { dialog, _ ->
//                    dialog.dismiss()
//                }
//                .show()
//        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}