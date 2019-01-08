package io.kirikcoders.bitcse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by Kartik on 24-Jul-18.
 */
public class NotificationsFragment extends Fragment {
    private Spinner notifSpinner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notifications,container,false);
        notifSpinner = rootView.findViewById(R.id.selectTypeOfNotif);
        notifSpinner.setAdapter(ArrayAdapter.createFromResource(getContext(),R.array.notif_types,
                android.R.layout.simple_spinner_dropdown_item));
        return rootView;
    }
}
