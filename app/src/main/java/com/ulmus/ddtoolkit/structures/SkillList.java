package com.ulmus.ddtoolkit.structures;

import android.os.Parcel;
import android.os.Parcelable;

import com.ulmus.ddtoolkit.structures.Abilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is a class that holds the list of skills and will return the bonus for a individual skill
 * Created by Jake on 12/18/15.
 */
public class SkillList implements Parcelable {
    private HashMap<String, Integer> skill_map;
    private List<Skill> skills;


    public SkillList(){
        skill_map = new HashMap<String,Integer>();
        skills = new ArrayList<Skill>(15);
    }

    public SkillList(Parcel in){

        skills = in.createTypedArrayList(Skill.CREATOR);

        skill_map = new HashMap<String,Integer>();
        for(int i = 0; i < skills.size(); i++){
            skill_map.put(skills.get(i).getName(),i);
        }

    }

    public static final Creator<SkillList> CREATOR = new Creator<SkillList>() {
        @Override
        public SkillList createFromParcel(Parcel in) {
            return new SkillList(in);
        }

        @Override
        public SkillList[] newArray(int size) {
            return new SkillList[size];
        }
    };
    public int getCount(){
        return skills.size();
    }

    public void addSkill(String name, boolean in_class, int ability_type, int other_modifier, int rank, boolean trained){
        skill_map.put(name, skills.size());
        skills.add(new Skill(name,in_class,ability_type,other_modifier,rank,trained));
    }
    public void addSkill(String name, boolean in_class,int ability_type, int rank, boolean trained){
        addSkill(name,in_class,ability_type,0,rank,trained);

    }
    public void addSkill(String name,boolean in_class, int ability_type){
        addSkill(name,in_class,ability_type,0,0,false);
    }
    public void addSkill(String name, boolean in_class,int ability_type, int rank){
        addSkill(name,in_class,ability_type,0,rank,false);
    }

    public int getSkillBonus(String name, Abilities abilities){
        Skill skill = skills.get(skill_map.get(name));

        int skill_bonus = (skill.isInClass() ? skill.getRank(): skill.getRank()/2 ) + skill.getOtherModifier();
        if(skill.isTrained() && skill.getRank() == 0)
            return 0;

        return skill_bonus + abilities.getAbilityBonus(skill.getAbilityType());
    }
    public Skill getSkill(int index){
        return skills.get(index);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
       out.writeTypedList(skills);
    }
}
