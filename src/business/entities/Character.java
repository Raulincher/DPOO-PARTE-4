package business.entities;

import com.google.gson.annotations.Expose;

import java.util.Random;

/**
 * Clase abstracta de Character
 */
public abstract class Character {

    //parámetros
    int actualLife, totalLife;
    @Expose
    String name, player, characterClass;
    @Expose
    int xp, body, mind, spirit;

    //Creamos constructor con todos los atributos
    /**
     * Esta función servirá para construir el Character
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
    public Character(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass, int actualLife, int totalLife){
        this.name = characterName;
        this.player = playerName;
        this.xp = characterLevel;
        this.body = body;
        this.mind = mind;
        this.spirit = spirit;
        this.characterClass = characterClass;
        this.actualLife = actualLife;
        this.totalLife = totalLife;
    }

    /**
     * Esta función servirá para actualizar la vida actual del character
     *
     * @param actualLife, será la nueva vida actual del character
     */
    public void setActualLife(int actualLife) {
        this.actualLife = actualLife;
    }

    /**
     * Esta función llamará a la vida actual del character
     *
     * @return actualLife, vida actual del character
     */
    public int getActualLife() {
        return actualLife;
    }

    /**
     * Esta función servirá para actualizar la vida total del character
     *
     * @param totalLife, será la nueva vida total del character
     */
    public void setTotalLife(int totalLife) {
        this.totalLife = totalLife;
    }

    /**
     * Esta función llamará a la vida total del character
     *
     * @return actualLife, vida total del character
     */
    public int getTotalLife() {
        return totalLife;
    }

    /**
     * Esta función llamará al nombre del character
     *
     * @return name, que será el nombre del character
     */
    public String getCharacterName(){return name;}

    /**
     * Esta función llamará al nombre del jugador
     *
     * @return player, que será el nombre del jugador
     */
    public String getPlayerName() {return player;}

    /**
     * Esta función llamará al body del character
     *
     * @return body, que será el body del character
     */
    public int getBody() {return body;}

    /**
     * Esta función llamará al nivel del character
     *
     * @return xp, que será el nivel del character
     */
    public int getCharacterLevel() {return xp;}

    /**
     * Esta función llamará a la mind del character
     *
     * @return mind, que será la mind del character
     */
    public int getMind() {return mind;}

    /**
     * Esta función llamará al spirit del character
     *
     * @return spirit, que será el spirit del character
     */
    public int getSpirit() {return spirit;}

    /**
     * Esta función llamará a la clase del character
     *
     * @return class, que será la clase del character
     */
    public String getCharacterClass() {return characterClass;}

    /**
     * Esta función servirá para actualizar la experiencia del character
     *
     * @param xp, que será la nueva experiencia del character
     */
    public void setXp(int xp) {this.xp = xp;}

    /**
     * Esta función servirá para actualizar el body del character
     *
     * @param body, que será el nuevo body del character
     */
    public void setBody(int body) {this.body = body;}

    /**
     * Esta función servirá para actualizar la mind del character
     *
     * @param mind, que será la nueva mind del character
     */
    public void setMind(int mind) {this.mind = mind;}

    /**
     * Esta función servirá para actualizar el spirit del character
     *
     * @param spirit, que será el nuevo spirit del character
     */
    public void setSpirit(int spirit) {this.spirit = spirit;}


    /**
     * Esta función servirá para actualizar la clase del character
     *
     * @param newClass, que será la nueva clase del character
     */
    public void setClass(String newClass) {this.characterClass = newClass;}


    /**
     * Esta función servirà para calcular la vida inicial
     * de cada personaje
     *
     * @param level, que será el nivel
     * @return life, vida que tendrá el personaje
     */
    public abstract int initialLifeCalculator(int level);

    /**
     * Esta función servirá para calcular la iniciativa del
     * personaje
     *
     * @return initiative, iniciativa del personaje
     */
    public abstract int initiative();

    /**
     * Esta función servirá para calcular el daño que
     * inflige el personaje
     *
     * @return damage, daño causado por el personaje
     */
    public abstract int attack();


    /**
     * Esta función genera un número entre el 1 y el 12 simulando tirar
     * un dado de 12 caras
     * La usamos en los hijos de la clase Character
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD3(){
        int roll;

        // Usaremos la clase Random para sacar el número aleatorio con upperbound de 12
        Random rand = new Random();
        int upperbound = 3;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }



    /**
     * Esta función genera un número entre el 1 y el 10 simulando tirar
     * un dado de 10 caras
     * La usamos en los hijos de la clase Character
     *
     * @return damage, int que será el número daño que causará
     */
    public int diceRollD10(){
        int roll;
        int damage;

        // Usaremos la clase Random para sacar el número aleatorio con upperbound de 10
        Random rand = new Random();
        int upperbound = 10;
        roll = rand.nextInt(upperbound) + 1;

        // Sacaremos directamente el daño que causará a través de varios ifs
        if(roll == 1){
            damage = 0;
        }else if(roll < 10){
            damage = 1;
        }else{
            damage = 2;
        }

        return damage;
    }

    /**
     * Esta función genera un número entre el 1 y el 6 simulando tirar
     * un dado de 6 caras
     * La usamos en los hijos de la clase Character
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD6(){
        int roll;

        // Usaremos la clase Random para sacar el número aleatorio con upperbound de 7
        Random rand = new Random();
        int upperbound = 6;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }

    /**
     * Esta función genera un número entre el 1 y el 8 simulando tirar
     * un dado de 8 caras
     * La usamos en los hijos de la clase Character
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD8(){
        int roll;

        // Usaremos la clase Random para sacar el número aleatorio con upperbound de 9
        Random rand = new Random();
        int upperbound = 8;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }


    /**
     * Esta función genera un número entre el 1 y el 20 simulando tirar
     * un dado de 8 caras
     * La usamos en los hijos de la clase Character
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD4(){
        int roll;

        // Usaremos la clase Random para sacar el número aleatorio con upperbound de 9
        Random rand = new Random();
        int upperbound = 4;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }


    /**
     * Esta función genera un número entre el 1 y el 20 simulando tirar
     * un dado de 8 caras
     * La usamos en los hijos de la clase Character
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD20(){
        int roll;

        // Usaremos la clase Random para sacar el número aleatorio con upperbound de 9
        Random rand = new Random();
        int upperbound = 20;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }


    /**
     * Esta función genera un número entre el 1 y el 12 simulando tirar
     * un dado de 12 caras
     * La usamos en los hijos de la clase Character
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD12(){
        int roll;

        // Usaremos la clase Random para sacar el número aleatorio con upperbound de 12
        Random rand = new Random();
        int upperbound = 12;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }

}
