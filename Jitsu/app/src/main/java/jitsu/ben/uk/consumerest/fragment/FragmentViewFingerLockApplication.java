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
import jitsu.ben.uk.consumerest.bean.FingerLockApplication;
import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.database.FingerLockApplicationDataSource;


public class FragmentViewFingerLockApplication extends FragmentViewBean implements View.OnClickListener{
    private static final String ARG_DETAIL_SWITCH_CHECKED = "detailIsChecked";
	private static final String ARG_FINGERLOCKAPPLICATION = "fingerlockapplicationBean";

	private FingerLockApplication mFingerLockApplication;

    public FragmentViewFingerLockApplication() {
    }

	public void onStart(){
		details_view = (ViewGroup) rootView.findViewById(R.id.fingerlockapplication_details_view);
		edit_details_view = (ViewGroup) rootView.findViewById(R.id.fingerlockapplication_edit_details_view);
		super.onStart();
	}

	public static FragmentViewFingerLockApplication newInstance(FingerLockApplication fingerLockApplication, Boolean detailIsChecked) {
		FragmentViewFingerLockApplication fragment = new FragmentViewFingerLockApplication();
		Bundle args = new Bundle();
		args.putParcelable(ARG_FINGERLOCKAPPLICATION, fingerLockApplication);
		args.putBoolean(ARG_DETAIL_SWITCH_CHECKED, detailIsChecked);
		fragment.setArguments(args);
		return fragment;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
			mFingerLockApplication = getArguments().getParcelable(ARG_FINGERLOCKAPPLICATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_view_fingerlockapplication, container, false);
		populateUI();
        return rootView;
    }

	public void onSaveEdit(){
		mFingerLockApplication = getFingerLockApplicationFromEditFields();

		FingerLockApplicationDataSource dataSource = new FingerLockApplicationDataSource(rootView.getContext());
		dataSource.updateFingerLockApplication(mFingerLockApplication);
		dataSource.close();

		Toast.makeText(rootView.getContext(), R.string.fingerlockapplication_updated, Toast.LENGTH_SHORT).show();

		populateUI();
		onCancelEdit();
	}

    public void populateUI(){
		TextView numberText = (TextView)rootView.findViewById(R.id.fingerlockapplication_number_value);
		numberText.setText(mFingerLockApplication.getNumber());
		TextView gradeText = (TextView)rootView.findViewById(R.id.fingerlockapplication_grade_value);
		gradeText.setText(mFingerLockApplication.getGrade().getName());
		TextView descriptionText = (TextView)rootView.findViewById(R.id.fingerlockapplication_description_value);
		descriptionText.setText(mFingerLockApplication.getDescription());

		populateEditUIFields();
		setButtonListeners();
	}

	protected void populateEditUIFields(){
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.fingerlockapplication_edit_grade_value);
		populateEditGrade(gradeEdit);
		EditText descriptionEdit = (EditText)rootView.findViewById(R.id.fingerlockapplication_edit_description_value);
		descriptionEdit.setText(mFingerLockApplication.getDescription());
	}

	protected int getGradePosition(List<Grade> allGrades){

		return allGrades.indexOf(mFingerLockApplication.getGrade());
	}

	public FingerLockApplication getFingerLockApplicationFromEditFields(){
		TextView numberText = (TextView)rootView.findViewById(R.id.fingerlockapplication_number_value);
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.fingerlockapplication_edit_grade_value);
		TextView descriptionText = (TextView)rootView.findViewById(R.id.fingerlockapplication_description_value);

		Integer id = mFingerLockApplication.getId();
		String number = numberText.getText().toString();
		Grade grade = (Grade)gradeEdit.getSelectedItem();
		String description = descriptionText.getText().toString();

		return new FingerLockApplication(id, number, grade, description);
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
