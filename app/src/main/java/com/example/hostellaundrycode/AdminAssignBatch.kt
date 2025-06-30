package com.example.hostellaundrycode

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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

// Order model
data class ReceivedOrder(
    val name: String,
    val room: String,
    val orderId: String,
    val type: String,
    val count: Int,
    val batch: String,
    var isSelected: Boolean = false
)

@Composable
fun OrderReceivedTodayScreen(
    onMarkClick: () -> Unit
) {
    var filter by remember { mutableStateOf("") }
    var selectAll by remember { mutableStateOf(false) }

    val orderList = remember {
        mutableStateListOf(
            *List(7) {
                ReceivedOrder(
                    name = "Diya Garg",
                    room = "A161",
                    orderId = "#16122",
                    type = "Normal",
                    count = 5,
                    batch = "Batch NA"
                )
            }.toTypedArray()
        )
    }

    // Apply "select all" if toggled
    LaunchedEffect(selectAll) {
        orderList.forEachIndexed { index, _ ->
            orderList[index] = orderList[index].copy(isSelected = selectAll)
        }
    }

    Column(Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Header Row
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

        Spacer(modifier = Modifier.height(12.dp))

        Text("Order Received Today", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(12.dp))

        // Dropdown filter (just a placeholder for now)
        OutlinedTextField(
            value = filter,
            onValueChange = { filter = it },
            label = { Text("Filter by hostel/ When ordered") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Select all checkbox
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = selectAll, onCheckedChange = { selectAll = it })
            Text("Select all in between")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Scrollable order list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(orderList) { index, order ->
                SelectableOrderCard(
                    order = order,
                    onToggle = {
                        orderList[index] = order.copy(isSelected = !order.isSelected)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mark button
        Button(
            onClick = { onMarkClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Mark for →", color = Color.White)
        }
    }
}

@Composable
fun SelectableOrderCard(order: ReceivedOrder, onToggle: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .border(
                width = if (order.isSelected) 2.dp else 0.dp,
                color = if (order.isSelected) Color.Blue else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F4F4))
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${order.name} ${order.room}", fontWeight = FontWeight.Medium)
                Text(order.batch, fontWeight = FontWeight.Light)
            }
            Spacer(modifier = Modifier.height(4.dp))

            Text(order.orderId)
            Text("${order.type} | ${order.count} items", fontSize = 12.sp)
        }
    }
}