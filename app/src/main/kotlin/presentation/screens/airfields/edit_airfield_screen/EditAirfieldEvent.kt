package presentation.screens.airfields.edit_airfield_screen

sealed class EditAirfieldEvent {
    data class OnNameChange(val name: String) : EditAirfieldEvent()
    data class OnLocationSelected(val latitude: Double, val longitude: Double) : EditAirfieldEvent()
    data class OnHomeToggled(val isHome: Boolean) : EditAirfieldEvent()
    data object Save : EditAirfieldEvent()
}
