package com.carlostorres.pokedexapp.ui.detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.carlostorres.pokedexapp.domain.model.PokemonDetails
import com.carlostorres.pokedexapp.domain.model.PokemonStat
import com.carlostorres.pokedexapp.presentation.detail.PokemonDetailUiState
import com.carlostorres.pokedexapp.presentation.detail.PokemonDetailViewModel
import com.carlostorres.pokedexapp.ui.theme.PokemonTypeColors

@Composable
fun PokemonDetailScreen(
    navController: NavController,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is PokemonDetailUiState.Loading -> {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }
            is PokemonDetailUiState.Success -> {
                PokemonDetailContent(
                    details = state.details,
                    onBackClick = { navController.popBackStack() }
                )
            }
            is PokemonDetailUiState.Error -> {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(text = "Error: ${state.message}")
                }
            }
        }
    }
}

@Composable
fun PokemonDetailContent(
    details: PokemonDetails,
    onBackClick: () -> Unit
) {
    val typeColor = PokemonTypeColors.getColor(details.types.first())
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState) // Hacemos la pantalla scrollable
    ) {
        // --- Header Section ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(typeColor)
                .padding(top = 32.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .clickable { onBackClick() }
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = String.format("#%03d", details.id),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Text(
                    text = details.name,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(details.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = details.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // --- Body Section ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Types Section ---
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                details.types.forEach { typeName ->
                    TypeBadge(typeName = typeName)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Physical Stats ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PhysicalStat(name = "Weight", value = "${details.weight} kg")
                PhysicalStat(name = "Height", value = "${details.height} m")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Base Stats ---
            Text(
                text = "Base Stats",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = typeColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            details.stats.forEach { stat ->
                StatBar(stat = stat, color = typeColor)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun TypeBadge(typeName: String) {
    val typeColor = PokemonTypeColors.getColor(typeName)
    Box(
        modifier = Modifier
            .background(color = typeColor, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = typeName,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun PhysicalStat(name: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        Text(text = name, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
    }
}

@Composable
fun StatBar(stat: PokemonStat, color: Color) {
    var animationPlayed by remember { mutableStateOf(false) }
    val currentStatValue = animateFloatAsState(
        targetValue = if (animationPlayed) stat.value.toFloat() else 0f,
        animationSpec = tween(durationMillis = 1000, delayMillis = 100),
        label = "statAnimation"
    ).value

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    val maxStat = 255f

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stat.name.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.3f),
            textAlign = TextAlign.End
        )
        Text(
            text = stat.value.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(30.dp)
        )
        LinearProgressIndicator(
            progress = { currentStatValue / maxStat },
            modifier = Modifier
                .weight(0.7f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.3f)
        )
    }
}