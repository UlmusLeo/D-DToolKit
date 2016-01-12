package com.ulmus.ddtoolkit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Space;
import android.widget.TextView;

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

    int dieType;

    public DiceRollOverlay(int dieType){
        this.dieType = dieType;
    }


    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        String bonusText = null;
        if(v instanceof Button){
            bonusText = ((Button)v).getText().toString();
            System.out.println("Button Text:"+bonusText);
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View diceView =  inflater.inflate(R.layout.popup_dice_roll, null,false);

        final Button dieButton = (Button) diceView.findViewById(R.id.dice_roll_button);
        TextView rollBonusView = (TextView) diceView.findViewById(R.id.dice_bonus_view);

        dieButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                roll(dieButton,dieType);
            }
        });
        if(bonusText!=null){
            rollBonusView.setText(bonusText);
        }
        else{
            rollBonusView.setVisibility(View.GONE);
        }
        switch(dieType) {
            case D4_DIE:
                dieButton.setBackground(ContextCompat.getDrawable(context, R.drawable.d4));
                break;
            case D6_DIE:
                dieButton.setBackground(ContextCompat.getDrawable(context, R.drawable.d6));
                break;
            case D8_DIE:
                dieButton.setBackground(ContextCompat.getDrawable(context, R.drawable.d8));
                break;
            case D10_DIE:
                dieButton.setBackground(ContextCompat.getDrawable(context, R.drawable.d10));
                break;
            case D12_DIE:
                dieButton.setBackground(ContextCompat.getDrawable(context, R.drawable.d12));
                break;
            case D20_DIE:
                dieButton.setBackground(ContextCompat.getDrawable(context, R.drawable.d20));
                break;

        }
        rollBonusView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rollBonusView.setBackground(new ColorDrawable(Color.WHITE));

        int width=180 + (bonusText!=null ? rollBonusView.getMeasuredWidth() + 10:0) ;
        int height=180;
        PopupWindow characterStats = new PopupWindow(diceView , width,
                height, true);  //TODO calculate dimentions dynamically some how
        characterStats.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        characterStats.showAsDropDown(v, 0, 0);
        roll(dieButton,dieType);

    }
    private static void roll(Button button,int type){
        int result = (int)(Math.random()*type)+1;
        button.setText(Integer.toString(result));
    }


}
