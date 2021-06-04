package com.example.databasemovie.view;

import android.view.View;

import com.example.databasemovie.entity.AppDatabase;
import com.example.databasemovie.entity.DataMovie;

import java.util.List;

public interface MainContact {
    interface view extends View.OnClickListener{
        void successAdd();
        void successDelete();
        void resetForm();
        void getData(List<DataMovie> list);
        void editData(DataMovie item);
        void deleteData(DataMovie item);
    }

    interface presenter{
        void insertData(String judul, String genre, int durasi, AppDatabase database);
        void readData(AppDatabase database);
        void editData(String judul, String genre, int durasi, int id, AppDatabase database);
        void deleteData(DataMovie dataMovie, AppDatabase database);
    }
}
