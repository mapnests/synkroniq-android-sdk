package com.synkroniqsdk.sampleapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synkroniqsdk.sampleapp.ui.theme.*

@Composable
fun ApiSectionCard(
    label: String,                          // e.g. "getServiceCategories"
    state: ApiState,                        // current call state
    onCallClick: () -> Unit,                // what happens when Call is tapped
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {

        // Gray monospace label above the card
        Text(
            text = label,
            fontSize = 13.sp,
            color = TextSecondary,
            fontFamily = FontFamily.Monospace
        )

        Spacer(modifier = Modifier.height(6.dp))

        // White card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {

                // Call button
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextButton(
                        onClick = onCallClick,
                        enabled = state !is ApiState.Loading,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Call",
                            color = SynkroniqBlue,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    if (state is ApiState.Loading) {
                        Spacer(modifier = Modifier.width(10.dp))
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                            color = SynkroniqBlue
                        )
                    }
                }

                // Response area (only shown after a call)
                if (state is ApiState.Success || state is ApiState.Error) {

                    HorizontalDivider(
                        color = DividerColor,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    when (state) {
                        is ApiState.Success -> ResponseBox(text = state.response)
                        is ApiState.Error   -> ErrorBox(message = state.message)
                        else -> Unit
                    }
                }
            }
        }
    }
}
 /**
  * Response box with Copy button
 */
@Composable
private fun ResponseBox(text: String) {
    val clipboard = LocalClipboardManager.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = BackgroundGray, shape = RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {
        // Copy button
        Row(
            modifier = Modifier.align(Alignment.TopEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { clipboard.setText(AnnotatedString(text)) },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copy",
                    tint = SynkroniqBlue,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text("Copy", color = SynkroniqBlue, fontSize = 13.sp)
        }

        // Response text
        Text(
            text = text,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            lineHeight = 18.sp,
            modifier = Modifier.padding(top = 30.dp, end = 56.dp)
        )
    }
}

/**
 * Error box
 */
@Composable
private fun ErrorBox(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = ErrorRed.copy(alpha = 0.08f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp)
    ) {
        Text(
            text = "Error: $message",
            color = ErrorRed,
            fontSize = 13.sp,
            fontFamily = FontFamily.Monospace
        )
    }
}