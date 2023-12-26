package ar.edu.unlam.mobile.scaffold.ui.screens.usuario

import android.Manifest
import android.view.ViewGroup
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.domain.usuario.Guest
import ar.edu.unlam.mobile.scaffold.ui.components.CustomButton
import ar.edu.unlam.mobile.scaffold.ui.components.CustomTitle
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxBackgroundImage
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.util.concurrent.Executor

@Composable
fun UsuarioScreen(
    modifier: Modifier,
    viewModel: UsuarioViewModel = hiltViewModel()
) {
    val usuario by viewModel.usuario.collectAsStateWithLifecycle()
    val crearUsuario: (String) -> Unit = {
        viewModel.crearUsuario(it)
        viewModel.obtenerUsuario()
    }

    val existeUsuario by viewModel.existeGuest.collectAsStateWithLifecycle()

    if (existeUsuario == true) {
        UsuarioUi(
            modifier = modifier,
            usuario = usuario
        )
    } else {
        CargarUsuarioUi(
            modifier = modifier,
            insertarUsuario = crearUsuario
        )
    }
}

@Preview(showBackground = true, heightDp = 800, widthDp = 400)
@Composable
fun UsuarioUi(
    modifier: Modifier = Modifier,
    usuario: Guest = Guest(1, "Test")
) {
    var camara by remember { mutableStateOf(false) }

    ParallaxBackgroundImage(
        modifier = Modifier
            .fillMaxSize()
            .testTag("background image"),
        painterResourceId = R.drawable.fondo_coleccion,
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTitle(
            modifier = Modifier.testTag("texto titulo"),
            text = { "Usuarios" }
        )

        Spacer(Modifier.size(16.dp))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("file:///data/user/0/ar.edu.unlam.mobile.scaffold/files/photoPic/My_photo.jpg")
                .transformations(CircleCropTransformation())
                .build(),
            contentDescription = "Imagen de usuario",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(100.dp)
                .testTag("imagen de usuario")
        )

        Text(
            modifier = Modifier.testTag("texto usuario actual"),
            text = "Usuario actual: ${usuario.username}"
        )

        CustomButton(
            modifier = Modifier.testTag("Boton Camara"),
            label = { "Camara" },
            onClick = { camara = !camara }
        )

        Spacer(Modifier.size(16.dp))

        if (camara) {
            PermisosDeLaCamara()
        }
    }
}

@Preview(showBackground = true, heightDp = 800, widthDp = 400)
@Composable
fun CargarUsuarioUi(modifier: Modifier = Modifier, insertarUsuario: (String) -> Unit = {}) {
    var camara by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("nombre de usuario") }

    ParallaxBackgroundImage(
        modifier = Modifier
            .fillMaxSize()
            .testTag("background image"),
        painterResourceId = R.drawable.fondo_coleccion,
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTitle(
            modifier = Modifier.testTag("texto usuario"),
            text = { "Usuarios" }
        )

        Spacer(Modifier.size(16.dp))

      /*  if ("si la imagen de perfil existe"){
            AsyncImage
        }else{
            Image(painter = painterResource(id = R.drawable.default_imagen_heroe), contentDescription = "Imagen temporal")
        }*/

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("file:///data/user/0/ar.edu.unlam.mobile.scaffold/files/photoPic/My_photo.jpg")
                .transformations(CircleCropTransformation())
                .build(),
            contentDescription = "Imagen de usuario",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(100.dp)
                .testTag(tag = "Test Imagen usuario")
        )

        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = { Text(text = "Ingrese usuario") },
            placeholder = { Text(text = "Nombre de usuario") },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.testTag("Textfield")
        )

        Button(
            modifier = Modifier.testTag(tag = "boton de ingresar usuario"),
            onClick = {
                if (name.isNotBlank()) {
                    insertarUsuario(name)
                }
            }
        ) {
            Text(
                modifier = Modifier.testTag(tag = "Texto de continuar"),
                text = "Continuar"
            )
        }

        CustomButton(
            modifier = Modifier.testTag("Camara"),
            label = { "Camara" },
            onClick = { camara = !camara }
        )

        Spacer(Modifier.size(16.dp))

        if (camara) {
            PermisosDeLaCamara(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("permiso de la camara")
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
@Composable
fun PermisosDeLaCamara(modifier: Modifier = Modifier) {
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    val context = LocalContext.current
    val camaraController = remember {
        LifecycleCameraController(context)
    }

    val lifecycle = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val executor = ContextCompat.getMainExecutor(context)
                    takePicture(camaraController, executor, context.filesDir)
                },
                containerColor = Color.DarkGray,
                contentColor = Color.White,
                shape = CircleShape,

            ) {
                Icon(Icons.Default.Add, "Icono de mas")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        if (permissionState.status.isGranted) {
            CamaraComposable(camaraController, lifecycle, modifier = Modifier.padding(it))
        } else {
            Text(text = "Permiso denegado", modifier = Modifier.padding(it))
        }
    }
}

fun makeProfilePicFile(filesDir: File): File {
    val baseDirectory = File(filesDir, "photoPic")

    if (!baseDirectory.exists()) {
        baseDirectory.mkdir()
    }

    val file = File(baseDirectory, "My_photo.jpg")

    if (file.exists()) {
        file.delete()
    }

    file.createNewFile()
    return file
}

private fun takePicture(camaraController: LifecycleCameraController, executor: Executor, filesDir: File) {
    val file = makeProfilePicFile(filesDir)

    val outputDirectory = ImageCapture.OutputFileOptions.Builder(file).build()
    camaraController.takePicture(
        outputDirectory,
        executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                println(outputFileResults.savedUri)
            }
            override fun onError(exception: ImageCaptureException) {
                println("Error")
            }
        }
    )
}

@Composable
fun CamaraComposable(
    camaraController: LifecycleCameraController,
    lifecycle: LifecycleOwner,
    modifier: Modifier = Modifier,
) {
    camaraController.bindToLifecycle(lifecycle)

    AndroidView(modifier = modifier, factory = { context ->
        val previewView = PreviewView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
        }
        previewView.controller = camaraController

        previewView
    })
}
