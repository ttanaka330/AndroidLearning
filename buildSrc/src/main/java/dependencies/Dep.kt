package dependencies

object Dep {

    object Versions {
        const val kotlin = "1.5.32"
        const val ktlint = "0.43.2"
    }

    object Kotlin {
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2"
    }

    object Jetpack {
        private const val lifecycleVersion = "2.4.0"
        const val core = "androidx.core:core-ktx:1.7.0"
        const val appcompat = "androidx.appcompat:appcompat:1.4.0"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.2"
        const val fragment = "androidx.fragment:fragment-ktx:1.4.0"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    }

    object Room {
        private const val roomVersion = "2.4.0"
        const val compiler = "androidx.room:room-compiler:$roomVersion"
        const val ktx = "androidx.room:room-ktx:$roomVersion"
        const val runtime = "androidx.room:room-runtime:$roomVersion"
        const val test = "androidx.room:room-testing:$roomVersion"
    }

    object PlayService {
        const val map = "com.google.android.gms:play-services-maps:17.0.1"
    }

    const val material = "com.google.android.material:material:1.4.0"

    object Koin {
        private const val koinVersion = "3.1.4"
        const val android = "io.insert-koin:koin-android:$koinVersion"
        const val test = "io.insert-koin:koin-test:$koinVersion"
    }

    object Debug {
        const val stetho = "com.facebook.stetho:stetho:1.6.0"
        const val timber = "com.jakewharton.timber:timber:5.0.1"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
    }

    object AndroidTest {
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        const val junit = "androidx.test.ext:junit:1.1.3"
        const val runner = "androidx.test:runner:1.4.0"
    }
}
