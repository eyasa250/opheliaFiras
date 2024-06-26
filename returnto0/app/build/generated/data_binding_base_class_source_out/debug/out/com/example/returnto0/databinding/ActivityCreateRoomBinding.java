// Generated by view binder compiler. Do not edit!
package com.example.returnto0.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.returnto0.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityCreateRoomBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button buttonCreateRoom;

  @NonNull
  public final EditText editTextRoomName;

  private ActivityCreateRoomBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button buttonCreateRoom, @NonNull EditText editTextRoomName) {
    this.rootView = rootView;
    this.buttonCreateRoom = buttonCreateRoom;
    this.editTextRoomName = editTextRoomName;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCreateRoomBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCreateRoomBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_create_room, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCreateRoomBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonCreateRoom;
      Button buttonCreateRoom = ViewBindings.findChildViewById(rootView, id);
      if (buttonCreateRoom == null) {
        break missingId;
      }

      id = R.id.editTextRoomName;
      EditText editTextRoomName = ViewBindings.findChildViewById(rootView, id);
      if (editTextRoomName == null) {
        break missingId;
      }

      return new ActivityCreateRoomBinding((ConstraintLayout) rootView, buttonCreateRoom,
          editTextRoomName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
