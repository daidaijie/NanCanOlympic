package com.jscheng.mr_horse.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.jscheng.mr_horse.R;
import com.jscheng.mr_horse.adapter.AnswerViewPaperAdapter;
import com.jscheng.mr_horse.model.QuestionModel;
import com.jscheng.mr_horse.model.PatternStatus;
import com.jscheng.mr_horse.presenter.AnswerPresenter;
import com.jscheng.mr_horse.presenter.impl.AnswerPresenterImpl;
import com.jscheng.mr_horse.view.AnswerView;
import com.orhanobut.logger.Logger;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PracticeActivity extends BaseActivity implements AnswerView {

    @BindView(R.id.viewpaper)
    ViewPager answerViewPager;
    @BindView(R.id.dati_pattern_view)
    Button datiPatternView;
    @BindView(R.id.beiti_pattern_view)
    Button beitiPatternView;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;

    private AnswerPresenter answerPresenter;
    private AnswerViewPaperAdapter answerViewPaperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        ButterKnife.bind(this);
        answerPresenter = new AnswerPresenterImpl(this);
        answerPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(answerPresenter !=null) {
            answerPresenter.detachView(false);
            if(answerViewPaperAdapter!=null){
                answerViewPaperAdapter.removeAnswerPageListener(answerPresenter);
            }
        }
    }

    @Override
    public void initPaperAdapter(List<QuestionModel> questionModelList, PatternStatus status) {
        answerViewPaperAdapter = new AnswerViewPaperAdapter(this, questionModelList,status);
        answerViewPager.setAdapter(answerViewPaperAdapter);
        answerViewPaperAdapter.addAnswerPageListener(answerPresenter);
    }

    @Override
    public void showProcessing() {
        progressWheel.setVisibility(View.VISIBLE);
        ValueAnimator progressFadeInAnim = ObjectAnimator.ofFloat(progressWheel, "alpha", 0, 1, 1);
        progressFadeInAnim.start();
    }

    @Override
    public void hideProcessing() {
        progressWheel.setVisibility(View.GONE);
        ValueAnimator progressFadeInAnim = ObjectAnimator.ofFloat(progressWheel, "alpha", 1, 0, 0);
        progressFadeInAnim.start();
    }

    @Override
    public void changeToBeitiView() {
        Logger.e("beiti");
        int top1 = datiPatternView.getPaddingTop();
        int left1 = datiPatternView.getPaddingLeft();
        int right1 = datiPatternView.getPaddingRight();
        int bottom1 = datiPatternView.getPaddingBottom();
        int top2 = beitiPatternView.getPaddingTop();
        int left2 = beitiPatternView.getPaddingLeft();
        int right2 = beitiPatternView.getPaddingRight();
        int bottom2 = beitiPatternView.getPaddingBottom();

        datiPatternView.setBackgroundResource(R.drawable.beiti_pattern_left);
        beitiPatternView.setBackgroundResource(R.drawable.beiti_pattern_right);
        datiPatternView.setPadding(left1,top1,right1,bottom1);
        beitiPatternView.setPadding(left2,top2,right2,bottom2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            datiPatternView.setTextColor(getColor(R.color.white));
            beitiPatternView.setTextColor(getColor(R.color.colorPrimary));
        }else {
            datiPatternView.setTextColor(getResources().getColor(R.color.white));
            beitiPatternView.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public void changeToDatiView() {
        Logger.e("dati");
        int top1 = datiPatternView.getPaddingTop();
        int left1 = datiPatternView.getPaddingLeft();
        int right1 = datiPatternView.getPaddingRight();
        int bottom1 = datiPatternView.getPaddingBottom();
        int top2 = beitiPatternView.getPaddingTop();
        int left2 = beitiPatternView.getPaddingLeft();
        int right2 = beitiPatternView.getPaddingRight();
        int bottom2 = beitiPatternView.getPaddingBottom();

        datiPatternView.setBackgroundResource(R.drawable.dati_pattern_left);
        beitiPatternView.setBackgroundResource(R.drawable.dati_pattern_right);
        datiPatternView.setPadding(left1,top1,right1,bottom1);
        beitiPatternView.setPadding(left2,top2,right2,bottom2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            beitiPatternView.setTextColor(getColor(R.color.white));
            datiPatternView.setTextColor(getColor(R.color.colorPrimary));
        }else {
            beitiPatternView.setTextColor(getResources().getColor(R.color.white));
            datiPatternView.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public void showError(String s) {

    }

    @OnClick(R.id.dati_pattern_view)
    public void onClickDatiPatternView(){
        answerPresenter.onClickDatiPattern();
    }

    @OnClick(R.id.beiti_pattern_view)
    public void onClickBeitiPatternView(){
        answerPresenter.onClickBeitiPattern();
    }
}
