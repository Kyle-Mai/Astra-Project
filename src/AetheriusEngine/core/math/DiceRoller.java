package AetheriusEngine.core.math;

import java.util.ArrayList;
import java.util.Random;

/*
Lolita's Revenge
July 01 2017

Rolls different types of dice.
 */

public class DiceRoller implements DiceConstants {

    /*------------------------------------------------------------------------------------------------------------------
     Variables.
     Used to determine the function of the class.
     */

    private ArrayList<Integer> rolledDice = new ArrayList<>();

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the dice roller
     */

    public DiceRoller() {}

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Methods that can be accessed from outside the dice roller class
     */

    public int roll(int type) { return rollDice(type); } //Rolls a single die.

    public int roll(int type, int num) { //Rolls multiple dice.
        if (num < 1) throw new IllegalArgumentException();
        int sum = 0;
        for (int i = 0; i < num; i++) { sum += rollDice(type); }
        return sum;
    }

    public int getLastRoll() {
        if (rolledDice.size() < 1) throw new NullPointerException();
        return rolledDice.get(rolledDice.size() - 1);
    }

    public int getRoll(int i) { return rolledDice.get(i); }

    public void reset() { rolledDice.clear(); }

    public int getSum() { //Gets the sum of all of the rolls
        int sum = 0;
        for (int i = 0; i < rolledDice.size(); i++) { sum += rolledDice.get(i); }
        return sum;
    }

    public int getSum(int i1, int i2) { //Gets the sum of all of the rolls in a specified range
        if (i1 >= rolledDice.size() || i1 < 0 || i1 > i2) throw new IllegalArgumentException();
        if (i2 >= rolledDice.size() || i2 < 0 || i2 < i1) throw new IllegalArgumentException();
        int sum = 0;
        for (int i = i1; i < i2; i++) { sum += rolledDice.get(i); }
        return sum;
    }

    /*------------------------------------------------------------------------------------------------------------------
     Internals
     Methods used by the dice roller that generally shouldn't be changed or accessed.
     */

    private int rollDice(int type) {
        if (type != D4 && type != D6 && type != D8 && type != D10 && type != D12 && type != D20 && type != D100) throw new IllegalArgumentException();
        Random r = new Random();
        int roll = 1 + r.nextInt(type - 1);
        rolledDice.add(roll);
        return roll;
    }

    //------------------------------------------------------------------------------------------------------------------

}
