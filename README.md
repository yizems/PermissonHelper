# PermissonHelper
一个针对6.0权限动态申请的库
# PermissionHelper封装



## 1 使用方法

- 申请权限的方法,如果权限已经被允许,会直接走成功的回调

````java
                PermissionHelper.init(MainActivity.this)
                        .request(1, Manifest.permission.CALL_PHONE)
                        .excute();

                PermissionHelper.init(MainActivity.this)
                        .request(2, Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .excute();
````



- 申请结果的接收
````java

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

````

- 打开本APP权限管理页面

````java

PermissionHelper.showPermissionEditAct(MainActivity.this);

````

## 2 更新历史


### 1.2
- 添加处理 不再询问的方法

### 1.1
- 添加打开权限管理页面的方法,效果并不好

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
	      	  compile 'com.github.yizeliang:PermissonHelper:1.2'
		}
	}

```

