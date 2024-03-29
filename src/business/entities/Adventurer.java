package business.entities;

/**
 * Clase Adventurer que hereda métodos de la clase Character
 */
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
     * @param actualLife, PV actuales
     * @param totalLife, PV totales
     */
    public Adventurer(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass, int actualLife, int totalLife) {
        super(characterName, playerName, characterLevel, body, mind, spirit, characterClass, actualLife, totalLife);
    }


    /**
     * Esta función servirá para construir el Adventurer
     * a partir de un Character
     *
     * @param character, personaje en cuestión
     */
    public Adventurer(Character character) {
        super(character.getCharacterName(), character.getPlayerName(), character.getCharacterLevel(), character.getBody(), character.getMind(), character.getSpirit(), character.getCharacterClass(), character.getActualLife(), character.getTotalLife());
    }

    /**
     * Esta función servirá para calcular la vida
     * inicial de un personaje.
     * Sobreescribe el método initialLifeCalculator
     * de la clase character
     *
     * @return initiative, iniciativa del personaje
     */
    @Override
    public int initialLifeCalculator(int level) {
        int life;
        int body = getBody();

        // Calculamos la vida con la fórmula
        life = (10 + body) * level;

        return life;
    }

    /**
     * Esta función servirá para calcular la iniciativa del
     * adventurer
     * Esta función sobreescribe initiative de la clase character
     *
     * @return initiative, iniciativa del personaje
     */
    @Override
    public int initiative() {
        int initiative;
        int spirit = getSpirit();
        int d12 = diceRollD12();

        // Calculamos la iniciativa del adventuer
        initiative = d12 + spirit;

        return initiative;
    }

    /**
     * Esta función servirá para realizar el ataque Sword Slash
     * del adventurer
     * sobreescribe el método attack de la clase character
     *
     * @return ataque del personaje
     */
    @Override
    public int attack(){
        int body = getBody();
        int d6 = diceRollD6();
        // Calculamos con la fórmula
        return d6 + body;
    }

    /**
     * Esta función servirá para hacer la acción de selfMotivated
     * en la batalla, es decir, subir 1 al spirit.
     */
    public void selfMotivated(){
        setSpirit(getSpirit() + 1);
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


