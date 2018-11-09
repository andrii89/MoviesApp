package com.test.android.moviesapp;

import android.app.Activity;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import android.view.LayoutInflater;

import android.view.View;

public class RetryDialogFragment extends DialogFragment {

    public static RetryDialogFragment newInstance(){
        RetryDialogFragment fragment = new RetryDialogFragment();
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.retry_dialog_fragment, null);

        AlertDialog componentParametersAlertDialog = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(R.string.retry_button,
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .create();

        return componentParametersAlertDialog;
    }

    private void sendResult(int resultCode){
        if (getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
