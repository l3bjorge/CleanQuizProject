package es.ulpgc.eite.clean.quiz.app;

import es.ulpgc.eite.clean.quiz.cheat.Cheat;
import es.ulpgc.eite.clean.quiz.question.Question;

/**
 * Created by imac on 23/1/18.
 */

public interface Mediator {

  interface Lifecycle {

    void startingQuestionScreen(Question.ToQuestion presenter);
    void startingCheatScreen(Cheat.QuestionToCheat presenter);
    void resumingQuestionScreen(Question.CheatToQuestion presenter);
    void resumingCheatScreen(Cheat.ToCheat presenter);
  }

  interface Navigation {

    void goToCheatScreen(Question.QuestionToCheat presenter);
    void backToQuestionScreen(Cheat.CheatToQuestion presenter);
  }

}
