//
//package anu.edu.cecs.holistay;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.intent.Intents.intended;
//import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
//
//import androidx.test.espresso.action.ViewActions;
//import androidx.test.espresso.intent.rule.IntentsTestRule;
//import androidx.test.espresso.matcher.ViewMatchers;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.LargeTest;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import anu.edu.cecs.holistay.ui.BookingStateActivity;
//import anu.edu.cecs.holistay.ui.PaymentMode;
//
//
//
//
//@RunWith(AndroidJUnit4.class)
//@LargeTest
//
//
//public class BookingStateClassTest {
//    @Rule
//    public IntentsTestRule<BookingStateActivity> activityRule = new IntentsTestRule<BookingStateActivity>(BookingStateActivity.class);
//
//    @Test
//    public void nextStageActivity() {
//        onView(ViewMatchers.withId(R.id.PersonName)).perform(typeText("Saurabh"), closeSoftKeyboard());
//        onView(ViewMatchers.withId(R.id.EmailAddress)).perform(typeText("Saurabh@gmail.com"), closeSoftKeyboard());
//        onView(ViewMatchers.withId(R.id.Phone)).perform(typeText("444999666"), closeSoftKeyboard());
//        onView(ViewMatchers.withId(R.id.button2)).perform(ViewActions.click());
//        intended(hasComponent(PaymentMode.class.getName()));
//
//    }
//
//}
