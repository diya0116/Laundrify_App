package com.example.hostellaundrycode



// Core Compose
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Material 3 UI components
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults

// Required for ExposedDropdownMenuBox (experimental)
import androidx.compose.material3.ExperimentalMaterial3Api

// ✅ MODEL
data class Batch(
    val name: String,
    val createdAt: String,
    val status: String
)

@Composable
fun UpdateScreen() {
    // ✅ Declare your mutableStateListOf inside a Composable
    val batches = remember {
        mutableStateListOf(
            Batch("Morning Batch", "2025-06-29 08:00", "Pending"),
            Batch("Evening Batch", "2025-06-29 17:00", "In Progress")
        )
    }

    UpdateBatchStatusScreen(
        batchList = batches,
        onStatusUpdated = { updatedBatch ->
            val index = batches.indexOfFirst { it.name == updatedBatch.name }
            if (index != -1) {
                batches[index] = updatedBatch
            }
            println("✅ Updated ${updatedBatch.name} to ${updatedBatch.status}!")
        },
        onBack = {
            println("🔙 Back pressed")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateBatchStatusScreen(
    batchList: List<Batch>,
    onStatusUpdated: (Batch) -> Unit,
    onBack: () -> Unit
) {
    var selectedBatch by remember { mutableStateOf<Batch?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val statusOptions = listOf("Pending", "In Progress", "Completed")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text("Update Batch Status", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        Text("Select Batch")

        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown for selecting batch
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedBatch?.name ?: "",
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Choose a batch") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                batchList.forEach { batch ->
                    DropdownMenuItem(
                        text = { Text(batch.name) },
                        onClick = {
                            selectedBatch = batch
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        selectedBatch?.let { batch ->
            var selectedStatus by remember { mutableStateOf(batch.status) }

            Text("Current Status: ${batch.status}", color = Color.Gray)

            Spacer(modifier = Modifier.height(8.dp))

            statusOptions.forEach { status ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { selectedStatus = status }
                ) {
                    RadioButton(
                        selected = selectedStatus == status,
                        onClick = { selectedStatus = status }
                    )
                    Text(status)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onStatusUpdated(batch.copy(status = selectedStatus))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update Status")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Cancel",
                color = Color(0xFF003366),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { onBack() }
            )
        }
    }
}
