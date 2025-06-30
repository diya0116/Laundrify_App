package com.example.hostellaundrycode

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StudentDashboardScreen(
    name: String,
    rollNo: String,
    hostelInput: String,
    onNewRequestClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onFeedbackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Hello 👋", fontSize = 20.sp)
        Text(text = name, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Text(text = "$hostelInput\nRoll No: $rollNo", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onNewRequestClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003366))
        ) {
            Text("+ New Laundry Request", color = Color.White)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EAF6))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Active Orders", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Order Request #16122", fontWeight = FontWeight.SemiBold)
                Text("● Order Processed", color = Color.Black)
                Text("Estimated Pickup: 5 July 2025", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "View Order history",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { onHistoryClick() },
            color = Color(0xFF003366),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

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
                Text("Orders this month\n5 of 8", fontSize = 13.sp)
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .background(Color(0xFFD6E4F0), RoundedCornerShape(8.dp))
                    .padding(16.dp)
                    .clickable { onFeedbackClick() }
            ) {
                Text("Log Feedback\nor Issue Request", fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        OrderStatusTracker(
            currentStage = "Dried", // You can dynamically update this
            onShowPasscodeClick = {
                // TODO: show a dialog or toast with passcode
            }
        )

    }
}
@Composable
fun OrderStatusTracker(
    currentStage: String,
    onShowPasscodeClick: () -> Unit
) {
    val stages = listOf("Checked", "Washed", "Dried", "Packed", "Collected")
    val currentIndex = stages.indexOf(currentStage)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8EAF6), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text("Active Orders", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            stages.forEachIndexed { index, stage ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(
                                color = if (index <= currentIndex) Color.Black else Color.Gray,
                                shape = CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stage,
                        fontSize = 10.sp,
                        fontWeight = if (index == currentIndex) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Estimated Pickup: 5 July 2025", fontSize = 12.sp)

        if (currentIndex >= stages.indexOf("Packed")) {
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onShowPasscodeClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003366)),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Show Passcode (for pickup)", color = Color.White)
            }
        }
    }
}
