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

// 定义数据库辅助类，继承自 SQLiteOpenHelper


// 定义主活动类，继承自 AppCompatActivity
class MainActivity : AppCompatActivity() {
    // 声明 ActivityMainBinding 类型的变量 binding，用于绑定布局
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DatabaseHelper

    // 重写 onCreate 方法，该方法在活动创建时调用
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        // 使用 ActivityMainBinding 的 inflate 方法加载布局
        binding = ActivityMainBinding.inflate(layoutInflater)
        // 将加载的布局设置为活动的内容视图
        setContentView(binding.root)



        databaseHelper = DatabaseHelper(this)
        readJsonFile()
//        qingkongdb()

        // 通过 binding 获取 BottomNavigationView 实例
        val navView: BottomNavigationView = binding.navView
        // 通过 findNavController 方法获取导航控制器实例
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // 创建 AppBarConfiguration 对象，指定顶级目标的菜单 ID 集合
        // 这里的每个菜单 ID 代表一个顶级目标，在导航时会有相应的处理
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        // 将导航控制器和 AppBarConfiguration 关联到 ActionBar 上
        setupActionBarWithNavController(navController, appBarConfiguration)
        // 将 BottomNavigationView 和导航控制器关联起来
        // 这样点击 BottomNavigationView 中的菜单项时，会进行相应的导航操作
        navView.setupWithNavController(navController)
    }

    private fun readJsonFile() {
        var jsonArray: JSONArray? = null
        try {
            // 获取 AssetManager 实例
            val assetManager: AssetManager = assets
            // 打开 assets 目录下的 data.json 文件
            val inputStream: InputStream = assetManager.open("data.json")
            // 获取文件的大小
            val size: Int = inputStream.available()
            // 创建一个字节数组来存储文件内容
            val buffer = ByteArray(size)
            // 读取文件内容到字节数组
            inputStream.read(buffer)
            // 关闭输入流
            inputStream.close()
            // 将字节数组转换为字符串
            val jsonString = String(buffer, Charsets.UTF_8)
            // 解析 JSON 数据并赋值给全局变量
            jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val oneinfo = jsonArray.getJSONObject(i)
                println(oneinfo)
                // 调用数据库，逐条存储
                insertData(oneinfo)
            }
        } catch (e: IOException) {
            // 处理文件读取异常
            e.printStackTrace()
        } catch (e: JSONException) {
            println("解析 JSON 数据时出错: ${e.message}")
            e.printStackTrace()
        }
    }

    // 清空数据库
    private fun qingkongdb() {
        val db = databaseHelper.writableDatabase
        // 分别删除每个表中的数据
//        db.delete(DatabaseHelper.TABLE_NAME, null, null)
//        删除表
//        db.execSQL("DROP TABLE IF EXISTS users")
//        db.execSQL("DROP TABLE IF EXISTS user_family")
//        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
//        db.onCreate(db)
//        db.close()
    }

    private fun insertData(jsonObject: org.json.JSONObject) {
        val db = databaseHelper.writableDatabase
        val values = ContentValues()
        try {
            // 插入用户信息
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

            // 将家庭信息以 JSON 字符串的形式插入
            val familyArray = jsonObject.getJSONArray("oneinfo_family")
            values.put(DatabaseHelper.COLUMN_FAMILY, familyArray.toString())

            val newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values)
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