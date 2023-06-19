package business.entities;

/**
 * Clase Paladin que hereda métodos de la clase Character
 */
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
    public Paladin(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass, int actualLife, int totalLife) {
        super(characterName, playerName, characterLevel, body, mind, spirit, characterClass, actualLife, totalLife);
    }

    /**
     * Esta función servirá para construir el Paladin
     * a partir de un Character
     *
     * @param character, personaje en cuestión
     */
    public Paladin(Character character) {
        super(character.getCharacterName(), character.getPlayerName(), character.getCharacterLevel(), character.getBody(), character.getMind(), character.getSpirit(), character.getCharacterClass(), character.getActualLife(), character.getTotalLife());
    }

    /**
     * Esta función servirà para calcular la vida inicial
     * de cada Cleric
     * Sobreescribe el método initialLifeCalculator
     * de la clase character
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
     * Paladin
     * Esta función sobreescribe initiative de la clase character
     *
     * @return initiative, iniciativa del personaje
     */
    @Override
    public int initiative(){
        int spirit = getSpirit();
        int d10 = diceRollD10();
        // Calculamos con la fórmula
        return d10 + spirit;
    }

    /**
     * Esta función servirá para realizar el movimiento Never on my watch,
     * con el que se sumará el spirit
     * sobreescribe el método attack de la clase character
     *
     * @return spirit resultante
     */
    @Override
    public int attack(){
        int spirit = getSpirit();
        int d8 = diceRollD8();
        // Calculamos con la fórmula
        return d8 + spirit;
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
     * @return cura resultante
     */
    public int heal(){
        int mind = getMind();
        int d10 = diceRollD10();
        // Calculamos con la fórmula
        return mind + d10;
    }

}
