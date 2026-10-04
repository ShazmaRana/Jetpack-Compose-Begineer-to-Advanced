
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigationapp.AddRoute
import com.example.navigationapp.AddScreen
import com.example.navigationapp.ProfileRoute
import com.example.navigationapp.ProfileScreen
import androidx.navigation.toRoute


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController , startDestination = AddRoute){

        composable<AddRoute>{
            AddScreen(onNavigateNext = { name, email, rollnum, university, skills, experience, imageUri ->
                navController.navigate(
                    ProfileRoute(name, email, rollnum, university, skills, experience, imageUri)
                ) {

                    //optional
                    ///popUpTo<AddRoute> {inclusive = true}


//                    What the curly braces are
//
//                    navController.navigate(route) { ... } takes an optional second argument: a lambda (the { } block) where you configure how the navigation happens. In Kotlin, if the last parameter of a function is a lambda, you can write it outside the parentheses. Inside the block, you can set options such as popUpTo, launchSingleTop, and restoreState.
//
//                    kotlin
//                    navController.navigate(ProfileRoute(...)) {
//                    // navigation options go here
//                }
////
////                    Without the block, it just navigates with default behavior.
////
//                    What popUpTo does
//
//                    Navigation keeps a back stack, a pile of screens. Pressing Back removes the top one and shows the one underneath.
//
//                    Default behavior (no popUpTo):
//
//                    Before submit:   [ AddScreen ]
//                    After submit:    [ AddScreen, ProfileScreen ]   <- Back returns to AddScreen
//
//                            With popUpTo<AddRoute> { inclusive = true }:
//
//                    Before submit:   [ AddScreen ]
//                    After submit:    [ ProfileScreen ]              <- AddScreen removed
//
//                            It means: "before navigating, pop (remove) screens off the stack until you reach AddRoute." And inclusive = true means "remove AddRoute itself too." If you wrote inclusive = false (the default), AddScreen would stay on the stack and only screens above it would be removed. Since nothing is above it here, that would change nothing.
//
//                    Why use it
//
//                    Pressing Back on ProfileScreen no longer returns to the form. With only ProfileScreen on the stack, Back exits the app.
//
//                    It's useful for flows where going back makes no sense, such as:
//
//                    Login → Home (Back shouldn't return to the login screen)
//                    Splash → Main
//                    A submitted form (like yours, if you don't want the user returning to the filled form)
//                            Should you use it in your app?
//
//                            It's your choice:
//
//                            Without it: the user can press Back to edit their data. Good for a form.
//                            With it: ProfileScreen becomes the end of the flow, and the form is gone. Good if submitting is final.
//


                }
            }
            )

        }


        composable<ProfileRoute> { backStackEntry->
//recieving arguments
            val args = backStackEntry.toRoute<ProfileRoute>()
            ProfileScreen(
                name = args.name,
                email = args.email,
                rollnum = args.rollnum,
                university = args.university,
                skills = args.skills,
                experience = args.experience,
                imageUri = args.imageUri

            )

        }


    }



}