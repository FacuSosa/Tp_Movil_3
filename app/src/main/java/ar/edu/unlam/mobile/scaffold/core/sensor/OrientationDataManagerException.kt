package ar.edu.unlam.mobile.scaffold.core.sensor

class OrientationDataManagerException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}
