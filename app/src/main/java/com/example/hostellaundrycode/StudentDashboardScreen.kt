package com.example.hostellaundrycode

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.res.painterResource

@Composable
fun StudentDashboardScreen(
    studentName: String,
    hostel: String,
    room: String,
    onNewRequestClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onFeedbackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
//            Image(
//                painter = painterResource(id = R.drawable.laundry_logo),
//                contentDescription = "Logo",
//                modifier = Modifier.height(40.dp)
//            )
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Hello ", fontSize = 20.sp)
        Text(
            text = studentName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text("$hostel\nRoom $room")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onNewRequestClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003366))
        ) {
            Text("+ New Laundry Request")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Active Orders Box
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EAF6))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Active Orders", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Order Request #16122", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                Text("● Order Processed", color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Estimated Pickup Date: 5 July 2025", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "View Order history",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { onHistoryClick() },
            color = Color(0xFF003366),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Row of boxes
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text("Orders this month\n5 of 8")
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .background(Color(0xFFD6E4F0), RoundedCornerShape(8.dp))
                    .padding(16.dp)
                    .clickable { onFeedbackClick() }
            ) {
                Text("Log Feedback\nor Issue Request")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Stages Footer
        Text(
            text = "Stages of Delivering Orders",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Text(
            text = "Order Processed → Order Checked → Washing → Drying → Packed → Collected",
            fontSize = 13.sp
        )
    }
}
