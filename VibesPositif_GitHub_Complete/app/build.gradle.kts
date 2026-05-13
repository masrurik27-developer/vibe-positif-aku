plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.kotlin.serialization)
    kotlin("kapt")
}

// Versi di-inject dari GitHub Actions via -P flag
val versionNameProp  = project.findProperty("version.name")?.toString()  ?: "1.0.0"
val versionCodeProp  = project.findProperty("version.code")?.toString()?.toIntOrNull() ?: 1

android {
    namespace   = "com.vibes.positif"
    compileSdk  = 34

    defaultConfig {
        applicationId    = "com.vibes.positif"
        minSdk           = 34
        targetSdk        = 34
        versionCode      = versionCodeProp
        versionName      = versionNameProp
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
        resourceConfigurations += listOf("en", "id")
    }

    // ── Signing: baca dari environment variable yang di-set workflow ──
    signingConfigs {
        create("release") {
            storeFile      = file("release.jks")   // di-decode oleh workflow step
            storePassword  = System.getenv("SIGNING_STORE_PASSWORD") ?: ""
            keyAlias       = System.getenv("SIGNING_KEY_ALIAS")      ?: ""
            keyPassword    = System.getenv("SIGNING_KEY_PASSWORD")   ?: ""
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable        = true
            isMinifyEnabled     = false
        }
        release {
            isMinifyEnabled    = true
            isShrinkResources  = true
            signingConfig      = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures {
        compose     = true
        buildConfig = true
    }
    packaging {
        resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" }
    }
}

kapt { correctErrorTypes = true }

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.splashscreen)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)

    // Hilt DI
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.work)
    kapt(libs.hilt.compiler)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    // DataStore
    implementation(libs.datastore.preferences)

    // WorkManager
    implementation(libs.work.runtime.ktx)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Lottie
    implementation(libs.lottie.compose)

    // Coil
    implementation(libs.coil.compose)

    // Accompanist
    implementation(libs.accompanist.systemuicontroller)

    // ── ADS_START ─────────────────────────────────────────
    implementation(libs.play.services.ads)
    // ── ADS_END ───────────────────────────────────────────

    // ── BILLING_START ─────────────────────────────────────
    implementation(libs.billing.ktx)
    // ── BILLING_END ───────────────────────────────────────

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
