# PermissonHelper
一个针对6.0权限动态申请的库
# PermissionHelper封装



## 1 使用方法

- 使用过于简单,就不写详细的注释了
- fragment 中使用方法一样

```java

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

```


## 2 更新历史

### 1.0
- 创建项目,实现基本功能


## 3 依赖添加

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
    
   module {
		dependencies {
	      	  compile 'com.github.yizeliang:PermissonHelper:1.0'
		}
	}

```

