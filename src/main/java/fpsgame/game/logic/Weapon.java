package fpsgame.game.logic;

public abstract class Weapon {
    
}

class Gun extends Weapon {
    private int ammo;
    
    public Gun(int initialAmmo) {
        this.ammo = initialAmmo;
    }
    
    public boolean shoot() {
        if (ammo > 0) {
            ammo--;
            return true; // Successful shot
        }
        return false; // No ammo left
    }
    
    public void reload(int ammoCount) {
        this.ammo += ammoCount;
    }
    
    public int getAmmo() {
        return ammo;
    }
}

class Melee extends Weapon {
    public void attack() {
        // Melee attack logic
    }
}
            