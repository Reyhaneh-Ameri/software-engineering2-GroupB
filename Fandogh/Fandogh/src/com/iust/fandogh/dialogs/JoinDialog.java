package com.iust.fandogh.dialogs;

import com.iust.fandogh.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * A dialog box for getting Server IP to join
 * @author FERi
 */
public class JoinDialog extends DialogFragment {
	/**
	 * An interface for delivering messages to activity that use from this class
	 * Activity must implements this interface to get contact
	 * @author FERi
	 */
	NoticeDialogListener mListener;
	public interface NoticeDialogListener {
        public void onDialogPositiveClick(String ip);
        public void onDialogNegativeClick();
    }
	
	/**
	 * Called when this dialog instance created
	 * Verify that the host activity implements the callback interface
	 * @author FERi
	 */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }
	
    /**
     * Called when dialog shown in screen
     * Set listeners and other f things
     * @author FERi
     */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View v = inflater.inflate(R.layout.dialog_join, null);
		final EditText ip = (EditText)v.findViewById(R.id.joinIp);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(v).setTitle("آدرس سرور بازی");
		builder.setPositiveButton("ملحق شدن", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				try {
					mListener.onDialogPositiveClick(ip.getText().toString());
				} catch(Exception ex) {
					mListener.onDialogPositiveClick("");
				}
			}
		}).setNegativeButton("منصرف شدن", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				mListener.onDialogNegativeClick();
				JoinDialog.this.getDialog().cancel();
			}
		});
		
		return builder.create();
	}
}
 