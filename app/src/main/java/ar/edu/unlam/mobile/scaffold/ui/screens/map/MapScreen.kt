package ar.edu.unlam.mobile.scaffold.ui.screens.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.domain.model.Point
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxBackgroundImage
import ar.edu.unlam.mobile.scaffold.ui.theme.shaka_pow
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    modifier: Modifier,
    controller: NavHostController,
    viewModel: MapViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val puntosDeEncuentro by viewModel.point.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    when {
        permissionState.allPermissionsGranted -> {
            LaunchedEffect(Unit) {
                viewModel.handle(PermissionEvent.Granted)
            }
        }

        permissionState.shouldShowRationale -> {
            LaunchedEffect(Unit) {
                permissionState.launchMultiplePermissionRequest()
            }
        }

        !permissionState.allPermissionsGranted && !permissionState.shouldShowRationale -> {
            LaunchedEffect(Unit) {
                viewModel.handle(PermissionEvent.Revoked)
            }
        }
    }

    with(viewState) {
        when (this) {
            ViewState.Loading -> {
                if (!isGpsEnabled(context)) {
                    Box(
                        modifier = modifier,
                        contentAlignment = Alignment.Center
                    ) {
                        ParallaxBackgroundImage(
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = "Pantalla Coleccion",
                            painterResourceId = R.drawable.fondo_coleccion,
                        )
                        Text(
                            text = "Por Favor activa el Gps para usar el mapa",
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Box(
                        modifier = modifier,
                        contentAlignment = Alignment.Center
                    ) {
                        ParallaxBackgroundImage(
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = "Pantalla Coleccion",
                            painterResourceId = R.drawable.fondo_coleccion,
                        )
                        CircularProgressIndicator()
                    }
                }
            }

            ViewState.RevokedPermissions -> {
                Box(
                    modifier = modifier,
                    contentAlignment = Alignment.Center
                ) {
                    ParallaxBackgroundImage(
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "Pantalla Coleccion",
                        painterResourceId = R.drawable.fondo_coleccion,
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Necesitas los permisos de localizacion y Gps activado para usar el mapa",
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            modifier = Modifier.padding(16.dp),
                            onClick = {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                intent.data = Uri.parse("package:${context.packageName}")
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(Color.Yellow)
                        ) {
                            Text(
                                "Settings",
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontFamily = shaka_pow
                            )
                        }
                    }
                }
            }

            is ViewState.Success -> {
                val currentLoc =
                    LatLng(
                        this.location?.latitude ?: 0.0,
                        this.location?.longitude ?: 0.0
                    )
                val cameraState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(currentLoc, 18f)
                }

                LaunchedEffect(key1 = currentLoc) {
                    cameraState.position
                }

                Map(
                    modifier = modifier.fillMaxSize(),
                    currentPosition = LatLng(
                        currentLoc.latitude,
                        currentLoc.longitude
                    ),
                    cameraState = cameraState,
                    puntosDeEncuentro = puntosDeEncuentro
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Map(
    modifier: Modifier = Modifier,
    currentPosition: LatLng,
    cameraState: CameraPositionState,
    puntosDeEncuentro: List<Point>
) {
    val punto1 = LatLng(puntosDeEncuentro[0].coordinates1, puntosDeEncuentro[0].coordinates2)
    val punto2 = LatLng(puntosDeEncuentro[1].coordinates1, puntosDeEncuentro[1].coordinates2)
    val marker = LatLng(currentPosition.latitude, currentPosition.longitude)

    var selectedDestination by remember { mutableStateOf<LatLng?>(null) }

    Box(modifier) {
        /*Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.fondo_coleccion),
            contentDescription ="Pantalla Coleccion" )*/

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Yellow),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Puntos de Encuentro",
                modifier = Modifier
                    .padding(10.dp)
                    .testTag("MapScreen title"),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                color = Color.Black,
                fontFamily = shaka_pow

            )

            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("MapScreen googleMap"),
                cameraPositionState = cameraState,
                properties = MapProperties(
                    isMyLocationEnabled = true,
                    mapType = MapType.NORMAL,

                )
            ) {
                Marker(
                    state = MarkerState(position = marker),
                    title = "Mi Posición Actual",
                )

                MarkerInfoWindowContent(
                    state = MarkerState(position = punto1),
                    snippet = "Punto de encuentro 1",
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.comic),

                ) {
                    selectedDestination = punto1
                    Box(
                        modifier = Modifier
                            .height(290.dp)
                            .width(300.dp)
                            .background(Color.Green)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.punto_encuentro_1),
                            contentDescription = null,
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .width(500.dp)
                                .height(250.dp)
                                .testTag("imagen punto uno de encuentro"),
                        )

                        Text(
                            text = "Punto De Encuentro 1",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 250.dp)
                                .fillMaxWidth()
                                .testTag(tag = "MapScreen Text punto de encuentro uno"),
                            fontSize = 30.sp,
                            color = Color.Black,
                            fontFamily = shaka_pow

                        )
                    }
                }

                MarkerInfoWindowContent(
                    state = MarkerState(position = punto2),
                    snippet = "Punto de encuentro 2",
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.comic_2)
                ) {
                    selectedDestination = punto2

                    Box(
                        modifier = Modifier
                            .height(270.dp)
                            .width(300.dp)
                            .background(Color.Green)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.punto_encuentro_2),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .width(380.dp)
                                .height(220.dp)
                                .testTag("MapScreen Image punto de encuentro2")
                        )

                        Text(
                            text = "Punto De Encuentro 2",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 230.dp)
                                .fillMaxWidth()
                                .testTag("MapScreen Text punto de encuentro2"),
                            fontSize = 30.sp,
                            color = Color.Black,
                            fontFamily = shaka_pow
                        )
                    }
                }
            }
        }
    }
}

fun isGpsEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}
