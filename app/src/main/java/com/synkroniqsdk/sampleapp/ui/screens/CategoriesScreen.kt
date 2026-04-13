package com.synkroniqsdk.sampleapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.synkroniqsdk.sampleapp.SdkConfig
import com.synkroniqsdk.sampleapp.ui.components.ApiSectionCard
import com.synkroniqsdk.sampleapp.ui.components.ApiState
import com.technonext.synkroniq.SynkroniqSDK
import com.technonext.synkroniq.data.ApiError
import org.json.JSONObject

@Composable
fun CategoriesScreen(modifier: Modifier) {

    /**
     * State for each API call
     * mutableStateOf → Compose re-draws this section whenever the value changes
     */
    var categoryListState  by remember { mutableStateOf<ApiState>(ApiState.Idle) }
    var categoryByIdState  by remember { mutableStateOf<ApiState>(ApiState.Idle) }
    var searchState        by remember { mutableStateOf<ApiState>(ApiState.Idle) }
    /**
     * One SDK instance shared across all calls on this screen
     */
    val sdk = remember {
        SynkroniqSDK.getInstance(SdkConfig.API_KEY, SdkConfig.PLATFORM, SdkConfig.TIMEOUT_MS)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text("Service Categories", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(24.dp))

        // getServiceCategoryList
        ApiSectionCard(
            label = "getServiceCategories",
            state = categoryListState,
            onCallClick = {
                categoryListState = ApiState.Loading
                Thread {
                    try {
                        val body = JSONObject().apply {
                            put("uid", 20)
                            put("email", "fahim@gmail.com")
                            put("firstname", "fahim")
                            put("lastname", "ashhab")
                            put("mobile", "01556839245")
                            put("designation", "Supervisor")
                        }

                        sdk.getServiceCategoryList(body, object : SynkroniqSDK.ResponseCallback<String> {
                            override fun onSuccess(response: String) {
                                categoryListState = ApiState.Success(response)
                            }
                            override fun onError(error: ApiError) {
                                categoryListState = ApiState.Error(error.message ?: "Unknown error")
                            }
                        })
                    } catch (e: Exception) {
                        categoryListState = ApiState.Error(e.message ?: "Exception occurred")
                    }
                }.start()
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // getServiceCategoryById
        ApiSectionCard(
            label = "getServiceCategoryById",
            state = categoryByIdState,
            onCallClick = {
                categoryByIdState = ApiState.Loading
                Thread {
                    try {
                        val body = JSONObject().apply {
                            put("uid", 20)
                            put("email", "fahim@gmail.com")
                            put("firstname", "fahim")
                            put("lastname", "ashhab")
                            put("mobile", "01556839245")
                            put("designation", "Supervisor")
                        }

                        sdk.getServiceCategory("1", body, object : SynkroniqSDK.ResponseCallback<String> {
                            override fun onSuccess(response: String) {
                                categoryByIdState = ApiState.Success(response)
                            }
                            override fun onError(error: ApiError) {
                                categoryByIdState = ApiState.Error(error.message ?: "Unknown error")
                            }
                        })
                    } catch (e: Exception) {
                        categoryByIdState = ApiState.Error(e.message ?: "Exception occurred")
                    }
                }.start()
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // searchServiceCategories
        ApiSectionCard(
            label = "searchServiceCategories",
            state = searchState,
            onCallClick = {
                searchState = ApiState.Loading
                Thread {
                    try {
                        val body = JSONObject().apply {
                            put("uid", 20)
                            put("email", "fahim@gmail.com")
                            put("firstname", "fahim")
                            put("lastname", "ashhab")
                            put("mobile", "01556839245")
                            put("designation", "Supervisor")
                            put("leaf_parents", "true")
                            put("query", "p")
                        }

                        sdk.getServiceCategorySearch(body, object : SynkroniqSDK.ResponseCallback<String> {
                            override fun onSuccess(response: String) {
                                searchState = ApiState.Success(response)
                            }
                            override fun onError(error: ApiError) {
                                searchState = ApiState.Error(error.message ?: "Unknown error")
                            }
                        })
                    } catch (e: Exception) {
                        searchState = ApiState.Error(e.message ?: "Exception occurred")
                    }
                }.start()
            }
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}