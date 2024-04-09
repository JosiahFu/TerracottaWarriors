package archives.tater.terracottawarriors

import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup

fun <T: Entity> EntityType(spawnGroup: SpawnGroup, entityFactory: EntityType.EntityFactory<T>, init: FabricEntityTypeBuilder<T>.() -> Unit): EntityType<T> {
    return FabricEntityTypeBuilder.create(spawnGroup, entityFactory).apply(init).build()
}
