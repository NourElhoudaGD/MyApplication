package com.example.android.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText, surnameEditText, marksEditText, idEditText;
    private Button addData, viewAll, update, Delete;
    private CreateBdStudent myDb;
    private SQLiteDatabase bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = (EditText) findViewById(R.id.editText);
        surnameEditText = (EditText) findViewById(R.id.editText1);
        marksEditText = (EditText) findViewById(R.id.editText2);
        idEditText = (EditText) findViewById(R.id.editText3);

        myDb = new CreateBdStudent(this);

        addData = (Button) findViewById(R.id.button);
        viewAll = (Button) findViewById(R.id.button2);
        update = (Button) findViewById(R.id.button1);
        Delete = (Button) findViewById(R.id.button3);

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(nameEditText.getText().toString(),
                        surnameEditText.getText().toString(),
                        marksEditText.getText().toString());
                if(isInserted){
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
                }
                idEditText.setText("");
                nameEditText.setText("");
                surnameEditText.setText("");
                marksEditText.setText("");
            }
        });

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0){
                    //show error message
                    showMessage("Error","Nothing Found");
                    return;
                }

                StringBuffer stringBuffer = new StringBuffer();
                while (res.moveToNext()){
                    stringBuffer.append("id :" + res.getString(0) + "\n");
                    stringBuffer.append("name :" + res.getString(1) + "\n");
                    stringBuffer.append("surname :" + res.getString(2) + "\n");
                    stringBuffer.append("mark :" + res.getString(3) + "\n\n");
                }
                //show message
                showMessage("Data",stringBuffer.toString());
                idEditText.setText("");
                nameEditText.setText("");
                surnameEditText.setText("");
                marksEditText.setText("");
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDb.updateData(idEditText.getText().toString(),
                        nameEditText.getText().toString(),
                        surnameEditText.getText().toString(),
                        marksEditText.getText().toString());
                if (isUpdated){
                    Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();
                }
                idEditText.setText("");
                nameEditText.setText("");
                surnameEditText.setText("");
                marksEditText.setText("");
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(idEditText.getText().toString());
                if (deletedRows > 0){
                    Toast.makeText(MainActivity.this, "Data Delete", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_SHORT).show();
                }
                idEditText.setText("");
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
