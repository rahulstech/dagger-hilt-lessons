### Hilt Scopes and Components

---

**Hilt Scopes & Components — Detailed Notes**

**1. What Determines an Object’s Lifetime**

In Hilt, an object’s lifetime (how long it stays alive in memory) depends on **two factors**:

1. **The Component it’s installed in** — via `@InstallIn(SomeComponent::class)`
2. **The Scope annotation on the class** — like `@Singleton`, `@ActivityScoped`, etc.


**2. Component = “Where it lives”**

Each Hilt *component* represents a part of the Android lifecycle.


SingletonComponent          -> lives as long as the app process
└── ActivityRetainedComponent  -> survives configuration changes
└── ActivityComponent         -> dies when activity dies
└── FragmentComponent        -> dies when fragment is detached
└── ViewComponent           -> dies when view is destroyed

The **component** defines the *maximum possible lifetime* of any object it owns.

**3. Scope = “How long it lives within that component”**

A **scope annotation** (like `@Singleton`, `@ActivityScoped`, etc.) tells Hilt:
> “Within this component, reuse the same instance as long as the component lives.”

If no scope is provided:
- A **new instance** is created every time that dependency is requested from the component.

**4. How They Work Together**

**The golden rule:**
>  **Actual lifetime = shortest between the object’s scope and the component’s lifetime.**

That means:
- You can **narrow** lifetime (shorter than the component’s).
- But you **can’t extend** lifetime beyond the component that owns it.

| `@InstallIn` Component | Object Scope | Actual Lifetime | Notes |
|------------------------|---------------|------------------|--------|
| `SingletonComponent` | `@Singleton` | App lifetime | Normal singleton |
| `SingletonComponent` | *(no scope)* | New every injection | Not cached |
| `ActivityComponent` | `@ActivityScoped` | Activity lifetime | Normal |
| `ActivityComponent` | `@Singleton` |  Activity lifetime | Object dies when activity dies |
| `FragmentComponent` | `@Singleton` |  Fragment lifetime | Still dies when fragment dies |
| `SingletonComponent` | `@ActivityScoped` |  Invalid | Narrower scope not allowed |


**Compile-Time Validation Rules**

Hilt validates component-scope compatibility at compile time:
- You **cannot install** a *narrower* scope into a *larger* component.
    - e.g., `@ActivityScoped` inside a module installed in `SingletonComponent` →  compile error.
- You **can** install a *wider* scope (like `@Singleton`) inside a smaller component.
    - But the object will only live as long as the **component**, not the scope itself.

**6. Thinking Hierarchically**

When an ActivityComponent is destroyed:
- All its **child components** (FragmentComponent, ViewComponent) are destroyed.
- All scoped objects in those components are also destroyed.
- The parent (e.g., ActivityRetainedComponent or SingletonComponent) still lives.

So the hierarchy enforces a *lifetime boundary*:
> **No child can outlive its parent.**

**7. Real-World Example**

```kotlin
// Installed in SingletonComponent → app-wide
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAnalyticsService() = AnalyticsService()
}

// Installed in ActivityComponent → per-activity
@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    @Singleton // allowed but limited by ActivityComponent
    fun provideLogger(analytics: AnalyticsService) = Logger(analytics)
}
````

Here:

* `AnalyticsService` lives as long as the **app**.
* `Logger` lives only as long as the **activity**, even though it’s marked `@Singleton`.


**8. Custom Scopes**

You can define custom scopes for semantic clarity (e.g., `@SessionScoped`, `@FeatureScoped`):

```kotlin
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SessionScoped
```

However:

* Custom scopes **do not create new lifecycle boundaries**.
* They only act as markers for developers — indicating intended usage.
* The actual lifetime still depends on the component installed via `@InstallIn`.

So:

> A `@SessionScoped` object installed in `SingletonComponent` will still live for the app’s lifetime — but the annotation tells readers it should logically reset on logout.

**9. Summary Cheat Sheet**

```
Actual Lifetime = min(Object Scope, Component Lifetime)

Component Hierarchy:
  SingletonComponent
    └── ActivityRetainedComponent
          └── ActivityComponent
                └── FragmentComponent
                      └── ViewComponent

Rules:
   You can narrow lifetime (bigger scope → smaller component)
  You can’t expand lifetime (smaller scope → bigger component)
```

**10. Rule of Thumb**

| You want...                                             | Use this                                               |
| ------------------------------------------------------- | ------------------------------------------------------ |
| App-wide single instance                                | `@Singleton` + `@InstallIn(SingletonComponent::class)` |
| Survive config changes but reset when activity finishes | `@ActivityRetainedScoped`                              |
| Live only while activity is alive                       | `@ActivityScoped`                                      |
| Live only while fragment is attached                    | `@FragmentScoped`                                      |
| Live only while view is attached                        | `@ViewScoped`                                          |


**In short:**

> `@InstallIn` defines *where* it can live.
> Scope defines *how long* it lives within that boundary.
> The actual retention is always bounded by the **shorter of the two.**
> Module always provides object whose lifetime is same or more than the module's lifetime. 
  in simple word a module InstalledIn SingletonComponent can never provide an object with ActivityRetainedScoped, ActivityScoped etc.
  but module InstalledIn ActivityComponent can provide object with Singleton scoped or ActivityRetainedScoped but not with FragmentScoped or FragmentViewScoped etc.
  if i try to violate this hierarchy a compile time error will be thrown
