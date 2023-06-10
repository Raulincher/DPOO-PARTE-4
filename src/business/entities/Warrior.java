package business.entities;

import business.entities.Character;

public class Warrior extends Character {

    //Creamos constructor con todos los atributos
    /**
     * Esta función servirá para construir el Warrior de cero
     *
     * @param characterName, nombre del Character
     * @param playerName, nombre del jugador que lo crea
     * @param characterLevel, nivel del Character
     * @param body, body del Character
     * @param mind, mind del Character
     * @param spirit, spirit del Character
     * @param characterClass, clase del Character
     */
    public Warrior(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass, int actualLife, int totalLife) {
        super(characterName, playerName, characterLevel, body, mind, spirit, characterClass, actualLife, totalLife);
    }

    @Override
    public int initialLifeCalculator(int level) {
        int life;
        int body = getBody();

        // Calculamos la vida con la fórmula
        life = (10 + body) * level;

        return life;
    }
    /**
     * Esta función servirá para construir el Warrior
     * a partir de un Character
     *
     * @param character, personaje en cuestión
     */
    public Warrior(Character character) {
        super(character.getCharacterName(), character.getPlayerName(), character.getCharacterLevel(), character.getBody(), character.getMind(), character.getSpirit(), character.getCharacterClass(), character.getActualLife(), character.getTotalLife());
    }

    /**
     * Esta función servirá para calcular la iniciativa del
     * Warrior
     *
     * @return initiative, iniciativa del personaje
     */
    @Override
    public int initiative() {

        int initiative = 0;
        int spirit = getSpirit();
        int d12 = diceRollD12();
        // Calculamos con la fórmula
        initiative = d12 + spirit;

        return initiative;
    }

    /**
     * Esta función servirá para hacer la acción de selfMotivated
     * en la batalla, es decir, subir 1 al spirit.
     * No tendrá ni param ni return.
     */
    public void selfMotivated(){
        setSpirit(getSpirit() + 1);
    }

    /**
     * Esta función servirá para realizar el ataque Sword Slash
     * mejorado del Warrior
     *
     * @return int con el ataque que realizará
     */
    @Override
    public int attack() {
        int body = getBody();
        int d10 = diceRollD10();
        // Calculamos con la fórmula
        return d10 + body;
    }


    /**
     * Esta función servirá para calcular cuánto se curará
     * el adventurer
     *
     * @return curación del personaje
     */
    public int bandageTime(){
        int mind = getMind();
        int d8 = diceRollD8();
        // Calculamos con la fórmula
        return mind + d8;
    }
}
