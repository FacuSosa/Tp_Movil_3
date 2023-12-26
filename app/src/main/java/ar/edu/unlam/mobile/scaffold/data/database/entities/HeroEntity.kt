package ar.edu.unlam.mobile.scaffold.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.edu.unlam.mobile.scaffold.domain.model.AppearanceModel
import ar.edu.unlam.mobile.scaffold.domain.model.BiographyModel
import ar.edu.unlam.mobile.scaffold.domain.model.ConnectionsModel
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.domain.model.ImageModel
import ar.edu.unlam.mobile.scaffold.domain.model.StatModel
import ar.edu.unlam.mobile.scaffold.domain.model.WorkModel

@Entity(tableName = "hero_table")
data class HeroEntity(
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER) @PrimaryKey(autoGenerate = false) val id: Int = 0,
    @Embedded(prefix = "app") val appearance: AppearanceEntity = AppearanceEntity(),
    @Embedded(prefix = "bio") val biography: BiographyEntity = BiographyEntity(),
    @Embedded(prefix = "conn") val connections: ConnectionsEntity = ConnectionsEntity(),
    @Embedded(prefix = "image") val image: ImageEntity = ImageEntity(),
    @ColumnInfo(name = "name") val name: String = "NA",
    @Embedded(prefix = "stat") val powerstats: PowerstatsEntity = PowerstatsEntity(),
    @Embedded(prefix = "work") val work: WorkEntity = WorkEntity(),
    @ColumnInfo(name = "quantity") val quantity: Int = 0
)

fun HeroEntity.toHeroModel(): HeroModel {
    return HeroModel(
        id = this.id,
        appearance = AppearanceModel(
            eyeColor = this.appearance.eyeColor,
            gender = this.appearance.gender,
            hairColor = this.appearance.hairColor,
            height = this.appearance.height,
            race = this.appearance.race,
            weight = this.appearance.weight
        ),
        biography = BiographyModel(
            aliases = this.biography.aliases,
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
            combat = this.powerstats.combat,
            durability = this.powerstats.durability,
            intelligence = this.powerstats.intelligence,
            power = this.powerstats.power,
            speed = this.powerstats.speed,
            strength = this.powerstats.strength
        ),
        work = WorkModel(
            base = this.work.base,
            occupation = this.work.occupation
        ),
        quantity = this.quantity
    )
}
