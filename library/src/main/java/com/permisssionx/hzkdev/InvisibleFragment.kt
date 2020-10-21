package com.permisssionx.hzkdev

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
//typealias关键字将任意类型指定一个别名。
typealias PermissionsCallback  = (Boolean, List<String>) -> Unit
class InvisibleFragment : Fragment() {
    //1.首先定义一个callback变量作为运行时权限申请结果的回调通知，该函数类型接收boolean和List<String>这两种类型的参数。
    private var callback : PermissionsCallback ?= null
    //2.requestNow接受一个与callback变量同类型的函数参数，同时使用vararg接受一个可变长度的permissions参数列表。
    fun requestNow (cb: PermissionsCallback, vararg permissions: String) {
        //将函数类型参数赋值给callback变量；
        callback = cb
        //立即申请运行时权限，并将permissions参数列表传递进行来。
        requestPermissions(permissions, 1)
    }
    //3.处理运行时权限的申请结果。
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            //deniedList用来存储运行时申请结果，遍历grantResults数组，若未授权，则添加进列表中，
            val deniedList = ArrayList<String>()
            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            //遍历结束后使用allGranted标识所有申请权限是否被授权。判断依据是deniedList是否为空，最后将运行时申请结果进行回调。
            val allGranted = deniedList.isEmpty()
            callback?.let {
                it(allGranted, deniedList)
            }
        }
    }
}