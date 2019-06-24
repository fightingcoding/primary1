package com.design.teaching_assistant;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.design.teaching_assistant.relevantClass.User;
import com.design.teaching_assistant.student.StudentMainActivity;
import com.design.teaching_assistant.teacher.TeacherMainActivity;
import com.design.teaching_assistant.utils.CCallBack;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends AppCompatActivity {
    private EditText loginAccount;//账号
    private EditText loginPassword;//密码
    private RadioGroup role1;
    private RadioButton mTeacher;
    private RadioButton mStudent;
    private String identity1 = null;
    private Button loginBtn;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "此处输入你的应用ID");
        initView();
      //  initListener();
        setListener();

    }

    private void initView() {
        role1 = (RadioGroup) findViewById(R.id.roleBtn);
        mTeacher=(RadioButton)findViewById(R.id.teacherBtn);
        mStudent=(RadioButton)findViewById(R.id.studentBtn);
        loginAccount = (EditText) findViewById(R.id.login_account);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginBtn = (Button) findViewById(R.id.login_btn);
        registerBtn = (Button) findViewById(R.id.register_btn);
        role1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mTeacher.getId() == checkedId) {
                    identity1 = mTeacher.getText().toString().trim();
                } else {
                    identity1 = mStudent.getText().toString().trim();
                }
            }
        });
    }
    private void setListener() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userId = loginAccount.getText().toString().trim();
                final String password  = loginPassword.getText().toString().trim();
                User.login(userId, password, identity1,new CCallBack() {

                    @Override
                    public void onSuccess(Object o) {
                        try{
                            if(identity1.equals("教师")){
                                Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                            //创建意图对象
                            Intent intent = new Intent(LoginActivity.this, TeacherMainActivity.class);
                            //设置传递键值对
                            intent.putExtra("data",userId);   //账号(学工号）
                            //激活意图
                            startActivity(intent);
                            finish();
                            }
                           if(identity1.equals("学生")) {
                               Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, StudentMainActivity.class);
                            intent.putExtra("data",userId);    //学生学号
                            startActivity(intent);
                            finish();
                           }
                        }catch(Exception e){
                            Toast.makeText(LoginActivity.this,"请选择登录角色！",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        if(TextUtils.isEmpty(userId)){
                            Toast.makeText(LoginActivity.this,"请输入账号！",Toast.LENGTH_SHORT).show();
                        }else if(TextUtils.isEmpty(password)) {
                            Toast.makeText(LoginActivity.this,"请输入密码！",Toast.LENGTH_SHORT).show();
                        }else{Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}

    /*private void initListener() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               userAccountLogin();
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               try{ Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
               }catch(ActivityNotFoundException e){
                   e.getMessage();
               }
            }
        });
    }


    private void userAccountLogin() {
        RadioButton radioBtn1 = (RadioButton) findViewById(role1.getCheckedRadioButtonId());
        final String accountId = loginAccount.getText().toString().trim();
        final String accountPassword = loginPassword.getText().toString().trim();
        role1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectRadioBtn1();
            }
        });
        final  String accountIdentity1 =  selectRadioBtn1();

        if (TextUtils.isEmpty(accountId)) {
            showToast("账号不能为空！");
            return;
        }
        if (TextUtils.isEmpty(accountPassword)) {
            showToast("密码不能为空！");
            return;
        }
        if(TextUtils.isEmpty(accountIdentity1)){
            showToast("用户角色必须被选择！");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //查询用户有登录
                BmobQuery<User> userBmobQuery = new BmobQuery<>();
                userBmobQuery.order("-createdAt");//按时间排序
                userBmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> lists, BmobException e) {
                        if (e == null) {
                            for (User list : lists) {
                                if (accountId.equals(list.getUserId())) {
                                    if (accountPassword.equals(list.getPassword())) {
                                        showToast("登录成功！");
                                        if(accountIdentity1.equals("教师")){
                                        Intent intent = new Intent(LoginActivity.this, TeacherMainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        }
                                        if(accountIdentity1.equals("学生")) {
                                            Intent intent = new Intent(LoginActivity.this, StudentMainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        return;
                                    } else {
                                        showToast("密码输入错误!");
                                        return;
                                    }
                                }
                            }
                            showToast("该账号未注册！");
                        }
                    }
                });
            }
        },3000);
    }

    *//**
     * @param msg 打印信息
     *//*
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private String selectRadioBtn1() {
        RadioButton radioBtn = (RadioButton) findViewById(role1.getCheckedRadioButtonId());
        String identity2 = radioBtn.getText().toString().trim();
        return identity2;
    }
}*/
    /* 点击注册按钮
        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });*/