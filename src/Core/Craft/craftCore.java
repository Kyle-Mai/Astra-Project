package Core.Craft;

import Core.SFX.audioRepository;
import Core.gameSettings;
import Core.starClass;

import java.awt.image.BufferedImage;

/**
 * KM
 * June 03 2017
 * Handles the primary spacecraft data used by all craft.
 */

/* Abstract/Interface reference sheet: (Summarized by myself using information from SO and Java API)

ABSTRACT: -------------------------------------

- Abstract method : Needs to be implemented by the subclass inheriting from the abstract class. Subclass must contain the method call so the program knows what to do with it.
- Final method : Method that CANNOT be overwritten by the subclass. No other implementations are allowed.
- Regular method : Method that can be overwritten by the subclass, but doesn't need to be.

Can create a new object of the superclass via [ AbstractClass x = new InheritingSubclass(); ] and it will behave with all of the overwrites.
With this, if we want to access a member specific to the subclass, we must cast down to it first via [ ((InheritingSubclass)x).subclassMethod(); ]

CANNOT extend multiple classes without some crazy Java guru that I learned on SO via [ public class <x, a extends b & c> { } ] Spooky!
Generally though, multiple class extension is disallowed to prevent instances of equivalent methods in both of the extended classes - Java doesn't know what to do in those instances.

INTERFACES: -----------------------------------

Interfaces via the 'implements' modifier [ public class InheritingSubclass extends AbstractClass implements InterfaceClassA, InterfaceClassB, InterfaceClassX { } ]
Interfaces cannot implement methods, and all interface methods must be implemented by the class that calls them.

Interfaces are considered the same as a 'public abstract' declaration.

Can declare an object of the Interface and use one of the subclasses as the declaration via [ InterfaceA x = new InterfaceInheritorA(); ]
The object would then use the method calls present in the subclass. [ x.SuperclassMethod(); ] will return whatever the inheritor class has for the implementation.

The differences between abstract and interfaces are:

- Interfaces cannot implement methods on their own. They can declare them, but they cannot have a method body. [ void interfaceMethod(); ] is valid, [ void interfaceMethod() { do stuff here } ] is not.
- Subclasses can implement as many interfaces as it wants, but only extend one class.

 */

public abstract class craftCore {

        private double speed; //number of turns it takes for the ship to travel a tile
        private int range; //maximum number of tiles this craft can travel through FTL
        private double buildCost; //how many resources this craft takes to build
        private int buildTime; //number of turns it takes to build the craft
        private double maintenanceCost; //upkeep cost of the ship
        private String shipGFXDir; //gfx image directory
        private BufferedImage shipGFX; //ship's GFX image
        private String craftName; //ship's name
        private boolean isActive; //whether or not the ship can be given orders
        private boolean inInterstellarSpace;
        private int maxHealth;
        private int health; //how much health the ship has
        private int timeUntilAction = 0; //how many turns until the ship can perform the next action
        private int mapX, mapY; //ship's location on the map
        private int systemX, systemY; //ship's location in the system
        private int moveToX, moveToY; //used to store the location the ship is moving to on the map

        public craftCore(String name, double speed, int range, int health, double buildCost, int buildTime, double maintenanceCost, String shipGFXDir) {
            this.speed = speed;
            this.craftName = name;
            this.range = range;
            this.buildCost = buildCost;
            this.buildTime = buildTime;
            this.maintenanceCost = maintenanceCost;
            this.shipGFXDir = shipGFXDir;
            this.maxHealth = health;
            this.health = health;
            this.isActive = false;

        }

        public final void ftlJump(int distance) {
            if (this.isInRange(distance) && this.isActive) {
                this.isActive = true;
                this.inInterstellarSpace = true;
                int duration = gameSettings.currentDate + (int)Math.max((range * speed), 1);
                playAudioMove();
                this.timeUntilAction = duration;
            } else {
                audioRepository.gameInvalid();
            }
        }

        abstract void performAction(); //determines what the ship is doing

        public void idleTick() { //checked every turn to determine whether or not the ship can perform actions
            if (this.isActive() && this.getTimeUntilAction() > 0) {
                timeUntilAction--;
            } else if (this.isActive() && getTimeUntilAction() <= 0) {
                timeUntilAction = 0;
                this.toggleActive();
                System.out.println("Ship finished performing an action.");
                this.actionComplete();

                if (isInInterstellarSpace()) { //ship was moving through FTL
                    this.inInterstellarSpace = false;
                    this.mapX = moveToX;
                    this.mapY = moveToY;
                }

            }
        }

        abstract void actionComplete(); //indicates an action is finished being performed

        abstract void playAudioMove(); //audio that plays when the craft moves
        abstract void playAudioSelected(); //audio that plays when the craft is selected

        private boolean isInRange(int distance) {
            return (distance <= this.range);
        }

        //getter methods
        public final int getHealth() { return this.health; }
        public final int getBuildTime() { return this.buildTime; }
        public final double getBuildCost() { return this.buildCost; }
        public final String getShipGFXDir() { return this.shipGFXDir; }
        public final int getRange() { return this.range; }
        public final double getSpeed() { return this.speed; }
        public final BufferedImage getShipGFX() { return  this.shipGFX; }
        public final String getCraftName() { return this.craftName; }
        public final boolean isActive() { return this.isActive; }
        public final int getTimeUntilAction() { return this.timeUntilAction; }
        public final boolean isInInterstellarSpace() { return this.inInterstellarSpace; }
        public final int getMapX() { return this.mapX; }
        public final int getMapY() { return this.mapY; }

        //setter methods
        public final void setHealth(int hp) { //sets the health of the ship
            this.health = hp;
            if (this.health > this.maxHealth) {
                this.health = this.maxHealth;
            } else if (this.health < 0) {
                this.health = 0;
            }
        }

        public final void toggleActive() { this.isActive = !this.isActive; }
        public final void setShipGFX(BufferedImage gfx) { this.shipGFX = gfx; }
        public final void setSpeed(double speed) { this.speed = speed; }
        public final void setBuildCost(double cost) { this.buildCost = cost; }
        public final void setCraftName(String name) { this.craftName = name; }
        public final void setBuildTime(int time) { this.buildTime = time; }
        public final void setRange(int range) { this.range = range; }
        public final void setMaintenanceCost(double cost) { this.maintenanceCost = cost; }
        public final void setTimeUntilAction(int time) { this.timeUntilAction = time; }
        public final void setMapLocation(int x, int y) { this.mapX = x; this.mapY = y; } //sets the map location of the ship
        public final void moveToLocation(int x, int y) { this.moveToX = x; this.moveToY = y; }

}
