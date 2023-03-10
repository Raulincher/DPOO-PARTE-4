package business;

import business.entities.*;
import business.entities.Character;
import persistance.AdventureAPI;
import persistance.AdventureDAO;

import java.io.IOException;
import java.util.ArrayList;

public class AdventureManager {

    AdventureDAO adventureDAO;
    AdventureAPI adventureAPI;
    CharacterManager characterManager;

    // Creamos constructores
    /**
     * Esta función servirá para construir el AdventureManager
     *
     * @param adventureDAO, lo vincularemos con su respectivo DAO
     * @param characterManager, lo vincularemos con la clase CharacterManager
     * @param adventureAPI, lo vincularemos con la API
     */
    public AdventureManager(AdventureDAO adventureDAO, CharacterManager characterManager, AdventureAPI adventureAPI){
        this.adventureDAO = adventureDAO;
        this.characterManager = characterManager;
        this.adventureAPI = adventureAPI;
    }

    public void setMonstersEncounter(ArrayList<Monster> monsters, ArrayList<ArrayList<Monster>> encounterMonsters, int monsterOption, int lastQuantity ,int monsterQuantity, int auxEncounter){

        int i = 0;

        // Abrimos bucle para añadir hasta el último monster en cuestión
        while(i < monsterQuantity) {
            encounterMonsters.get(auxEncounter).add(lastQuantity + i, monsters.get(monsterOption - 1));
            i++;
        }

    }

    /**
     * Esta función servirá para comprobar que a un encounter se le pueden añadir
     * los suficientes monsters
     *
     * @param encounter, num de encuentro
     * @param encounterMonsters, lista de ArrayLists con los monsters de cada encuentro
     * @param lastQuantity, índice con el último añadido
     * @param monsterQuantity, cantidad de monstruos a añadir
     * @return lastQuantity, int con la última cantidad de encuentros que hay
     */
    public int capacityEnsurance(int encounter, int lastQuantity, int monsterQuantity, ArrayList<ArrayList<Monster>> encounterMonsters){

        // Primero nos aseguramos de que el encuentro esté inicializado
        // En caso contrario lo inicializamos
        if(encounterMonsters.get(encounter).get(0) == null){
            encounterMonsters.set(encounter, new ArrayList<Monster>(monsterQuantity));
            lastQuantity = 0;
        }else{
            // Nos aseguramos de que quepa otro monster
            encounterMonsters.get(encounter).ensureCapacity(lastQuantity);
        }
        return lastQuantity;
    }

    /**
     * Esta función servirá para actualizar nuestra lista de monsters con sus cantidades
     * y sus nombres
     *
     * @param monstersQuantityAndNames, ArrayList con la cantidad de monstruos y sus respectivos nombres
     * @param monsters, ArrayList con todos los monsters
     * @param monsterQuantity, cantidad de monstruos
     * @param monsterOption, tipo de monstruos
     */
    public void setMonstersNames(ArrayList<String> monstersQuantityAndNames, ArrayList<Monster> monsters, int monsterQuantity, int monsterOption){
        int auxSize = monstersQuantityAndNames.size();

        // Nos aseguramos de que hayan monstruos añadidos en el encuentro
        if(monstersQuantityAndNames.size() == 0){
            monstersQuantityAndNames.add(0, monsters.get(monsterOption - 1).getMonsterName() + monsterQuantity);
        }
        else{

            // En caso que sí, iniciamos un bucle a través de todos los monsters con sus nombres
            for(int j = 0; j < monstersQuantityAndNames.size(); j++){
                String auxName = monstersQuantityAndNames.get(j);
                String[] auxNameSplit = auxName.split("\\d+");

                // En caso que el monster no esté añadido, lo añadimos a la ArrayList
                if(!auxNameSplit[0].matches(monsters.get(monsterOption - 1).getMonsterName())){
                    if(auxSize == monstersQuantityAndNames.size() && j == monstersQuantityAndNames.size() - 1){
                        monstersQuantityAndNames.add(j + 1, monsters.get(monsterOption - 1).getMonsterName() + monsterQuantity);
                    }

                }else{
                    // Si el monster ya está en la lista, actualizamos su cantidad y frenamos el bucle
                    auxName = monstersQuantityAndNames.get(j);
                    int auxQuantity = Integer.parseInt(auxName.replaceAll("[^0-9]", ""));
                    if(auxSize == monstersQuantityAndNames.size()){
                        monstersQuantityAndNames.set(j, monsters.get(monsterOption - 1).getMonsterName() + (monsterQuantity + auxQuantity));
                        j = monstersQuantityAndNames.size();
                    }
                }

            }

        }

    }

