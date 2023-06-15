package presentation;

import business.*;
import business.entities.*;
import business.entities.Character;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
/**
 * Clase UIController, la cual manejará la info que otras clases mostrarán
 */
public class UIController {
    //parámetros
    private final UIManager uiManager;
    private final AdventureManager adventureManager;
    private final CharacterManager characterManager;
    private final MonsterManager monsterManager;

    /**
     * Esta función hace de constructor del UIController
     *
     * @param uiManager, para vincularlo con UIManager
     * @param adventureManager, para vincularlo con AdventureManager
     * @param characterManager, para vincularlo con CharacterManager
     * @param monsterManager, para vincularlo con MonsterManager
     */
    public UIController(UIManager uiManager, AdventureManager adventureManager, CharacterManager characterManager, MonsterManager monsterManager) {
        this.uiManager = uiManager;
        this.adventureManager = adventureManager;
        this.characterManager = characterManager;
        this.monsterManager = monsterManager;
    }

    /**
     * Esta función acciona el programa entero
     * No tendrá ni param ni return
     */
    public void run() throws IOException {
        int option;
        int i = 0;
        int totalCharacters = 0;

        // Damos la bienvenida después de crear variables
        uiManager.showMessage("Welcome to Simple LSRPG.\n");
        // preguntamos que tipo de almacenamiento desea el usuario
        uiManager.showDataMenu();
        option = uiManager.askForInteger("\nYour answer: ");
        uiManager.showMessage("\nLoading data...");

        //via json
        if(option == 1){
            // Nos aseguremos que se carguen los monsters
            ArrayList<Monster> monsters = monsterManager.getAllMonsters();
            if(monsters.size() > 0){
                uiManager.showMessage("Data was successfully loaded.\n\n\n");

                // Abrimos un bucle para mostrar el menú
                do {
                    ArrayList<Character> characters = characterManager.getAllCharacters();

                    // Obtenemos el número de personajes
                    for (Character ignored : characters) {
                        i++;
                        totalCharacters = i;
                    }
                    i = 0;

                    // Obtenemos todas las adventures y las guardamos
                    ArrayList<Adventure> adventures = adventureManager.getAdventuresList();

                    // En caso de que haya menos de 3 personajes creamos, denegamos opción 4
                    if(totalCharacters < 3){
                        uiManager.showMainMenuDissabled();
                        option = uiManager.askForInteger("\nYour answer: ");
                        if(option != 4){
                            executeOption(option, false);
                        }else{
                            uiManager.showMessage("\nTavern keeper: You need to gather a minimum of 3 characters to play an adventure.\n");
                        }

                        // En caso que no hayan adventures, denegamos opción 4 y avisamos que necesitan crearla
                    }else if(adventures == null){
                        uiManager.showMainMenu();
                        option = uiManager.askForInteger("\nYour answer: ");
                        if(option != 4){
                            executeOption(option, false);
                        }else{
                            uiManager.showMessage("\nYou need to create an adventure before playing one.\n");
                        }
                        // En caso de que no falte nada, se ejecuta la opción
                    }else{
                        uiManager.showMainMenu();
                        option = uiManager.askForInteger("\nYour answer: ");
                        executeOption(option, false);
                    }
                } while(option != 5);
            }else{
                uiManager.showMessage("Error: The monsters.json file can’t be accessed.\n");
            }

            //via API
        }else if(option == 2){

            ArrayList<Monster> getMonstersFromApi = monsterManager.getAPIMonsters();

            if(getMonstersFromApi != null){
                uiManager.showMessage("Data was successfully loaded.\n\n\n");

                do {
                    ArrayList<Character> characters = characterManager.getAPICharacters();
                    for (Character ignored : characters) {
                        i++;
                        totalCharacters = i;
                    }
                    i = 0;
                    ArrayList<Adventure> adventures = adventureManager.getAPIAdventuresList();
                    if(totalCharacters < 3){
                        uiManager.showMainMenuDissabled();
                        option = uiManager.askForInteger("\nYour answer: ");
                        if(option != 4){
                            executeOption(option, true);
                        }else{
                            uiManager.showMessage("\nTavern keeper: You need to gather a minimum of 3 characters to play an adventure.\n");
                        }
                    }else if(adventures == null){
                        uiManager.showMainMenu();
                        option = uiManager.askForInteger("\nYour answer: ");
                        if(option != 4){
                            executeOption(option, true);
                        }else{
                            uiManager.showMessage("\nYou need to create an adventure before playing one.\n");
                        }
                    }else{
                        uiManager.showMainMenu();
                        option = uiManager.askForInteger("\nYour answer: ");
                        executeOption(option, true);
                    }
                } while(option != 5);

            //volvemos a json cuando la API no esté disponible
            }else{
                uiManager.showMessage("Couldn’t connect to the remote server.\n");
                uiManager.showMessage("Reverting to local data.");

                //Seguimos el mismo proceso que en json
                ArrayList<Monster> monsters = monsterManager.getAllMonsters();
                if(monsters.size() > 0){

                    uiManager.showMessage("Data was successfully loaded.\n\n\n");

                    do {
                        ArrayList<Character> characters = characterManager.getAllCharacters();
                        for (Character ignored : characters) {
                            i++;
                            totalCharacters = i;
                        }
                        i = 0;
                        ArrayList<Adventure> adventures = adventureManager.getAdventuresList();
                        if(totalCharacters < 3){
                            uiManager.showMainMenuDissabled();
                            option = uiManager.askForInteger("\nYour answer: ");
                            if(option != 4){
                                executeOption(option, false);
                            }else{
                                uiManager.showMessage("\nTavern keeper: You need to gather a minimum of 3 characters to play an adventure.\n");
                            }
                        }else if(adventures == null){
                            uiManager.showMainMenu();
                            option = uiManager.askForInteger("\nYour answer: ");
                            if(option != 4){
                                executeOption(option, false);
                            }else{
                                uiManager.showMessage("\nYou need to create an adventure before playing one.\n");
                            }
                        }else{
                            uiManager.showMainMenu();
                            option = uiManager.askForInteger("\nYour answer: ");
                            executeOption(option, false);
                        }
                    } while(option != 5);
                }else{
                    uiManager.showMessage("Error: The monsters.json file can’t be accessed.\n");
                }
            }
        }else{
            uiManager.showMessage("Error: That's an incorrect option.\n");
        }
    }

    /**
     * Esta función ejecuta la opción del menú que el usuario desee
     *
     * @param option, opción que el usuario habrá escogido
     * @param isUsingApi, bool para saber si se está usando la API
     * @throws IOException, por posible fallo de lectura
     */
    private void executeOption(int option, boolean isUsingApi) throws IOException {
        uiManager.showMessage("");

        // Abrimos switch según el valor introducido
        switch (option) {
            // Opción 1: activamos la creación de personajes
            case 1 -> characterCreation(isUsingApi);
            // Opción 2: Preparamos la lista de personajes
            case 2 -> listCharacters(isUsingApi);
            // Opción 3: activamos la creación de adventures
            case 3 -> adventureCreation(isUsingApi);
            // Opción 4: activamos las adventures
            case 4 -> adventurePlay(isUsingApi);
            // Opción 5: Nos despedimos
            case 5 -> uiManager.showMessage("\nTavern keeper: Are you leaving already? See you soon, adventurer.\n");
            default -> {
                uiManager.showMessage("\nTavern keeper: I can't understand you, could you repeat it to me, please?");
                uiManager.showMessage("\nValid options are between 1 to 5 (including them)");
            }
        }
    }

