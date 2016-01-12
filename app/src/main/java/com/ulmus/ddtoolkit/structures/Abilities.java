package com.ulmus.ddtoolkit.structures;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class is a holder class for a character's abilities.
 * It also does some computation of bonus for each ability
 *
 * Created by Jake on 12/18/15.
 */
public class Abilities implements Parcelable {

    public static final int STR = 1;
    public static final int DEX = 2;
    public static final int CON = 3;
    public static final int INT = 4;
    public static final int WIS = 5;
    public static final int CHA = 6;

    private int strVal,dexVal,conVal,intVal,wisVal,chaVal;
    private int strMisc,dexMisc,conMisc,intMisc,wisMisc,chaMisc;



    public static String getAbilityString(int ability){
        switch(ability){
            case STR:
                return "STR";
            case DEX:
                return "DEX";
            case CON:
                return "CON";
            case INT:
                return "INT";
            case WIS:
                return "WIS";
            case CHA:
                return "CHA";
            default:
                return "UNKNOWN";
        }
    }

    public Abilities(int strVal, int dexVal, int conVal, int intVal, int wisVal, int chaVal){
        this.strVal = strVal;
        this.dexVal = dexVal;
        this.conVal = conVal;
        this.intVal = intVal;
        this.wisVal = wisVal;
        this.chaVal = chaVal;
    }

    protected Abilities(Parcel in) {
        strVal = in.readInt();
        dexVal = in.readInt();
        conVal = in.readInt();
        intVal = in.readInt();
        wisVal = in.readInt();
        chaVal = in.readInt();

        strMisc = in.readInt();
        dexMisc = in.readInt();
        conMisc = in.readInt();
        intMisc = in.readInt();
        wisMisc = in.readInt();
        chaMisc = in.readInt();
    }

    public static final Creator<Abilities> CREATOR = new Creator<Abilities>() {
        @Override
        public Abilities createFromParcel(Parcel in) {
            return new Abilities(in);
        }

        @Override
        public Abilities[] newArray(int size) {
            return new Abilities[size];
        }
    };

    private static int calculateBonus(int statVal) {
        if (statVal > 9)
            return (statVal - 10) / 2;
        return (statVal - 11) / 2;
    }

    public int getAbilityBonus(int type){
        switch(type){
            case STR:
                return getStrBonus();
            case DEX:
                return getDexBonus();
            case CON:
                return getConBonus();
            case WIS:
                return getWisBonus();
            case INT:
                return getIntBonus();
            case CHA:
                return getChaBonus();
        }
        return 0;
    }

    public int getStrVal() {
        return strVal;
    }

    public int getDexVal() {
        return dexVal;
    }

    public int getConVal() {
        return conVal;
    }

    public int getIntVal() {
        return intVal;
    }

    public int getWisVal() {
        return wisVal;
    }

    public int getChaVal() {
        return chaVal;
    }

    public int getStrMisc() {
        return strMisc;
    }

    public int getDexMisc() {
        return dexMisc;
    }

    public int getConMisc() {
        return conMisc;
    }

    public int getIntMisc() {
        return intMisc;
    }

    public int getWisMisc() {
        return wisMisc;
    }

    public int getChaMisc() {
        return chaMisc;
    }

    public int getStrBonus() {
        return calculateBonus(strVal);
    }

    public int getDexBonus() {
        return calculateBonus(dexVal);
    }

    public int getIntBonus() {
        return calculateBonus(intVal);
    }

    public int getConBonus() {
        return calculateBonus(conVal);
    }

    public int getWisBonus() {
        return calculateBonus(wisVal);
    }

    public int getChaBonus() {
        return calculateBonus(chaVal);
    }



    public void setStrVal(int strVal) {
        this.strVal = strVal;
    }

    public void setDexVal(int dexVal) {
        this.dexVal = dexVal;
    }

    public void setConVal(int conVal) {
        this.conVal = conVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    public void setWisVal(int wisVal) {
        this.wisVal = wisVal;
    }

    public void setChaVal(int chaVal) {
        this.chaVal = chaVal;
    }

    public void setStrMisc(int strMisc) {
        this.strMisc = strMisc;
    }

    public void setDexMisc(int dexMisc) {
        this.dexMisc = dexMisc;
    }

    public void setConMisc(int conMisc) {
        this.conMisc = conMisc;
    }

    public void setIntMisc(int intMisc) {
        this.intMisc = intMisc;
    }

    public void setWisMisc(int wisMisc) {
        this.wisMisc = wisMisc;
    }

    public void setChaMisc(int chaMisc) {
        this.chaMisc = chaMisc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(strVal);
        parcel.writeInt(dexVal);
        parcel.writeInt(conVal);
        parcel.writeInt(intVal);
        parcel.writeInt(wisVal);
        parcel.writeInt(chaVal);

        parcel.writeInt(strMisc);
        parcel.writeInt(dexMisc);
        parcel.writeInt(conMisc);
        parcel.writeInt(intMisc);
        parcel.writeInt(wisMisc);
        parcel.writeInt(chaMisc);
    }
}
