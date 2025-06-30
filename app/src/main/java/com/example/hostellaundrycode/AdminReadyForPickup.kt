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

// Data class for ready-to-pickup batches
data class PickupBatch(
    val hostel: String,
    val batch: String,
    val orderCount: Int
)

@Composable
fun ReadyForPickupScreen() {
    val pickupBatches = listOf(
        PickupBatch("Hostel A", "Batch 1", 544),
        PickupBatch("Hostel N", "Batch 2", 540)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Top bar with logo and menu
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
            text = "Ready for Pickup",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Scrollable list of ready-for-pickup batches
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(pickupBatches) { batch ->
                PickupCard(batch)
            }
        }
    }
}

@Composable
fun PickupCard(batch: PickupBatch) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F4F4))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(batch.hostel, fontWeight = FontWeight.Medium)
                Text("${batch.orderCount} orders", fontSize = 12.sp)
            }
            Text(batch.batch, fontWeight = FontWeight.Light)
        }
    }
}