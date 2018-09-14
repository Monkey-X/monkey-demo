package com.example.xlc.monkey.mvp;

import com.example.xlc.monkey.mvp.bace.BasePresenter;
import com.example.xlc.monkey.mvp.bean.BaseObjectBean;
import com.example.xlc.monkey.mvp.bean.LoginBean;
import com.example.xlc.monkey.mvp.contract.MainContract;
import com.example.xlc.monkey.mvp.net.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * @author:xlc
 * @date:2018/9/14
 * @descirbe:
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private MainContract.Model model;

    public MainPresenter() {
        model = new MainModel();
    }

    @Override
    public void login(String username, String password) {
            //view是否绑定，如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }

        mView.showLoading();
        model.login(username,password)
                .compose(RxScheduler.<BaseObjectBean<LoginBean>>Flo_io_main())
                .subscribe(new Consumer<BaseObjectBean<LoginBean>>() {
                    @Override
                    public void accept(BaseObjectBean<LoginBean> bean) throws Exception {
                            mView.onSuccess(bean);
                            mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }
}
