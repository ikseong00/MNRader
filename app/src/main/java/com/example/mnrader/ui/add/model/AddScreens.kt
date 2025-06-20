package com.example.mnrader.ui.add.viewmodel

sealed class AddScreens(val route: String) {
    object SelectType : AddScreens("select_type")
    object AddInfo : AddScreens("register_info")
    object AnimalType : AddScreens("animal_type")
    object ReportOrLost : AddScreens("report_or_lost")
    object SubmitSuccess : AddScreens("submit_success")
}

