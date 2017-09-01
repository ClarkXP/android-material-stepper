package com.stepstone.stepper.sample.test.matcher;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.stepstone.stepper.internal.widget.StepTabStateMatcher;
import com.stepstone.stepper.sample.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.stepstone.stepper.sample.test.matcher.StepperLayoutTabStateMatcher.tabAtPositionIsInState;
import static com.stepstone.stepper.sample.test.matcher.ViewPagerPositionMatcher.hasPagePosition;

/**
 * Contains commonly used matchers.
 *
 * @author Piotr Zawadzki
 */
public final class CommonMatchers {

    private CommonMatchers() {
    }

    public static void checkTabState(int position, @NonNull StepTabStateMatcher.TabState state) {
        onView(withId(R.id.stepperLayout)).check(matches(tabAtPositionIsInState(position, state)));
    }

    public static void checkCurrentStepIs(@IntRange(from = 0) int expectedCurrentStep) {
        onView(withId(R.id.ms_stepPager)).check(matches(hasPagePosition(expectedCurrentStep)));
    }

    public static void checkCompleteButtonShown() {
        onView(withId(R.id.ms_stepCompleteButton)).check(matches(isDisplayed()));
    }

}
