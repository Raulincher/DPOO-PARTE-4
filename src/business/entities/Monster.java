package business.entities;

/**
 * Clase Monster, nos permite crear monstruos
 */
public class Monster {
    String name;
    String challenge;
    int experience;
    int hitPoints;
    int initiative;
    int actualHitPoints;
    String damageDice;
    String damageType;

    //Creamos constructor con todos los atributos
    /**
     * Esta función servirá para construir el Monster
     *
     * @param monsterName, nombre del Monster
     * @param monsterChallenge, dificultad del Monster
     * @param monsterXpDrop, experiencia que deja el Monster
     * @param monsterHitPoints, hitPoints del Monster
     * @param monsterDice, dado de daño del Monster
     * @param damageType, tipo de daño del Monster
     * @param monsterInitiative, iniciativa del monstruo en combate
     * @param actualHitPoints, PV actuales
     */
    public Monster(String monsterName, String monsterChallenge, int monsterXpDrop, int monsterHitPoints, int actualHitPoints, int monsterInitiative, String monsterDice, String damageType){
        this.name = monsterName;
        this.challenge = monsterChallenge;
        this.experience = monsterXpDrop;
        this.hitPoints = monsterHitPoints;
        this.initiative = monsterInitiative;
        this.damageDice = monsterDice;
        this.damageType = damageType;
        this.actualHitPoints = actualHitPoints;
    }

    /**
     * Esta función llamará al nombre del Monster
     *
     * @return name, que será el nombre del Monster
     */
    public String getMonsterName() {
        return name;
    }

    /**
     * Esta función llamará la dificultad del Monster
     *
     * @return challenge, que será la dificultad del Monster
     */
    public String getMonsterChallenge() {
        return challenge;
    }

    /**
     * Esta función llamará el num de hitPoints del Monster
     *
     * @return hitPoints, que será el núm de hitPoints del Monster
     */
    public int getMonsterHitPoints() {
        return hitPoints;
    }

    /**
     * Esta función llamará la iniciativa del Monster
     *
     * @return initiative, que será la iniciativa del Monster
     */
    public int getMonsterInitiative() {
        return initiative;
    }

    /**
     * Esta función llamará el num de XP que se gana al derrotarlo
     *
     * @return experience, que será num de XP
     */
    public int getMonsterXpDrop() {
        return experience;
    }

    /**
     * Esta función llamará el tipo de daño del Monster
     *
     * @return damageType, que será el tipo de daño del Monster
     */
    public String getDamageType() {
        return damageType;
    }

    /**
     * Esta función llamará dado de daño del Monster
     *
     * @return damageDice, que será dado de daño del Monster
     */
    public String getMonsterDice() {
        return damageDice;
    }

    /**
     * Esta función servirá para actualizar la initiative del monster
     *
     * @param initiative, será la initiative del monster
     */
    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    /**
     * Esta función servirá para actualizar la vida actual del monster
     *
     * @param actualHitPoints, será la nueva vida actual del monster
     */
    public void setActualHitPoints(int actualHitPoints) {
        this.actualHitPoints = actualHitPoints;
    }

    /**
     * Esta función recogerá la vida actual del monster
     *
     * @return actualHitPoints, que será la vida actual del monster
     */
    public int getActualHitPoints() {
        return actualHitPoints;
    }
}
