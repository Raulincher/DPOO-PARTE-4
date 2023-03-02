package business;

import business.entities.Character;

public class Cleric extends Character {
    public Cleric(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass) {
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

    public int prayerOfGoodLuck(int d10){
        int mind = getMind();
        return d10 + mind;
    }

    public int notOnMyWatch(int d4){
        int spirit = getSpirit();
        return d4 + spirit;
    }

    public int prayerOfSelfHealing(int d10){
        int mind = getMind();
        return d10 + mind;
    }
}
