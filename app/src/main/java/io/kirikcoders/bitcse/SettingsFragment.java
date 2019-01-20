package io.kirikcoders.bitcse;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.kirikcoders.bitcse.utils.Constants;
import io.kirikcoders.bitcse.utils.UserDetails;

/**
 * Created by Kartik on 24-Jul-18.
 */
public class SettingsFragment extends Fragment {
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private RelativeLayout layout;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        layout = view.findViewById(R.id.signout_layout);
        layout.setOnClickListener((rootView)->{
                Toast.makeText(getContext(), "You have been signed out", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                UserDetails details = new UserDetails(getContext(), Constants.USER_PREFERENCE_FILE);
                details.deleteAll();
                getActivity().startActivity(new Intent(getActivity(),LoginActivity.class));
                getActivity().finish();
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings,container,false);
        return rootView;
    }
}
