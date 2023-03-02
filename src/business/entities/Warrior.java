package business.entities;

import business.entities.Character;

public class Warrior extends Character {
    public Warrior(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass) {
        super(characterName, playerName, characterLevel, body, mind, spirit, characterClass);
    }

    public Warrior(Character character) {
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
    public void setSpirit(int spirit) {
        super.setSpirit(spirit);
    }

    public int initiative(int d12) {

        int initiative = 0;
        int spirit = getSpirit();

        initiative = d12 + spirit;

        return initiative;
    }

    public void selfMotivated(){
        setSpirit(getSpirit() + 1);
    }

    public int improvedSwordSlash(int d10) {
        int body = getBody();

        return d10 + body;
    }
}
