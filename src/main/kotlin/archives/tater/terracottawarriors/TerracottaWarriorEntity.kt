package archives.tater.terracottawarriors

import net.minecraft.entity.*
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.mob.Monster
import net.minecraft.entity.passive.GolemEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.LocalDifficulty
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.World
import java.util.*

class TerracottaWarriorEntity(entityType: EntityType<TerracottaWarriorEntity>, world: World) : GolemEntity(entityType, world) {
    var standing by STANDING
    var pedestalPos by PEDESTAL_POS

    private val wanderGoal = 2 to WanderAroundFarGoal(this, 0.6, 240F)
    private val lookEntityGoal = 3 to LookAtEntityGoal(this, PlayerEntity::class.java, 6.0f)
    private val lookAroundGoal = 4 to LookAroundGoal(this)

    constructor(world: World) : this(TerracottaWarriors.TERRACOTTA_WARRIOR_TYPE, world)

    constructor(world: World, pos: Vec3d) : this(world) {
        setPosition(pos)
    }

    override fun initDataTracker() {
        super.initDataTracker()
        dataTracker.startTracking(STANDING, false)
        dataTracker.startTracking(PEDESTAL_POS, Optional.empty())
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
        nbt[STANDING_NBT] = standing
        pedestalPos?.let { nbt[PEDESTAL_NBT] = it }
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
        standing = nbt.getBoolean(STANDING_NBT)
        pedestalPos = NbtHelper.toBlockPos(nbt[PEDESTAL_NBT] as NbtCompound)
    }

    override fun initGoals() {
        goalSelector.add(wanderGoal)
        goalSelector.add(lookEntityGoal)
        goalSelector.add(lookAroundGoal)
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

        val STANDING: TrackedData<Boolean> = DataTracker.registerData(TerracottaWarriorEntity::class.java, TrackedDataHandlerRegistry.BOOLEAN)
        val PEDESTAL_POS: TrackedData<Optional<BlockPos>> = DataTracker.registerData(TerracottaWarriorEntity::class.java, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS)

        const val STANDING_NBT = "Standing"
        const val PEDESTAL_NBT = "Pedestal"
    }
}

