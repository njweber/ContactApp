package webersoftwaresolutions.contacts_nate_weber;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private ListView mListView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView)findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);

        try {
            int i = getIntent().getExtras().getInt("delete_id", -1);
            if(i != 0){
                Cursor data = mDatabaseHelper.getData();
                data.moveToPosition(i);
                String name = data.getString(0);
                mDatabaseHelper.delete(name);
            }
        } catch(Exception e){}

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent appInfo = new Intent(MainActivity.this, ContactDetailsActivity.class);
                appInfo.putExtra("id", i);
                startActivity(appInfo);
            }
        });

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //Handle item selection
        switch (item.getItemId()){
            case R.id.add:
                Intent i = new Intent(this, AddContactActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");
        Cursor data = mDatabaseHelper.getData();

        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(0));
        }
        ListAdapter adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);
    }
}
