package shahin.luasforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialization the Up Button
        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    /**
     * Support Navigation Up to display Up button and navigate back to the top stack
     */
    override fun onSupportNavigateUp(): Boolean{
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }

}
