package rahulstech.android.daggerhiltdemo.qualifier_intermedia

data class Note (
    val content: String,
    var localId: String = "",
    var remoteId: String = ""
)

interface NoteSource {

    fun createNote(note: Note): Note

    fun getNotes( offset: Int, limit: Int): List<Note>

    fun getTotalNotesCount(): Int
}
