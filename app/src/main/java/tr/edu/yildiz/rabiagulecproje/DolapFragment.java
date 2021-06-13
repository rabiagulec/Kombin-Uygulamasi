package tr.edu.yildiz.rabiagulecproje;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DolapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DolapFragment extends Fragment {
    public static int seciliCekmece;
    public int cekmeceposition;
    public ArrayList<Cekmece> cekmeceIsimleri;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView cekmeceler;
    private CekmeceAdapter anAdapter;

    public DolapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DolapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DolapFragment newInstance(String param1, String param2) {
        DolapFragment fragment = new DolapFragment();
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
        return inflater.inflate(R.layout.fragment_dolap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VeriTabani vt = new VeriTabani(view.getContext());
        cekmeceIsimleri = vt.cekmeceleriGetir(MainActivity.kullaniciID);
        anAdapter = new CekmeceAdapter(cekmeceIsimleri);
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        TextView cekmeceYok = (TextView) view.findViewById(R.id.cekmeceYok);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.dialogcekmece, null);
                EditText cekmeceAdi = (EditText) dialogView.findViewById(R.id.dialogedittext);
                adb.setView(dialogView);
                adb.setCancelable(true);
                adb.setPositiveButton(
                        "Tamam",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                cekmeceIsimleri.add(0, new Cekmece(cekmeceAdi.getText().toString(),vt.cekmeceEkle(cekmeceAdi.getText().toString(), MainActivity.kullaniciID)));
                                anAdapter.notifyItemInserted(0);
                                cekmeceYok.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Çekmece Eklendi", Toast.LENGTH_LONG).show();
                            }
                        });

                adb.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = adb.create();
                alert.show();
            }
        });
        cekmeceler =view.findViewById(R.id.cekmeceList);
        cekmeceler.setHasFixedSize(true);
        cekmeceler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        if(cekmeceIsimleri.size()==0){
            cekmeceYok.setVisibility(View.VISIBLE);
        }
        cekmeceler.setAdapter(anAdapter);

        anAdapter.setOnButtonListener(new CekmeceAdapter.OnItemClicked() {
            @Override
            public void onClicked(int position) {
                Intent anIntent = new Intent (getActivity(), CekmeceActivity.class);
                seciliCekmece=cekmeceIsimleri.get(position).getCekmeceNo();
                cekmeceposition=position;
                startActivityForResult(anIntent, 1);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==1){
                cekmeceIsimleri.remove(cekmeceposition);
                anAdapter.notifyItemRemoved(cekmeceposition);
            }
        }
    }
}