package com.example.databasemovie.view;

import android.os.AsyncTask;
import android.util.Log;

import com.example.databasemovie.entity.AppDatabase;
import com.example.databasemovie.entity.DataMovie;

import java.util.List;

public class MainPresenter implements MainContact.presenter {
    private MainContact.view view;

    public MainPresenter(MainContact.view view) {
        this.view = view;
    }

    class InsertData extends AsyncTask<Void, Void, Long>{
        private AppDatabase database;
        private DataMovie dataMovie;

        public InsertData(AppDatabase database, DataMovie dataMovie) {
            this.database = database;
            this.dataMovie = dataMovie;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            return database.dao().insertData(dataMovie);
        }

        protected void onPostExecute(Long along){
            super.onPostExecute(along);
            view.successAdd();
        }
    }
    @Override
    public void insertData(String judul, String genre, int durasi, final AppDatabase database) {
        final DataMovie dataMovie = new DataMovie();
        dataMovie.setJudul(judul);
        dataMovie.setGenre(genre);
        dataMovie.setDurasi(durasi);
        new InsertData(database, dataMovie).execute();
    }

    @Override
    public void readData(AppDatabase database) {
        List<DataMovie> list;
        list = database.dao().getData();
        view.getData(list);
    }

    class EditData extends AsyncTask<Void, Void, Integer> {
        private AppDatabase database;
        private DataMovie dataMovie;

        public EditData(AppDatabase database, DataMovie dataMovie) {
            this.database = database;
            this.dataMovie = dataMovie;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return database.dao().updateData(dataMovie);
        }

        protected void onPostExecute(Integer integer){
            super.onPostExecute(integer);
            Log.d("integer db", "onPostExecute: " + integer);
            view.successAdd();
        }
    }

    @Override
    public void editData(String judul, String genre, int durasi, int id, final AppDatabase database) {
        final DataMovie dataMovie = new DataMovie();
        dataMovie.setJudul(judul);
        dataMovie.setGenre(genre);
        dataMovie.setDurasi(durasi);
        dataMovie.setId(id);
        new EditData(database, dataMovie).execute();
    }

    class DeleteData extends AsyncTask<Void, Void, Void>{
        private AppDatabase database;
        private DataMovie dataMovie;

        DeleteData(AppDatabase database, DataMovie dataMovie) {
            this.database = database;
            this.dataMovie = dataMovie;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            database.dao().deleteData(dataMovie);
            return null;
        }

        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            view.successDelete();
        }
    }

    @Override
    public void deleteData(DataMovie dataMovie, AppDatabase database) {
        new DeleteData(database, dataMovie).execute();
    }
}
