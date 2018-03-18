package es.ulpgc.eite.clean.quiz.cheat;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.Model;
import es.ulpgc.eite.clean.mvp.Presenter;

/**
 * Created by Luis on 12/11/16.
 */

public interface Cheat {

  interface ToCheat {
    void onScreenResumed();
  }

  interface QuestionToCheat {
    void onScreenStarted();
    void setToolbarVisibility(boolean visible);
    void setCurrentAnswer(boolean answer);
  }

  interface CheatToQuestion {

  }


  /////////////////////////////////////////////////////////////////////////////////////


  /**
   * Methods offered to VIEW to communicate with PRESENTER
   */
  interface ViewToPresenter extends Presenter<PresenterToView> {
    void onFalseBtnClicked();
    void onTrueBtnClicked();
  }

  /**
   * Required VIEW methods available to PRESENTER
   */
  interface PresenterToView extends ContextView {
    void hideAnswer();
    void hideToolbar();
    void setAnswer(String text);
    void setConfirm(String text);
    void setFalseButton(String label);
    void setTrueButton(String label);
    void showAnswer();
  }

  /**
   * Methods offered to MODEL to communicate with PRESENTER
   */
  interface PresenterToModel extends Model<ModelToPresenter> {
    String getCurrentAnswerLabel();
    String getConfirmLabel();
    String getFalseLabel();
    String getTrueLabel();
    void setCurrentAnswer(boolean answer);
  }

  /**
   * Required PRESENTER methods available to MODEL
   */
  interface ModelToPresenter {

  }
}
