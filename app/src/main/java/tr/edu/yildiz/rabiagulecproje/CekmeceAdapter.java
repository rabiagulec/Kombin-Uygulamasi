package tr.edu.yildiz.rabiagulecproje;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CekmeceAdapter extends RecyclerView.Adapter<CekmeceAdapter.myViewHolder>{

    ArrayList<Cekmece> aList;
    private OnItemClicked aListener;

    public void setOnButtonListener (OnItemClicked onItemClicked){
        aListener=onItemClicked;
    }

    public interface OnItemClicked{
        public void onClicked (int position);
    }


    public CekmeceAdapter(ArrayList<Cekmece> aList) {
        this.aList=aList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cekmece, parent, false), aListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, final int position) {
        holder.cekmeceIsmi.setText(aList.get(position).getCekmeceAdi());
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        private TextView cekmeceIsmi;
        private LinearLayout cekmece;

        public myViewHolder(@NonNull View v, OnItemClicked aListener) {
            super(v);
            cekmeceIsmi=v.findViewById(R.id.cekmeceIsmi);
            cekmece=v.findViewById(R.id.cekmece);

            cekmece.setOnClickListener(new View.OnClickListener() {
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
