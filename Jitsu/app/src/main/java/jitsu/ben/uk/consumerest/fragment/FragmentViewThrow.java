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
import jitsu.ben.uk.consumerest.bean.Throw;
import jitsu.ben.uk.consumerest.database.ThrowsDataSource;


public class FragmentViewThrow extends FragmentViewBean implements View.OnClickListener{
    private static final String ARG_DETAIL_SWITCH_CHECKED = "detailIsChecked";
	private static final String ARG_THROW = "throwBean";

    private Throw mThrow;

    public FragmentViewThrow() {
    }

	public void onStart(){
		details_view = (ViewGroup) rootView.findViewById(R.id.throw_details_view);
		edit_details_view = (ViewGroup) rootView.findViewById(R.id.throw_edit_details_view);
		super.onStart();
	}

	public static FragmentViewThrow newInstance(Throw jitsuThrow, Boolean detailIsChecked) {
		FragmentViewThrow fragment = new FragmentViewThrow();
		Bundle args = new Bundle();
		args.putParcelable(ARG_THROW, jitsuThrow);
		args.putBoolean(ARG_DETAIL_SWITCH_CHECKED, detailIsChecked);
		fragment.setArguments(args);
		return fragment;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mThrow = getArguments().getParcelable(ARG_THROW);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_view_throw, container, false);
        populateUI();
        return rootView;

    }

	public void onSaveEdit(){
    	mThrow = getThrowFromEditFields();

		ThrowsDataSource dataSource = new ThrowsDataSource(rootView.getContext());
		dataSource.updateThrow(mThrow);
		dataSource.close();

		Toast.makeText(rootView.getContext(), R.string.throw_updated, Toast.LENGTH_SHORT).show();

		populateUI();
		onCancelEdit();
	}

    private void populateUI(){
		TextView nameText = (TextView)rootView.findViewById(R.id.throw_name_value);
		nameText.setText(mThrow.getName());
		TextView translationText = (TextView)rootView.findViewById(R.id.throw_translation_value);
		translationText.setText(mThrow.getTranslation());
		TextView gradeText = (TextView)rootView.findViewById(R.id.throw_grade_value);
		gradeText.setText(mThrow.getGrade().getName());
		TextView entranceText = (TextView)rootView.findViewById(R.id.throw_entrance_value);
		entranceText.setText(mThrow.getEntrance());
		TextView descriptionText = (TextView)rootView.findViewById(R.id.throw_description_value);
		descriptionText.setText(mThrow.getDescription());

		populateEditUIFields();
		setButtonListeners();
	}

	protected void populateEditUIFields(){
		EditText translationEdit = (EditText)rootView.findViewById(R.id.throw_edit_translation_value);
		translationEdit.setText(mThrow.getTranslation());
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.throw_edit_grade_value);
		populateEditGrade(gradeEdit);
		EditText entranceEdit = (EditText)rootView.findViewById(R.id.throw_edit_entrance_value);
		entranceEdit.setText(mThrow.getEntrance());
		EditText descriptionEdit = (EditText)rootView.findViewById(R.id.throw_edit_description_value);
		descriptionEdit.setText(mThrow.getDescription());
	}

	protected int getGradePosition(List<Grade> allGrades){

		return allGrades.indexOf(mThrow.getGrade());
	}

	public Throw getThrowFromEditFields(){
		TextView nameText = (TextView)rootView.findViewById(R.id.throw_name_value);
		EditText translationEdit = (EditText)rootView.findViewById(R.id.throw_edit_translation_value);
		Spinner gradeEdit = (Spinner)rootView.findViewById(R.id.throw_edit_grade_value);
		EditText entranceEdit = (EditText)rootView.findViewById(R.id.throw_edit_entrance_value);
		TextView descriptionText = (TextView)rootView.findViewById(R.id.throw_description_value);

		Integer id = mThrow.getId();
		String name = nameText.getText().toString();
		String translation = translationEdit.getText().toString();
		Grade grade = (Grade)gradeEdit.getSelectedItem();
		String entrance = entranceEdit.getText().toString();
		String description = descriptionText.getText().toString();
		return new Throw(id, name, translation, grade, entrance, description, "");
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
