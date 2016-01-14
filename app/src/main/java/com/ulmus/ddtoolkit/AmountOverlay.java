package com.ulmus.ddtoolkit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Created by Jake on 1/13/16.
 */
public class AmountOverlay implements View.OnClickListener {
    OnQuantityChangeListener quantityChangeListener;

    public AmountOverlay(OnQuantityChangeListener quantityChangeListener){
        this.quantityChangeListener = quantityChangeListener;
    }
    final int button0 = R.id.number_0_button;
    final int button1 = R.id.number_1_button;
    final int button2 = R.id.number_2_button;
    final int button3 = R.id.number_3_button;
    final int button4 = R.id.number_4_button;
    final int button5 = R.id.number_5_button;
    final int button6 = R.id.number_6_button;
    final int button7 = R.id.number_7_button;
    final int button8 = R.id.number_8_button;
    final int button9 = R.id.number_9_button;

    final int buttonEnt   = R.id.number_ent_button;
    final int buttonDel   = R.id.number_del_button;
    final int buttonPlus  = R.id.number_plus_button;
    final int buttonMinus = R.id.number_minus_button;


    @Override
    public void onClick(View v) {
        Context context = v.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View numberInputView =  inflater.inflate(R.layout.popup_dice_roll, null,false);

        Button b0 = (Button) numberInputView.findViewById(button0);
        Button b1 = (Button) numberInputView.findViewById(button1);
        Button b2 = (Button) numberInputView.findViewById(button2);
        Button b3 = (Button) numberInputView.findViewById(button3);
        Button b4 = (Button) numberInputView.findViewById(button4);
        Button b5 = (Button) numberInputView.findViewById(button5);
        Button b6 = (Button) numberInputView.findViewById(button6);
        Button b7 = (Button) numberInputView.findViewById(button7);
        Button b8 = (Button) numberInputView.findViewById(button8);
        Button b9 = (Button) numberInputView.findViewById(button9);

        Button bEnt   = (Button) numberInputView.findViewById(buttonEnt);
        Button bDel   = (Button) numberInputView.findViewById(buttonDel);
        Button bPlus  = (Button) numberInputView.findViewById(buttonPlus);
        Button bMinus = (Button) numberInputView.findViewById(buttonMinus);

        EditText fieldView = (EditText) numberInputView.findViewById(R.id.number_value_field);

        CalcButtonListener buttonListener = new CalcButtonListener(fieldView);
        
        b0.setOnClickListener(buttonListener);
        b1.setOnClickListener(buttonListener);
        b2.setOnClickListener(buttonListener);
        b3.setOnClickListener(buttonListener);
        b4.setOnClickListener(buttonListener);
        b5.setOnClickListener(buttonListener);
        b6.setOnClickListener(buttonListener);
        b7.setOnClickListener(buttonListener);
        b8.setOnClickListener(buttonListener);
        b9.setOnClickListener(buttonListener);

        bEnt.setOnClickListener(buttonListener);
        bDel.setOnClickListener(buttonListener);
        bPlus.setOnClickListener(buttonListener);
        bMinus.setOnClickListener(buttonListener);



        PopupWindow numberInput = new PopupWindow(numberInputView , 200,
                300, true);  //TODO calculate dimentions dynamically some how
        numberInput.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        numberInput.showAsDropDown(v, 0, 0);

    }

    private class CalcButtonListener implements View.OnClickListener{

        EditText linkedView;
        public CalcButtonListener(EditText linkedView) {
            this.linkedView = linkedView;
        }

        @Override
        public void onClick(View v) {
            String currentText;
            switch(v.getId()){
                case button0:  linkedView.append("0");    break;
                case button1:  linkedView.append("1");    break;
                case button2:  linkedView.append("2");    break;
                case button3:  linkedView.append("3");    break;
                case button4:  linkedView.append("4");    break;
                case button5:  linkedView.append("5");    break;
                case button6:  linkedView.append("6");    break;
                case button7:  linkedView.append("7");    break;
                case button8:  linkedView.append("8");    break;
                case button9:  linkedView.append("9");    break;
                case buttonEnt:
                    DecimalFormat df = new DecimalFormat("+#;-#");
                    int parsedValue = 0;

                    try {
                        parsedValue = df.parse(linkedView.getText().toString()).intValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    quantityChangeListener.OnQuantityChange(parsedValue);
                    break;
                case buttonDel:
                    currentText = linkedView.getText().toString();
                    if(currentText.equals("+") || currentText.equals("-"))
                        return;
                    currentText = currentText.substring(0,currentText.length()-2);
                    linkedView.setText(currentText);
                    break;
                case buttonPlus:
                    currentText = linkedView.getText().toString();
                    if(currentText.startsWith("+"))
                        return;
                    currentText = currentText.replace('-','+');
                    linkedView.setText(currentText);
                    break;
                case buttonMinus:
                    currentText = linkedView.getText().toString();
                    if(currentText.startsWith("-"))
                        return;
                    currentText = currentText.replace('+','-');
                    linkedView.setText(currentText);
                    break;
            }
        }
    }
}
