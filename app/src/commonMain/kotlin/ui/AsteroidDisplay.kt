/*
 * ONI Seed Browser
 * Copyright (C) 2024 Stefan Oltmann
 * https://stefan-oltmann.de/oni-seed-browser
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import model.Asteroid
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.theme.DefaultSpacer
import ui.theme.HalfSpacer
import ui.theme.anthraticeTransparentBackgroundColor
import ui.theme.cardColorBackground
import ui.theme.defaultPadding
import ui.theme.defaultRoundedCornerShape
import ui.theme.defaultSpacing
import ui.theme.halfPadding
import ui.theme.halfSpacing
import ui.theme.lightGrayTransparentBorderColor
import ui.theme.minimalRoundedCornerShape
import ui.theme.surfaceVariantColor

val countBackground = Color.Black.copy(alpha = 0.3F)

@Composable
fun AsteroidView(
    asteroid: Asteroid,
    isStarterAsteroid: Boolean,
    isSelected: Boolean,
    showDetails: () -> Unit,
    showMap: () -> Unit
) {

    val hovered = remember { mutableStateOf(false) }
    val density = LocalDensity.current.density
    val localScreenWidth = remember { mutableStateOf(0) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                if (hovered.value)
                    surfaceVariantColor
                else
                    cardColorBackground,
                defaultRoundedCornerShape
            )
            .border(
                if (isStarterAsteroid) 1.dp else 0.dp,
                if (isSelected) Color.Yellow else lightGrayTransparentBorderColor,
                defaultRoundedCornerShape
            )
            .defaultPadding()
            .fillMaxWidth()
            .noRippleClickable(showDetails)
            .onHover(hovered)
            .onSizeChanged { localScreenWidth.value = (it.width / density).roundToInt() }
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(124.dp)
                .background(Color.Black, defaultRoundedCornerShape)
        ) {

            Image(
                painter = painterResource(getAsteroidTypeDrawable(asteroid.id)),
                contentDescription = null,
                modifier = Modifier.size(108.dp)
            )

            ShowMapButton(
                onClick = showMap,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }

        DefaultSpacer()

        BoxWithConstraints {

            val maxWidth = maxWidth

            Column(
                horizontalAlignment = Alignment.Start
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(halfSpacing),
                    modifier = Modifier.height(24.dp)
                ) {

                    Text(
                        text = stringResource(asteroid.id.stringResource),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )

                    for (worldTrait in asteroid.worldTraits.sorted()) {

                        TooltipContainer(
                            tooltipContent = {
                                GenericTooltip {
                                    Text(
                                        text = stringResource(worldTrait.stringResource),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.padding(
                                            horizontal = defaultSpacing,
                                            vertical = halfSpacing
                                        )
                                    )
                                }
                            },
                            yOffset = 15
                        ) {

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .background(
                                        anthraticeTransparentBackgroundColor,
                                        minimalRoundedCornerShape
                                    )
                                    .border(
                                        2.dp,
                                        worldTrait.rating.color,
                                        minimalRoundedCornerShape
                                    )
                                    .size(24.dp)
                            ) {

                                Image(
                                    painter = painterResource(getWorldTraitDrawable(worldTrait)),
                                    contentDescription = null,
                                    modifier = Modifier.halfPadding()
                                )
                            }
                        }
                    }
                }

                if (localScreenWidth.value > 300) {

                    HalfSpacer()

                    GeysersRow(asteroid.geysers, maxWidth, isStarterAsteroid)

                    HalfSpacer()

                    PointOfInterestsRow(asteroid.pointsOfInterest, maxWidth, isStarterAsteroid)
                } else if (localScreenWidth.value > 200) {

                    val geyserCount = asteroid.geysers.count()
                    if (geyserCount > 0) {
                        HalfSpacer()
                        Text(
                            text = "${geyserCount}x Geysers",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                    val poiCount = asteroid.pointsOfInterest.count()
                    if (poiCount > 0) {
                        HalfSpacer()
                        Text(
                            text = "${poiCount}x POIs",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            }
        }
    }
}

