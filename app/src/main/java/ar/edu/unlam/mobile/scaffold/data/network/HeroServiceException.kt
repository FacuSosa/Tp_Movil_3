package ar.edu.unlam.mobile.scaffold.data.network

/*
    https://stackoverflow.com/a/68775013
 */
class HeroServiceException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}
