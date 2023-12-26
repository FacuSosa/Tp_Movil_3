package ar.edu.unlam.mobile.scaffold.domain.model

data class HeroModel(
    val id: Int = 0,
    val appearance: AppearanceModel = AppearanceModel(),
    val biography: BiographyModel = BiographyModel(),
    val connections: ConnectionsModel = ConnectionsModel(),
    val image: ImageModel = ImageModel(),
    val name: String = "NA",
    val stats: StatModel = StatModel(),
    val work: WorkModel = WorkModel(),
    val quantity: Int = 0
)
