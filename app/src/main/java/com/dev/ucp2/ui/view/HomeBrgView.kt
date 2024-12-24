package com.dev.ucp2.ui.view


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.ucp2.R


@Composable
fun HomeBrgView(
    onBarangClick: () -> Unit,
    onSuplierClick: () -> Unit,
    onAddBrgClick: () -> Unit,
    onAddSplClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopHomeAppBar(
                showBackButton = false,
                onBack = {}
            )
        },
        content = { paddingValues ->
            BodyHomeBrgView(
                onBarangClick = onBarangClick,
                onSuplierClick = onSuplierClick,
                onAddBrgClick = onAddBrgClick,
                onAddSplClick = onAddSplClick,
                paddingValues = paddingValues
            )
        }
    )
}

@Composable
fun TopHomeAppBar(
    onBack: () -> Unit,
    showBackButton: Boolean = true,
    showImage: Boolean = true,
    imageResource: Int? = R.drawable.logoo,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF6200EE),
                        Color(0xFF6200EE)
                    )
                ),
                shape = RoundedCornerShape(bottomEnd = 60.dp)
            )
            .padding(horizontal = 24.dp, vertical = 26.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            if (showBackButton) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f))
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(6.dp)
            ) {
                Text(
                    text = "Ware Flow",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                )
                Text(
                    text = "Management Barang",
                    color = Color.White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                )
            }

            if (showImage && imageResource != null) {
                Image(
                    painter = painterResource(R.drawable.logoo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f))
                )
            }
        }
    }
}

@Composable
fun BodyHomeBrgView(
    onBarangClick: () -> Unit,
    onSuplierClick: () -> Unit,
    onAddBrgClick: () -> Unit,
    onAddSplClick: () -> Unit,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Selamat Datang di Aplikasi Ware Flow",
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color(0xFF6200EE),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )


        Text(
            text = "Aplikasi Manajemen Barang Terbaik",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Gray,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            item {
                GradientCard(
                    title = "Add Barang",
                    imageRes = R.drawable.add_brg,
                    gradient = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF4CAF50), Color(0xFF8BC34A))
                    ),
                    onClick = onAddBrgClick
                )
            }
            item {
                GradientCard(
                    title = "List Barang",
                    imageRes = R.drawable.list_brg,
                    gradient = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF2196F3), Color(0xFF64B5F6))
                    ),
                    onClick = onBarangClick
                )
            }
            item {
                GradientCard(
                    title = "Add Supplier",
                    imageRes = R.drawable.add_spl,
                    gradient = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFF9800), Color(0xFFFFB74D))
                    ),
                    onClick = onAddSplClick
                )
            }
            item {
                GradientCard(
                    title = "List Supplir",
                    imageRes = R.drawable.list_spl,
                    gradient = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF9C27B0), Color(0xFFBA68C8))
                    ),
                    onClick = onSuplierClick
                )
            }
        }
    }
}


@Composable
fun GradientCard(
    title: String,
    imageRes: Int, // ID gambar
    gradient: Brush,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var clicked by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (clicked) 0.98f else 1f,
        animationSpec = tween(durationMillis = 150),
        finishedListener = { clicked = false }
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                clicked = true
                onClick()
            }
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .height(150.dp),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.elevatedCardElevation(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = title,
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
