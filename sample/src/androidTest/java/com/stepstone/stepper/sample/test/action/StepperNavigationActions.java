package com.stepstone.stepper.sample.test.action;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.ViewPagerActions;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.sample.test.idling.CustomViewPagerListener;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

/**
 * A set of actions which can potentially change the currently selected step in a {@link com.stepstone.stepper.StepperLayout}.
 *
 * @author Piotr Zawadzki
 */
public class StepperNavigationActions {

    /**
     * Clicks the Next button.
     */
    public static ViewAction clickNext() {
        return new AbstractStepperNavigationAction() {

            @Override
            public String getDescription() {
                return "Click on the Next button";
            }

            @Override
            protected void performAction(StepperLayout stepperLayout) {
                View nextButton = stepperLayout.findViewById(com.stepstone.stepper.R.id.ms_stepNextButton);
                nextButton.performClick();
            }

        };
    }

    /**
     * Based on {@link ViewPagerActions.ViewPagerScrollAction}.
     */
    private abstract static class AbstractStepperNavigationAction implements ViewAction {

        @Override
        public final Matcher<View> getConstraints() {
            return isDisplayed();
        }

        @Override
        public final void perform(UiController uiController, View view) {
            final StepperLayout stepperLayout = (StepperLayout) view;
            final ViewPager viewPager = (ViewPager) stepperLayout.findViewById(com.stepstone.stepper.R.id.ms_stepPager);
            // Add a custom tracker listener
            final CustomViewPagerListener customListener = new CustomViewPagerListener();
            viewPager.addOnPageChangeListener(customListener);

            // Note that we're running the following block in a try-finally construct. This
            // is needed since some of the actions are going to throw (expected) exceptions. If that
            // happens, we still need to clean up after ourselves to leave the system (Espresso) in a good
            // state.
            try {
                // Register our listener as idling resource so that Espresso waits until the
                // wrapped action results in the view pager getting to the STATE_IDLE state
                Espresso.registerIdlingResources(customListener);

                uiController.loopMainThreadUntilIdle();

                performAction(stepperLayout);

                uiController.loopMainThreadUntilIdle();

                customListener.mNeedsIdle = true;
                uiController.loopMainThreadUntilIdle();
                customListener.mNeedsIdle = false;
            } finally {
                // Unregister our idling resource
                Espresso.unregisterIdlingResources(customListener);
                // And remove our tracker listener from ViewPager
                viewPager.removeOnPageChangeListener(customListener);
            }
        }

        protected abstract void performAction(StepperLayout stepperLayout);
    }
}


