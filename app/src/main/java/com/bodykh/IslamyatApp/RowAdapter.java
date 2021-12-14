package com.bodykh.IslamyatApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RowAdapter extends ArrayAdapter<Comment> {

    public RowAdapter(Context context, List<Comment> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_list, parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new TweetViewHolder();
            viewHolder.pseudo = convertView.findViewById(R.id.pseudo);
            viewHolder.text = convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        }

        Comment tweet = getItem(position);

        if (tweet.getImportant().equals("y")) {
            viewHolder.pseudo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_star, 0);


        }

        viewHolder.pseudo.setText(" " + tweet.getPseudo());
        viewHolder.text.setText(tweet.getText());

        return convertView;
    }

    private class TweetViewHolder {
        public TextView pseudo;
        public TextView text;
        public ImageView avatar;
    }
}

class Comment {

    private final String pseudo;
    private final String text;
    private final String important;


    public Comment(String pseudo, String text, String important) {

        this.pseudo = pseudo;
        this.text = text;
        this.important = important;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public String getText() {
        return this.text;
    }

    public String getImportant() {
        return this.important;
    }
}
