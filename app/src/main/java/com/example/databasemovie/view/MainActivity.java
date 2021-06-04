package com.example.databasemovie.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.databasemovie.R;
import com.example.databasemovie.entity.AppDatabase;
import com.example.databasemovie.entity.DataMovie;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContact.view{
    private AppDatabase appDatabase;
    private MainPresenter mainPresenter;
    private MainAdapter mainAdapter;

    private Button btnOK;
    private RecyclerView recyclerView;
    private EditText etJudul, etGenre, etDurasi;
    private int id = 0;
    private boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOK = findViewById(R.id.btn_submit);
        etJudul = findViewById(R.id.et_judul);
        etGenre = findViewById(R.id.et_genre);
        etDurasi = findViewById(R.id.et_durasi);
        recyclerView = findViewById(R.id.rc_main);

        appDatabase = AppDatabase.inidb(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mainPresenter = new MainPresenter(this);
        mainPresenter.readData(appDatabase);
    }

    @Override
    public void successAdd() {
        Toast.makeText(this, "Berhasil Memasukkan Data", Toast.LENGTH_SHORT).show();
        mainPresenter.readData(appDatabase);
    }

    @Override
    public void successDelete() {
        Toast.makeText(this, "Berhasil Menghapus Data", Toast.LENGTH_SHORT).show();
        mainPresenter.readData(appDatabase);
    }

    @Override
    public void resetForm() {
        etJudul.setText("");
        etGenre.setText("");
        etDurasi.setText("");
        btnOK.setText("Submit");
    }

    @Override
    public void getData(List<DataMovie> list) {
        mainAdapter = new MainAdapter(this, list, this);
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    public void editData(DataMovie item) {
        etJudul.setText(item.getJudul());
        etGenre.setText(item.getGenre());
        etDurasi.setText(item.getDurasi());
        id = item.getId();
        edit = true;
        btnOK.setText("EDIT DATA");
    }

    @Override
    public void deleteData(final DataMovie item) {
        AlertDialog.Builder builder;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN){
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }else{
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Delete Data")
                .setMessage("Are you sure to delete this data?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetForm();
                        mainPresenter.deleteData(item, appDatabase);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_dialer)
                .show();
    }

    @Override
    public void onClick(View view) {
        if(view == btnOK){
            if(etJudul.getText().toString().equals("")||etGenre.getText().toString().equals("")||etDurasi.getText().toString().equals("")){
                Toast.makeText(this, "Harap isi semua data!", Toast.LENGTH_SHORT).show();
            }else{
                if (!edit){
                    mainPresenter.insertData(etJudul.getText().toString(), etGenre.getText().toString(), Integer.parseInt(etDurasi.getText().toString()), appDatabase);
                }else{
                    mainPresenter.editData(etJudul.getText().toString(), etGenre.getText().toString(), Integer.parseInt(etDurasi.getText().toString()), id, appDatabase);
                    edit = false;
                }
                resetForm();
            }
        }
    }

}