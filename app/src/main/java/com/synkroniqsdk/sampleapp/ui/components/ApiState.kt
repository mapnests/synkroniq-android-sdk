package com.synkroniqsdk.sampleapp.ui.components

/**
 * Represents the three possible states of an API call
 */

sealed class ApiState {
    data object Idle    : ApiState()   // nothing called yet
    data object Loading : ApiState()   // call in progress
    data class  Success(val response: String) : ApiState()
    data class  Error(val message: String)    : ApiState()
}