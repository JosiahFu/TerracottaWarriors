package archives.tater.terracottawarriors.client.render

import archives.tater.terracottawarriors.TerracottaWarriorEntity
import archives.tater.terracottawarriors.TerracottaWarriors
import archives.tater.terracottawarriors.TerracottaWarriorsClient
import archives.tater.terracottawarriors.client.model.TerracottaWarriorEntityModel
import net.minecraft.client.render.entity.BipedEntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer
import net.minecraft.util.Identifier

class TerracottaWarriorEntityRenderer(
    context: EntityRendererFactory.Context,
) : BipedEntityRenderer<TerracottaWarriorEntity, TerracottaWarriorEntityModel>(context, TerracottaWarriorEntityModel(
    context.getPart(TerracottaWarriorsClient.ModelLayers.BASE)
), 0.5f) {
    init {
        addFeature(ArmorFeatureRenderer(this, TerracottaWarriorEntityModel(context.getPart(TerracottaWarriorsClient.ModelLayers.ARMOR_INNER)), TerracottaWarriorEntityModel(context.getPart(TerracottaWarriorsClient.ModelLayers.ARMOR_OUTER)), context.modelManager))
    }

    override fun getTexture(entity: TerracottaWarriorEntity) =
        Identifier(TerracottaWarriors.MOD_ID, "textures/entity/terracotta_warrior.png")
}
