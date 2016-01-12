package com.ulmus.ddtoolkit;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This is a class that creates a overlay as a result of a button press of a dice
 * being rolled a d4 d6 d8 d10 d12 d20 along with showing a bonus associated with
 * that roll if applicable
 * The concept of this class is: - means not implemented + means implemented
 * -Rerolling if the player chooses to
 * -Showing the bonus associated with the roll
 * -Animating a roll
 * -Animating the expansion from the button press with an expanding image of the die
 * -Returning the result of the roll to the caller when applicable for times like a
 *     level up script etc
 * Created by Jake on 1/12/16.
 */
public class DiceRollOverlay implements OnClickListener{
    public static final int D20_DIE = 20;
    public static final int D12_DIE = 12;
    public static final int D10_DIE = 10;
    public static final int D8_DIE = 8;
    public static final int D6_DIE = 6;
    public static final int D4_DIE = 4;

    int die_type;
    @Override
    public void onClick(View v) {
        if(v.getClass().equals(Button.class)){
            String buttonText = ((Button)v).getText().toString();
            System.out.println("Button Text:"+buttonText);
        }


    }
}
