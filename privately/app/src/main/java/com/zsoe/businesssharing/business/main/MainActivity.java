package com.zsoe.businesssharing.business.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMClientListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMMultiDeviceListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.TabEntity;
import com.zsoe.businesssharing.business.login.LoginActivity;
import com.zsoe.businesssharing.business.message.MessageEvent;
import com.zsoe.businesssharing.easechat.ChatActivity;
import com.zsoe.businesssharing.easechat.Constant;
import com.zsoe.businesssharing.easechat.DemoHelper;
import com.zsoe.businesssharing.easechat.InviteMessgeDao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends BaseActivity<MainPresenter> {


//    private ViewPager mViewPager;

    private String[] mTitles = {"首页", "展厅", "关注", "消息", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_shouye_pre, R.mipmap.tab_zhanting_pre,
            R.mipmap.tab_guanzhu_pre, R.mipmap.tab_xiaoxi_pre, R.mipmap.tab_wode_pre};

    private int[] mIconSelectIds = {
            R.mipmap.tab_shouye, R.mipmap.tab_zhanting,
            R.mipmap.tab_guanzhu, R.mipmap.tab_xiaoxi, R.mipmap.tab_wode};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    protected BaseFragment currentFragment;
    private CommonTabLayout mBottomNavigation;


    private HomeFragment homeFragment;//首页
    private GalleryRoomFragment galleryRoomFragment;//展厅
    private AttentionFragment attentionFragment;//关注
    private MessageFragment messageFragment;//消息
    private MeFragment meFragment;//我的

    private InviteMessgeDao inviteMessgeDao;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver internalDebugReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                try {
                    //some device doesn't has activity to handle this intent
                    //so add try catch
                    Intent intent = new Intent();
                    intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    startActivity(intent);
                } catch (Exception e) {
                }
            }
        }


        //make sure activity will not in background if user is logged into another device or removed
        if (getIntent() != null &&
                (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) ||
                        getIntent().getBooleanExtra(Constant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD, false) ||
                        getIntent().getBooleanExtra(Constant.ACCOUNT_KICKED_BY_OTHER_DEVICE, false))) {
            DemoHelper.getInstance().logout(false, null);
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        } else if (getIntent() != null && getIntent().getBooleanExtra("isConflict", false)) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        inviteMessgeDao = new InviteMessgeDao(this);


        setContentView(R.layout.activity_main);
        initView();
        initBottomNavigationView();
        initData();
        EventBus.getDefault().register(this);
        getPresenter().industry_allcate();

        showExceptionDialogFromIntent(getIntent());

        registerBroadcastReceiver();

        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
        EMClient.getInstance().addClientListener(clientListener);
        EMClient.getInstance().addMultiDeviceListener(new MyMultiDeviceListener());

        //debug purpose only
        registerInternalDebugReceiver();
    }


    private android.app.AlertDialog.Builder exceptionBuilder;
    private boolean isExceptionDialogShow = false;

    private void showExceptionDialogFromIntent(Intent intent) {
        if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false)) {
            showExceptionDialog(Constant.ACCOUNT_CONFLICT);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false)) {
            showExceptionDialog(Constant.ACCOUNT_REMOVED);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_FORBIDDEN, false)) {
            showExceptionDialog(Constant.ACCOUNT_FORBIDDEN);
        } else if (intent.getBooleanExtra(Constant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD, false) ||
                intent.getBooleanExtra(Constant.ACCOUNT_KICKED_BY_OTHER_DEVICE, false)) {
            this.finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showExceptionDialogFromIntent(intent);
    }


    /**
     * show the dialog when user met some exception: such as login on another device, user removed or user forbidden
     */
    private void showExceptionDialog(String exceptionType) {
        isExceptionDialogShow = true;
        DemoHelper.getInstance().logout(false, null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (exceptionBuilder == null)
                    exceptionBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                exceptionBuilder.setTitle(st);
                exceptionBuilder.setMessage(getExceptionMessageId(exceptionType));
                exceptionBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exceptionBuilder = null;
                        isExceptionDialogShow = false;
                        finish();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                exceptionBuilder.setCancelable(false);
                exceptionBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {
            }
        }
    }


    private int getExceptionMessageId(String exceptionType) {
        if (exceptionType.equals(Constant.ACCOUNT_CONFLICT)) {
            return R.string.connect_conflict;
        } else if (exceptionType.equals(Constant.ACCOUNT_REMOVED)) {
            return R.string.em_user_remove;
        } else if (exceptionType.equals(Constant.ACCOUNT_FORBIDDEN)) {
            return R.string.user_forbidden;
        }
        return R.string.Network_error;
    }

    EMClientListener clientListener = new EMClientListener() {
        @Override
        public void onMigrate2x(boolean success) {
            Toast.makeText(MainActivity.this, "onUpgradeFrom 2.x to 3.x " + (success ? "success" : "fail"), Toast.LENGTH_LONG).show();
            if (success) {
                refreshUIWithMessage();
            }
        }
    };


    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                updateUnreadLabel();
                EventBus.getDefault().post(new MessageEvent(2));
            }
        });
    }

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                updateUnreadLabel();
                updateUnreadAddressLable();

                // refresh conversation list
                EventBus.getDefault().post(new MessageEvent(2));

