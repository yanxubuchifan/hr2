package com.cnpc.myapplication

class AllDao {
}

data class PersonInfo(
    val oneinfo_name: String,
    val oneinfo_sex: String,
    val oneinfo_birthday: String,
    val oneinfo_headpic: String,
    val oneinfo_nationality: String,
    val oneinfo_nativeplace: String,
    val oneinfo_birthplace: String,
    val oneinfo_date_of_CPC: String,
    val oneinfo_date_of_work: String,
    val oneinfo_health_status: String,
    val oneinfo_technical_position: String,
    val oneinfo_talent: String,
    val oneinfo_full_time_schooling: String,
    val oneinfo_School_and_Major: String,
    val oneinfo_inservice_education: String,
    val oneinfo_School_and_Major2: String,
    val oneinfo_current_position: String,
    val oneinfo_proposed_position: String,
    val oneinfo_proposed_removal: String,
    val oneinfo_work_experience: String,
    val oneinfo_reward: String,
    val oneinfo_annual_assessment: String,
    val oneinfo_reasons: String,
    val oneinfo_family: List<FamilyMember>
){
    data class FamilyMember(
        val oneinfo_family_mamber_relationship: String,
        val oneinfo_family_mamber_name: String,
        val oneinfo_family_mamber_birthday: String,
        val oneinfo_family_mamber_political: String,
        val oneinfo_family_mamber_position: String
    )
}