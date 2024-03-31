package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myapplication.databinding.ActivityCategoryBinding

class CategoryActivity : ComponentActivity() {
    private lateinit var rvAdapter: CategoryAdapter
    private lateinit var dataList: ArrayList<Recipe>
    private val binding by lazy {
        ActivityCategoryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = intent.getStringExtra("TITTLE")
        setUpRecyclerView()

        binding.goBackHome.setOnClickListener{
            finish()
        }
    }

    private fun setUpRecyclerView() {
        dataList=ArrayList()

        binding.rvCategory.layoutManager=
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var db= Room.databaseBuilder(this@CategoryActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        var daoObject=db.getDao()
        var recipes=daoObject.getAll()
        for (i in recipes.indices){
            if (recipes[i]!!.category.contains(intent.getStringExtra("CATEGORY")!!)){
                dataList.add(recipes[i]!!)
            }
            rvAdapter= CategoryAdapter(dataList, this)
            binding.rvCategory.adapter=rvAdapter
        }
    }

private fun addRecipe(recipe: Recipe) {
        val db = Room.databaseBuilder(
            this@CategoryActivity,
            AppDatabase::class.java, "db_name"
        ).build()

        val daoObject = db.getDao()
        daoObject.insert(recipe)
        db.close()
    }

    private fun deleteRecipe(recipe: Recipe) {
        val db = Room.databaseBuilder(
            this@CategoryActivity,
            AppDatabase::class.java, "db_name"
        ).build()

        val daoObject = db.getDao()
        daoObject.delete(recipe)
        db.close()
    }
}
