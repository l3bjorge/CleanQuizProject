package es.ulpgc.eite.clean.quiz.cheat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.quiz.R;

public class CheatView
    extends GenericActivity<Cheat.PresenterToView, Cheat.ViewToPresenter, CheatPresenter>
    implements Cheat.PresenterToView {

  private Toolbar toolbarScreen;
  private Button buttonTrue, buttonFalse;
  private TextView labelConfirm, labelAnswer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);
    Log.d(TAG, "calling onCreate()");

    labelConfirm = (TextView) findViewById(R.id.labelConfirm);
    labelAnswer = (TextView) findViewById(R.id.labelAnswer);

    toolbarScreen = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbarScreen);

    buttonTrue = (Button) findViewById(R.id.buttonTrue);
    buttonTrue.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.d(TAG, "calling onTrueBtnClicked()");
        getPresenter().onTrueBtnClicked();
      }
    });
    buttonFalse = (Button) findViewById(R.id.buttonFalse);
    buttonFalse.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.d(TAG, "calling onFalseBtnClicked()");
        getPresenter().onFalseBtnClicked();
      }
    });
  }

  /**
   * Method that initialized MVP objects
   * {@link super#onResume(Class, Object)} should always be called
   */
  @SuppressLint("MissingSuperCall")
  @Override
  protected void onResume() {
    super.onResume(CheatPresenter.class, this);
    Log.d(TAG, "calling onResume()");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "calling onDestroy()");
  }

  /////////////////////////////////////////////////////////////////////////////////////
  // Presenter To View ///////////////////////////////////////////////////////////////


  @Override
  public void hideToolbar() {
    toolbarScreen.setVisibility(View.GONE);
  }

  @Override
  public void hideAnswer() {
    labelAnswer.setVisibility(View.INVISIBLE);
  }

  @Override
  public void setAnswer(String txt) {
    labelAnswer.setText(txt);
  }

  @Override
  public void setConfirm(String text) {
    labelConfirm.setText(text);
  }

  @Override
  public void setFalseButton(String label) {
    buttonFalse.setText(label);
  }

  @Override
  public void setTrueButton(String label) {
    buttonTrue.setText(label);
  }

  @Override
  public void showAnswer() {
    labelAnswer.setVisibility(View.VISIBLE);
  }

}
