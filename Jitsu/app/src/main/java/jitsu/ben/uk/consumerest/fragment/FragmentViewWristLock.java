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
import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.bean.WristLock;
import jitsu.ben.uk.consumerest.database.WristLockDataSource;


public class FragmentViewWristLock extends FragmentViewBean implements View.OnClickListener{
    private static final String ARG_DETAIL_SWITCH_CHECKED = "detailIsChecked";
	private static final String ARG_WRISTLOCK = "wristlockBean";

    private WristLock mWristLock;

    public FragmentViewWristLock() {
    }

    public void onStart(){
		details_view = (ViewGroup) rootView.findViewById(R.id.wristlock_details_view);
		edit_details_view = (ViewGroup) rootView.findViewById(R.id.wristlock_edit_details_view);
		super.onStart();
	}

	public static FragmentViewWristLock newInstance(WristLock wristLock, Boolean detailIsChecked) {
		FragmentViewWristLock fragment = new FragmentViewWristLock();
		Bundle args = new Bundle();
		args.putParcelable(ARG_WRISTLOCK, wristLock);
		args.putBoolean(ARG_DETAIL_SWITCH_CHECKED, detailIsChecked);
		fragment.setArguments(args);
		return fragment;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mWristLock = getArguments().getParcelable(ARG_WRISTLOCK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_view_wristlock, container, false);
        populateUI();
        return rootView;

    }

	public void onSaveEdit(){
		mWristLock = getWristLockFromEditFields();

		WristLockDataSource dataSource = new WristLockDataSource(rootView.getContext());
		dataSource.updateWristLock(mWristLock);
		dataSource.close();

		Toast.makeText(rootView.getContext(), R.string.wristlock_updated, Toast.LENGTH_SHORT).show();

		populateUI();
		onCancelEdit();
	}

	private void populateUI(){
		TextView numberText = (TextView)rootView.findViewById(R.id.wristlock_number_value);
		numberText.setText(mWristLock.getNumber());
		TextView gradeText = (TextView)rootView.findViewById(R.id.wristlock_grade_value);
		gradeText.setText(mWristLock.getGrade().getName());
		TextView entranceText = (TextView)rootView.findViewById(R.id.wristlock_entrance_value);
		entranceText.setText(mWristLock.getEntrance());
		TextView descriptionText = (TextView)rootView.findViewById(R.id.wristlock_description_value);
		descriptionText.setText(mWristLock.getDescription());

		populateEditUIFields();
		setButtonListeners();
	}

	protected void populateEditUIFields(){
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.wristlock_edit_grade_value);
		populateEditGrade(gradeEdit);
		EditText entranceEdit = (EditText)rootView.findViewById(R.id.wristlock_edit_entrance_value);
		entranceEdit.setText(mWristLock.getEntrance());
		EditText descriptionEdit = (EditText)rootView.findViewById(R.id.wristlock_edit_description_value);
		descriptionEdit.setText(mWristLock.getDescription());
	}

	protected int getGradePosition(List<Grade> allGrades){
		return allGrades.indexOf(mWristLock.getGrade());
	}

	public WristLock getWristLockFromEditFields(){
		TextView numberText = (TextView)rootView.findViewById(R.id.wristlock_number_value);
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.wristlock_edit_grade_value);
		TextView entranceText = (TextView)rootView.findViewById(R.id.wristlock_edit_entrance_value);
		TextView descriptionText = (TextView)rootView.findViewById(R.id.wristlock_description_value);

		Integer id = mWristLock.getId();
		String number = numberText.getText().toString();
		String entrance = entranceText.getText().toString();
		Grade grade = (Grade)gradeEdit.getSelectedItem();
		String description = descriptionText.getText().toString();

		return new WristLock(id, number, grade, entrance, description);
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
