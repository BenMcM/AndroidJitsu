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

import java.util.List;

import jitsu.ben.uk.consumerest.R;
import jitsu.ben.uk.consumerest.bean.ArmLockCounter;
import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.database.ArmLockCounterDataSource;


public class FragmentViewArmLockCounter extends FragmentViewBean implements View.OnClickListener{
    private static final String ARG_DETAIL_SWITCH_CHECKED = "detailIsChecked";
	private static final String ARG_ARMLOCKCOUNTER = "armlockcounterBean";

	private ArmLockCounter mArmLockCounter;

    public FragmentViewArmLockCounter() {
    }

	public void onStart(){
		details_view = (ViewGroup) rootView.findViewById(R.id.armlockcounter_details_view);
		edit_details_view = (ViewGroup) rootView.findViewById(R.id.armlockcounter_edit_details_view);
		super.onStart();
	}

	public static FragmentViewArmLockCounter newInstance(ArmLockCounter armLockCounter, Boolean detailIsChecked) {
		FragmentViewArmLockCounter fragment = new FragmentViewArmLockCounter();
		Bundle args = new Bundle();
		args.putParcelable(ARG_ARMLOCKCOUNTER, armLockCounter);
		args.putBoolean(ARG_DETAIL_SWITCH_CHECKED, detailIsChecked);
		fragment.setArguments(args);
		return fragment;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArmLockCounter = getArguments().getParcelable(ARG_ARMLOCKCOUNTER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_view_armlockcounter, container, false);
		populateUI();
        return rootView;
    }

	public void onSaveEdit(){
		mArmLockCounter = getArmLockCounterFromEditFields();

		ArmLockCounterDataSource dataSource = new ArmLockCounterDataSource(rootView.getContext());
		dataSource.updateArmLockCounter(mArmLockCounter);
		dataSource.close();

		Toast.makeText(rootView.getContext(), R.string.armlockcounter_updated, Toast.LENGTH_SHORT).show();

		populateUI();
		onCancelEdit();
	}

    private void populateUI(){
		TextView numberText = (TextView)rootView.findViewById(R.id.armlockcounter_number_value);
		numberText.setText(mArmLockCounter.getNumber());
		TextView gradeText = (TextView)rootView.findViewById(R.id.armlockcounter_grade_value);
		gradeText.setText(mArmLockCounter.getGrade().getName());
		TextView descriptionText = (TextView)rootView.findViewById(R.id.armlockcounter_description_value);
		descriptionText.setText(mArmLockCounter.getDescription());

		populateEditUIFields();
		setButtonListeners();
	}

	protected void populateEditUIFields(){
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.armlockcounter_edit_grade_value);
		populateEditGrade(gradeEdit);
		EditText descriptionEdit = (EditText)rootView.findViewById(R.id.armlockcounter_edit_description_value);
		descriptionEdit.setText(mArmLockCounter.getDescription());
	}

	protected int getGradePosition(List<Grade> allGrades){

		return allGrades.indexOf(mArmLockCounter.getGrade());
	}

	public ArmLockCounter getArmLockCounterFromEditFields(){
		TextView numberText = (TextView)rootView.findViewById(R.id.armlockcounter_number_value);
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.armlockcounter_edit_grade_value);
		TextView descriptionText = (TextView)rootView.findViewById(R.id.armlockcounter_description_value);

		Integer id = mArmLockCounter.getId();
		String number = numberText.getText().toString();
		Grade grade = (Grade)gradeEdit.getSelectedItem();
		String description = descriptionText.getText().toString();

		return new ArmLockCounter(id, number, grade, description);
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
