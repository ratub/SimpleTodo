package com.example.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private static final String TAG_POS = "position";
    private static final String TAG_ITEM = "item";

    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 20;

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvitems);

        // read items from the file
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        lvItems.setAdapter(itemsAdapter);

        // setup listener for deleting item on long click
        setupListViewDeleteListener();

        // setup listener for editing item on click
        setupListViewEditListener();
    }

    // delete item also deletes from the file
    private void setupListViewDeleteListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;


            }

        });

    }

    private void setupListViewEditListener () {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {

                //Toast.makeText(getApplicationContext(), "Item is" + pos, Toast.LENGTH_LONG).show();

                // Starting single contact activity
                Intent in = new Intent(getApplicationContext(),
                        EditTodo.class);
                // Pass extras to the calling activity for the position of the item and the item
                in.putExtra(TAG_POS, pos);
                in.putExtra(TAG_ITEM, items.get(pos).toString());

                // Call start activity for result to get the updated item
                startActivityForResult(in,REQUEST_CODE);

            }
        });

    }

    // ActivityOne.java, time to handle the result of the sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String name = data.getExtras().getString("name");

            // Get the extra from edit activity
            int position=data.getIntExtra(TAG_POS,0);
            String item=  data.getStringExtra(TAG_ITEM);

            // Update the item in the array and called notify for adapter
            items.set(position,item);
            itemsAdapter.notifyDataSetChanged();

            // Toast the name to display temporarily on screen
            //Toast.makeText(this, "pos is "+position, Toast.LENGTH_SHORT).show();
        }
    }

    // Add new item, adds to the file
    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();

        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    // read items for todo.txt file
    private void readItems() {
        File filesDir= getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));

        } catch (IOException e){
            items = new ArrayList<String>();
        }

    }

    // write items to todo.txt file
    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(todoFile,items);

        } catch (IOException e) {
            e.printStackTrace();

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