//                if (contactListFragment != null) {
//                    contactListFragment.refresh();
//                }

            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    public class MyContactListener implements EMContactListener {
        @Override
        public void onContactAdded(String username) {
        }

        @Override
        public void onContactDeleted(final String username) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (ChatActivity.activityInstance != null && ChatActivity.activityInstance.toChatUsername != null &&
                            username.equals(ChatActivity.activityInstance.toChatUsername)) {
                        String st10 = getResources().getString(R.string.have_you_removed);
                        Toast.makeText(MainActivity.this, ChatActivity.activityInstance.getToChatUsername() + st10, Toast.LENGTH_LONG)
                                .show();
                        ChatActivity.activityInstance.finish();
                    }
                }
            });
            updateUnreadAddressLable();
        }

        @Override
        public void onContactInvited(String username, String reason) {
        }

        @Override
        public void onFriendRequestAccepted(String username) {
        }

        @Override
        public void onFriendRequestDeclined(String username) {
        }
    }

    /**
     * update the total unread count
     */
    public void updateUnreadAddressLable() {
//        runOnUiThread(new Runnable() {
//            public void run() {
//                int count = getUnreadAddressCountTotal();
//                if (count > 0) {
//                    unreadAddressLable.setVisibility(View.VISIBLE);
//                } else {
//                    unreadAddressLable.setVisibility(View.INVISIBLE);
//                }
//            }
//        });

    }


    /**
     * get unread event notification count, including application, accepted, etc
     *
     * @return
     */
