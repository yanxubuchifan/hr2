package com.cnpc.myapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cnpc.myapplication.databinding.FragmentHomeBinding
import com.cnpc.myapplication.DatabaseHelper
import com.cnpc.myapplication.OneInfoActivity
import com.cnpc.myapplication.PersonInfo
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var databaseHelper: DatabaseHelper // 添加 DatabaseHelper 实例

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // 初始化 databaseHelper
        databaseHelper = DatabaseHelper(requireContext())

        // 获取全局变量的值
//        新建一个按钮
        val button11: Button = binding.button2
//        新建一个输入框
        val input1: EditText = binding.editTextText
//        按钮点击事件
        button11.setOnClickListener { view ->
            println("第一次")
//            获取输入框内容
            println(input1.text)
            val allinfo = queryDatabaseByLike(input1.text.toString())
            println(allinfo.size)
            val intent = Intent(requireContext(), OneInfoActivity::class.java)
            intent.putExtra("name", "张三")
            startActivity(intent)
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 模糊查询并遍历打印结果的函数
    private fun queryDatabaseByLike(name: String): MutableList<PersonInfo> {
        val resultList = mutableListOf<PersonInfo>()
        val cursor = databaseHelper.queryUserInfoByLike(name)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // 从游标中获取各字段的值
                val oneinfo_name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME))
                val oneinfo_sex = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SEX))
                val oneinfo_birthday = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BIRTHDAY))
                val oneinfo_headpic = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_HEADPIC))
                val oneinfo_nationality = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NATIONALITY))
                val oneinfo_nativeplace = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NATIVEPLACE))
                val oneinfo_birthplace = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BIRTHPLACE))
                val oneinfo_date_of_CPC = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE_OF_CPC))
                val oneinfo_date_of_work = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE_OF_WORK))
                val oneinfo_health_status = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_HEALTH_STATUS))
                val oneinfo_technical_position = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TECHNICAL_POSITION))
                val oneinfo_talent = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TALENT))
                val oneinfo_full_time_schooling = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FULL_TIME_SCHOOLING))
                val oneinfo_School_and_Major = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SCHOOL_AND_MAJOR))
                val oneinfo_inservice_education = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_INSERVICE_EDUCATION))
                val oneinfo_School_and_Major2 = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SCHOOL_AND_MAJOR2))
                val oneinfo_current_position = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CURRENT_POSITION))
                val oneinfo_proposed_position = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PROPOSED_POSITION))
                val oneinfo_proposed_removal = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PROPOSED_REMOVAL))
                val oneinfo_work_experience = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_WORK_EXPERIENCE))
                val oneinfo_reward = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_REWARD))
                val oneinfo_annual_assessment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ANNUAL_ASSESSMENT))
                val oneinfo_reasons = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_REASONS))

                // 获取家庭信息的 JSON 字符串
                val familyJson = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FAMILY))
                val oneinfo_family = parseFamilyMembers(familyJson)

                // 创建 PersonInfo 对象
                val personInfo = PersonInfo(
                    oneinfo_name,
                    oneinfo_sex,
                    oneinfo_birthday,
                    oneinfo_headpic,
                    oneinfo_nationality,
                    oneinfo_nativeplace,
                    oneinfo_birthplace,
                    oneinfo_date_of_CPC,
                    oneinfo_date_of_work,
                    oneinfo_health_status,
                    oneinfo_technical_position,
                    oneinfo_talent,
                    oneinfo_full_time_schooling,
                    oneinfo_School_and_Major,
                    oneinfo_inservice_education,
                    oneinfo_School_and_Major2,
                    oneinfo_current_position,
                    oneinfo_proposed_position,
                    oneinfo_proposed_removal,
                    oneinfo_work_experience,
                    oneinfo_reward,
                    oneinfo_annual_assessment,
                    oneinfo_reasons,
                    oneinfo_family
                )

                resultList.add(personInfo)
            } while (cursor.moveToNext())
            cursor.close()
        } else {
            println("未找到相关用户")
        }
        return resultList
    }

    private fun parseFamilyMembers(familyJson: String): List<PersonInfo.FamilyMember> {
        val familyList = mutableListOf<PersonInfo.FamilyMember>()
        try {
            val familyArray = JSONArray(familyJson)
            for (i in 0 until familyArray.length()) {
                val familyObject = familyArray.getJSONObject(i)
                val relationship = familyObject.getString("oneinfo_family_mamber_relationship")
                val name = familyObject.getString("oneinfo_family_mamber_name")
                val birthday = familyObject.getString("oneinfo_family_mamber_birthday")
                val political = familyObject.getString("oneinfo_family_mamber_political")
                val position = familyObject.getString("oneinfo_family_mamber_position")
                val familyMember = PersonInfo.FamilyMember(relationship, name, birthday, political, position)
                familyList.add(familyMember)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return familyList
    }

}