package ar.edu.unlam.mobile.scaffold.ui.screens.usuario

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class UsuarioScreenKtTest {

    @get: Rule
    val ruleCompose = createComposeRule()

    @Test
    fun alPasarleTodosLosEstadosPorDefaultAlUsuarioUIVerificarSiTodosLosComposablesExisten() {
        ruleCompose.setContent {
            UsuarioUi()
        }
        ruleCompose.onNodeWithTag(testTag = "background image").assertExists()
        ruleCompose.onNodeWithTag(testTag = "texto titulo").assertExists()
        ruleCompose.onNodeWithTag(testTag = "imagen de usuario" ).assertExists()
        ruleCompose.onNodeWithTag(testTag = "texto usuario actual").assertExists()
        ruleCompose.onNodeWithTag(testTag = "Boton Camara").assertExists()
        ruleCompose.onNodeWithTag(testTag = "permiso de la camara").assertDoesNotExist()
    }

    @Test
    fun alPasarleTodosLosEstadosPorDefaultAlCargarDeUsuarioVerificarSiTodosLosComposablesExisten() {
        ruleCompose.setContent {
            CargarUsuarioUi()
        }
        ruleCompose.onNodeWithTag(testTag = "background image").assertExists()
        ruleCompose.onNodeWithTag(testTag = "texto usuario").assertExists()
        ruleCompose.onNodeWithTag(testTag = "Test Imagen usuario" ).assertExists()
        ruleCompose.onNodeWithTag(testTag = "Textfield").assertExists()
        ruleCompose.onNodeWithTag(testTag = "boton de ingresar usuario").assertExists()
        ruleCompose.onNodeWithTag(testTag = "Texto de continuar", useUnmergedTree = true).assertExists()
    }
}