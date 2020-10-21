package com.permisssionx.hzkdev

import androidx.fragment.app.FragmentActivity

//对外接口的代码,将PermissionX指定为单例类，是为了PermissionX接口更方便被调用
object PermissionX {
    private const val TAG = "InvisibleFragment"

    //request方法接受一个FragmentActivity参数、一个可变长度的permissions参数列表以及一个callback回调。FragmentActivity是AppCompatActivity的父类
    fun request(activity: FragmentActivity, vararg permissions: String, callback: PermissionsCallback) {
        //获取FragmentManager实例
        val fragmentManager = activity.supportFragmentManager
        //再调用findFragmentByTag来判断传入的Activity参数是否包含指定TAG的Fragment，
        val existFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if (existFragment != null) {
            //如果是，直接使用
            existFragment as InvisibleFragment
        } else {
            //如果不是，创建新InvisibleFragment实例，将其添加进Activity中去，并指定一个TAG，添加结束后调用commitNow方法，commit不会立即执行添加操作。
            val invisibleFragment = InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        //requestNow目的是申请运行时权限，申请结果会自动回调至callback参数中，permissions是一个数组，加*的目的是将数组转换为可变长度参数传递过去。
        fragment.requestNow(callback, *permissions)
    }
}