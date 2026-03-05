plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.listycity"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.listycity"
        minSdk = 29
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

tasks.register<org.gradle.api.tasks.javadoc.Javadoc>("generateJavadocs") {
    description = "Generates Javadocs for main source files."
    group = "documentation"
    source = fileTree("src/main/java/com/example/listycity") {
        include("City.java", "CityList.java")
    }
    classpath = files()
    destinationDir = file("$projectDir/javadocs")
    isFailOnError = false
}
