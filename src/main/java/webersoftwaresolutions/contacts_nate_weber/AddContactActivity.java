package webersoftwaresolutions.contacts_nate_weber;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class AddContactActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    EditText name, street, city, state, zip, email, phone;
    Button saveContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        mDatabaseHelper = new DatabaseHelper(this);

        saveContact = (Button) findViewById(R.id.saveContact);
        name = (EditText) findViewById(R.id.contactEdit);
        street = (EditText) findViewById(R.id.addressStreet);
        city = (EditText) findViewById(R.id.addressCity);
        state = (EditText) findViewById(R.id.addressState);
        zip = (EditText) findViewById(R.id.addressZip);
        email = (EditText) findViewById(R.id.contactEmail);
        phone = (EditText) findViewById(R.id.contactPhone);

        AddData();
    }

    public void AddData() {
        saveContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean isInserted = mDatabaseHelper.addData(
                        name.getText().toString(),
                        street.getText().toString(),
                        city.getText().toString(),
                        state.getText().toString(),
                        zip.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString());
                if (isInserted == true) {
                    Toast.makeText(AddContactActivity.this, "Date Inserted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddContactActivity.this, "Date NOT Inserted", Toast.LENGTH_LONG).show();
                }
                name.setText("");
                street.setText("");
                city.setText("");
                state.setText("");
                zip.setText("");
                email.setText("");
                phone.setText("");
            }
        });
    }
}