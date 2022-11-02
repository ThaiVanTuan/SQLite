package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database database;
    ListView lvComputer;
    ArrayList<Computer> arrayListComputer;
    ComputerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvComputer = (ListView) findViewById(R.id.listviewComputer);
        arrayListComputer = new ArrayList<>();
        adapter = new ComputerAdapter(this, R.layout.dong_computer,arrayListComputer);
        lvComputer.setAdapter(adapter);

        database = new Database(this,"Category.sql",null,1);

        database.QueryData("CREATE TABLE IF NOT EXISTS Computer(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenPC VARCHAR(200))");

//        database.QueryData("INSERT INTO Computer VALUES(null, 'PC1')");
        GetDataComputer();

    }

    public void DialogXoaPC(String tenpc, int id)
    {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa PC "+ tenpc +"không");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM Computer WHERE Id = '"+ id +"'");
                Toast.makeText(MainActivity.this, "Đã xóa"+ tenpc, Toast.LENGTH_SHORT).show();
                GetDataComputer();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }

    public void DialogSuaComputer(String ten, int id)
    {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua);

        EditText edtTenPC = (EditText) dialog.findViewById(R.id.editTextTenPC);
        Button btnXacNhan = (Button) dialog.findViewById(R.id.buttonXacNhan);
        Button btnHuy = (Button) dialog.findViewById(R.id.buttonHuyEdit);

        edtTenPC.setText(ten);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = edtTenPC.getText().toString().trim();
                database.QueryData("UPDATE Computer SET TenPC = '"+ tenMoi +"' WHERE Id ='"+ id +"'");
                Toast.makeText(MainActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                dialog.show();
                GetDataComputer();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    private void GetDataComputer()
    {
        Cursor dataComputer = database.GetData("SELECT * FROM Computer");
        arrayListComputer.clear();
        while (dataComputer.moveToNext()){
            String ten = dataComputer.getString(1);
            int id = dataComputer.getInt(0);
            arrayListComputer.add(new Computer(id, ten));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_computer,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() ==R.id.menuAdd)
        {
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogThem()
    {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_computer);

        EditText edtTen = (EditText) dialog.findViewById(R.id.editTextTenPC);
        Button btnThem = (Button) dialog.findViewById(R.id.buttonThem);
        Button btnHuy = (Button) dialog.findViewById(R.id.buttonHuy);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tencv = edtTen.getText().toString();
                if(tencv.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên PC!", Toast.LENGTH_SHORT).show();
                }else
                {
                    database.QueryData("INSERT INTO Computer VALUES(null, '"+ tencv +"')");
                    Toast.makeText(MainActivity.this, "Đã thêm.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataComputer();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}