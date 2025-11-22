package com.peanech.khmershopcart.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.peanech.khmershopcart.ui.screens.CartScreen
import com.peanech.khmershopcart.ui.screens.CheckoutScreen
import com.peanech.khmershopcart.ui.screens.HomeScreen
import com.peanech.khmershopcart.ui.screens.ProductDetailScreen
import com.peanech.khmershopcart.ui.screens.SignInScreen
import com.peanech.khmershopcart.ui.screens.SignUpScreen
import com.peanech.khmershopcart.ui.screens.WelcomeScreen

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object SignIn : Screen("signin")
    object SignUp : Screen("signup")
    object Home : Screen("home")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object Cart : Screen("cart")
    object Checkout : Screen("checkout")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onGetStarted = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.SignIn.route) {
            SignInScreen(
                onSignInSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUp.route)
                }
            )
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                    }
                },
                onNavigateToSignIn = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onProductClick = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onCartClick = {
                    navController.navigate(Screen.Cart.route)
                }
            )
        }
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(
                productId = productId,
                onBackClick = { navController.popBackStack() },
                onAddToCart = { /* TODO */ },
                onBuyNow = {
                    // For now, go to cart or checkout
                    navController.navigate(Screen.Cart.route)
                }
            )
        }
        composable(Screen.Cart.route) {
            CartScreen(
                onBackClick = { navController.popBackStack() },
                onCheckout = {
                    navController.navigate(Screen.Checkout.route)
                }
            )
        }
        composable(Screen.Checkout.route) {
            CheckoutScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
