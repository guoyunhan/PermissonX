package com.permisssionx.app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.permisssionx.hzkdev.PermissionX
import kotlinx.android.synthetic.main.activity_main.*
import java.security.Permission
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeCallBtn.setOnClickListener {
            //只需调用request方法，传入当前Activity和所需要的权限名，然后再Lambda表达式中处理权限的申请结果即可。
            //与此同时，PermissionX支持一次性申请多个权限，将所需要的权限名传入Request方法即可。
//            PermissionX.request(this,
//                android.Manifest.permission.CALL_PHONE,
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                android.Manifest.permission.READ_CONTACTS) { allGranted, deniedList ->
//                ...
//            }
            PermissionX.request(this,
                android.Manifest.permission.CALL_PHONE) { allGranted, deniedList ->
                if (allGranted) {
                    call()
                } else {
                    Toast.makeText(this, "you denied $deniedList", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun call() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}