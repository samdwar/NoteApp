package sam.com.noteapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sam.com.noteapp.R;
import sam.com.noteapp.adapters.HomeListAdapter;
import sam.com.noteapp.constants.Constant;
import sam.com.noteapp.listeners.OnListItemClickListener;
import sam.com.noteapp.pojo.NoteList;
import sam.com.noteapp.pojo.Notes;

public class HomeFragment extends Fragment implements OnListItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "NOTE_APP";
    private NoteList notes;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    private TextView createNoteButton;

    public HomeListAdapter getHomeListAdapter() {
        return homeListAdapter;
    }

    private HomeListAdapter homeListAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        createNoteButton = (TextView) view.findViewById(R.id.create_note_button);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Notes");

        /*Read data sent from Activity here*/
        notes = (NoteList) getArguments().getSerializable(Constant.LIST_OF_NOTES);

        /*Init recycler view*/
        recyclerView = (RecyclerView) view.findViewById(R.id.all_notes_list);
        homeListAdapter = new HomeListAdapter();
        homeListAdapter.setNotesList(notes.getNotesList());
        homeListAdapter.setOnListItemClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(homeListAdapter);

        createNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateNewNoteScreen();
            }
        });
        return view;
    }

    private void openCreateNewNoteScreen() {
        mListener.openCreateNewNoteScreen();
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

    @Override
    public void onClick(View view, int noteId) {
        Log.i(TAG, "onClick: item clicked  = " + noteId);
        mListener.onListItemClick(noteId);

    }

    @Override
    public void onLongPressClick(View view, int noteId) {
        mListener.onListItemLongClick(noteId);
    }

    public void refresh(List<Notes> notesList) {
        notes.setNotesList(notesList);
        homeListAdapter.notifyDataSetChanged();
    }

    public interface OnFragmentInteractionListener {
        void onListItemClick(int position);

        void onListItemLongClick(int position);

        void openCreateNewNoteScreen();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);  // Use filter.xml from step 1
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_action) {
            //Do whatever you want to do
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
