1. Navigation: navigation in jetpack-compose is managing by the navigation component, which allows you to move between different screens(composables),while maintaining  a back stack
   and supporting types safety.

2. Core Components:
   .NavController: Central API that manages App Navigation. it tracks the back stack and state of each screen. we create it using rememberNavController().
   .NavHost: A container that links the NavController to a navigation graph, it displays the current destination based on the active route.
   .Navgraph: Map's Unique roues to specific composables, defining all possible pathswith in the app.



Type-Safe-Navigation: type-safe-navigation in jetpack compose is a feature introduced in Jetpack Navigation 2.8.0 that replaces fragile,string-based route URLs with strongly-typed
kotlin objects and data classes.


Old way (String routes): Navigation worked like typing web URLs (e.g., "profile/123"). If you made a typo or forgot an argument, the app wouldn't notice until it crashed while running.

New way (Type-safe): Navigation works like calling a standard Kotlin function. You pass a concrete code object (e.g., Profile(id=123)). if you pass the wrong datatype or miss a required argument, the code will not compile, preventing runtime crashes.


How it works in Short Quick 3 Steps:


1st Step. Define your routes as code objects instead of text string, using the @Serializable annotation:
@Serializable Object Home // No arguments
@Serializable data class Profile(val id: Int) //with arguments


2nd Step. Setup your graph by passing the class types directly into your NavHost:
composable<Profile> {backStackEntry ->
//Automatically extract the safely-typed object
val profile: Profile = backStackEntry.toRoute()
ProfileScreen(profile.id)
}


3rd Step. Navigate safely by instantiating the destination class.
navController.navigate(Profile(id = 123))



In this Project:
I made two Screens The one Having the Student Bio-Data form and The other displaying it.
The Add Screen in this project not only taches how to send data/ pass data to other screen but it also covers things like picking an Image using photo picker and ActivityLauncher to chose Image from gallery , and also teaches that how we can customize for fields or OutlinedTextFields.and how we moved to Profile Screen on Button Click in Add Screen using the onNavigateNext()
function which was then used and has the Definition inside NavGraph.kt file.


The Profile Screen teaches how to receive those fields sent by AddScreen and also teaches on how to display data beautifully using different/Custom Styling Techniques.


The Roues.kt : file simply explains and define on How to Define Routes: Teaches to use object for Screens or Routes Having no Data to Receive and are just Sending Data simplt, while also covers on how to make data class type routes for Screens Receiving that Data or arguments like the profile Screen here in this case.


The NavGrapg.kt : Covers the concept of NavController that how we can define pur main NavController and NavHost to Host Different Screens it covers the concept on how we passed the navController to NavHost and defined startDestination and other composable screen on which we want to Navigate  , AddScreen route passing all form fields and sending then to profile screen using navController.navigate() function and ProfileRoue rev=sieving it.


& ProfileRoue : receiving that data using backStackEntry arguments.

& this project also teaches on how we can apply our own made custom-themes to whole apps.


