package com.example.hostellaundrycode

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

@Composable

fun FeedbackFormScreen(
    studentName: String,
    hostel: String,
    room: String,
    onSubmit: (String) -> Unit = {}
) {
    var feedbackText by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Submitted") },
            text = { Text("Your feedback has been submitted.") },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
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
            Image(
                painter = painterResource(id = R.drawable.laundrifytext),
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
            text = "Feedback/ Issue Request",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Write your Feedback/ Issue", fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = feedbackText,
            onValueChange = { feedbackText = it },
            placeholder = { Text("Write here") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontStyle = FontStyle.Italic, fontWeight = FontWeight.SemiBold)) {
                    append("Writing Request as:\n")
                }
                append("$studentName\n$hostel\nRoom $room")
            },
            color = Color.DarkGray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onSubmit(feedbackText)
                showDialog = true
                feedbackText = ""  // Optional: clear the field after submission
            },
            enabled = feedbackText.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Request")
        }
    }
}
