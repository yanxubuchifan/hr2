package com.cnpc.myapplication

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cnpc.myapplication.databinding.ActivityMainBinding
import android.content.res.AssetManager
import java.io.InputStream
import java.io.IOException
import org.json.JSONArray
import org.json.JSONException
import android.content.ContentValues
import android.widget.Toast
import com.cnpc.myapplication.DatabaseHelper.Companion.TABLE_NAME

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)
        val db = databaseHelper.writableDatabase // 获取可写数据库，确保 onCreate 方法被调用
//        readJsonFile()

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun readJsonFile() {
        var jsonArray: JSONArray? = null
        try {
            val assetManager: AssetManager = assets
            val inputStream: InputStream = assetManager.open("data.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)
            jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val oneinfo = jsonArray.getJSONObject(i)
                println(oneinfo)
                insertData(oneinfo)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            println("解析 JSON 数据时出错: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun insertData(jsonObject: org.json.JSONObject) {
        val db = databaseHelper.writableDatabase
        val values = ContentValues()
        try {
            values.put(DatabaseHelper.COLUMN_NAME, jsonObject.getString("oneinfo_name"))
            values.put(DatabaseHelper.COLUMN_SEX, jsonObject.getString("oneinfo_sex"))
            values.put(DatabaseHelper.COLUMN_BIRTHDAY, jsonObject.getString("oneinfo_birthday"))
            values.put(DatabaseHelper.COLUMN_HEADPIC, jsonObject.getString("oneinfo_headpic"))
            values.put(DatabaseHelper.COLUMN_NATIONALITY, jsonObject.getString("oneinfo_nationality"))
            values.put(DatabaseHelper.COLUMN_NATIVEPLACE, jsonObject.getString("oneinfo_nativeplace"))
            values.put(DatabaseHelper.COLUMN_BIRTHPLACE, jsonObject.getString("oneinfo_birthplace"))
            values.put(DatabaseHelper.COLUMN_DATE_OF_CPC, jsonObject.getString("oneinfo_date_of_CPC"))
            values.put(DatabaseHelper.COLUMN_DATE_OF_WORK, jsonObject.getString("oneinfo_date_of_work"))
            values.put(DatabaseHelper.COLUMN_HEALTH_STATUS, jsonObject.getString("oneinfo_health_status"))
            values.put(DatabaseHelper.COLUMN_TECHNICAL_POSITION, jsonObject.getString("oneinfo_technical_position"))
            values.put(DatabaseHelper.COLUMN_TALENT, jsonObject.getString("oneinfo_talent"))
            values.put(DatabaseHelper.COLUMN_FULL_TIME_SCHOOLING, jsonObject.getString("oneinfo_full_time_schooling"))
            values.put(DatabaseHelper.COLUMN_SCHOOL_AND_MAJOR, jsonObject.getString("oneinfo_School_and_Major"))
            values.put(DatabaseHelper.COLUMN_INSERVICE_EDUCATION, jsonObject.getString("oneinfo_inservice_education"))
            values.put(DatabaseHelper.COLUMN_SCHOOL_AND_MAJOR2, jsonObject.getString("oneinfo_School_and_Major2"))
            values.put(DatabaseHelper.COLUMN_CURRENT_POSITION, jsonObject.getString("oneinfo_current_position"))
            values.put(DatabaseHelper.COLUMN_PROPOSED_POSITION, jsonObject.getString("oneinfo_proposed_position"))
            values.put(DatabaseHelper.COLUMN_PROPOSED_REMOVAL, jsonObject.getString("oneinfo_proposed_removal"))
            values.put(DatabaseHelper.COLUMN_WORK_EXPERIENCE, jsonObject.getString("oneinfo_work_experience"))
            values.put(DatabaseHelper.COLUMN_REWARD, jsonObject.getString("oneinfo_reward"))
            values.put(DatabaseHelper.COLUMN_ANNUAL_ASSESSMENT, jsonObject.getString("oneinfo_annual_assessment"))
            values.put(DatabaseHelper.COLUMN_REASONS, jsonObject.getString("oneinfo_reasons"))

            val familyArray = jsonObject.getJSONArray("oneinfo_family")
            values.put(DatabaseHelper.COLUMN_FAMILY, familyArray.toString())

            val newRowId = db.insert(TABLE_NAME, null, values)
            if (newRowId != -1L) {
                Toast.makeText(this, "用户数据插入成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "用户数据插入失败", Toast.LENGTH_SHORT).show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } finally {
            // db.close()
        }
    }
}
