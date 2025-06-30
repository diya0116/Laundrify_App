package com.example.hostellaundrycode

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*


// Data model for each order item
data class LaundryOrder(
    val name: String,
    val hostelRoom: String,
    val orderId: String,
    val type: String,
    val itemsCount: Int,
    val batch: String
)

@Composable
fun OrdersReceivedScreen() {
    // Sample order list
    val orders = List(7) {
        LaundryOrder(
            name = "Diya Garg",
            hostelRoom = "A161",
            orderId = "#16122",
            type = "Normal",
            itemsCount = 5,
            batch = "Batch NA"
        )
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Top bar: Logo and menu
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

        Spacer(modifier = Modifier.height(16.dp))

        // Heading
        Text(
            text = "Order Received Today",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Scrollable list of orders
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(orders) { order ->
                OrderCard(order)
            }
        }
    }
}

@Composable
fun OrderCard(order: LaundryOrder) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F4F4))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${order.name} ${order.hostelRoom}", fontWeight = FontWeight.Medium)
                Text(order.batch, fontWeight = FontWeight.Light)
            }
            Spacer(modifier = Modifier.height(4.dp))

            Text(order.orderId)
            Text("${order.type} | ${order.itemsCount} items", fontSize = 12.sp)
        }
    }
}
