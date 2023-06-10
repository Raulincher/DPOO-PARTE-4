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
    public Champion(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass, int actualLife, int totalLife) {
        super(characterName, playerName, characterLevel, body, mind, spirit, characterClass, actualLife, totalLife);
    }

    /**
     * Esta función servirá para construir el Champion
     * a partir de un Character
     *
     * @param character, personaje en cuestión
     */
    public Champion(Character character) {
        super(character.getCharacterName(), character.getPlayerName(), character.getCharacterLevel(), character.getBody(), character.getMind(), character.getSpirit(), character.getCharacterClass(), character.getActualLife(), character.getTotalLife());
    }

    /**
     * Esta función servirà para calcular la vida inicial
     * de cada Champion
     *
     * @param level, que será el nivel del Champion
     * @return life, vida que tendrá el personaje
     */
    @Override
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
     * @return initiative, número de iniciativa del personaje
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
     * @return int con el ataque que realizará
     */
    @Override
    public int attack(){
        int body = getBody();
        int d10 = diceRollD10();
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
