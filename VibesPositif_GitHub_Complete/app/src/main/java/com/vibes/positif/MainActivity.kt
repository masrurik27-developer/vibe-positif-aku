package com.vibes.positif

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint

// ── ADS_START ─────────────────────────────────────────────
import com.google.android.gms.ads.MobileAds
// ── ADS_END ───────────────────────────────────────────────

// ── BILLING_START ─────────────────────────────────────────
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.PurchasesUpdatedListener
// ── BILLING_END ───────────────────────────────────────────

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ── ADS_START ──────────────────────────────────────
        MobileAds.initialize(this) {}
        // ── ADS_END ────────────────────────────────────────

        // ── BILLING_START ──────────────────────────────────
        initBillingClient()
        // ── BILLING_END ────────────────────────────────────

        setContent {
            VibesApp()
        }
    }

    // ── ADS_START ──────────────────────────────────────────
    private fun loadBannerAd() {
        // Load banner ad here
    }
    // ── ADS_END ────────────────────────────────────────────

    // ── BILLING_START ──────────────────────────────────────
    private fun initBillingClient() {
        val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases -> }
        val billingClient = BillingClient.newBuilder(this)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()
    }
    // ── BILLING_END ────────────────────────────────────────
}

@Composable
fun VibesApp() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "✨ Vibes Positif+",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
