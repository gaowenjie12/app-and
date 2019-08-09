package com.zsoe.businesssharing.base.presenter;


public interface PresenterFactory<P extends Presenter> {
    P createPresenter();
}
