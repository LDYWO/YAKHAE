package com.example.user.yakhae_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

        final Intent drug_intent = getIntent();

        TextView drug_company = (TextView)findViewById(R.id.drug_company);
        TextView drug_name = (TextView)findViewById(R.id.drug_name);
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingbar);
        TextView rating_num = (TextView)findViewById(R.id.rating_num);

        drug_company.setText("제약회사: "+drug_intent.getStringExtra("drug_company").toString());
        drug_name.setText("의약품: "+drug_intent.getStringExtra("drug_name").toString());
        ratingBar.setRating(Float.parseFloat(drug_intent.getStringExtra("drug_rating")));
        rating_num.setText("평점 "+drug_intent.getStringExtra("drug_rating_num").toString());


        drug_intent.getStringExtra("drug_category").toString();
        drug_intent.getStringExtra("drug_type").toString();
        drug_intent.getStringExtra("drug_main_ingre").toString();
        drug_intent.getStringExtra("drug_taboo").toString();

        final String medicine_name = drug_intent.getStringExtra("drug_name").toString();
        final String company_name = drug_intent.getStringExtra("drug_company").toString();
        final String drug_index = drug_intent.getStringExtra("drug_index").toString();

        ListView listView;
        DrugInfoItemDetailAdapter adapter;

        adapter = new DrugInfoItemDetailAdapter();
        listView = (ListView)findViewById(R.id.infor_detail);
        adapter.addItem(
                drug_intent.getStringExtra("drug_type").toString(),
                drug_intent.getStringExtra("drug_category").toString(),
                drug_intent.getStringExtra("drug_main_ingre").toString(),
                drug_intent.getStringExtra("drug_taboo").toString());
        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DrugInfoDetailActivity.this,WriteReviewActivity.class);
                //intent.putExtra("drug_image",drug_image.toString());
                intent.putExtra("drug_name",medicine_name);
                intent.putExtra("drug_company",company_name);
                intent.putExtra("drug_index",drug_index);
                /*intent.putExtra("durg_category",drug_intent.getStringExtra("durg_category").toString());
                intent.putExtra("durg_type",drug_intent.getStringExtra("durg_type").toString());
                intent.putExtra("durg_main_ingre",drug_intent.getStringExtra("durg_main_ingre").toString());
                intent.putExtra("durg_taboo",drug_intent.getStringExtra("durg_taboo").toString());*/
                startActivity(intent);
            }
        });
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
