package com.gurhan.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(preferenceManager: com.gurhan.util.PreferenceManager) {
    val darkMode by preferenceManager.darkModeFlow.collectAsState()
    val keepScreenOn by preferenceManager.keepScreenOnFlow.collectAsState()
    
    // Non-reactive but needs local state for the slider to feel smooth
    var fontSize by remember { mutableStateOf(preferenceManager.getFontSize()) }
    var notificationsEnabled by remember { mutableStateOf(preferenceManager.areNotificationsEnabled()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Sazlamalar",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        // --- GÖRÜNÜŞ (Appearance) ---
        SectionHeader("Görünüş")
        
        SettingsCard {
            SettingsToggleItem(
                title = "Garaňky tertip",
                subtitle = if (darkMode == 2) "Açyk" else if (darkMode == 1) "Öçük" else "Sitema görä",
                checked = darkMode == 2,
                onCheckedChange = { 
                    val newMode = if (it) 2 else 1
                    preferenceManager.setDarkMode(newMode)
                }
            )
            
            Divider(
                modifier = Modifier.padding(horizontal = 16.dp), 
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            )
            
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Harp ölçegi",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Okalan metniň ululygy: ${(fontSize * 100).toInt()}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Slider(
                    value = fontSize,
                    onValueChange = { 
                        fontSize = it
                        preferenceManager.setFontSize(it)
                    },
                    valueRange = 0.8f..1.5f,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.primary,
                        activeTrackColor = MaterialTheme.colorScheme.primary,
                        inactiveTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.24f)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- UMUMY (General) ---
        SectionHeader("Umumy")
        
        SettingsCard {
            SettingsToggleItem(
                title = "Ekrany açyk sakla",
                subtitle = "Okap durkaňyz ekran öçmez",
                checked = keepScreenOn,
                onCheckedChange = { 
                    preferenceManager.setKeepScreenOn(it)
                }
            )
            
            Divider(
                modifier = Modifier.padding(horizontal = 16.dp), 
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            )
            
            SettingsToggleItem(
                title = "Bildirişler",
                subtitle = "Günüň aýaty barada habar biýr",
                checked = notificationsEnabled,
                onCheckedChange = { 
                    notificationsEnabled = it
                    preferenceManager.setNotificationsEnabled(it)
                }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
        
        Text(
            text = "Wersiýa 1.0.0",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
    )
}

@Composable
fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(content = content)
    }
}

@Composable
fun SettingsToggleItem(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.surface,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
    }
}
