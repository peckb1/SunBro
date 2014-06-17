package org.sunbro.app.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.ButterKnife;
import org.sunbro.app.R;
import org.sunbro.app.events.StatChangeEvent;
import org.sunbro.app.model.SelectedClass;
import org.sunbro.app.model.Stat;
import rx.subjects.PublishSubject;

import static android.view.View.OnClickListener;

public class StatArrayAdapter extends ArrayAdapter<Stat> {

    private static final String TAG = StatArrayAdapter.class.getSimpleName();

    private final Activity activity;
    private final SelectedClass selectedClass;
    private final PublishSubject<StatChangeEvent> statChangedSubject;

    public StatArrayAdapter(Activity activity, Stat[] stats, SelectedClass selectedClass,
                            PublishSubject<StatChangeEvent> statChangedSubject) {
        super(activity, R.layout.stat_layout, stats);
        this.activity = activity;
        this.selectedClass = selectedClass;
        this.statChangedSubject = statChangedSubject;
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
        ImageButton lowerStat = ButterKnife.findById(rowView, R.id.stat_decrease_level);
        ImageButton raiseStat = ButterKnife.findById(rowView, R.id.stat_increase_level);

        name.setText(stat.toString());
        value.setText(String.format("%d", selectedClass.getCurrentStatValue(stat)));

        setupListeners(lowerStat, raiseStat, value, stat);

        return rowView;
    }

    private void setupListeners(final ImageButton lowerStat, final ImageButton raiseStat,
                                final TextView    value,     final Stat        stat) {

        lowerStat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedClass.decrease(stat);
                if(!canDecrease(stat)) {
                    lowerStat.setClickable(false);
                }
                raiseStat.setClickable(true);

                int newValue = selectedClass.getCurrentStatValue(stat);
                value.setText(String.format("%d", newValue));
                statChangedSubject.onNext(StatChangeEvent.create(stat, newValue));
            }
        });
        raiseStat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedClass.increase(stat);
                lowerStat.setClickable(true);
                if(!canIncrease(stat)) {
                    raiseStat.setClickable(false);
                }

                int newValue = selectedClass.getCurrentStatValue(stat);
                value.setText(String.format("%d", newValue));
                statChangedSubject.onNext(StatChangeEvent.create(stat, newValue));
            }
        });

        if(!canDecrease(stat)) {
            lowerStat.setClickable(false);
        }

        if(!canIncrease(stat)) {
            raiseStat.setClickable(false);
        }
    }

    private boolean canIncrease(Stat stat) {
        return !(selectedClass.getCurrentStatValue(stat) == 99);
    }

    private boolean canDecrease(Stat stat) {
        return !(selectedClass.getCurrentStatValue(stat) == selectedClass.getBaseClass().getStatValue(stat));
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
