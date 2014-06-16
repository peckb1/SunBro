package org.sunbro.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import org.sunbro.app.R;
import org.sunbro.app.adapters.StatArrayAdapter;
import org.sunbro.app.model.BaseClass;
import org.sunbro.app.model.SelectedClass;
import org.sunbro.app.model.Stat;

import java.util.EnumSet;

public class ClassFragment extends Fragment {

    private static final String TAG = ClassFragment.class.getSimpleName();

    @InjectView(R.id.select_class_stat_list) ListView statsList;

    private final EnumSet<BaseClass> classes = EnumSet.allOf(BaseClass.class);
    private SelectedClass selectedClass = new SelectedClass(BaseClass.WARRIOR);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_class, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(statsList.getAdapter() == null) {
            EnumSet<Stat> statsSet = EnumSet.allOf(Stat.class);
            Stat[] stats = new Stat[statsSet.size()];
            statsSet.toArray(stats);

            statsList.setAdapter(new StatArrayAdapter(getActivity(), stats, selectedClass));
        }
    }
}
