package presentation;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Clase UIManager, la cual permitirá mostrar toda la info necesaria por pantalla
 */
public class UIManager {

    // Añadimos un scanner
    Scanner scanner;

    /**
     * Esta función servirá para construir UIManager
     * No tendrá ni param ni return
     */
    public UIManager(){this.scanner = new Scanner(System.in);}

    /**
     * Esta función servirá para mostrar el menú de selección de archivos
     * No tendrá ni param ni return
     */
    public void showDataMenu(){
        System.out.println("Do you want to use your local or cloud data?\n");
        System.out.println("\n\t1) Local data");
        System.out.println("\t2) Cloud Data");
    }


    /**
     * Esta función servirá para enseñar el menú al usuario
     * No tendrá ni param ni return
     */
    public void showMainMenu(){
        System.out.println("The tavern keeper looks at you and says:");
        System.out.println("“Welcome adventurer! How can I help you?");
        System.out.println("\n\t1) Character creation");
        System.out.println("\t2) List characters");
        System.out.println("\t3) Create an adventure");
        System.out.println("\t4) Start an adventure");
        System.out.println("\t5) Exit");
    }

    /**
     * Esta función servirá para enseñar el menú a un usuario que
     * aún no ha creado 3 personajes como mínimo
     * No tendrá ni param ni return
     */
    public void showMainMenuDissabled(){
        System.out.println("The tavern keeper looks at you and says:");
        System.out.println("“Welcome adventurer! How can I help you?");
        System.out.println("\n\t1) Character creation");
        System.out.println("\t2) List characters");
        System.out.println("\t3) Create an adventure");
        System.out.println("\t4) Start an adventure (disabled: create 3 characters first)");
        System.out.println("\t5) Exit");
    }

    /**
     * Esta función servirá para enseñar el menú de aventuras al usuario
     * No tendrá ni param ni return
     */
    public void showAdventureMenu(){
        System.out.println("\n1. Add monster");
        System.out.println("2. Remove monster");
        System.out.println("3. Continue");
    }

    /**
     * Esta función servirá para enseñar cualquier mensaje al usuario
     *
     * @param message, mensaje en cuestión que se enseñará
     */
    public void showMessage(String message){
        System.out.println(message);
    }

    /**
     * Esta función servirá para mostrar que se está produciendo la Preparation Stage
     * No tendrá ni param ni return
     */
    public void showPrepStage(){
        System.out.println("-------------------------");
        System.out.println("*** Preparation stage ***");
        System.out.println("-------------------------\n");
    }

    /**
     * Esta función servirá para mostrar que se está produciendo la Combat Stage
     * No tendrá ni param ni return
     */
    public void showCombatStage(){
        System.out.println("\n\n--------------------");
        System.out.println("*** Combat stage ***");
        System.out.println("--------------------");
    }

    /**
     * Esta función servirá para mostrar que se ha terminado el combate y
     * se entra en la Rest Stage
     * No tendrá ni param ni return
     */
    public void showRestStage(){
        System.out.println("All enemies are defeated.");
        System.out.println("--------------------");
        System.out.println("*** Short rest stage ***");
        System.out.println("--------------------");
    }

