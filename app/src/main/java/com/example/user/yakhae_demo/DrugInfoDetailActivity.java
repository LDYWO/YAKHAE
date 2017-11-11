package com.example.user.yakhae_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DrugInfoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_info_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DrugInfoDetailActivity.this,WriteReviewActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();

        TextView drug_company = (TextView)findViewById(R.id.drug_company);
        TextView drug_name = (TextView)findViewById(R.id.drug_name);
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingbar);
        TextView rating_num = (TextView)findViewById(R.id.rating_num);

        drug_company.setText("제약회사: "+intent.getStringExtra("durg_company").toString());
        drug_name.setText("의약품: "+intent.getStringExtra("durg_name").toString());
        ratingBar.setRating(Float.parseFloat(intent.getStringExtra("durg_rating")));
        rating_num.setText("평점 "+intent.getStringExtra("durg_rating_num").toString());


        intent.getStringExtra("durg_category").toString();
        intent.getStringExtra("durg_type").toString();
        intent.getStringExtra("durg_main_ingre").toString();
        intent.getStringExtra("durg_taboo").toString();


        ListView listView;
        DrugInfoItemDetailAdapter adapter;

        adapter = new DrugInfoItemDetailAdapter();
        listView = (ListView)findViewById(R.id.infor_detail);
        adapter.addItem(
                intent.getStringExtra("durg_type").toString(),
                intent.getStringExtra("durg_category").toString(),
                intent.getStringExtra("durg_main_ingre").toString(),
                intent.getStringExtra("durg_taboo").toString());
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
