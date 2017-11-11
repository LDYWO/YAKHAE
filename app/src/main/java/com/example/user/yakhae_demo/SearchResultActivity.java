package com.example.user.yakhae_demo;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);

        Intent intent = getIntent();
        String s = intent.getStringExtra("search").toString();
        if(s==null)
            toolbar.setTitle("null");
        else
            toolbar.setTitle(s);

        ListView listView;
        DrugInfoItemAdapter adapter;

        adapter = new DrugInfoItemAdapter();

        listView = (ListView)findViewById(R.id.search_result_listview);

        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.pills),"company1","drug1","일반의약품","진통제","XXX","병용금기",3.7f);
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.pills),"company2","drug2","일반의약품","진통제","XXX","노인주의",2.3f);
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.pills),"company3","drug3","전문의약품","진통제","XXX"," ",4.0f);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DrugInfoItem item = (DrugInfoItem) parent.getItemAtPosition(position) ;

                //BitmapDrawable drug_image = (BitmapDrawable)item.getDrug_image();
                //Bitmap drug_bitmap = drug_image.getBitmap();
                String drug_company = item.getDrug_company();
                String drug_name = item.getDrug_name();
                String drug_category = item.getDrug_category();
                String drug_type = item.getDrug_type();
                String drug_main_ingre = item.getMain_ingredient();
                String drug_taboo = item.getTaboo();
                Float drug_rating = item.getRating();
                String drug_rating_num = item.getRating_number();

                Intent intent=new Intent(SearchResultActivity.this,DrugInfoDetailActivity.class);
                //intent.putExtra("drug_bitmap",drug_bitmap);
                intent.putExtra("durg_company",drug_company.toString());
                intent.putExtra("durg_name",drug_name.toString());
                intent.putExtra("durg_category",drug_category.toString());
                intent.putExtra("durg_type",drug_type.toString());
                intent.putExtra("durg_main_ingre",drug_main_ingre.toString());
                intent.putExtra("durg_taboo",drug_taboo.toString());
                intent.putExtra("durg_rating",drug_rating.toString());
                intent.putExtra("durg_rating_num",drug_rating_num.toString());
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
