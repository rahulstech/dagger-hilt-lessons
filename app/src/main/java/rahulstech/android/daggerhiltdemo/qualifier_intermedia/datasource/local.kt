package rahulstech.android.daggerhiltdemo.qualifier_intermedia.datasource

import android.util.Log
import rahulstech.android.daggerhiltdemo.qualifier_intermedia.Note
import rahulstech.android.daggerhiltdemo.qualifier_intermedia.NoteSource
import java.util.UUID
import javax.inject.Inject

class NoteLocalDataSource @Inject constructor(): NoteSource {

    private val allNotes = mutableMapOf<String, Note>()

    override fun createNote(note: Note): Note {
        if (note.localId.isEmpty()) {
            note.localId = UUID.randomUUID().toString()
        }
        Log.i("NoteLocalDataSource", "newNote = $note")
        allNotes[note.localId] = note
        return note
    }

    override fun getNotes(offset: Int, limit: Int): List<Note> {
        return allNotes.values.toList() // guarantees the order
            .drop(offset) // removes first offset element
            .take(limit) // returns only first up to limit items
    }

    override fun getTotalNotesCount(): Int = allNotes.size
}