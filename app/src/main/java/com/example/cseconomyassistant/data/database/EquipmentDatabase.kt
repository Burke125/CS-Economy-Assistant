package com.example.cseconomyassistant.data.database

import com.example.cseconomyassistant.R
import com.example.cseconomyassistant.data.model.Equipment
import com.example.cseconomyassistant.data.model.EquipmentSlot
import com.example.cseconomyassistant.data.model.Side

val equipment: List<Equipment> = listOf(
    Equipment(
        image = R.drawable.equipment_he_grenade,
        id = "he_grenade",
        name = "HE Grenade",
        price = 300,
        side = Side.BOTH,
        equipmentSlot = EquipmentSlot.UTILITY,
        description = "The high explosive fragmentation grenade administers high damage through a wide area, making it ideal for clearing out hostile rooms."
    ),
    Equipment(
        image = R.drawable.equipment_flashbang,
        id = "flashbang",
        name = "Flashbang",
        price = 200,
        side = Side.BOTH,
        equipmentSlot = EquipmentSlot.UTILITY,
        description = "The non-lethal flashbang grenade temporarily blinds anybody within its concussive blast, making it perfect for flushing out closed-in areas. Its loud explosion also temporarily masks the sound of footsteps."
    ),
    Equipment(
        image = R.drawable.equipment_smoke_grenade,
        id = "smoke_grenade",
        name = "Smoke Grenade",
        price = 300,
        side = Side.BOTH,
        equipmentSlot = EquipmentSlot.UTILITY,
        description = "The smoke grenade creates a medium-area smoke screen. It can effectively hide your team from snipers, or even just create a useful distraction."
    ),
    Equipment(
        image = R.drawable.equipment_molotov,
        id = "molotov",
        name = "Molotov",
        price = 400,
        side = Side.T,
        equipmentSlot = EquipmentSlot.UTILITY,
        description = "The Molotov is a powerful and unpredictable area denial weapon that bursts into flames when thrown on the ground, injuring any player in its radius."
    ),
    Equipment(
        image = R.drawable.equipment_incendiary_grenade,
        id = "incendiary_grenade",
        name = "Incendiary Grenade",
        price = 500,
        side = Side.CT,
        equipmentSlot = EquipmentSlot.UTILITY,
        description = "When thrown, the incendiary grenade releases a high-temperature chemical reaction capable of burning anyone within its wide blast radius."
    ),
    Equipment(
        image = R.drawable.equipment_decoy_grenade,
        id = "decoy_grenade",
        name = "Decoy Grenade",
        price = 50,
        side = Side.BOTH,
        equipmentSlot = EquipmentSlot.UTILITY,
        description = "When thrown, the decoy grenade emulates the sound of the most powerful weapon you are carrying, creating the illusion of additional supporting forces."
    ),
    Equipment(
        image = R.drawable.equipment_defuse_kit,
        id = "defuse_kit",
        name = "Defuse Kit",
        price = 200,
        side = Side.CT,
        equipmentSlot = EquipmentSlot.NONE,
        description = "This item is a defuse kit. Once found it greatly reduces the time needed to defuse a live bomb."
    ),
    Equipment(
        image = R.drawable.equipment_kevlar,
        id = "kevlar",
        name = "Kevlar",
        price = 650,
        side = Side.BOTH,
        equipmentSlot = EquipmentSlot.NONE,
        description = "Kevlar provides body armor that reduces incoming damage by absorbing bullets, allowing players to survive longer in gunfights. It helps maintain accuracy by preventing heavy aim-punch when taking hits."
    ),
    Equipment(
        image = R.drawable.equipment_kevlar_and_helmet,
        id = "kevlar+helmet",
        name = "Kevlar+Helmet",
        price = 1000,
        side = Side.BOTH,
        equipmentSlot = EquipmentSlot.NONE,
        description = "Kevlar + Helmet offers full protective armor, reducing incoming damage and preventing instant headshot kills from weaker weapons. It also eliminates aim-punch, giving players better stability and survivability in firefights."
    )
)