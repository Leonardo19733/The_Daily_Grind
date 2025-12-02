plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.proyectop3"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.proyectop3"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    // --- LO QUE YA TENÍAS (Versión Catalog) ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // --- AGREGADAS PARA EL PROYECTO (Forma Directa) ---
    // Necesario para el Menú y la lista del Perfil (RecyclerView)
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Necesario para las tarjetas redondeadas de los productos (CardView)
    implementation("androidx.cardview:cardview:1.0.0")

    // Opcional pero recomendado para manejo de Fragments más fácil
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // --- TESTING (Lo que ya tenías) ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}