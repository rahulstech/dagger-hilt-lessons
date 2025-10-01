package rahulstech.android.daggerhiltdemo.lifecycle

import android.util.Log
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Singleton

@Singleton /* keep the instance till the app exists */
class AppScopedService @Inject constructor() {
    init {
        Log.i("AppScopedService", "app service created")
    }
}

/**
 *  NOTES on @ActivityScoped vs @ActivityRetainedScoped
 *
 * 👉 @ActivityScoped
 * - Scope: Tied to a single Activity instance.
 * - A new instance is created whenever the Activity is recreated
 *   (e.g., during a configuration change like screen rotation).
 * - Dies as soon as the Activity instance dies.
 * - Good for holding references that must not survive rotations,
 *   e.g., UI-related helpers, Activity-level resources,
 *   dialog managers, navigation controllers.
 *
 * 👉 @ActivityRetainedScoped
 * - Scope: Tied to the Activity’s ViewModelStoreOwner (survives config changes).
 * - Instance is retained across Activity recreation (e.g., survives rotation).
 * - Dies only when the Activity itself is finished for good.
 * - Good for data/business logic that you want to survive rotation,
 *   e.g., Repository access objects, UseCases, session managers.
 *
 * 🔍 Example Scenarios
 * - Use @ActivityScoped for:
 *     → A ToastHelper that depends on the current Activity context.
 *     → A Navigator that pushes fragments into the current Activity.
 *
 * - Use @ActivityRetainedScoped for:
 *     → A UserSessionManager that tracks if the user is logged in.
 *     → A repository wrapper that fetches user profile data and
 *       should not reset on screen rotation.
 */
@ActivityScoped // create new instance on each recreate eg. configuration change
//@ActivityRetainedScoped // create instance when lifecycle create event occurs and destroy when lifecycle destroy event occurs
class ActivityScopedService @Inject constructor() {
    init {
        Log.i("ActivityScopedService", "activity service created")
    }
}

@FragmentScoped /* keep the instance till the fragment exists */
class FragmentScopedService @Inject constructor() {
    init {
        Log.i("FragmentScopedService", "fragment service created")
    }
}