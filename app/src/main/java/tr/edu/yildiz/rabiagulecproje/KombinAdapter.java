package tr.edu.yildiz.rabiagulecproje;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KombinAdapter extends RecyclerView.Adapter<KombinAdapter.myViewHolder>{

    ArrayList<Kombin> aList;
    private OnItemClicked aListener;

    public void setOnButtonListener (OnItemClicked onItemClicked){
        aListener=onItemClicked;
    }

    public interface OnItemClicked{
        public void onClicked (int position);
    }


    public KombinAdapter(ArrayList<Kombin> aList) {
        this.aList=aList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.kombinitem, parent, false), aListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, final int position) {
        holder.kombinAdi.setText(aList.get(position).getAd());

    }

    @Override
    public int getItemCount() {
        return aList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        LinearLayout kombinLayout;
        TextView kombinAdi;


        public myViewHolder(@NonNull View v, OnItemClicked aListener) {
            super(v);
            kombinAdi=v.findViewById(R.id.kombinAdi);
            kombinLayout=v.findViewById(R.id.kombinLayout);

            kombinLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(aListener != null){
                        int position=getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            aListener.onClicked(position);
                        }
                    }
                }
            });
        }
    }
}
