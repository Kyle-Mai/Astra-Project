package EnemyData;

/**
 KM
 February-March 2017
 Class to handle the basis of every enemy in the game. This data is used by both the AI and general battle structure.
 */

public class Enemy {

    /**
     Class for defining the data structure of the basic enemies along with some basic methodology.
     Bosses and special enemies may branch off and add to this class, this class only includes the basic blueprint
     Therefore, this blueprint applies to ALL enemy types, and the information here is used by all of them
     Enemy specific data will be defined separately in the object declarations
     */

    // --------------------- INTEGER VARIABLE DECLARATIONS ------------------------------------------------------------------------------------------------------------

    int enemyMaxHP; //The maximum health of the enemy, the basis for their survivability. Winning a battle depends on this value reaching 0
    int enemyMaxMana; //Maximum Mana of the enemy, used for abilities. Higher mama -> higher ability usage rate. Not all abilities use Mana.
    int enemyMaxArmor; //The maximum armor of an enemy. A buff that applies directly to the health and reduces the damage taken by a percentile.
    int enemyMaxShield; //maximum shields of the enemy. A regenerative 'health' bar that will constantly replenish and negate damage from the health of the enemy.
    int enemyLevel; //Determines the 'level' of the enemy. Higher level enemies are supposedly stronger than lower level enemies.
    int experienceReward; //Determines the base amount of experience earned for killing this enemy.
    int goldReward; //Determines the base amount of currency earned for killing this enemy.

    // --------------------- DOUBLE VARIABLE DECLARATIONS -------------------------------------------------------------------------------------------------------------

    double difficultyMultiplier = 1.0; //Determines the base multiplier applied to the enemy's stats. 1 is the default.
    double shieldRecoveryRate = 1.0; //Determines the multiplier applied to shield recovery. A higher value indicates a faster shield recovery rate. Defaults to 1.0x.

    // --------------------- BOOLEAN VARIABLE DECLARATIONS ------------------------------------------------------------------------------------------------------------

    boolean attacksFirst = false; //Determines whether or not the enemy has the advantage in the first. Defaults to false.
    boolean canHeal = false; //Determines whether or not the enemy is allowed to use healing abilities. Disabled by default.
    boolean canUseAbilities = false; //Determines whether or not the enemy is able to use normal abilities (this includes healing abilities). Disabled by default.

    // --------------------- STRING DECLARATIONS ----------------------------------------------------------------------------------------------------------------------

    String enemyName; //String that defines the name of the enemy.

    // --------------------- ARRAY DECLARATIONS -----------------------------------------------------------------------------------------------------------------------

    int[] attackIDs = {}; //Array that handles the IDs of the attacks that the enemy can use, attacks are stored in their own class.
    int[] abilityIDs = {}; //Array for storing the ID numbers of the different abilities that the enemy may use. Abilities are stored in their own class.

    // --------------------- PRIMARY METHODS --------------------------------------------------------------------------------------------------------------------------

    //TAGS: [combat] [status] [check]
    public boolean enemyIsAlive(int enemyHP){ //Determines whether or not the enemy is still alive or not.

        if (enemyHP > 0) { //If the enemy's health is greater than 0, return true (they are alive). Otherwise, return false (they are dead).
            return true;
        }
        else {
            return false;
        }

    } //Close enemyIsAlive method.




} //Close enemy class.