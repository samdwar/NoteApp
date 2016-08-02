package sam.com.noteapp;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import sam.com.noteapp.fragments.CreateNoteFragment;
import sam.com.noteapp.fragments.DetailsFragment;
import sam.com.noteapp.fragments.HomeFragment;

public class HomeActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
        DetailsFragment.OnFragmentInteractionListener, CreateNoteFragment.OnFragmentInteractionListener {

    private static final String TAG = "HomeActivity";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment, new HomeFragment());
        ft.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListItemClick(int position) {
        openDetailsFragment();
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

    private void openDetailsFragment() {
        Log.i(TAG, "onListItemClick: open detail fragment");

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailsFragment detailsFragment = new DetailsFragment();
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
    }
}
