package com.example.quanlysach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvAuthorGenre, tvStatus, tvNote;
    private RatingBar rbRating;
    private Button btnMarkReading, btnMarkRead, btnDelete, btnBack;

    private Book book;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        tvTitle = findViewById(R.id.tvDetailTitle);
        tvAuthorGenre = findViewById(R.id.tvDetailAuthorGenre);
        tvStatus = findViewById(R.id.tvDetailStatus);
        tvNote = findViewById(R.id.tvDetailNote);
        rbRating = findViewById(R.id.rbDetailRating);
        btnMarkReading = findViewById(R.id.btnMarkReading);
        btnMarkRead = findViewById(R.id.btnMarkRead);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("book")) {
            book = (Book) intent.getSerializableExtra("book");
            position = intent.getIntExtra("position", -1);

            tvTitle.setText(book.getTitle());
            tvAuthorGenre.setText(book.getAuthor() + " - " + book.getGenre());
            tvStatus.setText("Tình trạng: " + book.getStatus());
            rbRating.setRating(book.getRating());
            
            if (book.getNote() != null && !book.getNote().isEmpty()) {
                tvNote.setText(book.getNote());
            }

            if ("Đang đọc".equals(book.getStatus())) {
                btnMarkReading.setEnabled(false);
                btnMarkReading.setVisibility(View.GONE);
            }

            if ("Đã đọc".equals(book.getStatus())) {
                btnMarkReading.setEnabled(false);
                btnMarkReading.setVisibility(View.GONE);
                btnMarkRead.setEnabled(false);
                btnMarkRead.setVisibility(View.GONE);
            }
        }

        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    book.setRating(rating);
                }
            }
        });

        btnMarkReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setStatus("Đang đọc");
                tvStatus.setText("Tình trạng: Đang đọc");
                btnMarkReading.setEnabled(false);
                btnMarkReading.setVisibility(View.GONE);
                
                // Mở lại nút Đã đọc nếu cần
                btnMarkRead.setEnabled(true);
                btnMarkRead.setVisibility(View.VISIBLE);
            }
        });

        btnMarkRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setStatus("Đã đọc");
                tvStatus.setText("Tình trạng: Đã đọc");
                
                btnMarkReading.setEnabled(false);
                btnMarkReading.setVisibility(View.GONE);
                
                btnMarkRead.setEnabled(false);
                btnMarkRead.setVisibility(View.GONE);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("action", "delete");
                resultIntent.putExtra("position", position);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("action", "update");
                resultIntent.putExtra("position", position);
                resultIntent.putExtra("book", book);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("action", "update");
        resultIntent.putExtra("position", position);
        resultIntent.putExtra("book", book);
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }
}