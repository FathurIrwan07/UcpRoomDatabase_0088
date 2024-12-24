package com.dev.ucp2.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.dev.ucp2.ui.view.HomeBrgView
import com.dev.ucp2.ui.view.SplashView
import com.dev.ucp2.ui.view.barang.*
import com.dev.ucp2.ui.view.suplier.HomeSplView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiSplash.route
    ) {
        // Splash Screen
        composable(route = DestinasiSplash.route) {
            SplashView(onMulaiButton = {
                navController.navigate(DestinasiHome.route) {
                    popUpTo(DestinasiSplash.route) { inclusive = true }
                }
            })
        }

        // Home Screen
        composable(
            route = DestinasiHome.route
        ) {
            HomeBrgView(
                onBarangClick = { navController.navigate(DestinasiHomeBrg.route) },
                onAddBrgClick = { navController.navigate(DestinasiInsertBrg.route) },
                onSuplierClick = { navController.navigate(DestinasiHomeSpl.route) },
                onAddSplClick = { navController.navigate(DestinasiInsertSpl.route) },
            )
        }

// Insert Barang Screen
        composable(
            route = DestinasiHomeBrg.route
        ){
            HomeBrgView(
                onDetailBrgClick = {idBarang ->
                    navController.navigate("${DestinasiDetailBrg.route}/$idBarang")
                    println("PengelolaHalaman: idBarang= $idBarang")
                },
                onAddBrgClick = {navController.navigate(DestinasiInsertBrg.route)},
                onBack = {navController.popBackStack()},
                modifier = Modifier
            )
        }
        composable(
            route = DestinasiHomeSpl.route
        ){
            HomeSplView(
                onBack = {navController.popBackStack()},
                modifier = Modifier
            )
        }
        composable(
            route = DestinasiInsertBrg.route
        ){
            InsertBrgView(
                onBack = {navController.popBackStack()},
                onNavigate = { },
                modifier = Modifier
            )
        }
        composable(
            DestinasiDetailBrg.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailBrg.idBarang){
                    type = NavType.StringType
                }
            )
        ){
            val id = it.arguments?.getString(DestinasiDetailBrg.idBarang)
            id?.let { id ->
                DetailBrgView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdate.route}/$it")
                    },
                    modifier = Modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )

            }
        }
        composable(
            DestinasiUpdate.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdate.idBarang){
                    type = NavType.StringType
                }
            )
        ){
            UpdateBrgView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = Modifier,
            )
        }
        composable(
            route = DestinasiInsertSpl.route
        ){
            InsertSplView(
                onBack = {navController.popBackStack()},
                onNavigate = { },
                modifier = Modifier
            )
        }
    }
}