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

    public int maxHP; //The maximum health of the enemy, the basis for their survivability. Winning a battle depends on this value reaching 0
    public int maxMana; //Maximum Mana of the enemy, used for abilities. Higher mama -> higher ability usage rate. Not all abilities use Mana.
    public int maxArmor; //The maximum armor of an enemy. A buff that applies directly to the health and reduces the damage taken by a percentile.
    public int maxShield; //maximum shields of the enemy. A regenerative 'health' bar that will constantly replenish and negate damage from the health of the enemy.
    public int level; //Determines the 'level' of the enemy. Higher level enemies are supposedly stronger than lower level enemies.
    public int expReward; //Determines the base amount of experience earned for killing this enemy.
    public int goldReward; //Determines amount of high tier currency earned for defeating this enemy.
    public int silverReward; //Determines amount of mid tier currency earned for killing this enemy.
    public int copperReward; //Determines amount of low tier currency earned for killing this enemy.

    // --------------------- DOUBLE VARIABLE DECLARATIONS -------------------------------------------------------------------------------------------------------------

    public double difficultyMultiplier = 1.0; //Determines the base multiplier applied to the enemy's stats. 1 is the default.
    public double shieldRecoveryRate = 1.0; //Determines the multiplier applied to shield recovery. A higher value indicates a faster shield recovery rate. Defaults to 1.0x.

    // --------------------- BOOLEAN VARIABLE DECLARATIONS ------------------------------------------------------------------------------------------------------------

    public boolean attacksFirst = false; //Determines whether or not the enemy has the advantage in the first. Defaults to false.
    public boolean canHeal = false; //Determines whether or not the enemy is allowed to use healing abilities. Disabled by default.
    public boolean canUseAbilities = false; //Determines whether or not the enemy is able to use normal abilities (this includes healing abilities). Disabled by default.

    // --------------------- STRING DECLARATIONS ----------------------------------------------------------------------------------------------------------------------

    public String name; //String that defines the name of the enemy.

    // --------------------- ARRAY DECLARATIONS -----------------------------------------------------------------------------------------------------------------------

    public int[] attackIDs = {}; //Array that handles the IDs of the attacks that the enemy can use, attacks are stored in their own class.
    public int[] abilityIDs = {}; //Array for storing the ID numbers of the different abilities that the enemy may use. Abilities are stored in their own class.

    // --------------------- PRIMARY CONSTRUCTOR ----------------------------------------------------------------------------------------------------------------------

    //Creates a constructor for the basic enemy type, with no unique modifiers.
    //TAGS: [combat] [constructor] [enemy]
    public Enemy(String name, int maxHealth, int maxMana, int maxArmor, int maxShield, int level, int expReward, int goldReward, int silverReward, int copperReward, double difficultyMultiplier, double shieldRecoveryRate, boolean attacksFirst, boolean canHeal, boolean canUseAbilities) {
        this.name = name;
        this.maxHP = maxHealth;
        this.maxMana = maxMana;
        this.maxArmor = maxArmor;
        this.maxShield = maxShield;
        this.level = level;
        this.expReward = expReward;
        this.goldReward = goldReward;
        this.silverReward = silverReward;
        this.copperReward = copperReward;
        this.difficultyMultiplier = difficultyMultiplier;
        this.shieldRecoveryRate = shieldRecoveryRate;
        this.attacksFirst = attacksFirst;
        this.canHeal = canHeal;
        this.canUseAbilities = canUseAbilities;
    }

    // --------------------- PRIMARY METHODS --------------------------------------------------------------------------------------------------------------------------


} //Close enemy class.