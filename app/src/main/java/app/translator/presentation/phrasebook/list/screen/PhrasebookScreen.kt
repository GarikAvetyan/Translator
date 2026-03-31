package app.translator.presentation.phrasebook.list.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AirportShuttle
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocalPostOffice
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.translator.core_res.R
import app.translator.domain.model.PhraseCategoryType
import app.translator.presentation.common.component.AppTopBar
import app.translator.presentation.common.component.ListItemCard
import app.translator.presentation.translator.component.BottomNavItem
import app.translator.presentation.translator.component.TranslatorBottomNav
import app.translator.presentation.phrasebook.list.viewmodel.PhrasebookViewModel

@Composable
fun PhrasebookScreen(
    viewModel: PhrasebookViewModel,
    onCategorySelected: (PhraseCategoryType) -> Unit,
    onBack: () -> Unit,
    onSettings: () -> Unit,
    selectedRoute: String,
    onBottomNavSelected: (BottomNavItem) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.phrasebook_title),
                coinBalance = uiState.coinBalance,
                showBack = true,
                onBackClick = onBack
            ) {
                IconButton(onClick = onSettings) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(R.string.settings)
                    )
                }
            }
        },
        bottomBar = {
            TranslatorBottomNav(
                selectedRoute = selectedRoute,
                onItemSelected = onBottomNavSelected
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = dimensionResource(R.dimen._12dp))
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen._12dp))
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen._4dp)))
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchChanged,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = stringResource(R.string.enter_word))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                shape = RoundedCornerShape(dimensionResource(R.dimen._12dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )

            uiState.filteredCategories.forEach { category ->
                val icon = categoryIcon(category.type)
                ListItemCard(
                    icon = icon,
                    title = stringResource(category.titleResId),
                    onClick = { onCategorySelected(category.type) }
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen._12dp)))
        }
    }
}

private fun categoryIcon(type: PhraseCategoryType) = when (type) {
    PhraseCategoryType.Greetings -> Icons.Default.Group
    PhraseCategoryType.EverydayPhrases -> Icons.AutoMirrored.Filled.Chat
    PhraseCategoryType.Transport -> Icons.Default.AirportShuttle
    PhraseCategoryType.Travels -> Icons.Default.TravelExplore
    PhraseCategoryType.Hotels -> Icons.Default.Hotel
    PhraseCategoryType.Restaurant -> Icons.Default.Restaurant
    PhraseCategoryType.Directions -> Icons.Default.Directions
    PhraseCategoryType.Health -> Icons.Default.LocalHospital
    PhraseCategoryType.Purchases -> Icons.Default.LocalOffer
    PhraseCategoryType.Emergency -> Icons.Default.ReportProblem
    PhraseCategoryType.Business -> Icons.Default.BusinessCenter
    PhraseCategoryType.TimeDate -> Icons.Default.CalendarToday
    PhraseCategoryType.Numbers -> Icons.Default.FormatListNumbered
    PhraseCategoryType.Bank -> Icons.Default.AccountBalance
    PhraseCategoryType.Sports -> Icons.Default.SportsSoccer
    PhraseCategoryType.BeautySalon -> Icons.Default.ContentCut
    PhraseCategoryType.Family -> Icons.Default.Group
    PhraseCategoryType.Excursions -> Icons.Default.TravelExplore
    PhraseCategoryType.FriendlyMeeting -> Icons.AutoMirrored.Filled.Chat
    PhraseCategoryType.PostOffice -> Icons.Default.LocalPostOffice
}