    /**
     * Esta función representa la opción 1. Con un seguido
     * de preguntas prepara un gran terreno para que el usuario cree un personaje.
     *
     * @param isUsingApi, bool para saber si se está usando la API
     * @throws IOException, por posible fallo de lectura
     */
    private void characterCreation(boolean isUsingApi) throws IOException {
        // Preparamos variables y damos la bienvenida
        int error = 0;
        String characterName = "NoCharacterName";
        String playerName;
        int characterLevel = 0;
        boolean saved;
        boolean exist;
        uiManager.showMessage("""
                Tavern keeper: Oh, so you are new to this land.
                What’s your name?
                """);
        // Iniciamos un bucle que se detenga cuando se introduzca un dato erróneo
        while(error == 0 ) {
            characterName = uiManager.askForString("-> Enter your name: ");

            boolean correct = characterManager.nameCheck(characterName);
            characterName = characterManager.fixName(characterName);
            // Si el usuario no introduce bien el nombre, mostramos error
            if(!correct){
                uiManager.showMessage("\nTavern keeper: C'mon don't fool around and tell me your real name, will ya?");
                uiManager.showMessage("Character name can't include numbers or special characters\n");
                // Si los datos son correctos, nos aseguramos que no esté usado y cerramos bucle si no es así
            }else{
                exist = characterManager.characterNameDisponibility(characterName, isUsingApi);
                if(exist){
                    uiManager.showMessage("\nTavern keeper: Sorry lad this character name already exists\n");
                }else{
                    error = 1;
                }
            }
        }

        // Mostramos más mensajes para seguir con la creación
        uiManager.showMessage("\nTavern keeper: Hello, " +  characterName + ", be welcome.\n" + "And now, if I may break the fourth wall, who is your Player?\n");

        playerName = uiManager.askForString("-> Enter the player’s name: ");

        uiManager.showMessage("""

                Tavern keeper: I see, I see...
                Now, are you an experienced adventurer?
                """);

        // Abrimos otro bucle que se detenga cuando el usuario introduzca datos erróneos
        error = 0;
        while(error == 0) {
            characterLevel = uiManager.askForInteger("-> Enter the character’s level [1..10]: ");

            // A través de un if / else nos aseguremos que el nivel esté bien introducido
            if(characterLevel > 10 || characterLevel < 1){
                uiManager.showMessage("\nTavern keeper: I don't think you could be at that level, C'mon tell me the truth");
                uiManager.showMessage("Character level can't be lower than 1 or higher than 10\n");
            }
            else {
                error = 1;
            }
        }

        int experience = characterManager.experienceCalculator(characterLevel);
        // Mostramos más mensajes para seguir con la creación
        uiManager.showMessage("\nTavern keeper: Oh, so you are level "+ characterLevel + "!\n" + "Great, let me get a closer look at you...\n");
        uiManager.showMessage("Generating your stats...\n");

        // Creamos el body, mind y spirit aleatorios que se le atribuirá al personaje
        int[] body = characterManager.diceRoll2D6();
        int[] mind = characterManager.diceRoll2D6();
        int[] spirit = characterManager.diceRoll2D6();

        // Le mostramos el resultado
        uiManager.showMessage("Body: You rolled " + (body[0] + body[1]) + " (" + body[0] + " and "+ body[1]+ ").");
        uiManager.showMessage("Mind: You rolled " + (mind[0] + mind[1]) + " (" + mind[0] + " and " + mind[1] + ").");
        uiManager.showMessage("Spirit: You rolled " + (spirit[0] + spirit[1]) + " (" + spirit[0] + " and "+ spirit[1] + ").");

        // Le mostramos los stats calculados con la función statCalculator
        uiManager.showMessage("\nYour stats are:");
        String stat1 = characterManager.statCalculator(body);
        uiManager.showMessage("\t- Body: "+ stat1);
        String stat2 = characterManager.statCalculator(mind);
        uiManager.showMessage("\t- Mind: "+ stat2);
        String stat3 = characterManager.statCalculator(spirit);
        uiManager.showMessage("\t- Spirit: "+ stat3);

        // Creamos las variables de los stats
        int bodySum = Integer.parseInt(stat1);
        int mindSum = Integer.parseInt(stat2);
        int spiritSum = Integer.parseInt(stat3);

        uiManager.showMessage("\nTavern keeper: Looking good!");
        uiManager.showMessage("And, lastly, ?\n");

        //el usuario escoge entre 3 clases iniciales
        String characterClass;
        //bucle de comprobación de errores
        do{
            characterClass = uiManager.askForString("-> Enter the character’s initial class [Adventurer, Cleric, Mage]: ");

            if(!Objects.equals(characterClass, "Cleric") && !Objects.equals(characterClass, "Adventurer") && !Objects.equals(characterClass, "Mage")){
                uiManager.showMessage("\nTavern keeper: I'm sorry but that class is unknown to me...");
                uiManager.showMessage("Character can only be the type of class announced in brackets (Adventurer, Cleric or Mage)\n");
            }

        }while(!Objects.equals(characterClass, "Cleric") && !Objects.equals(characterClass, "Adventurer") && !Objects.equals(characterClass, "Mage"));

        characterClass = characterManager.initialEvolution(characterClass, characterLevel);

        //guardado de personaje dependiendo de que modo de datos estemos empleando
        if(!isUsingApi){
            saved = characterManager.createCharacter(characterName, playerName, experience, bodySum, mindSum, spiritSum, characterClass);
        }else{
            saved = characterManager.createCharacterAPI(characterName, playerName, experience, bodySum, mindSum, spiritSum, characterClass);
        }

        //mostramos el nombre del personaje confirmando que se ha creado y salimos de vuelta al menu
        if (saved){
            uiManager.showMessage("\nThe new character " + characterName + " has been created.\n");
        }else{
            uiManager.showMessage("\nTavern keeper: Im sorry friend but " + characterName +  " couldn't be found on the guild. Try it again next time.");
            uiManager.showMessage("There is an error in the save of your character.");
        }

    }

