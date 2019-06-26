# SafeArea
Android lib can provide a view to handle system view insets(Such as: StatusBar, NavigationBar, etc).You can also use it to makeyour layout under system bar,And change the System UI's background color.

This lib can make it easier to manage the System ui insets on android.It just like SafeArea on IOS.
All you need to do is to add Safe Area to your layout and tell it which edge you may to put it(TOP, BOTTOM, LEFT, RIGHT).
It will measure itself to fit the system ui(Like statusbar, navigation bar).And that is all.

Below KitKit: It's size always 0, Because of the Android limit.So it can not work before KitKat. But you don't need to do any thing else. Because it is invisible.

KitKat: Top is always equal to the status bar height, Bottom is alwats equal to the navigationbar's height.
        It's always 0 on both LEFT and Right Edge.
        
After Kitkat: It can fit the right inset size, on all four direction. 

Example:

In activity:

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val overSystemUIHelper = AppCompatOverSystemUIHelper()
        overSystemUIHelper.setOverSystemUi(window, true)

        overSystemUIHelper.setStatusBarBackgroundColor(window, Color.YELLOW)
        overSystemUIHelper.setNavigationBarBackgroundColor(window, Color.DKGRAY)

        setContentView(R.layout.activity_main)
   }
}

In layout:

<android.support.constraint.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/colorPrimary"
        tools:context=".MainActivity">

    <com.wenhao.xia.safearea.SafeArea
            android:layout_width="0dp"
            android:layout_height="0dp"
            app:edgeDirection="top"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            android:id="@id/safeAreaTop"/>

    <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Hello World!"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toBottomOf="@id/safeAreaTop"/>

    <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:layout_constraintBottom_toTopOf="@id/safeAreaBottom"
            android:text="BottomEdge"
            app:layout_constraintEnd_toEndOf="@id/safeAreaBottom"
            app:layout_constraintStart_toStartOf="@id/safeAreaBottom"/>

    <com.wenhao.xia.safearea.SafeArea
            android:layout_width="0dp"
            android:layout_height="0dp"
            app:edgeDirection="bottom"
            app:layout_constraintEnd_toEndOf="parent"
            android:id="@id/safeAreaBottom"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintHorizontal_bias="0.0"/>
</android.support.constraint.ConstraintLayout>

I recommand that you use the ids i use {safeAreaBottom, safeAreaTop, safeAreaLeft, safeAreaRight} to SafeArea's id.
Because in kitkat, the background color we use. is just directly set to SafeArea. So if you use the Id I provide.You don't need to do any thing else.But there is a other way to provide the SafeArea by your self,you just need to set the TranslucentSystemUIHelper's safeAreaProvider. 

Example:

1.You need to optional cast your helper to TranslucentSystemUIHelper:

val overSystemUIHelper = AppCompatOverSystemUIHelper() as? TranslucentSystemUIHelper

2.The set the provider:

overSystemUIHelper?.safeAreaProvider = object : SafeAreaProvider {...}

