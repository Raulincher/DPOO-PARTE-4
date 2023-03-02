import business.AdventureManager;
import business.CharacterManager;
import business.MonsterManager;
import persistance.*;
import presentation.UIController;
import presentation.UIManager;
import java.io.File;
import java.io.IOException;

public class Main {

    /**
     * Función padre para hacer funcionar el programa
     */
    public static void main(String[] args) throws IOException {

        // Persistance
        MonsterDAO monsterDAO = new MonsterDAO();
        CharacterDAO characterDAO = new CharacterDAO();
        AdventureDAO adventureDAO = new AdventureDAO();
        MonsterAPI monsterAPI = new MonsterAPI();
        CharacterAPI characterAPI = new CharacterAPI();
        AdventureAPI adventureAPI = new AdventureAPI();

        // Business
        CharacterManager characterManager = new CharacterManager(characterDAO, characterAPI);
        AdventureManager adventureManager = new AdventureManager(adventureDAO, characterManager, adventureAPI);
        MonsterManager monsterManager = new MonsterManager(monsterDAO, monsterAPI);

        // Presentation
        UIManager uiManager = new UIManager();
        UIController uiController = new UIController(uiManager, adventureManager, characterManager, monsterManager);

        uiController.run();

    }

}
