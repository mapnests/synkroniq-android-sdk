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
import org.json.JSONArray
import org.json.JSONObject

@Composable
fun ArticlesScreen(modifier: Modifier) {

    var articleListState   by remember { mutableStateOf<ApiState>(ApiState.Idle) }
    var createArticleState by remember { mutableStateOf<ApiState>(ApiState.Idle) }

    val sdk = remember {
        SynkroniqSDK.getInstance(SdkConfig.API_KEY, SdkConfig.PLATFORM, SdkConfig.TIMEOUT_MS)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text("Ticket Articles", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(24.dp))

        // getTicketArticleList
        ApiSectionCard(
            label = "getTicketArticles",
            state = articleListState,
            onCallClick = {
                articleListState = ApiState.Loading
                Thread {
                    try {
                        val body = JSONObject().apply {
                            put("uid", 20)
                            put("email", "fahim@gmail.com")
                            put("firstname", "fahim")
                            put("lastname", "ashhab")
                            put("mobile", "01556839245")
                            put("designation", "Supervisor")
                            put("ticket_id", "108")
                            put("page", "1")
                            put("limit", "50")
                        }

                        sdk.getTicketsArticleList(body, object : SynkroniqSDK.ResponseCallback<String> {
                            override fun onSuccess(response: String) {
                                articleListState = ApiState.Success(response)
                            }
                            override fun onError(error: ApiError) {
                                articleListState = ApiState.Error(error.message ?: "Unknown error")
                            }
                        })
                    } catch (e: Exception) {
                        articleListState = ApiState.Error(e.message ?: "Exception occurred")
                    }
                }.start()
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // createTicketArticle
        ApiSectionCard(
            label = "createTicketArticle",
            state = createArticleState,
            onCallClick = {
                createArticleState = ApiState.Loading
                Thread {
                    try {

                        val body = JSONObject().apply {
                            put("uid", 20)
                            put("email", "fahim@gmail.com")
                            put("firstname", "fahim")
                            put("lastname", "ashhab")
                            put("mobile", "01556839245")
                            put("designation", "Supervisor")
                            put("ticket_id", 108)
                            put("body", "sample body")

                            put("attachments", JSONArray().apply {
                                put(JSONObject().apply {
                                    put("filename", "maintenance_report.pdf")
                                    put("data", "JVBERi0xLjQKJ...(base64_data)...")
                                    put("mime-type", "application/pdf")
                                    put("content_type", "application/pdf")
                                })
                            })
                        }

                        sdk.createTicketArticle(body, object : SynkroniqSDK.ResponseCallback<String> {
                            override fun onSuccess(response: String) {
                                createArticleState = ApiState.Success(response)
                            }
                            override fun onError(error: ApiError) {
                                createArticleState = ApiState.Error(error.message ?: "Unknown error")
                            }
                        })
                    } catch (e: Exception) {
                        createArticleState = ApiState.Error(e.message ?: "Exception occurred")
                    }
                }.start()
            }
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}