package com.example.cseconomyassistant.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cseconomyassistant.data.database.weapons
import com.example.cseconomyassistant.data.model.*
import com.example.cseconomyassistant.data.repository.LoadoutRepository
import kotlinx.coroutines.launch

class LoadoutViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = LoadoutRepository(application)

    val selectedSide = mutableStateOf(Side.CT)

    private val ctLoadout = mutableStateOf(defaultCtLoadout())
    private val tLoadout = mutableStateOf(defaultTLoadout())

    init {
        viewModelScope.launch {
            repository.loadCt()?.let { ctLoadout.value = it }
            repository.loadT()?.let { tLoadout.value = it }
        }
    }

    fun currentLoadout(): LoadoutState {
        return if (selectedSide.value == Side.CT) ctLoadout.value else tLoadout.value
    }

    fun updateLoadout(loadout: LoadoutState) {
        if (selectedSide.value == Side.CT) {
            ctLoadout.value = loadout
        } else {
            tLoadout.value = loadout
        }
    }

    fun saveLoadouts() {
        viewModelScope.launch {
            repository.save(ctLoadout.value, tLoadout.value)
        }
    }

    fun resetLoadouts() {
        ctLoadout.value = defaultCtLoadout()
        tLoadout.value = defaultTLoadout()
    }

    fun changeSide(side: Side) {
        selectedSide.value = side
    }

    private fun defaultCtLoadout(): LoadoutState {
        //Starting pistol
        val usp = weapons.first { it.name == "USP-S" }

        //Other pistols
        val p250 = weapons.first { it.name == "P250" }
        val dualBerretas = weapons.first { it.name == "Dual Berretas" }
        val fiveSeven = weapons.first { it.name == "Five-SeveN" }
        val desertEagle = weapons.first { it.name == "Desert Eagle" }

        //Mid-Tier
        val mp9 = weapons.first { it.name == "MP9" }
        val ump45 = weapons.first { it.name == "UMP-45" }
        val p90 = weapons.first { it.name == "P90" }
        val xm1014 = weapons.first { it.name == "XM1014" }
        val mp5sd = weapons.first { it.name == "MP5-SD" }

        //Rifles
        val famas = weapons.first { it.name == "FAMAS" }
        val m4a1s = weapons.first { it.name == "M4A1-S" }
        val m4a4 = weapons.first { it.name == "M4A4" }
        val aug = weapons.first { it.name == "AUG" }
        val awp = weapons.first { it.name == "AWP" }


        return LoadoutState(
            pistols = listOf(usp, p250, dualBerretas, fiveSeven, desertEagle),
            midTier = listOf(mp9, ump45, p90, xm1014, mp5sd),
            rifles = listOf(famas, m4a1s, m4a4, aug, awp)
        )
    }

    private fun defaultTLoadout(): LoadoutState {
        //Starting pistol
        val glock = weapons.first { it.name == "Glock-18" }

        //Other pistols
        val p250 = weapons.first { it.name == "P250" }
        val cz75auto = weapons.first { it.name == "CZ75-Auto" }
        val tec9 = weapons.first { it.name == "Tec-9" }
        val desertEagle = weapons.first { it.name == "Desert Eagle" }

        //Mid-Tier
        val mac10 = weapons.first { it.name == "MAC-10" }
        val mp7 = weapons.first { it.name == "MP7" }
        val mp9 = weapons.first { it.name == "MP9" }
        val ppbizon = weapons.first { it.name == "PP-Bizon" }
        val ump45 = weapons.first { it.name == "UMP-45" }

        //Rifles
        val ak = weapons.first { it.name == "AK-47" }
        val galilar = weapons.first { it.name == "Galil AR" }
        val sg553 = weapons.first { it.name == "SG 553" }
        val awp = weapons.first { it.name == "AWP" }
        val ssg08 = weapons.first { it.name == "SSG 08" }

        return LoadoutState(
            pistols = listOf(glock, p250, cz75auto, tec9, desertEagle),
            midTier = listOf(mac10, mp7, mp9, ppbizon, ump45),
            rifles = listOf(galilar, ak, ssg08, sg553, awp)
        )
    }


    private fun enforceTGlock() {
        val glock = weapons.firstOrNull { it.name == "Glock-18" } ?: return
        val updated = tLoadout.value.pistols.toMutableList()
        updated[0] = glock
        tLoadout.value = tLoadout.value.copy(pistols = updated)
    }

    fun isSlotLocked(loadoutClass: LoadoutClass, index: Int): Boolean {
        return selectedSide.value == Side.T &&
                loadoutClass == LoadoutClass.PISTOLS &&
                index == 0
    }

    fun isWeaponAlreadyUsed(weapon: Weapon): Boolean {
        val loadout = currentLoadout()
        return loadout.allSelectedWeapons().any { it.id == weapon.id }
    }

    fun getWeaponsForSlot(
        loadoutClass: LoadoutClass,
        index: Int
    ): List<Weapon> {
        val side = selectedSide.value

        return when (loadoutClass) {

            LoadoutClass.PISTOLS -> {
                val pistols = weapons.filter { it.type == WeaponType.PISTOL }

                when (side) {
                    Side.CT -> {
                        if (index == 0) {
                            pistols.filter { it.name == "USP-S" || it.name == "P2000" }
                        } else {
                            pistols.filter {
                                it.name != "USP-S" &&
                                        it.name != "P2000" &&
                                        (it.side == Side.CT || it.side == Side.BOTH)
                            }
                        }
                    }

                    else -> {
                        if (index == 0) {
                            pistols.filter { it.name == "Glock-18" }
                        } else {
                            pistols.filter {
                                it.name != "Glock-18" &&
                                        (it.side == Side.T || it.side == Side.BOTH)
                            }
                        }
                    }
                }
            }

            LoadoutClass.MID_TIER ->
                weapons.filter {
                    it.type in listOf(WeaponType.SMG, WeaponType.SHOTGUN, WeaponType.LMG) &&
                            (it.side == side || it.side == Side.BOTH)
                }

            LoadoutClass.RIFLES ->
                weapons.filter {
                    it.type in listOf(WeaponType.RIFLE, WeaponType.SNIPER) &&
                            (it.side == side || it.side == Side.BOTH)
                }
        }
    }

    private fun pistolOptions(side: Side, slotIndex: Int): List<Weapon> {
        val pistols = weapons.filter { it.type == WeaponType.PISTOL }

        return when (side) {

            Side.CT -> {
                if (slotIndex == 0) {
                    pistols.filter {
                        it.name == "USP-S" || it.name == "P2000"
                    }
                } else {
                    pistols.filter {
                        it.name != "USP-S" &&
                                it.name != "P2000" &&
                                (it.side == Side.CT || it.side == Side.BOTH)
                    }
                }
            }

            else -> {
                if (slotIndex == 0) {
                    pistols.filter { it.name == "Glock-18" }
                } else {
                    pistols.filter {
                        it.name != "Glock-18" &&
                                (it.side == Side.T || it.side == Side.BOTH)
                    }
                }
            }
        }
    }

    fun replaceWeapon(
        loadoutClass: LoadoutClass,
        index: Int,
        weapon: Weapon
    ) {
        val loadout = currentLoadout()

        val updated = when (loadoutClass) {
            LoadoutClass.PISTOLS -> {
                loadout.copy(
                    pistols = loadout.pistols.toMutableList().also {
                        it[index] = weapon
                    }
                )
            }

            LoadoutClass.MID_TIER -> {
                loadout.copy(
                    midTier = loadout.midTier.toMutableList().also {
                        it[index] = weapon
                    }
                )
            }

            LoadoutClass.RIFLES -> {
                loadout.copy(
                    rifles = loadout.rifles.toMutableList().also {
                        it[index] = weapon
                    }
                )
            }
        }

        updateLoadout(updated)
    }
}
