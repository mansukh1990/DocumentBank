package com.example.documentbank.DocumentBank.domain.ui.Screens.VehicleExpenseSettleScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.documentbank.DocumentBank.domain.ui.Screens.VehicleExpenseSettleScreen.component.AdditionalACRunningCost.AdditionalACRunning
import com.example.documentbank.DocumentBank.domain.ui.Screens.VehicleExpenseSettleScreen.component.BtnSettle.BtnSettle
import com.example.documentbank.DocumentBank.domain.ui.Screens.VehicleExpenseSettleScreen.component.DriverSettlementReimbursement.DriverSettlementReimbursement
import com.example.documentbank.DocumentBank.domain.ui.Screens.VehicleExpenseSettleScreen.component.FuelReimbursementVehicle.FuelReimbursementVehicle
import com.example.documentbank.DocumentBank.domain.ui.Screens.VehicleExpenseSettleScreen.component.MountainRoad.MountainRoad


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun vehicleExpenseSettlement() {

    val state = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(state)
    ) {
        vehicleExpenseSettlementPreview()
    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
private fun vehicleExpenseSettlementPreview() {
        Column {
            FuelReimbursementVehicle()
            AdditionalACRunning()
            MountainRoad()
            DriverSettlementReimbursement()
            BtnSettle()
        }
    }

