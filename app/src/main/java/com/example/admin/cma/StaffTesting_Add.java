package com.example.admin.cma;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StaffTesting_Add extends AppCompatActivity implements View.OnClickListener{

    private StaffTesting staffTesting;
    private EditText name_text;
    private EditText time_text;
    private EditText person_text;
    private EditText auditor_text;
    private EditText auditTime_text;
    private EditText content_text;
    private EditText result_text;
    private Button submitButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stafftesting_add);
        initView();
    }

    public void initView(){
        name_text = (EditText)findViewById(R.id.name_text);
        time_text = (EditText)findViewById(R.id.time_text);
        person_text = (EditText)findViewById(R.id.person_text);
        auditor_text = (EditText)findViewById(R.id.auditor_text);
        auditTime_text = (EditText)findViewById(R.id.auditTime_text);
        content_text = (EditText)findViewById(R.id.content_text);
        result_text = (EditText)findViewById(R.id.result_text);
        submitButton = (Button) findViewById(R.id.submit_button);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //使用Toolbar对象替换ActionBar,并在Toolbar左边显示一个返回按钮
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_button:
                onSave();
                break;
            default:break;
        }
    }

    //系统的返回按钮的点击监听
    @Override
    public void onBackPressed() {
        if(isTextChanged()){
            //TODO 内容已经更改，需要传到数据库
            AlertDialog.Builder dialog = new AlertDialog.Builder(StaffTesting_Add.this);
            dialog.setMessage("修改尚未保存，确定返回吗？");
            dialog.setCancelable(false);
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Do nothing
                }
            });
            dialog.show();
        } else {
            super.onBackPressed();
        }
    }

    //toolBar的返回按钮的点击监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //判断是否修改了内容
    public boolean isTextChanged(){
        if(!name_text.getText().toString().isEmpty()) {
            //if (!staffTesting.getName().equals(name_text.getText().toString()))
            return true;
        }
        if(!time_text.getText().toString().isEmpty()) {
            //if (!staffTesting.getTime().equals(time_text.getText().toString()))
            return true;
        }
        if(!person_text.getText().toString().isEmpty()) {
            //if (!staffTesting.getPerson().equals(person_text.getText().toString()))
            return true;
        }
        if(!auditor_text.getText().toString().isEmpty()) {
            //if (!staffTesting.getAuditor().equals(auditor_text.getText().toString()))
            return true;
        }
        if(!auditTime_text.getText().toString().isEmpty()) {
            //if (!staffTesting.getAuditTime().equals(auditTime_text.getText().toString()))
            return true;
        }
        if(!content_text.getText().toString().isEmpty()) {
            //if (!staffTesting.getContent().equals(content_text.getText().toString()))
            return true;
        }
        if(!result_text.getText().toString().isEmpty()) {
            //if (!staffTesting.getResult().equals(result_text.getText().toString()))
            return true;
        }
        return false;
    }

    public void onBack(){
        if(isTextChanged()){
            //TODO 内容已经更改，需要传到数据库
            AlertDialog.Builder dialog = new AlertDialog.Builder(StaffTesting_Add.this);
            dialog.setMessage("修改尚未保存，确定返回吗？");
            dialog.setCancelable(false);
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Do nothing
                }
            });
            dialog.show();
        }
        else
            finish();
    }

    public void onSave(){
        Toast.makeText(StaffTesting_Add.this,"点击了提交",Toast.LENGTH_SHORT).show();
        if(name_text.getText().toString().isEmpty()
                ||time_text.getText().toString().isEmpty()
                ||person_text.getText().toString().isEmpty()
                ||auditor_text.getText().toString().isEmpty()
                ||auditTime_text.getText().toString().isEmpty()
                ||content_text.getText().toString().isEmpty()
                ||result_text.getText().toString().isEmpty())
            Toast.makeText(StaffTesting_Add.this,"请填写完整！",Toast.LENGTH_LONG).show();
        else{
            staffTesting.setName(name_text.getText().toString());
            staffTesting.setTime(time_text.getText().toString());
            staffTesting.setPerson(person_text.getText().toString());
            staffTesting.setAuditor(auditor_text.getText().toString());
            staffTesting.setAuditTime(auditTime_text.getText().toString());
            staffTesting.setContent(content_text.getText().toString());
            staffTesting.setResult(result_text.getText().toString());
            //doPost();
        }
    }

    public void doPost(){
        String address = "http://119.23.38.100:8080/cma/StaffFile/addStaff";
        //拿到body的构建器
        FormBody.Builder builder = new FormBody.Builder();

        //添加参数
        builder.add("name", staffTesting.getName())
                .add("time", staffTesting.getTime())
                .add("person", staffTesting.getPerson())
                .add("auditor", staffTesting.getAuditor())
                .add("auditTime", staffTesting.getAuditTime())
                .add("content",staffTesting.getContent())
                .add("result",staffTesting.getResult());
        //拿到requestBody
        RequestBody requestBody = builder.build();

        HttpUtil.sendOkHttpWithRequestBody(address,requestBody,new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response)throws IOException {
                String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(StaffTesting_Add.this, "提交成功！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onFailure(Call call,IOException e){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(StaffTesting_Add.this, "提交失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
