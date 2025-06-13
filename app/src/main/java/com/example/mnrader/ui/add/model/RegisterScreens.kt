package com.example.mnrader.ui.add.model

sealed class RegisterScreens(val route: String) {
    object SelectType : RegisterScreens("select_type")
    object RegisterInfo : RegisterScreens("register_info")
    object AnimalType : RegisterScreens("animal_type")
    object ReportOrLost : RegisterScreens("report_or_lost")
    object SubmitSuccess : RegisterScreens("submit_success")
}

