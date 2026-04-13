package com.synkroniqsdk.sampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.synkroniqsdk.sampleapp.navigation.AppNavigation
import com.synkroniqsdk.sampleapp.ui.theme.SynkroniqSDKSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SynkroniqSDKSampleTheme {
                AppNavigation()
            }
        }
    }
}