    package com.example.notatnik;

    import android.app.AlertDialog;
    import android.os.Build;
    import android.os.Bundle;
    import android.text.InputType;
    import android.view.View;
    import android.widget.ArrayAdapter;
    import android.widget.EditText;
    import android.widget.ListView;

    import androidx.annotation.RequiresApi;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.lifecycle.LiveData;

    import com.google.android.material.textfield.TextInputEditText;

    import java.util.List;
    import java.util.Objects;

    public class MainActivity extends AppCompatActivity {

        private TextInputEditText et_content;
        private Note note;
        private ListView list;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            et_content = findViewById(R.id.et_content);

            RoomNoteDatabase noteDatabase = RoomNoteDatabase.getDatabase(MainActivity.this);
            NoteDao noteDao = noteDatabase.noteDao();

            showNotesList(noteDao);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private void showNotesList(NoteDao noteDao) {
            list = findViewById(R.id.list);
            note = new Note(Objects.requireNonNull(et_content.getText()).toString());

            RoomNoteDatabase.databaseWriteExecutor.execute(() -> noteDao.insertAll(note));

            LiveData<List<Note>> notes = noteDao.getAll();
            notes.observe(this, words -> {
                ArrayAdapter<Note> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, words); //simple_list_item_1, words);
                list.setAdapter(adapter);
            });
        }

        public void addNote(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add new note");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                RoomNoteDatabase db = RoomNoteDatabase.getDatabase(MainActivity.this);
                NoteDao roomDao = db.noteDao();
                RoomNoteDatabase.databaseWriteExecutor.execute(() -> roomDao.insertAll(new Note(input.getText().toString())));
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        }

        public void deleteAllNotes(View view) {
            RoomNoteDatabase db = RoomNoteDatabase.getDatabase(MainActivity.this);
            NoteDao roomDao = db.noteDao();
            RoomNoteDatabase.databaseWriteExecutor.execute(roomDao::deleteAll);
        }
    }