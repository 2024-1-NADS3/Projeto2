package com.example.telas_iniciais;

import com.example.telas_iniciais.LoginActivity;
import com.example.telas_iniciais.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class LoginActivityComponentTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testLoginSucesso() {
        // Simula um input
        Espresso.onView(ViewMatchers.withId(R.id.inputEmailLogin)).perform(ViewActions.typeText("example@example.com"));
        Espresso.onView(ViewMatchers.withId(R.id.inputSenhaLogin)).perform(ViewActions.typeText("password"));

        // Clica no botão de login
        Espresso.onView(ViewMatchers.withId(R.id.btnEntrar)).perform(ViewActions.click());

        // Checa se o login foi bem sucedido se a perfil abre
        //Espresso.onView(ViewMatchers.withId(R.id.activity_p_layout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }


    @Test
    public void testLoginInvalido() {
        // Simula um input
        Espresso.onView(ViewMatchers.withId(R.id.inputEmailLogin)).perform(ViewActions.typeText("invalid@example.com"));
        Espresso.onView(ViewMatchers.withId(R.id.inputSenhaLogin)).perform(ViewActions.typeText("invalid"));

        // Clica no botão de login
        Espresso.onView(ViewMatchers.withId(R.id.btnEntrar)).perform(ViewActions.click());

        // Checa se a mensagem de erro aparece
        Espresso.onView(ViewMatchers.withText("Erro de Login")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}