    /**
     * Esta función servirá para borrar un monster de un encuentro ya establecido
     *
     * @param monstersQuantityAndNames, ArrayList con la cantidad de monstruos y sus respectivos nombres
     * @param encounterMonsters, lista de ArrayLists con los monsters de cada encuentro
     * @param monsterQuantity, cantidad de monstruos a eliminar
     * @param lastQuantity, índice con el último añadido
     * @param monsterDeleteOption, num del monster que eliminaremos
     * @param auxEncounter, int que nos dice el encuentro del que estamos hablando
     * @return removedCounter, cantidad de monstruos eliminados
     */
    public int removeMonsterFromEncounter(ArrayList<ArrayList<Monster>> encounterMonsters, ArrayList<String> monstersQuantityAndNames, int monsterDeleteOption, int lastQuantity, int monsterQuantity, int auxEncounter){
        String monsterToBeErased = monstersQuantityAndNames.get(monsterDeleteOption - 1);
        String[] auxNameSplit = monsterToBeErased.split("\\d+");
        int removedCounter = 0;

        lastQuantity = lastQuantity + monsterQuantity;

        // Iniciamos un bucle a través de todos los monsters de los encuentros
        for(int i = 0; i < lastQuantity; i++){
            String comparedName = encounterMonsters.get(auxEncounter).get(i - removedCounter).getMonsterName();

            // Si el nombre coincide con el monster que queremos eliminar, lo eliminamos
            if(comparedName.equals(auxNameSplit[0])){
                encounterMonsters.get(auxEncounter).remove(i - removedCounter);
                removedCounter++;

                // Si el encuentro queda sin monstruos, lo dejamos en NULL
                if(encounterMonsters.get(auxEncounter).size() == 0){
                    encounterMonsters.get(auxEncounter).add(0,null);

                }
            }
        }
        monstersQuantityAndNames.remove(monsterDeleteOption - 1);
        return removedCounter;
    }

    /**
     * Esta función servirá para recoger todas las adventures
     *
     * @return se devolverá en una ArrayList todas las adventures
     */
    public ArrayList<Adventure> getAdventuresList(){
        return adventureDAO.getAllAdventures();
    }

