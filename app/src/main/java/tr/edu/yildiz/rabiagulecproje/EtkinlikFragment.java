package tr.edu.yildiz.rabiagulecproje;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EtkinlikFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EtkinlikFragment extends Fragment {
    public static int seciliEtkinlik;
    private int pos;
    public static Etkinlik yeniEtkinlik;
    private  ArrayList<Etkinlik> etkinlikler;
    private EtkinlikAdapter anAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EtkinlikFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EtkinlikFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EtkinlikFragment newInstance(String param1, String param2) {
        EtkinlikFragment fragment = new EtkinlikFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_etkinlik, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VeriTabani vt = new VeriTabani(view.getContext());
        etkinlikler = vt.etkinlikleriGetir();
        anAdapter = new EtkinlikAdapter(etkinlikler);

        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton3);
        TextView etkinlikYok = view.findViewById(R.id.etkinlikYok);
        RecyclerView etkinliklistesi = view.findViewById(R.id.etkinlikList);
        etkinliklistesi.setHasFixedSize(true);
        etkinliklistesi.setLayoutManager(new LinearLayoutManager(getContext()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EtkinlikEkleActivity.class);
                startActivityForResult(intent,1);
            }
        });

        if(etkinlikler.size()==0){
            etkinlikYok.setVisibility(View.VISIBLE);
        }
        etkinliklistesi.setAdapter(anAdapter);
        anAdapter.setOnButtonListener(new EtkinlikAdapter.OnItemClicked() {
            @Override
            public void onClicked(int position) {
                seciliEtkinlik=etkinlikler.get(position).getEtkinlikNo();
                pos=position;
                Intent intent = new Intent(getActivity(), EtkinlikGuncelleActivity.class);
                startActivityForResult(intent,2);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==1){
                etkinlikler.add(0,yeniEtkinlik);
                anAdapter.notifyItemInserted(0);
            } else {
                etkinlikler.set(pos, yeniEtkinlik);
                anAdapter.notifyDataSetChanged();
            }
        }
    }
}