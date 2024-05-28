package org.application.IA.IA_Palkia.Utility;

import lombok.Data;
import org.application.IA.IA_Palkia.Research.CloseEnemy.CloseEnemy;
import org.application.model.Block;

@Data
public class FindLockingPath {
    private static FindLockingPath instance = null;

    // ----- CONSTRUCTOR -----
    private FindLockingPath() {}
    public static FindLockingPath getInstance() {
        if (instance == null) {
            instance = new FindLockingPath();
        }
        return instance;
    }

    // ----- METHODS -----
    public boolean evaluate(Block[][] blocks, int playerHead) {
        // Valuto se ci sta un path di chiusura oppure no
        if(CloseEnemy.getInstance().isEnemyCloseable(blocks, 7, playerHead))
            return true;
        else
            return false;
    }
}
