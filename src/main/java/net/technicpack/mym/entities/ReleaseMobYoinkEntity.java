package net.technicpack.mym.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ReleaseMobYoinkEntity extends CatchMobYoinkEntity {
    public ReleaseMobYoinkEntity(World world, double x, double y, double z, ItemStack itemStack) {
        super(world, x, y, z, itemStack);
    }

    @Override
    protected void fall(float p_70069_1_) {
        Entity entity = EntityList.createEntityByName(getEntityItem().getTagCompound().getString("TypeName"), worldObj);

        if (!(entity instanceof  EntityAnimal)) {
            //How did this happen?!
            entity.setDead();
            this.setDead();
            return;
        }
        NBTTagCompound entityData = getEntityItem().getTagCompound().getCompoundTag("EntityData");
        EntityAnimal animal = (EntityAnimal)entity;
        animal.readEntityFromNBT(entityData);
        animal.setLocationAndAngles(this.posX, this.posY, this.posZ, 0, 0);
        worldObj.spawnEntityInWorld(animal);
        this.setDead();
    }
}
