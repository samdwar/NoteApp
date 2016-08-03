package sam.com.noteapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import sam.com.noteapp.pojo.NoteList;
import sam.com.noteapp.pojo.Notes;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    private EditText editText;
    private TextView editTextView;
    private boolean isOpenForEdit;
    private OnFragmentInteractionListener mListener;
    private Notes notes;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        final View editButtonView = view.findViewById(R.id.edit_note_view);
        notes = (Notes) getArguments().getSerializable("NOTES");
        editTextView = (TextView) view.findViewById(R.id.edit_note_button);
        editText = (EditText) view.findViewById(R.id.edit_text_for_content);
        editText.setEnabled(false);
        editText.setFocusable(false);
        editText.setText(notes.getNote());
        isOpenForEdit = false;
        editTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpenForEdit) {
                    String editedText = editText.getText().toString();
                    notes.setNote(editedText);
                    saveAndGoBackToPrevScreen(notes);
                } else {
                    editTheContent();
                }
            }
        });
        return view;
    }

    private void editTheContent() {
        editText.setEnabled(true);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setFocusable(true);
        editTextView.setText("DONE");
        isOpenForEdit = true;
        editText.setSingleLine(false);
        editText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);

    }

    private void saveAndGoBackToPrevScreen(Notes editedNote) {
        mListener.onEditSaveNote(editedNote);
        getFragmentManager().popBackStack();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

        void onEditSaveNote(Notes editedNote);
    }
}
