package com.ulmus.ddtoolkit;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ulmus.ddtoolkit.structures.DDCharacter;
import com.ulmus.ddtoolkit.structures.Skill;
import com.ulmus.ddtoolkit.structures.SkillList;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SkillsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SkillsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkillsFragment extends Fragment implements AdapterView.OnItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private DDCharacter character;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private SkillListAdapter skillAdapter;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SkillsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SkillsFragment newInstance(DDCharacter character) {
        SkillsFragment fragment = new SkillsFragment();
        fragment.character = character;
        return fragment;
    }

    public SkillsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View skillsView = inflater.inflate(R.layout.fragment_skills, container, false);

        ListView skillList = (ListView) skillsView.findViewById(R.id.skills_listView);
        skillAdapter = new SkillListAdapter(skillsView.getContext(),character.getSkillList());
        skillList.setAdapter(skillAdapter);
        skillList.setOnItemClickListener(this);

        // Inflate the layout for this fragment
        return skillsView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        skillAdapter.toggleExpanded((int)id);
    }

    protected class SkillListAdapter extends BaseAdapter {
        SkillList skills;
        boolean editMode = false;
        ArrayList<Boolean> expanded;
        LayoutInflater inflater;

        public SkillListAdapter(Context context, SkillList skills) {
            this.skills = skills;
            expanded = new ArrayList<>();
            for(int i = 0; i < skills.getCount(); i++){
                expanded.add(false);
            }
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void toggleExpanded(int id){
            expanded.set(id, !expanded.get(id));
            this.notifyDataSetChanged();
        }

        @Override
        public int getViewTypeCount(){
            return 2;
        }

        @Override
        public int getCount() {
            return skills.getCount();
        }

        @Override
        public Object getItem(int i) {
            return skills.getSkill(i);
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }


        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            View rowView;
            Skill rowSkill = skills.getSkill(i);
            if(convertView == null)
                rowView = inflater.inflate(R.layout.list_element_skills_list, parent, false);
            else
                rowView = convertView;

            Button addRankButton = (Button) rowView.findViewById(R.id.skills_add_button);
            Button subRankButton = (Button) rowView.findViewById(R.id.skills_subtract_button);


            TextView nameView = (TextView) rowView.findViewById(R.id.skills_list_name);
            Button bonusView = (Button) rowView.findViewById(R.id.skills_list_bonus);

            TextView inClassView = (TextView) rowView.findViewById(R.id.skills_list_cross_class);
            TextView trainedView = (TextView) rowView.findViewById(R.id.skills_list_trained);
            TextView otherBonusView = (TextView) rowView.findViewById(R.id.skills_list_other_bonus);
            RelativeLayout otherBonusLayout = (RelativeLayout) rowView.findViewById(R.id.skills_list_other_value_label);

            TextView rankView = (TextView) rowView.findViewById(R.id.skills_list_rank);
            RelativeLayout rankViewLayout = (RelativeLayout) rowView.findViewById(R.id.skills_list_rank_value_label);
            TextView abilityTypeView = (TextView) rowView.findViewById(R.id.skills_list_ability_label);

            nameView.setText(rowSkill.getName());
            bonusView.setText(rowSkill.getBonusToString(character.getAbilities()));

            otherBonusView.setText(rowSkill.getOtherModifierToString());
            rankView.setText(rowSkill.getRankToString());

            String abilityTypeViewString = " + "+rowSkill.getAbilityTypeToString();
            abilityTypeView.setText(abilityTypeViewString);

            //set view visabilities
            addRankButton.setVisibility(View.GONE);
            subRankButton.setVisibility(View.GONE);

            if(expanded.get(i)){
                otherBonusLayout.setVisibility(View.VISIBLE);
                inClassView.setVisibility(rowSkill.isInClass() ? View.GONE : View.VISIBLE);
                trainedView.setVisibility(rowSkill.isTrained() ? View.VISIBLE : View.GONE);

                if(rowSkill.getOtherModifier() == 0) {
                    otherBonusLayout.setVisibility(View.GONE);
                } else{
                    otherBonusLayout.setVisibility(View.VISIBLE);
                }
                rankViewLayout.setVisibility(View.VISIBLE);
                abilityTypeView.setVisibility(View.VISIBLE);

            }
            else{
                 inClassView.setVisibility(View.GONE);
                 trainedView.setVisibility(View.GONE);
                 otherBonusLayout.setVisibility(View.GONE);
                 rankViewLayout.setVisibility(View.GONE);
                 abilityTypeView.setVisibility(View.GONE);
            }



            return rowView;
        }

    }
}
