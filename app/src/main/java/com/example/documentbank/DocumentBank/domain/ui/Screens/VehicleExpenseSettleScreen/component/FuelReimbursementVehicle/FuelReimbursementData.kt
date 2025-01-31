package com.example.documentbank.DocumentBank.domain.ui.Screens.VehicleExpenseSettleScreen.component.FuelReimbursementVehicle

data class FuelReimbursementData(
    var openingKm: String,
    var closingKm: String,
    val totalKmRun: String,
    var gmapsKm: String,
    var approvedKm: String,
    var ratePerKm: String,
    var totalFuelAmount: String
)