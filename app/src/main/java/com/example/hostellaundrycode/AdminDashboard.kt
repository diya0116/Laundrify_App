package com.example.hostellaundrycode

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

@Composable
fun AdminDashboardScreen(
    adminName: String,
    adminId: String,
    onConfirmPickup: () -> Unit,
    onUpdateStatusClick: () -> Unit,
    onNewBatchClick: () -> Unit,
    onBatchStatusClick: () -> Unit
) {
    var orderId by remember { mutableStateOf("") }
    var passcode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Row: Laundrify logo and menu icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.laundrifytext),
                contentDescription = "Laundrify Logo",
                modifier = Modifier.height(60.dp)
            )
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Welcome text
        Text("Hello Mr. $adminName!", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Id - $adminId", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(20.dp))

        // Section: Pickup form
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD6E4F0))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Give pickup to student", fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = orderId,
                    onValueChange = { orderId = it },
                    label = { Text("Enter order Id") },
                    placeholder = { Text("Enter your room no.") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = passcode,
                    onValueChange = { passcode = it },
                    label = { Text("Enter passcode") },
                    placeholder = { Text("Enter your password") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Confirm",
                    color = Color(0xFF003366),
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { onConfirmPickup() }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Row: Order summary boxes
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            StatusBox("Orders received today -", "213")
            StatusBox("In progress orders", "2044")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            StatusBox("Ready for pickup", "544")
            StatusBox("Pending feedback/\nissue requests", "")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Clickable update status text
        Text(
            text = "Update Status",
            fontSize = 14.sp,
            color = Color(0xFF003366),
            modifier = Modifier.clickable { onUpdateStatusClick() }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Initialize new batch button
        Button(
            onClick = { onNewBatchClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003366))
        ) {
            Text("+ Initialize New Batch")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Disabled batch status button (grey)
        Button(
            onClick = { onBatchStatusClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = false, // Set to true when needed
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White
            )
        ) {
            Text("Update Batch Status")
        }
    }
}

@Composable
fun StatusBox(title: String, count: String) {
    Card(
        modifier = Modifier
//            .weight(1f)
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(title, fontSize = 14.sp)
            if (count.isNotEmpty()) {
                Text(count, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
