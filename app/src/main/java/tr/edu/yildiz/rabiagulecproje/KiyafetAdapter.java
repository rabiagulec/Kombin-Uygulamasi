package tr.edu.yildiz.rabiagulecproje;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KiyafetAdapter extends RecyclerView.Adapter<KiyafetAdapter.myViewHolder>{
    ArrayList<Kiyafet> aList;
    private OnItemClicked aListener;
    public void setOnButtonListener (OnItemClicked onItemClicked){
        aListener=onItemClicked;
    }
    public interface OnItemClicked{
        public void onClicked (int position);
    }


    public KiyafetAdapter(ArrayList<Kiyafet> aList) {
        this.aList=aList;
    }

    @NonNull
    @Override
    public KiyafetAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new KiyafetAdapter.myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.kiyafet_item, parent, false), aListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final KiyafetAdapter.myViewHolder holder, final int position) {
        holder.kiyafetItemFoto.setImageBitmap(aList.get(position).getFoto());
        holder.kiyafetItemEtiket.setText(aList.get(position).getTur());
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }

public static class myViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout kiyafetListLayout;
        private ImageView kiyafetItemFoto;
        private TextView kiyafetItemEtiket;


    public myViewHolder(@NonNull View v, KiyafetAdapter.OnItemClicked aListener) {
        super(v);
        kiyafetListLayout = (LinearLayout) v.findViewById(R.id.kiyafetListLayout);
        kiyafetItemEtiket = (TextView) v.findViewById(R.id.kiyafetItemEtiket);
        kiyafetItemFoto = (ImageView) v.findViewById(R.id.kiyafetItemFoto);

        kiyafetListLayout.setOnClickListener(new View.OnClickListener() {
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
