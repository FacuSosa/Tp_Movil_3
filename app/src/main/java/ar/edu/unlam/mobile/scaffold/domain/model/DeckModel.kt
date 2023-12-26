package ar.edu.unlam.mobile.scaffold.domain.model

data class DeckModel(
    val id: Int?,
    val carta1: HeroModel = HeroModel(),
    val carta2: HeroModel = HeroModel(),
    val carta3: HeroModel = HeroModel(),
    val carta4: HeroModel = HeroModel(),
    val carta5: HeroModel = HeroModel(),
    val carta6: HeroModel = HeroModel()
)
