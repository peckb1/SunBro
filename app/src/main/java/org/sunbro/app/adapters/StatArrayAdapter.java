package org.sunbro.app.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import org.sunbro.app.R;
import org.sunbro.app.model.SelectedClass;
import org.sunbro.app.model.Stat;

public class StatArrayAdapter extends ArrayAdapter<Stat> {

    private static final String TAG = StatArrayAdapter.class.getSimpleName();

    private final Activity activity;
    private final SelectedClass selectedClass;

    public StatArrayAdapter(Activity activity, Stat[] stats, SelectedClass selectedClass) {
        super(activity, R.layout.stat_layout, stats);
        this.activity = activity;
        this.selectedClass = selectedClass;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.stat_layout, null);
        }

        Stat stat = getItem(position);
        TextView name = ButterKnife.findById(rowView, R.id.stat_layout_name);
        TextView value = ButterKnife.findById(rowView, R.id.stat_layout_value);

        name.setText(toCamelCase(stat.toString()));
        value.setText(String.format("%d", selectedClass.getRaisedStatValue(stat)));

        return rowView;
    }

    private String toCamelCase(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
