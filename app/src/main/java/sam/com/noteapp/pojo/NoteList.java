package sam.com.noteapp.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sam on 3/8/16.
 */
public class NoteList implements Serializable{

    public List<Notes> getNotesList() {
        return notesList;
    }

    public void setNotesList(List<Notes> notesList) {
        this.notesList = notesList;
    }

    List<Notes> notesList;
}
