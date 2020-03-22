package com.example.whowroteit;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;

public class FetchBook extends AsyncTask <String, Void, String> {
    private TextView mAuthor;
    private TextView mTitle;
    private String[] params;
    //private Array params;

    public FetchBook(TextView mAuthor, TextView mTitle) {
        this.mAuthor = mAuthor;
        this.mTitle = mTitle;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject =	new	JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for(int	i =	0; i<itemsArray.length(); i++) {
                JSONObject book = itemsArray.getJSONObject(i);
                String title = null;
                String authors = null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (title != null && authors != null) {
                    mTitle.setText(title);
                    mAuthor.setText(authors);
                    return;
                }
            }

            mTitle.setText("No Results Found");
            mAuthor.setText("");

        }catch (Exception e) {
            mTitle.setText("No Results Found");
            mAuthor.setText("");
            e.printStackTrace();
        }


    }




    @Override
    protected String doInBackground(String... array) {
        return	NetworkUtils.getBookInfo(params[0]);

    }
}
