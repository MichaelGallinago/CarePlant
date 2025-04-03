import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
}

android {
    namespace = "net.micg.plantcare"
    compileSdk = 35

    defaultConfig {
        applicationId = "net.micg.plantcare"
        minSdk = 24
        targetSdk = 35
        versionCode = 9
        versionName = "1.18"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "WEB_STORAGE_URL",
                getLocalProperties().getProperty("web_storage_url")
            )
            buildConfigField(
                "String",
                "ANALYTICS_URL",
                getLocalProperties().getProperty("analytics_url")
            )
            signingConfig = signingConfigs.getByName("debug")
        }
        debug {
            buildConfigField(
                "String",
                "WEB_STORAGE_URL",
                getLocalProperties().getProperty("web_storage_url")
            )
            buildConfigField(
                "String",
                "ANALYTICS_URL",
                getLocalProperties().getProperty("analytics_url")
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.constraintlayout)

    //Dagger 2 DI
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

    //VBPD
    implementation(libs.viewbindingpropertydelegate.full)

    //Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.dynamic.features.fragment)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Coil
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)

    //Material Components
    implementation(libs.material)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // ViewModel and LiveData
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Google analytics
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)
}

fun getLocalProperties() = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}
