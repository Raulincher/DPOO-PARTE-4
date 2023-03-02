package business.entities;

import business.entities.Character;

public class Cleric extends Character {
    public Cleric(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass) {
        super(characterName, playerName, characterLevel, body, mind, spirit, characterClass);
    }

    public Cleric(Character character) {
        super(character.getCharacterName(), character.getPlayerName(), character.getCharacterLevel(), character.getBody(), character.getMind(), character.getSpirit(), character.getCharacterClass());
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

    @Override
    public void setMind(int mind) {
        super.setMind(mind);
    }

    public int initialLifeCalculator(int level){
        int body = getBody();
        return (10 + body) * level;
    }

    public int initiative(int d10){
        int spirit = getSpirit();
        return d10 + spirit;
    }

    public void prayerOfGoodLuck(Character character){
        character.setMind(character.getMind() + 1);
    }

    public int prayerOfHealing(int d10){
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
