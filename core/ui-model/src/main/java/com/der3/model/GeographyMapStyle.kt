package com.der3.model

enum class GeographyMapStyle(
    val displayName: String,
    val value: String,
    val isDark: Boolean
) {
    OsmCarto("OSM Carto", "osm-carto", false),
    OsmBright("OSM Bright", "osm-bright", false),
    OsmBrightGrey("OSM Bright Grey", "osm-bright-grey", false),
    OsmBrightSmooth("OSM Bright Smooth", "osm-bright-smooth", false),
    KlokantechBasic("Klokantech Basic", "klokantech-basic", false),
    OsmLiberty("OSM Liberty", "osm-liberty", false),
    Maptiler3d("Maptiler 3D", "maptiler-3d", false),
    Toner("Toner", "toner", false),
    TonerGrey("Toner Grey", "toner-grey", false),
    Positron("Positron", "positron", false),
    PositronBlue("Positron Blue", "positron-blue", false),
    PositronRed("Positron Red", "positron-red", false),
    DarkMatter("Dark Matter", "dark-matter", true),
    DarkMatterBrown("Dark Matter Brown", "dark-matter-brown", true),
    DarkMatterDarkGrey("Dark Matter Dark Grey", "dark-matter-dark-grey", true),
    DarkMatterDarkPurple("Dark Matter Dark Purple", "dark-matter-dark-purple", true),
    DarkMatterPurpleRoads("Dark Matter Purple Roads", "dark-matter-purple-roads", true),
    DarkMatterYellowRoads("Dark Matter Yellow Roads", "dark-matter-yellow-roads", true);

    fun previewUrl(apiKey: String): String {
        return "https://maps.geoapify.com/v1/tile/$value/10/543/354.png?apiKey=$apiKey"
    }

    fun styleUrl(apiKey: String): String {
        return "https://maps.geoapify.com/v1/styles/$value/style.json?apiKey=$apiKey"
    }

    fun tileUrl(apiKey: String): String {
        return "https://maps.geoapify.com/v1/tile/$value/{z}/{x}/{y}.png?apiKey=$apiKey"
    }

    companion object {
        fun getLightStyles() = entries.filter { !it.isDark }
        fun getDarkStyles() = entries.filter { it.isDark }
        fun fromValue(value: String): GeographyMapStyle? = entries.find { it.value == value }
    }
}
