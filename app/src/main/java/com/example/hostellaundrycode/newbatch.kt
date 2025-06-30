package com.example.hostellaundrycode

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun InitializeNewBatchScreen(
    onBatchInitialized: (String, String) -> Unit,
    onBack: () -> Unit
) {
    var batchName by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var initializedData by remember { mutableStateOf<Pair<String, String>?>(null) }

    val currentTime = remember {
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
    }

    // Show dialog only after initialization
    if (showDialog && initializedData != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Batch Initialized") },
            text = {
                Text("Batch '${initializedData!!.first}' has been successfully initialized.")
            },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onBack()
                }) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Initialize New Batch",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = batchName,
            onValueChange = { batchName = it },
            label = { Text("Batch Name") },
            placeholder = { Text("e.g., Morning Pickup, Block A") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Created at: $currentTime", fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (batchName.isNotBlank()) {
                    initializedData = batchName to currentTime
                    onBatchInitialized(batchName, currentTime)
                    showDialog = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = batchName.isNotBlank()
        ) {
            Text("Initialize Batch")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Cancel",
            color = Color(0xFF003366),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { onBack() }
        )
    }
}
