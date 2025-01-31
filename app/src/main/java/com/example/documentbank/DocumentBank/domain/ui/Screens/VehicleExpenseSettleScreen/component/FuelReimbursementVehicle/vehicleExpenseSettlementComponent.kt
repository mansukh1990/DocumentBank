package com.example.documentbank.DocumentBank.domain.ui.Screens.VehicleExpenseSettleScreen.component.FuelReimbursementVehicle

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
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentbank.R

@Composable
fun FuelReimbursementVehicle() {
    var openningKm by remember { mutableStateOf("-") }
    var closingKm by remember { mutableStateOf("12") }
    var totalKmRun by remember { mutableStateOf("-") }
    var gmapsKm by remember { mutableStateOf("25") }
    var approvedKm by remember { mutableStateOf("30") }
    var ratePerKm by remember { mutableStateOf("100") }
    var totalFuelAmount by remember { mutableStateOf("2000") }

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
                    painter = painterResource(id = R.drawable.ic_fuel_reimbursement_vehicle), // Replace with your custom icon
                    contentDescription = "Fuel Icon"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Fuel Reimbursement Vehicle",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(thickness = 1.dp, color = Color(0xFFABABD1))

            Spacer(modifier = Modifier.height(16.dp))

            FuelReimbursementVehicleCost(
                label = "Opening KM",
                value = openningKm,
                isEditable = false,
                onValueChange = { openningKm = it }
            )
            FuelReimbursementVehicleCost(
                label = "Closing KM",
                value = closingKm,
                isEditable = true,
                onValueChange = {
                    closingKm = it

                }
            )
            FuelReimbursementVehicleCost(
                label = "Total KM Run",
                value = totalKmRun,
                isEditable = false,
                onValueChange = {
                    totalKmRun = it
                }
            )
            FuelReimbursementVehicleCost(
                label = "Gmaps KM",
                value = gmapsKm,
                isEditable = true,
                onValueChange = {
                    gmapsKm = it
                }
            )
            FuelReimbursementVehicleCost(
                label = "Approved (KM)",
                value = approvedKm,
                isEditable = true,
                onValueChange = {
                    approvedKm = it
                }
            )
            FuelReimbursementVehicleCost(
                label = "Rate Per Km (₹)",
                value = ratePerKm,
                isEditable = true,
                onValueChange = {
                    ratePerKm = it
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
                    text = "Total Fuel Amount (₹)",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = totalFuelAmount,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF042E4D)
                )
            }
        }
    }

}

@Composable
fun DataRowWithBorder(label: String, value: String, hasBorder: Boolean) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .height(40.dp)
                    .width(100.dp)
                    .padding(start = 8.dp)
                    .then(
                        if (hasBorder) {
                            Modifier
                                .border(
                                    BorderStroke(1.dp, Color(0xFFDCDCDC)),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(4.dp)
                        } else Modifier
                    )
            ) {
                Text(
                    text = value,
                    fontSize = 14.sp,
                    fontWeight = if (hasBorder) FontWeight.Bold else FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(end = 5.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
    }

}

@Composable
fun EditableDataRow(
    label: String,
    value: String,
    isEditable: Boolean,
    onValueChange: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.weight(1f)
        )
        if (isEditable) {
            TextField(
                value = value,
                onValueChange = { onValueChange(it) },
                modifier = Modifier
                    .height(40.dp)
                    .width(100.dp)
                    .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(4.dp)),
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                singleLine = true
            )
        } else {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(100.dp)
                    .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(4.dp))
                    .padding(4.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = value,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun FuelReimbursementVehicleCost(
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
