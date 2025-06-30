package com.example.hostellaundrycode

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
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

// Data class for order history
data class OrderHistoryItem(
    val orderId: String,
    val date: String,
    val status: String,
    val type: String,
    val itemCount: Int,
    val isActive: Boolean
)

@Composable
fun OrderHistoryScreen() {
    val historyList = listOf(
        OrderHistoryItem("#16122", "Thu, 5 July 2025", "Washing", "Normal", 5, true),
        OrderHistoryItem("#16122", "Thu, 1 July 2025", "Order completed", "Normal", 8, false),
        OrderHistoryItem("#16122", "Thu, 1 July 2025", "Order completed", "Normal", 8, false),
        OrderHistoryItem("#16122", "Thu, 1 July 2025", "Order completed", "Normal", 8, false),
        OrderHistoryItem("#16122", "Thu, 1 July 2025", "Order completed", "Normal", 8, false)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.Laundrifytext),
                contentDescription = "Logo",
                modifier = Modifier.height(40.dp)
            )
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Order History",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Scrollable list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(historyList) { order ->
                OrderHistoryCard(order)
            }
        }
    }
}

@Composable
fun OrderHistoryCard(order: OrderHistoryItem) {
    val bgColor = if (order.isActive) Color(0xFFCBD9F5) else Color(0xFFF4F4F4)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("${order.orderId} | ${order.date}", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(order.status, fontWeight = FontWeight.Normal)
            Text("${order.type} | ${order.itemCount} items", fontSize = 12.sp)
        }
    }
}
