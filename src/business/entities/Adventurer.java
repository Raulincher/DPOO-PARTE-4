package business.entities;

import business.entities.Character;

public class Adventurer extends Character {

    //Creamos constructor con todos los atributos
    /**
     * Esta función servirá para construir el Adventurer de cero
     *
     * @param characterName, nombre del Character
     * @param playerName, nombre del jugador que lo crea
     * @param characterLevel, nivel del Character
     * @param body, body del Character
     * @param mind, mind del Character
     * @param spirit, spirit del Character
     * @param characterClass, clase del Character
     */
    public Adventurer(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass) {
        super(characterName, playerName, characterLevel, body, mind, spirit, characterClass);
    }

    /**
     * Esta función servirá para construir el Adventurer
     * a partir de un Character
     *
     * @param character, personaje en cuestión
     */
    public Adventurer(Character character) {
        super(character.getCharacterName(), character.getPlayerName(), character.getCharacterLevel(), character.getBody(), character.getMind(), character.getSpirit(), character.getCharacterClass());
    }

    /**
     * Esta función llamará al body del adventurer
     *
     * @return el body del adventurer
     */
    @Override
    public int getBody() {
        return super.getBody();
    }

    /**
     * Esta función llamará al spirit del adventurer
     *
     * @return el spirit del adventurer
     */
    @Override
    public int getSpirit() {
        return super.getSpirit();
    }

    /**
     * Esta función llamará a la mind del adventurer
     *
     * @return la mind del adventurer
     */
    @Override
    public int getMind() {
        return super.getMind();
    }

    /**
     * Esta función servirá para actualizar el spirit del adventurer
     *
     * @param spirit, que será el nuevo spirit del adventurer
     */
    @Override
    public void setSpirit(int spirit) {
        super.setSpirit(spirit);
    }

    /**
     * Esta función servirà para calcular la vida inicial
     * de cada adventurer
     *
     * @param level, que será el nivel del adventurer
     * @return life, vida del personaje
     */
    public int initialLifeCalculator(int level){
        int life;
        int body = getBody();

        // Calculamos la vida con la fórmula
        life = (10 + body) * level;

        return life;
    }

    /**
     * Esta función servirá para calcular la iniciativa del
     * adventurer
     *
     * @param d12, que será el dado con el que se calculará la iniciativa
     * @return initiative, iniciativa del personaje
     */
    public int initiative(int d12) {

        int initiative = 0;
        int spirit = getSpirit();

        // Calculamos la iniciativa del adventuer
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
     * del adventurer
     *
     * @param d6, que será el dado para calcular el ataque
     * @return ataque del personaje
     */
    public int swordSlash(int d6){
        int body = getBody();

        // Calculamos con la fórmula
        return d6 + body;
    }

    /**
     * Esta función servirá para calcular cuánto se curará
     * el adventurer
     *
     * @param d8, que será el dado para calcular la curación
     * @return curación del personaje
     */
    public int bandageTime(int d8){
        int mind = getMind();

        // Calculamos con la fórmula
        return mind + d8;
    }
}


