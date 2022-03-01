package jitsu.ben.uk.consumerest.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import jitsu.ben.uk.consumerest.R;
import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.bean.Kata;
import jitsu.ben.uk.consumerest.bean.KataStep;
import jitsu.ben.uk.consumerest.database.KataDataSource;


public class FragmentViewKata extends FragmentViewBean implements View.OnClickListener{
    private static final String ARG_DETAIL_SWITCH_CHECKED = "detailIsChecked";
	private static final String ARG_KATA = "kataBean";

    private Kata mKata;

    public FragmentViewKata() {
    }

    public void onStart(){
    	details_view = (ViewGroup) rootView.findViewById(R.id.kata_details_view);
    	edit_details_view = (ViewGroup) rootView.findViewById(R.id.kata_edit_details_view);
    	super.onStart();
	}

	public static FragmentViewKata newInstance(Kata kata, Boolean detailIsChecked) {
		FragmentViewKata fragment = new FragmentViewKata();
		Bundle args = new Bundle();
		args.putParcelable(ARG_KATA, kata);
		args.putBoolean(ARG_DETAIL_SWITCH_CHECKED, detailIsChecked);
		fragment.setArguments(args);
		return fragment;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mKata = getArguments().getParcelable(ARG_KATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_view_kata, container, false);
		populateUI();
        return rootView;
    }

	public void onSaveEdit(){
		mKata = getKataFromEditFields();

		KataDataSource dataSource = new KataDataSource(rootView.getContext());
		dataSource.updateKata(mKata);
		dataSource.close();

		Toast.makeText(rootView.getContext(), R.string.armlock_updated, Toast.LENGTH_SHORT).show();

		populateUI();
		onCancelEdit();
	}

    private void populateUI(){
		TextView nameText = (TextView)rootView.findViewById(R.id.kata_name_value);
		nameText.setText(mKata.getName());
		TextView translationText = (TextView)rootView.findViewById(R.id.kata_translation_value);
		translationText.setText(mKata.getTranslation());
		TextView gradeText = (TextView)rootView.findViewById(R.id.kata_grade_value);
		gradeText.setText(mKata.getGrade().getName());
		TextView descriptionText = (TextView)rootView.findViewById(R.id.kata_steps_value);
		descriptionText.setText(makeDescription(mKata));

		populateEditUIFields();
		setButtonListeners();
	}

	protected void populateEditUIFields(){
		EditText translationEdit = (EditText)rootView.findViewById(R.id.kata_edit_translation_value);
		translationEdit.setText(mKata.getTranslation());
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.kata_edit_grade_value);
		populateEditGrade(gradeEdit);
		EditText descriptionEdit = (EditText)rootView.findViewById(R.id.kata_edit_steps_value);
		descriptionEdit.setText(makeDescriptionNoNumbers(mKata));
	}

	protected int getGradePosition(List<Grade> allGrades){

		return allGrades.indexOf(mKata.getGrade());
	}

	public Kata getKataFromEditFields(){
		TextView nameText = (TextView)rootView.findViewById(R.id.kata_name_value);
		EditText translationEdit = (EditText)rootView.findViewById(R.id.kata_edit_translation_value);
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.kata_edit_grade_value);
		TextView descriptionText = (TextView)rootView.findViewById(R.id.kata_edit_steps_value);

		Integer id = mKata.getId();
		String name = nameText.getText().toString();
		String translation = translationEdit.getText().toString();
		Grade grade = (Grade)gradeEdit.getSelectedItem();
		String description = descriptionText.getText().toString();

		List<KataStep> kataSteps = KataStep.getStepsFromString(description, System.getProperty("line.separator"));

		return new Kata(id, name, grade, translation, kataSteps);
	}

	private String makeDescription(Kata kata){
		String description = "";
		List<KataStep> steps = kata.getSteps();
		Collections.sort(steps);
		for(KataStep step: steps){
			description = description + step.getStepNumber() + " - " + step.getDescription() + "\n";
		}
		return description;
	}

	private String makeDescriptionNoNumbers(Kata kata){
		String description = "";
		List<KataStep> steps = kata.getSteps();
		Collections.sort(steps);
		for(KataStep step: steps){
			description = description + step.getDescription() + "\n";
		}
		return description;
	}


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