    /**
     * Esta función representa la opción 2, en la que se mostrarán los personajes
     * ya existentes.
     *
     * @param isUsingApi, bool para saber si se está usando la API
     * @throws IOException, por posible fallo de lectura
     */
    private void listCharacters(boolean isUsingApi) throws IOException {
        int i = 0;
        // Preguntamos al usuario qué personajes de qué jugador quiere ver
        uiManager.showMessage("Tavern keeper: Lads! The Boss wants to see you, come here!\n" + "Who piques your interest?");
        String playerName = uiManager.askForString("-> Enter the name of the Player to filter: ");

        //aquí filtramos el personaje por el nombre y también miramos que tipo de lectura usa si JSON o API
        ArrayList<Character> character = characterManager.filteredPlayers(playerName, isUsingApi);

        // En caso de que exista, abrimos if
        if(character.size() != 0){
            if(character.get(0) != null){
                uiManager.showMessage("You watch as some adventurers get up from their chairs and approach you.\n");

                // Mostramos en un bucle todos los personajes creados por el jugador
                while(i < character.size() ){
                    uiManager.showMessage("\t" + (i+1) +"." + character.get(i).getCharacterName());
                    i++;
                }
                uiManager.showMessage("\t\n0. Back");

                // Preguntamos por qué personaje quiere "conocer y nos aseguramos de que exista
                int characterPicked = uiManager.askForInteger("Who would you like to meet [0.." + character.size() + "]: ");
                if((characterPicked) > character.size() || (characterPicked) < 0) {
                    while ((characterPicked) > character.size() || (characterPicked) < 1) {
                        uiManager.showMessage("Tavern keeper: Please choose an existing character\n");
                        characterPicked = uiManager.askForInteger("Who would you like to meet [0.." + character.size() + "]: ");
                    }
                }

                // Si existe el personaje, abrimos if y nos quedamos con todos sus valores
                // para mostrarlos luego, pasándolos primero a int
                if(characterPicked != 0){
                    Character characterChosen = character.get(characterPicked - 1);

                    int bodyChosen = characterChosen.getBody();
                    String bodyIntToString;

                    if(bodyChosen >=  0){
                        bodyIntToString = "+" + bodyChosen;
                    }else{
                        bodyIntToString = String.valueOf(bodyChosen);
                    }

                    int mindChosen = characterChosen.getMind();
                    String mindIntToString;

                    if(mindChosen >=  0){
                        mindIntToString = "+" + mindChosen;
                    }else{
                        mindIntToString = String.valueOf(mindChosen);
                    }

                    int spiritChosen = characterChosen.getSpirit();
                    String spiritIntToString;

                    if(spiritChosen >=  0){
                        spiritIntToString = "+" + spiritChosen;
                    }else{
                        spiritIntToString = String.valueOf(spiritChosen);
                    }

                    // Mostramos el personaje y sus datos & stats
                    uiManager.showMessage("Tavern keeper: Hey" + characterChosen.getCharacterName()  + " get here; the boss wants to see you!\n");
                    uiManager.showMessage("* " + "Name:   " + characterChosen.getCharacterName());
                    uiManager.showMessage("* " + "Player: " + characterChosen.getPlayerName());
                    uiManager.showMessage("* " + "Class:  " + characterChosen.getCharacterClass());
                    uiManager.showMessage("* " + "level:  " + characterManager.revertXpToLevel(characterChosen.getCharacterLevel()));
                    uiManager.showMessage("* " + "XP:     " + characterChosen.getCharacterLevel());
                    uiManager.showMessage("* " + "Body:   " + bodyIntToString);
                    uiManager.showMessage("* " + "Mind:   " + mindIntToString);
                    uiManager.showMessage("* " + "Spirit: " + spiritIntToString);

                    // En caso de que el usuario quiera borrar el personaje, activamos la función
                    uiManager.showMessage("[Enter name to delete, or press enter to cancel]\n");
                    String characterDelete = uiManager.askForString("Do you want to delete " + characterChosen.getCharacterName() + " ? ");

                    boolean erased;

                    //el personaje se borrará en funcion de si estamos usando API o Json y de si existe o no
                    if(isUsingApi){
                        erased = characterManager.deleteCharacterAPI(characterDelete);
                    }else{
                        erased = characterManager.deleteCharacter(characterDelete);
                    }
                    if(erased){
                        uiManager.showMessage("Tavern keeper: I’m sorry kiddo, but you have to leave.\n");
                        uiManager.showMessage("Character " + characterChosen.getCharacterName() + " left the Guild.\n");
                    }else{
                        uiManager.showMessage("Tavern keeper: Don't worry mate you don't need to decide now. Come back when you decided who you want to fire\n");
                    }

                }else{
                    uiManager.showMessage("Tavern keeper: Don't worry mate you don't need to decide now. Come back when you decided who you want to meet\n");
                }
            }else{
                uiManager.showMessage("Tavern keeper: That player has never created a character. Come back later\n");
            }
        }else{
            uiManager.showMessage("\nTavern keeper: I'm sorry mate, it seems that there is no one here at the moment");
            uiManager.showMessage("There are no characters created at the moment\n");
        }
    }

