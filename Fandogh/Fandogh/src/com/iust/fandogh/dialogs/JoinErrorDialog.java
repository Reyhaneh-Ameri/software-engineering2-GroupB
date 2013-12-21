package com.iust.fandogh.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Dialog box for reporting invalid IP
 * @author FERi
 */
public class JoinErrorDialog extends DialogFragment {
	/**
	 * An interface for delivering messages to activity that use from this class
	 * Activity must implements this interface to get contact
	 * @author FERi
	 */
	NoticeDialogListener mListener;
	public interface NoticeDialogListener {
		public void onErrorDialogClick();
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
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Error!").setMessage("آدرس وارد شده دارای فرمت درست نمی باشد /n و یا سرور مورد نظر در دسترس نیست")
		.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				mListener.onErrorDialogClick();
			}
		});
		
		return builder.create();
	}
}