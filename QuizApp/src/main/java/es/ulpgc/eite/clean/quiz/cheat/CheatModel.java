package es.ulpgc.eite.clean.quiz.cheat;

import android.util.Log;

import es.ulpgc.eite.clean.mvp.GenericModel;


public class CheatModel
    extends GenericModel<Cheat.ModelToPresenter> implements Cheat.PresenterToModel {

  private String falseLabel, trueLabel;
  private String confirmLabel;
  private boolean currentAnswer;

  /**
   * Method that recovers a reference to the PRESENTER
   * You must ALWAYS call {@link super#onCreate(Object)} here
   *
   * @param presenter Presenter interface
   */
  @Override
  public void onCreate(Cheat.ModelToPresenter presenter) {
    super.onCreate(presenter);
    Log.d(TAG, "calling onCreate()");

    falseLabel = "False";
    trueLabel = "True";
    confirmLabel = "Are you sure?";
  }

  /**
   * Called by layer PRESENTER when VIEW pass for a reconstruction/destruction.
   * Usefull for kill/stop activities that could be running on the background Threads
   *
   * @param isChangingConfiguration Informs that a change is occurring on the configuration
   */
  @Override
  public void onDestroy(boolean isChangingConfiguration) {
    if(isChangingConfiguration){
      Log.d(TAG, "calling onChangingConfiguration()");
    } else {
      Log.d(TAG, "calling onDestroy()");
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////
  // Presenter To Model //////////////////////////////////////////////////////////////


  @Override
  public String getConfirmLabel() {
    return confirmLabel;
  }


  @Override
  public String getFalseLabel() {
    return falseLabel;
  }


  @Override
  public String getCurrentAnswerLabel() {
    if(currentAnswer) {
      return trueLabel;
    } else {
      return falseLabel;
    }
  }

  @Override
  public String getTrueLabel() {
    return trueLabel;
  }

  @Override
  public void setCurrentAnswer(boolean answer){
    currentAnswer = answer;
  }


}
