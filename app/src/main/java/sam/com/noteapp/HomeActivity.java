package sam.com.noteapp;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.List;

import sam.com.noteapp.dao.DataBaseHelper;
import sam.com.noteapp.fragments.CreateNoteFragment;
import sam.com.noteapp.fragments.DetailsFragment;
import sam.com.noteapp.fragments.HomeFragment;
import sam.com.noteapp.pojo.NoteList;
import sam.com.noteapp.pojo.Notes;

public class HomeActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
        DetailsFragment.OnFragmentInteractionListener, CreateNoteFragment.OnFragmentInteractionListener, FragmentManager.OnBackStackChangedListener {

    private static final String TAG = "HomeActivity";
    private Toolbar toolbar;
    private DataBaseHelper dataBaseHelper;
    HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        Notes notes = new Notes();
        notes.setHeader("test");
        notes.setNote("hi this is test");

        long id = dataBaseHelper.createNote(notes);

        Log.i(TAG, "onCreate: " + id);

        notes = dataBaseHelper.getNote(id);
        System.out.println("notes = " + notes);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        List<Notes> notesList = dataBaseHelper.getAllTags();
        openHomeFragment(notesList, false);
    }

    private void openHomeFragment(List<Notes> notesList, boolean reload) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        NoteList listOfNotes = new NoteList();
        listOfNotes.setNotesList(notesList);
        bundle.putSerializable("LIST_OF_NOTES", listOfNotes);
        homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        ft.add(R.id.fragment, homeFragment, "HomeFragment");
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onNoteCreated(Notes notes) {
        dataBaseHelper.createNote(notes);
        List<Notes> notesList = dataBaseHelper.getAllTags();
        homeFragment.refresh(notesList);
    }

    @Override
    public void onEditSaveNote(Notes editedNote) {
        dataBaseHelper.updateNote(editedNote);
        List<Notes> notesList = dataBaseHelper.getAllTags();
        homeFragment.refresh(notesList);
    }

    @Override
    public void onListItemClick(int position) {
        Notes notes = dataBaseHelper.getNote(position + 1);
        openDetailsFragment(notes);
    }

    @Override
    public void openCreateNewNoteScreen() {
        Log.i(TAG, "onListItemClick: open create new note fragment");

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CreateNoteFragment createNoteFragment = new CreateNoteFragment();
        fragmentTransaction.replace(R.id.fragment, createNoteFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void openDetailsFragment(Notes notes) {
        Log.i(TAG, "onListItemClick: open detail fragment");

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("NOTES", notes);
        detailsFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment, detailsFragment);
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
        //Enable Up button only  if there are entries in the back stack
        boolean canback = getSupportFragmentManager().getBackStackEntryCount() > 0;
        Log.i(TAG, "shouldDisplayHomeUp: " + canback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack
        Log.i(TAG, "onSupportNavigateUp: called");
        shouldDisplayHomeUp();
        getSupportFragmentManager().popBackStack();
        return true;
    }
}
