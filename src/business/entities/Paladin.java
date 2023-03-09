package business.entities;

import business.entities.Character;

import java.util.Random;

public class Paladin extends Character {

    //Creamos constructor con todos los atributos
    /**
     * Esta función servirá para construir el Paladin de cero
     *
     * @param characterName, nombre del Character
     * @param playerName, nombre del jugador que lo crea
     * @param characterLevel, nivel del Character
     * @param body, body del Character
     * @param mind, mind del Character
     * @param spirit, spirit del Character
     * @param characterClass, clase del Character
     */
    public Paladin(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass) {
        super(characterName, playerName, characterLevel, body, mind, spirit, characterClass);
    }

    /**
     * Esta función servirá para construir el Paladin
     * a partir de un Character
     *
     * @param character, personaje en cuestión
     */
    public Paladin(Character character) {
        super(character.getCharacterName(), character.getPlayerName(), character.getCharacterLevel(), character.getBody(), character.getMind(), character.getSpirit(), character.getCharacterClass());
    }

    /**
     * Esta función servirá para calcular la iniciativa del
     * Paladin
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
     * Esta función servirá para realizar el movimiento de Bless of good luck
     *
     * @param roll, necesario para el cálculo
     * @param character, personaje que recibirá la subida de mind
     */
    public void blessOfGoodLuck(int roll, Character character){
        character.setMind(character.getMind() + roll);
    }

    /**
     * Esta función servirá para realizar el Prayer of mass healing,
     * con el que se sumará la mind
     *
     * @param d10, dado para calcular la suma de mind
     * @return cura resultante
     */
    public int prayerOfMassHealing(int d10){
        int mind = getMind();

        // Calculamos con la fórmula
        return mind + d10;
    }

    /**
     * Esta función servirá para realizar el movimiento Never on my watch,
     * con el que se sumará el spirit
     *
     * @param d8, dado con el que se calculará la suma
     * @return spirit resultante
     */
    public int neverOnMyWatch(int d8){
        int spirit = getSpirit();

        // Calculamos con la fórmula
        return d8 + spirit;
    }
}
