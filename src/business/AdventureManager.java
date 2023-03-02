package business;

import business.entities.Adventure;
import business.entities.Monster;
import business.entities.Character;
import persistance.AdventureAPI;
import persistance.AdventureDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class AdventureManager {

    AdventureDAO adventureDAO;
    AdventureAPI adventureAPI;
    CharacterManager characterManager;


    public AdventureManager(AdventureDAO adventureDAO, CharacterManager characterManager, AdventureAPI adventureAPI){
        this.adventureDAO = adventureDAO;
        this.characterManager = characterManager;
        this.adventureAPI = adventureAPI;
    }

    public void setMonstersEncounter(ArrayList<Monster> monsters, ArrayList<ArrayList<Monster>> encounterMonsters, int monsterOption, int lastQuantity ,int monsterQuantity, int auxEncounter){

        int i = 0;
        while(i < monsterQuantity) {
            encounterMonsters.get(auxEncounter).add(lastQuantity + i, monsters.get(monsterOption - 1));
            i++;
        }

    }

    public int capacityEnsurance(int encounter, int lastQuantity, int monsterQuantity, ArrayList<ArrayList<Monster>> encounterMonsters){

        if(encounterMonsters.get(encounter).get(0) == null){
            encounterMonsters.set(encounter, new ArrayList<Monster>(monsterQuantity));
            lastQuantity = 0;
        }else{
            encounterMonsters.get(encounter).ensureCapacity(lastQuantity);
        }
        return lastQuantity;
    }

    public void setMonstersNames(ArrayList<String> monstersQuantityAndNames, ArrayList<Monster> monsters, int monsterQuantity, int monsterOption){
        int auxSize = monstersQuantityAndNames.size();
        if(monstersQuantityAndNames.size() == 0){
            monstersQuantityAndNames.add(0, monsters.get(monsterOption - 1).getMonsterName() + monsterQuantity);
        }
        else{
            for(int j = 0; j < monstersQuantityAndNames.size(); j++){
                String auxName = monstersQuantityAndNames.get(j);
                String[] auxNameSplit = auxName.split("\\d+");

                if(!auxNameSplit[0].matches(monsters.get(monsterOption - 1).getMonsterName())){
                    if(auxSize == monstersQuantityAndNames.size() && j == monstersQuantityAndNames.size() - 1){
                        monstersQuantityAndNames.add(j + 1, monsters.get(monsterOption - 1).getMonsterName() + monsterQuantity);
                    }

                }else{
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

    public int removeMonsterFromEncounter(ArrayList<ArrayList<Monster>> encounterMonsters, ArrayList<String> monstersQuantityAndNames, int monsterDeleteOption, int lastQuantity, int monsterQuantity, int auxEncounter){
        String monsterToBeErased = monstersQuantityAndNames.get(monsterDeleteOption - 1);
        String[] auxNameSplit = monsterToBeErased.split("\\d+");
        int removedCounter = 0;

        lastQuantity = lastQuantity + monsterQuantity;

        for(int i = 0; i < lastQuantity; i++){
            String comparedName = encounterMonsters.get(auxEncounter).get(i - removedCounter).getMonsterName();

            if(comparedName.equals(auxNameSplit[0])){
                encounterMonsters.get(auxEncounter).remove(i - removedCounter);
                removedCounter++;
                if(encounterMonsters.get(auxEncounter).size() == 0){
                    encounterMonsters.get(auxEncounter).add(0,null);

                }
            }
        }
        monstersQuantityAndNames.remove(monsterDeleteOption - 1);
        return removedCounter;
    }

    public ArrayList<Adventure> getAdventuresList(){
        return adventureDAO.getAllAdventures();
    }

    public ArrayList<Adventure> getAPIAdventuresList() throws IOException {return adventureAPI.getFromUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_12/adventures");}


    public boolean checkMonsterTypeOfEncounter(ArrayList<Monster> monstersInEncounter, ArrayList<Monster> monstersList, int option){

        boolean exist = false;
        int i = 0;
        int bossCounter = 0;

        while(i < monstersInEncounter.size()){
            if(monstersInEncounter.get(i).getMonsterChallenge().equals("Boss")){
                bossCounter++;
            }
            i++;
        }

        if(monstersList.get(option - 1).getMonsterChallenge().equals("Boss") && bossCounter >= 1){
            exist = true;
        }


        return exist;
    }

    public void setMonstersLifeList(ArrayList<Character> charactersInParty, ArrayList<String> monstersLife, ArrayList<Monster> monstersInEncounter, ArrayList<String> listOfPriorities, int characterQuantity){
        int z = 0;
        int i = 0;
        while(z < listOfPriorities.size()){
            String[] auxName = listOfPriorities.get(z).split("\\d+");
            String actualName = auxName[0];
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

    public void setAdventurersLifeList(ArrayList<Character> characterInParty, ArrayList<String> charactersLife, boolean isUsingApi) throws IOException {
        int z;
        int characterInitialLife = 0;
        if(charactersLife.size() == 0) {
            z = 0;
            while (z < characterInParty.size()) {
                characterInitialLife = characterManager.initialLifeCalculator(characterInParty.get(z).getCharacterName(), isUsingApi);
                charactersLife.add(z, characterInParty.get(z).getCharacterName() + characterInitialLife + "/" + characterInitialLife);
                z++;
            }
        }
    }

    public int countDeadCharacters(ArrayList<String> charactersLife){
        int deadCounter = 0;

        for(int a = 0; a < charactersLife.size(); a++){
            String[] auxLife = charactersLife.get(a).split("/");
            int actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
            if(actualLife == 0){
                deadCounter++;
            }
        }

        return deadCounter;
    }

    public int smallestCharacterLife(ArrayList<String> charactersLife){
        int z = 0;
        int flag = 0;
        int index = 0;
        int smallerCharacterLife = 0;
        while(z < charactersLife.size()){
            String[] auxLife = charactersLife.get(z).split("/");
            int actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));

            if(actualLife != 0){
                if(flag == 0){
                    smallerCharacterLife = actualLife;
                    index = z;
                    flag = 1;
                }else{
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

    public int highestEnemyLife(ArrayList<String> monstersLife){
        int index = 0;
        int z = 0;
        int biggerMonsterLife = 0;
        int flag = 0;

        while(z < monstersLife.size()){
            String[] auxLife = monstersLife.get(z).split("/");
            int actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
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


    public int smallestEnemyLife(ArrayList<String> monstersLife){
        int index = 0;
        int z = 0;
        int smallerMonsterLife = 0;
        int flag = 0;

        while(z < monstersLife.size()){
            String[] auxLife = monstersLife.get(z).split("/");
            int actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
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


    public void setMagesForAdventure(ArrayList<Character> characterInParty, ArrayList<Mage> magesInBattle){
        int z = 0;

        for(int a = 0; a < characterInParty.size(); a++){
            if(characterInParty.get(a).getCharacterClass().equals("Mage")){
                magesInBattle.add(z, new Mage(characterInParty.get(a), 0));
                z++;
            }
        }
    }

    public void orderListOfPriorities(ArrayList<String> listOfPriorities){
        int i = 0;
        int z;
        boolean auxFlag;
        while(i < listOfPriorities.size()){
            z = 0;
            String[] auxName = listOfPriorities.get(i).split("\\d+");
            String actualName = auxName[0];
            auxFlag = false;
            int actualInitiative = Integer.parseInt(listOfPriorities.get(i).replaceAll("[^0-9]", ""));

            while(z < listOfPriorities.size()){
                String[] auxCompareName = listOfPriorities.get(z).split("\\d+");
                String compareName = auxCompareName[0];
                int compareInitiative = Integer.parseInt(listOfPriorities.get(z).replaceAll("[^0-9]", ""));

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


    public ArrayList<String> listOfPriorities(int characterQuantity, int monsterQuantity, ArrayList<Mage> magesInBattle, ArrayList<Character> characterInParty, ArrayList<Monster> monstersInEncounter){
        ArrayList<String> listOfPriorities = new ArrayList<>(0);
        int i = 0;
        int z = 0;
        int characterSpirit = 0;
        int characterInitiative = 0;
        int diceroll = 0;
        while(i < characterQuantity + monsterQuantity){
            if(i < characterInParty.size()){
                if(characterInParty.get(i).getCharacterClass().equals("Adventurer") || characterInParty.get(i).getCharacterClass().equals("Warrior") || characterInParty.get(i).getCharacterClass().equals("Champion")){
                    Adventurer adventurer = new Adventurer(characterInParty.get(i));
                    diceroll = characterManager.diceRollD12();
                    characterInitiative = adventurer.initiative(diceroll);
                    listOfPriorities.add(z, characterInParty.get(i).getCharacterName() + characterInitiative);
                }else if(characterInParty.get(i).getCharacterClass().equals("Cleric") || characterInParty.get(i).getCharacterClass().equals("Paladin")){
                    Cleric cleric = new Cleric(characterInParty.get(i));
                    diceroll = characterManager.diceRollD10();
                    characterInitiative = cleric.initiative(diceroll);
                    listOfPriorities.add(z, characterInParty.get(i).getCharacterName() + characterInitiative);
                }else if(characterInParty.get(i).getCharacterClass().equals("Mage")){
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

            if(i < monstersInEncounter.size()){
                listOfPriorities.add(z,monstersInEncounter.get(i).getMonsterName() + monstersInEncounter.get(i).getMonsterInitiative());
                z++;
            }
            i++;
        }

        return listOfPriorities;
    }

    public ArrayList<ArrayList<Monster>> initializeEncounters(ArrayList<ArrayList<Monster>> encounterMonsters, int adventureEncounters){
        for (int i = 0; i < adventureEncounters; i++) {
            encounterMonsters.add(i, new ArrayList<Monster>(1));
            encounterMonsters.get(i).add(0,null);
        }
        return encounterMonsters;
    }

    public void countSameMonstersInEncounter(ArrayList<String> storedName, ArrayList<Monster> monstersInEncounter){

        int i = 0;
        int j = 0;
        int z = 0;
        boolean repeated = true;
        while(i < monstersInEncounter.size()){
            z = 0;
            if(i == 0){
                storedName.add(j, monstersInEncounter.get(i).getMonsterName());
                j++;
            }else{
                while(z < storedName.size()){
                    repeated = storedName.get(z).equals(monstersInEncounter.get(i).getMonsterName());
                    if(repeated){
                        z = storedName.size();
                    }else{
                        z++;
                    }
                }
                if(!repeated){
                    storedName.add(j, monstersInEncounter.get(i).getMonsterName());
                    j++;
                }
            }

            i++;
        }
    }

    public boolean createAdventure(String adventureName, int encounters, ArrayList<ArrayList<Monster>> monsters){
        return adventureDAO.saveAdventure(new Adventure(adventureName, encounters, monsters));
    }


    public boolean createAdventureAPI(String adventureName, int encounters, ArrayList<ArrayList<Monster>> monsters) throws IOException {
        return adventureAPI.postToUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_12/adventures", new Adventure(adventureName, encounters, monsters));
    }

    public void enemyDice(ArrayList<String> monstersDamage, ArrayList<Monster> monstersInEncounter){
        int z = 0;
        int i = 0;

        while(z < monstersInEncounter.size()){
            if(z == 0){
                monstersDamage.add(i,monstersInEncounter.get(z).getMonsterName() + " " + monstersInEncounter.get(z).getMonsterDice());
                i++;
            }else{
                String [] auxName = monstersDamage.get(i - 1).split(" ");
                String actualName = auxName[0];
                if(!actualName.equals(monstersInEncounter.get(z).getMonsterName())){
                    monstersDamage.add(i,monstersInEncounter.get(z).getMonsterName() + " " + monstersInEncounter.get(z).getMonsterDice());
                    i++;
                }
            }
            z++;
        }
    }

}
