package ar.edu.unlam.mobile.scaffold.data.database.guest

import ar.edu.unlam.mobile.scaffold.data.database.dao.GuestDao
import ar.edu.unlam.mobile.scaffold.domain.usuario.Guest

class GuestRepository(private val dao: GuestDao) {
    suspend fun addGuestInDatabase(guest: Guest) {
        dao.insertGuest(guest.toEntity())
    }

    suspend fun verifyDatabase(): Boolean {
        return dao.getGuests().isNotEmpty()
    }

    suspend fun usuarioExiste(): Guest? {
        // Obtengo la lista
        val listaDeUsuario = dao.getGuests()
        if (listaDeUsuario.isNotEmpty()) {
            return listaDeUsuario[0].toDomain()
        }
        return null
    }
}
