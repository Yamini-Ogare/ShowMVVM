package ognora.showmvvm.View;
import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;


import java.util.ArrayList;

import ognora.showmvvm.Model.FileModel;
import ognora.showmvvm.Others.ListAdapters;
import ognora.showmvvm.R;
import ognora.showmvvm.ViewModel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    RecyclerView recyclerView;
    ListAdapters adapter ;
    final int READ_STORAGE_PERMISSION = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.subname);
        recyclerView = findViewById(R.id.list_image);
        // ListAdapters adapter = new ListAdapters(null , 1, this);

        checkPermission();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        MainActivityViewModel viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        LiveData<ArrayList<FileModel>> arrayList = viewModel.openfolder();


        adapter = new ListAdapters(arrayList, 1, MainActivity.this);
        recyclerView.setAdapter(adapter);

            arrayList.observe(MainActivity.this, new Observer<ArrayList<FileModel>>() {
                @Override
                public void onChanged(ArrayList<FileModel> fileModels) {
                    adapter.notifyDataSetChanged();
                }
            });

    }
    private void checkPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION);

        }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==READ_STORAGE_PERMISSION && grantResults.length>0 ) {

            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                 return ;

            }
            else
            {
                finish();
            }

        }

    }



}

