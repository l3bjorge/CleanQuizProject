package es.ulpgc.eite.clean.quiz.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import es.ulpgc.eite.clean.quiz.cheat.Cheat;
import es.ulpgc.eite.clean.quiz.cheat.CheatView;
import es.ulpgc.eite.clean.quiz.question.Question;


public class MediatorApp extends Application implements Mediator.Lifecycle, Mediator.Navigation {

  private final String TAG = this.getClass().getSimpleName();

  private QuestionState toQuestionState;
  private QuestionState cheatToQuestionState;
  private CheatState questionToCheatState;
  private CheatState toCheatState;

  @Override
  public void onCreate() {
    super.onCreate();
    Log.d(TAG, "calling onCreate()");

    Log.d(TAG, "calling creatingInitialQuestionState()");
    toQuestionState = new QuestionState();
    toQuestionState.toolbarVisible = false;
  }


  /////// Lifecycle ////////////////////////////////////////////////////////////////////


  @Override
  public void goToCheatScreen(Question.QuestionToCheat presenter){
    Log.d(TAG, "calling savingCheatState()");
    questionToCheatState = new CheatState();
    questionToCheatState.toolbarVisible = presenter.getToolbarVisibility();
    questionToCheatState.currentAnswer = presenter.getCurrentAnswer();

    Context view = presenter.getManagedContext();
    if (view != null) {
      Log.d(TAG, "calling startingCheatScreen()");
      view.startActivity(new Intent(view, CheatView.class));
    }
  }


  @Override
  public void backToQuestionScreen(Cheat.CheatToQuestion presenter){
    Log.d(TAG, "calling savingUpdatedState()");
    cheatToQuestionState = new QuestionState();
    cheatToQuestionState.toolbarVisible = presenter.getToolbarVisibility();
    cheatToQuestionState.cheated = presenter.getCheated();


    Context view = presenter.getManagedContext();
    if (view != null) {
      //Log.d(TAG, "calling startingHelloScreen()");
      //view.startActivity(new Intent(view, HelloView.class));
      Log.d(TAG, "calling finishingCurrentScreen()");
      presenter.destroyView();
    }

  }

  /////// Navigation ////////////////////////////////////////////////////////////////////


  @Override
  public void startingQuestionScreen(Question.ToQuestion presenter){
    if(toQuestionState != null) {
      Log.d(TAG, "calling settingInitialQuestionState()");
      presenter.setToolbarVisibility(toQuestionState.toolbarVisible);

      Log.d(TAG, "calling removingInitialQuestionState()");
      toQuestionState = null;
    }

    presenter.onScreenStarted();
  }

  @Override
  public void resumingQuestionScreen(Question.CheatToQuestion presenter) {

    if(cheatToQuestionState != null) {
      Log.d(TAG, "calling resumingQuestionScreen()");
      Log.d(TAG, "calling settingUpdatedQuestionState()");
      presenter.setAnswerBtnClicked(cheatToQuestionState.answerBtnClicked);
      presenter.setCheated(cheatToQuestionState.cheated);

      Log.d(TAG, "calling removingUpdatedQuestionState()");
      cheatToQuestionState = null;
    }

    presenter.onScreenResumed();
  }


  @Override
  public void startingCheatScreen(Cheat.QuestionToCheat presenter) {
    if(questionToCheatState != null) {
      Log.d(TAG, "calling settingCheatState()");
      presenter.setToolbarVisibility(questionToCheatState.toolbarVisible);
      presenter.setCurrentAnswer(questionToCheatState.currentAnswer);

      Log.d(TAG, "calling removingCheatState()");
      questionToCheatState = null;
    }

    presenter.onScreenStarted();
  }


  @Override
  public void resumingCheatScreen(Cheat.ToCheat presenter) {
    if(toCheatState != null) {
      Log.d(TAG, "calling resumingCheatScreen()");
      Log.d(TAG, "calling settingCheatState()");

      Log.d(TAG, "calling removingCheatState()");
      toCheatState = null;
    }

    presenter.onScreenResumed();
  }

  /////////////////////////////////////////////////////////////////////////////////////


  private class QuestionState {
    boolean toolbarVisible;
    boolean answerBtnClicked;
    boolean cheated;
  }

  private class CheatState {
    boolean toolbarVisible;
    boolean currentAnswer;
  }

}
