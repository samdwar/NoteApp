package sam.com.noteapp;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import sam.com.noteapp.constants.Constant;
import sam.com.noteapp.dao.DataBaseHelper;
import sam.com.noteapp.fragments.CreateNoteFragment;
import sam.com.noteapp.fragments.DeleteFragment;
import sam.com.noteapp.fragments.DetailsFragment;
import sam.com.noteapp.fragments.HomeFragment;
import sam.com.noteapp.pojo.NoteList;
import sam.com.noteapp.pojo.Notes;

public class HomeActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
        DetailsFragment.OnFragmentInteractionListener, CreateNoteFragment.OnFragmentInteractionListener,
        FragmentManager.OnBackStackChangedListener, DeleteFragment.OnFragmentInteractionListener {

    private static final String TAG = "HomeActivity";
    private Toolbar toolbar;
    private DataBaseHelper dataBaseHelper;
    private HomeFragment homeFragment;
    private AlertDialog alert;
    private CoordinatorLayout coordinatorLayout;
    private final static String SUCCESS_MSG_FOR_NEW_NOTE = "Your note has been saved successfully !";
    private final static String SUCCESS_MSG_FOR_UPDATE_NOTE = "Your note has been updated successfully !";
    private final static String SUCCESS_MSG_FOR_DELETE_NOTE = "Your note has been deleted successfully !";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_for_snackbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        List<Notes> notesList = dataBaseHelper.getAllTags();
        openHomeFragment(notesList);
    }

    private void showSnackbarMessage(String message) {
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                snackbar.show();
            }
        }, 1000);

    }

    private void openHomeFragment(List<Notes> notesList) {
        android.support.v4.app.FragmentManager supportFragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        Bundle bundle = new Bundle();
        NoteList listOfNotes = new NoteList();
        listOfNotes.setNotesList(notesList);
        bundle.putSerializable(Constant.LIST_OF_NOTES, listOfNotes);
        homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment, homeFragment, Constant.HOME_FRAGMENT);
        fragmentTransaction.commit();
    }

    @Override
    public void onDeleteNote(Notes note) {
        deleteNote(note.getId());
    }

    @Override
    public void onNoteCreated(Notes notes) {
        dataBaseHelper.createNote(notes);
        List<Notes> notesList = dataBaseHelper.getAllTags();
        homeFragment.refresh(notesList);
        shouldDisplayHomeUp();
        showSnackbarMessage(SUCCESS_MSG_FOR_NEW_NOTE);
    }

    @Override
    public void onEditSaveNote(Notes editedNote) {
        dataBaseHelper.updateNote(editedNote);
        List<Notes> notesList = dataBaseHelper.getAllTags();
        homeFragment.refresh(notesList);
        showSnackbarMessage(SUCCESS_MSG_FOR_UPDATE_NOTE);
    }

    @Override
    public void onListItemClick(int position) {
        Notes notes = dataBaseHelper.getNote(position);
        openDetailsFragment(notes);
    }

    @Override
    public void onListItemLongClick(int position) {
        Notes notes = dataBaseHelper.getNote(position);
        showPopUpForDelete(notes);
    }

    private void showPopUpForDelete(final Notes notes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(notes.getHeader())
                .setTitle(Constant.DELETE_MESSAGE);

        builder.setPositiveButton(Constant.OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alert.dismiss();
                openScreenForNoteDeletion(notes);

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

    private void openScreenForNoteDeletion(Notes notes) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        DeleteFragment deleteFragment = new DeleteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.NOTE, notes);
        deleteFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment, deleteFragment, Constant.DELETE_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    private void deleteNote(final int id) {

        dataBaseHelper.deleteNote(id);
        List<Notes> notesList = dataBaseHelper.getAllTags();
        homeFragment.refresh(notesList);
        shouldDisplayHomeUp();
        showSnackbarMessage(SUCCESS_MSG_FOR_DELETE_NOTE);

    }

    @Override
    public void openCreateNewNoteScreen() {

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        CreateNoteFragment createNoteFragment = new CreateNoteFragment();
        fragmentTransaction.replace(R.id.fragment, createNoteFragment, Constant.CREATE_NOTE_FRAGMENT);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    private void openDetailsFragment(Notes notes) {

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.NOTE, notes);
        detailsFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment, detailsFragment, Constant.DETAIL_NOTE_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
        shouldDisplayHomeUp();
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    private void shouldDisplayHomeUp() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        shouldDisplayHomeUp();
        getSupportFragmentManager().popBackStack();
        return true;
    }
}
