package ognora.showmvvm.View;



import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import ognora.showmvvm.Model.FileModel;
import ognora.showmvvm.Others.ListAdapters;
import ognora.showmvvm.R;
import ognora.showmvvm.ViewModel.MainActivityViewModel;

public class DisplayActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    ListAdapters adapter ;
    TextView textview ;
     Bundle bundle ;
    public  MainActivityViewModel viewModel ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        recyclerView = findViewById(R.id.list_image);
        textview = findViewById(R.id.subname);
        bundle = getIntent().getExtras();

       viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.writeTo(bundle);
        LiveData<ArrayList<FileModel>> arrayList = viewModel.getImages();
        LiveData<String> folderName = viewModel.getname() ;

        textview.setText(folderName.getValue());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListAdapters(arrayList, 2, DisplayActivity.this);
        recyclerView.setAdapter(adapter);


        arrayList.observe(DisplayActivity.this, new Observer<ArrayList<FileModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<FileModel> fileModels) {

                adapter.notifyDataSetChanged();

            }
        });


    }



}
