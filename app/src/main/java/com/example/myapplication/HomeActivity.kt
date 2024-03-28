package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myapplication.databinding.ActivityHomeBinding

class HomeActivity : ComponentActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var rvAdapter: PopularAdapter
    private lateinit var dataList: ArrayList<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        binding.search.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

    }

    private fun setUpRecyclerView() {
        dataList=ArrayList()


        binding.rvPopular.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var db= Room.databaseBuilder(this@HomeActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        var daoObject=db.getDao()
        var recipes=daoObject.getAll()
        for (i in recipes.indices){
            if (recipes[i]!!.category.contains("Popular")){
                dataList.add(recipes[i]!!)
            }
            rvAdapter= PopularAdapter(dataList, this)
            binding.rvPopular.adapter=rvAdapter
        }
    }
}