package jitsu.ben.uk.consumerest.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jitsu.ben.uk.consumerest.bean.JitsuBean;

import static jitsu.ben.uk.consumerest.constant.Constants.GRADE_BLUE;
import static jitsu.ben.uk.consumerest.constant.Constants.GRADE_BROWN;
import static jitsu.ben.uk.consumerest.constant.Constants.GRADE_GREEN;
import static jitsu.ben.uk.consumerest.constant.Constants.GRADE_ORANGE;
import static jitsu.ben.uk.consumerest.constant.Constants.GRADE_PURPLE;
import static jitsu.ben.uk.consumerest.constant.Constants.GRADE_YELLOW;

/**
 * Created by ben on 28/09/2016.
 */

public class JitsuArrayAdapter<T extends JitsuBean> extends ArrayAdapter {
    List<T> objects;

    private static final Map<String, Integer> colours;
    static
    {
        colours = new HashMap<String, Integer>();
        colours.put(GRADE_YELLOW, 0xFFFDFF33);
        colours.put(GRADE_ORANGE, 0xFFFFB220);
        colours.put(GRADE_GREEN, 0xFF07D600);
        colours.put(GRADE_PURPLE, 0xFF7204FF);
        colours.put(GRADE_BLUE, 0xFF042EFF);
        colours.put(GRADE_BROWN, 0xFFA54B00);
    }

    public JitsuArrayAdapter(Context context, int resource, List<T> objects){
        super(context, resource, objects);
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        JitsuBean jitsu = objects.get(position);
        String grade = jitsu.getGrade().getName().toUpperCase();

        view.setBackgroundColor(colours.get(grade));

        return view;
    }
}
