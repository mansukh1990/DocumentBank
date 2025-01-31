package com.example.documentbank.DocumentBank.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.documentbank.DocumentBank.domain.ui.Screens.VehicleExpenseSettleScreen.vehicleExpenseSettlement
import com.example.documentbank.DocumentBank.domain.ui.Screens.documentBankScreen.DocumentBank
import com.example.documentbank.DocumentBank.domain.ui.Screens.login.LoginScreenNew

@Composable
fun DocumentBankNavigation() {

    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = loginScreen
    ) {
        composable(documentBankScreen) {
            DocumentBank(navHostController = navHostController)
        }
        composable(vehicleExpenseSettlementScreen) {
            vehicleExpenseSettlement()
        }
        composable(loginScreen) {
            LoginScreenNew(navHostController = navHostController)
        }
    }

}

const val documentBankScreen = "documentBankScreen"
const val vehicleExpenseSettlementScreen = "vehicleExpenseSettlementScreen"
const val loginScreen = "loginScreen"
