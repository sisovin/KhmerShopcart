package com.peanech.khmershopcart.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.peanech.khmershopcart.R
import com.peanech.khmershopcart.ui.components.ProductCard
import com.peanech.khmershopcart.ui.components.SectionHeader

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onProductClick: (String) -> Unit,
    onCartClick: () -> Unit
) {
    var selectedItem by remember { mutableIntStateOf(1) } // Default to Home
    val items = listOf("Explore", "Home", "Wishlist", "Cart", "Profile")
    val icons = listOf(
        Icons.Default.Search,
        Icons.Default.Home,
        Icons.Default.Favorite,
        Icons.Default.ShoppingCart,
        Icons.Default.Person
    )

    Scaffold(
        topBar = {
            if (selectedItem == 1) {
                TopAppBar(
                    title = { Text("Khmer ShopCart", fontWeight = FontWeight.Bold) },
                    actions = {
                        IconButton(onClick = { /* TODO: Search */ }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                        IconButton(onClick = { /* TODO: Navigate to Settings */ }) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedItem) {
                0 -> ExploreScreen()
                1 -> HomeDashboardContent(onProductClick)
                2 -> WishlistScreen()
                3 -> CartTabContent()
                4 -> ProfileScreen()
            }
        }
    }
}

@Composable
fun HomeDashboardContent(onProductClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Banner Carousel
        val pagerState = rememberPagerState(pageCount = { 3 })
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondary)
            ) {
                // Placeholder for banner image
                AsyncImage(
                    model = "https://picsum.photos/800/400?random=$page",
                    contentDescription = "Banner $page",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    error = painterResource(R.drawable.ic_launcher_background)
                )
                Text(
                    text = "Promo Banner ${page + 1}",
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        }

        // Categories
        SectionHeader(title = "Categories")
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(listOf("Electronics", "Fashion", "Home", "Beauty", "Sports")) { category ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary),
                        contentAlignment = Alignment.Center
                    ) {
                        // Icon placeholder
                        Text(category.take(1))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = category, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        // Featured Products
        SectionHeader(title = "Featured Collections", actionText = "See All", onActionClick = {})
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(5) { index ->
                ProductCard(
                    title = "Product Title $index",
                    price = "$${(index + 1) * 10}.99",
                    imageUrl = "https://picsum.photos/200/200?random=$index",
                    onClick = { onProductClick("prod_$index") }
                )
            }
        }

        // CTA
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "Don't miss out on our daily deals!",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text("View Deals")
                }
            }
        }

        // Recommendations
        SectionHeader(title = "Recommended for You")
        
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ProductCard(
                    title = "Rec Product 1",
                    price = "$49.99",
                    imageUrl = "https://picsum.photos/200/200?random=10",
                    onClick = { onProductClick("rec_1") },
                    modifier = Modifier.weight(1f)
                )
                ProductCard(
                    title = "Rec Product 2",
                    price = "$29.99",
                    imageUrl = "https://picsum.photos/200/200?random=11",
                    onClick = { onProductClick("rec_2") },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ProductCard(
                    title = "Rec Product 3",
                    price = "$89.99",
                    imageUrl = "https://picsum.photos/200/200?random=12",
                    onClick = { onProductClick("rec_3") },
                    modifier = Modifier.weight(1f)
                )
                ProductCard(
                    title = "Rec Product 4",
                    price = "$19.99",
                    imageUrl = "https://picsum.photos/200/200?random=13",
                    onClick = { onProductClick("rec_4") },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun ExploreScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Explore Screen")
    }
}

@Composable
fun WishlistScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Wishlist Screen")
    }
}

@Composable
fun CartTabContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Cart Screen")
    }
}

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Profile Screen")
    }
}
