package business.entities;

import business.entities.Character;

public class Champion extends Character {

    //Creamos constructor con todos los atributos
    /**
     * Esta función servirá para construir el Champion de cero
     *
     * @param characterName, nombre del Character
     * @param playerName, nombre del jugador que lo crea
     * @param characterLevel, nivel del Character
     * @param body, body del Character
     * @param mind, mind del Character
     * @param spirit, spirit del Character
     * @param characterClass, clase del Character
     */
    public Champion(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass) {
        super(characterName, playerName, characterLevel, body, mind, spirit, characterClass);
    }

    /**
     * Esta función servirá para construir el Champion
     * a partir de un Character
     *
     * @param character, personaje en cuestión
     */
    public Champion(Character character) {
        super(character.getCharacterName(), character.getPlayerName(), character.getCharacterLevel(), character.getBody(), character.getMind(), character.getSpirit(), character.getCharacterClass());
    }

    /**
     * Esta función llamará al body del Champion
     *
     * @return el body del Champion
     */
    @Override
    public int getBody() {
        return super.getBody();
    }

    /**
     * Esta función llamará al spirit del Champion
     *
     * @return el spirit del Champion
     */
    @Override
    public int getSpirit() {
        return super.getSpirit();
    }

    /**
     * Esta función llamará a la mind del Champion
     *
     * @return la mind del Champion
     */
    @Override
    public int getMind() {
        return super.getMind();
    }

    /**
     * Esta función servirá para actualizar el spirit del Champion
     *
     * @param spirit, que será el nuevo spirit del Champion
     */
    @Override
    public void setSpirit(int spirit) {
        super.setSpirit(spirit);
    }

    /**
     * Esta función servirà para calcular la vida inicial
     * de cada Champion
     *
     * @param level, que será el nivel del Champion
     * @return life, vida que tendrá el personaje
     */
    public int initialLifeCalculator(int level){
        int life;
        int body = getBody();

        // Calculamos la vida con la fórmula
        life = ((10 + body) * level) + (body * level);

        return life;
    }

    /**
     * Esta función servirá para calcular la iniciativa del
     * Champion
     *
     * @param d12, que será el dado con el que se calculará la iniciativa
     * @return initiative, número de iniciativa del personaje
     */
    public int initiative(int d12) {

        int initiative = 0;
        int spirit = getSpirit();

        // Calculamos con la fórmula
        initiative = d12 + spirit;

        return initiative;
    }

    /**
     * Esta función servirá para hacer la acción de Motivational Speech
     * en la batalla, es decir, subir 1 al spirit.
     *
     * @param character, personaje al que se le aplicará la subida
     */
    public void MotivationalSpeech(Character character){
        character.setSpirit(character.getSpirit() + 1);
    }

    /**
     * Esta función servirá para realizar el ataque Sword Slash
     * mejorado del Champion
     *
     * @param d10, que será el dado para calcular el ataque
     * @return int con el ataque que realizará
     */
    public int improvedSwordSlash(int d10){
        int body = getBody();

        // Calculamos con la fórmula
        return d10 + body;
    }

    /**
     * Esta función servirá para calcular cuánto se curará
     * el Champion
     *
     * @param totalLife, vida máxima que puede tener
     * @param leftLife, vida restante
     * @return healing, número de curación que tendrá
     */
    public int improvedBandageTime(int totalLife, int leftLife){

       int healing = totalLife - leftLife;

       // Nos aseguramos de que no tenga una curación negativa
        if(healing < 0){
            healing = -1 * healing;
        }

        return healing;
    }
}
