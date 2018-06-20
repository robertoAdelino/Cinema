package com.example.acer.cinema;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class CinemaCursorAdapter extends RecyclerView.Adapter<CinemaCursorAdapter.CinemaViewHolder> {

    private Context context;
    private Cursor cursor = null;
    private View.OnClickListener viewHolderClickListener = null;
    private int lastFIlmekClicked = -1;

    public CinemaCursorAdapter(Context context) {

        this.context = context;
    }

    public void refreshData(Cursor cursor) {
        if (this.cursor != cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    public void setViewHolderClickListener(View.OnClickListener viewHolderClickListener) {
        this.viewHolderClickListener = viewHolderClickListener;
    }

    public int getLastFIlmekClicked() {
        return lastFIlmekClicked;
    }

    @NonNull
    @Override

    public CinemaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.activity_show_vistos, parent, false);

        return new CinemaViewHolder(item);
    }





    @Override
    public void onBindViewHolder(@NonNull CinemaViewHolder holder, int position) {
        cursor.moveToPosition(position);
        Filmes filmes = DbTableFilmes.getCurrentFilmesFromCursor(cursor);
        holder.setFilme(filmes);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (cursor == null) return 0;

        return cursor.getCount();
    }

    public class CinemaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewNome;
        private TextView textViewData;
        private int filmeId;

        public CinemaViewHolder(View itemView) {
            super(itemView);

            textViewNome = (TextView) itemView.findViewById(R.id.textViewNome);
            textViewData = (TextView) itemView.findViewById(R.id.textViewData);

            itemView.setOnClickListener(this);
        }

        public void setFilme(Filmes filmes) {
            textViewNome.setText(filmes.getName());
            textViewData.setText(String.format("%.2f", filmes.getDate()) + "");
            filmeId = filmes.getId();
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position == RecyclerView.NO_POSITION) {
                return;
            }

            if (viewHolderClickListener != null) {
                lastFIlmekClicked = filmeId;
                viewHolderClickListener.onClick(v);
            }
        }
    }
}

