package business.entities;

import business.entities.Character;

public class Cleric extends Character {

    //Creamos constructor con todos los atributos
    /**
     * Esta función servirá para construir el Cleric de cero
     *
     * @param characterName, nombre del Character
     * @param playerName, nombre del jugador que lo crea
     * @param characterLevel, nivel del Character
     * @param body, body del Character
     * @param mind, mind del Character
     * @param spirit, spirit del Character
     * @param characterClass, clase del Character
     */
    public Cleric(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass, int actualLife, int totalLife) {
        super(characterName, playerName, characterLevel, body, mind, spirit, characterClass, actualLife, totalLife);
    }

    /**
     * Esta función servirá para construir el Cleric
     * a partir de un Character
     *
     * @param character, personaje en cuestión
     */
    public Cleric(Character character) {
        super(character.getCharacterName(), character.getPlayerName(), character.getCharacterLevel(), character.getBody(), character.getMind(), character.getSpirit(), character.getCharacterClass(), character.getActualLife(), character.getTotalLife());
    }

    /**
     * Esta función servirà para calcular la vida inicial
     * de cada Cleric
     *
     * @param level, que será el nivel del Cleric
     * @return life, vida del personaje
     */
    @Override
    public int initialLifeCalculator(int level){
        int body = getBody();

        // Calculamos con la fórmula
        return (10 + body) * level;
    }

    /**
     * Esta función servirá para calcular la iniciativa del
     * Cleric
     *
     * @param d10, que será el dado con el que se calculará la iniciativa
     * @return initiative, iniciativa del personaje
     */
    @Override
    public int initiative(int d10){
        int spirit = getSpirit();

        // Calculamos con la fórmula
        return d10 + spirit;
    }

    /**
     * Esta función servirá para hacer la acción de Prayer of good luck
     * en la batalla, es decir, subir 1 al mind.
     *
     * @param character, personaje al que le llegará la subida
     */
    public void prayerOfGoodLuck(Character character){
        character.setMind(character.getMind() + 1);
    }

    /**
     * Esta función servirá para curar al personaje con
     * un valor aleatorio
     *
     * @param d10, dado con el que se calculará la mind
     * @return int de vida que se curará
     */
    public int heal(int d10){
        int mind = getMind();

        // Calculamos con la fórmula
        return d10 + mind;
    }

    /**
     * Esta función servirá para aumentar el spirit del personaje
     *
     * @param d4, dado con el que se calculará el spirit
     * @return spirit resultante
     */
    public int attack(int d4){
        int spirit = getSpirit();

        // Calculamos con la fórmula
        return d4 + spirit;
    }

}
