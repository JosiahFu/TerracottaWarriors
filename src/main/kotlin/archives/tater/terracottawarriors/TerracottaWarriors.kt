package archives.tater.terracottawarriors

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

object TerracottaWarriors : ModInitializer {
	const val MOD_ID = "terracottawarriors"

    private val logger = LoggerFactory.getLogger(MOD_ID)

	val TERRACOTTA_WARRIOR_TYPE: EntityType<TerracottaWarriorEntity> = Registry.register(
		Registries.ENTITY_TYPE,
		Identifier(MOD_ID, "terracotta_warrior"),
		EntityType(SpawnGroup.MISC, ::TerracottaWarriorEntity) {
			dimensions(EntityDimensions.fixed(0.6f, 1.95f))
		}
	)

	val PEDESTAL_POI = RegistryKeys.POINT_OF_INTEREST_TYPE.keyOf(Identifier(MOD_ID, "pedestal"))

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		FabricDefaultAttributeRegistry.register(TERRACOTTA_WARRIOR_TYPE, TerracottaWarriorEntity.attributes)

	}
}
