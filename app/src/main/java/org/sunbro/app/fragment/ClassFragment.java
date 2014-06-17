package org.sunbro.app.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.google.common.collect.Lists;
import org.sunbro.app.R;
import org.sunbro.app.adapters.StatArrayAdapter;
import org.sunbro.app.events.StatChangeEvent;
import org.sunbro.app.model.BaseClass;
import org.sunbro.app.model.SelectedClass;
import org.sunbro.app.model.Stat;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

import java.util.ArrayList;
import java.util.EnumSet;

import static android.app.AlertDialog.Builder;
import static android.content.DialogInterface.OnClickListener;
import static android.view.View.INVISIBLE;

public class ClassFragment extends Fragment {

    private static final String TAG = ClassFragment.class.getSimpleName();

    @InjectView(R.id.select_class_stat_list) ListView statsList;
    @InjectView(R.id.select_class_soul_level_layout) LinearLayout soulLevelLayout;
    @InjectView(R.id.select_class_text_view) TextView currentClassText;

    private final PublishSubject<StatChangeEvent> statChangeSubject = PublishSubject.create();

    private Subscription statChangeSubscription;

    @OnClick(R.id.select_class_layout) void submit() {
        Dialog dialog = createClassSelectionDialog();
        dialog.show();
    }

    private final ArrayList<BaseClass> classes = Lists.newArrayList(EnumSet.allOf(BaseClass.class));
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
            setupAdapter();
        }

        setSoulLevel();
    }

    private void setupAdapter() {
        EnumSet<Stat> statsSet = EnumSet.allOf(Stat.class);
        Stat[] stats = new Stat[statsSet.size()];
        statsSet.toArray(stats);

        statsList.setAdapter(new StatArrayAdapter(getActivity(), stats, selectedClass, statChangeSubject));
    }

    private void setSoulLevel() {
        TextView soulLevelName = ButterKnife.findById(soulLevelLayout, R.id.stat_layout_name);
        final TextView soulLevelValue = ButterKnife.findById(soulLevelLayout, R.id.stat_layout_value);
        ImageButton lowerStat = ButterKnife.findById(soulLevelLayout, R.id.stat_decrease_level);
        ImageButton raiseStat = ButterKnife.findById(soulLevelLayout, R.id.stat_increase_level);

        if(statChangeSubscription == null || statChangeSubscription.isUnsubscribed()) {
            statChangeSubscription = statChangeSubject.subscribe(new Action1<StatChangeEvent>() {
                @Override
                public void call(StatChangeEvent statChangeEvent) {
                    soulLevelValue.setText(String.format("%d", selectedClass.getSoulLevel()));
                }
            });
        }

        soulLevelName.setText(getResources().getText(R.string.soul_level));
        soulLevelValue.setText(String.format("%d", selectedClass.getSoulLevel()));
        lowerStat.setVisibility(INVISIBLE);
        raiseStat.setVisibility(INVISIBLE);
    }

    private void setClassTitle() {
        currentClassText.setText(selectedClass.getBaseClass().toString());
    }

    private Dialog createClassSelectionDialog() {
        Builder builder = new Builder(getActivity());

        ArrayList<String> classNames = Lists.newArrayList();
        for(BaseClass baseClass : classes) {
            classNames.add(baseClass.toString());
        }
        String[] names = new String[classNames.size()];
        classNames.toArray(names);

        builder.setTitle(R.string.class_selection)
                .setItems(names, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        setSelectedClass(classes.get(which));
                    }
                })
                .setNegativeButton(R.string.cancel_class_selection, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
        return builder.create();
    }

    private void setSelectedClass(BaseClass newClass) {
        selectedClass = new SelectedClass(newClass);

        setupAdapter();
        setSoulLevel();
        setClassTitle();
    }
}
