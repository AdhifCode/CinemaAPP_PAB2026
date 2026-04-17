package com.example.cinemaapp.ui.screens.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cinemaapp.ui.theme.*

@Composable
fun PaymentScreen(
    seatCount: Int,
    totalPrice: String,
    onBack: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    var selectedPaymentMethod by remember { mutableStateOf("BCA Virtual Account") }
    val paymentMethods = listOf("BCA Virtual Account", "Mandiri Virtual Account", "GoPay", "OVO")

    Scaffold(
        containerColor = DeepNavy,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = TextPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Payment",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.size(48.dp))
            }
        },
        bottomBar = {
            Button(
                onClick = onPaymentSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NeonGreen),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Confirm Payment",
                    color = DeepNavy,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Order Summary",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = BottomBarBg),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Selected Seats", color = TextSecondary)
                        Text("$seatCount Seats", color = TextPrimary, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = DividerColor)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total Price", color = TextSecondary)
                        Text(totalPrice, color = NeonGreen, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Payment Method",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = BottomBarBg),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    paymentMethods.forEach { method ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedPaymentMethod = method }
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        ) {
                            RadioButton(
                                selected = (selectedPaymentMethod == method),
                                onClick = { selectedPaymentMethod = method },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = NeonGreen,
                                    unselectedColor = TextSecondary
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = method,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}