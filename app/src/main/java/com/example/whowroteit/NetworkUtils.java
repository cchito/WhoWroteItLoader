package com.example.whowroteit;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private	static	final	String	LOG_TAG	=	NetworkUtils.class.getSimpleName();

    private	static	final	String	BOOK_BASE_URL="https://www.googleapis.com/books/v1/volumes?";
    private	static	final	String	QUERY_PARAM	="q";
    private	static	final	String	MAX_RESULTS	="maxResults";
    private	static	final	String	PRINT_TYPE	="printType";

    static	String	getBookInfo(String	queryString){
        HttpURLConnection urlConnection	=	null;
        BufferedReader reader	=	null;
        String	bookJSONString	=	null;

        //return	bookJSONString;

        try{
            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM,queryString)
                    .appendQueryParameter(MAX_RESULTS,"10")
                    .appendQueryParameter(PRINT_TYPE,"books")
                    .build();
            URL requestURL = new URL(builtURI.toString());

            urlConnection= (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream	= urlConnection.getInputStream();

            StringBuilder builder = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null); {
                builder.append(line + "\n");
            }

            if (builder.length() == 0) {
                return	null;
            }

            bookJSONString = builder.toString();


        }catch (IOException e){
            e.printStackTrace();
            return	null;

        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();

            }
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bookJSONString;
    }

}

