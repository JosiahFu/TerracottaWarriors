package archives.tater.terracottawarriors

import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.ai.goal.Goal
import net.minecraft.entity.ai.goal.GoalSelector
import net.minecraft.entity.data.TrackedData
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import java.util.*
import kotlin.reflect.KProperty

fun <T: Entity> EntityType(spawnGroup: SpawnGroup, entityFactory: EntityType.EntityFactory<T>, init: FabricEntityTypeBuilder<T>.() -> Unit): EntityType<T> {
    return FabricEntityTypeBuilder.create(spawnGroup, entityFactory).apply(init).build()
}

operator fun <T> TrackedData<T>.getValue(thisRef: Entity, property: KProperty<*>): T {
    return thisRef.dataTracker.get(this)
}

operator fun <T> TrackedData<T>.setValue(thisRef: Entity, property: KProperty<*>, value: T) {
    thisRef.dataTracker.set(this, value)
}

operator fun <T: Any> TrackedData<Optional<T>>.getValue(thisRef: Entity, property: KProperty<*>): T? {
    return thisRef.dataTracker.get(this).toNullable()
}

operator fun <T: Any> TrackedData<Optional<T>>.setValue(thisRef: Entity, property: KProperty<*>, value: T?) {
    thisRef.dataTracker.set(this, value.toOptional())
}

fun GoalSelector.add(goal: Pair<Int, Goal>) {
    add(goal.first, goal.second)
}

fun <T: Any> Optional<T>.toNullable(): T? = orElse(null)
fun <T: Any> T?.toOptional(): Optional<T> = Optional.ofNullable(this)

fun <T> RegistryKey<Registry<T>>.keyOf(id: Identifier): RegistryKey<T> = RegistryKey.of(this, id)

operator fun NbtCompound.set(key: String, value: NbtCompound) {
    put(key, value)
}

operator fun NbtCompound.set(key: String, value: Boolean) {
    putBoolean(key, value)
}

operator fun NbtCompound.set(key: String, value: Int) {
    putInt(key, value)
}

operator fun NbtCompound.set(key: String, value: UUID) {
    putUuid(key, value)
}

operator fun NbtCompound.set(key: String, value: String) {
    putString(key, value)
}

operator fun NbtCompound.set(key: String, value: BlockPos) {
    put(key, NbtHelper.fromBlockPos(value))
}

operator fun NbtCompound.set(key: String, value: BlockState) {
    put(key, NbtHelper.fromBlockState(value))
}
