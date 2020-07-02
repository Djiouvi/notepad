package exercice.bja.notepad

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import exercice.bja.notepad.NoteAdapter.OnNoteClickListener
import kotlinx.android.synthetic.main.activity_note_list.*

lateinit var notes: MutableList<Note>
lateinit var adapter: NoteAdapter

class NoteListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        setSupportActionBar(toolbar)

        create_note_fab.setOnClickListener {
            createNewNote()
        }

        notes = mutableListOf()
        notes.add(Note("Note 1", "Blablabla"))
        notes.add(Note("Memo", "C'est une super blague pour le coup"))
        notes.add(Note("Memo 2", "Je suis bientot riche"))
        notes.add(Note("Test", "Super mega test"))

        adapter = NoteAdapter(notes, onNoteClickListener)

        notes_recycler_view.layoutManager = LinearLayoutManager(this)
        notes_recycler_view.adapter = adapter
    }

    private var onNoteClickListener: OnNoteClickListener = object : OnNoteClickListener {
        override fun onNoteClicked(note: Note, index: Int) {
            showNoteDetail(index)
        }
    }

    private fun showNoteDetail(noteIndex: Int) {
        val note = if (noteIndex < 0) Note() else notes[noteIndex]

        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE, note)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, noteIndex)

        startActivityForResult(intent, NoteDetailActivity.REQUEST_EDIT_NOTE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK || data == null)
            return
        when (requestCode) {
            NoteDetailActivity.REQUEST_EDIT_NOTE -> processEditNoteResult(data)
        }
    }

    private fun processEditNoteResult(data: Intent) {
        val noteIndex = data.getIntExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, -1)
        val note = data.getParcelableExtra<Note>(NoteDetailActivity.EXTRA_NOTE)

        saveNote(note, noteIndex)
    }

    private fun saveNote(note: Note, noteIndex: Int) {
        if (noteIndex < 0) {
            notes.add(0, note)
        } else {
            notes[noteIndex] = note
        }
        adapter.notifyDataSetChanged()
    }

    private fun createNewNote() {
        showNoteDetail(-1)
    }
}