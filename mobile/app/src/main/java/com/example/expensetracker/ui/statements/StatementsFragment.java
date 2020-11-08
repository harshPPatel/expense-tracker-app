package com.example.expensetracker.ui.statements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.expensetracker.R;

public class StatementsFragment extends Fragment {

    private StatementsViewModel statementsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statementsViewModel =
                ViewModelProviders.of(this).get(StatementsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statements, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        statementsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}