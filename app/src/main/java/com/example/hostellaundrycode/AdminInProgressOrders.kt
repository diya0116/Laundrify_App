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

// Data model for in-progress batches
data class InProgressBatch(
    val hostel: String,
    val status: String,
    val batch: String,
    val orderCount: Int
)

@Composable
fun InProgressOrdersScreen() {
    // Sample list of batches
    val batches = listOf(
        InProgressBatch("Hostel A", "Washing", "Batch 1", 544),
        InProgressBatch("Hostel N", "Drying", "Batch 2", 544),
        InProgressBatch("Hostel Q", "Orders checked", "Batch 3", 544)
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

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

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "In Progress Orders",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Scrollable list of in-progress batches
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(batches) { batch ->
                InProgressCard(batch)
            }
        }
    }
}

@Composable
fun InProgressCard(batch: InProgressBatch) {
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
                Column {
                    Text(batch.hostel, fontWeight = FontWeight.Medium)
                    Text(batch.status, fontSize = 14.sp)
                }
                Text(batch.batch, fontWeight = FontWeight.Light)
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text("${batch.orderCount} orders", fontSize = 12.sp)
        }
    }
}
