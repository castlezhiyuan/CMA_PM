package com.example.admin.cma;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StaffTesting_Modify extends AppCompatActivity implements View.OnClickListener{

    private StaffTesting staffTesting;
    private EditText name_text;
    private EditText time_text;
    private EditText person_text;
    private EditText auditor_text;
    private EditText auditTime_text;
    private EditText content_text;
    private EditText result_text;
    private Button saveButton;
    private Button backButton;
    private Button deleteButton;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stafftesting_modify);
        initView();
        Intent intent = getIntent();
        staffTesting = (StaffTesting)intent.getSerializableExtra("StaffTesting");
        setText();
    }

    public void initView(){
        name_text = (EditText)findViewById(R.id.name_text);
        time_text = (EditText)findViewById(R.id.time_text);
        person_text = (EditText)findViewById(R.id.person_text);
        auditor_text = (EditText)findViewById(R.id.auditor_text);
        auditTime_text = (EditText)findViewById(R.id.auditTime_text);
        content_text = (EditText)findViewById(R.id.content_text);
        result_text = (EditText)findViewById(R.id.result_text);
        saveButton = (Button) findViewById(R.id.save_button);
        deleteButton = (Button)findViewById(R.id.delete_button);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //使用Toolbar对象替换ActionBar,并在Toolbar左边显示一个返回按钮
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //设置监听
        name_text.setOnClickListener(this);
        time_text.setOnClickListener(this);
        person_text.setOnClickListener(this);
        auditor_text.setOnClickListener(this);
        auditTime_text.setOnClickListener(this);
        content_text.setOnClickListener(this);
        result_text.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    public void setText(){
        name_text.setText(staffTesting.getName());
        time_text.setText(staffTesting.getTime());
        person_text.setText(staffTesting.getPerson());
        auditor_text.setText(staffTesting.getAuditor());
        auditTime_text.setText(staffTesting.getAuditTime());
        content_text.setText(staffTesting.getContent());
        result_text.setText(staffTesting.getResult());
    }

    //判断是否修改了内容
    public boolean isTextChanged(){
        if(!staffTesting.getName().equals(name_text.getText().toString()))
            return true;
        if(!staffTesting.getTime().equals(time_text.getText().toString()))
            return true;
        if(!staffTesting.getPerson().equals(person_text.getText().toString()))
            return true;
        if(!staffTesting.getAuditor().equals(auditor_text.getText().toString()))
            return true;
        if(!staffTesting.getAuditTime().equals(auditTime_text.getText().toString()))
            return true;
        if(!staffTesting.getContent().equals(content_text.getText().toString()))
            return true;
        if(!staffTesting.getResult().equals(result_text.getText().toString()))
            return true;
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.name_text:
                name_text.setCursorVisible(true);break;
            case R.id.time_text:
                time_text.setCursorVisible(true);break;
            case R.id.person_text:
                person_text.setCursorVisible(true);break;
            case R.id.auditor_text:
                auditor_text.setCursorVisible(true);break;
            case R.id.auditTime_text:
                auditTime_text.setCursorVisible(true);break;
            case R.id.content_text:
                content_text.setCursorVisible(true);break;
            case R.id.result_text:
                result_text.setCursorVisible(true);break;
            case R.id.save_button:
                Toast.makeText(StaffTesting_Modify.this, "点击了保存！", Toast.LENGTH_SHORT).show();                onSave();
                //onSave();
                break;
            case R.id.delete_button:  //点击删除，弹出弹窗
                Toast.makeText(StaffTesting_Modify.this, "点击了删除！", Toast.LENGTH_SHORT).show();
                //onDelete();
                //showPopupWindow();
                break;
/*            case R.id.title_cancel:  //取消删除，dismiss 弹窗
                mPopWindow.dismiss();
            case R.id.delete_comfirm:
                //TODO 从数据库中删除此记录*/
            default:break;
        }
    }

    //系统的返回按钮的点击监听
    @Override
    public void onBackPressed() {
        if(isTextChanged()){
            //TODO 内容已经更改，需要传到数据库
            AlertDialog.Builder dialog = new AlertDialog.Builder(StaffTesting_Modify.this);
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

    public void onBack(){
        if(isTextChanged()){
            //TODO 内容已经更改，需要传到数据库
            AlertDialog.Builder dialog = new AlertDialog.Builder(StaffTesting_Modify.this);
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
        Toast.makeText(StaffTesting_Modify.this, "点击了保存！", Toast.LENGTH_SHORT).show();

    }

    public void onDelete(){
        String address = "http://119.23.38.100:8080/cma/StaffTesting/delete";
        RequestBody requestBody=new FormBody.Builder().add("name",staffTesting.getName()).build();

        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();

        HttpUtil.sendOkHttpWithRequestBody(address,requestBody,new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response)throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(StaffTesting_Modify.this, "删除成功！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onFailure(Call call,IOException e){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(StaffTesting_Modify.this, "删除失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
