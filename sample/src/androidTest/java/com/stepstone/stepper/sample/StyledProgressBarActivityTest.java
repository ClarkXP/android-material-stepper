package com.stepstone.stepper.sample;

import android.support.annotation.NonNull;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.stepstone.stepper.sample.test.action.SpoonScreenshotAction;
import com.stepstone.stepper.sample.test.rule.WakeUpIntentsTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.stepstone.stepper.sample.test.action.StepperNavigationActions.clickNext;
import static com.stepstone.stepper.sample.test.matcher.CommonMatchers.checkCompleteButtonShown;
import static com.stepstone.stepper.sample.test.matcher.CommonMatchers.checkCurrentStepIs;
import static org.hamcrest.Matchers.allOf;

/**
 * Performs tests on a styled progress bar stepper i.e. the one with {@code ms_stepperType="progress_bar"}.
 *
 * @author Piotr Zawadzki
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class StyledProgressBarActivityTest {

    @Rule
    public WakeUpIntentsTestRule<StyledProgressBarActivity> intentsTestRule = new WakeUpIntentsTestRule<>(StyledProgressBarActivity.class);

    @Test
    public void shouldStayOnTheFirstStepWhenVerificationFails() {
        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //then
        checkCurrentStepIs(0);
        SpoonScreenshotAction.perform(getScreenshotTag(1, "Verification failure test"));
    }

    @Test
    public void shouldGoToTheNextStepWhenVerificationSucceeds() {
        //given
        onView(allOf(withId(R.id.button), isCompletelyDisplayed())).perform(doubleClick());

        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //then
        checkCurrentStepIs(1);
        SpoonScreenshotAction.perform(getScreenshotTag(2, "Verification success test"));
    }

    @Test
    public void shouldShowCompleteButtonOnTheLastStep() {
        //given
        onView(allOf(withId(R.id.button), isCompletelyDisplayed())).perform(doubleClick());
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(allOf(withId(R.id.button), isCompletelyDisplayed())).perform(doubleClick());

        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //then
        checkCurrentStepIs(2);
        checkCompleteButtonShown();
        SpoonScreenshotAction.perform(getScreenshotTag(3, "Last step test"));
    }

    @NonNull
    private String getScreenshotTag(int position, @NonNull String title) {
        return String.format(Locale.ENGLISH,"%02d", position) + ". " + title;
    }

}
