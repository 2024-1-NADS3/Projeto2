package br.fecap.fecap_social;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.telas_iniciais.R;

@RunWith(AndroidJUnit4.class)
public class LoginActivityComponentTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    /**
     * método que testa o input de um login válido
     */
    public void testLoginSucesso() {
        // Simula um input
        Espresso.onView(ViewMatchers.withId(R.id.inputTokenLogin)).perform(ViewActions.typeText("skl.projetopi2@gmail.com"));
        Espresso.onView(ViewMatchers.withId(R.id.inputSenhaLogin)).perform(ViewActions.typeText("valid6"), ViewActions.closeSoftKeyboard());

        // Clica no botão de login
        Espresso.onView(ViewMatchers.withId(R.id.btnEntrar)).perform(ViewActions.click());

    }


    @Test
    /**
     * método que testa o input de um login inválido
     */
    public void testLoginInvalido() {
        // Simula um input
        Espresso.onView(ViewMatchers.withId(R.id.inputTokenLogin)).perform(ViewActions.typeText("skl.projetopi2@gmail.com"));
        Espresso.onView(ViewMatchers.withId(R.id.inputSenhaLogin)).perform(ViewActions.typeText("invalid"), ViewActions.closeSoftKeyboard());

        // Clica no botão de login
        Espresso.onView(ViewMatchers.withId(R.id.btnEntrar)).perform(ViewActions.click());

        // Verifica se o alerta é exibido com o título correto
        Espresso.onView(ViewMatchers.withText("Erro de Login")).inRoot(RootMatchers.isDialog()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Verifica se o alerta contém a mensagem correta
        Espresso.onView(ViewMatchers.withText("Email ou senha incorretos!!!")).inRoot(RootMatchers.isDialog()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Verifica se o botão no alerta contém o texto correto
        Espresso.onView(ViewMatchers.withText("Tentar novamente")).inRoot(RootMatchers.isDialog()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }


}
