package com.ulmus.ddtoolkit;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ulmus.ddtoolkit.structures.Abilities;
import com.ulmus.ddtoolkit.structures.DDCharacter;

import java.util.ArrayList;
import java.util.List;

import static com.ulmus.ddtoolkit.structures.Abilities.*;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlayFragment extends Fragment implements SkillsFragment.OnFragmentInteractionListener, InventoryFragment.OnFragmentInteractionListener, FeatsFragment.OnFragmentInteractionListener{
    private static final boolean IN_CLASS = true;
    private static final boolean CR_CLASS = false;
    private static final boolean TRAINED = true;
    private static final boolean UNTRAIN = false;
    public PlayFragment() {
    }


    private DDCharacter character;
    private PlayerCharacterFragmentPagerAdaper adapter;
    private ViewPager pager;
    private PagerSlidingTabStrip tabs;

    private View playView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            character = getArguments().getParcelable(DDCharacter.DD_CHARACTER_KEY);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(character == null)
            loadDummyCharacter();
        //inflate and puttogether the view
        playView = inflater.inflate(R.layout.fragment_play, container, false);

        adapter = new PlayerCharacterFragmentPagerAdaper(getFragmentManager());
        pager = (ViewPager) playView.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (PagerSlidingTabStrip) playView.findViewById(R.id.tabs);
        tabs.setViewPager(pager);
        tabs.setShouldExpand(true);
        tabs.setTextSize(23); //TODO make this a loaded from resourses
        //TODO make skills inventory and feats icons

        adapter.addFragment(SkillsFragment.newInstance(character),"skills");
        adapter.addFragment(new InventoryFragment(),"inventory");
        adapter.addFragment(new FeatsFragment(), "feats");
        tabs.notifyDataSetChanged();

        View characterName = playView.findViewById(R.id.character_name);
        characterName.setOnClickListener(new CharacterStatsShower(this.getContext()));
        //fill in all the fields
        showCharacterName(character.getName());
        showCharacterLevel(character.getLevel());
        showCharacterAC(character.getArmorClass(0));
        showCharacterHP(character.getHPCurrent(), character.getHpTotal());
        showCharacterFort(character.getFortSave());
        showCharacterReflex(character.getReflexSave());
        showCharacterWill(character.getWillSave());

        return playView;

    }

    protected class CharacterStatsShower implements View.OnClickListener {
        View characterStatsView;
        Context context;
        public CharacterStatsShower(Context context){
            this.context = context;

        }
        @Override
        public void onClick(View v) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            characterStatsView =  inflater.inflate(R.layout.popup_character_stats, null,false);
            TextView strView = (TextView) characterStatsView.findViewById(R.id.character_str_val);
            TextView dexView = (TextView) characterStatsView.findViewById(R.id.character_dex_val);
            TextView conView = (TextView) characterStatsView.findViewById(R.id.character_con_val);
            TextView intView = (TextView) characterStatsView.findViewById(R.id.character_int_val);
            TextView wisView = (TextView) characterStatsView.findViewById(R.id.character_wis_val);
            TextView chaView = (TextView) characterStatsView.findViewById(R.id.character_cha_val);

            TextView strBonusView = (TextView) characterStatsView.findViewById(R.id.character_str_bonus);
            TextView dexBonusView = (TextView) characterStatsView.findViewById(R.id.character_dex_bonus);
            TextView conBonusView = (TextView) characterStatsView.findViewById(R.id.character_con_bonus);
            TextView intBonusView = (TextView) characterStatsView.findViewById(R.id.character_int_bonus);
            TextView wisBonusView = (TextView) characterStatsView.findViewById(R.id.character_wis_bonus);
            TextView chaBonusView = (TextView) characterStatsView.findViewById(R.id.character_cha_bonus);

            strView.setText(Integer.toString(character.getAbilities().getStrVal()));
            dexView.setText(Integer.toString(character.getAbilities().getDexVal()));
            conView.setText(Integer.toString(character.getAbilities().getConVal()));
            intView.setText(Integer.toString(character.getAbilities().getIntVal()));
            wisView.setText(Integer.toString(character.getAbilities().getWisVal()));
            chaView.setText(Integer.toString(character.getAbilities().getChaVal()));

            strBonusView.setText(Integer.toString(character.getAbilities().getStrBonus()));
            dexBonusView.setText(Integer.toString(character.getAbilities().getDexBonus()));
            conBonusView.setText(Integer.toString(character.getAbilities().getConBonus()));
            intBonusView.setText(Integer.toString(character.getAbilities().getIntBonus()));
            wisBonusView.setText(Integer.toString(character.getAbilities().getWisBonus()));
            chaBonusView.setText(Integer.toString(character.getAbilities().getChaBonus()));

            PopupWindow characterStats = new PopupWindow(characterStatsView , 400,
                    580, true);
            characterStats.setContentView(characterStatsView);
            characterStats.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
           // characterStats.showAtLocation(characterStatsView, 0, 0, 120);
            characterStats.showAsDropDown(v);

        }
    }

    private void showCharacterName(String name){
        TextView characterName = (TextView) playView.findViewById(R.id.character_name);
        characterName.setText(name);
    }
    private void showCharacterLevel(int lvl){
        TextView characterLvl = (TextView) playView.findViewById(R.id.character_level);
        characterLvl.setText("Level "+lvl);
    }
    private void showCharacterHP(int current, int max){
        TextView characterHP = (TextView) playView.findViewById(R.id.character_hp);
        characterHP.setText("HP "+current+"/"+max);
    }
    private void showCharacterAC(int ac){
        TextView characterAC = (TextView) playView.findViewById(R.id.character_armor_class);
        characterAC.setText("AC "+ac);
    }
    private void showCharacterFort(int fort){
        TextView characterFort = (TextView) playView.findViewById(R.id.character_fort_save);
        characterFort.setText("Fortitude "+fort);
    }
    private void showCharacterReflex(int reflex){
        TextView characterRefl = (TextView) playView.findViewById(R.id.character_refl_save);
        characterRefl.setText("Reflex "+reflex);
    }
    private void showCharacterWill(int will){
        TextView characterWill = (TextView) playView.findViewById(R.id.character_will_save);
        characterWill.setText("Will "+will);
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * Created by Jake on 12/19/15.
     */

    private void loadDummyCharacter(){
        int strVal = 15;
        int dexVal = 16;
        int conVal = 13;
        int intVal = 13;
        int wisVal = 11;
        int chaVal = 9;
        character = new DDCharacter("Dummy Character Name", strVal,  dexVal,  conVal,  intVal,  wisVal,  chaVal);
        character.setLevel(3);
        character.setHpTotal(23);
        character.setHpCurrent(22);
        character.setHitDie(10);

        character.setFortBase(2);
        character.setReflexBase(2);
        character.setWillBase(0);

        character.setSkillPerLevelBase(2);
        character.setSkillTotalPoints(18);



        character.addSkill("acrobatics"         ,CR_CLASS, DEX,0,0,UNTRAIN);
        character.addSkill("bluff"              ,CR_CLASS, CHA,1,0,UNTRAIN);
        character.addSkill("climb"              ,IN_CLASS, STR,0,0,UNTRAIN);
        character.addSkill("computer use"       ,CR_CLASS, INT,0,0,UNTRAIN);
        character.addSkill("concentration"      ,CR_CLASS, CON,0,0,UNTRAIN);
        character.addSkill("craft chemical"     ,IN_CLASS, INT,0,1,TRAINED);
        character.addSkill("craft electric"     ,IN_CLASS, INT,0,0,TRAINED);
        character.addSkill("craft mechanicl"    ,IN_CLASS, INT,0,1,TRAINED);
        character.addSkill("demolitions"        ,IN_CLASS, INT,0,4,TRAINED);
        character.addSkill("deplomacy"          ,CR_CLASS, CHA,0,0,UNTRAIN);
        character.addSkill("disable device"     ,CR_CLASS, INT,0,0,TRAINED);
        character.addSkill("disguise"           ,CR_CLASS, CHA,0,0,UNTRAIN);
        character.addSkill("drive"              ,CR_CLASS, DEX,0,0,UNTRAIN);
        character.addSkill("escape artist"      ,IN_CLASS, DEX,0,0,UNTRAIN);
        character.addSkill("forgery"            ,CR_CLASS, INT,0,0,UNTRAIN);
        character.addSkill("gather information" ,CR_CLASS, CHA,0,0,UNTRAIN);
        character.addSkill("handle animal"      ,CR_CLASS, CHA,0,0,TRAINED);
        character.addSkill("hide"               ,IN_CLASS, DEX,0,2,UNTRAIN);
        character.addSkill("intimidate"         ,CR_CLASS, CHA,0,0,UNTRAIN);
        character.addSkill("jump"               ,IN_CLASS, STR,0,2,UNTRAIN);
        character.addSkill("knowlage polotics"  ,CR_CLASS, INT,0,0,TRAINED);
        character.addSkill("knowlage geography" ,CR_CLASS, INT,0,0,TRAINED);
        character.addSkill("knowlage biology"   ,CR_CLASS, INT,0,0,TRAINED);
        character.addSkill("knowlage tactics"   ,IN_CLASS, INT,0,0,TRAINED);
        character.addSkill("listen"             ,IN_CLASS, WIS,1,3,UNTRAIN);
        character.addSkill("navigate"           ,CR_CLASS, INT,0,0,UNTRAIN);
        character.addSkill("open lock"          ,CR_CLASS, DEX,0,0,TRAINED);
        character.addSkill("preform"            ,CR_CLASS, CHA,0,0,UNTRAIN);
        character.addSkill("pilot"              ,CR_CLASS, DEX,0,0,UNTRAIN);
        character.addSkill("search"             ,CR_CLASS, INT,0,0,UNTRAIN);
        character.addSkill("sense motive"       ,CR_CLASS, WIS,0,0,UNTRAIN);
        character.addSkill("slight of hand"     ,CR_CLASS, DEX,0,0,TRAINED);
        character.addSkill("sneak"              ,IN_CLASS, DEX,0,4,UNTRAIN);
        character.addSkill("spot"               ,CR_CLASS, WIS,0,1,UNTRAIN);
        character.addSkill("survival"           ,CR_CLASS, WIS,0,0,UNTRAIN);
        character.addSkill("tumble"             ,CR_CLASS, DEX,0,3,TRAINED);
        character.addSkill("treat injury"       ,CR_CLASS, WIS,0,0,UNTRAIN);
        character.addSkill("use rope"           ,CR_CLASS, DEX,0,0,UNTRAIN);



    }
    static class PlayerCharacterFragmentPagerAdaper extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public PlayerCharacterFragmentPagerAdaper(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
       //     System.out.println(mFragmentList.size());

            return mFragmentList.size();
        }


        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
