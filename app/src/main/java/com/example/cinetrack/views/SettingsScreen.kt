package com.example.cinetrack.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cinetrack.R
import com.example.cinetrack.viewmodels.SettingsViewModel


@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(),
    onThemeChange: (Boolean) -> Unit,
    onLanguageChange: (String) -> Unit
) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
    ) {
        item {
            // Apperance Section
            Text(
                text = stringResource(R.string.appearance),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        item {
            HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)
        }

        // Dark mode toggle
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.dark_mode),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = if (isDarkMode) stringResource(R.string.enabled) else stringResource(
                            R.string.disabled
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = {
                        viewModel.toggleDarkMode(it)
                        onThemeChange(it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                        checkedTrackColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

        // Data Management Section
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.data_management),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        item {
            HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)
        }

        // Clear watchlist button
        item {
            Button(
                onClick = { viewModel.clearWatchlist() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(stringResource(R.string.clear_watchlist))
            }
        }
        // Clear watched list button
        item {
            Button(
                onClick = { viewModel.clearWatched() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(stringResource(R.string.clear_watched))
            }
        }

        // Language section
        item {
            var expanded by remember { mutableStateOf(false) }
            val language by viewModel.language.collectAsState()
            val selectedLanguage = viewModel.supportedLanguages.find { it.first == language }
                ?: viewModel.supportedLanguages[0]

            Column {
                androidx.compose.material3.OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = selectedLanguage.second,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = stringResource(R.string.select_language)
                    )
                }

                // Dropdown menu with language options
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Loop through all supported languages
                    viewModel.supportedLanguages.forEach { (code, name) ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = name,
                                    fontWeight = if (code == language) FontWeight.Bold
                                    else FontWeight.Normal,
                                    color = if (code == language) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurface
                                )
                            },
                            onClick = {
                                viewModel.setLanguage(code)
                                onLanguageChange(code)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}
