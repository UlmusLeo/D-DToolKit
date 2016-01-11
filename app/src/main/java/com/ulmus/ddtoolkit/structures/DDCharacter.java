package com.ulmus.ddtoolkit.structures;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This is a class that manages a D&D character
 * Created by Jake on 12/17/15.
 */


public class DDCharacter implements Parcelable{

    public static final String DD_CHARACTER_KEY = "DD_CHARACTER_KEY FOR JLU DDTOOLKIT";
    private String name;
    private int level;

    //base stats
    private Abilities abilities;

    //combat stats
    private int hp_total;
    private int hp_current;
    private int hit_die;


    public static final int SIZE_COLASSAL = -8;
    public static final int SIZE_GARGANTUAN = -4;
    public static final int SIZE_HUGE = -2;
    public static final int SIZE_LARGE = 1;
    public static final int SIZE_MEDIUM = 0;
    public static final int SIZE_SMALL = 1;
    public static final int SIZE_TINY = 2;
    public static final int SIZE_DIMINUTIVE = 4;
    public static final int SIZE_FINE = 8;

    private int ac_armor_bonus;
    private int ac_shield_bonus;
    private int ac_size_modifier;

    private int fort_base;
    private int fort_bonus;
    private int reflex_base;
    private int reflex_bonus;
    private int will_base;
    private int will_bonus;

    private int base_attack;

    private int skill_per_level_base;
    private int skill_total_points;
    private SkillList skills = new SkillList();

    public DDCharacter(String name, int strVal, int dexVal, int conVal, int intVal, int wisVal, int chaVal) {
        this.name = name;
        abilities = new Abilities(strVal,dexVal,conVal,intVal,wisVal,chaVal);
    }
    public DDCharacter(Parcel in){
        name = in.readString();
        level = in.readInt();
        hp_total = in.readInt();
        hp_current = in.readInt();
        hit_die = in.readInt();
        ac_armor_bonus = in.readInt();
        ac_shield_bonus = in.readInt();
        ac_size_modifier = in.readInt();
        fort_base = in.readInt();
        fort_bonus = in.readInt();
        reflex_base = in.readInt();
        reflex_bonus = in.readInt();
        will_base = in.readInt();
        will_bonus = in.readInt();
        base_attack = in.readInt();
        skill_per_level_base = in.readInt();
        skill_total_points = in.readInt();

        abilities = (Abilities) in.readParcelable(Abilities.class.getClassLoader());
        skills = (SkillList) in.readParcelable(SkillList.class.getClassLoader());
    }

    public static final Creator<DDCharacter> CREATOR = new Creator<DDCharacter>() {
        @Override
        public DDCharacter createFromParcel(Parcel in) {
            return new DDCharacter(in);
        }

        @Override
        public DDCharacter[] newArray(int size) {
            return new DDCharacter[size];
        }
    };

    public SkillList getSkillList(){
        return skills;
    }

    public Abilities getAbilities(){
        return abilities;
    }
    public void setHpTotal(int hp_total){
        this.hp_total = hp_total;
    }
    public int  getHpTotal(){
        return hp_total;
    }
    public void setHpCurrent(int hp){
        hp_current = hp;
    }
    public void modifyHPCurrent(int amount){
        hp_current += amount;
    }
    public int getHPCurrent(){
        return hp_current;
    }

    public int getHitDie() {
        return hit_die;
    }

    public void setHitDie(int hit_die) {
        this.hit_die = hit_die;
    }

    public int getAcArmorBonus() {
        return ac_armor_bonus;
    }

    public void setAcArmorBonus(int ac_armor_bonus) {
        this.ac_armor_bonus = ac_armor_bonus;
    }

    public int getAcShieldBonus() {
        return ac_shield_bonus;
    }

    public void setAcShieldBonus(int ac_shield_bonus) {
        this.ac_shield_bonus = ac_shield_bonus;
    }

    public int getAcSizeModifier() {
        return ac_size_modifier;
    }

    public void setAcSizeModifier(int ac_size_modifier) {
        this.ac_size_modifier = ac_size_modifier;
    }


    public void setWillBonus(int will_bonus) {
        this.will_bonus = will_bonus;
    }

    public int getBaseAttack() {
        return base_attack;
    }

    public void setBaseAttack(int base_attack) {
        this.base_attack = base_attack;
    }

    public int getSkillPerLevelBase() {
        return skill_per_level_base;
    }

    public void setSkillPerLevelBase(int skill_per_level_base) {
        this.skill_per_level_base = skill_per_level_base;
    }

    public int getSkillTotalPoints() {
        return skill_total_points;
    }

    public void setSkillTotalPoints(int skill_total_points) {
        this.skill_total_points = skill_total_points;
    }

    public int getArmorClass(int dodge_modifier) {
        return ac_armor_bonus + 10 + abilities.getAbilityBonus(Abilities.DEX) + ac_size_modifier + dodge_modifier;
    }

    public int getMeeleAttackBonus(){
        return base_attack + abilities.getAbilityBonus(Abilities.STR) + ac_size_modifier;
    }
    public int getRangedAttackBonus(int range_penalty){
        return base_attack + abilities.getAbilityBonus(Abilities.DEX) + ac_size_modifier + range_penalty;
    }

    /**
     * *******************************************************
     * **************** Saving Throws **************************
     * ********************************************************
     */
    public int getFortBase() {
        return fort_base;
    }

    public void setFortBase(int fort_base) {
        this.fort_base = fort_base;
    }

    public int getFortBonus() {
        return fort_bonus;
    }

    public void setFortBonus(int fort_bonus) {
        this.fort_bonus = fort_bonus;
    }

    public int getFortSave() {
        return fort_bonus + fort_base + abilities.getAbilityBonus(Abilities.CON);
    }

    public int getReflexBase() {
        return reflex_base;
    }

    public void setReflexBase(int reflex_base) {
        this.reflex_base = reflex_base;
    }

    public int getReflexBonus() {
        return reflex_bonus;
    }

    public void setReflexBonus(int reflex_bonus) {
        this.reflex_bonus = reflex_bonus;
    }

    public int getReflexSave() {
        return reflex_bonus + reflex_base + abilities.getAbilityBonus(Abilities.DEX);
    }

    public int getWillBase() {
        return will_base;
    }

    public void setWillBase(int will_base) {
        this.will_base = will_base;
    }

    public int getWillBonus() {
        return will_bonus;
    }

    public int getWillSave() {
        return will_bonus + will_base + abilities.getAbilityBonus(Abilities.WIS);
    }

    /**
     * *******************************************************
     * **************** Basic Info Getter Setters **************
     * ********************************************************
     */
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addSkill(String name, boolean in_class, int ability_type, int other_modifier, int rank, boolean trained){
        skills.addSkill(name,in_class,ability_type,other_modifier,rank,trained);
    }


    public static void main(String args[]) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(flags);
        out.writeString(name);
        out.writeInt(level);
        out.writeInt(hp_total);
        out.writeInt(hp_current);
        out.writeInt(hit_die);
        out.writeInt(ac_armor_bonus);
        out.writeInt(ac_shield_bonus);
        out.writeInt(ac_size_modifier);
        out.writeInt(fort_base);
        out.writeInt(fort_bonus);
        out.writeInt(reflex_base);
        out.writeInt(reflex_bonus);
        out.writeInt(will_base);
        out.writeInt(will_bonus);
        out.writeInt(base_attack);
        out.writeInt(skill_per_level_base);
        out.writeInt(skill_total_points);

        out.writeParcelable(abilities,0);
        out.writeParcelable(skills,0);
    }
}
