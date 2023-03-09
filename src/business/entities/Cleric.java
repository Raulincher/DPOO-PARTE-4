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
    public Cleric(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass) {
        super(characterName, playerName, characterLevel, body, mind, spirit, characterClass);
    }

    /**
     * Esta función servirá para construir el Cleric
     * a partir de un Character
     *
     * @param character, personaje en cuestión
     */
    public Cleric(Character character) {
        super(character.getCharacterName(), character.getPlayerName(), character.getCharacterLevel(), character.getBody(), character.getMind(), character.getSpirit(), character.getCharacterClass());
    }

    /**
     * Esta función llamará al body del Cleric
     *
     * @return el body del Cleric
     */
    @Override
    public int getBody() {
        return super.getBody();
    }

    /**
     * Esta función llamará al spirit del Cleric
     *
     * @return el spirit del Cleric
     */
    @Override
    public int getSpirit() {
        return super.getSpirit();
    }

    /**
     * Esta función llamará a la mind del Cleric
     *
     * @return la mind del Cleric
     */
    @Override
    public int getMind() {
        return super.getMind();
    }

    /**
     * Esta función servirá para actualizar el mind del Cleric
     *
     * @param mind, que será el nuevo mind del Cleric
     */
    @Override
    public void setMind(int mind) {
        super.setMind(mind);
    }

    /**
     * Esta función servirà para calcular la vida inicial
     * de cada Cleric
     *
     * @param level, que será el nivel del Cleric
     * @return life, vida del personaje
     */
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
    public int prayerOfHealing(int d10){
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
    public int notOnMyWatch(int d4){
        int spirit = getSpirit();

        // Calculamos con la fórmula
        return d4 + spirit;
    }

    /**
     * Esta función servirá para curar al propip personaje su mind
     *
     * @param d10, dado con el que se calculará la cura
     * @return mind resultante
     */
    public int prayerOfSelfHealing(int d10){
        int mind = getMind();

        // Calculamos con la fórmula
        return d10 + mind;
    }
}
