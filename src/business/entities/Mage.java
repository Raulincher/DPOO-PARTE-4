package business.entities;

import business.entities.Character;

public class Mage extends Character {

    int shield;

    public Mage(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass, int shield) {
        super(characterName, playerName, characterLevel, body, mind, spirit, characterClass);
        this.shield = shield;
    }

    public Mage(Character character, int shield) {
        super(character.getCharacterName(), character.getPlayerName(), character.getCharacterLevel(), character.getBody(), character.getMind(), character.getSpirit(), character.getCharacterClass());
        this.shield = shield;
    }

    @Override
    public int getMind() {
        return super.getMind();
    }

    @Override
    public int getBody() {
        return super.getBody();
    }

    public void setShield(int shield){
        this.shield = shield;
    }

    public int getShield() {
        return shield;
    }

    public int initialLifeCalculator(int level){
        int body = getBody();
        return (10 + body) * level;
    }

    public int initiative(int d20){
        int mind = getMind();
        return d20 + mind;
    }

    public void shieldSetUp(int d6, int level){
        int shield;
        int mind = getMind();
        shield = (d6 + mind) * level;
        setShield(shield);
    }

    public int fireball(int d4){
        int dmg;
        int mind = getMind();
        dmg = d4 + mind;
        return dmg;
    }

    public int arcane_missile(int d6){
        int dmg;
        int mind = getMind();
        dmg = d6 + mind;
        return dmg;
    }

    public int passive(int dmg, int level){
        int total = dmg - level;
        if(total < 0){
            total = 0;
        }
        return total;
    }

}
