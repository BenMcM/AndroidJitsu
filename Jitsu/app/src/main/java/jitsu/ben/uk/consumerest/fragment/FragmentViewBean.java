package jitsu.ben.uk.consumerest.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.List;

import jitsu.ben.uk.consumerest.R;
import jitsu.ben.uk.consumerest.bean.Grade;
import jitsu.ben.uk.consumerest.database.JitsuDataSource;

/**
 * Created by ben on 30/09/2016.
 */

public abstract class FragmentViewBean extends Fragment implements View.OnClickListener{
	protected ViewGroup details_view, edit_details_view;
	protected Button edit_button, cancel_button, save_button;
	protected View rootView;
	protected Boolean mDetailIsChecked;

	public void onStart(){
		super.onStart();
		Switch detailsSwitch = (Switch)getActivity().findViewById(R.id.bean_details_button);
		mDetailIsChecked = detailsSwitch.isChecked();
		details_view.setVisibility(mDetailIsChecked ? View.VISIBLE : View.GONE);
		edit_details_view.setVisibility(View.GONE);
		edit_button = (Button)rootView.findViewById(R.id.edit_button);
		edit_button.setVisibility(mDetailIsChecked ? View.VISIBLE : View.GONE);
		cancel_button = (Button)rootView.findViewById(R.id.cancel_button);
		save_button = (Button)rootView.findViewById(R.id.save_button);
	}

	protected void populateEditGrade(Spinner editGrade){
		JitsuDataSource dataSource = new JitsuDataSource(rootView.getContext());
		List<Grade> allGrades = dataSource.getGrades();
		dataSource.close();

		ArrayAdapter<Grade> gradeAdapter = new ArrayAdapter(editGrade.getContext(), android.R.layout.simple_spinner_item, allGrades);
		editGrade.setAdapter(gradeAdapter);
		editGrade.setSelection(getGradePosition(allGrades));
	}

	protected abstract int getGradePosition(List<Grade> allGrades);

    public void toggleDetails(Boolean isChecked){
		details_view.setVisibility(isChecked ? View.VISIBLE : View.GONE);
		edit_details_view.setVisibility(View.GONE);
		edit_button.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
		if(!isChecked){
			cancel_button.setVisibility(View.GONE);
			save_button.setVisibility(View.GONE);
		}
    }

    protected void setButtonListeners(){
		Button edit_throw = (Button)rootView.findViewById(R.id.edit_button);
		edit_throw.setOnClickListener(this);
		Button cancel_edit = (Button)rootView.findViewById(R.id.cancel_button);
		cancel_edit.setOnClickListener(this);
		Button save_edit = (Button)rootView.findViewById(R.id.save_button);
		save_edit.setOnClickListener(this);
	}

	public void onEdit(){
		details_view.setVisibility(View.GONE);
		edit_details_view.setVisibility(View.VISIBLE);
		save_button.setVisibility(View.VISIBLE);
		cancel_button.setVisibility(View.VISIBLE);
	}

	public void onCancelEdit(){
		details_view.setVisibility(View.VISIBLE);
		edit_details_view.setVisibility(View.GONE);
		save_button.setVisibility(View.GONE);
		cancel_button.setVisibility(View.GONE);

		populateEditUIFields();
	}

	public abstract void onSaveEdit();

	public void onClick(View view) {
		switch (view.getId()){
			case R.id.edit_button:
				onEdit();
				break;
			case R.id.cancel_button:
				onCancelEdit();
				break;
			case R.id.save_button:
				onSaveEdit();
				break;
			default:
		}
	}

	protected abstract void populateEditUIFields();
}
