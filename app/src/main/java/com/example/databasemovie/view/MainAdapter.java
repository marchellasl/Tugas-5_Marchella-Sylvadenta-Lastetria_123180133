package com.example.databasemovie.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databasemovie.R;
import com.example.databasemovie.entity.DataMovie;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewHolder> {
    Context context;
    List<DataMovie> list;
    MainContact.view mView;

    public MainAdapter(Context context, List<DataMovie> list, MainContact.view mView) {
        this.context = context;
        this.list = list;
        this.mView = mView;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvGenre, tvDurasi, id;
        CardView cardView;

        public viewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_item);
            tvJudul = itemView.findViewById(R.id.tv_item_judul);
            tvGenre = itemView.findViewById(R.id.tv_item_genre);
            tvDurasi = itemView.findViewById(R.id.tv_item_durasi);
            id = itemView.findViewById(R.id.tv_item_id);
        }
    }

    @NonNull
    @Override
    public MainAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new viewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.viewHolder holder, int position) {
        final DataMovie item = list.get(position);
        holder.tvJudul.setText(item.getJudul());
        holder.tvGenre.setText(item.getGenre());
        holder.tvDurasi.setText(item.getDurasi());
        holder.id.setText(String.valueOf(item.getId()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.editData(item);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mView.deleteData(item);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
