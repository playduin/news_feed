package playduin.newsfeed.ui.network;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import playduin.newsfeed.R;

public class NetworkErrorDialogFragment extends DialogFragment {
    public static final String TAG = "NetworkErrorDialogFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.network_error_message))
                .setNeutralButton(R.string.ok, (dialog, which) -> {
                })
                .create();
    }
}
