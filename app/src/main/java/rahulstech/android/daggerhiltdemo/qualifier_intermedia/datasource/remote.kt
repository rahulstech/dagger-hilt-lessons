package rahulstech.android.daggerhiltdemo.qualifier_intermedia.datasource

import android.util.Log
import rahulstech.android.daggerhiltdemo.qualifier_intermedia.Note
import rahulstech.android.daggerhiltdemo.qualifier_intermedia.NoteSource
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRemoteDataSource @Inject constructor(): NoteSource {

    private val allNotes = mutableMapOf<String, Note>()
    private val remoteIdMap = mutableMapOf<String,String>()

    override fun createNote(note: Note): Note {
        if (remoteIdMap.contains(note.remoteId)) {
            return allNotes[remoteIdMap[note.remoteId]]!!
        }
        note.localId = UUID.randomUUID().toString()
        allNotes[note.localId] = note
        remoteIdMap[note.remoteId] = note.localId
        Log.i("NoteRemoteDataSource", "newNote $note")
        return note
    }

    override fun getNotes(offset: Int, limit: Int): List<Note> {
        // return allNotes.values.filterIndexed { index,_ -> index >= offset && index < offset+limit }
        // the following approach is better that the above in this case
        return allNotes.values.toList()
            .drop(offset)
            .take(limit)
    }

    override fun getTotalNotesCount(): Int = allNotes.size
}