    /**
     * Esta función servirá para recoger todas las adventures de las APIs
     *
     * @return se devolverá en una ArrayList todas las adventures
     */
    public ArrayList<Adventure> getAPIAdventuresList() throws IOException {return adventureAPI.getFromUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_12/adventures");}

    /**
     * Esta función servirá para comprobar si ya se ha añadido algún boss
     * en el encounter
     *
     * @param monstersInEncounter, lista de todos los monsters del encuentro
     * @param monstersList, lista con todos los monsters
     * @param option, monster a verificar
     * @return exist, bool para confirmar si se puede añadir otro boss
     */
    public boolean checkMonsterTypeOfEncounter(ArrayList<Monster> monstersInEncounter, ArrayList<Monster> monstersList, int option){

        boolean exist = false;
        int i = 0;
        int bossCounter = 0;

        // Iniciamos un bucle en la lista de monsters del encuentro
        while(i < monstersInEncounter.size()){

            // En caso que algún monster sea Boss, lo añadimos al contador
            if(monstersInEncounter.get(i).getMonsterChallenge().equals("Boss")){
                bossCounter++;
            }
            i++;
        }

        // Si el monster que queremos añadir es un boss, y hay más de 2 bosses en el encuentro
        // activamos bool
        if(monstersList.get(option - 1).getMonsterChallenge().equals("Boss") && bossCounter >= 1){
            exist = true;
        }


        return exist;
    }

    /**
     * Esta función servirá para crear una lista con todas las vidas
     * de los monsters
     *
     * @param monstersInEncounter, lista de todos los monsters del encuentro
     * @param monstersLife, lista de monsters con sus vidas
     * @param listOfPriorities, lista de prioridades
     * @param characterQuantity, cantidad de personajes del encuentro
     * @param charactersInParty, lista de personajes
     */
    public void setMonstersLifeList(ArrayList<Character> charactersInParty, ArrayList<String> monstersLife, ArrayList<Monster> monstersInEncounter, ArrayList<String> listOfPriorities, int characterQuantity){
        int z = 0;
        int i = 0;

        // Iniciamos bucle que dure toda la lista de prioridades, restado las de los personajes ya que no interesan
        while(z < listOfPriorities.size()){
            String[] auxName = listOfPriorities.get(z).split("\\d+");
            String actualName = auxName[0];

            // Si nos encontramos con un monster en esta, guardamos su vida
            for(int a = 0; a < monstersInEncounter.size(); a++){
                if(monstersInEncounter.get(a).getMonsterName().equals(actualName)){
                    monstersLife.add(i,monstersInEncounter.get(a).getMonsterName() + monstersInEncounter.get(a).getMonsterHitPoints() + "/" + monstersInEncounter.get(a).getMonsterHitPoints());
                    i++;
                    a = monstersInEncounter.size();
                }
            }
            z++;
        }
    }

    /**
     * Esta función servirá para crear una lista con todas las vidas
     * de los personajes
     *
     * @param characterInParty, lista de todos los personajes en el grupo
     * @param charactersLife, lista de personajes con sus vidas
     * @param isUsingApi, para comprobar si es a través de la API
     */
    public void setAdventurersLifeList(ArrayList<Character> characterInParty, ArrayList<String> charactersLife, boolean isUsingApi) throws IOException {
        int z;
        int characterInitialLife = 0;

        // Abrimos un if para comprobar si hay alguna vida establecida
        if(charactersLife.size() == 0) {
            z = 0;

            // Iniciamos un bucle para calcular todas las respectivas vidas
            while (z < characterInParty.size()) {

                // Caso de Adventurer
                if(characterInParty.get(z).getCharacterClass().equals("Adventurer") || characterInParty.get(z).getCharacterClass().equals("Warrior")){
                    Adventurer adventurer = new Adventurer(characterInParty.get(z));
                    characterInitialLife = adventurer.initialLifeCalculator(characterManager.revertXpToLevel(characterInParty.get(z).getCharacterLevel()));

                }
                // Caso de Champion
                else if(characterInParty.get(z).getCharacterClass().equals("Champion")){
                    Champion champion = new Champion(characterInParty.get(z));
                    characterInitialLife = champion.initialLifeCalculator(characterManager.revertXpToLevel(characterInParty.get(z).getCharacterLevel()));

                }
                // Caso de Cleric
                else if(characterInParty.get(z).getCharacterClass().equals("Cleric") || characterInParty.get(z).getCharacterClass().equals("Paladin")){
                    Cleric cleric = new Cleric(characterInParty.get(z));
                    characterInitialLife = cleric.initialLifeCalculator(characterManager.revertXpToLevel(characterInParty.get(z).getCharacterLevel()));

                }
                // Caso de Mage
                else if(characterInParty.get(z).getCharacterClass().equals("Mage")){
                    Mage mage = new Mage(characterInParty.get(z),0);
                    characterInitialLife = mage.initialLifeCalculator(characterManager.revertXpToLevel(characterInParty.get(z).getCharacterLevel()));
                }
                charactersLife.add(z, characterInParty.get(z).getCharacterName() + characterInitialLife + "/" + characterInitialLife);
                z++;
            }
        }
    }

    /**
     * Esta función servirá para contar los personajes que estén
     * muertos en ese instante
     *
     * @param charactersLife lista con todas las vidas de los personajes
     * @return número total de muertos
     */
    public int countDeadCharacters(ArrayList<String> charactersLife){
        int deadCounter = 0;

        // A través de un bucle revisamos cuáles no tienen vida
        for(int a = 0; a < charactersLife.size(); a++){
            String[] auxLife = charactersLife.get(a).split("/");
            int actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
            if(actualLife == 0){
                deadCounter++;
            }
        }

        return deadCounter;
    }

    /**
     * Esta función servirá para buscar el personaje que tenga menos vida
     *
     * @param charactersLife lista con todas las vidas de los personajes
     * @return índice del personaje con menos vida
     */
    public int smallestCharacterLife(ArrayList<String> charactersLife){
        int z = 0;
        int flag = 0;
        int index = 0;
        int smallerCharacterLife = 0;

        // Abrimos bucle a través de toda la lista de vidas
        while(z < charactersLife.size()){
            String[] auxLife = charactersLife.get(z).split("/");
            int actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));

            // Si el personaje no está muerto iremos comparando vida por vida
            if(actualLife != 0){
                if(flag == 0){
                    smallerCharacterLife = actualLife;
                    index = z;
                    flag = 1;
                }
                // En caso que la nueva vida de la lista sea menor, actualizamos el índice del personaje
                else{
                    if(smallerCharacterLife > actualLife){
                        smallerCharacterLife = actualLife;
                        index = z;
                    }
                }
            }
            z++;
        }
        return index;
    }

    /**
     * Esta función servirá para buscar el enemigo con mayor vida
     *
     * @param monstersLife lista con todas las vidas de los monsters
     * @return índice del monster con mayor vida
     */
    public int highestEnemyLife(ArrayList<String> monstersLife){
        int index = 0;
        int z = 0;
        int biggerMonsterLife = 0;
        int flag = 0;

        // Abrimos bucle a través de toda la lista de vidas de monsters
        while(z < monstersLife.size()){
            String[] auxLife = monstersLife.get(z).split("/");
            int actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));

            // Las iremos comparando y actualizaremos el índice en caso que haya una mayor
            if (actualLife != 0) {
                if (flag == 0) {
                    biggerMonsterLife = actualLife;
                    index = z;
                    flag = 1;
                } else {
                    if (biggerMonsterLife < actualLife) {
                        biggerMonsterLife = actualLife;
                        index = z;
                    }
                }
            }
            z++;
        }

        return index;
    }

    /**
     * Esta función servirá para buscar el monster que tenga menos vida
     *
     * @param monstersLife lista con todas las vidas de los monsters
     * @return índice del monster con menos vida
     */
    public int smallestEnemyLife(ArrayList<String> monstersLife){
        int index = 0;
        int z = 0;
        int smallerMonsterLife = 0;
        int flag = 0;

        // Abrimos bucle a través de la lista de vidas de los monsters
        while(z < monstersLife.size()){
            String[] auxLife = monstersLife.get(z).split("/");
            int actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));

            // Iremos comparando entre ellas, en caso que se detecte una menor actualizaremos índice
            if (actualLife != 0) {
                if (flag == 0) {
                    smallerMonsterLife = actualLife;
                    index = z;
                    flag = 1;
                } else {
                    if (smallerMonsterLife > actualLife) {
                        smallerMonsterLife = actualLife;
                        index = z;
                    }
                }
            }
            z++;
        }

        return index;
    }

    /**
     * Esta función servirá para actualizar la lista que cuente los magos
     * que hay en una batalla
     *
     * @param characterInParty lista con todos los personajes de la aventura
     * @param magesInBattle lista a actualizar
     */
    public void setMagesForAdventure(ArrayList<Character> characterInParty, ArrayList<Mage> magesInBattle){
        int z = 0;

        // Recorreremos un bucle e iremos añadiendo a la lista si se detecta una
        for(int a = 0; a < characterInParty.size(); a++){
            if(characterInParty.get(a).getCharacterClass().equals("Mage")){
                magesInBattle.add(z, new Mage(characterInParty.get(a), 0));
                z++;
            }
        }
    }

    /**
     * Esta función servirá para crear una lista con todas las prioridades
     * ordenadas según la iniciativa de cada personaje, para saber cuál
     * se moverá primero
     *
     * @param listOfPriorities, lista de todas las prioridades
     */
    public void orderListOfPriorities(ArrayList<String> listOfPriorities){
        int i = 0;
        int z;
        boolean auxFlag;

        // Iniciamos un bucle que recorra todas las prioridades
        while(i < listOfPriorities.size()){
            z = 0;
            String[] auxName = listOfPriorities.get(i).split("\\d+");
            String actualName = auxName[0];
            auxFlag = false;
            int actualInitiative = Integer.parseInt(listOfPriorities.get(i).replaceAll("[^0-9]", ""));

            // Volvemos a recorrer las prioridades con otro contador para poder compararlas
            while(z < listOfPriorities.size()){
                String[] auxCompareName = listOfPriorities.get(z).split("\\d+");
                String compareName = auxCompareName[0];
                int compareInitiative = Integer.parseInt(listOfPriorities.get(z).replaceAll("[^0-9]", ""));

                // Comparamos iniciativas para cambiar el orden
                if(actualInitiative < compareInitiative && i < z){
                    listOfPriorities.set(i, compareName + compareInitiative);
                    listOfPriorities.set(z, actualName + actualInitiative);
                    auxFlag = true;
                    z = listOfPriorities.size();
                }

                z++;
            }
            i++;
            if(auxFlag){
                i = 0;
            }
        }
    }

    /**
     * Esta función servirá para crear una lista con todas las prioridades
     * según la iniciativa de cada personaje y monster sin ordenar
     *
     * @param characterInParty, lista de personajes en el grupo
     * @param characterQuantity, int con el num de personajes
     * @param monsterQuantity, int con el num de monsters
     * @param magesInBattle lista con todos los magos de la batalla
     * @param monstersInEncounter lista con los monsters del encuentro
     * @return listOfPriorities, lista de prioridades sin ordenar
     */
    public ArrayList<String> listOfPriorities(int characterQuantity, int monsterQuantity, ArrayList<Mage> magesInBattle, ArrayList<Character> characterInParty, ArrayList<Monster> monstersInEncounter){
        ArrayList<String> listOfPriorities = new ArrayList<>(0);
        int i = 0;
        int z = 0;
        int characterSpirit = 0;
        int characterInitiative = 0;
        int diceroll = 0;

        // Iniciamos un bucle que dure la cantidad de personajes y monsters combinada
        while(i < characterQuantity + monsterQuantity){

            // Recogemos todas las iniciativas de los personajes
            if(i < characterInParty.size()){

                // Caso Adventurer
                if(characterInParty.get(i).getCharacterClass().equals("Adventurer") || characterInParty.get(i).getCharacterClass().equals("Warrior") || characterInParty.get(i).getCharacterClass().equals("Champion")){
                    Adventurer adventurer = new Adventurer(characterInParty.get(i));
                    diceroll = characterManager.diceRollD12();
                    characterInitiative = adventurer.initiative(diceroll);
                    listOfPriorities.add(z, characterInParty.get(i).getCharacterName() + characterInitiative);
                }
                // Caso Cleric
                else if(characterInParty.get(i).getCharacterClass().equals("Cleric") || characterInParty.get(i).getCharacterClass().equals("Paladin")){
                    Cleric cleric = new Cleric(characterInParty.get(i));
                    diceroll = characterManager.diceRollD10();
                    characterInitiative = cleric.initiative(diceroll);
                    listOfPriorities.add(z, characterInParty.get(i).getCharacterName() + characterInitiative);
                }
                // Caso Mage
                else if(characterInParty.get(i).getCharacterClass().equals("Mage")){
                    int b = 0;
                    for(int a = 0; a < magesInBattle.size(); a++){
                        if(characterInParty.get(i).getCharacterName().equals(magesInBattle.get(a).getCharacterName())){
                            b = a;
                        }
                    }
                    diceroll = characterManager.diceRollD20();
                    characterInitiative = magesInBattle.get(b).initiative(diceroll);
                    listOfPriorities.add(z, characterInParty.get(i).getCharacterName() + characterInitiative);
                }
                z++;
            }

            // Recogemos todas las iniciativas de los monsters
            if(i < monstersInEncounter.size()){
                listOfPriorities.add(z,monstersInEncounter.get(i).getMonsterName() + monstersInEncounter.get(i).getMonsterInitiative());
                z++;
            }
            i++;
        }

        return listOfPriorities;
    }
    /**
     * Esta función servirá para crear una lista con todos los monsters
     * que se encontrarán en toda la aventura, será una lista de listas
     *
     * @param encounterMonsters, lista de todos los monsters del encuentro
     * @param adventureEncounters, int con todos los encuentros de la adventure
     * @return encounterMonsters, ArrayList en cuestión
     */
    public ArrayList<ArrayList<Monster>> initializeEncounters(ArrayList<ArrayList<Monster>> encounterMonsters, int adventureEncounters){

        // Iniciamos bucle que recorra todos los encuentros y avamos añadiendo las ArrayLists
        for (int i = 0; i < adventureEncounters; i++) {
            encounterMonsters.add(i, new ArrayList<Monster>(1));
            encounterMonsters.get(i).add(0,null);
        }
        return encounterMonsters;
    }

    /**
     * Esta función servirá para contar cuantos monsters habrán
     * en un mismo encuentro
     *
     * @param storedName, lista con los nombres de monsters almacenados
     * @param monstersInEncounter, lista de todos los monsters del encuentro
     */
    public void countSameMonstersInEncounter(ArrayList<String> storedName, ArrayList<Monster> monstersInEncounter){

        int i = 0;
        int j = 0;
        int z = 0;
        boolean repeated = true;

        // Iniciamos bucle a través de todos los monster
        while(i < monstersInEncounter.size()){
            z = 0;

            // En la primera oleada guardamos el primer nombre
            if(i == 0){
                storedName.add(j, monstersInEncounter.get(i).getMonsterName());
                j++;
            }else{

                // En las demás, en un bucle comparamos los otros monsters con los que guardemos
                while(z < storedName.size()){
                    repeated = storedName.get(z).equals(monstersInEncounter.get(i).getMonsterName());
                    if(repeated){
                        z = storedName.size();
                    }else{
                        z++;
                    }
                }
                // Si no está repetido guardamos nombre
                if(!repeated){
                    storedName.add(j, monstersInEncounter.get(i).getMonsterName());
                    j++;
                }
            }

            i++;
        }
    }

    /**
     * Esta función servirá para crear una adventure
     *
     * @param adventureName, nombre deseado de la aventura
     * @param encounters, número de encuentros deseados
     * @param monsters, lista de lista de todos los monsters que habrán
     * @return se guardará la aventura a través del DAO
     */
    public boolean createAdventure(String adventureName, int encounters, ArrayList<ArrayList<Monster>> monsters){
        return adventureDAO.saveAdventure(new Adventure(adventureName, encounters, monsters));
    }

    /**
     /**
     * Esta función servirá para crear una adventure en la API
     *
     * @param adventureName, nombre deseado de la aventura
     * @param encounters, número de encuentros deseados
     * @param monsters, lista de lista de todos los monsters que habrán
     * @return se guardará la aventura a través de la API
     */
    public boolean createAdventureAPI(String adventureName, int encounters, ArrayList<ArrayList<Monster>> monsters) throws IOException {
        return adventureAPI.postToUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_12/adventures", new Adventure(adventureName, encounters, monsters));
    }

    /**
     * Esta función servirá para crear una adventure
     *
     * @param monstersDamage, lista con todos los daños que causarán los monsters
     * @param monstersInEncounter, lista con todos los monsters del encuentro
     */
    public void enemyDice(ArrayList<String> monstersDamage, ArrayList<Monster> monstersInEncounter){
        int z = 0;
        int i = 0;

        // Iniciamos un bucle por todos los monsters disponibles
        while(z < monstersInEncounter.size()){

            // En la primera oleada guardamos el daño
            if(z == 0){
                monstersDamage.add(i,monstersInEncounter.get(z).getMonsterName() + " " + monstersInEncounter.get(z).getMonsterDice());
                i++;
            }else{
                String [] auxName = monstersDamage.get(i - 1).split(" ");
                String actualName = auxName[0];

                // Guardaremos el daño en caso que se trate de otro monster del último daño guardado
                if(!actualName.equals(monstersInEncounter.get(z).getMonsterName())){
                    monstersDamage.add(i,monstersInEncounter.get(z).getMonsterName() + " " + monstersInEncounter.get(z).getMonsterDice());
                    i++;
                }
            }
            z++;
        }
    }

}
