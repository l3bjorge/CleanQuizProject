package es.ulpgc.eite.clean.quiz.cheat;


import android.content.Context;
import android.util.Log;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.quiz.app.Mediator;

public class CheatPresenter extends GenericPresenter
      <Cheat.PresenterToView, Cheat.PresenterToModel, Cheat.ModelToPresenter, CheatModel>
    implements Cheat.ViewToPresenter, Cheat.ModelToPresenter,
      Cheat.QuestionToCheat, Cheat.ToCheat, Cheat.CheatToQuestion {


  private boolean toolbarVisible;
  private boolean answerBtnClicked;
  private boolean cheated;
  //private boolean currentAnswer;

  /**
   * Operation called during VIEW creation in {@link GenericActivity#onResume(Class, Object)}
   * Responsible to initialize MODEL.
   * Always call {@link GenericPresenter#onCreate(Class, Object)} to initialize the object
   * Always call  {@link GenericPresenter#setView(ContextView)} to save a PresenterToView reference
   *
   * @param view The current VIEW instance
   */
  @Override
  public void onCreate(Cheat.PresenterToView view) {
    super.onCreate(CheatModel.class, this);
    setView(view);
    Log.d(TAG, "calling onCreate()");

    Log.d(TAG, "calling startingCheatScreen()");
    Mediator.Lifecycle mediator = (Mediator.Lifecycle) getView().getApplication();
    mediator.startingCheatScreen(this);
  }

  /**
   * Operation called by VIEW after its reconstruction.
   * Always call {@link GenericPresenter#setView(ContextView)}
   * to save the new instance of PresenterToView
   *
   * @param view The current VIEW instance
   */
  @Override
  public void onResume(Cheat.PresenterToView view) {
    setView(view);
    Log.d(TAG, "calling onResume()");

    Mediator.Lifecycle mediator = (Mediator.Lifecycle) getView().getApplication();
    mediator.resumingCheatScreen(this);
  }

  /**
   * Helper method to inform Presenter that a onBackPressed event occurred
   * Called by {@link GenericActivity}
   */
  @Override
  public void onBackPressed() {
    Log.d(TAG, "calling onBackPressed()");

    Log.d(TAG, "calling backToQuestionScreen()");
    Mediator.Navigation mediator = (Mediator.Navigation) getView().getApplication();
    mediator.backToQuestionScreen(this);
  }

  /**
   * Hook method called when the VIEW is being destroyed or
   * having its configuration changed.
   * Responsible to maintain MVP synchronized with Activity lifecycle.
   * Called by onDestroy methods of the VIEW layer, like: {@link GenericActivity#onDestroy()}
   *
   * @param isChangingConfiguration true: configuration changing & false: being destroyed
   */
  @Override
  public void onDestroy(boolean isChangingConfiguration) {
    super.onDestroy(isChangingConfiguration);

    if(isChangingConfiguration){
      Log.d(TAG, "calling onChangingConfiguration()");
    } else {
      Log.d(TAG, "calling onDestroy()");
    }
  }


  /////////////////////////////////////////////////////////////////////////////////////
  // View To Presenter ///////////////////////////////////////////////////////////////


  @Override
  public void onFalseBtnClicked() {
    Log.d(TAG, "calling backToQuestionScreen()");
    Mediator.Navigation mediator = (Mediator.Navigation) getView().getApplication();
    mediator.backToQuestionScreen(this);
  }

  @Override
  public void onTrueBtnClicked() {
    if(isViewRunning()) {
      getView().setAnswer(getModel().getCurrentAnswerLabel());
      //answerVisible = true;
      answerBtnClicked = true;
      cheated= true;
    }
    checkAnswerVisibility();
  }


  /////////////////////////////////////////////////////////////////////////////////////
  // To Cheat ////////////////////////////////////////////////////////////////////////


  @Override
  public void onScreenResumed() {
    Log.d(TAG, "calling onScreenResumed()");
    settingUpdatedState();
  }

  /////////////////////////////////////////////////////////////////////////////////////
  // Cheat To Question ///////////////////////////////////////////////////////////////



  /////////////////////////////////////////////////////////////////////////////////////
  // Question To Cheat ///////////////////////////////////////////////////////////////

  @Override
  public void onScreenStarted() {
    Log.d(TAG, "calling onScreenStarted()");
    settingInitialState();
  }

  @Override
  public void setCurrentAnswer(boolean answer){
    getModel().setCurrentAnswer(answer);
  }

  @Override
  public void setToolbarVisibility(boolean visibility) {
    toolbarVisible = visibility;
  }



  /////////////////////////////////////////////////////////////////////////////////////

  private void settingInitialState(){
    Log.d(TAG, "calling settingInitialState()");
    setButtonLabels();
    checkVisibility();
  }

  private void settingUpdatedState(){
    settingInitialState();
    Log.d(TAG, "calling settingUpdatedState()");

    if(answerBtnClicked){
      getView().setAnswer(getModel().getCurrentAnswerLabel());
    }
  }

  private void checkAnswerVisibility(){
    if(isViewRunning()) {
      if(!answerBtnClicked) {
        getView().hideAnswer();
      } else {
        getView().showAnswer();
      }
    }
  }


  private void checkToolbarVisibility(){
    if(isViewRunning()) {
      if (!toolbarVisible) {
        getView().hideToolbar();
      }
    }
  }

  private void checkVisibility(){
    checkToolbarVisibility();
    checkAnswerVisibility();
  }


  private void setButtonLabels(){
    if(isViewRunning()) {
      getView().setTrueButton(getModel().getTrueLabel());
      getView().setFalseButton(getModel().getFalseLabel());
      getView().setConfirm(getModel().getConfirmLabel());
    }
  }

  @Override
  public Context getManagedContext() {
    return getActivityContext();
  }

  @Override
  public boolean getToolbarVisibility() {
    return toolbarVisible;
  }

  @Override
  public boolean getCheated() {
    return cheated;
  }

    @Override
    public void destroyView() {
      if (isViewRunning()) {
        getView().finishScreen();
      }
    }
}
