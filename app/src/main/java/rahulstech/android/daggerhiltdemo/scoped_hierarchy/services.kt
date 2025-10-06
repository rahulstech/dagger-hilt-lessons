package rahulstech.android.daggerhiltdemo.scoped_hierarchy

import android.util.Log
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Singleton

/*
================================================================================
                 Dagger Hilt - Scope Hierarchy (Android Components)
================================================================================

        ┌──────────────────────────────────────────────────────────┐
        │                    @Singleton (AppComponent)             │
        │  - Lives as long as the entire application               │
        │  - Example: Retrofit, RoomDatabase, AnalyticsService     │
        └──────────────────────────────────────────────────────────┘
                                   │
                                   ▼
        ┌──────────────────────────────────────────────────────────┐
        │     @ActivityRetainedScoped (ActivityRetainedComponent)  │
        │  - Survives configuration changes                        │
        │  - Destroyed when activity is permanently destroyed      │
        │  - Example: ViewModel-like services      │
        └──────────────────────────────────────────────────────────┘
                                   │
                                   ▼
        ┌──────────────────────────────────────────────────────────┐
        │          @ActivityScoped (ActivityComponent)             │
        │  - Lives only during current Activity instance           │
        │  - Recreated after rotation                              │
        │  - Example: ActivityLogger, NavigationController         │
        └──────────────────────────────────────────────────────────┘
                                   │
                                   ▼
        ┌──────────────────────────────────────────────────────────┐
        │          @FragmentScoped (FragmentComponent)             │
        │  - Lives as long as Fragment is attached                 │
        │  - Destroyed when fragment is detached                   │
        │  - Example: FragmentUIManager, FragmentStateHandler      │
        └──────────────────────────────────────────────────────────┘
                                   │
                                   ▼
        ┌──────────────────────────────────────────────────────────┐
        │           @ViewScoped / @ViewWithFragmentScoped          │
        │  - Tied to Fragment’s View lifecycle                     │
        │  - Recreated with onCreateView/onDestroyView             │
        │  - Example: ViewBinder, Adapter, UI-only helpers         │
        └──────────────────────────────────────────────────────────┘
                                   │
                                   ▼
        ┌──────────────────────────────────────────────────────────┐
        │               @ServiceScoped (ServiceComponent)          │
        │  - Lives as long as the Android Service                  │
        │  - Example: ForegroundTaskHandler, SyncService           │
        └──────────────────────────────────────────────────────────┘

--------------------------------------------------------------------------------
 Rules of Scope Interaction:
  • Parent-scoped object can be injected into child components.
  • Child-scoped object CANNOT be injected into parent components.
  • Scopes represent lifecycle boundaries — shorter-lived objects depend on
    longer-lived ones, never the opposite.

Example Valid Injections:
  Singleton → ActivityRetained → Activity → Fragment → View
--------------------------------------------------------------------------------
*/


// destroyed as soon as the lifecycle destroy event occurred
@ActivityRetainedScoped
class CounterManager @Inject constructor() {
    init {
        Log.i("CounterManager","new CounterManager created $this")
    }

    private var count: Int = 0

    fun increment() { count++ }

    fun getValue() = count
}

// destroyed as soon as the configuration is changed
@ActivityScoped
class ActivityLogger @Inject constructor(
    private val counterManager: CounterManager,
    private val analyticsService: AnalyticsService
) {
    init {
        Log.i("ActivityLogger","new ActivityLogger created $this")
    }

    fun logIncrement() {
        analyticsService.trackEvent("logging increment ${counterManager.getValue()} for ${this.hashCode()}")
    }
}

// destroyed as soon as detached from activity
@FragmentScoped
class FragmentUIManager @Inject constructor(
    private val counterManager: CounterManager
) {
    init {
        Log.i("FragmentUIManager","new FragmentUIManager created $this")
    }

    fun handleIncrement(): Int {
        counterManager.increment()
        return counterManager.getValue()
    }
}

// destroyed when app killed
@Singleton
class AnalyticsService @Inject constructor() {

    init {
        Log.i("AnalyticsService","new AnalyticsService created $this")
    }

    fun trackEvent(message: String) {
        Log.i("AnalyticsService",message)
    }
}