    /**
     * Esta función representa la opción 3 y crea una adventure.
     *
     * @param isUsingApi, bool para saber si se está usando la API
     * @throws IOException, por posible fallo de lectura
     */
    private void adventureCreation(boolean isUsingApi) throws IOException {
        int error = 0;
        int adventureEncounters = 0;
        int lastQuantity = 0;
        int monsterQuantity = 0;
        int auxEncounter = 0;
        int option;
        int i;
        int totalMonsters = 0;
        int monsterOption;
        int monsterDeleteOption;
        boolean adventureSaved, exist;
        String adventureName = null;

        // Preguntamos por los datos de la adventure
        uiManager.showMessage("Tavern keeper: Planning an adventure? Good luck with that!\n");

        while(error == 0 ) {
            adventureName = uiManager.askForString("-> Name your adventure: ");
            exist = adventureManager.adventureNameDisponibility(adventureName, isUsingApi);
            if(exist){
                uiManager.showMessage("\nTavern keeper: Sorry lad this adventure name already exists\n");
            }else{
                error = 1;
            }
        }
        uiManager.showMessage("\nTavern keeper: You plan to undertake " + adventureName + " , really?\n" + "How long will that take?\n");

        error = 0;
        // Nos aseguramos de que se introduzcan correctamente el número de encuentros
        while(error == 0) {
            adventureEncounters = uiManager.askForInteger("-> How many encounters do you want [1..4]: ");
            if(adventureEncounters > 4 || adventureEncounters < 1){
                uiManager.showMessage("\nTavern keeper: That number of encounters is impossible to be truth, ya fooling around with me aren't ya");
                uiManager.showMessage("Number of encounters should be between 1 and 4\n");
            }
            else {
                error = 1;
            }
        }

        // Creamos listas de los monsters y seguimos con la creación
        uiManager.showMessage("\nTavern keeper: "+ adventureEncounters +" encounters? That is too much for me...");
        ArrayList<ArrayList<Monster>> encounterMonsters;
        ArrayList<String> monstersQuantityAndNames = new ArrayList<>(1);

        encounterMonsters = new ArrayList<>(adventureEncounters);

        encounterMonsters = adventureManager.initializeEncounters(encounterMonsters, adventureEncounters);

        // Abrimos bucle para gestionar todos los encuentros
        while(auxEncounter < adventureEncounters){
            do {
                uiManager.showMessage("\n\n* Encounter " + (auxEncounter + 1) + " / " + adventureEncounters + "");
                uiManager.showMessage("* Monsters in encounter");
                exist = false;

                // Abrimos if / else para mostrar los monsters que ya estén añadidos
                if(encounterMonsters.get(auxEncounter).get(0) != null){
                    for (i = 0; i < monstersQuantityAndNames.size();i++) {
                        String auxName = monstersQuantityAndNames.get(i);
                        String[] auxNameSplit = auxName.split("\\d+");
                        uiManager.showMessage("\t" + (i+1) + " " + auxNameSplit[0] + " (x" + auxName.replaceAll("[^0-9]", "") + ")");
                    }
                }else{
                    // En caso contrario, lo dejamos en Empty
                    uiManager.showMessage("  # Empty");
                }
                // Preguntamos qué quiere gestionar del encounter
                uiManager.showAdventureMenu();
                option = uiManager.askForInteger("\n-> Enter an option [1..3]: ");

                // En caso que el usuario trate de eliminar un monster y no haya añadido ninguno, mostramos error
                if(option == 2 && encounterMonsters.get(auxEncounter).get(0) == null){
                    uiManager.showMessage("\nTavern keeper: Sorry pal you can't erase monsters if your adventure don't have any, I'll let you add some first");
                    uiManager.showMessage("The tavern keeper shows you the add monster menu\n");
                    option = 1;
                }

                // Abrimos switch según la opción introducida
                switch (option) {

                    // Caso 1: Añadir monster
                    case 1 -> {
                        ArrayList<Monster> monsters;

                        //miramos desde que formato debemos leer
                        if(isUsingApi){
                            monsters = monsterManager.getAPIMonsters();
                        }else{
                            monsters = monsterManager.getAllMonsters();
                        }

                        i = 0;

                        // Mostramos lista con todos los monsters existentes
                        for (Monster monster : monsters) {
                            uiManager.showMessage((i + 1) + ". " + monster.getMonsterName() + " (" +monster.getMonsterChallenge()+ ")");
                            i++;
                            totalMonsters = i;
                        }

                        // Pedimos monster a añadir y nos aseguramos de que el número esté bien introducido
                        monsterOption = uiManager.askForInteger("-> Choose a monster to add [1.." + totalMonsters + "]: ");

                        if(monsterOption > monsters.size() || monsterOption < 1) {
                            while(monsterOption > monsters.size() || monsterOption < 1) {
                                uiManager.showMessage("Tavern keeper: Please choose an existing monster\n");
                                monsterOption = uiManager.askForInteger("-> Choose a monster to add [1.." + monsters.size() + "]: ");
                            }
                        }

                        lastQuantity = monsterQuantity + lastQuantity;
                        error = 1;
                        // Abrimos bucle para preguntar cuantos mismos monsters quiere añadir
                        while(error == 1) {
                            monsterQuantity = uiManager.askForInteger("-> How many " + monsters.get(monsterOption - 1).getMonsterName() + " do you want to add: ");



                            if(monsterQuantity < 1){
                                uiManager.showMessage("\nTavern keeper: Please add a correct number of monsters");
                                uiManager.showMessage("Quantity must be greater than 0\n");
                            }
                            else{
                                if(monsters.get(monsterOption - 1).getMonsterChallenge().equals("Boss") && monsterQuantity > 1){
                                    uiManager.showMessage("\nTavern keeper: More than 1 boss for encounter is too much pal, think about it again");
                                    uiManager.showMessage("You can only add one boss for encounter\n");
                                }else{
                                    error = 0;
                                }
                            }
                        }

                        lastQuantity = adventureManager.capacityEnsurance(auxEncounter, lastQuantity, monsterQuantity, encounterMonsters);
                        // Nos aseguramos de que no se introduzcan más de 2 monsters diferentes con categoría de Boss
                        if(encounterMonsters.get(auxEncounter) != null){
                            exist = adventureManager.checkMonsterTypeOfEncounter(encounterMonsters.get(auxEncounter), monsters, monsterOption);
                        }

                        if(exist){
                            lastQuantity = lastQuantity - monsterQuantity;
                            uiManager.showMessage("\nTavern keeper: You can't add more than 2 different type of boss in your encounter");
                        }else {
                            adventureManager.setMonstersEncounter(monsters, encounterMonsters, monsterOption, lastQuantity, monsterQuantity, auxEncounter);
                            adventureManager.setMonstersNames(monstersQuantityAndNames, monsters, monsterQuantity, monsterOption);
                        }
                    }

                    // Caso 2: Borrar monster
                    case 2 -> {
                        // Preguntamos por el monster a eliminar y nos aseguramos que esté bien introducido
                        monsterDeleteOption = uiManager.askForInteger("Which monster do you want to delete: ");
                        if(monsterDeleteOption > monstersQuantityAndNames.size() || monsterDeleteOption < 1) {
                            while(monsterDeleteOption > monstersQuantityAndNames.size() || monsterDeleteOption < 1) {
                                uiManager.showMessage("Tavern keeper: Please choose an existing monster\n");
                                monsterDeleteOption = uiManager.askForInteger("Which monster do you want to delete: ");
                            }
                        }

                        int removedCounter;
                        String monsterToBeErased = monstersQuantityAndNames.get(monsterDeleteOption - 1);

                        // Borramos el monster del encuentro y actualizamos la cantidad de monsters
                        removedCounter = adventureManager.removeMonsterFromEncounter(encounterMonsters, monstersQuantityAndNames, monsterDeleteOption, lastQuantity, monsterQuantity, auxEncounter);

                        lastQuantity = lastQuantity - removedCounter;

                        monsterToBeErased = monsterToBeErased.replaceAll("\\d","");

                        uiManager.showMessage(removedCounter + " " + monsterToBeErased  + " were removed from the encounter.");
                    }

                    // Caso 3: Crear encuentro nuevo (y cerramos switch)
                    case 3 -> {

                        // Nos aseguramos de que no se cree el encuentro sin ningún enemigo añadido
                        if(encounterMonsters.get(auxEncounter).get(0) == null){
                            uiManager.showMessage("\nYou can't create and encounter without monsters\n");
                        }else{
                            auxEncounter++;
                            monsterQuantity = 0;
                            lastQuantity = 0;
                            monstersQuantityAndNames = new ArrayList<>(1);
                        }
                    }
                    // Otros casos: Hacemos repetir la opción
                    default -> {
                        uiManager.showMessage("\nTavern keeper: I can't understand you, could you repeat it to me, please?");
                        uiManager.showMessage("\nValid options are between 1 to 3 (including them)");
                    }
                }
            }while(option != 3);
        }

        // Nos aseguramos que la adventure esté creada y se lo comentamos al usuario. Mirando siempre el formato de guardado escogido por el usuario
        if(isUsingApi) {
            adventureSaved = adventureManager.createAdventureAPI(adventureName, adventureEncounters, encounterMonsters);
        }else{
            adventureSaved = adventureManager.createAdventure(adventureName, adventureEncounters, encounterMonsters);
        }

        if(adventureSaved){
            uiManager.showMessage("\nTavern keeper: Your adventure is ready whenever you want to play it");
        }else{
            uiManager.showMessage("\nTavern keeper: I don't know an adventure like that could be carry on, make sure to do it correctly");
            uiManager.showMessage("\nSomething went wrong in the creation of your adventure");
        }
    }

