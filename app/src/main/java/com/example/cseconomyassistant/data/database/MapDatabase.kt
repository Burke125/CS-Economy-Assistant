package com.example.cseconomyassistant.data.database

import com.example.cseconomyassistant.R
import com.example.cseconomyassistant.data.model.GameMap
import com.example.cseconomyassistant.data.model.MapVisual

val gameMaps: List<GameMap> = listOf(
    GameMap(
        images = listOf(
            MapVisual("Overview", R.drawable.ancient),
            MapVisual("Radar", R.drawable.ancient_radar)
        ),
        id = "ancient",
        name = "Ancient",
        ctWinRate = 51.8f,
        tWinRate = 48.2f,
        description = "A dense, jungle-surrounded archaeological site with stone structures, narrow pathways, and visually striking ancient ruins.",
        location = "Mexico",
        tip = "A structured and discipline-focused map emphasizing strong defensive setups, calculated utility usage, and precise control of key chokepoints."
    ),
    GameMap(
        images = listOf(
            MapVisual("Overview", R.drawable.dust_2),
            MapVisual("Radar", R.drawable.dust_2_radar)
        ),
        id = "dust_2",
        name = "Dust II",
        ctWinRate = 49f,
        tWinRate = 51f,
        description = "An iconic desert battleground with instantly recognizable long sightlines, open spaces, and classic Counter-Strike architecture.",
        location = "Morocco",
        tip = "An aim-centric and mechanically demanding map that rewards strong individual performance, precise peeks, and simple yet effective tactical structures."
    ),
    GameMap(
        images = listOf(
            MapVisual("Overview", R.drawable.inferno),
            MapVisual("Radar", R.drawable.inferno_radar)
        ),
        id = "inferno",
        name = "Inferno",
        ctWinRate = 49.7f,
        tWinRate = 50.3f,
        description = "A compact European village with narrow streets, claustrophobic chokepoints, and memorable battlegrounds like Banana, Apartments, and the A courtyard.",
        location = "Italy",
        tip = "A highly tactical and utility-dependent map, emphasizing map control, disciplined rotations, and synchronized grenade usage to dictate pace and territory."
    ),
    GameMap(
        images = listOf(
            MapVisual("Overview", R.drawable.mirage),
            MapVisual("Radar", R.drawable.mirage_radar)
        ),
        id = "mirage",
        name = "Mirage",
        ctWinRate = 52.5f,
        tWinRate = 47.5f,
        description = "A lively desert metropolis featuring open courtyards, tight corridors, and layered architecture that creates dynamic and visually iconic engagements.",
        location = "Morocco",
        tip = "A fundamentals-driven, balanced map that rewards strong mid control, clean aim duels, and coordinated team play across both short and long ranges."
    ),
    GameMap(
        images = listOf(
            MapVisual("Overview", R.drawable.nuke),
            MapVisual("Upper Radar", R.drawable.nuke_upper_radar),
            MapVisual("Lower Radar", R.drawable.nuke_lower_radar)
        ),
        id = "nuke",
        name = "Nuke",
        ctWinRate = 54.6f,
        tWinRate = 45.4f,
        description = "A secure industrial facility with multi-layered environments, enclosed bombsites, and a complex vertical structure that defines its unique identity.",
        location = "United States",
        tip = "A strategically demanding and rotation-heavy map that rewards deep map knowledge, strong communication, and advanced team coordination across multiple levels."
    ),
    GameMap(
        images = listOf(
            MapVisual("Overview", R.drawable.overpass),
            MapVisual("Radar", R.drawable.overpass_radar)
        ),
        id = "overpass",
        name = "Overpass",
        ctWinRate = 53.5f,
        tWinRate = 46.5f,
        description = "An urban European setting combining wide outdoor areas, underground tunnels, and elevated positions with a strong environmental identity.",
        location = "Germany",
        tip = "A control-oriented map focused on territory dominance, proactive CT play, long-range duels, and layered mid-round decision-making."
    ),
    GameMap(
        images = listOf(
            MapVisual("Overview", R.drawable.train),
            MapVisual("Upper Radar", R.drawable.train_upper_radar),
            MapVisual("Lower Radar", R.drawable.train_lower_radar)
        ),
        id = "train",
        name = "Train",
        ctWinRate = 54.8f,
        tWinRate = 45.2f,
        description = "A cold industrial rail yard filled with cargo trains, tight angles, and layered vertical cover across both bombsites.",
        location = "Russia",
        tip = "A positioning-focused map that highly rewards discipline, angle control, strong crossfire setups, and coordinated team movement."
    ),
    GameMap(
        images = listOf(
            MapVisual("Overview", R.drawable.anubis),
            MapVisual("Radar", R.drawable.anubis_radar)
        ),
        id = "anubis",
        name = "Anubis",
        ctWinRate = 44.6f,
        tWinRate = 55.4f,
        description = "A visually rich Egyptian-inspired environment with waterways, detailed architecture, and well-defined combat spaces.",
        location = "Egypt",
        tip = "A tactically flexible, mid-control-oriented map that rewards coordinated map pressure, creative utility usage, and well-timed team executions."
    ),
    GameMap(
        images = listOf(
            MapVisual("Overview", R.drawable.vertigo),
            MapVisual("Upper Radar", R.drawable.vertigo_upper_radar),
            MapVisual("Lower Radar", R.drawable.vertigo_lower_radar)
        ),
        id = "vertigo",
        name = "Vertigo",
        ctWinRate = 49.1f,
        tWinRate = 50.9f,
        description = "A modern skyscraper construction site with open platforms, risky walkways, and vertical elements that create constant tension and unpredictability.",
        location = "United States",
        tip = "A high-pressure, fast-paced map that rewards aggressive play, sharp mechanics, and confident executes while punishing hesitation and poor positioning."
    ),
)