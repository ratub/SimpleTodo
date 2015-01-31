package com.example.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by rbhavsar on 1/29/2015.
 */
public class EditTodo extends ActionBarActivity {
    int position = 0;
    String item = null;
    private static final String TAG_POS = "position";
    private static final String TAG_ITEM = "item";
    EditText edittxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);
        Intent intent = getIntent();

        // Get extras if you stored.
        position=intent.getIntExtra(TAG_POS,0);
        item=  intent.getStringExtra(TAG_ITEM);

        //Toast.makeText(getApplicationContext(), " Item Pos is " + position, Toast.LENGTH_LONG).show();

        edittxt = (EditText) findViewById(R.id.edittxt);
        edittxt.setText(item);

    }


    public void btnEditTodo(View view) {
        EditText edittext = (EditText) findViewById(R.id.edittxt);
        String text =  edittext.getText().toString();
        //Toast.makeText(getApplicationContext(), "New Item is " + text, Toast.LENGTH_LONG).show();

        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra(TAG_POS, position);
        data.putExtra(TAG_ITEM, text);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent

    }

}
