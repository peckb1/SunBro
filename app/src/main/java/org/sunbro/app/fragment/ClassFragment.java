package org.sunbro.app.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.google.common.collect.Lists;
import org.sunbro.app.R;
import org.sunbro.app.adapters.StatArrayAdapter;
import org.sunbro.app.model.BaseClass;
import org.sunbro.app.model.SelectedClass;
import org.sunbro.app.model.Stat;

import java.util.ArrayList;
import java.util.EnumSet;

import static android.content.DialogInterface.OnClickListener;

public class ClassFragment extends Fragment {

    private static final String TAG = ClassFragment.class.getSimpleName();

    @InjectView(R.id.select_class_stat_list) ListView statsList;
    @InjectView(R.id.select_class_soul_level_layout) LinearLayout soulLevelLayout;
    @InjectView(R.id.select_class_text_view) TextView currentClassText;

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

        statsList.setAdapter(new StatArrayAdapter(getActivity(), stats, selectedClass));
    }

    private void setSoulLevel() {
        TextView soulLevelName = ButterKnife.findById(soulLevelLayout, R.id.stat_layout_name);
        TextView soulLevelValue = ButterKnife.findById(soulLevelLayout, R.id.stat_layout_value);

        soulLevelName.setText(getResources().getText(R.string.soul_level));
        soulLevelValue.setText(String.format("%d", selectedClass.getSoulLevel()));
    }

    private void setClassTitle() {
        currentClassText.setText(selectedClass.getBaseClass().toString());
    }

    private Dialog createClassSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        ArrayList<String> classNames = Lists.newArrayList();
        for(BaseClass baseClass : classes) {
            classNames.add(baseClass.toString());
        }
        String[] names = new String[classNames.size()];
        classNames.toArray(names);

        builder.setTitle("SELECT CLASS")
                .setItems(names, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        setSelectedClass(classes.get(which));
                    }
                })
                .setNegativeButton("CANCEL", new OnClickListener() {
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
