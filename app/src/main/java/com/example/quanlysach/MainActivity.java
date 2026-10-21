package com.example.quanlysach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private TextView tvTotalBooks, tvReadBooks, tvUnreadBooks;
    private Button btnAddBook;
    private ListView lvBooks;

    private ArrayList<Book> bookList;
    private BookAdapter bookAdapter;

    private ActivityResultLauncher<Intent> addLauncher;
    private ActivityResultLauncher<Intent> detailLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvTotalBooks = findViewById(R.id.tvTotalBooks);
        tvReadBooks = findViewById(R.id.tvReadBooks);
        tvUnreadBooks = findViewById(R.id.tvUnreadBooks);
        btnAddBook = findViewById(R.id.btnAddBook);
        lvBooks = findViewById(R.id.lvBooks);

        bookList = new ArrayList<>();
        bookList.add(new Book(UUID.randomUUID().toString(), "Lập trình Android cơ bản", "Nguyễn Văn A", "Công nghệ", "Đang đọc", 4f, ""));
        bookList.add(new Book(UUID.randomUUID().toString(), "Đắc nhân tâm", "Dale Carnegie", "Kỹ năng sống", "Đã đọc", 5f, ""));
        bookList.add(new Book(UUID.randomUUID().toString(), "Dế Mèn phiêu lưu ký", "Tô Hoài", "Văn học", "Chưa đọc", 0f, ""));

        bookAdapter = new BookAdapter(this, bookList);
        lvBooks.setAdapter(bookAdapter);

        updateStatistics();

        addLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Book newBook = (Book) result.getData().getSerializableExtra("book");
                            if (newBook != null) {
                                bookList.add(newBook);
                                bookAdapter.notifyDataSetChanged();
                                updateStatistics();
                            }
                        }
                    }
                }
        );

        detailLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            String action = result.getData().getStringExtra("action");
                            int position = result.getData().getIntExtra("position", -1);
                            
                            if (position != -1) {
                                if ("update".equals(action)) {
                                    Book updatedBook = (Book) result.getData().getSerializableExtra("book");
                                    bookList.set(position, updatedBook);
                                } else if ("delete".equals(action)) {
                                    bookList.remove(position);
                                }
                                bookAdapter.notifyDataSetChanged();
                                updateStatistics();
                            }
                        }
                    }
                }
        );

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
                addLauncher.launch(intent);
            }
        });

        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book selectedBook = bookList.get(position);
                Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
                intent.putExtra("book", selectedBook);
                intent.putExtra("position", position);
                detailLauncher.launch(intent);
            }
        });
    }

    private void updateStatistics() {
        int total = bookList.size();
        int read = 0;
        int unread = 0;

        for (Book book : bookList) {
            if ("Đã đọc".equals(book.getStatus())) {
                read++;
            } else {
                unread++;
            }
        }

        tvTotalBooks.setText(String.valueOf(total));
        tvReadBooks.setText(String.valueOf(read));
        tvUnreadBooks.setText(String.valueOf(unread));
    }
}