package ar.edu.unlam.mobile.scaffold.ui.screens.qrscanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.domain.qrmanager.HeroQrManager
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class QrScannerViewModel @Inject constructor(
    private val qrScanner: GmsBarcodeScanner,
    private val heroQrManager: HeroQrManager
) : ViewModel() {

    private val _qrScannerUiState = MutableStateFlow<QrScannerUiState>(QrScannerUiState.Loading)
    val qrScannerUIState = _qrScannerUiState.asStateFlow()

    private val _heroQr = MutableStateFlow(HeroModel())
    val heroQr = _heroQr.asStateFlow()

    fun startScan() {
        _qrScannerUiState.value = QrScannerUiState.Loading
        qrScanner.startScan()
            .addOnSuccessListener { barcode ->
                viewModelScope.launch(Dispatchers.IO) {
                    _heroQr.value = heroQrManager.getHeroFromQr(barcode.rawValue!!)
                    _qrScannerUiState.value = QrScannerUiState.QrSuccess
                }
            }
            .addOnCanceledListener {
                _qrScannerUiState.value = QrScannerUiState.Cancelled
            }
            .addOnFailureListener { e ->
                _qrScannerUiState.value = QrScannerUiState.Error("Error al leer qr: ${e.message}")
            }
    }

    init {
        _qrScannerUiState.value = QrScannerUiState.Success
    }
}

@Immutable
sealed interface QrScannerUiState {
    data object Success : QrScannerUiState
    data object QrSuccess : QrScannerUiState
    data object Loading : QrScannerUiState
    data object Cancelled : QrScannerUiState
    data class Error(val message: String) : QrScannerUiState
}
