package cn.yzl.demo;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import cn.yzl.permissionhelper.anotation.PermssionAgree;
import cn.yzl.permissionhelper.anotation.PermssionRefuse;
import cn.yzl.permissionhelper.library.PermissionHelper;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.but1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionHelper.init(MainActivity.this)
                        .request(1, Manifest.permission.CALL_PHONE)
                        .excute();
            }
        });

        findViewById(R.id.but2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionHelper.init(MainActivity.this)
                        .request(2, Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .excute();
            }
        });

        findViewById(R.id.but3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionHelper.showPermissionEditAct(MainActivity.this);


            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        PermissionHelper.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @PermssionAgree(1)
    public void agreePhonePermission() {
        Toast.makeText(this, "权限通过", Toast.LENGTH_SHORT).show();
    }

    @PermssionRefuse(1)
    public void refusePhonePermission() {
        Toast.makeText(this, "权限拒绝", Toast.LENGTH_SHORT).show();
    }


    @PermssionAgree(2)
    public void agreeMulPermission() {
        Toast.makeText(this, "权限通过", Toast.LENGTH_SHORT).show();
    }

    @PermssionRefuse(2)
    public void refuseMulPermission() {
        Toast.makeText(this, "权限拒绝", Toast.LENGTH_SHORT).show();
    }

}