    /**
     * Esta función servirá para pedirle al usuario que
     * escriba un Integer, cubriendo el posible error
     *
     * @param message, mensaje con el que se pedirá el Integer
     * @return int con el Integer que se pida
     */
    public int askForInteger(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nThis isn't an integer!\n");
            } finally {
                scanner.nextLine();
            }
        }
    }

    /**
     * Esta función servirá para pedirle al usuario que
     * escriba un String, cubriendo el posible error
     *
     * @param message, mensaje con el que se pedirá el String
     * @return String con lo que se pida
     */
    public String askForString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    /**
     * Esta función servirá para mostrar el mensaje correspondiente
     * según que clase sea el personaje que ataque
     *
     * @param actualName, nombre del personaje
     * @param attackedMonster, nombre del monstruo que ataca
     * @param characterClass, clase del personaje
     * @param heal, curación en caso que el personaje cure
     * @param multihit, para los magos que hagan multihit
     * @param healedCharacter, personaje que sea curado
     */
    public void messageAttack(String actualName, String attackedMonster, String characterClass, int heal, int multihit, String healedCharacter){
        String message = "empty";
        switch (characterClass) {
            case "Adventurer" ->
                //mostramos nombre de personaje que ataca, habilidad que usa y enemigo que es atacado
                message = "\n" + actualName + " attacks " + attackedMonster + " with Sword slash.";

            case "Warrior", "Champion" ->
                //mostramos nombre de personaje que ataca, habilidad que usa y enemigo que es atacado
                message = "\n" + actualName + " attacks " + attackedMonster + " with Improved sword slash.";

            case "Cleric" -> {
                if (healedCharacter.equals("empty")) {
                    //en caso de no sanar el clérigo atacará con su habilidad de clase
                    message = "\n" + actualName + " attacks " + attackedMonster + " with Not on my watch.";
                } else {
                    //mostramos que personaje realiza la sanción y a quien sana (PJ con menos vida en nuestro caso)
                    message = "\n" + actualName + " uses Prayer of healing. Heals " + heal + " hit points to " + healedCharacter;
                }
            }
            case "Paladin" -> {
                if (healedCharacter.equals("empty")) {

                    //en caso de no sanar el paladin atacará con su habilidad de clase
                    message = "\n" + actualName + " attacks " + attackedMonster + " with Never on my watch.";


                } else {
                    //mostramos que personaje realiza la sanción y curamos a toda la party aun consciente
                    message = "\n" + actualName + " uses Prayer of mass healing. Heals " + heal + " hit points to all conscious party";
                }
            }
            case "Mage" -> {

                //si hay más de 2 enemigos en batalla el mago pegara en área
                if (multihit == 1) {
                    message = "\n" + actualName + " uses Fireball. Hits to all alive monsters";
                }
                //si por el contrario no los hay el mago atacara al enemigo con más vida
                else {
                    message = "\n" + actualName + " attacks " + attackedMonster + " with Arcane missile.";
                }
            }
        }

        showMessage(message);
    }

    /**
     * Esta función servirá para mostrar el hit y el dañó provocado,
     * teniendo en cuenta si es crítico o si se falla
     *
     * @param damage, daño provocado
     * @param typeOfDamage, tipo de daño que provoca
     * @param isCrit, int para saber si el hit será crítico
     */
    public void hitMessage(int damage, String typeOfDamage, int isCrit){
        String message;

        // En caso que sea crítico
        if(isCrit == 2){
            message = "Critical hit and deals " + (damage * 2) + " " + typeOfDamage + " damage.";
        }
        // En caso que sea normal
        else if(isCrit == 1){
            message = "Hit and deals " + damage + " " + typeOfDamage + " damage.";
        }
        // En caso que falle
        else{
            message = "Fails and deals 0 damage.";
        }

        showMessage(message);
    }

    /**
     * Esta función servirá para mostrar la situación de vida en la que se encuentran
     * nos personajes
     *
     * @param actualLife, vida actual del personaje
     * @param actualName, nombre del personaje
     * @param totalLife, vida total que tenía el personaje
     * @param shield, escudo en caso que tenga
     * @param z, int para saber si se trata del primer personaje que se nombra
     */
    public void showLifeInBattle(int actualLife, String actualName, int totalLife, int shield, int z){

        String message;

        // En caso que no lleve shield
        if(shield == -1){
            if(z == 0){
               message = "\t- "+ actualName + "\t\t\t"+ actualLife + " / " + totalLife + " hit points";
            }else{
                message = "\t- "+ actualName + "\t\t"+ actualLife + " / " + totalLife + " hit points";
            }
        }
        // En caso que lleve shield
        else{
            if(z == 0){
                message = "\t- "+ actualName + "\t\t\t"+ actualLife + " / " + totalLife + " hit points (Shield: " + shield + ")";
            }else{
                message = "\t- "+ actualName + "\t\t"+ actualLife + " / " + totalLife + " hit points (Shield: " + shield + ")";
            }
        }
        showMessage(message);
    }

    /**
     * Esta función servirá para mostrar el grupo ha sido derrotado y han caído
     * todos inconscientes
     * No tendrá ni param ni return
     */
    public void unconsciousMessage(){
        System.out.println("""
                    Tavern keeper: Lad, wake up. Yes, your party fell unconscious.
                    Don’t worry, you are safe back at the Tavern.
                    """);
    }

    /**
     * Esta función servirá para mostrar el ataque en área del boss
     *
     * @param consciousPosition, lista con los jugadores aún conscientes
     * @param actualName, nombre del boss
     */
    public void bossAttackMessage(ArrayList<String> consciousPosition, String actualName){
        String message;

        // Abrimos un seguido de if/else if según los personajes con vida que aún estén disponibles
        // Para sufrir el ataque en área del boss
        if(consciousPosition.size() == 1){
            message = "\n" + actualName + " attacks " + consciousPosition.get(0) + ".";
        }else if(consciousPosition.size() == 2){
            message = "\n" + actualName + " attacks " + consciousPosition.get(0) + " and " + consciousPosition.get(1) + ".";
        }else if(consciousPosition.size() == 3){
            message = "\n" + actualName + " attacks " + consciousPosition.get(0) + ", " + consciousPosition.get(1) + " and " + consciousPosition.get(2) + ".";
        }else if(consciousPosition.size() == 4){
            message = "\n" + actualName + " attacks " + consciousPosition.get(0) + ", " + consciousPosition.get(1) + ", " + consciousPosition.get(2) + " and " + consciousPosition.get(3) + ".";
        }else{
            message = "\n" + actualName + " attacks " + consciousPosition.get(0) + ", " + consciousPosition.get(1) + ", " + consciousPosition.get(2) + ", " + consciousPosition.get(3) + " and " + consciousPosition.get(4) + ".";
        }
        showMessage(message);
    }

    /**
     * Esta función servirá para mostrar que un jugador ha caído inconsciente
     * @param name, nombre del PJ inconsciente
     */
    public void deadMessage(String name){
        showMessage(name + " falls unconscious.");
    }

    /**
     * Esta función servirá para mostrar que un personaje usa su habilidad en la fase de
     * preparación
     *
     * @param characterClass, clase del personaje
     * @param characterName, nombre del personaje
     * @param shield, shield en caso que tenga
     * @param roll, dado D3
     */
    public void showAbilitiesPrepPhase(String characterClass, String characterName, int shield, int roll) {

        String message = "";

        switch (characterClass) {
            case "Adventurer", "Warrior" ->
                //anunciamos habilidad
                message = characterName + " uses Self-Motivated. Their Spirit increases in +1";

            case "Champion" ->
                //anunciamos habilidad
                message = characterName + " uses Motivational speech. Everyone’s Spirit increases in +1";

            case "Cleric" ->
                //anunciamos habilidad
                message = characterName + " uses Prayer of good luck. Everyone’s Mind increases in +1";

            case "Paladin" ->
                //anunciamos habilidad
                message = characterName + " uses Blessing of good luck. Everyone’s Mind increases in +" + roll;

            case "Mage" ->
                //anunciamos habilidad
                message = characterName + " uses Mage shield. Shield recharges " + shield;

        }
        showMessage(message);
    }

    /**
     * Esta función servirá para mostrar que un personaje usa su habilidad en la fase de
     * descanso
     *
     * @param characterClass, clase del personaje
     * @param characterName, nombre del personaje
     * @param characterCuration, personaje que es curado
     * @param temporalLife, vida temporal para asegurar que no está muerto
     */
    public void showAbilitiesRestPhase(String characterClass, String characterName, int characterCuration, int temporalLife) {

        String message = characterName + " is reading a book. I guess it feels clever now! (no effect)";

        switch (characterClass) {
            //bandagetime para la clase aventurero y guerrero
            case "Adventurer":
            case "Warrior":
                if (temporalLife != 0) {

                    message = characterName + " uses BandageTime. Heals " + characterCuration + " hit points";
                } else {
                    message = characterName + " is unconscious";
                }

                break;
            //improved bandagetime para la clase campeón
            case "Champion":
                if (temporalLife != 0) {
                    message = characterName + " uses Improved bandage time. Heals " + characterCuration + " hit points";
                } else {
                    message = characterName + " is unconscious";
                }
                break;
            //prayer of self-healing para la clase clérigo
            case "Cleric":
                if (temporalLife != 0) {
                    message = characterName + " uses Payer of self-healing. Heals " + characterCuration + " hit points";
                } else {
                    message = characterName + " is unconscious";
                }
                break;
            case "Paladin":
                //prayer of mass healing para la clase paladin
                if (temporalLife != 0) {
                    message = characterName + " uses Prayer of mass healing. Heals " + characterCuration + " hit points to all the conscious party";
                } else {
                    message = characterName + " is unconscious";
                }
                break;
        }

        showMessage(message);
    }
}
