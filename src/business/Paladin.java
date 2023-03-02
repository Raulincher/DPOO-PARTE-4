package business;

import business.entities.Character;

import java.util.Random;

public class Paladin extends Character {
    public Paladin(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass) {
        super(characterName, playerName, characterLevel, body, mind, spirit, characterClass);
    }

    @Override
    public int getBody() {
        return super.getBody();
    }

    @Override
    public int getSpirit() {
        return super.getSpirit();
    }

    @Override
    public int getMind() {
        return super.getMind();
    }

    public int setInitialLife(int level){
        int body = getBody();
        return (10 + body) * level;
    }

    public int initiative(int d10){
        int spirit = getSpirit();
        return d10 + spirit;
    }

    public int blessOfGoodLuck(){
        int roll = 0;

        // Usaremos la clase Random para sacar el número aleatorio con upperbound de 3
        Random rand = new Random();
        int upperbound = 3;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }

    public int prayerOfMassHealing(int d10){
        int mind = getMind();
        return mind + d10;
    }

    public int neverOnMyWatch(int d8){
        int spirit = getSpirit();
        return d8 + spirit;
    }

    public int passive(int dmg){
        return dmg / 2;
    }

}
