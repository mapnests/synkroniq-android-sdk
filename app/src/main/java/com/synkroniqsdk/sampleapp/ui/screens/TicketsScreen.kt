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
fun TicketsScreen(modifier: Modifier) {

    var ticketsState      by remember { mutableStateOf<ApiState>(ApiState.Idle) }
    var createTicketState by remember { mutableStateOf<ApiState>(ApiState.Idle) }
    var ticketStatesState by remember { mutableStateOf<ApiState>(ApiState.Idle) }

    val sdk = remember {
        SynkroniqSDK.getInstance(SdkConfig.API_KEY, SdkConfig.PLATFORM, SdkConfig.TIMEOUT_MS)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text("Tickets", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(24.dp))

        // getTicketsList
        ApiSectionCard(
            label = "getTickets",
            state = ticketsState,
            onCallClick = {
                ticketsState = ApiState.Loading
                Thread {
                    try {
                        val body = JSONObject().apply {
                            put("uid", 20)
                            put("email", "fahim@gmail.com")
                            put("firstname", "fahim")
                            put("lastname", "ashhab")
                            put("mobile", "01556839245")
                            put("designation", "Supervisor")
                            put("from_date", "2024-01-01T00:00:00Z")
                            put("to_date", "2025-12-15T23:59:59Z")
                        }

                        sdk.getTicketsList(body, object : SynkroniqSDK.ResponseCallback<String> {
                            override fun onSuccess(response: String) {
                                ticketsState = ApiState.Success(response)
                            }
                            override fun onError(error: ApiError) {
                                ticketsState = ApiState.Error(error.message ?: "Unknown error")
                            }
                        })
                    } catch (e: Exception) {
                        ticketsState = ApiState.Error(e.message ?: "Exception occurred")
                    }
                }.start()
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // createTicket
        ApiSectionCard(
            label = "createTicket",
            state = createTicketState,
            onCallClick = {
                createTicketState = ApiState.Loading
                Thread {
                    try {
                        val body = JSONObject().apply {
                            put("uid", 20)
                            put("email", "fahim@gmail.com")
                            put("firstname", "fahim")
                            put("lastname", "ashhab")
                            put("mobile", "01556839245")
                            put("designation", "Supervisor")
                            put("body", "issue details")
                            put("service_category_id", 1)
                            put("title", "VPN Connectivity Issue - Urgent")

                            put("attachments", JSONArray().apply {
                                put(JSONObject().apply {
                                    put("filename", "error_log.txt")
                                    put("data", "U2FtcGxlIGJhc2U2NCBlbmNvZGVkIGNvbnRlbnQ=")
                                    put("mime-type", "text/plain")
                                    put("content_type", "text/plain")
                                })
                            })
                        }

                        sdk.createTicket(body, object : SynkroniqSDK.ResponseCallback<String> {
                            override fun onSuccess(response: String) {
                                createTicketState = ApiState.Success(response)
                            }
                            override fun onError(error: ApiError) {
                                createTicketState = ApiState.Error(error.message ?: "Unknown error")
                            }
                        })
                    } catch (e: Exception) {
                        createTicketState = ApiState.Error(e.message ?: "Exception occurred")
                    }
                }.start()
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // getTicketStatesList
        ApiSectionCard(
            label = "getTicketStates",
            state = ticketStatesState,
            onCallClick = {
                ticketStatesState = ApiState.Loading
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

                        sdk.getTicketsStatesList(body, object : SynkroniqSDK.ResponseCallback<String> {
                            override fun onSuccess(response: String) {
                                ticketStatesState = ApiState.Success(response)
                            }
                            override fun onError(error: ApiError) {
                                ticketStatesState = ApiState.Error(error.message ?: "Unknown error")
                            }
                        })
                    } catch (e: Exception) {
                        ticketStatesState = ApiState.Error(e.message ?: "Exception occurred")
                    }
                }.start()
            }
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}