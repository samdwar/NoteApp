package sam.com.noteapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import sam.com.noteapp.R;
import sam.com.noteapp.constants.Constant;
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

    private static final String TAG = "DetailsFragment";
    private EditText editText;
    private TextView editTextView;
    private boolean isOpenForEdit;
    private OnFragmentInteractionListener mListener;
    private Notes notes;
    private TextInputLayout textInputLayout;
    private AlertDialog alert;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        final Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        setHasOptionsMenu(true);
        textInputLayout = (TextInputLayout) view.findViewById(R.id.edit_note_input_layout);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_18dp);
        notes = (Notes) getArguments().getSerializable(Constant.NOTE);
        editTextView = (TextView) view.findViewById(R.id.edit_note_button);
        editText = (EditText) view.findViewById(R.id.edit_text_for_content);
        editText.setEnabled(false);
        editText.setText(notes.getNote());
        actionBar.setTitle(notes.getHeader());
        isOpenForEdit = false;
        editTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpenForEdit) {
                    if (TextUtils.isEmpty(editText.getText())) {
                        textInputLayout.setError(getString(R.string.edit_err_msg));
                        return;
                    }
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
        editText.setFocusable(true);
        editTextView.setText(getString(R.string.done));
        editText.setText(notes.getNote());
        editText.clearFocus();
        isOpenForEdit = true;
        editText.setSingleLine(false);
    }

    private void saveAndGoBackToPrevScreen(Notes editedNote) {
        mListener.onEditSaveNote(editedNote);
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
        void onEditSaveNote(Notes editedNote);

        void onDeleteNote(Notes noteForDelete);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);  // Use filter.xml from step 1
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_action) {
            showPopUpForDelete(notes);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showPopUpForDelete(final Notes notes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(notes.getHeader())
                .setTitle(Constant.DELETE_MESSAGE);

        builder.setPositiveButton(Constant.OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alert.dismiss();
                mListener.onDeleteNote(notes);
                getFragmentManager().popBackStack();

            }
        });

        builder.setNegativeButton(Constant.CANCEL, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alert.dismiss();
            }
        });
        alert = builder.create();
        alert.show();
    }
}
