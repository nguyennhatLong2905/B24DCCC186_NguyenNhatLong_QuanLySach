package com.example.quanlysach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(@NonNull Context context, @NonNull ArrayList<Book> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_book, parent, false);
        }

        Book book = getItem(position);

        if (book != null) {
            TextView tvTitle = convertView.findViewById(R.id.tvBookTitle);
            TextView tvAuthorGenre = convertView.findViewById(R.id.tvBookAuthorGenre);
            TextView tvStatus = convertView.findViewById(R.id.tvBookStatus);
            RatingBar rbRating = convertView.findViewById(R.id.rbBookRating);

            tvTitle.setText(book.getTitle());
            tvAuthorGenre.setText(book.getAuthor() + " - " + book.getGenre());
            tvStatus.setText("Tình trạng: " + book.getStatus());
            rbRating.setRating(book.getRating());
        }

        return convertView;
    }
}