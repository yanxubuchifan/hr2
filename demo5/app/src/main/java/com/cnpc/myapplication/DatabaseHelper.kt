package com.cnpc.myapplication

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "MyDatabase.db", null, 2) { // 提升数据库版本号

    companion object {
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "oneinfo_name"
        const val COLUMN_SEX = "oneinfo_sex"
        const val COLUMN_BIRTHDAY = "oneinfo_birthday"
        const val COLUMN_HEADPIC = "oneinfo_headpic"
        const val COLUMN_NATIONALITY = "oneinfo_nationality"
        const val COLUMN_NATIVEPLACE = "oneinfo_nativeplace"
        const val COLUMN_BIRTHPLACE = "oneinfo_birthplace"
        const val COLUMN_DATE_OF_CPC = "oneinfo_date_of_CPC"
        const val COLUMN_DATE_OF_WORK = "oneinfo_date_of_work"
        const val COLUMN_HEALTH_STATUS = "oneinfo_health_status"
        const val COLUMN_TECHNICAL_POSITION = "oneinfo_technical_position"
        const val COLUMN_TALENT = "oneinfo_talent"
        const val COLUMN_FULL_TIME_SCHOOLING = "oneinfo_full_time_schooling"
        const val COLUMN_SCHOOL_AND_MAJOR = "oneinfo_School_and_Major"
        const val COLUMN_INSERVICE_EDUCATION = "oneinfo_inservice_education"
        const val COLUMN_SCHOOL_AND_MAJOR2 = "oneinfo_School_and_Major2"
        const val COLUMN_CURRENT_POSITION = "oneinfo_current_position"
        const val COLUMN_PROPOSED_POSITION = "oneinfo_proposed_position"
        const val COLUMN_PROPOSED_REMOVAL = "oneinfo_proposed_removal"
        const val COLUMN_WORK_EXPERIENCE = "oneinfo_work_experience"
        const val COLUMN_REWARD = "oneinfo_reward"
        const val COLUMN_ANNUAL_ASSESSMENT = "oneinfo_annual_assessment"
        const val COLUMN_REASONS = "oneinfo_reasons"
        const val COLUMN_FAMILY = "oneinfo_family"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // 添加日志语句
        android.util.Log.d("DatabaseHelper", "onCreate method called, creating table $TABLE_NAME")
        db.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, $COLUMN_SEX TEXT, $COLUMN_BIRTHDAY TEXT, $COLUMN_HEADPIC TEXT, " +
                "$COLUMN_NATIONALITY TEXT, $COLUMN_NATIVEPLACE TEXT, $COLUMN_BIRTHPLACE TEXT, " +
                "$COLUMN_DATE_OF_CPC TEXT, $COLUMN_DATE_OF_WORK TEXT, $COLUMN_HEALTH_STATUS TEXT, " +
                "$COLUMN_TECHNICAL_POSITION TEXT, $COLUMN_TALENT TEXT, $COLUMN_FULL_TIME_SCHOOLING TEXT, " +
                "$COLUMN_SCHOOL_AND_MAJOR TEXT, $COLUMN_INSERVICE_EDUCATION TEXT, $COLUMN_SCHOOL_AND_MAJOR2 TEXT, " +
                "$COLUMN_CURRENT_POSITION TEXT, $COLUMN_PROPOSED_POSITION TEXT, $COLUMN_PROPOSED_REMOVAL TEXT, " +
                "$COLUMN_WORK_EXPERIENCE TEXT, $COLUMN_REWARD TEXT, $COLUMN_ANNUAL_ASSESSMENT TEXT, " +
                "$COLUMN_REASONS TEXT, $COLUMN_FAMILY TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 添加日志语句
        android.util.Log.d("DatabaseHelper", "onUpgrade method called, dropping and recreating table $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // 模糊查询用户信息的方法
    fun queryUserInfoByLike(name: String): Cursor? {
        val db = readableDatabase
        val selection = "$COLUMN_NAME LIKE?"
        val selectionArgs = arrayOf("%$name%")
        return db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)
    }
}
