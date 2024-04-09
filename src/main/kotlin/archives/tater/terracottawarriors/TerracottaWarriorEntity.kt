package archives.tater.terracottawarriors

import net.minecraft.entity.*
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.mob.Monster
import net.minecraft.entity.passive.GolemEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.world.LocalDifficulty
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.World

class TerracottaWarriorEntity(entityType: EntityType<TerracottaWarriorEntity>, world: World) : GolemEntity(entityType, world) {


    override fun initGoals() {
        goalSelector.add(2, WanderAroundFarGoal(this, 0.6, 240F))
        goalSelector.add(3, LookAtEntityGoal(this, PlayerEntity::class.java, 6.0f))
        goalSelector.add(4, LookAroundGoal(this))
        goalSelector.add(2, MeleeAttackGoal(this, 1.0, false))
        targetSelector.add(1, ActiveTargetGoal(this, MobEntity::class.java, 10, true, false) {
            it is Monster
        })
    }

    override fun getActiveEyeHeight(pose: EntityPose, dimensions: EntityDimensions) = 1.7f

    override fun initialize(
        world: ServerWorldAccess,
        difficulty: LocalDifficulty,
        spawnReason: SpawnReason,
        entityData: EntityData?,
        entityNbt: NbtCompound?
    ): EntityData? {
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt).apply {
            setCanPickUpLoot(true)
        }
    }

    override fun interactMob(player: PlayerEntity, hand: Hand): ActionResult {
        val itemStack = player.getStackInHand(hand)
        if (canGather(itemStack)) {
            tryEquip(itemStack.copyWithCount(1)).let {
                if (!it.isEmpty) {
                    itemStack.decrement(1)
                    return ActionResult.CONSUME
                }
            }
        }
        return ActionResult.PASS
    }

    companion object {
        val attributes: DefaultAttributeContainer.Builder = createMobAttributes().apply {
            add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0)
            add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2)
            add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
        }
    }
}
