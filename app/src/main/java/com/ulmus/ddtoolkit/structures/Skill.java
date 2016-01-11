package com.ulmus.ddtoolkit.structures;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jake on 12/23/15.
 */
public class Skill implements Parcelable {
    private String name;
    private int rank;
    private boolean inClass;
    private boolean trained;
    private int abilityType;
    private int otherModifier;
    public Skill(String name, int ability_type){
        populateSkill(name,false,ability_type,0,0,false);
    }
    public Skill(String name,boolean in_class, int ability_type){
        populateSkill(name,in_class,ability_type,0,0,false);
    }
    public Skill(String name, boolean in_class,int ability_type, int rank){
        populateSkill(name,in_class,ability_type,0,rank,false);
    }
    public Skill(String name, boolean in_class,int ability_type, int rank, boolean trained){
        populateSkill(name,in_class,ability_type,0,rank,trained);
    }
    public Skill(String name, boolean in_class, int ability_type, int other_modifier, int rank, boolean trained){
        populateSkill(name,in_class,ability_type,other_modifier,rank,trained);
    }

    protected Skill(Parcel in) {
        name = in.readString();
        rank = in.readInt();
        inClass = in.readByte() != 0;
        trained = in.readByte() != 0;
        abilityType = in.readInt();
        otherModifier = in.readInt();
    }

    public static final Creator<Skill> CREATOR = new Creator<Skill>() {
        @Override
        public Skill createFromParcel(Parcel in) {
            return new Skill(in);
        }

        @Override
        public Skill[] newArray(int size) {
            return new Skill[size];
        }
    };

    private void populateSkill(String name, boolean in_class, int ability_type, int other_modifier, int rank, boolean trained){
        this.name = name;
        this.inClass = in_class;
        this.abilityType = ability_type;
        this.otherModifier = other_modifier;
        this.rank = rank;
        this.trained = trained;
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public String getRankToString(){
        if(inClass)
            return Integer.toString(rank);
        if(rank % 2 == 0)
            return Integer.toString(rank/2);

        if(rank/2 == 0)
            return "1/2";

        return rank/2 + " 1/2";
    }

    public boolean isInClass() {
        return inClass;
    }

    public int getBonus(Abilities abilities){

        int skill_bonus = (inClass ? rank: rank/2 ) + otherModifier;
        if(trained && rank == 0)
            return 0;

        return skill_bonus + abilities.getAbilityBonus(abilityType);
    }

    public String getBonusToString(Abilities abilities){
        int bonus = getBonus(abilities);
        return (bonus >= 0 ? "+":"-") + Integer.toString(Math.abs(bonus));
    }

    public boolean isTrained() {
        return trained;
    }

    public int getAbilityType() {
        return abilityType;
    }

    public String getAbilityTypeToString() {
        return Abilities.getAbilityString(abilityType);
    }

    public String getOtherModifierToString(){
        int bonus = getOtherModifier();
        return (bonus >= 0 ? "+":"-") + Integer.toString(Math.abs(bonus));
    }

    public int getOtherModifier() {
        return otherModifier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setInClass(boolean inClass) {
        this.inClass = inClass;
    }

    public void setTrained(boolean trained) {
        this.trained = trained;
    }

    public void setAbilityType(int abilityType) {
        this.abilityType = abilityType;
    }

    public void setOtherModifier(int otherModifier) {
        this.otherModifier = otherModifier;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(rank);
        parcel.writeByte((byte) (inClass ? 1 : 0));
        parcel.writeByte((byte) (trained ? 1 : 0));
        parcel.writeInt(abilityType);
        parcel.writeInt(otherModifier);
    }
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append("  "+otherModifier+" + ");
        builder.append(getRankToString() + " + ");
        builder.append(getAbilityTypeToString());
        builder.append((inClass ? " in class " : " cross class "));
        builder.append((trained ? "trained" : "untrained"));
        return builder.toString();
    }
}
