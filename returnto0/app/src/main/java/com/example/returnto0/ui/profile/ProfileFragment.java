package com.example.returnto0.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.returnto0.R;
import com.example.returnto0.user.MainViewModel;


public class ProfileFragment extends Fragment {
    private TextView nameTextView;
    private TextView emailTextView;
    private MainViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nameTextView = view.findViewById(R.id.textView);
        emailTextView = view.findViewById(R.id.textView2);

        // Initialize ViewModel
        userViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Observe LiveData from ViewModel
        userViewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::updateUI);
        userViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), this::showError);

        // Fetch user details
        if (getArguments() != null) {
            String token = getArguments().getString("TOKEN_KEY");
            userViewModel.fetchUserDetails();
        }
    }

    private void updateUI(User user) {
        if (user != null) {
            nameTextView.setText(user.getName());
            emailTextView.setText(user.getEmail());
        }
    }

    private void showError(String error) {
        // Handle error situation, maybe show a Toast or Snackbar
        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_LONG).show();
    }
}
