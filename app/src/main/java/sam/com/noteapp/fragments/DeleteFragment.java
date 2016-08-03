package sam.com.noteapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import sam.com.noteapp.R;
import sam.com.noteapp.constants.Constant;
import sam.com.noteapp.pojo.Notes;

public class DeleteFragment extends Fragment {

    private EditText editText;
    private TextView deleteTextView;
    private boolean isOpenForEdit;
    private OnFragmentInteractionListener mListener;
    private Notes notes;

    public DeleteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete, container, false);
        final Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_18dp);
        final View deleteButtonView = view.findViewById(R.id.delete_note_view);
        notes = (Notes) getArguments().getSerializable(Constant.NOTE);
        deleteTextView = (TextView) view.findViewById(R.id.delete_note_button);
        editText = (EditText) view.findViewById(R.id.edit_text_for_content);
        editText.setEnabled(false);
        editText.setFocusable(false);
        editText.setText(notes.getNote());

        actionBar.setTitle(notes.getHeader());
        isOpenForEdit = false;
        deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNote(notes);
            }
        });
        return view;
    }

    private void deleteNote(Notes notes) {
        mListener.onDeleteNote(notes);
        getFragmentManager().popBackStack();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onDeleteNote(Notes editedNote);
    }
}
