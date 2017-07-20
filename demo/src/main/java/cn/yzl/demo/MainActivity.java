package cn.yzl.demo;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import cn.yzl.permissionhelper.anotation.PermissionAgree;
import cn.yzl.permissionhelper.anotation.PermissionNoAsk;
import cn.yzl.permissionhelper.anotation.PermissionRefuse;
import cn.yzl.permissionhelper.library.PermissionHelper;


public class MainActivity extends Activity {

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


    @PermissionAgree(1)
    public void agreePhonePermission() {
        Toast.makeText(this, "权限通过", Toast.LENGTH_SHORT).show();
    }

    @PermissionRefuse(1)
    public void refusePhonePermission() {
        Toast.makeText(this, "权限拒绝", Toast.LENGTH_SHORT).show();
    }


    @PermissionNoAsk(1)
    public void noask1() {
        Toast.makeText(this, "权限不再询问", Toast.LENGTH_SHORT).show();
    }

    @PermissionNoAsk(2)
    public void noask(List<String> diedPermissions) {
        String p = "";
        for (int i = 0; i < diedPermissions.size(); i++) {
            p += diedPermissions.get(i);
        }
        Toast.makeText(this, p+"\n权限不再询问", Toast.LENGTH_SHORT).show();
    }


    @PermissionAgree(2)
    public void agreeMulPermission() {
        Toast.makeText(this, "权限通过", Toast.LENGTH_SHORT).show();
    }

    @PermissionRefuse(2)
    public void refuseMulPermission() {
        Toast.makeText(this, "权限拒绝", Toast.LENGTH_SHORT).show();
    }

}
