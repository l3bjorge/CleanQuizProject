package es.ulpgc.eite.clean.quiz.question;


import android.content.Context;
import android.util.Log;

import es.ulpgc.eite.clean.mvp.ContextView;
import es.ulpgc.eite.clean.mvp.GenericActivity;
import es.ulpgc.eite.clean.mvp.GenericPresenter;
import es.ulpgc.eite.clean.quiz.app.Mediator;

public class QuestionPresenter extends GenericPresenter
      <Question.PresenterToView, Question.PresenterToModel, Question.ModelToPresenter, QuestionModel>
    implements Question.ViewToPresenter, Question.ModelToPresenter,
      Question.ToQuestion, Question.CheatToQuestion, Question.QuestionToCheat {


  private boolean toolbarVisible;
  private boolean answerBtnClicked;
  private boolean answerVisible;

  /**
   * Operation called during VIEW creation in {@link GenericActivity#onResume(Class, Object)}
   * Responsible to initialize MODEL.
   * Always call {@link GenericPresenter#onCreate(Class, Object)} to initialize the object
   * Always call  {@link GenericPresenter#setView(ContextView)} to save a PresenterToView reference
   *
   * @param view The current VIEW instance
   */
  @Override
  public void onCreate(Question.PresenterToView view) {
    super.onCreate(QuestionModel.class, this);
    setView(view);
    Log.d(TAG, "calling onCreate()");

    Log.d(TAG, "calling startingQuestionScreen()");
    Mediator.Lifecycle mediator = (Mediator.Lifecycle) getView().getApplication();
    mediator.startingQuestionScreen(this);
  }

  /**
   * Operation called by VIEW after its reconstruction.
   * Always call {@link GenericPresenter#setView(ContextView)}
   * to save the new instance of PresenterToView
   *
   * @param view The current VIEW instance
   */
  @Override
  public void onResume(Question.PresenterToView view) {
    setView(view);
    Log.d(TAG, "calling onResume()");

    Mediator.Lifecycle mediator = (Mediator.Lifecycle) getView().getApplication();
    mediator.resumingQuestionScreen(this);
  }

  /**
   * Helper method to inform Presenter that a onBackPressed event occurred
   * Called by {@link GenericActivity}
   */
  @Override
  public void onBackPressed() {
    Log.d(TAG, "calling onBackPressed()");
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
  public void onCheatBtnClicked() {
    Log.d(TAG, "calling goToCheatScreen()");
    Mediator.Navigation mediator = (Mediator.Navigation) getView().getApplication();
    mediator.goToCheatScreen(this);
  }

  @Override
  public void onFalseBtnClicked() {
    answerBtnClicked = true;
    setButtonColors();
    onAnswerBtnClicked(false);
  }

  @Override
  public void onNextBtnClicked(){
    answerBtnClicked = false;
    loadNextQuestion();
  }

  @Override
  public void onTrueBtnClicked() {
    answerBtnClicked = true;
    setButtonColors();
    onAnswerBtnClicked(true);
  }

  /////////////////////////////////////////////////////////////////////////////////////
  // Cheat To Question ///////////////////////////////////////////////////////////////

  @Override
  public void onScreenResumed() {
    Log.d(TAG, "calling onScreenResumed()");
    settingUpdatedState();
  }

  @Override
  public void setAnswerBtnClicked(boolean clicked) {
    answerBtnClicked = clicked;
  }

  @Override
  public void setToolbarVisibility(boolean visibility) {
    toolbarVisible = visibility;
  }


  /////////////////////////////////////////////////////////////////////////////////////
  // To Question /////////////////////////////////////////////////////////////////////


  @Override
  public void onScreenStarted() {
    Log.d(TAG, "calling onScreenStarted()");
    settingInitialState();
  }


  /////////////////////////////////////////////////////////////////////////////////////
  // Question To Cheat ///////////////////////////////////////////////////////////////


  @Override
  public boolean getToolbarVisibility() {
    return toolbarVisible;
  }

  @Override
  public boolean getCurrentAnswer() {
    return getModel().getCurrentAnswer();
  }

  @Override
  public Context getManagedContext(){
    return getActivityContext();
  }

  /////////////////////////////////////////////////////////////////////////////////////


  private void loadNextQuestion(){
    Log.d(TAG, "calling loadNextQuestion()");
    getModel().setNextQuestion();
    settingInitialState();
  }

  private void settingInitialState(){
    Log.d(TAG, "calling settingInitialState()");
    setButtonLabels();
    setButtonColors();
    checkVisibility();
    getView().setQuestion(getModel().getCurrentQuestionLabel());
  }


  private void settingUpdatedState() {
    settingInitialState();
    Log.d(TAG, "calling settingUpdatedState()");

    if(answerVisible){
      getView().setAnswer(getModel().getCurrentAnswerLabel());
    }
  }

  private void onAnswerBtnClicked(boolean answer) {
    if(isViewRunning()) {
      getModel().setCurrentAnswer(answer);
      getView().setAnswer(getModel().getCurrentAnswerLabel());
      answerVisible = true;
    }
    checkAnswerVisibility();
  }

  private void checkAnswerVisibility(){
    if(isViewRunning()) {
      if(answerVisible) {
        getView().showAnswer();
      } else {
        getView().hideAnswer();
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
      getView().setCheatButton(getModel().getCheatLabel());
      getView().setNextButton(getModel().getNextLabel());
    }
  }

  private void setButtonColors(){
    if(isViewRunning()) {
      getView().setTrueButtonClickability(answerBtnClicked);
      getView().setFalseButtonClickability(answerBtnClicked);
      getView().setCheatButtonClickability(answerBtnClicked);
      getView().setNextButtonClickability(answerBtnClicked);
    }
  }


}
