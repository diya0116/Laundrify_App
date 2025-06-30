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

// Data class for feedback items
data class FeedbackItem(
    val message: String,
    val studentName: String,
    val hostelRoom: String,
    val orderId: String,
    val batch: String
)

@Composable
fun FeedbackRequestsScreen() {
    val feedbackList = List(4) {
        FeedbackItem(
            message = "Clothes not washed properly. A stain was found on the tshirt",
            studentName = "Diya Garg",
            hostelRoom = "A161",
            orderId = "#16122",
            batch = "Batch 1"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Top bar
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
            text = "Feedback/ Issue Requests",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Feedback list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(feedbackList) { item ->
                FeedbackCard(item)
            }
        }
    }
}

@Composable
fun FeedbackCard(item: FeedbackItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F4F4))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(item.message, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "${item.studentName} ${item.hostelRoom} | ${item.orderId} | ${item.batch}",
                fontSize = 12.sp,
                color = Color.DarkGray
            )
        }
    }
}