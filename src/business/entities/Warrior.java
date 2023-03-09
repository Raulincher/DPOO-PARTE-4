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
    public Warrior(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass) {
        super(characterName, playerName, characterLevel, body, mind, spirit, characterClass);
    }

    /**
     * Esta función servirá para construir el Warrior
     * a partir de un Character
     *
     * @param character, personaje en cuestión
     */
    public Warrior(Character character) {
        super(character.getCharacterName(), character.getPlayerName(), character.getCharacterLevel(), character.getBody(), character.getMind(), character.getSpirit(), character.getCharacterClass());
    }

    /**
     * Esta función llamará al body del Warrior
     *
     * @return el body del Warrior
     */
    @Override
    public int getBody() {
        return super.getBody();
    }

    /**
     * Esta función llamará al spirit del Warrior
     *
     * @return el spirit del Warrior
     */
    @Override
    public int getSpirit() {
        return super.getSpirit();
    }

    /**
     * Esta función llamará a la mind del Warrior
     *
     * @return la mind del Warrior
     */
    @Override
    public int getMind() {
        return super.getMind();
    }

    /**
     * Esta función servirá para actualizar el spirit del Warrior
     *
     * @param spirit, que será el nuevo spirit del Warrior
     */
    @Override
    public void setSpirit(int spirit) {
        super.setSpirit(spirit);
    }

    /**
     * Esta función servirá para calcular la iniciativa del
     * Warrior
     *
     * @param d12, que será el dado con el que se calculará la iniciativa
     * @return initiative, iniciativa del personaje
     */
    public int initiative(int d12) {

        int initiative = 0;
        int spirit = getSpirit();

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
     * @param d10, que será el dado para calcular el ataque
     * @return int con el ataque que realizará
     */
    public int improvedSwordSlash(int d10) {
        int body = getBody();

        // Calculamos con la fórmula
        return d10 + body;
    }
}
