package com.damagedearth.Worlds;

import com.damagedearth.DamagedEarth;
import com.damagedearth.Entities.Components.Entity;
import com.damagedearth.Entities.ControlledEntityPlayer;
import com.damagedearth.Entities.EnumPlayerClass;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicWorld
{
    protected int spawnX, spawnY;
    public ControlledEntityPlayer thePlayer;
    protected List<Entity> entityList = new ArrayList<Entity>();

    DamagedEarth damagedEarth;

    public BasicWorld(int spawnX, int spawnY, DamagedEarth damagedEarth)
    {
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        thePlayer = new ControlledEntityPlayer(this.spawnX, this.spawnY, 40, 45, this, EnumPlayerClass.SCIENTIST);
        this.damagedEarth = damagedEarth;
    }

    public abstract void gameLoop();

    public abstract void loadWorld();

    /**
     * Updates all entities and objects in the world
     */
    public void updateAndRender()
    {
        this.gameLoop();
        try
        {
            for (Entity entity : this.entityList)
            {
                entity.onLivingUpdate();
            }
            this.thePlayer.onLivingUpdate();
        }
        catch (Exception e)
        {
        }
        this.scrollWorld(this.thePlayer.getX(), this.thePlayer.getY());
    }

    /**
     * The viewing area has specific coordinates(top left). If the player gets near the end of the viewing area,
     * it will scroll and increase the coordinates of the viewing area to the direction the player is going. Not
     * sure how this will react with the teleport() method, but it works perfectly with the move()
     */
    public void scrollWorld(double playerX, double playerY)
    {
        if (playerX >= (DamagedEarth.VIEW_CORDS_X + damagedEarth.width) - 300)
        {
            DamagedEarth.TRANSLATE_MODIFIER_X = 2;
            DamagedEarth.VIEW_CORDS_X += 2;
        }
        else if (playerX <= (DamagedEarth.VIEW_CORDS_X) + 300)
        {
            DamagedEarth.TRANSLATE_MODIFIER_X = -2;
            DamagedEarth.VIEW_CORDS_X -= 2;
        }

        if (playerY >= (DamagedEarth.VIEW_CORDS_Y + damagedEarth.height) - 100)
        {
            DamagedEarth.TRANSLATE_MODIFIER_Y = 2;
            DamagedEarth.VIEW_CORDS_Y += 2;
        }
        else if (playerY <= (DamagedEarth.VIEW_CORDS_Y) + 100)
        {
            DamagedEarth.TRANSLATE_MODIFIER_Y = -2;
            DamagedEarth.VIEW_CORDS_Y -= 2;
        }
    }

    public int getSpawnX()
    {
        return spawnX;
    }

    public void setSpawnX(int spawnX)
    {
        this.spawnX = spawnX;
    }

    public int getSpawnY()
    {
        return spawnY;
    }

    public void setSpawnY(int spawnY)
    {
        this.spawnY = spawnY;
    }

    public List<Entity> getEntityList()
    {
        return entityList;
    }
}
