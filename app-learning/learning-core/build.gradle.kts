plugins {
    alias(libs.plugins.learning.android.library)
}

android {
    namespace = "com.github.ttanaka330.android.learning"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            consumerProguardFiles("consumer-rules.pro")
        }
    }
}

dependencies {
    implementation(libs.material)
    testImplementation(libs.junit)
}
