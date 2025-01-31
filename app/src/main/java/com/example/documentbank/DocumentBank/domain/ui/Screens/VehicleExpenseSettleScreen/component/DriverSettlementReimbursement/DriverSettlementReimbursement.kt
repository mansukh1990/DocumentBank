package com.example.documentbank.DocumentBank.domain.ui.Screens.VehicleExpenseSettleScreen.component.DriverSettlementReimbursement

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentbank.R

@Composable
fun DriverSettlementReimbursement() {
    var actualPaidForTrip by remember { mutableStateOf("12") }
    var fastTagCharges by remember { mutableStateOf("12") }
    var driverDirectPayment by remember { mutableStateOf("500") }
    val toPay by remember { mutableStateOf("-2000") }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,

            )
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_driver),
                    contentDescription = "Fuel Icon"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.str_driver_settlement_reimbursement),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(thickness = 1.dp, color = Color(0xFFABABD1))

            Spacer(modifier = Modifier.height(16.dp))

            DriverSettlementReimbursementCost(
                label = stringResource(id = R.string.str_actual_paid_for_trip),
                value = actualPaidForTrip,
                isEditable = true,
                onValueChange = {
                    actualPaidForTrip = it
                }
            )
            DriverSettlementReimbursementCost(
                label = stringResource(id = R.string.str_fast_tag_charges),
                value = fastTagCharges,
                isEditable = true,
                onValueChange = {
                    fastTagCharges = it

                }
            )
            DriverSettlementReimbursementCost(
                label = stringResource(id = R.string.str_driver_direct_payment),
                value = driverDirectPayment,
                isEditable = true,
                onValueChange = {
                    driverDirectPayment = it
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF7F6F6), shape = RoundedCornerShape(4.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.str_to_pay),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Text(
                   // text = if (toPay < 0) Color(0xFFE42728) else Color(0xFF34D399),
                    text = toPay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFFE42728)
                )
            }
        }
    }

}

@Composable
fun DriverSettlementReimbursementCost(
    label: String,
    value: String,
    isEditable: Boolean,
    onValueChange: (String) -> Unit = {}
) {

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            if (isEditable) {
                BasicTextField(
                    value = value,
                    onValueChange = { onValueChange(it) },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black,
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Bold,
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .height(30.dp)
                        .width(100.dp)
                        .border(
                            BorderStroke(1.dp, Color(0xFFDCDCDC)),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(8.dp)
                )
            } else {
                Box(
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = value,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }

}