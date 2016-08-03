package sam.com.noteapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import sam.com.noteapp.R;
import sam.com.noteapp.pojo.Notes;

public class CreateNoteFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EditText editTextHeader;
    private EditText editTextNote;
    private TextView textViewCreateButton;
    private Toolbar toolbar;
    private TextInputLayout textInputLayoutHeader;
    private TextInputLayout textInputLayoutNote;


    public CreateNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_note, container, false);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Create new note");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_18dp);

        textInputLayoutHeader = (TextInputLayout) view.findViewById(R.id.header_input_layout);
        editTextHeader = (EditText) view.findViewById(R.id.edit_text_for_header);

        textInputLayoutNote = (TextInputLayout) view.findViewById(R.id.note_input_layout);
        editTextNote = (EditText) view.findViewById(R.id.edit_text_for_content);
        textViewCreateButton = (TextView) view.findViewById(R.id.save_note_button);
        textViewCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String header = editTextHeader.getText().toString();
                if (TextUtils.isEmpty(editTextHeader.getText())) {
                    textInputLayoutHeader.setError(getString(R.string.err_msg_header));
                    return;
                }

                if (TextUtils.isEmpty(editTextNote.getText())) {
                    textInputLayoutNote.setError(getString(R.string.err_msg_note));
                    return;
                }

                String note = editTextNote.getText().toString();
                Notes notes = new Notes();
                notes.setNote(note);
                notes.setHeader(header);
                saveNewNote(notes);
            }
        });
        return view;
    }

    private void saveNewNote(Notes notes) {
        mListener.onNoteCreated(notes);
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
        void onNoteCreated(Notes notes);
    }
}
