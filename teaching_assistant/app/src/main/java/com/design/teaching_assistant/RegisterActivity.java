package com.design.teaching_assistant;

import android.app.Activity;
import android.content.Intent;
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
import com.design.teaching_assistant.utils.CCallBack;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


public class RegisterActivity extends AppCompatActivity {
//    public static final String ACTION="com.design.teaching_assistant.intent.action.RegisterActivity";
    private EditText loginName;//用户姓名
    private EditText account;//注册账号
    private EditText password1;//注册密码
    private RadioGroup role;//注册的角色（老师或者学生）
    private RadioButton mTeacher;
    private RadioButton mStudent;
    private String identity;
    private EditText passwordConfirm;//确认密码
    private Button registBtn;//注册


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
       // initListener();

    }
    private void initView() {
        role = (RadioGroup) findViewById(R.id.roleGroup);
        mTeacher=(RadioButton)findViewById(R.id.teacherBtn);
        mStudent=(RadioButton)findViewById(R.id.studentBtn);
        loginName = (EditText)findViewById(R.id.regist_trueName);
        account = (EditText)findViewById(R.id.regist_account);
        password1 = (EditText)findViewById(R.id.regist_password);
        passwordConfirm = (EditText)findViewById(R.id.regist_passwordConfirm);
        registBtn = (Button) findViewById(R.id.regist_btn);
        role.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mTeacher.getId() == checkedId) {
                    identity = mTeacher.getText().toString().trim();
                } else if (mStudent.getId() == checkedId) {
                    identity = mStudent.getText().toString().trim();
                }
            }
        });
        registBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String userId =account.getText().toString().trim();
                String userName = loginName.getText().toString().trim();
                String password = password1.getText().toString().trim();
                String repassword = passwordConfirm.getText().toString().trim();

                if (userId.isEmpty()||userName.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "账号、用户名不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "密码至少6位", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(repassword)) {
                    Toast.makeText(RegisterActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                User.register(userId,userName,password,identity,new CCallBack() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
   /* private String selectRadioBtn(RadioGroup group) {
        RadioButton radioBtn = (RadioButton) findViewById(group.getCheckedRadioButtonId());
        return radioBtn.getText().toString().trim();
    }*/
}


    /*private void initListener() {
        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount();
            }
        });

    }

    private void registerAccount(){
        //获得用户输入的信息
        role.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectRadioBtn();
            }
        });
                final String identity=selectRadioBtn();//用户的身份或者角色类型
                final String userName = loginName.getText().toString().trim();
                final String accountNum = account.getText().toString().trim();
                final String passwordStr = password.getText().toString().trim();
                final String passwordCon = passwordConfirm.getText().toString().trim();
                if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(accountNum)
                        && !TextUtils.isEmpty(passwordStr)&& !TextUtils.isEmpty(passwordCon)&&!TextUtils.isEmpty(identity)) {
                        if (passwordStr.length() < 6) {
                            showToast("密码至少6位！");
                            return;
                        }

                       if (!passwordStr.equals(passwordCon)) {
                           showToast("前后密码不一致！");
                           return;
                       }

                }else{ showToast("用户名、账号、密码不能为空或者用户角色必须被选择！");
                        return;
                }

        BmobQuery<User> registerUserBmobQuery = new BmobQuery<>();
        registerUserBmobQuery.order("-createdAt");//按时间排序
        registerUserBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> lists, BmobException e) {
                for (User list : lists) {
                    if (accountNum.equals(list.getUserId())) {
                        showToast("账号已被注册，请重新输入！");
                    } else {
                        registerAccount(accountNum,userName, passwordStr,identity);
                    }
                }
            }
        });
    }

    *//**
     * 注册账号，将用户账号密码添加进数据库
     *
     * @param accountNum    注册账号
     * @param passwordStr   密码
     * @param userName      姓名
     * @param identity      角色
     *//*
    private void registerAccount(String accountNum,String userName, String passwordStr,String identity) {
        User user = new User(accountNum,userName,passwordStr,identity);
        user.setUserId(accountNum);
        user.setPassword(passwordStr);
        user.setUserName(userName);
        user.setIdentity(identity);
        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    showToast("恭喜，注册账号成功");
                    finish();
                } else {
                    showToast("注册账号失败");
                }
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private String selectRadioBtn() {
        RadioButton radioBtn = (RadioButton) findViewById(role.getCheckedRadioButtonId());
        String identity2 = radioBtn.getText().toString().trim();
        return identity2;
    }
}
*/


