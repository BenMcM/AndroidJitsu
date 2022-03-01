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
import jitsu.ben.uk.consumerest.bean.ArmLock;
import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.database.ArmLockDataSource;


public class FragmentViewArmLock extends FragmentViewBean implements View.OnClickListener{
    private static final String ARG_DETAIL_SWITCH_CHECKED = "detailIsChecked";
	private static final String ARG_ARMLOCK = "armlockBean";

	private ArmLock mArmLock;

    public FragmentViewArmLock() {
    }

	public void onStart(){
		details_view = (ViewGroup) rootView.findViewById(R.id.armlock_details_view);
		edit_details_view = (ViewGroup) rootView.findViewById(R.id.armlock_edit_details_view);
		super.onStart();
	}

	public static FragmentViewArmLock newInstance(ArmLock armLock, Boolean detailIsChecked) {
		FragmentViewArmLock fragment = new FragmentViewArmLock();
		Bundle args = new Bundle();
		args.putParcelable(ARG_ARMLOCK, armLock);
		args.putBoolean(ARG_DETAIL_SWITCH_CHECKED, detailIsChecked);
		fragment.setArguments(args);
		return fragment;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArmLock = getArguments().getParcelable(ARG_ARMLOCK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_view_armlock, container, false);
		populateUI();
        return rootView;
    }

	public void onSaveEdit(){
		mArmLock = getArmLockFromEditFields();

		ArmLockDataSource dataSource = new ArmLockDataSource(rootView.getContext());
		dataSource.updateArmLock(mArmLock);
		dataSource.close();

		Toast.makeText(rootView.getContext(), R.string.armlock_updated, Toast.LENGTH_SHORT).show();

		populateUI();
		onCancelEdit();
	}

    private void populateUI(){
		TextView numberText = (TextView)rootView.findViewById(R.id.armlock_number_value);
		numberText.setText(mArmLock.getNumber());
		TextView gradeText = (TextView)rootView.findViewById(R.id.armlock_grade_value);
		gradeText.setText(mArmLock.getGrade().getName());
		TextView entranceText = (TextView)rootView.findViewById(R.id.armlock_entrance_value);
		entranceText.setText(mArmLock.getEntrance());
		TextView descriptionText = (TextView)rootView.findViewById(R.id.armlock_description_value);
		descriptionText.setText(mArmLock.getDescription());

		populateEditUIFields();
		setButtonListeners();
	}

	protected void populateEditUIFields(){
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.armlock_edit_grade_value);
		populateEditGrade(gradeEdit);
		EditText entranceEdit = (EditText)rootView.findViewById(R.id.armlock_edit_entrance_value);
		entranceEdit.setText(mArmLock.getEntrance());
		EditText descriptionEdit = (EditText)rootView.findViewById(R.id.armlock_edit_description_value);
		descriptionEdit.setText(mArmLock.getDescription());
	}

	protected int getGradePosition(List<Grade> allGrades){

		return allGrades.indexOf(mArmLock.getGrade());
	}

	public ArmLock getArmLockFromEditFields(){
		TextView numberText = (TextView)rootView.findViewById(R.id.armlock_number_value);
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.armlock_edit_grade_value);
		TextView entranceText = (TextView)rootView.findViewById(R.id.armlock_edit_entrance_value);
		TextView descriptionText = (TextView)rootView.findViewById(R.id.armlock_description_value);

		Integer id = mArmLock.getId();
		String number = numberText.getText().toString();
		String entrance = entranceText.getText().toString();
		Grade grade = (Grade)gradeEdit.getSelectedItem();
		String description = descriptionText.getText().toString();

		return new ArmLock(id, number, grade, entrance, description);
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
