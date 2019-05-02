package com.example.ribijoy.smarttravelguide;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.os.TestLooperManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class activity_topPlaces extends AppCompatActivity {

    public static ArrayList<String> arryName = new ArrayList<>();
    public static ArrayList<String> arrayAddress = new ArrayList<>();
    public static ArrayList<String> arrayLat = new ArrayList<>();
    public static ArrayList<String> arrayLon = new ArrayList<>();
    public static ArrayList<String> arrayType = new ArrayList<>();
    public static ArrayList<String> arrayDescription = new ArrayList<>();
    public static ArrayList<String> arrayCity = new ArrayList<>();



    private Context mContext;

    //card list view
    //public static String type="", address="";
    //public static double lat=0, lon=0;
    //public static String name = "";

    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_places);

        //-------------------------card view-------------------------------------
        mDatabase = FirebaseDatabase.getInstance().getReference().child("places");
        mDatabase.keepSynced(true);

         mBlogList = (RecyclerView)findViewById(R.id.myrecyleview);
         mBlogList.setHasFixedSize(true);
         mBlogList.setLayoutManager(new LinearLayoutManager(this));

        /* name.add("name0 ------name");
        name.add("name1 ------name");
        name.add("name2 ------name");
        name.add("name3 ------name");
        name.add("name 4------name");
        name.add("name 0------name");

*/

       /*  int listSize = name.size();

        for (int i = 0; i<listSize; i++){
            Log.i("mmmmmmMember name: ", name.get(i));
        }*/

        //-----------------------------------------------------------------------------

    }



    //backpress--------------------------------------------------------------
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(activity_topPlaces.this, activity_home.class);
        startActivity(intent);

    }

    //-------------------------------place list------------------------------

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Blog,BlogViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Blog, BlogViewHolder>
                (Blog.class,R.layout.top_place_cardview,BlogViewHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, final int position) {

                viewHolder.setTitle(model.getName());
                viewHolder.setDec(model.getReview());
                viewHolder.setImage(getApplicationContext(),model.getImage());

                viewHolder.setAddress(model.getAddress());
                viewHolder.setType(model.getType());
                viewHolder.setLat(Double.toString(model.getLat()));
                viewHolder.setLon(Double.toString(model.getLon()));
                viewHolder.setCity(model.getCity());

               // name.add(model.getName());

                //lat = model.getLat();
                //lon = model.getLon();
                //type = model.getType();
                //address = model.getAddress();


                // file transfer------------------------------------------------------------------------------------new activity
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Recycle Click" + position, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(activity_topPlaces.this, activity_showPlace_map.class);
                        //intent.putExtra("name",nam)
                        //intent.putExtra("name", Integer.toString(position));
                        intent.putExtra("name", arryName.get(position));
                        intent.putExtra("type", arrayType.get(position));
                        intent.putExtra("lat", arrayLat.get(position));
                        intent.putExtra("lon", arrayLon.get(position));
                        intent.putExtra("address", arrayAddress.get(position));
                        intent.putExtra("city", arrayCity.get(position));
                        startActivity(intent);
                    }
                });
                // file transfer------------------------------------------------------------------------------------new activity


             /*   viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() { //go to another layout
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(activity_topPlaces.this, activity_showPlace.class);
                        startActivity(intent);
                       // Toast.makeText(getApplicationContext(),"jkhj",Toast.LENGTH_SHORT).show();
                    }
                });
                */
            }
        };

        mBlogList.setAdapter(firebaseRecyclerAdapter);


    }


   /* public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Recycle Click" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
    */

    public static class BlogViewHolder extends RecyclerView.ViewHolder{
        View mView;
        LinearLayout linearLayout;




        public BlogViewHolder( View itemView ){
            super(itemView);

           // name = names;
            //address = addresses;
           // lat = clat;
          //  lon = clon;
          //  type = ctype;

            mView = itemView;
            //linearLayout = itemView.findViewById(R.id.crd_linear_layout); //go to another layout
        }

        public void setTitle(String title){

            TextView post_title = (TextView)mView.findViewById(R.id.txt_post_title);
            post_title.setText(title);
            //name.set(i, title);
            //Log.i("see name arraylist", String.valueOf(name));
            arryName.add(title);

        }

        public void setDec(String desc){
            TextView post_desc = (TextView)mView.findViewById(R.id.txt_post_details);
            post_desc.setText(desc);
            arrayDescription.add(desc);

        }

        public void setImage(Context ctx,String image){
            //ImageView post_image = (ImageView)mView.findViewById(R.id.post_image);
            //Picasso.with(ctx).load(image).into(post_image);
        }

        public void setLat(String lat){
            //TextView post_lat = (TextView)mView.findViewById(R.id.txt_post_title);
            //post_lat.setText(lat);
            Log.i("see lat",lat);
            arrayLat.add(lat);
        }
        public void setLon(String lon) {
            //TextView post_lon = (TextView)mView.findViewById(R.id.txt_post_details);
           // post_lon.setText(lon);
            Log.i("see lon",lon);
            arrayLon.add(lon);
        }
        public void setAddress(String address) {
            //TextView post_add = (TextView)mView.findViewById(R.id.txtAddress);
            //post_add.setText(address);
            Log.i("see address",address);
            arrayAddress.add(address);
        }
        public void setType(String type) {
            //TextView post_type = (TextView)mView.findViewById(R.id.txtType);
            //post_type.setText(type);
            Log.i("see type",type);
            arrayType.add(type);
        }
        public void setCity(String city) {
            //TextView post_type = (TextView)mView.findViewById(R.id.txtType);
            //post_type.setText(type);
            Log.i("see city",city);
            arrayCity.add(city);
        }



    }


    //-------------------------------place list-------------------------------


}
