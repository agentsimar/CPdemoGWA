package com.example.akal.cpdemogwa;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignUPActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    @InjectView(R.id.editTextName)
    EditText eTxtName;

    @InjectView(R.id.editTextEmail)
    EditText eTxtEmail;

    @InjectView(R.id.editTextPassword)
    EditText eTxtPassword;

    @InjectView(R.id.spinnerCity)
    Spinner spCity;

    @InjectView(R.id.radioButtonMale)
    RadioButton rbMale;

    @InjectView(R.id.radioButtonFemale)
    RadioButton rbFemale;

    @InjectView(R.id.buttonSignUp)
    Button btnSignUP;
    ArrayAdapter<String> adapter;

    User user;
    ContentResolver resolver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);

        resolver = getContentResolver();


        user = new User();

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        adapter.add("---select city");
        adapter.add("Ludhiana");
        adapter.add("chandigarh");
        adapter.add("Delhi");
        adapter.add("Pune");
        spCity.setAdapter(adapter);
        spCity.setOnItemSelectedListener(this);
        rbMale.setOnClickListener(this);
        rbFemale.setOnClickListener(this);

        btnSignUP.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.buttonSignUp:
                user.setName(eTxtName.getText().toString().trim());
                user.setEmail(eTxtEmail.getText().toString().trim());
                user.setPassword(eTxtPassword.getText().toString().trim());
                insertUser();
                break;

            case R.id.radioButtonMale:
                user.setGender("Male");
                break;

            case R.id.radioButtonFemale:
                user.setGender("Female");
                break;

        }


    }
    void insertUser() {
        ContentValues values = new ContentValues();
        values.put(Util.COL_NAME,user.getName());
        values.put(Util.COL_EMAIL,user.getEmail());
        values.put(Util.COL_PASSWORD,user.getPassword());
        values.put(Util.COL_GENDER,user.getGender());
        values.put(Util.COL_CITY,user.getCity());

      Uri uri=resolver.insert(Util.USER_URI,values);
        Toast.makeText(this,user.getName()+"registered with id"+uri.getLastPathSegment(),Toast.LENGTH_LONG).show();
        clearfields();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String city = adapter.getItem(i);
        user.setCity(city);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    void clearfields(){
        eTxtEmail.setText("");
        eTxtName.setText("");
        eTxtPassword.setText("");
        spCity.setSelection(0);
        rbMale.setChecked(false);
        rbFemale.setChecked(false);
    }
}
