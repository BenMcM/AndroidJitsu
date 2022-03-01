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
import jitsu.ben.uk.consumerest.bean.NeckLock;
import jitsu.ben.uk.consumerest.database.NeckLockDataSource;


public class FragmentViewNeckLock extends FragmentViewBean implements View.OnClickListener{
    private static final String ARG_DETAIL_SWITCH_CHECKED = "detailIsChecked";
	private static final String ARG_NECKLOCK = "necklockBean";

    private NeckLock mNeckLock;

    public FragmentViewNeckLock() {
    }

	public void onStart(){
		details_view = (ViewGroup) rootView.findViewById(R.id.necklock_details_view);
		edit_details_view = (ViewGroup) rootView.findViewById(R.id.necklock_edit_details_view);
		super.onStart();
	}

	public static FragmentViewNeckLock newInstance(NeckLock neckLock, Boolean detailIsChecked) {
		FragmentViewNeckLock fragment = new FragmentViewNeckLock();
		Bundle args = new Bundle();
		args.putParcelable(ARG_NECKLOCK, neckLock);
		args.putBoolean(ARG_DETAIL_SWITCH_CHECKED, detailIsChecked);
		fragment.setArguments(args);
		return fragment;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNeckLock = getArguments().getParcelable(ARG_NECKLOCK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_view_necklock, container, false);

        populateUI();
        return rootView;
    }

	public void onSaveEdit(){
		mNeckLock = getNeckLockFromEditFields();

		NeckLockDataSource dataSource = new NeckLockDataSource(rootView.getContext());
		dataSource.updateNeckLock(mNeckLock);
		dataSource.close();

		Toast.makeText(rootView.getContext(), R.string.necklock_updated, Toast.LENGTH_SHORT).show();

		populateUI();
		onCancelEdit();
	}

	private void populateUI(){
		TextView numberText = (TextView)rootView.findViewById(R.id.necklock_number_value);
		numberText.setText(mNeckLock.getNumber());
		TextView gradeText = (TextView)rootView.findViewById(R.id.necklock_grade_value);
		gradeText.setText(mNeckLock.getGrade().getName());
		TextView descriptionText = (TextView)rootView.findViewById(R.id.necklock_description_value);
		descriptionText.setText(mNeckLock.getDescription());

		populateEditUIFields();
		setButtonListeners();
	}

	protected void populateEditUIFields(){
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.necklock_edit_grade_value);
		populateEditGrade(gradeEdit);
		EditText descriptionEdit = (EditText)rootView.findViewById(R.id.necklock_edit_description_value);
		descriptionEdit.setText(mNeckLock.getDescription());
	}

	protected int getGradePosition(List<Grade> allGrades){

		return allGrades.indexOf(mNeckLock.getGrade());
	}

	public NeckLock getNeckLockFromEditFields(){
		TextView numberText = (TextView)rootView.findViewById(R.id.necklock_number_value);
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.necklock_edit_grade_value);
		TextView descriptionText = (TextView)rootView.findViewById(R.id.necklock_description_value);

		Integer id = mNeckLock.getId();
		String number = numberText.getText().toString();
		Grade grade = (Grade)gradeEdit.getSelectedItem();
		String description = descriptionText.getText().toString();

		return new NeckLock(id, number, grade, description);
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
