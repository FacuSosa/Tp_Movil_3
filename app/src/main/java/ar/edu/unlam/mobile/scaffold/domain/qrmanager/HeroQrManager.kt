package ar.edu.unlam.mobile.scaffold.domain.qrmanager

import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import javax.inject.Inject

class HeroQrManager @Inject constructor(private val repo: IHeroRepository) {

    // solamente se pueden obtener cartas x qr una sola vez
    suspend fun getHeroFromQr(qrRawValue: String): HeroModel {
        val id = getHeroId(qrRawValue)
        return repo.winHeroCard(id)
    }

    private fun getHeroId(string: String): Int {
        val id = string.filterIndexed { index, char ->
            index <= 2 && char.isDigit()
        }.toInt()
        if (id <= 0 || id > repo.COLLECTION_MAX_SIZE) {
            throw HeroQrManagerException(message = "QrManager: Id fuera de rango en getHeroFromQr().")
        }
        return id
    }
}
