package CustomComponents;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class UserProgressManager {
    private static final String PREF_NAME = "UserProgress";
    private static final String KEY_SOLVED_QUESTIONS = "SolvedQuestions";

    public  void markQuestionAsSolved(Context context, String questionId) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> solvedQuestions = preferences.getStringSet(KEY_SOLVED_QUESTIONS, new HashSet<>());

        // Check if the question has already been solved
        if (!solvedQuestions.contains(questionId)) {
            solvedQuestions.add(questionId);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putStringSet(KEY_SOLVED_QUESTIONS, solvedQuestions);
            editor.apply();
        }
    }

    public int getNumberOfSolvedQuestions(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> solvedQuestions = preferences.getStringSet(KEY_SOLVED_QUESTIONS, new HashSet<>());
        return solvedQuestions.size();
    }
}
