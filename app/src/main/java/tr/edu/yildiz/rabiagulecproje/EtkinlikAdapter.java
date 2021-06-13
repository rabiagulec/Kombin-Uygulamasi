package tr.edu.yildiz.rabiagulecproje;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EtkinlikAdapter extends RecyclerView.Adapter<EtkinlikAdapter.myViewHolder>{

    ArrayList<Etkinlik> aList;
    private OnItemClicked aListener;

    public void setOnButtonListener (OnItemClicked onItemClicked){
        aListener=onItemClicked;
    }

    public interface OnItemClicked{
        public void onClicked (int position);
    }


    public EtkinlikAdapter(ArrayList<Etkinlik> aList) {
        this.aList=aList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.etkinlikitem, parent, false), aListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, final int position) {
        holder.etkinlikAdi.setText(aList.get(position).getEtkinlikAdi());
        holder.etkinlikTuru.setText(aList.get(position).getEtkinlikTuru());
        holder.etkinlikMekani.setText(aList.get(position).getEtkinlikMekani());
        holder.etkinlikTarihi.setText(aList.get(position).getEtkinlikTarihi());
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout etkinlikLayout;
        private TextView etkinlikAdi;
        private TextView etkinlikTuru;
        private TextView etkinlikTarihi;
        private TextView etkinlikMekani;

        public myViewHolder(@NonNull View v, OnItemClicked aListener) {
            super(v);
            etkinlikLayout=v.findViewById(R.id.etkinlikLayout);
            etkinlikAdi=v.findViewById(R.id.etkinlikAdi);
            etkinlikTuru=v.findViewById(R.id.etkinlikTuru);
            etkinlikTarihi=v.findViewById(R.id.etkinlikTarihi);
            etkinlikMekani=v.findViewById(R.id.etkinlikMekani);

            etkinlikLayout.setOnClickListener(new View.OnClickListener() {
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
