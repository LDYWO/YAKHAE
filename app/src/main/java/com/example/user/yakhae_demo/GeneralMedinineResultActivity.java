package com.example.user.yakhae_demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class GeneralMedinineResultActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    ListView listView;
    DrugInfoItemAdapter adapter = new DrugInfoItemAdapter();
    String drug_ingr;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("0").child("medicine");
    private DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("1").child("ingr");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_medinine_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("검색 중 입니다...");

        listView = (ListView)findViewById(R.id.courseListView);

        FirebaseAsyncTask firebaseAsyncTask =new FirebaseAsyncTask();
        firebaseAsyncTask.execute();



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{
                onBackPressed();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private class FirebaseAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
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

                        if(contact.child("spclty_pblc").getValue().toString().trim().contains("일반의약품"))
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

                                adapter.initTaboo();
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

                                adapter.initTaboo();

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
                    adapter.initTaboo();
                    Iterable<DataSnapshot> childcontact = dataSnapshot.getChildren();
                    for (DataSnapshot contact : childcontact){
                        if(drug_ingr.contains(contact.child("ingr_name").getValue().toString())){
                            adapter.setTaboo(contact.child("type_name_b").getValue().toString()+", "+
                                            contact.child("type_name_h").getValue().toString()+", "+
                                            contact.child("type_name_i").getValue().toString()+", "+
                                            contact.child("type_name_n").getValue().toString()+", "+
                                            contact.child("type_name_t").getValue().toString()+", "+
                                            contact.child("type_name_ty").getValue().toString()+", "+
                                            contact.child("type_name_y").getValue().toString(),
                                    contact.child("ingr_name").getValue().toString(),
                                    contact.child("prohbt_content").getValue().toString());

                        }
                    }
                    listView.setAdapter(adapter);
                    if (dataSnapshot.exists()){
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
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
                    String drug_prohibit = item.getProhibited_content();
                    Float drug_rating = item.getRating();
                    String drug_rating_num = item.getRating_number();

                    Intent intent=new Intent(GeneralMedinineResultActivity.this,DrugInfoDetailActivity.class);
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
                    intent.putExtra("drug_prohibit",drug_prohibit.toString());
                    startActivity(intent);
                }
            });

        }
    }
}