//    public int getUnreadAddressCountTotal() {
//        int unreadAddressCountTotal = 0;
//        unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
//        return unreadAddressCountTotal;
//    }

    public class MyMultiDeviceListener implements EMMultiDeviceListener {

        @Override
        public void onContactEvent(int event, String target, String ext) {

        }

        @Override
        public void onGroupEvent(int event, String target, final List<String> username) {
            switch (event) {
                case EMMultiDeviceListener.GROUP_LEAVE:
                    ChatActivity.activityInstance.finish();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 默认第一个tab的Fragment
     */
    private void initData() {
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance(getString(R.string.tab_1));
        }
        if (!homeFragment.isAdded()) {

            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, homeFragment).commitAllowingStateLoss();

        } else {
            getSupportFragmentManager().beginTransaction().show(homeFragment).commitAllowingStateLoss();
        }

        currentFragment = homeFragment;
    }

    /**
     * 添加或者显示 fragment
     *
     * @param transaction
     * @param fragment
     */
    protected void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment).add(R.id.fl_content, fragment).commitAllowingStateLoss();
        } else {
            transaction.hide(currentFragment).show(fragment).commitAllowingStateLoss();
        }

        currentFragment = (BaseFragment) fragment;
    }

    private void initView() {
//        mFlContent = (FrameLayout) findViewById(R.id.fl_content);
        mBottomNavigation = (CommonTabLayout) findViewById(R.id.bottom_navigation);
    }

    private void initBottomNavigationView() {

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }


        mBottomNavigation.setTabData(mTabEntities);
        mBottomNavigation.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {// 首页
                    if (homeFragment == null) {
                        homeFragment = HomeFragment.newInstance(getString(R.string.tab_1));
                    }
                    addOrShowFragment(getSupportFragmentManager().beginTransaction(), homeFragment);

                } else if (position == 1) {// 展厅
                    if (galleryRoomFragment == null) {
                        galleryRoomFragment = GalleryRoomFragment.newInstance(getString(R.string.tab_2));
                    }
                    addOrShowFragment(getSupportFragmentManager().beginTransaction(), galleryRoomFragment);
                } else if (position == 2) {// 关注
                    if (attentionFragment == null) {
                        attentionFragment = AttentionFragment.newInstance(getString(R.string.tab_3));
                    }
                    addOrShowFragment(getSupportFragmentManager().beginTransaction(), attentionFragment);
                } else if (position == 3) {//消息
                    if (messageFragment == null) {
                        messageFragment = MessageFragment.newInstance(getString(R.string.tab_4));
                    }
                    addOrShowFragment(getSupportFragmentManager().beginTransaction(), messageFragment);
                } else if (position == 4) {//我的
                    if (meFragment == null) {
                        meFragment = MeFragment.newInstance(getString(R.string.tab_5));
                    }
                    addOrShowFragment(getSupportFragmentManager().beginTransaction(), meFragment);
                }

            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }


    /**
     * update unread message count
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();

        if (count > 0) {
            //三位数
            mBottomNavigation.showMsg(3, count);
            mBottomNavigation.setMsgMargin(3, -5, 5);

            if (null != messageFragment) {
                messageFragment.updateUnreadLabel();
            }
        } else {
            mBottomNavigation.hideMsg(3);
        }
    }

    /**
     * get unread message count
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMessageCount();
    }


    /**
     * 通知“我的”显示小红点
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if (messageEvent.type == 1) {
            updateUnreadLabel();
        }
    }


    // user logged into another device
    public boolean isConflict = false;
    // user account was removed
    private boolean isCurrentAccountRemoved = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
        EventBus.getDefault().unregister(this);

        try {
            unregisterReceiver(internalDebugReceiver);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isConflict && !isCurrentAccountRemoved) {
            updateUnreadLabel();
            updateUnreadAddressLable();
            EventBus.getDefault().post(new MessageEvent(2));

        }

        if (null != messageFragment) {
            messageFragment.updateUnreadLabel();
        }

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        EMClient.getInstance().removeClientListener(clientListener);
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.popActivity(this);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);

//        //save fragments
//        FragmentManager fm = getSupportFragmentManager();
//        for (Fragment f : fragments) {
//            if (f.isAdded()) {
//                fm.putFragment(outState, f.getClass().getSimpleName(), f);
//            }
//        }

        super.onSaveInstanceState(outState);
    }


    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                DemoHelper.getInstance().getNotifier().vibrateAndPlayTone(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };


    /**
     * debug purpose only, you can ignore this
     */
    private void registerInternalDebugReceiver() {
        internalDebugReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                DemoHelper.getInstance().logout(false, new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                finish();
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                        });
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                    }

                    @Override
                    public void onError(int code, String message) {
                    }
                });
            }
        };
        IntentFilter filter = new IntentFilter(getPackageName() + ".em_internal_debug");
        registerReceiver(internalDebugReceiver, filter);
    }


    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }
}
