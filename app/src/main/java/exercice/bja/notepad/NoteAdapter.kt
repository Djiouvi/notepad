package exercice.bja.notepad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(val notes: List<Note>, var onNoteClickListener: OnNoteClickListener) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById<CardView>(R.id.card_view)
        val titleView = cardView.findViewById<TextView>(R.id.title)
        val excerptView = cardView.findViewById<TextView>(R.id.excerpt)

        fun bind(note: Note) {
           cardView.setOnClickListener(clickListener)
           cardView.tag = position
           titleView.text = note.title
           excerptView.text = note.text
        }

        private val clickListener: View.OnClickListener = View.OnClickListener{
            onNoteClickListener.onNoteClicked(notes[adapterPosition], adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    interface OnNoteClickListener  {
        fun onNoteClicked(note: Note, index: Int)
    }
}