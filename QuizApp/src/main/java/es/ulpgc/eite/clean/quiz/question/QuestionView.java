package es.ulpgc.eite.clean.quiz.question;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.quiz.R;


public class QuestionView
    extends GenericActivity<Question.PresenterToView, Question.ViewToPresenter, QuestionPresenter>
    implements Question.PresenterToView {

  private Toolbar toolbarScreen;
  private Button buttonTrue, buttonFalse, buttonCheat, buttonNext;
  private TextView labelQuestion, labelAnswer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);
    Log.d(TAG, "calling onCreate()");

    labelQuestion = (TextView) findViewById(R.id.labelQuestion);
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
    buttonCheat = (Button) findViewById(R.id.buttonCheat);
    buttonCheat.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.d(TAG, "calling onCheatBtnClicked()");
        getPresenter().onCheatBtnClicked();
      }
    });
    buttonNext = (Button) findViewById(R.id.buttonNext);
    buttonNext.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.d(TAG, "calling onNextBtnClicked()");
        getPresenter().onNextBtnClicked();
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
    super.onResume(QuestionPresenter.class, this);
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
  public void hideAnswer() {
    labelAnswer.setVisibility(View.INVISIBLE);
  }

  @Override
  public void hideToolbar() {
    toolbarScreen.setVisibility(View.GONE);
  }

  @Override
  public void setAnswer(String text) {
    labelAnswer.setText(text);
  }

  @Override
  public void setCheatButton(String label) {
    buttonCheat.setText(label);
  }

  @Override
  public void setFalseButton(String label) {
    buttonFalse.setText(label);
  }

  @Override
  public void setNextButton(String label) {
    buttonNext.setText(label);
  }

  @Override
  public void setQuestion(String text) {
    labelQuestion.setText(text);
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
