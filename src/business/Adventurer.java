package business;

import business.entities.Character;

public class Adventurer extends Character {
    public Adventurer(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass) {
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

    @Override
    public void setSpirit(int spirit) {
        super.setSpirit(spirit);
    }

    public int initialLifeCalculator(int level){
        int life;
        int body = getBody();

        // Calculamos la vida con la fórmula
        life = (10 + body) * level;

        return life;
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

    public int swordSlash(int d6){
        int body = getBody();

        return d6 + body;
    }


    public int bandageTime(int d8){
        int mind = getMind();

        return mind + d8;
    }
}


