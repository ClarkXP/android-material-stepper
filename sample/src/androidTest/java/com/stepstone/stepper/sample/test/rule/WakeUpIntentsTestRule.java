package com.stepstone.stepper.sample.test.rule;

import android.app.Activity;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.view.WindowManager;

/**
 * {@link IntentsTestRule} which also wakes up the screen before each test.
 *
 * @author Piotr Zawadzki
 */
public class WakeUpIntentsTestRule<T extends Activity> extends IntentsTestRule<T> {

    public WakeUpIntentsTestRule(Class<T> activityClass) {
        super(activityClass);
    }

    public WakeUpIntentsTestRule(Class<T> activityClass, boolean initialTouchMode) {
        super(activityClass, initialTouchMode);
    }

    public WakeUpIntentsTestRule(Class<T> activityClass, boolean initialTouchMode, boolean launchActivity) {
        super(activityClass, initialTouchMode, launchActivity);
    }

    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getActivity().getWindow().addFlags(
                            WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                                    | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                                    | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
