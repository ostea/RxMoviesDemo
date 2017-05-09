package com.chaospro.rxmoviesdemo.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.chaospro.rxmoviesdemo.R;
import com.chaospro.rxmoviesdemo.event.ForceFreshEvent;
import com.chaospro.rxmoviesdemo.model.MainTabType;
import com.chaospro.rxmoviesdemo.ui.about.AboutRxFragment;
import com.chaospro.rxmoviesdemo.ui.gank.GankRxFragment;
import com.chaospro.rxmoviesdemo.ui.info.InfoRxFragment;
import com.chaospro.rxmoviesdemo.utils.BottomNavigationViewHelper;
import com.chaospro.rxmoviesdemo.utils.RxBus;

import io.reactivex.functions.Consumer;

import static com.chaospro.rxmoviesdemo.model.MainTabType.ABOUT;
import static com.chaospro.rxmoviesdemo.model.MainTabType.GANK;
import static com.chaospro.rxmoviesdemo.model.MainTabType.INFO;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final String TAG = "MainActivity";

    private MainContract.Presenter mPresenter;


    private BottomNavigationView bottomNavigationView;

    private MainTabType mCurrentTabType = GANK;
    private Fragment[] fragmentArrayList = new Fragment[3];

    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainPresenter(this);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bntv);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        switchTabFragment(GANK, false);
        RxBus.create().toObservable(ForceFreshEvent.class)
                .subscribe(new Consumer<ForceFreshEvent>() {
                    @Override
                    public void accept(ForceFreshEvent forceFreshEvent) throws Exception {
                        if (forceFreshEvent.getEventType() == ForceFreshEvent.STOP) {
                            BottomNavigationViewHelper.stopMenuAnim(bottomNavigationView, 0);
                            menuItem.setIcon(R.drawable.selector_home_menu);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.main_menu_gank:
                        item.setIcon(R.drawable.selector_menu);
                        switchTabFragment(GANK, false);
                        break;
                    case R.id.main_menu_info:
                        switchTabFragment(INFO, false);
                        break;
                    case R.id.main_menu_about:
                        switchTabFragment(ABOUT, false);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_menu_gank:
                        item.setIcon(R.drawable.selector_home_menu);
                        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_menu_refresh);
                        BottomNavigationViewHelper.startMenuAnim(bottomNavigationView, 0, animation);
                        switchTabFragment(GANK, true);
                        break;
                    case R.id.main_menu_info:

                        break;
                    case R.id.main_menu_about:

                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public void switchTabFragment(MainTabType tabType, boolean isForce) {
        Log.d(TAG, "switchTabFragment : " + tabType.name() + " typeValue:" + tabType.getValue() + " isForce :" + isForce);

        if (mCurrentTabType.equals(tabType) && !isForce) {
            return;
        }
        Fragment oldFragment = fragmentArrayList[mCurrentTabType.getValue()];
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (oldFragment != null) {
            ft.hide(oldFragment);
        }
        mCurrentTabType = tabType;

        //select bottom meun
        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
        }
        menuItem = bottomNavigationView.getMenu().getItem(mCurrentTabType.getValue());
        menuItem.setChecked(true);
        //change fragment or viewpager
        Fragment newFragment = fragmentArrayList[mCurrentTabType.getValue()];
        //create frament
        switch (tabType) {
            case GANK:
                if (newFragment == null) {
                    newFragment = GankRxFragment.newInstance();
                }
                break;
            case INFO:
                if (newFragment == null) {
                    newFragment = InfoRxFragment.newInstance();
                }
                break;
            case ABOUT:
                if (newFragment == null) {
                    newFragment = AboutRxFragment.newInstance();
                }
                break;
            default:
                break;
        }
        fragmentArrayList[tabType.getValue()] = newFragment;
        if (!newFragment.isAdded()) {
            ft.add(R.id.fl_container, newFragment, String.valueOf(tabType.getValue()));
        }
        ft.show(newFragment);
        newFragment.setUserVisibleHint(true);
        ft.commitAllowingStateLoss();
        if (isForce) {
            RxBus.create().post(new ForceFreshEvent(ForceFreshEvent.START));
        }
    }
}
