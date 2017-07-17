package com.ffl.todoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    EditText edNewItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //readItems();
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);
        edNewItems= (EditText)findViewById(R.id.edtNewItem);
       lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               items.remove(position);
               itemsAdapter.notifyDataSetChanged();
               WriteItems();
               return true;
           }
       });

    }
    public void populateArrayItems()
    {
        readItems();
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1, items);

    }
    public void onAddItem(View v){
     itemsAdapter.add(edNewItems.getText().toString());
        edNewItems.setText("");
        WriteItems();
    }
    private void readItems()
    {
        File filesDir = getFilesDir();
        File todofile=new File(filesDir,"todo.txt");
        try {
            items=new ArrayList<String>(FileUtils.readLines(todofile));
        }catch (IOException e){
            items= new ArrayList<String>();
        }
    }
    private void WriteItems()
    {
        File filesDir = getFilesDir();
        File todofile=new File(filesDir,"todo.txt");
        try {
            FileUtils.writeLines(todofile, items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
