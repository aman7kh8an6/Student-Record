package com.example.studentrecord;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editTextid, editTextName,editTextEmail, editTextCc;
    Button buttonAdd, buttongetData, buttonUpdate, buttonViewAll,buttonDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editTextid = findViewById(R.id.editText_id);
        editTextName = findViewById(R.id.editText_name);
        editTextEmail = findViewById(R.id.editText_email);
        editTextCc = findViewById(R.id.editText_CC);

        buttonAdd = findViewById(R.id.button_add);
        buttongetData = findViewById(R.id.button_view);
        buttonUpdate = findViewById(R.id.button_update);
        buttonViewAll = findViewById(R.id.button_viewAll);
        buttonDelete = findViewById(R.id.button_delete);

//        showMessage("Test","Testing Donw!!");
        addData();
        getData();
        viewAll();
        updateData();
        deleteData();
    }

    public void addData(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String myEmail = editTextEmail.getText().toString();
                  boolean isInserted = myDb.insertData(
                          editTextName.getText().toString(),
                          editTextEmail.getText().toString(),
                          editTextCc.getText().toString());
                  if (isInserted==true){
                      Toast.makeText(MainActivity.this,"Data is being inserted",Toast.LENGTH_SHORT).show();
                  }else{
                      Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                  }
                  editTextid.setText("");
                  editTextName.setText("");
                  editTextCc.setText("");
                  editTextEmail.setText("");

//                Toast.makeText(MainActivity.this,"test",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getData(){
        buttongetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextid.getText().toString();
                if(id.equals(String.valueOf(""))){
                    editTextid.setError("Enter id");
                    return;
                }
                Cursor cursor = myDb.getData(id);
                String data = null;
                if (cursor.moveToNext()){
                    data = "ID: "+ cursor.getString(0)+ "\n"+
                            "Name: "+ cursor.getString(1)+ "\n"+
                            "Email: "+ cursor.getString(2)+ "\n"+
                            "Course Count: "+ cursor.getString(3)+ "\n";
                }
                showMessage("Data",data);

            }
        });
    }
    public void viewAll(){
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDb.getAllData();
                if(cursor.getCount()==0){
                    showMessage("Error","Nothing Found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();

                while (cursor.moveToNext()){
                    buffer.append("ID: "+ cursor.getString(0) + "\n");
                    buffer.append("Name: "+ cursor.getString(1) + "\n");
                    buffer.append("Email: "+ cursor.getString(2) + "\n");
                    buffer.append("Course Count: "+ cursor.getString(3) + "\n\n");
                }
                showMessage("All Data",buffer.toString());
            }
        });
    }
    public void updateData(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateData(
                        editTextid.getText().toString(),
                        editTextName.getText().toString(),
                        editTextEmail.getText().toString(),
                        editTextCc.getText().toString());
                if(isUpdate==true){
                    Toast.makeText(MainActivity.this,"Updated successfully",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Oooppss!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void deleteData(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRow = myDb.deleteData(editTextid.getText().toString());
                if(deleteRow == 0){
                    editTextid.setError("ID should not be empty");
                }
                if(deleteRow > 0){
                    Toast.makeText(MainActivity.this,"Deleted successfully",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"Ooopss!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
