package ar.edu.unlam.mobile.scaffold.data.repository.herorepository

/*
https://stackoverflow.com/a/68775013
 */
class HeroRepositoryException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}
