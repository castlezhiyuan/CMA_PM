package com.example.admin.cma;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class StaffTesting_Main extends AppCompatActivity implements SearchView.OnQueryTextListener,View.OnClickListener,AdapterView.OnItemClickListener{

    //data
    private List<StaffTesting> list= new ArrayList<>();;

    //View
    private Toolbar toolbar;
    private ListView listView;
    private SearchView searchView;
    private Button addButton;
    private StaffTestingAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stafftesting_main);

        initView();
        StaffTesting testing = new StaffTesting("考核1","5-20","content1","resutl1","王国新","审核人1","5-20");
        list.add(testing);
        adapter = new StaffTestingAdapter(StaffTesting_Main.this,R.layout.stafftesting_listitem,list);
        listView.setAdapter(adapter);
    }

    //初始化所有控件
    public void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView =(ListView)findViewById(R.id.listview);
        searchView =(SearchView)findViewById(R.id.searchview);
        addButton = (Button)findViewById(R.id.add_button);

        //使用Toolbar对象替换ActionBar
        setSupportActionBar(toolbar);
        //设置Toolbar左边显示一个返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setSupportActionBar(toolbar);

        //默认不弹出键盘
        searchView.setFocusable(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);

        //listView可筛选
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(this);

        addButton.setOnClickListener(this);
    }

    //监听searchView中文本的改变
    @Override
    public boolean onQueryTextChange(String newText) {
        ListAdapter adapter=listView.getAdapter();
        if(adapter instanceof Filterable){
            Filter filter=((Filterable)adapter).getFilter();
            if(newText.isEmpty()){
                filter.filter(null);
            }else{
                filter.filter(newText);
            }
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // TODO Auto-generated method stub
        ListAdapter listAdapter=listView.getAdapter();
        if(listAdapter instanceof Filterable){
            Filter filter=((Filterable)listAdapter).getFilter();
            if(query.isEmpty()){
                filter.filter(null);
            }else{
                filter.filter(query);
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_button:{
                Intent intent=new Intent(StaffTesting_Main.this,StaffTesting_Add.class);
                startActivity(intent);
                break;
            }
            default:break;
        }
    }

    //listView 的Item点击事件,跳转到编辑页面
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(StaffTesting_Main.this,StaffTesting_Modify.class);
        StaffTesting staffTesting = (StaffTesting)listView.getItemAtPosition(position);
        intent.putExtra("StaffTesting", staffTesting);
        startActivity(intent);
    }

    //监听返回按钮的点击事件，比如可以返回上级Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //向后端发送请求，返回所有人员档案记录
    public void getDataFromServer(){
        String address = "http://119.23.38.100:8080/cma/StaffFile/getAllwithoutpics";
        //String address = "http://10.0.2.2/get_data.json";
        HttpUtil.sendOkHttpRequest(address,new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response)throws IOException {
                String responseData = response.body().string();
                parseJSONWithGSON(responseData);
            }
            @Override
            public void onFailure(Call call,IOException e){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(StaffTesting_Main.this, "请求数据失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void parseJSONWithGSON(String jsondata){
        Gson gson = new Gson();
        //不能直接赋值给list，这样list就指向别的内存了，这样再刷新adapter也没有。
        //list = gson.fromJson(jsondata,new TypeToken<List<PersonnelFile>>(){}.getType());
        List<StaffTesting> newList = gson.fromJson(jsondata,new TypeToken<List<StaffTesting>>(){}.getType());
        list.clear();
        list.addAll(newList);
    }

    private void showResponse() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
                listView.setAdapter(adapter);
                //刷新适配器
                adapter.notifyDataSetChanged();

                /* 李贵银的 写法
                adapter=new StaffFileAdapter(StaffFile_Main.this,R.layout.listview_item,dangAns);
                listView.setAdapter(adapter);
                */
            }
        });
    }
}
