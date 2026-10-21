package com.example.quanlysach;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class AddBookActivity extends AppCompatActivity {

    private EditText etTitle, etAuthor, etNote;
    private Spinner spGenre;
    private RadioGroup rgStatus;
    private RatingBar rbRating;
    private Button btnSave, btnBackAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etNote = findViewById(R.id.etNote);
        spGenre = findViewById(R.id.spGenre);
        rgStatus = findViewById(R.id.rgStatus);
        rbRating = findViewById(R.id.rbRating);
        btnSave = findViewById(R.id.btnSave);
        btnBackAdd = findViewById(R.id.btnBackAdd);

        String[] genres = {"Văn học", "Khoa học", "Công nghệ", "Kỹ năng sống", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenre.setAdapter(adapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String author = etAuthor.getText().toString().trim();
                String genre = spGenre.getSelectedItem().toString();
                String note = etNote.getText().toString().trim();
                float rating = rbRating.getRating();

                if (TextUtils.isEmpty(title)) {
                    etTitle.setError("Không được để trống");
                    return;
                }
                if (TextUtils.isEmpty(author)) {
                    etAuthor.setError("Không được để trống");
                    return;
                }

                int checkedId = rgStatus.getCheckedRadioButtonId();
                String status = "Chưa đọc";
                if (checkedId == R.id.rbReading) {
                    status = "Đang đọc";
                } else if (checkedId == R.id.rbRead) {
                    status = "Đã đọc";
                }

                Book book = new Book(UUID.randomUUID().toString(), title, author, genre, status, rating, note);

                Intent intent = new Intent();
                intent.putExtra("book", book);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnBackAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}