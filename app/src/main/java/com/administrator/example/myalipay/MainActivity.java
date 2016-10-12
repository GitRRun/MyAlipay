package com.administrator.example.myalipay;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.administrator.example.myalipay.bean.Product;
import com.administrator.example.myalipay.db.DaoMaster;
import com.administrator.example.myalipay.db.DaoSession;
import com.administrator.example.myalipay.db.ProductDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class MainActivity extends AppCompatActivity implements DataCallBack{

    @BindView(R.id.btn_check)
    Button mBtnCheck;
    @BindView(R.id.listview)
    ListView mListview;
    List<String> mList;
    ProductDao mProductDao;
    List<Product> mProductList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        initDao();
        MyAdapter adapter=new MyAdapter(mList,this,mProductDao,this);
        mListview.setAdapter(adapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, mList.get(position), Toast.LENGTH_SHORT).show();
                Log.e("====","sss"+position);
            }
        });
    }

    private void initDao() {
        DaoMaster.DevOpenHelper master=new DaoMaster.DevOpenHelper(this,"product.db");
        SQLiteDatabase db = master.getWritableDatabase();
        DaoMaster dao=new DaoMaster(db);
        DaoSession daoSession = dao.newSession();
        mProductDao=daoSession.getProductDao();
    }

    private void init() {
        mList=new ArrayList<>();
      for (int i = 0; i <100 ; i++) {
            mList.add("商品"+"\t"+i);
        }
        mProductList=new ArrayList<>();
    }

    @OnClick(R.id.btn_check)
    public void onClick() {
    //跳转到结算界面
        Intent intent=new Intent(this,CheckActivity.class);

    Bundle bundle=new Bundle();

        startActivity(intent);
    }


    @Override
    public void setData(Product product) {
        String productName = product.getProductName();
        Log.e("==444=","==="+productName);
        mProductList.add(product);
    }
}
