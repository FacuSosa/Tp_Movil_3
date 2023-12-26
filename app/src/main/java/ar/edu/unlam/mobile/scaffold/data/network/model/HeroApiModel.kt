package ar.edu.unlam.mobile.scaffold.data.network.model

import ar.edu.unlam.mobile.scaffold.data.database.entities.AppearanceEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.BiographyEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.ConnectionsEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.HeroEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.ImageEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.PowerstatsEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.WorkEntity
import ar.edu.unlam.mobile.scaffold.domain.model.AppearanceModel
import ar.edu.unlam.mobile.scaffold.domain.model.BiographyModel
import ar.edu.unlam.mobile.scaffold.domain.model.ConnectionsModel
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.domain.model.ImageModel
import ar.edu.unlam.mobile.scaffold.domain.model.StatModel
import ar.edu.unlam.mobile.scaffold.domain.model.WorkModel
import com.google.gson.annotations.SerializedName

data class HeroApiModel(
    @SerializedName("appearance") val appearance: Appearance = Appearance(),
    @SerializedName("biography") val biography: Biography = Biography(),
    @SerializedName("connections") val connections: Connections = Connections(),
    @SerializedName("id") val id: String = "NA",
    @SerializedName("image") val image: Image = Image(),
    @SerializedName("name") val name: String = "NA",
    @SerializedName("powerstats") val powerstats: Powerstats = Powerstats(),
    @SerializedName("response") val response: String = "NA",
    @SerializedName("work") val work: Work = Work(),
)

fun HeroApiModel.toHeroEntityModel(): HeroEntity {
    return HeroEntity(
        appearance = appearanceToAppearanceEntity(this.appearance),
        biography = biographyToBiographyEntity(this.biography),
        id = this.id.toInt(),
        connections = connectionsApiModelToConnectionsEntity(this.connections),
        image = imageApiModelToImageEntity(this.image),
        name = this.name,
        powerstats = powerstatsApiModelToPowerstatsEntity(this.powerstats),
        work = workApiModelToWorkEntity(this.work)
    )
}

fun HeroApiModel.toHeroModel(): HeroModel {
    return HeroModel(
        id = this.id.toInt(),
        appearance = AppearanceModel(
            eyeColor = this.appearance.eyeColor,
            gender = this.appearance.gender,
            hairColor = this.appearance.hairColor,
            height = listOfStringsToString(this.appearance.height),
            race = this.appearance.race,
            weight = listOfStringsToString(this.appearance.weight)
        ),
        biography = BiographyModel(
            aliases = listOfStringsToString(this.biography.aliases),
            alignment = this.biography.alignment,
            alterEgos = this.biography.alterEgos,
            firstAppearance = this.biography.firstAppearance,
            fullName = this.biography.fullName,
            placeOfBirth = this.biography.placeOfBirth,
            publisher = this.biography.publisher
        ),
        connections = ConnectionsModel(
            groupAffiliation = this.connections.groupAffiliation,
            relatives = this.connections.relatives
        ),
        image = ImageModel(
            url = this.image.url
        ),
        name = this.name,
        stats = StatModel(
            combat = this.powerstats.combat.toInt(),
            durability = this.powerstats.durability.toInt(),
            intelligence = this.powerstats.intelligence.toInt(),
            power = this.powerstats.power.toInt(),
            speed = this.powerstats.speed.toInt(),
            strength = this.powerstats.strength.toInt()
        ),
        work = WorkModel(
            base = this.work.base,
            occupation = this.work.occupation
        )
    )
}

private fun workApiModelToWorkEntity(work: Work): WorkEntity {
    return WorkEntity(
        base = work.base,
        occupation = work.occupation
    )
}
private fun powerstatsApiModelToPowerstatsEntity(powerstats: Powerstats): PowerstatsEntity {
    return PowerstatsEntity(
        combat = powerstats.combat.toInt(),
        durability = powerstats.durability.toInt(),
        intelligence = powerstats.intelligence.toInt(),
        power = powerstats.power.toInt(),
        speed = powerstats.speed.toInt(),
        strength = powerstats.strength.toInt()
    )
}
private fun imageApiModelToImageEntity(image: Image): ImageEntity {
    return ImageEntity(
        url = image.url
    )
}
private fun connectionsApiModelToConnectionsEntity(connections: Connections): ConnectionsEntity {
    return ConnectionsEntity(
        groupAffiliation = connections.groupAffiliation,
        relatives = connections.relatives
    )
}

private fun appearanceToAppearanceEntity(app: Appearance): AppearanceEntity {
    return AppearanceEntity(
        height = listOfStringsToString(app.height),
        weight = listOfStringsToString(app.weight),
        eyeColor = app.eyeColor,
        gender = app.gender,
        hairColor = app.hairColor,
        race = app.race
    )
}

private fun listOfStringsToString(list: List<String>): String {
    val size = list.size
    var string = list[0]
    for (i in 1..<(size)) {
        string += ", ${list[i]}"
    }
    return string
}

fun biographyToBiographyEntity(bio: Biography): BiographyEntity {
    return BiographyEntity(
        aliases = listOfStringsToString(bio.aliases),
        alignment = bio.alignment,
        alterEgos = bio.alterEgos,
        firstAppearance = bio.firstAppearance,
        fullName = bio.fullName,
        placeOfBirth = bio.placeOfBirth,
        publisher = bio.publisher
    )
}
