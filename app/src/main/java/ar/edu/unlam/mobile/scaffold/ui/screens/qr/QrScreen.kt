package ar.edu.unlam.mobile.scaffold.ui.screens.qr

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.ui.components.HeroText
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroImage
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

@Composable
fun QrScreen(
    modifier: Modifier = Modifier,
    // controller: NavHostController,
    viewModel: QrScreenViewModel = hiltViewModel(),
    heroID: Int = 1
) {
    viewModel.getHero(heroID)
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.fondo_coleccion),
            contentDescription = "Pantalla detalles del héroe",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .testTag("TestQRScreen pantalla fondo")
        )
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            val hero by viewModel.hero.collectAsStateWithLifecycle()
            val qrCodeBitmap = generateQRCode("${hero.id}\n${hero.name}", 300)
            val titleTextModifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
            Column(
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState())
            ) {
                HeroImage(
                    modifier = Modifier
                        .testTag("TestQRScreen imagen heroe")
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                        .size(300.dp),
                    url = hero.image.url,
                    contentScale = ContentScale.FillWidth
                )
                HeroText(
                    modifier = titleTextModifier
                        .testTag("TestQRScreen nombre heroe"),
                    text = "${hero.id} ${hero.name}"
                )

                Image(
                    bitmap = qrCodeBitmap, // Utilizamos el código QR generado
                    contentDescription = "Código QR del héroe",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("TestQRScreen imagen qr")
                        .padding(32.dp)
                        .size(300.dp)

                )
            }
        }
    }
}

@Composable
fun generateQRCode(content: String, size: Int): ImageBitmap {
    val bitMatrix: BitMatrix = MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size)
    val imageBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)

    for (x in 0 until size) {
        for (y in 0 until size) {
            imageBitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
        }
    }

    return imageBitmap.asImageBitmap()
}
