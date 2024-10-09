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

package model

import kotlinx.serialization.Serializable

@Suppress("UNUSED")
@Serializable
enum class ZoneType {

    Barren,
    BoggyMarsh,
    CarrotQuarry,
    CrystalCaverns,
    Forest,
    FrozenWastes,
    IceCaves,
    MagmaCore,
    Metallic,
    Moo,
    Ocean,
    OilField,
    Radioactive,
    RocketInterior,
    Rust,
    Sandstone,
    Space,
    SugarWoods,
    Swamp,
    ToxicJungle,
    Wasteland
}