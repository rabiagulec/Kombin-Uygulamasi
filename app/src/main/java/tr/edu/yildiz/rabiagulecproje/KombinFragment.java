package tr.edu.yildiz.rabiagulecproje;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KombinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KombinFragment extends Fragment {
    public static Kombin seciliKombin;
    public static boolean silindi=false;
    private int selectedPosition;
    private ArrayList <Kombin> aList;
    private KombinAdapter anAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public KombinFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KombinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KombinFragment newInstance(String param1, String param2) {
        KombinFragment fragment = new KombinFragment();
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
        return inflater.inflate(R.layout.fragment_kombin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView kombinYok = view.findViewById(R.id.kombinYok);
        RecyclerView kombinler = view.findViewById(R.id.kombinList);
        FloatingActionButton fab= view.findViewById(R.id.floatingActionButton2);
        VeriTabani vt = new VeriTabani(view.getContext());
        aList = vt.kombinleriGetir();
        anAdapter = new KombinAdapter(aList);
        kombinler.setHasFixedSize(true);
        kombinler.setLayoutManager(new LinearLayoutManager(getContext()));
        kombinler.setAdapter(anAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.dialogkombin, null);
                EditText kombinAdi = (EditText) dialogView.findViewById(R.id.dialogedittextkombin);
                adb.setView(dialogView);
                adb.setCancelable(true);
                adb.setPositiveButton(
                        "Tamam",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                aList.add(0, new Kombin(vt.kombinEkle(kombinAdi.getText().toString(),MainActivity.kullaniciID),MainActivity.kullaniciID,kombinAdi.getText().toString()));
                                anAdapter.notifyItemInserted(0);
                                kombinYok.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Kombin Eklendi", Toast.LENGTH_LONG).show();
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
        if(aList.size()==0){
            kombinYok.setVisibility(View.VISIBLE);
        }
        anAdapter.setOnButtonListener(new KombinAdapter.OnItemClicked() {
            @Override
            public void onClicked(int position) {
                seciliKombin=aList.get(position);
                selectedPosition=position;
                Intent goIntent = new Intent(getActivity(), KabinOdasiActivity.class);
                startActivityForResult(goIntent,1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==1){
            if(silindi){
                aList.remove(selectedPosition);
                anAdapter.notifyItemRemoved(selectedPosition);
                silindi=false;
            }
        }
    }
}