package exercice.bja.notepad

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(var title: String = "", var text: String = "", var fileName: String = "") : Parcelable