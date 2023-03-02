package business;

import business.entities.Character;

import java.util.Random;

public class Paladin extends Character {
    public Paladin(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass) {
        super(characterName, playerName, characterLevel, body, mind, spirit, characterClass);
    }
    public Paladin(Character character) {
        super(character.getCharacterName(), character.getPlayerName(), character.getCharacterLevel(), character.getBody(), character.getMind(), character.getSpirit(), character.getCharacterClass());
    }

    public int setInitialLife(int level){
        int body = getBody();
        return (10 + body) * level;
    }

    public int initiative(int d10){
        int spirit = getSpirit();
        return d10 + spirit;
    }

    public void blessOfGoodLuck(int roll, Character character){
        character.setMind(character.getMind() + roll);
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
