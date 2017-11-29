package com.example.user.yakhae_demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private List<DatabaseReference> mDatabaseList_medicine = new ArrayList<DatabaseReference>();
    private List<DatabaseReference> mDatabaseList_ingr = new ArrayList<DatabaseReference>();
    ProgressDialog progressDialog1,progressDialog2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);

        Intent intent = getIntent();
        final String search = intent.getStringExtra("search").toString();
        if(search==null)
            toolbar.setTitle("null");
        else
            toolbar.setTitle(search);

        ListView listView;
        final DrugInfoItemAdapter adapter;

       progressDialog1 = new ProgressDialog(this);
       progressDialog2 = new ProgressDialog(this);;

        adapter = new DrugInfoItemAdapter();

        listView = (ListView)findViewById(R.id.search_result_listview);

        for(int i=1;i<=49524;i++){
            mDatabaseList_medicine.add( FirebaseDatabase.getInstance().getReference("0").child("medicine").child(String.valueOf(i)));
        }

        for(int i=1;i<=2328;i++){
            mDatabaseList_ingr.add( FirebaseDatabase.getInstance().getReference("1").child("ingr").child(String.valueOf(i)));
        }


        for(int i=0;i<49523;i++) {
            progressDialog1.setMessage("검색 중 입니다. 기다려 주세요...");
            progressDialog1.show();
            final int finalI = i;
            mDatabaseList_medicine.get(i).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Log.i("item_name", dataSnapshot.child("item_name").getValue().toString());

                    if(dataSnapshot.child("item_name").getValue().toString().contains(search))
                    {adapter.addItem(
                            Integer.toString(finalI +1),
                            dataSnapshot.child("item_image").getValue().toString(),
                            dataSnapshot.child("entp_name").getValue().toString(),
                            dataSnapshot.child("item_name").getValue().toString(),
                            dataSnapshot.child("spclty_pblc").getValue().toString(),
                            dataSnapshot.child("prduct_type").getValue().toString(),
                            dataSnapshot.child("item_ingr_name").getValue().toString(),
                            "없음",
                            Float.parseFloat(dataSnapshot.child("rating").getValue().toString()));
                        toolbar.setTitle(search);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        for(int i=0;i<2327;i++){
            progressDialog2.setMessage("검색 중 입니다. 기다려 주세요...");
            progressDialog2.show();
            mDatabaseList_ingr.get(i).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("ingr_name").getValue().toString().contains(search)){
                        adapter.setTaboo(dataSnapshot.child("type_name_b").getValue().toString()+", "+
                                        dataSnapshot.child("type_name_h").getValue().toString()+", "+
                                        dataSnapshot.child("type_name_i").getValue().toString()+", "+
                                        dataSnapshot.child("type_name_n").getValue().toString()+", "+
                                        dataSnapshot.child("type_name_t").getValue().toString()+", "+
                                        dataSnapshot.child("type_name_ty").getValue().toString()+", "+
                                        dataSnapshot.child("type_name_y").getValue().toString(),
                                dataSnapshot.child("ingr_name").getValue().toString());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }



        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DrugInfoItem item = (DrugInfoItem) parent.getItemAtPosition(position) ;

                String drug_index =item.getDrug_index();
                String drug_image = item.getDrug_image();
                String drug_company = item.getDrug_company();
                String drug_name = item.getDrug_name();
                String drug_category = item.getDrug_category();
                String drug_type = item.getDrug_type();
                String drug_main_ingre = item.getMain_ingredient();
                String drug_taboo = item.getTaboo();
                Float drug_rating = item.getRating();
                String drug_rating_num = item.getRating_number();

                Intent intent=new Intent(SearchResultActivity.this,DrugInfoDetailActivity.class);
                intent.putExtra("drug_index",drug_index.toString());
                intent.putExtra("drug_image",drug_image.toString());
                intent.putExtra("drug_company",drug_company.toString());
                intent.putExtra("drug_name",drug_name.toString());
                intent.putExtra("drug_category",drug_category.toString());
                intent.putExtra("drug_type",drug_type.toString());
                intent.putExtra("drug_main_ingre",drug_main_ingre.toString());
                intent.putExtra("drug_taboo",drug_taboo.toString());
                intent.putExtra("drug_rating",drug_rating.toString());
                intent.putExtra("drug_rating_num",drug_rating_num.toString());
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
