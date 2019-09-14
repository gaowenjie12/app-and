package com.zsoe.businesssharing.business.message;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;

public class ConversationListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        initTitleText("聊天列表");

        /*在activity对应java类中通过getFragmentManager()
         *获得FragmentManager，用于管理ViewGrop中的fragment
         * */
        FragmentManager fragmentManager = getSupportFragmentManager();
        /*FragmentManager要管理fragment（添加，替换以及其他的执行动作）
         *的一系列的事务变化，需要通过fragmentTransaction来操作执行
         */
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //实例化要管理的fragment
        ConversationListFragment conversationListFragment = new ConversationListFragment();

        //通过添加（事务处理的方式）将fragment加到对应的布局中
        fragmentTransaction.replace(R.id.add_layout, conversationListFragment);
        //事务处理完需要提交
        fragmentTransaction.commit();
    }
}
