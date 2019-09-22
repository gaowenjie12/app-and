package com.zsoe.businesssharing.business.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;

import rx.functions.Action1;

public class ConversationListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        initTitleText("聊天列表");
        setTitleRightText("好友列表", new Action1<View>() {
            @Override
            public void call(View view) {
                startActivity(new Intent(mContext,HaoYouActivity.class));
            }
        });

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
