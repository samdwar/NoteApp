package sam.com.noteapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import sam.com.noteapp.R;
import sam.com.noteapp.pojo.Notes;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateNoteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateNoteFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EditText editTextHeader;
    private EditText editTextNote;
    private TextView textViewCreateButton;
    private Toolbar toolbar;

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


        editTextHeader = (EditText) view.findViewById(R.id.edit_text_for_header);
        editTextNote = (EditText) view.findViewById(R.id.edit_text_for_content);
        textViewCreateButton = (TextView) view.findViewById(R.id.save_note_button);
        textViewCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String header = editTextHeader.getText().toString();
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void onNoteCreated(Notes notes);
    }
}
