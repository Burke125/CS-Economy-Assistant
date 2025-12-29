package com.example.cseconomyassistant.data.database

import com.example.cseconomyassistant.R
import com.example.cseconomyassistant.data.model.Equipment
import com.example.cseconomyassistant.data.model.Side

val equipment: List<Equipment> = listOf(
    Equipment(
        image = R.drawable.he_grenade,
        id = "he_grenade",
        name = "HE Grenade",
        price = 300,
        side = Side.BOTH,
        description = "The high explosive fragmentation grenade administers high damage through a wide area, making it ideal for clearing out hostile rooms."
    ),
    Equipment(
        image = R.drawable.flashbang,
        id = "flashbang",
        name = "Flashbang",
        price = 200,
        side = Side.BOTH,
        description = "The non-lethal flashbang grenade temporarily blinds anybody within its concussive blast, making it perfect for flushing out closed-in areas. Its loud explosion also temporarily masks the sound of footsteps."
    ),
    Equipment(
        image = R.drawable.smoke_grenade,
        id = "smoke_grenade",
        name = "Smoke Grenade",
        price = 300,
        side = Side.BOTH,
        description = "The smoke grenade creates a medium-area smoke screen. It can effectively hide your team from snipers, or even just create a useful distraction."
    ),
    Equipment(
        image = R.drawable.molotov,
        id = "molotov",
        name = "Molotov",
        price = 400,
        side = Side.T,
        description = "The Molotov is a powerful and unpredictable area denial weapon that bursts into flames when thrown on the ground, injuring any player in its radius."
    ),
    Equipment(
        image = R.drawable.incendiary_grenade,
        id = "incendiary_grenade",
        name = "Incendiary Grenade",
        price = 500,
        side = Side.CT,
        description = "When thrown, the incendiary grenade releases a high-temperature chemical reaction capable of burning anyone within its wide blast radius."
    ),
    Equipment(
        image = R.drawable.decoy_grenade,
        id = "decoy_grenade",
        name = "Decoy Grenade",
        price = 50,
        side = Side.BOTH,
        description = "When thrown, the decoy grenade emulates the sound of the most powerful weapon you are carrying, creating the illusion of additional supporting forces."
    ),
    Equipment(
        image = R.drawable.defuse_kit,
        id = "defuse_kit",
        name = "Defuse Kit",
        price = 200,
        side = Side.CT,
        description = "This item is a defuse kit. Once found it greatly reduces the time needed to defuse a live bomb."
    ),
    Equipment(
        image = R.drawable.kevlar,
        id = "kevlar",
        name = "Kevlar",
        price = 650,
        side = Side.BOTH,
        description = "Kevlar provides body armor that reduces incoming damage by absorbing bullets, allowing players to survive longer in gunfights. It helps maintain accuracy by preventing heavy aim-punch when taking hits."
    ),
    Equipment(
        image = R.drawable.kevlar_and_helmet,
        id = "kevlar+helmet",
        name = "Kevlar+Helmet",
        price = 1000,
        side = Side.BOTH,
        description = "Kevlar + Helmet offers full protective armor, reducing incoming damage and preventing instant headshot kills from weaker weapons. It also eliminates aim-punch, giving players better stability and survivability in firefights."
    )
)