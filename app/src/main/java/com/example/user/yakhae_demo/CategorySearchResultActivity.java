package com.example.user.yakhae_demo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CategorySearchResultActivity extends AppCompatActivity {

    ArrayAdapter arrayAdapter;
    Spinner spinner;
    Button searchButton;
    String selected, drug_ingr;

    ListView listView;
    DrugInfoItemAdapter adapter = new DrugInfoItemAdapter();

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("0").child("medicine");
    private DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("1").child("ingr");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_search_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);

        listView = (ListView)findViewById(R.id.courseListView);

        spinner = (Spinner)findViewById(R.id.termSpinner);
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Category,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);




        searchButton=(Button)findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selected = spinner.getSelectedItem().toString().trim();
                Log.e("Selected::",selected);

                FirebaseAsyncTask firebaseAsyncTask =new FirebaseAsyncTask();
                firebaseAsyncTask.execute();

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

    private class FirebaseAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    adapter.drugInfoItemsList.clear();
                    Iterable<DataSnapshot> childcontact = dataSnapshot.getChildren();
                    for (DataSnapshot contact : childcontact){
                        // Log.i("contact_Drug_infof:::",contact.getKey().toString());
                        // Log.i("item_name:::",contact.child("item_name").getValue().toString());

                        if(contact.child("prduct_type").getValue().toString().contains(selected))
                        {
                            drug_ingr = contact.child("item_ingr_name").getValue().toString();

                            if(contact.child("item_image").getValue().toString().trim().contains("NA")){
                                Log.i("item_name:::",contact.child("item_name").getValue().toString());
                                adapter.addItem(
                                        contact.getKey().toString(),
                                        "http://drug.mfds.go.kr/html/images/noimages.png",
                                        contact.child("entp_name").getValue().toString(),
                                        contact.child("item_name").getValue().toString(),
                                        contact.child("spclty_pblc").getValue().toString(),
                                        contact.child("prduct_type").getValue().toString(),
                                        contact.child("item_ingr_name").getValue().toString(),
                                        "없음",
                                        Float.parseFloat(contact.child("rating").getValue().toString()));
                            }
                            else{
                                adapter.addItem(
                                        contact.getKey().toString(),
                                        contact.child("item_image").getValue().toString(),
                                        contact.child("entp_name").getValue().toString(),
                                        contact.child("item_name").getValue().toString(),
                                        contact.child("spclty_pblc").getValue().toString(),
                                        contact.child("prduct_type").getValue().toString(),
                                        contact.child("item_ingr_name").getValue().toString(),
                                        "없음",
                                        Float.parseFloat(contact.child("rating").getValue().toString()));

                            }
                        }
                    }
                    listView.setAdapter(adapter);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }

            });

            mDatabase2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> childcontact = dataSnapshot.getChildren();
                    for (DataSnapshot contact : childcontact){
                        //Log.i("contact_Drug_infof:::",contact.getKey().toString());
                        //Log.i("type_name_b:::",contact.child("type_name_b").getValue().toString());
                        if(drug_ingr.contains(contact.child("ingr_name").getValue().toString())){
                            adapter.setTaboo(contact.child("type_name_b").getValue().toString()+", "+
                                            contact.child("type_name_h").getValue().toString()+", "+
                                            contact.child("type_name_i").getValue().toString()+", "+
                                            contact.child("type_name_n").getValue().toString()+", "+
                                            contact.child("type_name_t").getValue().toString()+", "+
                                            contact.child("type_name_ty").getValue().toString()+", "+
                                            contact.child("type_name_y").getValue().toString(),
                                    contact.child("ingr_name").getValue().toString());
                        }
                    }
                    listView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
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

                    Intent intent=new Intent(CategorySearchResultActivity.this,DrugInfoDetailActivity.class);
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
    }


}