    /**
     * Esta función representa la opción 4 para empezar una adventure.
     *
     * @param isUsingApi, bool para saber si se está usando la API
     * @throws IOException, por posible fallo de lectura
     */
    private void adventurePlay(boolean isUsingApi) throws IOException {

        int adventureSelection, characterQuantity, defeated = 0, counterEncounters, charactersDefeat = 0;
        int[] saveNumber = new int[5];
        ArrayList<Adventure> adventures;

        // Mostramos las aventuras disponibles
        uiManager.showMessage("""
                Tavern keeper: So, you are looking to go on an adventure?
                Where do you fancy going?
                """);
        uiManager.showMessage("Available adventures:\n");
        if(isUsingApi){
            //aventuras de API
            adventures = adventureManager.getAPIAdventuresList();
        }else{
            //listar aventuras en el JSON
            adventures = adventureManager.getAdventuresList();
        }
        for(int i = 0; i < adventures.size(); i++){
            uiManager.showMessage((i+1) + ". " + adventures.get(i).getAdventureName());
        }


        // Pedimos que seleccione una aventura y nos aseguramos que sea existente
        adventureSelection = uiManager.askForInteger("-> Choose an adventure: ");

        if((adventureSelection) > adventures.size() || (adventureSelection) < 1) {
            while ((adventureSelection) > adventures.size() || (adventureSelection) < 1) {
                uiManager.showMessage("Tavern keeper: Please choose an existing adventure\n");
                adventureSelection = uiManager.askForInteger("-> Choose an adventure: ");
            }
        }
        uiManager.showMessage("Tavern keeper: " + adventures.get(adventureSelection - 1).getAdventureName() + " it is!" + " \n And how many people shall join you?");

        // Pedimos la cantidad de personajes y nos aseguramos que esté bien introducido
        characterQuantity = uiManager.askForInteger("-> Choose a number of characters [3..5]: ");
        if((characterQuantity) > 5 || (characterQuantity) < 3) {
            while ((characterQuantity) > 5 || (characterQuantity) < 3)  {
                uiManager.showMessage("Tavern keeper: Please choose a correct number of characters\n");
                characterQuantity = uiManager.askForInteger("-> Choose a number of characters [3..5]: ");
            }
        }
        uiManager.showMessage("Tavern keeper: Great, " + characterQuantity + " it is.\n" + "Who among these lads shall join you?");

        // Creamos lista para que solo falte añadir los personajes
        int i, j = 0;
        ArrayList<Character> characterInParty = new ArrayList<>(characterQuantity);

        for(i = 0; i < characterQuantity; i++){
            characterInParty.add(i, null);
        }

        // Abrimos un bucle para mostrar los personajes que ya están añadidos
        do{
            i = 0;
            ArrayList<Character> characters;
            uiManager.showMessage("------------------------------");
            uiManager.showMessage("Your party (" + j + " / "+ characterQuantity +"):\n");

            // En caso de que no haya ningún introducido ponemos Empty
            while(i < characterQuantity){
                if(characterInParty.get(i) == null){
                    uiManager.showMessage((i+1) + ". Empty");
                }else{
                    uiManager.showMessage((i+1) + "." + characterInParty.get(i).getCharacterName());
                }
                i++;
            }

            // Mostramos con una lista todos los personajes disponibles para seleccionar
            uiManager.showMessage("------------------------------");
            uiManager.showMessage("Available characters:");
            i = 0;
            if(isUsingApi){
                characters = characterManager.getAPICharacters();
            }else{
                characters = characterManager.getAllCharacters();
            }

            for (Character character: characters) {
                uiManager.showMessage((i+1) + "." + character.getCharacterName());
                i++;
            }
            int CharacterPartySelected = uiManager.askForInteger("-> Choose character "+ (j+1) + " in your party: \n");


            // Nos aseguramos de que el personaje seleccionado exista
            if (CharacterPartySelected < 1 || CharacterPartySelected > characters.size()) {
                while (CharacterPartySelected < 1 || CharacterPartySelected > characters.size()) {
                    uiManager.showMessage("Tavern keeper: Please choose an existing character\n");
                    CharacterPartySelected = uiManager.askForInteger("-> Choose character "+ (j+1) + " in your party: \n");
                }
            }

            // Nos aseguramos con otro bucle que el personaje seleccionado no haya sido seleccionado anteriormente
            int w;
            for (w=0; w<saveNumber.length; w++) {
                if (saveNumber[w] == CharacterPartySelected) {
                    uiManager.showMessage("Tavern keeper: You've already chosen this character!\n");
                    CharacterPartySelected = uiManager.askForInteger("-> Choose character "+ (j+1) + " in your party: \n");
                    while (CharacterPartySelected < 1 || CharacterPartySelected > characters.size() || saveNumber[w] == CharacterPartySelected) {
                        uiManager.showMessage("Tavern keeper: Come on! Just choose an existing new character.\n");
                        CharacterPartySelected = uiManager.askForInteger("-> Choose character "+ (j+1) + " in your party: \n");
                    }
                }
            }

            saveNumber[j] = CharacterPartySelected;
            String characterClass = characters.get(CharacterPartySelected - 1).getCharacterClass();
            Character auxCharacter = null;
            switch (characterClass) {
                case "Adventurer" -> auxCharacter = new Adventurer(characters.get(CharacterPartySelected - 1)) ;
                case "Warrior" -> auxCharacter = new Warrior(characters.get(CharacterPartySelected - 1));
                case "Champion" -> auxCharacter = new Champion(characters.get(CharacterPartySelected - 1));
                case "Cleric" -> auxCharacter = new Cleric(characters.get(CharacterPartySelected - 1));
                case "Paladin" -> auxCharacter = new Paladin(characters.get(CharacterPartySelected - 1));
                case "Mage" -> auxCharacter = new Mage(characters.get(CharacterPartySelected - 1), 0);
            }
            characterInParty.set(j, auxCharacter);
            j++;
        }while(j < characterQuantity);

        // Al acabar volvemos a mostrar la lista actualizada
        i = 0;
        while(i < characterQuantity){
            if(characterInParty.get(i) == null){
                uiManager.showMessage((i+1) + ". Empty");
            }else{
                uiManager.showMessage((i+1) + "." + characterInParty.get(i).getCharacterName());
            }
            i++;
        }

        // Enviamos mensaje de confirmación y empezamos la aventura
        uiManager.showMessage("\nTavern keeper: Great, good luck on your adventure lads!\n");
        uiManager.showMessage("The " + adventures.get(adventureSelection - 1).getAdventureName()  +" will start soon...\n");
        counterEncounters = 0;
        int adventureEncounters = adventures.get(adventureSelection - 1).getEncounters();

        adventureManager.setAdventurersLifeList(characterInParty);


        // FASES DE COMBATE

        // Abrimos bucle para empezar el combate
        do{
            // Preparamos variables y listas
            ArrayList<Mage> magesInBattle = new ArrayList<>(0);

            // Mostramos el encuentro en el que nos encontramos
            uiManager.showMessage("---------------------");
            uiManager.showMessage("Starting Encounter "+ (counterEncounters + 1) +":");

            ArrayList<Monster> monstersInEncounter = adventures.get(adventureSelection - 1).getAdventureEncounterMonsters().get(counterEncounters);
            ArrayList<String> storedName = new ArrayList<>(0);

            // Mostramos los monsters que se van a encontrar con un bucle y con las funciones correspondientes
            adventureManager.countSameMonstersInEncounter(storedName, monstersInEncounter);
            i = 0;
            long count;
            while(i < storedName.size()){
                int finalI1 = i;
                count = monstersInEncounter.stream().filter(m -> m.getMonsterName().equals(storedName.get(finalI1))).count();
                uiManager.showMessage("\t- "+ count + "x " + storedName.get(i));
                i++;
            }
            int z;
            adventureManager.setMagesForAdventure(characterInParty, magesInBattle);
            uiManager.showMessage("---------------------");

            // FASE DE PREPARACIÓN
            uiManager.showPrepStage();
            // Obtenemos los monsters del encuentro y el número de este

            i = 0;
            while(i < characterQuantity){
                int roll = characterInParty.get(i).diceRollD3();
                adventureManager.applyAbilitiesPrepPhase(characterInParty.get(i), characterInParty, magesInBattle, roll);
                // Abrimos switch para comprobar que clase posee el personaje para así realizar su habilidad correspondiente
                int shield = -1;
                boolean isMage = adventureManager.isMage(characterInParty, i);
                if(isMage){
                    int index = adventureManager.getMageIndex(magesInBattle, characterInParty.get(i).getCharacterName());
                    shield = magesInBattle.get(index).getShield();
                }
                uiManager.showAbilitiesPrepPhase(characterInParty.get(i).getCharacterClass(), characterInParty.get(i).getCharacterName(), shield, roll);
                i++;
            }

            // Preparamos la lista de prioridades según la iniciativa
            uiManager.showMessage("Rolling initiative...\n");
            int monsterQuantity = monstersInEncounter.size();
            int roundCounter = 0;

            // Creamos la lista en cuestión
            ArrayList<String> listOfPriorities = adventureManager.listOfPriorities(characterQuantity, monsterQuantity, characterInParty, monstersInEncounter );

            // La ordenamos según el número de iniciativa
            adventureManager.orderListOfPriorities(listOfPriorities);

            int xpSum = adventureManager.sumAllMonsterXp(monstersInEncounter);

            // Abrimos bucle para mostrar en orden la lista de quién atacará primero
            i = 0;
            while(i < listOfPriorities.size()){
                String[] auxCompareName = listOfPriorities.get(i).split("\\d+");
                String compareName = auxCompareName[0];
                int compareInitiative = Integer.parseInt(listOfPriorities.get(i).replaceAll("[^0-9]", ""));
                if(compareName.endsWith("-")){
                    auxCompareName = compareName.split("-");
                    compareName = auxCompareName[0];
                    compareInitiative = -compareInitiative;
                }
                uiManager.showMessage("\t- " + compareInitiative + "   " + compareName);
                i++;
            }

            // FASE DE COMBATE
            uiManager.showCombatStage();

            // Preparamos todos los stats de los monsters
            monsterManager.setInitialMonsterLife(monstersInEncounter);
            int aliveMonsters = adventureManager.countAliveMonsters(monstersInEncounter);

            // Abrimos nuevo bucle para realizar el combate
            do{
                // Preparamos variables

                int q = 0, reducedDmg = 0, isCrit, totalLife, actualLife, highestMonsterIndex, smallestMonsterIndex, smallestCharacterIndex, shield, total, damage, multihit = 0, heal = 0;
                boolean isMage, isBoss = false, fail, needAHeal;
                String[] auxName;
                String actualName, attackedMonster, compareName, typeOfDamage;
                ArrayList<String> consciousPosition = new ArrayList<>(0);
                // Mostramos ronda
                uiManager.showMessage("Round "+  (roundCounter + 1) + ":");
                uiManager.showMessage("Party :");

                // Mostramos todos los personajes del grupo y sus hit points (vida)
                z = 0;
                while(z < characterInParty.size()) {
                    actualName = characterInParty.get(z).getCharacterName();

                    //comprobación de que nuestro personaje sea un mago
                    //en caso de serlo lo guardamos en un array para almacenar su cantidad de escudo
                    int mageIndex = adventureManager.getMageIndex(magesInBattle, actualName);
                    actualLife = characterInParty.get(z).getActualLife();
                    totalLife = characterInParty.get(z).getTotalLife();

                    // Después de preparar todos los formatos (con ints) mostramos los personajes
                    // En caso de que este muerto, dejamos un poco más de espacio para remarcarlo (un \t)
                    // En el caso de que nuestro personaje sea un mago su cantidad de escudo se verá remarcada
                    if(mageIndex == -1){
                        shield = -1;
                    }else{
                        shield = magesInBattle.get(mageIndex).getShield();
                    }
                    uiManager.showLifeInBattle(actualLife, actualName, totalLife, shield, z);
                    z++;
                }


                // Iniciamos bucle para la batalla
                while(q < listOfPriorities.size()){
                    //comprobamos si hay o no critico. (2 (crítico), 1 (golpe normal), 0 (fallo))
                    isCrit = characterManager.diceRollD10();
                    //indice del enemigo con más vida
                    highestMonsterIndex = adventureManager.highestEnemyLife(monstersInEncounter);
                    //indice del enemigo con menos vida
                    smallestMonsterIndex = adventureManager.smallestEnemyLife(monstersInEncounter);
                    //indice del personaje con menos vida
                    smallestCharacterIndex = adventureManager.smallestCharacterLife(characterInParty);
                    //contador de jugadores muertos
                    charactersDefeat = adventureManager.countDeadCharacters(characterInParty);
                    auxName = listOfPriorities.get(q).split("\\d+");
                    actualName = auxName[0];
                    if(actualName.contains("-")){
                        actualName = actualName.substring(0, actualName.length()-1);
                    }
                    damage = 0;

                    aliveMonsters = adventureManager.countAliveMonsters(monstersInEncounter);



                    //comprobamos que alguno de los bandos siga en pie (personajes vivos >= 1 && monstruos en batalla > 0)
                    if(charactersDefeat < characterInParty.size() && aliveMonsters > 0) {
                        z = 0;
                        //abrimos bucle donde revisaremos toda nuestra party
                        //si el nombre de la lista de prioridades coincide con alguno entraremos
                        //aquí mostraremos quien está atacando y que habilidad está usando
                        //las clases con habilidades de combate iguales han sido englobadas en un solo caso

                        while (z < characterInParty.size()) {
                            if (actualName.equals(characterInParty.get(z).getCharacterName())) {
                                actualLife = characterInParty.get(z).getActualLife();

                                if(actualLife != 0) {
                                    attackedMonster = monstersInEncounter.get(smallestMonsterIndex).getMonsterName();
                                    isMage = adventureManager.isMage(characterInParty,z);
                                    if(isMage){
                                        if(aliveMonsters >= 3){
                                            multihit = 1;
                                        }else{
                                            multihit = 0;
                                        }
                                    }
                                    if(characterInParty.get(z).getCharacterClass().equals("Paladin") || characterInParty.get(z).getCharacterClass().equals("Cleric") ){
                                        needAHeal = adventureManager.needAHeal(characterInParty.get(smallestCharacterIndex));
                                    }else{
                                        needAHeal = false;
                                    }

                                    if(!needAHeal){
                                        damage = adventureManager.calculateDamage(characterInParty.get(z), aliveMonsters);
                                        uiManager.messageAttack(actualName, attackedMonster, characterInParty.get(z).getCharacterClass(),heal,multihit,"empty");
                                    }else{
                                        heal = adventureManager.healingInCombat(characterInParty.get(smallestCharacterIndex), characterInParty.get(z), characterInParty);
                                        uiManager.messageAttack(actualName, attackedMonster, characterInParty.get(z).getCharacterClass(),heal,multihit, characterInParty.get(smallestCharacterIndex).getCharacterName());
                                    }


                                }
                                z = characterInParty.size();
                            }
                            z++;
                        }
                    }

                    //comprobamos que alguno de los bandos siga en pie (personajes vivos >= 1 && monstruos en batalla > 0)
                    if(charactersDefeat < characterInParty.size() && aliveMonsters > 0){
                        z = 0;

                        //abrimos bucle donde revisaremos toda nuestros monstruos del encuentro
                        //si el nombre de la lista de prioridades coincide con alguno entraremos
                        //aquí mostraremos quien está atacando
                        //en este caso los jefes se representarán aparte, ya que estos pegan en área
                        while(z < monstersInEncounter.size()){
                            compareName = monstersInEncounter.get(z).getMonsterName();
                            if(actualName.equals(compareName)){
                                actualLife = monstersInEncounter.get(z).getActualHitPoints();
                                String actualDice = monsterManager.diceNumber(monstersInEncounter.get(z).getMonsterDice());
                                if(actualLife != 0) {
                                    damage = monsterManager.monsterDamageCalculator(actualDice);
                                    if(monstersInEncounter.get(z).getMonsterChallenge().equals("Boss")){
                                        isBoss = true;
                                        adventureManager.setConsciousPosition(consciousPosition, characterInParty);
                                        uiManager.bossAttackMessage(consciousPosition, actualName);
                                    }else{
                                        isBoss = false;
                                        uiManager.showMessage("\n" + actualName + " attacks " + characterInParty.get(smallestCharacterIndex).getCharacterName());
                                    }
                                }
                                z = monstersInEncounter.size();
                            }
                            z++;
                        }
                    }

                    //hacemos reinicio de variables auxiliares
                    z = 0;
                    j = 0;
                    i = 0;

                    //comprobamos que alguno de los bandos siga en pie (personajes vivos >= 1 && monstruos en batalla > 0)
                    if(aliveMonsters > 0 && charactersDefeat < characterInParty.size() ){
                        //recorriendo toda la lista de prioridades buscamos a que bando pertenece el personaje que pega
                        while(z < listOfPriorities.size()){
                            while (j < monstersInEncounter.size()) {
                                compareName = monstersInEncounter.get(j).getMonsterName();
                                //si el personaje que pega es un monstruo entraremos aquí
                                if (actualName.equals(compareName)) {
                                    //miramos el tipo de daño que hace el enemigo en cuestión
                                    typeOfDamage = monstersInEncounter.get(j).getDamageType();

                                    fail = adventureManager.failedAttack(isCrit);


                                    if(isBoss){
                                        //si el ataque no ha fallado procedemos a restar las vidas
                                        if(!fail){
                                            for(int b = 0; b < characterInParty.size(); b++){
                                                actualLife = characterInParty.get(b).getActualLife();
                                                //comprobamos que el personaje atacado no este muerto
                                                if(actualLife != 0){
                                                    isMage = adventureManager.isMage(characterInParty, b);

                                                    reducedDmg = adventureManager.damageReduction(damage, characterInParty.get(b), typeOfDamage);
                                                    if(isMage){
                                                        total = adventureManager.shieldDealer(characterInParty.get(b), magesInBattle, isCrit, reducedDmg);
                                                    }else{
                                                        total = adventureManager.applyDamage(isCrit,actualLife, reducedDmg);
                                                    }

                                                    if(damage != 0) {
                                                        uiManager.hitMessage(reducedDmg, typeOfDamage, isCrit);
                                                    }
                                                    characterInParty.get(b).setActualLife(total);
                                                    if (total == 0) {
                                                        uiManager.deadMessage(characterInParty.get(b).getCharacterName());
                                                    }
                                                }
                                            }
                                        }


                                    }else {

                                        //aplicamos reducciones de daño dependiendo del tipo del mismo

                                        reducedDmg = adventureManager.damageReduction(damage, characterInParty.get(smallestCharacterIndex), typeOfDamage);

                                        isMage = adventureManager.isMage(characterInParty, smallestCharacterIndex);
                                        if(isMage){
                                            total = adventureManager.shieldDealer(characterInParty.get(smallestCharacterIndex), magesInBattle, isCrit, reducedDmg);
                                        }else{
                                            total = adventureManager.applyDamage(isCrit, characterInParty.get(smallestCharacterIndex).getActualLife(), reducedDmg);
                                        }
                                        if(damage != 0) {
                                            uiManager.hitMessage(reducedDmg, typeOfDamage, isCrit);
                                        }
                                        //si el ataque no ha fallado procedemos a restar las vidas
                                        if(!fail){
                                            characterInParty.get(smallestCharacterIndex).setActualLife(total);
                                            //comprobamos si alguno de los personajes que ha recibido el golpe muere
                                            if (total == 0) {
                                                uiManager.deadMessage(characterInParty.get(smallestCharacterIndex).getCharacterName());
                                            }
                                        }

                                    }
                                    z = listOfPriorities.size();
                                    j = monstersInEncounter.size();

                                }
                                j++;
                            }


                            while(i < characterInParty.size()) {

                                //si el personaje que pega es un jugador entraremos aquí
                                if(actualName.equals(characterInParty.get(i).getCharacterName())) {

                                    actualLife = characterInParty.get(i).getActualLife();

                                    //miramos el tipo de daño infligido dependiendo de la clase del personaje
                                    typeOfDamage = characterManager.characterTypeOfDamage(characterInParty.get(i).getCharacterClass());

                                    //si el personaje que ataca esta consciente entraremos
                                    if(actualLife > 0) {
                                        //si el personaje atacante es mago y además a más de dos enemigos
                                        if(characterInParty.get(i).getCharacterClass().equals("Mage") && aliveMonsters >= 3){
                                            //ataque en área afecta a todos los monstruos vivos
                                            for(int c = 0; c < aliveMonsters; c++){
                                                actualLife = monstersInEncounter.get(c).getActualHitPoints();
                                                attackedMonster = monstersInEncounter.get(c).getMonsterName();

                                                reducedDmg = adventureManager.monsterDamageReduction(damage, monstersInEncounter.get(c), typeOfDamage);

                                                total = adventureManager.applyDamage(isCrit, actualLife, reducedDmg);
                                                fail = adventureManager.failedAttack(isCrit);
                                                uiManager.hitMessage(reducedDmg, typeOfDamage, isCrit);

                                                //si no hay fallo aplicamos los daños y notificamos las muertes (en caso de haberlas)
                                                if(!fail){
                                                    if (total == 0) {
                                                        uiManager.showMessage(attackedMonster + " dies.");
                                                        i = 0;
                                                        while(i < listOfPriorities.size()){
                                                            auxName = listOfPriorities.get(i).split("\\d+");
                                                            String enemyToErase = auxName[0];
                                                            if(enemyToErase.contains("-")){
                                                                enemyToErase = enemyToErase.substring(0, enemyToErase.length()-1);
                                                            }
                                                            if(enemyToErase.equals(monstersInEncounter.get(c).getMonsterName())){
                                                                listOfPriorities.remove(i);
                                                                if(i < q){
                                                                    q--;
                                                                }
                                                                if(q<0){
                                                                    q = 0;
                                                                }
                                                                i = listOfPriorities.size();
                                                            }
                                                            i++;
                                                        }
                                                        monstersInEncounter.remove(c);
                                                        aliveMonsters--;
                                                        c--;
                                                        if(c < 0){
                                                            c = 0;
                                                        }

                                                    }
                                                    monstersInEncounter.get(c).setActualHitPoints(total);
                                                }
                                            }

                                            //en caso de que el personaje que ataca sea un mago, pero haya menos de 3 enemigos
                                        }else if(characterInParty.get(i).getCharacterClass().equals("Mage") && aliveMonsters < 3){
                                            actualLife = monstersInEncounter.get(highestMonsterIndex).getActualHitPoints();
                                            //atacaremos al monstruo con más vida el encuentro
                                            attackedMonster = monstersInEncounter.get(highestMonsterIndex).getMonsterName();

                                            reducedDmg = adventureManager.monsterDamageReduction(damage, monstersInEncounter.get(highestMonsterIndex), typeOfDamage);

                                            total = adventureManager.applyDamage(isCrit, actualLife, reducedDmg);


                                            uiManager.hitMessage(reducedDmg, typeOfDamage, isCrit);
                                            fail = adventureManager.failedAttack(isCrit);


                                            //si no hay fallo aplicamos el daño y notificamos la muerte (en caso de haberla)
                                            if(!fail){
                                                monstersInEncounter.get(highestMonsterIndex).setActualHitPoints(total);
                                                if (total == 0) {

                                                    uiManager.showMessage(attackedMonster + " dies.");
                                                    i = 0;
                                                    while(i < listOfPriorities.size()){
                                                        auxName = listOfPriorities.get(i).split("\\d+");
                                                        String enemyToErase = auxName[0];
                                                        if(enemyToErase.contains("-")){
                                                            enemyToErase = enemyToErase.substring(0, enemyToErase.length()-1);
                                                        }
                                                        if(enemyToErase.equals(monstersInEncounter.get(highestMonsterIndex).getMonsterName())){
                                                            listOfPriorities.remove(i);
                                                            if(i < q){
                                                                q--;
                                                            }
                                                            if(q < 0){
                                                                q = 0;
                                                            }
                                                            i = listOfPriorities.size();
                                                        }
                                                        i++;
                                                    }
                                                    monstersInEncounter.remove(highestMonsterIndex);
                                                    aliveMonsters--;



                                                }

                                            }
                                        }else{
                                            //comprobación orientada a cuando es turno de un clérigo o paladin que cura
                                            //en caso de no ser ese caso entrara a comprobar y aplicar daños
                                            if(damage != 0) {
                                                actualLife = monstersInEncounter.get(smallestMonsterIndex).getActualHitPoints();

                                                //cogemos el monstruo con menos vida
                                                attackedMonster = monstersInEncounter.get(smallestMonsterIndex).getMonsterName();
                                                reducedDmg = adventureManager.monsterDamageReduction(damage, monstersInEncounter.get(smallestMonsterIndex), typeOfDamage);

                                                total = adventureManager.applyDamage(isCrit, actualLife, reducedDmg);

                                                uiManager.hitMessage(reducedDmg, typeOfDamage, isCrit);
                                                fail = adventureManager.failedAttack(isCrit);

                                                //si no hay fallo aplicamos el daño y notificamos la muerte (en caso de haberla)
                                                if(!fail){
                                                    monstersInEncounter.get(smallestMonsterIndex).setActualHitPoints(total);
                                                    if (total == 0) {
                                                        uiManager.showMessage(attackedMonster + " dies.");
                                                        i = 0;
                                                        while(i < listOfPriorities.size()){
                                                            auxName = listOfPriorities.get(i).split("\\d+");
                                                            String enemyToErase = auxName[0];
                                                            if(enemyToErase.contains("-")){
                                                                enemyToErase = enemyToErase.substring(0, enemyToErase.length()-1);
                                                            }
                                                            if(enemyToErase.equals(monstersInEncounter.get(smallestMonsterIndex).getMonsterName())){
                                                                listOfPriorities.remove(i);
                                                                if(i < q){
                                                                    q--;
                                                                }
                                                                if(q<0){
                                                                    q = 0;
                                                                }
                                                                i = listOfPriorities.size();
                                                            }
                                                            i++;
                                                        }
                                                        monstersInEncounter.remove(smallestMonsterIndex);
                                                        aliveMonsters--;


                                                    }
                                                }
                                            }
                                        }
                                        z = listOfPriorities.size();
                                        i = characterInParty.size();
                                    }
                                }
                                i++;
                            }
                            z++;
                        }
                    }
                    //salida forzada del bucle cuando uno de los bandos ha muerto
                    q++;
                }


                //notificación de fin de ronda + auxiliar de derrota en caso de perder la partida
                if(charactersDefeat == characterInParty.size()){
                    defeated = 1;
                }else{
                    uiManager.showMessage("\nEnd of round "+ (roundCounter + 1) +".\n");
                    roundCounter++;
                }

            }while((aliveMonsters > 0 && charactersDefeat < characterInParty.size()));

            //si la auxiliar de derrota no se activa entraremos
            if(defeated == 0){

                //FASE DE DESCANSO
                uiManager.showRestStage();


                boolean levelUp;
                //Cogemos toda la XP del encuentro y la repartimos entre todos los miembros de la party por igual. Todos recibirán toda la cantidad
                i = 0;
                boolean evolved;

                //bucle que mostrara la cantidad de XP ganada por el PJ + la posible subida de nivel del mismo
                while(i < characterQuantity){

                    int auxTotalLife = characterInParty.get(i).getTotalLife();
                    int auxActualLife = characterInParty.get(i).getActualLife();

                    levelUp = characterManager.levelUpCheck(xpSum, characterInParty.get(i).getCharacterLevel());

                    if(isUsingApi){
                        characterManager.levelUpdateAPI(characterInParty.get(i), xpSum);
                    }else{
                        characterManager.levelUpdate(characterInParty.get(i), xpSum);
                    }
                    if(levelUp){
                        characterInParty.set(i,characterManager.getCharacterByName(characterInParty.get(i).getCharacterName(),isUsingApi));
                        uiManager.showMessage(characterInParty.get(i).getCharacterName() + " gains " + xpSum + " xp." + characterInParty.get(i).getCharacterName() + " levels up. They are now lvl " + characterManager.revertXpToLevel(characterInParty.get(i).getCharacterLevel()) + "!");

                        evolved = characterManager.evolution(characterInParty.get(i), isUsingApi);

                        if(evolved){
                            uiManager.showMessage(characterInParty.get(i).getCharacterName() + " evolves to " + characterInParty.get(i).getCharacterClass());
                        }
                    }else{
                        uiManager.showMessage(characterInParty.get(i).getCharacterName() + " gains " + xpSum + " xp.");
                    }
                    characterInParty.set(i,characterManager.getCharacterByName(characterInParty.get(i).getCharacterName(),isUsingApi));
                    characterInParty.get(i).setTotalLife(auxTotalLife);
                    characterInParty.get(i).setActualLife(auxActualLife);

                    i++;

                }

                i = 0;
                //Recorremos toda la party para usar las posibles habilidades disponibles que tengamos en el momento
                while(i < characterQuantity){
                    int temporalLife = characterInParty.get(i).getActualLife();
                    int smallIndex = adventureManager.smallestCharacterLife(characterInParty);
                    int characterCuration = adventureManager.applyAbilitiesRestPhase(characterInParty.get(i), characterInParty, smallIndex);

                    uiManager.showAbilitiesRestPhase(characterInParty.get(i).getCharacterClass(), characterInParty.get(i).getCharacterName(), characterCuration, temporalLife);
                    adventureManager.applyAbilitiesRestPhase(characterInParty.get(i),characterInParty, smallIndex);

                    i++;
                }
            }else{
                counterEncounters = 6;
            }
            //después de descansar y usar sus habilidades los personajes empezarán el siguiente encuentro hasta que termine la aventura
            counterEncounters++;
        }while(counterEncounters < adventureEncounters);

        //en caso de perder la partida veremos este mensaje y volveremos a la taberna
        if(defeated != 0) {
            uiManager.unconsciousMessage();
        }
        //en caso de ganar la partida veremos este mensaje y volveremos a la taberna
        else{
            uiManager.showMessage("Congratulations, your party completed "+ adventures.get(adventureSelection - 1).getAdventureName() +"\n");
        }

    }
}
