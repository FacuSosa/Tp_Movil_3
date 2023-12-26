package ar.edu.unlam.mobile.scaffold.ui.components.hero

import android.content.Context
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.unlam.mobile.scaffold.R
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest

/*
    Para ver el preview de estos composables hay que hacer lo siguiente:
    Hay un botoncito de un celular arriba a la derecha del rectángulo del preview.
    Este botón abre el emulador para mostrar el composable.
 */

@Preview
@Composable
fun HeroSimpleImage(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    url: String = "https://loremflickr.com/400/400/cat?lock=1",
    contentScale: ContentScale = ContentScale.Fit,
    context: Context = LocalContext.current
) {
    val imageRequest = asyncImageRequestBuilder(context, url)

    AsyncImage(
        model = imageRequest,
        contentDescription = "Hero's portrait",
        contentScale = contentScale,
        alignment = alignment,
        modifier = modifier
    )
}

private fun asyncImageRequestBuilder(
    context: Context,
    url: String
): ImageRequest {
    return ImageRequest.Builder(context)
        .data(url)
        .error(R.drawable.broken_image)
        .placeholder(R.drawable.default_imagen_heroe)
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()
}

@Composable
fun HeroImage(
    modifier: Modifier = Modifier,
    url: String = "https://loremflickr.com/400/400/cat?lock=1",
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    context: Context = LocalContext.current,
    alpha: Float = 1.0f
) {
    val imageRequest = subcomposeImageRequestBuilder(context, url)

    SubcomposeAsyncImage(
        modifier = modifier,
        model = imageRequest,
        contentDescription = "Hero's portrait",
        loading = {
            CircularProgressIndicator()
        },
        error = {
            Icon(
                painter = painterResource(id = R.drawable.broken_image),
                contentDescription = "broken image"
            )
        },
        contentScale = contentScale,
        alignment = alignment,
        alpha = alpha
    )
}

private fun subcomposeImageRequestBuilder(
    context: Context,
    url: String
): ImageRequest {
    return ImageRequest.Builder(context)
        .data(url)
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()
}
