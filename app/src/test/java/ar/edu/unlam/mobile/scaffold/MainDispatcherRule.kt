package ar.edu.unlam.mobile.scaffold

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * https://stackoverflow.com/questions/58303961/kotlin-coroutine-unit-test-fails-with-module-with-the-main-dispatcher-had-faile
 *
 * https://youtu.be/B-dJTFeOAqw
 *
 * https://developer.android.com/kotlin/coroutines/test
 *
 * No se puede acceder a Dispatcher.Main en los test unitarios porque no pueden acceder a los recursos de
 * android.
 *
 * Por esta razón, se debe crear esta reglar para sobreescribir Dispatchers.Main por un test
 * dispatcher.
 *
 * Si uno no hace esto ocurre el siguiente error:
 *
 * * Module with the Main dispatcher had failed to initialize. For tests Dispatchers.setMain from
 * kotlinx-coroutines-test module can be used.
 */
// Reusable JUnit4 TestRule to override the Main dispatcher
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
