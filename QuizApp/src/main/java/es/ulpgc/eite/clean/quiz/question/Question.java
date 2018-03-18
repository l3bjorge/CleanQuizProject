package es.ulpgc.eite.clean.quiz.question;

import android.content.Context;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;

/**
 * Created by Luis on 12/11/16.
 */

public interface Question {

  interface ToQuestion {
    void onScreenStarted();
    void setToolbarVisibility(boolean visibility);
  }

  interface CheatToQuestion {
    void onScreenResumed();
    void setAnswerBtnClicked(boolean clicked);
  }

  interface QuestionToCheat {
    boolean getToolbarVisibility();
    boolean getCurrentAnswer();
    Context getManagedContext();
  }

  /////////////////////////////////////////////////////////////////////////////////////


  /**
   * Methods offered to VIEW to communicate with PRESENTER
   */
  interface ViewToPresenter extends Presenter<PresenterToView> {
    void onCheatBtnClicked();
    void onFalseBtnClicked();
    void onNextBtnClicked();
    void onTrueBtnClicked();
  }

  /**
   * Required VIEW methods available to PRESENTER
   */
  interface PresenterToView extends ContextView {
    void hideAnswer();
    void hideToolbar();
    void setAnswer(String text);
    void setCheatButton(String label);
    void setFalseButton(String label);
    void setNextButton(String label);
    void setQuestion(String text);
    void setTrueButton(String label);
    void showAnswer();
  }

  /**
   * Methods offered to MODEL to communicate with PRESENTER
   */
  interface PresenterToModel extends Model<ModelToPresenter> {
    String getCheatLabel();
    String getCurrentAnswerLabel();
    boolean getCurrentAnswer();
    String getCurrentQuestionLabel();
    String getFalseLabel();
    String getNextLabel();
    void setNextQuestion();
    String getTrueLabel();
    void setCurrentAnswer(boolean answer);
  }

  /**
   * Required PRESENTER methods available to MODEL
   */
  interface ModelToPresenter {

  }
}
