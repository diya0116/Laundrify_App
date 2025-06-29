package com.example.hostellaundrycode

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

// Data classes
data class LaundryOrder(
    val id: String,
    val customerName: String,
    val clothesType: String,
    val pickupTime: String,
    val hostelBlock: String,
    var status: OrderStatus
)

enum class OrderStatus(val displayName: String, val color: Color) {
    PENDING("Pending", Color(0xFFFFA726)),
    WASHING("Washing", Color(0xFF42A5F5)),
    DRYING("Drying", Color(0xFFAB47BC)),
    READY("Ready", Color(0xFF66BB6A))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard() {
    var orders by remember { mutableStateOf(getSampleOrders()) }

            LaunchedEffect(Unit) {

            orders = orders.map { order ->
                if (order.status != OrderStatus.READY && kotlin.random.Random.nextFloat() < 0.3f) {
                    val nextStatus = when (order.status) {
                        OrderStatus.PENDING -> OrderStatus.WASHING
                        OrderStatus.WASHING -> OrderStatus.DRYING
                        OrderStatus.DRYING -> OrderStatus.READY
                        OrderStatus.READY -> OrderStatus.READY
                    }
                    order.copy(status = nextStatus)
                } else {
                    order
                }
            }
        }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF5F7FA)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top App Bar
            TopAppBar(
                title = {
                    Text(
                        "LAUNDRIFY",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2)
                ),

                )
            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Header Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Order Management",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C3E50)
                    )

                    TextButton(
                        onClick = { /* Refresh action */ },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color(0xFF1976D2)
                        )
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Refresh")
                    }
                }

                Text(
                    "Manage daily laundry requests and update statuses",
                    fontSize = 14.sp,
                    color = Color(0xFF7B8794),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Statistics Cards
                StatisticsRow(orders)

                Spacer(modifier = Modifier.height(24.dp))

                // Filters
                Text(
                    "Filters",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2C3E50),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                FilterChip(
                    onClick = { /* Filter by today */ },
                    label = { Text("Today") },
                    selected = true,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Orders List
                Text(
                    "Laundry Requests",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2C3E50),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    "Simply click on any status cell of a row to register",
                    fontSize = 12.sp,
                    color = Color(0xFF7B8794),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OrdersList(
                    orders = orders,
                    onStatusUpdate = { orderId, newStatus ->
                        orders = orders.map { order ->
                            if (order.id == orderId) {
                                order.copy(status = newStatus)
                            } else {
                                order
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun StatisticsRow(orders: List<LaundryOrder>) {
    val pendingCount = orders.count { it.status == OrderStatus.PENDING }
    val processingCount = orders.count { it.status == OrderStatus.WASHING || it.status == OrderStatus.DRYING }
    val inProgressCount = orders.count { it.status == OrderStatus.WASHING }
    val readyCount = orders.count { it.status == OrderStatus.READY }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            modifier = Modifier.weight(1f),
            title = "Total Requests",
            count = orders.size,
            icon = Icons.Default.Assignment,
            backgroundColor = Color(0xFF1976D2),
            iconColor = Color.White
        )

        StatCard(
            modifier = Modifier.weight(1f),
            title = "Pending",
            count = pendingCount,
            icon = Icons.Default.Schedule,
            backgroundColor = Color(0xFF1976D2),
            iconColor = Color.White
        )

        StatCard(
            modifier = Modifier.weight(1f),
            title = "In Progress",
            count = inProgressCount,
            icon = Icons.Default.Sync,
            backgroundColor = Color(0xFF1976D2),
            iconColor = Color.White
        )

        StatCard(
            modifier = Modifier.weight(1f),
            title = "Ready",
            count = readyCount,
            icon = Icons.Default.CheckCircle,
            backgroundColor = Color(0xFF1976D2),
            iconColor = Color.White
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    count: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    backgroundColor: Color,
    iconColor: Color
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedContent(
                targetState = count,
                transitionSpec = {
                    slideInVertically { height -> height } + fadeIn() with
                            slideOutVertically { height -> -height } + fadeOut()
                },
                label = "count_animation"
            ) { targetCount ->
                Text(
                    text = targetCount.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = iconColor
                )
            }

            Text(
                text = title,
                fontSize = 12.sp,
                color = iconColor.copy(alpha = 0.9f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun OrdersList(
    orders: List<LaundryOrder>,
    onStatusUpdate: (String, OrderStatus) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(orders, key = { it.id }) { order ->
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                OrderCard(
                    order = order,
                    onStatusUpdate = onStatusUpdate
                )
            }
        }
    }
}

@Composable
fun OrderCard(
    order: LaundryOrder,
    onStatusUpdate: (String, OrderStatus) -> Unit
) {
    var showStatusDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Request ID
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = order.id,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1976D2)
                )
                Text(
                    text = "Request ID",
                    fontSize = 10.sp,
                    color = Color(0xFF7B8794)
                )
            }

            // Customer Name
            Column(
                modifier = Modifier.weight(1.2f)
            ) {
                Text(
                    text = order.customerName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Student Name",
                    fontSize = 10.sp,
                    color = Color(0xFF7B8794)
                )
            }

            // Clothes Type
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (order.clothesType == "Jeans") Color(0xFF2C3E50) else Color(0xFF34495E)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = order.clothesType,
                        fontSize = 10.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Text(
                    text = "Clothes Type",
                    fontSize = 10.sp,
                    color = Color(0xFF7B8794),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Pickup Time
            Column(
                modifier = Modifier.weight(1.2f)
            ) {
                Text(
                    text = order.pickupTime,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Pickup Time",
                    fontSize = 10.sp,
                    color = Color(0xFF7B8794)
                )
            }

            // Hostel Block
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = order.hostelBlock,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Hostel Block",
                    fontSize = 10.sp,
                    color = Color(0xFF7B8794)
                )
            }

            // Status
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Card(
                    modifier = Modifier.clickable { showStatusDialog = true },
                    colors = CardDefaults.cardColors(
                        containerColor = order.status.color.copy(alpha = 0.1f)
                    ),
                    border = BorderStroke(1.dp, order.status.color),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = order.status.displayName,
                        fontSize = 12.sp,
                        color = order.status.color,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
                Text(
                    text = "Status",
                    fontSize = 10.sp,
                    color = Color(0xFF7B8794),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Actions
            IconButton(
                onClick = { /* More actions */ }
            ) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "More actions",
                    tint = Color(0xFF7B8794)
                )
            }
        }
    }

    // Status Update Dialog
    if (showStatusDialog) {
        StatusUpdateDialog(
            currentStatus = order.status,
            onStatusSelected = { newStatus ->
                onStatusUpdate(order.id, newStatus)
                showStatusDialog = false
            },
            onDismiss = { showStatusDialog = false }
        )
    }
}

@Composable
fun StatusUpdateDialog(
    currentStatus: OrderStatus,
    onStatusSelected: (OrderStatus) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Update Status",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                OrderStatus.values().forEach { status ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onStatusSelected(status) },
                        colors = CardDefaults.cardColors(
                            containerColor = if (status == currentStatus)
                                status.color.copy(alpha = 0.3f)
                            else
                                Color.Transparent
                        ),
                        border = BorderStroke(
                            width = if (status == currentStatus) 2.dp else 1.dp,
                            color = status.color
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(
                                        color = status.color,
                                        shape = CircleShape
                                    )
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = status.displayName,
                                fontWeight = if (status == currentStatus) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

fun getSampleOrders(): List<LaundryOrder> {
    return listOf(
        LaundryOrder(
            id = "#LR001",
            customerName = "John Doe",
            clothesType = "Normal",
            pickupTime = "10:00 AM - 11:00 AM",
            hostelBlock = "Block A",
            status = OrderStatus.PENDING
        ),
        LaundryOrder(
            id = "#LR002",
            customerName = "Jane Smith",
            clothesType = "Jeans",
            pickupTime = "2:00 PM - 3:00 PM",
            hostelBlock = "Block B",
            status = OrderStatus.WASHING
        ),
        LaundryOrder(
            id = "#LR003",
            customerName = "Mike Johnson",
            clothesType = "Normal",
            pickupTime = "4:00 PM - 5:00 PM",
            hostelBlock = "Block A",
            status = OrderStatus.DRYING
        )
    )
}


