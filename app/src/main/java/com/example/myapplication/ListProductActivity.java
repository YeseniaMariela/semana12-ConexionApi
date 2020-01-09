package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.myapplication.Models.Products;
import com.example.myapplication.adapters.ProductAdapter;
import com.example.myapplication.helpers.QueueUtils;

import java.util.ArrayList;

public class ListProductActivity extends AppCompatActivity {
    ListView productList;
    ProductAdapter adapter;
    QueueUtils.QueueObject queue =  null;
    ArrayList<Products> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        productList = findViewById(R.id.listaproductos);

        queue = QueueUtils.getInstance(this.getApplicationContext());
        items = new ArrayList<>();
        Products.injectContactsFromCloud(queue, items, this);

        adapter = new ProductAdapter(this, items);
        productList.setAdapter(adapter);


        Products.sendRequestPOST(queue, this);



//        adapter = new ProductAdapter(
//                this, Products.getData());



    }

    public void refreshList(){
        if ( adapter != null ) {
            adapter.notifyDataSetChanged();
        }
    }
}
