package com.example.returnto0.ui.task;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import com.example.returnto0.R;
import com.example.returnto0.room.Room;

public class addtaskFragment extends Fragment {

    private AddtaskViewModel mViewModel;

    public static addtaskFragment newInstance() {
        return new addtaskFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addtask, container, false);
    }

    @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("roomobject", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                try {
                    // Extract the room object from the bundle
                    Room room = (Room) result.getSerializable("ROOM_OBJECT");
                    if (room != null) {
                        // Do whatever you need to do with the room object
                        // For example, you can update UI elements with room information
                    }
                } catch (Exception e) {
                    Log.e("addtaskFragment", "Error handling fragment result", e);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddtaskViewModel.class);
        // TODO: Use the ViewModel
    }
}
