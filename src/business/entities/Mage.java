package business.entities;

import business.entities.Character;

public class Mage extends Character {

    int shield;

    //Creamos constructor con todos los atributos
    /**
     * Esta función servirá para construir el Mage de cero
     *
     * @param characterName, nombre del Character
     * @param playerName, nombre del jugador que lo crea
     * @param characterLevel, nivel del Character
     * @param body, body del Character
     * @param mind, mind del Character
     * @param spirit, spirit del Character
     * @param characterClass, clase del Character
     * @param shield, protección del Character
     */
    public Mage(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass, int shield) {
        super(characterName, playerName, characterLevel, body, mind, spirit, characterClass);
        this.shield = shield;
    }

    /**
     * Esta función servirá para construir el Mage
     * a partir de un Character
     *
     * @param character, personaje en cuestión
     */
    public Mage(Character character, int shield) {
        super(character.getCharacterName(), character.getPlayerName(), character.getCharacterLevel(), character.getBody(), character.getMind(), character.getSpirit(), character.getCharacterClass());
        this.shield = shield;
    }

    /**
     * Esta función llamará a la mind del Mage
     *
     * @return la mind del Mage
     */
    @Override
    public int getMind() {
        return super.getMind();
    }

    /**
     * Esta función llamará al body del Mage
     *
     * @return el body del Mage
     */
    @Override
    public int getBody() {
        return super.getBody();
    }

    /**
     * Esta función servirá para actualizar el shield del Mage
     *
     * @param shield, que será el nuevo shield del Mage
     */
    public void setShield(int shield){
        this.shield = shield;
    }

    /**
     * Esta función llamará al shield del Mage
     *
     * @return el shield del Mage
     */
    public int getShield() {
        return shield;
    }

    /**
     * Esta función servirà para calcular la vida inicial
     * de cada Mage
     *
     * @param level, que será el nivel del Mage
     * @return life, vida del personaje
     */
    public int initialLifeCalculator(int level){
        int body = getBody();

        // Calculamos con la fórmula
        return (10 + body) * level;
    }

    /**
     * Esta función servirá para calcular la iniciativa del
     * Mage
     *
     * @param d20, que será el dado con el que se calculará la iniciativa
     * @return initiative, iniciativa del personaje
     */
    public int initiative(int d20){
        int mind = getMind();

        // Calculamos con la fórmula
        return d20 + mind;
    }

    /**
     * Esta función servirá para calcular el shield de inicio
     *
     * @param d6, dado con el que se calculará el shield
     * @param level, nivel del personaje
     */
    public void shieldSetUp(int d6, int level){
        int shield;
        int mind = getMind();

        // Calculamos con la fórmula y actualizamos valor
        shield = (d6 + mind) * level;
        setShield(shield);
    }

    /**
     * Esta función servirá para calcular cuánto daño se hará con la fireball
     *
     * @param d4, dado con el que se calculará el daño
     * @return dmg, daño resultante
     */
    public int fireball(int d4){
        int dmg;
        int mind = getMind();

        // Calculamos con la fórmula
        dmg = d4 + mind;
        return dmg;
    }

    /**
     * Esta función servirá para calcular cuánto daño se hará con el misil
     *
     * @param d6, dado con el que se calculará el daño
     * @return dmg, daño resulltante
     */
    public int arcane_missile(int d6){
        int dmg;
        int mind = getMind();

        // Calculamos con la fórmula
        dmg = d6 + mind;
        return dmg;
    }


}
