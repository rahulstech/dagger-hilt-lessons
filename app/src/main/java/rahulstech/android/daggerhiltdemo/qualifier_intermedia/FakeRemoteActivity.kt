package rahulstech.android.daggerhiltdemo.qualifier_intermedia

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import rahulstech.android.daggerhiltdemo.databinding.ActivityFakeRemoteBinding
import javax.inject.Inject

/**
 * This activity fakes adding notes to the remote form different client.
 */

@AndroidEntryPoint
class FakeRemoteActivity : AppCompatActivity() {

    @Inject
    lateinit var noteRepository: NoteRepository

    private lateinit var binding: ActivityFakeRemoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFakeRemoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAddRemoteNote.setOnClickListener { addRemoteNote() }
        updateCounterLabel()
    }

    fun addRemoteNote() {
        val content = binding.noteContentInput.text.toString()
        if (content.isNotEmpty() && content.isNotBlank()) {
            noteRepository.createNote(content)
            Toast.makeText(this,"note added to remote source successfully", Toast.LENGTH_LONG).show()
            updateCounterLabel()
        }
        binding.noteContentInput.text.clear()
    }

    fun updateCounterLabel() {
        binding.counterLabel.text = "Remote Notes: ${noteRepository.getTotalRemoteNotesCount()}"
    }
}