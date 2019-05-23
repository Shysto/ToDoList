package com.example.todolist.recycler_activities.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.modele.ListeToDo;

import java.util.List;

public class ItemAdapterList extends RecyclerView.Adapter<ItemAdapterList.ItemViewHolder> {

    private List<ListeToDo> lesListes;
    private onClickListListener onClickListListener;

    public ItemAdapterList(List<ListeToDo> lesListes, onClickListListener onClickListListener) {
        this.lesListes = lesListes;
        this.onClickListListener = onClickListListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.liste,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(lesListes.get(position));
    }

    @Override
    public int getItemCount() {
        return lesListes.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView titreListe;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            titreListe = itemView.findViewById(R.id.listTitle);
            titreListe.setOnClickListener(this);
        }

        public void bind(ListeToDo listeToDo) {
            titreListe.setText(listeToDo.getTitreListeToDo());

        }

        @Override
        public void onClick(View v) {
            onClickListListener.clickList(getAdapterPosition());
        }


    }

    public interface onClickListListener{
        void clickList(int position);
    }
}
