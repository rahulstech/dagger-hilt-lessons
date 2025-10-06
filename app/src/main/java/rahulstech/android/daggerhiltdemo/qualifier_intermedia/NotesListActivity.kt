package rahulstech.android.daggerhiltdemo.qualifier_intermedia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import rahulstech.android.daggerhiltdemo.R
import rahulstech.android.daggerhiltdemo.databinding.ActivityNotesListBinding
import javax.inject.Inject

class NoteViewHolder(itemView: View) {
    val contentView: TextView = itemView.findViewById<TextView>(android.R.id.text1)
}

class NotesAdapter(context: Context): BaseAdapter() {

    private val layoutInflater = LayoutInflater.from(context)
    private var notes: List<Note> = emptyList()

    public fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getCount(): Int = notes.size

    override fun getItem(position: Int): Note = notes[position]

    override fun getItemId(position: Int): Long = notes[position].hashCode().toLong()

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var view: View
        var vh: NoteViewHolder
        if (null == convertView) {
            view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            vh = NoteViewHolder(view)
            view.tag = vh
        }
        else {
            view = convertView
            vh = view.tag as NoteViewHolder
        }
        val note = getItem(position)
        vh.contentView.text = note.content
        return view
    }
}


/**
 * this activity let you add notes and shows a list of notes available. notes are loaded using paging, 5 notes at a time.
 * at first notes from local source is loaded. if local source is exhausted then it tries to load more notes from remote source.
 * if notes found in remote source, notes are first added to the local source then displayed.
 * the FakeRemoteActivity simulated the second client which maintains it own local source and adds notes to the remote source.
 * the remote source is the single source of truth. therefore all the remotely added notes will be available here also. here the
 * scope of the repository and the remote source is important. repository is scoped at activity lifecycle where as remote source is
 * scoped at singleton. therefore each client i.e. has it own note repository instance eventually then local source but only one remote source
 */

@AndroidEntryPoint
class NotesListActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: NoteRepository

    private val viewModel: NotesViewModel by viewModels<NotesViewModel>()

    private lateinit var binding: ActivityNotesListBinding

    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = NotesAdapter(this)
        binding.notesList.adapter = adapter
        binding.btnAddNote.setOnClickListener { addNote() }
        loadMore()
    }

    fun addNote() {
        val content = binding.noteContentInput.text.toString()
        if (content.isNotEmpty()) {
            repository.createNote(content)
            loadMore()
        }
        binding.noteContentInput.text.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notes_list_activity,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.load_more -> loadMore()
            R.id.goto_remote_activity -> {
                startActivity(Intent(this, FakeRemoteActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun loadMore() {
        if (viewModel.loadNextNotes()) {
            val allNotes = viewModel.getNotes()
            adapter.setNotes(allNotes)
            Log.i("NotesListActivity","total notes ${allNotes.size}")
        }
    }
}