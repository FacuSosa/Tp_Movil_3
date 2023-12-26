package ar.edu.unlam.mobile.scaffold.di

import android.content.Context
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.qrmanager.HeroQrManager
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object QrModule {

    @ViewModelScoped
    @Provides
    fun providesQrConfiguration() = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .enableAutoZoom()
        .build()

    @ViewModelScoped
    @Provides
    fun providesQrScanner(
        @ApplicationContext context: Context,
        config: GmsBarcodeScannerOptions
    ): GmsBarcodeScanner {
        return GmsBarcodeScanning.getClient(context, config)
    }

    @ViewModelScoped
    @Provides
    fun provideHeroQrManager(repo: IHeroRepository): HeroQrManager {
        return HeroQrManager(repo)
    }
}
