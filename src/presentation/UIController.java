package presentation;

import business.*;
import business.entities.*;
import business.entities.Character;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class UIController {
    private final UIManager uiManager;
    private final AdventureManager adventureManager;
    private final CharacterManager characterManager;
    private final MonsterManager monsterManager;

    public UIController(UIManager uiManager, AdventureManager adventureManager, CharacterManager characterManager, MonsterManager monsterManager) {
        this.uiManager = uiManager;
        this.adventureManager = adventureManager;
        this.characterManager = characterManager;
        this.monsterManager = monsterManager;
    }


    public void run() throws IOException {
        int option;
        boolean isUsingApi = false;
        int i = 0;
        int totalCharacters = 0;
        boolean serverConnect = true;

        uiManager.showMessage("Welcome to Simple LSRPG.\n");
        uiManager.showDataMenu();
        option = uiManager.askForInteger("\nYour answer: ");
        uiManager.showMessage("\nLoading data...");
        if(option == 1){
            isUsingApi = false;
            ArrayList<Monster> monsters = monsterManager.getAllMonsters();
            if(monsters.size() > 0){
                uiManager.showMessage("Data was successfully loaded.\n\n\n");
                do {
                    ArrayList<Character> characters = characterManager.getAllCharacters();
                    for (Character character: characters) {
                        i++;
                        totalCharacters = i;
                    }
                    i = 0;
                    ArrayList<Adventure> adventures = adventureManager.getAdventuresList();
                    if(totalCharacters < 3){
                        uiManager.showMainMenuDissabled();
                        option = uiManager.askForInteger("\nYour answer: ");
                        if(option != 4){
                            executeOption(option, isUsingApi);
                        }else{
                            uiManager.showMessage("\nTavern keeper: “You need to gather a minimum of 3 characters to play an adventure.”\n");
                        }
                    }else if(adventures == null){
                        uiManager.showMainMenu();
                        option = uiManager.askForInteger("\nYour answer: ");
                        if(option != 4){
                            executeOption(option, isUsingApi);
                        }else{
                            uiManager.showMessage("\nYou need to create an adventure before playing one.\n");
                        }
                    }else{
                        uiManager.showMainMenu();
                        option = uiManager.askForInteger("\nYour answer: ");
                        executeOption(option, isUsingApi);
                    }
                } while(option != 5);
            }else{
                uiManager.showMessage("Error: The monsters.json file can’t be accessed.\n");
            }
        }else if(option == 2){

            ArrayList<Monster> getMonstersFromApi = monsterManager.getAPIMonsters();
            isUsingApi = true;

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
                            executeOption(option, isUsingApi);
                        }else{
                            uiManager.showMessage("\nTavern keeper: “You need to gather a minimum of 3 characters to play an adventure.”\n");
                        }
                    }else if(adventures == null){
                        uiManager.showMainMenu();
                        option = uiManager.askForInteger("\nYour answer: ");
                        if(option != 4){
                            executeOption(option, isUsingApi);
                        }else{
                            uiManager.showMessage("\nYou need to create an adventure before playing one.\n");
                        }
                    }else{
                        uiManager.showMainMenu();
                        option = uiManager.askForInteger("\nYour answer: ");
                        executeOption(option, isUsingApi);
                    }
                } while(option != 5);


            }else{
                uiManager.showMessage("Couldn’t connect to the remote server.\n");
                uiManager.showMessage("Reverting to local data.");
                isUsingApi = false;

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
                                executeOption(option, isUsingApi);
                            }else{
                                uiManager.showMessage("\nTavern keeper: “You need to gather a minimum of 3 characters to play an adventure.”\n");
                            }
                        }else if(adventures == null){
                            uiManager.showMainMenu();
                            option = uiManager.askForInteger("\nYour answer: ");
                            if(option != 4){
                                executeOption(option, isUsingApi);
                            }else{
                                uiManager.showMessage("\nYou need to create an adventure before playing one.\n");
                            }
                        }else{
                            uiManager.showMainMenu();
                            option = uiManager.askForInteger("\nYour answer: ");
                            executeOption(option, isUsingApi);
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


    private void executeOption(int option, boolean isUsingApi) throws IOException {
        uiManager.showMessage("");
        switch (option) {
            case 1:
                characterCreation(isUsingApi);
                break;
            case 2:
                listCharacters(isUsingApi);
                break;
            case 3:
                adventureCreation(isUsingApi);
                break;
            case 4:
                adventurePlay(isUsingApi);
                break;
            case 5:
                uiManager.showMessage("\nTavern keeper: “Are you leaving already? See you soon, adventurer.”\n");
                break;
            default:
                uiManager.showMessage("\nTavern keeper: “I can't understand you, could you repeat it to me, please?”");
                uiManager.showMessage("\nValid options are between 1 to 5 (including them)”");
                break;
        }
    }

    private void characterCreation(boolean isUsingApi) throws IOException {
        int error = 0;
        String characterName = "NoCharacterName";
        String playerName = "NoPlayerName";
        int characterLevel = 0;
        boolean saved = false;
        boolean exist = false;
        uiManager.showMessage("""
                Tavern keeper: “Oh, so you are new to this land.”
                “What’s your name?”
                """);
        while(error == 0 ) {
            characterName = uiManager.askForString("-> Enter your name: ");

            boolean correct = characterManager.nameCheck(characterName);
            characterName = characterManager.fixName(characterName);
            if(!correct){
                uiManager.showMessage("\nTavern keeper: “C'mon don't fool around and tell me your real name, will ya?”");
                uiManager.showMessage("Character name can't include numbers or special characters\n");
            }else{
                exist = characterManager.characterNameDisponibility(characterName, isUsingApi);
                if(exist){
                    uiManager.showMessage("\nTavern keeper: “Sorry lad this character name already exists”\n");
                }else{
                    error = 1;
                }
            }
        }


        uiManager.showMessage("\nTavern keeper: “Hello, " +  characterName + ", be welcome.”\n" + "“And now, if I may break the fourth wall, who is your Player?”\n");

        playerName = uiManager.askForString("-> Enter the player’s name: ");

        uiManager.showMessage("""

                Tavern keeper: “I see, I see...”
                “Now, are you an experienced adventurer?”
                """);

        error = 0;
        while(error == 0) {
            characterLevel = uiManager.askForInteger("-> Enter the character’s level [1..10]: ");
            if(characterLevel > 10 || characterLevel < 1){
                uiManager.showMessage("\nTavern keeper: “I don't think you could be at that level, c'mon tell me the truth”");
                uiManager.showMessage("Character level can't be lower than 1 or higher than 10\n");
            }
            else {
                error = 1;
            }
        }

        int experience = characterManager.experienceCalculator(characterLevel);

        uiManager.showMessage("\nTavern keeper: “Oh, so you are level "+ characterLevel + "!”\n" + "“Great, let me get a closer look at you...”\n");
        uiManager.showMessage("Generating your stats...\n");

        //mind, body, spirit

        int[] body = characterManager.diceRoll2D6();
        int[] mind = characterManager.diceRoll2D6();
        int[] spirit = characterManager.diceRoll2D6();

        uiManager.showMessage("Body: You rolled " + (body[0] + body[1]) + " (" + body[0] + " and "+ body[1]+ ").");
        uiManager.showMessage("Mind: You rolled " + (mind[0] + mind[1]) + " (" + mind[0] + " and " + mind[1] + ").");
        uiManager.showMessage("Spirit: You rolled " + (spirit[0] + spirit[1]) + " (" + spirit[0] + " and "+ spirit[1] + ").");

        uiManager.showMessage("\nYour stats are:");
        String stat1 = characterManager.statCalculator(body);
        uiManager.showMessage("\t- Body: "+ stat1);
        String stat2 = characterManager.statCalculator(mind);
        uiManager.showMessage("\t- Mind: "+ stat2);
        String stat3 = characterManager.statCalculator(spirit);
        uiManager.showMessage("\t- Spirit: "+ stat3);

        int bodySum = Integer.parseInt(stat1);
        int mindSum = Integer.parseInt(stat2);
        int spiritSum = Integer.parseInt(stat3);

        uiManager.showMessage("\nTavern keeper: “Looking good!”");
        uiManager.showMessage("“And, lastly, ?”\n");

        String characterClass = " ";

        do{
            characterClass = uiManager.askForString("-> Enter the character’s initial class [Adventurer, Cleric, Mage]: ");

            if(!Objects.equals(characterClass, "Cleric") && !Objects.equals(characterClass, "Adventurer") && !Objects.equals(characterClass, "Mage")){
                uiManager.showMessage("\nTavern keeper: “I'm sorry but that class is unknown to me...”");
                uiManager.showMessage("Character can only be the type of class announced in brackets (Adventurer, Cleric or Mage)”\n");
            }

        }while(!Objects.equals(characterClass, "Cleric") && !Objects.equals(characterClass, "Adventurer") && !Objects.equals(characterClass, "Mage"));

        characterClass = characterManager.initialEvolution(characterClass, characterLevel);

        if(!isUsingApi){
            saved = characterManager.createCharacter(characterName, playerName, experience, bodySum, mindSum, spiritSum, characterClass);
        }else{
            saved = characterManager.createCharacterAPI(characterName, playerName, experience, bodySum, mindSum, spiritSum, characterClass);
        }

        if (saved){
            uiManager.showMessage("\nThe new character " + characterName + " has been created.\n");
        }else{
            uiManager.showMessage("\nTavern keeper: “Im sorry friend but " + characterName +  " couldn't be found on the guild. Try it again next time.");
            uiManager.showMessage("There is an error in the save of your character.");
        }

    }


    private void listCharacters(boolean isUsingApi) throws IOException {
        int i = 0;

        uiManager.showMessage("Tavern keeper: “Lads! The Boss wants to see you, come here!”\n" + "“Who piques your interest?”");
        String playerName = uiManager.askForString("-> Enter the name of the Player to filter: ");

        ArrayList<Character> character = characterManager.filteredPlayers(playerName, isUsingApi);
        if(character.size() != 0){
            if(character.get(0) != null){
                uiManager.showMessage("You watch as some adventurers get up from their chairs and approach you.\n");
                while(i < character.size() ){
                    uiManager.showMessage("\t" + (i+1) +"." + character.get(i).getCharacterName());
                    i++;
                }
                uiManager.showMessage("\t\n0. Back");

                int characterPicked = uiManager.askForInteger("Who would you like to meet [0.." + character.size() + "]: ");
                if((characterPicked) > character.size() || (characterPicked) < 0) {
                    while ((characterPicked) > character.size() || (characterPicked) < 1) {
                        uiManager.showMessage("Tavern keeper: “Please choose an existing character”\n");
                        characterPicked = uiManager.askForInteger("Who would you like to meet [0.." + character.size() + "]: ");
                    }
                }

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

                    uiManager.showMessage("Tavern keeper: “Hey" + characterChosen.getCharacterName()  + " get here; the boss wants to see you!”\n");
                    uiManager.showMessage("* " + "Name:   " + characterChosen.getCharacterName());
                    uiManager.showMessage("* " + "Player: " + characterChosen.getPlayerName());
                    uiManager.showMessage("* " + "Class:  " + characterChosen.getCharacterClass());
                    uiManager.showMessage("* " + "level:  " + characterManager.revertXpToLevel(characterChosen.getCharacterLevel()));
                    uiManager.showMessage("* " + "XP:     " + characterChosen.getCharacterLevel());
                    uiManager.showMessage("* " + "Body:   " + bodyIntToString);
                    uiManager.showMessage("* " + "Mind:   " + mindIntToString);
                    uiManager.showMessage("* " + "Spirit: " + spiritIntToString);

                    uiManager.showMessage("[Enter name to delete, or press enter to cancel]\n");
                    String characterDelete = uiManager.askForString("Do you want to delete " + characterChosen.getCharacterName() + " ? ");

                    boolean erased;

                    if(isUsingApi){
                        erased = characterManager.deleteCharacterAPI(characterDelete);
                    }else{
                        erased = characterManager.deleteCharacter(characterDelete);
                    }
                    if(erased){
                        uiManager.showMessage("Tavern keeper: “I’m sorry kiddo, but you have to leave.”\n");
                        uiManager.showMessage("Character " + characterChosen.getCharacterName() + " left the Guild.\n");
                    }else{
                        uiManager.showMessage("Tavern keeper: “Don't worry mate you don't need to decide now. Come back when you decided who you want to fire”\n");
                    }

                }else{
                    uiManager.showMessage("Tavern keeper: “Don't worry mate you don't need to decide now. Come back when you decided who you want to meet”\n");
                }
            }else{
                uiManager.showMessage("Tavern keeper: “That player has never created a character. Come back later”\n");
            }
        }else{
            uiManager.showMessage("\nTavern keeper: “I'm sorry mate, it seems that there is no one here at the moment”");
            uiManager.showMessage("There are no characters created at the moment\n");
        }
    }

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
        boolean adventureSaved;

        uiManager.showMessage("Tavern keeper: “Planning an adventure? Good luck with that!”\n");
        String adventureName = uiManager.askForString("-> Name your adventure: ");

        uiManager.showMessage("\nTavern keeper: “You plan to undertake " + adventureName + " , really?”\n" + "“How long will that take?”\n");

        while(error == 0) {
            adventureEncounters = uiManager.askForInteger("-> How many encounters do you want [1..4]: ");
            if(adventureEncounters > 4 || adventureEncounters < 1){
                uiManager.showMessage("\nTavern keeper: “That number of encounters is impossible to be truth, ya fooling around with me aren't ya”");
                uiManager.showMessage("Number of encounters should be between 1 and 4\n");
            }
            else {
                error = 1;
            }
        }

        uiManager.showMessage("\nTavern keeper: “"+ adventureEncounters +" encounters? That is too much for me...”");

        ArrayList<ArrayList<Monster>> encounterMonsters;
        ArrayList<String> monstersQuantityAndNames = new ArrayList<String>(1);

        encounterMonsters = new ArrayList<ArrayList<Monster>>(adventureEncounters);

        encounterMonsters = adventureManager.initializeEncounters(encounterMonsters, adventureEncounters);

        while(auxEncounter < adventureEncounters){
            do {
                uiManager.showMessage("\n\n* Encounter " + (auxEncounter + 1) + " / " + adventureEncounters + "");
                uiManager.showMessage("* Monsters in encounter");
                i = 0;
                boolean exist = false;

                if(encounterMonsters.get(auxEncounter).get(0) != null){
                    for (i = 0; i < monstersQuantityAndNames.size();i++) {
                        String auxName = monstersQuantityAndNames.get(i);
                        String[] auxNameSplit = auxName.split("\\d+");
                        uiManager.showMessage("\t" + (i+1) + " " + auxNameSplit[0] + " (x" + auxName.replaceAll("[^0-9]", "") + ")");
                    }
                }else{
                    uiManager.showMessage("  # Empty");
                }
                uiManager.showAdventureMenu();
                option = uiManager.askForInteger("\n-> Enter an option [1..3]: ");

                if(option == 2 && encounterMonsters.get(auxEncounter).get(0) == null){
                    uiManager.showMessage("\nTavern keeper: “Sorry pal you can't erase monsters if your adventure don't have any, i'll let you add some first”");
                    uiManager.showMessage("The tavern keeper shows you the add monster menu\n");
                    option = 1;
                }

                switch (option) {
                    case 1 -> {
                        ArrayList<Monster> monsters;
                        if(isUsingApi){
                            monsters = monsterManager.getAPIMonsters();
                        }else{
                            monsters = monsterManager.getAllMonsters();
                        }

                        i = 0;

                        for (Monster monster : monsters) {
                            uiManager.showMessage((i + 1) + ". " + monster.getMonsterName() + " (" +monster.getMonsterChallenge()+ ")");
                            i++;
                            totalMonsters = i;
                        }

                        monsterOption = uiManager.askForInteger("-> Choose a monster to add [1.." + totalMonsters + "]: ");

                        if(monsterOption > monsters.size() || monsterOption < 1) {
                            while(monsterOption > monsters.size() || monsterOption < 1) {
                                uiManager.showMessage("Tavern keeper: “Please choose an existing monster”\n");
                                monsterOption = uiManager.askForInteger("-> Choose a monster to add [1.." + monsters.size() + "]: ");
                            }
                        }

                        lastQuantity = monsterQuantity + lastQuantity;
                        error = 1;
                        while(error == 1) {
                            monsterQuantity = uiManager.askForInteger("-> How many " + monsters.get(monsterOption - 1).getMonsterName() + " do you want to add: ");

                            if(monsterQuantity < 1){
                                uiManager.showMessage("\nTavern keeper: “Please add a correct number of monsters”");
                                uiManager.showMessage("Quantity must be greater than 0”\n");
                            }
                            else{
                                error = 0;
                            }
                        }

                        lastQuantity = adventureManager.capacityEnsurance(auxEncounter, lastQuantity, monsterQuantity, encounterMonsters);

                        if(encounterMonsters.get(auxEncounter) != null){
                            exist = adventureManager.checkMonsterTypeOfEncounter(encounterMonsters.get(auxEncounter), monsters, monsterOption);
                        }

                        if(exist){
                            lastQuantity = lastQuantity - monsterQuantity;
                            uiManager.showMessage("\nTavern keeper: “You can't add more than 2 different type of boss in your encounter”");
                        }else {
                            adventureManager.setMonstersEncounter(monsters, encounterMonsters, monsterOption, lastQuantity, monsterQuantity, auxEncounter);
                            adventureManager.setMonstersNames(monstersQuantityAndNames, monsters, monsterQuantity, monsterOption);
                        }
                    }

                    case 2 -> {
                        monsterDeleteOption = uiManager.askForInteger("Which monster do you want to delete: ");

                        if(monsterDeleteOption > monstersQuantityAndNames.size() || monsterDeleteOption < 1) {
                            while(monsterDeleteOption > monstersQuantityAndNames.size() || monsterDeleteOption < 1) {
                                uiManager.showMessage("Tavern keeper: “Please choose an existing monster”\n");
                                monsterDeleteOption = uiManager.askForInteger("Which monster do you want to delete: ");
                            }
                        }

                        int removedCounter = 0;
                        String monsterToBeErased = monstersQuantityAndNames.get(monsterDeleteOption - 1);

                        removedCounter = adventureManager.removeMonsterFromEncounter(encounterMonsters, monstersQuantityAndNames, monsterDeleteOption, lastQuantity, monsterQuantity, auxEncounter);

                        lastQuantity = lastQuantity - removedCounter;

                        monsterToBeErased = monsterToBeErased.replaceAll("\\d","");

                        uiManager.showMessage(removedCounter + " " + monsterToBeErased  + " were removed from the encounter.");
                    }
                    case 3 -> {

                        if(encounterMonsters.get(auxEncounter).get(0) == null){
                            uiManager.showMessage("\nYou can't create and encounter without monsters\n");
                        }else{
                            auxEncounter++;
                            monsterQuantity = 0;
                            lastQuantity = 0;
                            monstersQuantityAndNames = new ArrayList<String>(1);
                        }
                    }
                    default -> {
                        uiManager.showMessage("\nTavern keeper: “I can't understand you, could you repeat it to me, please?”");
                        uiManager.showMessage("\nValid options are between 1 to 3 (including them)”");
                    }
                }
            }while(option != 3);
        }

        if(isUsingApi) {
            adventureSaved = adventureManager.createAdventureAPI(adventureName, adventureEncounters, encounterMonsters);
        }else{
            adventureSaved = adventureManager.createAdventure(adventureName, adventureEncounters, encounterMonsters);
        }

        if(adventureSaved){
            uiManager.showMessage("\nTavern keeper: “Your adventure is ready whenever you want to play it”");
        }else{
            uiManager.showMessage("\nTavern keeper: “I don't know an adventure like that could be carry on, make sure to do it correctly”");
            uiManager.showMessage("\nSomething went wrong in the creation of your adventure");
        }
    }


    private void adventurePlay(boolean isUsingApi) throws IOException {
        int characterQuantity;
        int adventureSelection;
        int defeated = 0;
        int[] saveNumber = new int[5];
        int counterEncounters;
        ArrayList<String> charactersLife = new ArrayList<>(0);
        ArrayList<Adventure> adventures;

        int monstersDefeat = 0; //number of monsters encounter defeat
        int charactersDefeat = 0; //number of characters defeat
        uiManager.showMessage("""
                Tavern keeper: “So, you are looking to go on an adventure?”
                “Where do you fancy going?”
                """);
        uiManager.showMessage("Available adventures:\n");
        if(isUsingApi){
            adventures = adventureManager.getAPIAdventuresList();
        }else{
            //listar aventuras en el JSON
            adventures = adventureManager.getAdventuresList();
        }
        for(int i = 0; i < adventures.size(); i++){
            uiManager.showMessage((i+1) + ". " + adventures.get(i).getAdventureName() );
        }

        adventureSelection = uiManager.askForInteger("-> Choose an adventure: ");

        if((adventureSelection) > adventures.size() || (adventureSelection) < 1) {
            while ((adventureSelection) > adventures.size() || (adventureSelection) < 1) {
                uiManager.showMessage("Tavern keeper: “Please choose an existing adventure”\n");
                adventureSelection = uiManager.askForInteger("-> Choose an adventure: ");
            }
        }
        uiManager.showMessage("Tavern keeper: “" + adventures.get(adventureSelection - 1).getAdventureName() + " it is!" + "” \n “And how many people shall join you?”");

        characterQuantity = uiManager.askForInteger("-> Choose a number of characters [3..5]: ");
        if((characterQuantity) > 5 || (characterQuantity) < 3) {
            while ((characterQuantity) > 5 || (characterQuantity) < 3)  {
                uiManager.showMessage("Tavern keeper: “Please choose a correct number of characters”\n");
                characterQuantity = uiManager.askForInteger("-> Choose a number of characters [3..5]: ");
            }
        }
        uiManager.showMessage("Tavern keeper: “Great, " + characterQuantity + " it is.”\n" + "“Who among these lads shall join you?”");

        int i = 0, j = 0;
        ArrayList<Character> characterInParty = new ArrayList<>(characterQuantity);

        for(i = 0; i < characterQuantity; i++){
            characterInParty.add(i, null);
        }

        //creation of the adventure party
        do{
            i = 0;
            ArrayList<Character> characters;
            uiManager.showMessage("------------------------------");
            uiManager.showMessage("Your party (" + j + " / "+ characterQuantity +"):\n");

            while(i < characterQuantity){
                if(characterInParty.get(i) == null){
                    uiManager.showMessage((i+1) + ". Empty");
                }else{
                    uiManager.showMessage((i+1) + "." + characterInParty.get(i).getCharacterName());
                }
                i++;
            }
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

            if (CharacterPartySelected < 1 || CharacterPartySelected > characters.size()) {
                while (CharacterPartySelected < 1 || CharacterPartySelected > characters.size()) {
                    uiManager.showMessage("Tavern keeper: “Please choose an existing character”\n");
                    CharacterPartySelected = uiManager.askForInteger("-> Choose character "+ (j+1) + " in your party: \n");
                }
            }
            int w;
            for (w=0; w<5; w++) {
                if (saveNumber[w] == CharacterPartySelected) {
                    uiManager.showMessage("Tavern keeper: “You've already chosen this character!”\n");
                    CharacterPartySelected = uiManager.askForInteger("-> Choose character "+ (j+1) + " in your party: \n");
                    while (CharacterPartySelected < 1 || CharacterPartySelected > characters.size() || saveNumber[w] == CharacterPartySelected) {
                        uiManager.showMessage("Tavern keeper: “Come on! Just choose an existing new character.”\n");
                        CharacterPartySelected = uiManager.askForInteger("-> Choose character "+ (j+1) + " in your party: \n");
                    }
                }
            }

            saveNumber[j] = CharacterPartySelected;
            characterInParty.set(j, characters.get(CharacterPartySelected - 1));
            j++;
        }while(j < characterQuantity);

        i = 0;
        while(i < characterQuantity){
            if(characterInParty.get(i) == null){
                uiManager.showMessage((i+1) + ". Empty");
            }else{
                uiManager.showMessage((i+1) + "." + characterInParty.get(i).getCharacterName());
            }
            i++;
        }

        uiManager.showMessage("\nTavern keeper: “Great, good luck on your adventure lads!”\n");
        uiManager.showMessage("The “" + adventures.get(adventureSelection - 1).getAdventureName()  +"” will start soon...\n");
        counterEncounters = 0;
        int adventureEncounters = adventures.get(adventureSelection - 1).getEncounters();

        adventureManager.setAdventurersLifeList(characterInParty, charactersLife, isUsingApi);


        //Combat phases

        do{
            ArrayList<String> monstersLife = new ArrayList<>(0);
            ArrayList<Mage> magesInBattle = new ArrayList<>(0);

            uiManager.showMessage("---------------------");
            uiManager.showMessage("Starting Encounter "+ (counterEncounters + 1) +":");

            ArrayList<Monster> monstersInEncounter = adventures.get(adventureSelection - 1).getAdventureEncounterMonsters().get(counterEncounters);
            ArrayList<String> storedName = new ArrayList<>(0);

            adventureManager.countSameMonstersInEncounter(storedName, monstersInEncounter);
            i = 0;
            long count = 0;
            while(i < storedName.size()){
                int finalI1 = i;
                count = monstersInEncounter.stream().filter(m -> m.getMonsterName().equals(storedName.get(finalI1))).count();
                uiManager.showMessage("\t- "+ count + "x " + storedName.get(i));
                i++;
            }
            int z = 0;
            adventureManager.setMagesForAdventure(characterInParty, magesInBattle);
            uiManager.showMessage("---------------------");

            //preparation phase
            uiManager.showMessage("-------------------------");
            uiManager.showMessage("*** Preparation stage ***");
            //get monsters from this battle and encounter number
            uiManager.showMessage("-------------------------\n");
            i = 0;
            while(i < characterQuantity){
                if(characterInParty.get(i).getCharacterClass().equals("Adventurer")){
                    Adventurer adventurer = new Adventurer(characterInParty.get(i));
                    //get characterName from party
                    uiManager.showMessage(characterInParty.get(i).getCharacterName() + " uses Self-Motivated. Their Spirit increases in +1");
                    //update character spirit +1
                    adventurer.selfMotivated();
                    characterInParty.set(i,adventurer);
                }else if(characterInParty.get(i).getCharacterClass().equals("Warrior")){
                    Warrior warrior = new Warrior(characterInParty.get(i));
                    //get characterName from party
                    uiManager.showMessage(characterInParty.get(i).getCharacterName() + " uses Self-Motivated. Their Spirit increases in +1");
                    warrior.selfMotivated();
                    characterInParty.set(i,warrior);
                }else if(characterInParty.get(i).getCharacterClass().equals("Champion")){
                    Champion champion = new Champion(characterInParty.get(i));

                    //get characterName from party
                    uiManager.showMessage(characterInParty.get(i).getCharacterName() + " uses Motivational speech. Everyone’s Spirit increases in +1");
                    //update character spirit +1
                    for(int a = 0; a < characterQuantity; a++){
                        champion.MotivationalSpeech(characterInParty.get(a));
                    }
                }else if(characterInParty.get(i).getCharacterClass().equals("Cleric")){
                    Cleric cleric = new Cleric(characterInParty.get(i));

                    //get characterName from party
                    uiManager.showMessage(characterInParty.get(i).getCharacterName() + " uses Prayer of good luck. Everyone’s Mind increases in +1");
                    //update character mind +1
                    for(int a = 0; a < characterQuantity; a++){
                        cleric.prayerOfGoodLuck(characterInParty.get(a));
                    }

                }else if(characterInParty.get(i).getCharacterClass().equals("Paladin")){

                    Paladin paladin = new Paladin(characterInParty.get(i));
                    int roll = characterManager.diceRollD3();

                    //update character mind +x
                    for(int a = 0; a < characterQuantity; a++){
                        paladin.blessOfGoodLuck(roll,characterInParty.get(a));
                    }

                    //get characterName from party
                    uiManager.showMessage(characterInParty.get(i).getCharacterName() + " uses Blessing of good luck. Everyone’s Mind increases in +" + roll);

                }else if(characterInParty.get(i).getCharacterClass().equals("Mage")){
                    int b = 0;
                    int diceRoll = characterManager.diceRollD6();
                    int characterLevel = characterManager.revertXpToLevel(characterInParty.get(i).getCharacterLevel());
                    for(int a = 0; a<magesInBattle.size(); a++){
                        if(magesInBattle.get(a).getCharacterName().equals(characterInParty.get(i).getCharacterName())){
                            magesInBattle.get(a).shieldSetUp(diceRoll, characterLevel);
                            b = a;
                        }
                    }
                    //get characterName from party
                    uiManager.showMessage(characterInParty.get(i).getCharacterName() + " uses Mage shield. Shield recharges " + magesInBattle.get(b).getShield());
                }
                i++;
            }

            uiManager.showMessage("Rolling initiative...\n");
            z = 0;
            int monsterQuantity = monstersInEncounter.size();
            int roundCounter = 0;

            ArrayList<String> listOfPriorities = adventureManager.listOfPriorities(characterQuantity, monsterQuantity, magesInBattle, characterInParty, monstersInEncounter );

            adventureManager.orderListOfPriorities(listOfPriorities);


            i = 0;
            while(i < listOfPriorities.size()){
                String[] auxCompareName = listOfPriorities.get(i).split("\\d+");
                String compareName = auxCompareName[0];
                int compareInitiative = Integer.parseInt(listOfPriorities.get(i).replaceAll("[^0-9]", ""));
                uiManager.showMessage("\t- " + compareInitiative + "   " + compareName);
                i++;
            }

            uiManager.showMessage("\n\n--------------------");
            uiManager.showMessage("*** Combat stage ***");
            uiManager.showMessage("--------------------");

            //storing lives in format, name actual live/total live
            adventureManager.setMonstersLifeList(characterInParty, monstersLife, monstersInEncounter, listOfPriorities, characterQuantity);

            do{
                int q = 0;
                int damage = 0;
                String[] auxName;
                String actualName;
                int isCrit = 0;
                int totalLife = 0;
                int actualLife = 0;
                boolean isBoss = false;
                int highestMonsterIndex;
                int smallestMonsterIndex;
                int smallestCharacterIndex ;
                int total = 0;
                boolean fail;
                ArrayList<Integer> consciousPosition = new ArrayList<>(0);
                String attackedMonster;
                String compareName;
                String actualDice = null;
                String typeOfDamage;
                ArrayList<String> monstersDamage = new ArrayList<>(0);

               uiManager.showMessage("Round "+  (roundCounter + 1) + ":");
               uiManager.showMessage("Party :");

                z = 0;
                while(z < characterInParty.size()) {
                    auxName = charactersLife.get(z).split("\\d+");
                    actualName = auxName[0];
                    String[] auxLife = charactersLife.get(z).split("/");

                    actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                    totalLife = Integer.parseInt(auxLife[1]);
                    if(z == 0){
                        uiManager.showMessage("\t- "+ actualName + "\t\t\t"+ actualLife + " / " + totalLife + " hit points");
                    }else{
                        uiManager.showMessage("\t- "+ actualName + "\t\t"+ actualLife + " / " + totalLife + " hit points");
                    }
                    z++;
                }

                adventureManager.enemyDice(monstersDamage, monstersInEncounter);

                //battling
                while(q < listOfPriorities.size()){
                    fail = false;
                    isCrit = characterManager.diceRollD10();
                    highestMonsterIndex = adventureManager.highestEnemyLife(monstersLife);
                    smallestMonsterIndex = adventureManager.smallestEnemyLife(monstersLife);
                    smallestCharacterIndex = adventureManager.smallestCharacterLife(charactersLife);
                    charactersDefeat = adventureManager.countDeadCharacters(charactersLife);
                    auxName = listOfPriorities.get(q).split("\\d+");
                    actualName = auxName[0];
                    damage = 0;

                    monstersDefeat = monstersInEncounter.size() - monstersLife.size();

                    if(charactersDefeat < characterInParty.size() && monstersLife.size() != 0) {
                        z = 0;
                        while (z < characterInParty.size()) {
                            if (actualName.equals(characterInParty.get(z).getCharacterName())) {
                                String[] auxLife = charactersLife.get(z).split("/");
                                actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                if(actualLife != 0) {
                                    if(characterInParty.get(z).getCharacterClass().equals("Adventurer")){
                                        Adventurer adventurer = new Adventurer(characterInParty.get(z));
                                        damage = adventurer.swordSlash(characterManager.diceRollD6());
                                        auxName = monstersLife.get(smallestMonsterIndex).split("\\d+");
                                        attackedMonster = auxName[0];
                                        uiManager.showMessage("\n" + actualName + " attacks " + attackedMonster + " with Sword slash.");
                                    }else if(characterInParty.get(z).getCharacterClass().equals("Warrior") || characterInParty.get(z).getCharacterClass().equals("Champion")){
                                        Warrior warrior = new Warrior(characterInParty.get(z));
                                        damage = warrior.improvedSwordSlash(characterManager.diceRollD10());
                                        auxName = monstersLife.get(smallestMonsterIndex).split("\\d+");
                                        attackedMonster = auxName[0];
                                        uiManager.showMessage("\n" + actualName + " attacks " + attackedMonster + " with Improved sword slash.");
                                    }else if(characterInParty.get(z).getCharacterClass().equals("Cleric")){
                                        Cleric cleric = new Cleric(characterInParty.get(z));
                                        int heal = 0;
                                        auxLife = charactersLife.get(smallestCharacterIndex).split("/");
                                        actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                        totalLife = Integer.parseInt(auxLife[1].replaceAll("[^0-9]", ""));
                                        if((totalLife / 2)  >= actualLife){
                                            heal = cleric.prayerOfHealing(characterManager.diceRollD10());
                                            auxName = charactersLife.get(smallestCharacterIndex).split("\\d+");
                                            String healedCharacter = auxName[0];
                                            uiManager.showMessage("\n" + actualName + " uses Prayer of healing. Heals "+ heal + " hit points to " + healedCharacter);
                                            int newLife = heal + actualLife;
                                            charactersLife.set(smallestCharacterIndex, healedCharacter + newLife + "/" + totalLife );
                                        }else{
                                            damage = cleric.notOnMyWatch(characterManager.diceRollD4());
                                            auxName = monstersLife.get(smallestMonsterIndex).split("\\d+");
                                            attackedMonster = auxName[0];
                                            uiManager.showMessage("\n" + actualName + " attacks " + attackedMonster + " with Not on my watch.");
                                        }
                                    }else if(characterInParty.get(z).getCharacterClass().equals("Paladin")){
                                        Paladin paladin = new Paladin(characterInParty.get(z));
                                        int heal = 0;
                                        auxLife = charactersLife.get(smallestCharacterIndex).split("/");
                                        actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                        totalLife = Integer.parseInt(auxLife[1].replaceAll("[^0-9]", ""));
                                        int diceRoll = characterManager.diceRollD10();
                                        if((totalLife / 2)  >= actualLife){
                                            heal = paladin.prayerOfMassHealing(diceRoll);
                                            for(int a = 0; a < characterInParty.size(); a++){
                                                auxLife = charactersLife.get(a).split("/");
                                                actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                                if(actualLife != 0){
                                                    auxName = charactersLife.get(a).split("\\d+");
                                                    String healedCharacter = auxName[0];
                                                    int newLife = heal + actualLife;
                                                    charactersLife.set(smallestCharacterIndex, healedCharacter + newLife + "/" + totalLife );
                                                }
                                            }
                                            uiManager.showMessage("\n" + actualName + " uses Prayer of healing. Heals "+ heal + " hit points to all conscious party" );

                                        }else{
                                            damage = paladin.neverOnMyWatch(characterManager.diceRollD8());
                                            auxName = monstersLife.get(smallestMonsterIndex).split("\\d+");
                                            attackedMonster = auxName[0];
                                            uiManager.showMessage("\n" + actualName + " attacks " + attackedMonster + " with Never on my watch.");
                                        }
                                    }else if(characterInParty.get(z).getCharacterClass().equals("Mage")){
                                        int auxMage = 0;
                                        for(int a = 0; a < magesInBattle.size(); a++){
                                            if(magesInBattle.get(a).getCharacterName().equals(characterInParty.get(z).getCharacterClass())){
                                                auxMage = a;
                                            }
                                        }
                                        if(monstersLife.size() >= 3){
                                            damage = magesInBattle.get(auxMage).fireball(characterManager.diceRollD4());
                                            uiManager.showMessage("\n" + actualName + " uses Fireball. Hits to all alive monsters" );
                                        }else{
                                            damage = magesInBattle.get(auxMage).arcane_missile(characterManager.diceRollD6());
                                            auxName = monstersLife.get(highestMonsterIndex).split("\\d+");
                                            attackedMonster = auxName[0];
                                            uiManager.showMessage("\n" + actualName + " attacks " + attackedMonster +" with Arcane missile.");
                                        }
                                    }

                                }
                                z = characterInParty.size();
                            }
                            z++;
                        }
                    }

                    if(charactersDefeat < characterInParty.size() && monstersDefeat < monstersInEncounter.size()){
                        z = 0;

                        while(z < monstersLife.size()){
                            auxName = monstersLife.get(z).split("\\d+");
                            compareName = auxName[0];
                            if(actualName.equals(compareName)){
                                String[] auxLife = monstersLife.get(z).split("/");
                                actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                i = 0;
                                while(i < monstersDamage.size()){
                                    String[] auxDice = monstersDamage.get(i).split(" ");
                                    compareName = auxDice[0];
                                    if(actualName.equals(compareName)){
                                        actualDice = auxDice[1];
                                    }
                                    i++;
                                }
                                if(actualLife != 0) {
                                    damage = monsterManager.monsterDamageCalculator(actualDice);
                                    if(monstersInEncounter.get(z).getMonsterChallenge().equals("Boss")){
                                        isBoss = true;
                                        int a = 0;
                                        if(consciousPosition.size() != 0){
                                            for(int b = 0; b < consciousPosition.size(); b++){
                                                consciousPosition.remove(b);
                                                b--;
                                            }
                                        }
                                        for(int b = 0; b < charactersLife.size(); b++){
                                            auxLife = charactersLife.get(b).split("/");
                                            actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                            if(actualLife != 0){
                                                consciousPosition.add(a,b);
                                                a++;
                                            }
                                        }

                                        if(consciousPosition.size() == 1){
                                            uiManager.showMessage("\n" + actualName + " attacks " + characterInParty.get(consciousPosition.get(0)).getCharacterName() + ".");
                                        }else if(consciousPosition.size() == 2){
                                            uiManager.showMessage("\n" + actualName + " attacks " + characterInParty.get(consciousPosition.get(0)).getCharacterName() + " and " + characterInParty.get(consciousPosition.get(1)).getCharacterName() + ".");
                                        }else if(consciousPosition.size() == 3){
                                            uiManager.showMessage("\n" + actualName + " attacks " + characterInParty.get(consciousPosition.get(0)).getCharacterName() + ", " + characterInParty.get(consciousPosition.get(1)).getCharacterName() + " and " + characterInParty.get(consciousPosition.get(2)).getCharacterName() + ".");
                                        }else if(consciousPosition.size() == 4){
                                            uiManager.showMessage("\n" + actualName + " attacks " + characterInParty.get(consciousPosition.get(0)).getCharacterName() + ", " + characterInParty.get(consciousPosition.get(1)).getCharacterName() + ", " + characterInParty.get(consciousPosition.get(2)).getCharacterName() + " and " + characterInParty.get(consciousPosition.get(3)).getCharacterName() + ".");
                                        }else{
                                            uiManager.showMessage("\n" + actualName + " attacks " + characterInParty.get(consciousPosition.get(0)).getCharacterName() + ", " + characterInParty.get(consciousPosition.get(1)).getCharacterName() + ", " + characterInParty.get(consciousPosition.get(2)).getCharacterName() + ", " + characterInParty.get(consciousPosition.get(3)).getCharacterName() + " and " + characterInParty.get(consciousPosition.get(4)).getCharacterName() + ".");
                                        }
                                    }else{
                                        uiManager.showMessage("\n" + actualName + " attacks " + characterInParty.get(smallestCharacterIndex).getCharacterName());
                                    }
                                    z = monstersInEncounter.size();
                                }
                            }
                            z++;
                        }
                    }
                    z = 0;
                    j = 0;
                    i = 0;
                    if(monstersLife.size() != 0 && charactersDefeat < characterInParty.size() ){
                        while(z < listOfPriorities.size()){
                            while (j < monstersLife.size()) {

                                auxName = monstersLife.get(j).split("\\d+");
                                compareName = auxName[0];

                                if (actualName.equals(compareName)) {

                                    String[] auxLife = charactersLife.get(smallestCharacterIndex).split("/");
                                    actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                    totalLife = Integer.parseInt(auxLife[1]);

                                    typeOfDamage = monstersInEncounter.get(j).getDamageType();

                                    if(isBoss){
                                        if(isCrit == 2){
                                            total = actualLife - (damage*2);
                                            if(total < 0){
                                                total = 0;
                                            }
                                            uiManager.showMessage("Critical hit and deals " + (damage * 2) + " " + typeOfDamage + " damage.");
                                        }else if(isCrit == 1){
                                            total = actualLife - (damage);
                                            if(total < 0){
                                                total = 0;
                                            }
                                            uiManager.showMessage("Hit and deals " + damage + " " + typeOfDamage + " damage.");
                                        }else{
                                            uiManager.showMessage("Fails and deals 0 damage.");
                                            fail = true;
                                        }
                                        if(!fail){
                                            for(int a = 0; a < characterInParty.size(); a++){
                                                auxLife = charactersLife.get(a).split("/");

                                                actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));

                                                if(actualLife != 0){
                                                    totalLife = Integer.parseInt(auxLife[1]);

                                                    total = actualLife - (damage*2);
                                                    if(total < 0){
                                                        total = 0;
                                                    }

                                                    charactersLife.set(a, characterInParty.get(a).getCharacterName() + total + "/" + totalLife);
                                                    if (total == 0) {
                                                        uiManager.showMessage(characterInParty.get(a).getCharacterName() + " falls unconscious.");
                                                    }
                                                }
                                            }
                                        }

                                    }else {

                                        if(typeOfDamage.equals("Magical") && characterInParty.get(smallestCharacterIndex).getCharacterClass().equals("Mage")){
                                            damage = damage/2;
                                        }else if(typeOfDamage.equals("Physical") && (characterInParty.get(smallestCharacterIndex).getCharacterClass().equals("Warrior") || characterInParty.get(smallestCharacterIndex).getCharacterClass().equals("Champion"))){
                                            damage = damage/2;
                                        }else if(typeOfDamage.equals("Psychical") && (characterInParty.get(smallestCharacterIndex).getCharacterClass().equals("Cleric") || characterInParty.get(smallestCharacterIndex).getCharacterClass().equals("Paladin"))){
                                            damage = damage/2;
                                        }

                                        if(isCrit == 2){
                                            total = actualLife - (damage*2);
                                            if(total < 0){
                                                total = 0;
                                            }
                                            uiManager.showMessage("Critical hit and deals " + (damage * 2) + " " + typeOfDamage + " damage.");
                                        }else if(isCrit == 1){
                                            total = actualLife - (damage);
                                            if(total < 0){
                                                total = 0;
                                            }
                                            uiManager.showMessage("Hit and deals " + damage + " " + typeOfDamage + " damage.");
                                        }else{
                                            uiManager.showMessage("Fails and deals 0 damage.");
                                            fail = true;
                                        }

                                        if(!fail){
                                            charactersLife.set(smallestCharacterIndex, characterInParty.get(smallestCharacterIndex).getCharacterName() + total + "/" + totalLife);
                                            if (total == 0) {
                                                uiManager.showMessage(characterInParty.get(smallestCharacterIndex).getCharacterName() + " falls unconscious.");
                                            }
                                        }

                                    }

                                    z = listOfPriorities.size();
                                    j = monstersInEncounter.size();


                                }
                                j++;
                            }

                            while(i < characterInParty.size()) {
                                if(actualName.equals(characterInParty.get(i).getCharacterName())) {

                                    String[] auxLife = charactersLife.get(i).split("/");
                                    actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));

                                    if(characterInParty.get(i).getCharacterClass().equals("Adventurer") || characterInParty.get(i).getCharacterClass().equals("Warrior") || characterInParty.get(i).getCharacterClass().equals("Champion")){
                                        typeOfDamage = "physical";
                                    }else if(characterInParty.get(i).getCharacterClass().equals("Cleric") || characterInParty.get(i).getCharacterClass().equals("Paladin")){
                                        typeOfDamage = "psychic";
                                    }else{
                                        typeOfDamage = "magical";
                                    }

                                    if(actualLife != 0) {
                                        if(characterInParty.get(i).getCharacterClass().equals("Mage") && monstersLife.size() >= 3){
                                            if(isCrit == 2){
                                                uiManager.showMessage("Critical hit and deals " + (damage * 2) + " " + typeOfDamage + " damage.");
                                            }else if(isCrit == 1){
                                                uiManager.showMessage("Hit and deals " + damage + " " + typeOfDamage + " damage.");
                                            }else{
                                                uiManager.showMessage("Fails and deals 0 damage.");
                                                fail = true;
                                            }

                                            for(int c = 0; c < monstersLife.size(); c++){
                                                auxLife = monstersLife.get(c).split("/");
                                                actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                                totalLife = Integer.parseInt(auxLife[1]);
                                                auxName = monstersLife.get(c).split("\\d+");
                                                attackedMonster = auxName[0];
                                                int auxIndex = 0;
                                                if(isCrit == 2){
                                                    total = actualLife - (damage*2);
                                                    if(total < 0){
                                                        total = 0;
                                                    }
                                                }else if(isCrit == 1){
                                                    total = actualLife - (damage);
                                                    if(total < 0){
                                                        total = 0;
                                                    }
                                                }else{
                                                    fail = true;
                                                }
                                                if(!fail){
                                                    if (total == 0) {
                                                        uiManager.showMessage(attackedMonster + " dies.");
                                                        for(int a = 0; a < listOfPriorities.size(); a++){
                                                            auxName = listOfPriorities.get(a).split("\\d+");
                                                            actualName = auxName[0];
                                                            if(actualName.equals(attackedMonster)){
                                                                listOfPriorities.remove(a);
                                                                monstersLife.remove(c);
                                                                a = listOfPriorities.size();
                                                                c--;
                                                            }
                                                        }
                                                    }else{
                                                        monstersLife.set(c, attackedMonster + total + "/" + totalLife);
                                                    }
                                                }
                                            }
                                        }else if(characterInParty.get(i).getCharacterClass().equals("Mage") && monstersLife.size() < 3){
                                            auxLife = monstersLife.get(highestMonsterIndex).split("/");
                                            actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                            totalLife = Integer.parseInt(auxLife[1]);

                                            auxName = monstersLife.get(highestMonsterIndex).split("\\d+");
                                            attackedMonster = auxName[0];

                                            if(isCrit == 2){
                                                total = actualLife - (damage*2);
                                                if(total < 0){
                                                    total = 0;
                                                }
                                                uiManager.showMessage("Critical hit and deals " + (damage * 2) + " " + typeOfDamage + " damage.");
                                            }else if(isCrit == 1){
                                                total = actualLife - (damage);
                                                if(total < 0){
                                                    total = 0;
                                                }
                                                uiManager.showMessage("Hit and deals " + damage + " " + typeOfDamage + " damage.");
                                            }else{
                                                uiManager.showMessage("Fails and deals 0 damage.");
                                                fail = true;
                                            }
                                            if(!fail){
                                                if (total == 0) {
                                                    uiManager.showMessage(attackedMonster + " dies.");
                                                    for(int a = 0; a < listOfPriorities.size(); a++){
                                                        auxName = listOfPriorities.get(a).split("\\d+");
                                                        actualName = auxName[0];
                                                        if(actualName.equals(attackedMonster)){
                                                            listOfPriorities.remove(a);
                                                            monstersLife.remove(smallestMonsterIndex);
                                                            a = listOfPriorities.size();
                                                        }
                                                    }
                                                }else{
                                                    monstersLife.set(highestMonsterIndex, attackedMonster + total + "/" + totalLife);
                                                }
                                            }
                                        }else{
                                            if(damage != 0) {
                                                auxLife = monstersLife.get(smallestMonsterIndex).split("/");
                                                actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                                totalLife = Integer.parseInt(auxLife[1]);

                                                auxName = monstersLife.get(smallestMonsterIndex).split("\\d+");
                                                attackedMonster = auxName[0];

                                                if (isCrit == 2) {
                                                    total = actualLife - (damage * 2);
                                                    if (total < 0) {
                                                        total = 0;
                                                    }
                                                    uiManager.showMessage("Critical hit and deals " + (damage * 2) + " " + typeOfDamage + " damage.");
                                                } else if (isCrit == 1) {
                                                    total = actualLife - (damage);
                                                    if (total < 0) {
                                                        total = 0;
                                                    }
                                                    uiManager.showMessage("Hit and deals " + damage + " " + typeOfDamage + " damage.");
                                                } else {
                                                    uiManager.showMessage("Fails and deals 0 damage.");
                                                    fail = true;
                                                }
                                                if(!fail){
                                                    if (total == 0) {
                                                        uiManager.showMessage(attackedMonster + " dies.");
                                                        for (int a = 0; a < listOfPriorities.size(); a++) {
                                                            auxName = listOfPriorities.get(a).split("\\d+");
                                                            actualName = auxName[0];
                                                            if (actualName.equals(attackedMonster)) {
                                                                listOfPriorities.remove(a);
                                                                monstersLife.remove(smallestMonsterIndex);
                                                                a = listOfPriorities.size();
                                                            }
                                                        }
                                                    } else {
                                                        monstersLife.set(highestMonsterIndex, attackedMonster + total + "/" + totalLife);
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
                    if(charactersDefeat == characterInParty.size()){
                        q = listOfPriorities.size();
                    }
                    else if(monstersLife.size() == 0){
                        q = listOfPriorities.size();
                    }
                    else{
                        q++;
                    }
                }

                if(charactersDefeat == characterInParty.size()){
                    defeated = 1;
                }else{
                    uiManager.showMessage("\nEnd of round "+ (roundCounter + 1) +".\n");
                    roundCounter++;
                }

            }while(monstersLife.size() != 0 && charactersDefeat < characterInParty.size());

            if(defeated == 0){
                uiManager.showMessage("All enemies are defeated.");
                uiManager.showMessage("--------------------");
                uiManager.showMessage("*** Short rest stage ***");
                uiManager.showMessage("--------------------");
                int xpSum = 0;
                i = 0;
                boolean levelUp;
                while(i < monstersInEncounter.size()){
                    xpSum = xpSum + monstersInEncounter.get(i).getMonsterXpDrop();
                    i++;
                }
                i = 0;
                boolean evolved;
                while(i < characterQuantity){

                    levelUp = characterManager.levelUpCheck(xpSum, characterInParty.get(i).getCharacterLevel());

                    if(isUsingApi){
                        if(levelUp){
                            characterManager.levelUpdateAPI(characterInParty.get(i), xpSum);
                            characterInParty.set(i,characterManager.getCharacterByName(characterInParty.get(i).getCharacterName(),isUsingApi));
                            uiManager.showMessage(characterInParty.get(i).getCharacterName() + " gains " + xpSum + " xp." + characterInParty.get(i).getCharacterName() + " levels up. They are now lvl " + characterManager.revertXpToLevel(characterInParty.get(i).getCharacterLevel()) + "!");

                            evolved = characterManager.evolution(characterInParty.get(i), isUsingApi);

                            if(evolved){
                                uiManager.showMessage(characterInParty.get(i).getCharacterName() + " evolves to " + characterInParty.get(i).getCharacterClass());
                            }
                        }else{
                            characterManager.levelUpdateAPI(characterInParty.get(i), xpSum);
                            uiManager.showMessage(characterInParty.get(i).getCharacterName() + " gains " + xpSum + " xp.");
                        }
                    }else{
                        if(levelUp){
                            characterManager.levelUpdate(characterInParty.get(i), xpSum);
                            characterInParty.set(i,characterManager.getCharacterByName(characterInParty.get(i).getCharacterName(),isUsingApi));
                            uiManager.showMessage(characterInParty.get(i).getCharacterName() + " gains " + xpSum + " xp." + characterInParty.get(i).getCharacterName() + " levels up. They are now lvl " + characterManager.revertXpToLevel(characterInParty.get(i).getCharacterLevel()) + "!");

                            evolved = characterManager.evolution(characterInParty.get(i), isUsingApi);
                            if(evolved){
                                uiManager.showMessage(characterInParty.get(i).getCharacterName() + " evolves to " + characterInParty.get(i).getCharacterClass());
                            }
                        }else{
                            characterManager.levelUpdate(characterInParty.get(i), xpSum);
                            uiManager.showMessage(characterInParty.get(i).getCharacterName() + " gains " + xpSum + " xp.");
                        }
                    }
                    characterInParty.set(i,characterManager.getCharacterByName(characterInParty.get(i).getCharacterName(),isUsingApi));

                    i++;

                }

                //Bandage time
                i = 0;
                while(i < characterQuantity){

                    String[] parts = charactersLife.get(i).split("/");
                    int temporalLife = Integer.parseInt(parts[0].replaceAll("[^0-99]", ""));
                    int restLife =  Integer.parseInt(parts[1]);
                    if(characterInParty.get(i).getCharacterClass().equals("Adventurer") || characterInParty.get(i).getCharacterClass().equals("Warrior")){
                        if(temporalLife != 0) {
                            Adventurer adventurer = new Adventurer(characterInParty.get(i));
                            int diceRollHeal = characterManager.diceRollD8();
                            int characterCuration = adventurer.bandageTime(diceRollHeal);
                            int characterBandage =  characterCuration + temporalLife;

                            if(characterBandage > restLife) {
                                characterBandage = restLife;
                            }

                            charactersLife.set(i, characterInParty.get(i).getCharacterName() + characterBandage + "/" + restLife);

                            uiManager.showMessage(characterInParty.get(i).getCharacterName() + " uses BandageTime. Heals " + characterCuration + " hit points");
                        }else{
                            uiManager.showMessage(characterInParty.get(i).getCharacterName() + " is unconscious");
                        }

                    }else if(characterInParty.get(i).getCharacterClass().equals("Champion")){
                        if(temporalLife != 0) {
                            Champion champion = new Champion(characterInParty.get(i));

                            int characterCuration = champion.improvedBandageTime(restLife, temporalLife);
                            int characterBandage =  characterCuration + temporalLife;

                            if(characterBandage > restLife) {
                                characterBandage = restLife;
                            }

                            charactersLife.set(i, characterInParty.get(i).getCharacterName() + characterBandage + "/" + restLife);

                            uiManager.showMessage(characterInParty.get(i).getCharacterName() + " uses Improved bandage time. Heals " + characterCuration + " hit points");
                        }else{
                            uiManager.showMessage(characterInParty.get(i).getCharacterName() + " is unconscious");
                        }
                    }else if(characterInParty.get(i).getCharacterClass().equals("Cleric")){
                        if(temporalLife != 0) {
                            Cleric cleric = new Cleric(characterInParty.get(i));
                            int diceRollHeal = characterManager.diceRollD10();
                            int characterCuration = cleric.prayerOfSelfHealing(diceRollHeal);
                            int characterHeal =  characterCuration + temporalLife;

                            if(characterHeal > restLife) {
                                characterHeal = restLife;
                            }

                            charactersLife.set(i, characterInParty.get(i).getCharacterName() + characterHeal + "/" + restLife);

                            uiManager.showMessage(characterInParty.get(i).getCharacterName() + " uses Payer of self-healing. Heals " + characterCuration + " hit points");
                        }else{
                            uiManager.showMessage(characterInParty.get(i).getCharacterName() + " is unconscious");
                        }
                    }else if(characterInParty.get(i).getCharacterClass().equals("Paladin")){
                        if(temporalLife != 0) {
                            Paladin paladin = new Paladin(characterInParty.get(i));
                            int diceRollHeal = characterManager.diceRollD10();
                            int characterCuration = paladin.prayerOfMassHealing(diceRollHeal);
                            int characterBandage =  characterCuration + temporalLife;

                            if(characterBandage > restLife) {
                                characterBandage = restLife;
                            }
                            for(int a = 0; a < characterInParty.size(); a++){
                                parts = charactersLife.get(a).split("/");
                                temporalLife = Integer.parseInt(parts[0].replaceAll("[^0-99]", ""));
                                restLife =  Integer.parseInt(parts[1]);
                                if(temporalLife != 0){
                                    charactersLife.set(a, characterInParty.get(a).getCharacterName() + characterBandage + "/" + restLife);
                                }
                            }

                            uiManager.showMessage(characterInParty.get(i).getCharacterName() + " uses Prayer of mass healing. Heals " + characterCuration + " hit points to all the conscious party");
                        }else{
                            uiManager.showMessage(characterInParty.get(i).getCharacterName() + " is unconscious");
                        }
                    }

                    i++;
                }
            }else{
                counterEncounters = 6;
            }
            counterEncounters++;
        }while(counterEncounters < adventureEncounters);

        if(defeated != 0) {
            uiManager.showMessage("""
                    Tavern keeper: “Lad, wake up. Yes, your party fell unconscious.”
                    “Don’t worry, you are safe back at the Tavern.”
                    """);
        }else{
            uiManager.showMessage("Congratulations, your party completed “"+ adventures.get(adventureSelection - 1).getAdventureName() +"”\n");
        }

    }
}
