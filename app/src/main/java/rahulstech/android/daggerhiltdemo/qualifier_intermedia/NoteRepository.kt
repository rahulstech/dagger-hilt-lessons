package rahulstech.android.daggerhiltdemo.qualifier_intermedia

import android.util.Log
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

fun Note.toLocal(): Note = copy(content, remoteId, localId)

fun Note.toRemote(): Note = copy(content, remoteId, localId)

/**
 * the intention of this project is to demonstrate when there is confusing constructor arguments
 * then how to guide hilt with @Qualifier. here i have two NoteSource instance - local and remote.
 * the NoteRepository uses both of those. but instead of the implementation class i used the common
 * interfaces, the NoteSource, as the constructor parameter. i created @LocalSource and @RemoteSource
 * qualifiers and in the NotesModules added the same qualifiers to corresponding implementation providing
 * methods. after using correct qualifiers hilt now can know which implementation to use for which parameter
 *
 * but why do i add NoteSource interface instead of the implementations. the reason is during test we can
 * use different implementation. but if i use a concrete implementation than the test will be difficult or impossible.
 *
 * @see NotesModule
 */

@ActivityRetainedScoped
class NoteRepository @Inject constructor(
    @LocalSource private val localSource: NoteSource,
    @RemoteSource private val remoteSource: NoteSource
) {

    fun createNote(content: String): Note {
        // create locally
        val localNote = Note(content)
        val locallySavedNote = localSource.createNote(localNote)

        // create remote
        val remoteNote = locallySavedNote.toRemote()
        val remotelySavedNote = remoteSource.createNote(remoteNote)

        // update the local note remoteId
        localNote.remoteId = remotelySavedNote.localId

        Log.i("NoteRepository", "locallySavedNote $locallySavedNote remotelySavedNote $remotelySavedNote")

        return localNote
    }

    fun getNotes(offset: Int = 0, limit: Int = 20): List<Note> {
        // get from local
        val localNotes = localSource.getNotes(offset,limit)

        Log.i("NoteRepository", "getNotes: offset=$offset limit=$limit local-size=${localNotes.size}")

        // if found return
        if (localNotes.isNotEmpty()) {
            return localNotes
        }

        val remoteNotes = remoteSource.getNotes(offset,limit)

        Log.i("NoteRepository", "getNotes: offset=$offset limit=$limit remote-size=${remoteNotes.size}")

        // if found save locally
        if (remoteNotes.isNotEmpty()) {
            return remoteNotes.map { note ->
                val localNote = note.toLocal()
                localSource.createNote(localNote)
            }
        }

        return emptyList()
    }

    fun getTotalRemoteNotesCount(): Int = remoteSource.getTotalNotesCount()
}