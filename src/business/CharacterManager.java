package business;

import business.entities.Character;
import persistance.CharacterAPI;
import persistance.CharacterDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterManager {

    // Añadimos los componentes
    CharacterDAO characterDAO;
    CharacterAPI characterAPI;

    // Creamos constructores
    /**
     * Esta función servirá para construir el CharacterManager
     *
     * @param characterDAO, lo vincularemos con su respectivo DAO
     */
    public CharacterManager(CharacterDAO characterDAO, CharacterAPI characterAPI){
        this.characterDAO = characterDAO;
        this.characterAPI = characterAPI;
    }

    /**
     * Esta función servirá para crear un character
     *
     * @param characterName, el nombre deseado del personaje
     * @param playerName, el nombre del jugador
     * @param characterLevel, el nivel deseado del personaje
     * @param body, el body deseado del personaje
     * @param mind, el mind deseado del personaje
     * @param spirit, el espíritu deseado del personaje
     * @return se guardará a través del DAO el personaje creado
     */
    public boolean createCharacter(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass){
        return characterDAO.saveCharacter(new Character(characterName, playerName, characterLevel, body, mind, spirit, characterClass));
    }


    /**
     * Esta función servirá para crear un character
     *
     * @param characterName, el nombre deseado del personaje
     * @param playerName, el nombre del jugador
     * @param characterLevel, el nivel deseado del personaje
     * @param body, el body deseado del personaje
     * @param mind, el mind deseado del personaje
     * @param spirit, el espíritu deseado del personaje
     * @return se guardará a través del DAO el personaje creado
     */
    public boolean createCharacterAPI(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass) throws IOException {
        return characterAPI.postToUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_12/characters", new Character(characterName, playerName, characterLevel, body, mind, spirit, characterClass));
    }

    /**
     * Esta función genera un par de números entre el 1 y el 6 simulando tirar
     * dos dados de 6 caras
     *
     * @return roll, array de ints que serán los 2 números random generados
     */
    public int[] diceRoll2D6(){

        int[] roll = {0, 0};

        // Usamos la clase Random para que genere los dos números aleatorios
        Random rand = new Random();
        int upperbound = 6;
        roll[0] = rand.nextInt(upperbound) + 1;
        roll[1] = rand.nextInt(upperbound) + 1;

        return roll;
    }

    /**
     * Esta función calcula qué cantidad de bonus se le tiene que otorgar al jugador
     *
     * @param stat, array que contendrá los dos valores con los que se calculará el bonus
     * @return statBonus, valor que devuleve el bonus que recibirá el jugador
     */
    public String statCalculator(int[] stat){

        String statBonus;

        // Según los dos ints que tenga, se le aplicará diferente bonus a través de ifs
        if(stat[0] + stat[1] <= 2){
            statBonus = "-1";
        }else if(stat[0] + stat[1] <= 5 && stat[0] + stat[1] >= 3){
            statBonus = "+0";
        }else if(stat[0] + stat[1] <= 9 && stat[0] + stat[1] >= 6){
            statBonus = "+1";
        }else if(stat[0] + stat[1] <= 11 && stat[0] + stat[1] >= 10){
            statBonus = "+2";
        }else {
            statBonus = "+3";
        }
        return statBonus;
    }

    /**
     * Función que servirá para calcular cuanta experiencia ganará el jugador según que
     * nivel tenga en ese momento
     *
     * @param level, valor que contendrá el nivel actual del personaje
     * @return xp, valor que devuleve la experiencia que ganará
     */
    public int experienceCalculator(int level){

        int xp;

        // Asignaremos el nivel según la experienca a través de ifs
        if(level == 1){
            xp = 0;
        }else if(level == 2){
            xp = 100;
        }else if(level == 3){
            xp = 200;
        }else if(level == 4){
            xp = 300;
        }else if(level == 5){
            xp = 400;
        }else if(level == 6){
            xp = 500;
        }else if(level == 7){
            xp = 600;
        }else if(level == 8){
            xp = 700;
        }else if(level == 9){
            xp = 800;
        }else{
            xp = 900;
        }
        return xp;
    }

    /**
     * Función que actualizará el nivel de espíritu que tendrá el personaje
     * No tiene ni parámetro ni return
     *
     */
    public void updateCharacterSpirit(){
        ArrayList<Character> characters = characterDAO.readCharacterJSON();
    }

    /**
     * Función que servirá para recibir el espíritu que tiene actualmente el personaje
     *
     * @param characterName, valor que describe el nombre del personaje
     * @return spirit, devuelve el espíritu actual que tiene
     */
    public int getCharacterSpirit(String characterName, boolean isUsingApi) throws IOException{
        int spirit = 0;
        int i = 0;
        ArrayList<Character> characters;

        if(isUsingApi){
            characters = characterAPI.getFromUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_12/characters");

        }else{
            characters = characterDAO.readCharacterJSON();
        }
        // A través del bucle recorreremos toda la ArrayList hasta coincidir nombre con personaje
        while(i < characters.size()){
            if(characterName.equals(characters.get(i).getCharacterName())){
                spirit = characters.get(i).getSpirit();
            }
            i++;
        }
        return spirit;
    }

    /**
     * Función que servirá para recibir el valor actual de body que tiene el personaje
     *
     * @param characterName, valor que describe el nombre del personaje
     * @return body, devuelve el valor de body del personaje
     */
    public int getCharacterBody(String characterName, boolean isUsingApi) throws IOException{
        int body = 0;
        int i = 0;
        ArrayList<Character> characters;

        if(isUsingApi){
            characters = characterAPI.getFromUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_12/characters");

        }else{
            characters = characterDAO.readCharacterJSON();
        }
        // A través del bucle recorreremos toda la ArrayList hasta coincidir nombre con personaje
        while(i < characters.size()){
            if(characterName.equals(characters.get(i).getCharacterName())){
                body = characters.get(i).getBody();
            }
            i++;
        }
        return body;
    }

    /**
     * Función que servirá para recibir el valor actual de mind que tiene el personaje
     *
     * @param characterName, valor que describe el nombre del personaje
     * @return mind, devuelve el valor de mind del personaje
     */
    public int getCharacterMind(String characterName, boolean isUsingApi) throws IOException {
        int mind = 0;
        int i = 0;
        ArrayList<Character> characters;

        if(isUsingApi){
            characters = characterAPI.getFromUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_12/characters");

        }else{
            characters = characterDAO.readCharacterJSON();
        }

        // A través del bucle recorreremos toda la ArrayList hasta coincidir nombre con personaje
        while(i < characters.size()){
            if(characterName.equals(characters.get(i).getCharacterName())){
                mind = characters.get(i).getMind();
            }
            i++;
        }
        return mind;
    }

    /**
     * Función que servirá para recibir el valor actual de xp que tiene el personaje
     *
     * @param characterName, valor que describe el nombre del personaje
     * @return xp, devuelve el valor de xp del personaje
     */
    public int getCharacterXp(String characterName, boolean isUsingApi) throws IOException {
        int xp = 0;
        int i = 0;

        ArrayList<Character> characters;

        if(isUsingApi){
            characters = characterAPI.getFromUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_12/characters");

        }else{
            characters = characterDAO.readCharacterJSON();
        }

        // A través del bucle recorreremos toda la ArrayList hasta coincidir nombre con personaje
        while(i < characters.size()){
            if(characterName.equals(characters.get(i).getCharacterName())){
                xp = characters.get(i).getCharacterLevel();
            }
            i++;
        }
        return xp;
    }

    /*public int initiativeCalculator(String characterName){

        int roll = diceRollD12();
        int initiative = 0;
        int spirit = getCharacterSpirit(characterName);

        initiative = roll + spirit;

        return initiative;
    }*/

    /**
     * Esta función genera un número entre el 1 y el 12 simulando tirar
     * un dado de 12 caras
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD12(){
        int roll = 0;

        // Usaremos la clase Random para sacar el número aleatorio con upperbound de 12
        Random rand = new Random();
        int upperbound = 12;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }

    /**
     * Esta función genera un número entre el 1 y el 10 simulando tirar
     * un dado de 10 caras
     *
     * @return damage, int que será el número daño que causará
     */
    public int diceRollD10(){
        int roll = 0;
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
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD6(){
        int roll = 0;

        // Usaremos la clase Random para sacar el número aleatorio con upperbound de 7
        Random rand = new Random();
        int upperbound = 7;
        roll = rand.nextInt(upperbound);

        if(roll == 0){
            roll = 1;
        }
        return roll;
    }

    /**
     * Esta función genera un número entre el 1 y el 8 simulando tirar
     * un dado de 8 caras
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD8(){
        int roll = 0;

        // Usaremos la clase Random para sacar el número aleatorio con upperbound de 9
        Random rand = new Random();
        int upperbound = 9;
        roll = rand.nextInt(upperbound);

        return roll;
    }

    /**
     * Esta función calcula el daño que hará el jugador con su Sword Slash
     *
     * @param characterName, valor que contendrá el nombre del personaje
     * @return damage, int que será el daño que hará el personaje
     */
    public int characterDamageCalculator(String characterName, boolean isUsingApi) throws IOException {
        int damage = 0;
        int body = getCharacterBody(characterName, isUsingApi);

        // Seguiremos la fórmula del enunciado para calcular el damage
        damage = diceRollD6() + body;

        return damage;
    }

    /**
     * Esta función calcula la curación que recibirá el jugador
     *
     * @param characterName, valor que contendrá el nombre del personaje
     * @return healing, int que dará la curación total del personaje
     */
    public int BandageTime(String characterName, boolean isUsingApi) throws IOException {
        int healing = 0;
        int mind = getCharacterMind(characterName, isUsingApi);

        // Seguiremos la fórmula del enunciado para calcular el healing
        healing = diceRollD8() + mind;

        return healing;
    }

    /**
     * Esta función calcula el nivel que tendrá el jugador tras recibir la experiencia
     *
     * @param xp, valor que contendrá la experiencia que ganará el jugador
     * @return level, int que será el nivel total el personaje
     */
    public int revertXpToLevel(int xp){
        int level;

        // A partir de la experiencia total que tenga el personaje, lo transformaremos a su nivel actual con unos ifs
        if(xp >= 0 && xp <= 99){
            level = 1;
        }else if(xp >= 100 && xp <= 199){
            level = 2;
        }else if(xp >= 200 && xp <= 299){
            level = 3;
        }else if(xp >= 300 && xp <= 399){
            level = 4;
        }else if(xp >= 400 && xp <= 499){
            level = 5;
        }else if(xp >= 500 && xp <= 599){
            level = 6;
        }else if(xp >= 600 && xp <= 699){
            level = 7;
        }else if(xp >= 700 && xp <= 799){
            level = 8;
        }else if(xp >= 800 && xp <= 899){
            level = 9;
        }else{
            level = 10;
        }

        return level;
    }

    /**
     * Esta función calcula la vida que tendrá el jugador una vez se haya modificado
     * al ganar experiencia y subir de nivel
     *
     * @param characterName, valor que contendrá el nombre del personaje
     * @return life, int que será el daño que hará el personaje
     */
    public int initialLifeCalculator(String characterName, boolean isUsingApi) throws IOException {

        int life;
        int body = getCharacterBody(characterName, isUsingApi);
        int xp = getCharacterXp(characterName, isUsingApi);
        int level = revertXpToLevel(xp);

        // Calculamos la vida con la fórmula
        life = (10 + body) * level;

        return life;
    }

    /**
     * Esta función indica si el nombre que se le quiere poner al personaje está ya en
     * uso o está disponible
     *
     * @param name, valor que contendrá el posible nombre del personaje
     * @return exist, bool que dirá si el nombre está disponible o no
     */
    public boolean characterNameDisponibility(String name){

        boolean exist = false;
        ArrayList<Character> characters = characterDAO.readCharacterJSON();
        int i = 0;

        // Analizamos con un bucle si el nombre coincide en la ArrayList
        while(i < characters.size() && !exist){
            if(name.toLowerCase(Locale.ROOT).matches(characters.get(i).getCharacterName().toLowerCase(Locale.ROOT))){
                exist = true;
            }
            i++;
        }

        return exist;
    }

    /**
     * Esta función arregla el nombre introducido por el usuario, haciendo que sí o sí
     * tenga la primera letra en mayúscula
     *
     * @param name, valor que contendrá el nombre posible del personaje
     * @return fixedName, valor con el nombre corregido
     */
    public String fixName(String name){
        String auxName = name;
        String[] auxNameSplit = auxName.split(" ");
        String fixedName = null;
        int i = 0;

        // Cambiamos con un bucle la primera letra y nos aseguramos de que no tenga ningún espacio
        while(i < auxNameSplit.length){
            auxNameSplit[i] = auxNameSplit[i].substring(0,1).toUpperCase() + auxNameSplit[i].substring(1);
            if (i == 0){
                fixedName = auxNameSplit[i];
            }else{
                fixedName = fixedName.concat(auxNameSplit[i]);
            }
            fixedName = fixedName.concat(" ");
            i++;
        }

        fixedName = fixedName.substring(0, fixedName.length() - 1);

        return fixedName;
    }

    /**
     * Esta función pide a través del DAO una lista con el nombre de todos
     * los characters existentes
     *
     * @return devolverá una ArrayList con todos los personajes
     */
    public ArrayList<Character> getAllCharacters(){
        return characterDAO.readCharacterJSON();
    }


    /**
     * Esta función pide a través de la API una lista con el nombre de todos
     * los characters existentes
     *
     * @return devolverá un Array con todos los personajes
     */
    public ArrayList<Character> getAPICharacters() throws IOException {
        return characterAPI.getFromUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_12/characters");
    }

    /**
     * Esta función revisará que el supuesto nombre del personaje no tenga cualquier
     * carácter que no seran letras
     *
     * @param name, valor que contendrá el nombre posible del personaje
     * @return correct, bool que expresará si el nombre respeta las normas o no
     */
    public boolean nameCheck(String name){
        boolean correct;

        // Mostramos los límites por los que no puede pasar un nombre
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

        Matcher hasDigit = digit.matcher(name);
        Matcher hasSpecial = special.matcher(name);

        correct = !hasDigit.find() && !hasSpecial.find();

        return correct;
    }

    /**
     * Esta función revisará que, una vez el jugador recibe su experiencia, poder afirmar
     * que sube de nivel, o poder afirmar que aún no
     *
     * @param xp, valor que contendrá la experiencia del personaje
     * @param playerLevel, valor que contendrá el nivel actual del personaje
     * @return levelUp, bool que indicará si el jugador sube de nivel o no
     */
    public boolean levelUpCheck(int xp, int playerLevel){

        boolean levelUp = false;

        int pastLevel = revertXpToLevel(playerLevel);
        int actualLevel = revertXpToLevel(playerLevel + xp);
        int auxCheck = actualLevel - pastLevel;

        // Si la resta da un núm diferente a 0 significa que ha subido de nivel
        if(auxCheck != 0){
            levelUp = true;
        }
        return levelUp;
    }

    /**
     * Esta función genera una lista de los personajes que un propio jugador crea.
     * En caso que no coincida, devuelve la ArrayList vacía
     *
     * @param playerName, valor que contendrá el nombre del jugador
     * @return filteredCharacters, lista con todos los personajes que ha creado un jugador
     */
    public ArrayList<Character> filteredPlayers(String playerName, boolean isUsingAPI) throws IOException {
        ArrayList<Character> characters = null;

        if(isUsingAPI){
            characters = characterAPI.getFromUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_12/characters");
        }else{
            characters = characterDAO.readCharacterJSON();
        }
        ArrayList<Character> filteredCharacters = null;
        int i = 0;
        int j = 0;
        boolean noCoincidence = true;

        // A través del if, nos aseguramos de que se haya introducido algo
        if(!Objects.equals(playerName, "")){

            // Buscamos si el nombre del jugador coincide y lo guardamos en la variable i
            while(i < characters.size()){
                if(characters.get(i).getPlayerName().toLowerCase(Locale.ROOT).contains(playerName.toLowerCase(Locale.ROOT))){
                    j++;
                }
                i++;
            }

            // Añadimos los personajes en una nueva ArrayList
            filteredCharacters = new ArrayList<>(j);
            for (i = 0; i < j; i++) {
                filteredCharacters.add(characters.get(i));
            }

            i = 0;
            j = 0;

            // Nos aseguramos de que haya creado algún personaje, en caso contrario lo recalcamos
            while(i < characters.size()){
                if(characters.get(i).getPlayerName().toLowerCase(Locale.ROOT).contains(playerName.toLowerCase(Locale.ROOT))){
                    filteredCharacters.set(j, characters.get(i));
                    j++;
                    noCoincidence = false;
                }else{
                    if(j == 0){
                        noCoincidence = true;
                    }
                }
                i++;
            }
        }else{

            // Si no introduce un nombre, mostramos todos los personajes
            if(isUsingAPI) {
                filteredCharacters = getAPICharacters();
            }else{
                filteredCharacters = getAllCharacters();
            }
           noCoincidence = false;
        }

        // Si no tiene ningún personaje creado, le damos una ArrayList vacía
        if(noCoincidence){
            filteredCharacters = new ArrayList<>(1);
            filteredCharacters.add(null);
        }

        return filteredCharacters;
    }


    public String initialEvolution(String characterClass, int characterLevel){

        String evolution;

        if(characterClass.equals("Adventurer")){
            if(characterLevel > 0 && characterLevel < 4){
                evolution = "Adventurer";
            }else if(characterLevel > 3 && characterLevel < 8){
                evolution = "Warrior";
            }else{
                evolution = "Champion";
            }
        }else if(characterClass.equals("Cleric")){
            if(characterLevel > 0 && characterLevel < 5){
                evolution = "Cleric";
            }else{
                evolution = "Paladin";
            }
        }else{
            evolution = "Mage";
        }

        return evolution;
    }

    /**
     * Esta función devuelve una variable de tipo Character siguiendo el nombre
     * que se ha introducido, buscándolo por la ArrayList
     *
     * @param characterName, valor que contendrá el nombre del personaje
     * @return filteredCharacter, personaje resultante de la búsqueda
     */
    public Character getCharacterByName(String characterName, boolean isUsingApi) throws IOException {
        Character filteredCharacter = null;
        if(isUsingApi){
            ArrayList<Character> characters = characterAPI.getFromUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_12/characters");
            int i = 0;

            // A través del while buscamos si coincide el personaje deseado
            while(i < characters.size()){
                if(characters.get(i).getCharacterName().toLowerCase(Locale.ROOT).contains(characterName.toLowerCase(Locale.ROOT))){
                    filteredCharacter = characters.get(i);
                    i = characters.size();
                }
                i++;
            }
        }else{
            ArrayList<Character> characters = characterDAO.readCharacterJSON();
            int i = 0;

            // A través del while buscamos si coincide el personaje deseado
            while(i < characters.size()){
                if(characters.get(i).getCharacterName().toLowerCase(Locale.ROOT).contains(characterName.toLowerCase(Locale.ROOT))){
                    filteredCharacter = characters.get(i);
                    i = characters.size();
                }
                i++;
            }
        }

        return filteredCharacter;
    }

    /**
     * Esta función borrará un personaje existente de la lista
     *
     * @param characterName, valor que contendrá el nombre del personaje a borrar
     * @return devuelve un bool, siguiendo la función del objeto DAO conforme si se ha eliminado o no
     */
    public boolean deleteCharacter(String characterName){
        return characterDAO.deleteCharacterByName(characterName);
    }



    public boolean deleteCharacterAPI (String characterName) throws IOException {
        return characterAPI.deleteFromUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_12/characters?name=" + characterName);
    }

    /**
     * Esta función servirá para indicar que el nivel del personaje debe ser
     * actualizado desde el DAO
     *
     * @param character, valor que contendrá el nombre del personaje
     * @param gainedXp, valor que contendrá toda la experiencia ganada
     */
    public void levelUpdate(Character character, int gainedXp){
        characterDAO.updateCharacterLevel(character,gainedXp);
    }


    public void levelUpdateAPI(Character character, int gainedXp) throws IOException {
        characterAPI.updateToUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_12/characters", character,gainedXp);
    }
    public void emergencyDelete() throws IOException {
        characterAPI.deleteFromUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_12/characters");
    }

}
