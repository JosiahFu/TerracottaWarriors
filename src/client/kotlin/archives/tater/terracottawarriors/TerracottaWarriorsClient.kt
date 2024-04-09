package archives.tater.terracottawarriors

import archives.tater.terracottawarriors.client.render.TerracottaWarriorEntityRenderer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.model.Dilation
import net.minecraft.client.model.TexturedModelData
import net.minecraft.client.render.entity.model.ArmorEntityModel
import net.minecraft.client.render.entity.model.BipedEntityModel
import net.minecraft.client.render.entity.model.EntityModelLayer
import net.minecraft.util.Identifier

object TerracottaWarriorsClient : ClientModInitializer {
	object ModelLayers {
		private val id = Identifier(TerracottaWarriors.MOD_ID, "terracotta_warrior")
		val BASE = EntityModelLayer(id, "main")
		val ARMOR_INNER = EntityModelLayer(id, "inner_armor")
		val ARMOR_OUTER = EntityModelLayer(id, "outer_armor")
	}

	override fun onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		EntityRendererRegistry.register(TerracottaWarriors.TERRACOTTA_WARRIOR_TYPE, ::TerracottaWarriorEntityRenderer)

		EntityModelLayerRegistry.registerModelLayer(ModelLayers.BASE) {
			TexturedModelData.of(BipedEntityModel.getModelData(Dilation.NONE, 0f), 64, 64)
		}
		EntityModelLayerRegistry.registerModelLayer(ModelLayers.ARMOR_INNER) {
			TexturedModelData.of(ArmorEntityModel.getModelData(Dilation(0.5f)), 64, 32)
		}
		EntityModelLayerRegistry.registerModelLayer(ModelLayers.ARMOR_OUTER) {
			TexturedModelData.of(ArmorEntityModel.getModelData(Dilation(1.0f)), 64, 32)
		}
	}
}
