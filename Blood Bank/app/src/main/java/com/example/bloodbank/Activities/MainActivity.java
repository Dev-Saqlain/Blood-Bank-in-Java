package com.example.bloodbank.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bloodbank.Adapters.RequestAdapter;
import com.example.bloodbank.DataModels.RequestDataModel;
import com.example.bloodbank.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<RequestDataModel> requestDataModelList;
    private RequestAdapter requestAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestDataModelList=new ArrayList<>();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.search_button)
                {
                    startActivity(new Intent(MainActivity.this,SearchActivity.class));
                }
                return false;
            }
        });

        recyclerView=findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        requestAdapter=new RequestAdapter(requestDataModelList,this);
        recyclerView.setAdapter(requestAdapter);
        populateHomePage();
    }

    private void populateHomePage()
    {
        RequestDataModel requestDataModel= new RequestDataModel("Lorem ipsum dolor sit amet consectetur adipiscing elit, suspendisse fames mollis netus bibendum. see more...","https://cdn.pixabay.com/photo/2013/07/13/09/48/blood-156063_960_720.png");
        requestDataModelList.add(requestDataModel);
        requestDataModelList.add(requestDataModel);
        requestDataModelList.add(requestDataModel);
        requestDataModelList.add(requestDataModel);
        requestDataModelList.add(requestDataModel);
        requestDataModelList.add(requestDataModel);
        requestDataModelList.add(requestDataModel);
        requestAdapter.notifyDataSetChanged();
    }

}