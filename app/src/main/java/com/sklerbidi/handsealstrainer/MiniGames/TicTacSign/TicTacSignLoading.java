package com.sklerbidi.handsealstrainer.MiniGames.TicTacSign;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sklerbidi.handsealstrainer.R;

public class TicTacSignLoading extends Fragment {

    ProgressBar pb_loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tic_tac_sign_loading, container, false);

        findView(view);

        return view;
    }

    private void findView(View view) {
        pb_loading = view.findViewById(R.id.pb_loading);
    }
}