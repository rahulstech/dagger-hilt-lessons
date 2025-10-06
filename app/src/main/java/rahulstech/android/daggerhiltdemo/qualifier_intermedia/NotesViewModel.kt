package rahulstech.android.daggerhiltdemo.qualifier_intermedia

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository
): ViewModel() {

    private val allNotes = mutableListOf<Note>()
    private var offset: Int = 0

    fun createNote(content: String) {
        noteRepository.createNote(content)
    }

    fun getNotes(): List<Note> = allNotes

    fun loadNextNotes(): Boolean {
        val notes = noteRepository.getNotes(offset, 5)
        offset += notes.size
        if (notes.isNotEmpty()) {
            allNotes.addAll(notes)
            return true
        }
        return false
    }
}