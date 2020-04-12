package com.basbas.lawanqfid.utama.ui.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.utama.ui.fragment.bantuan.BantuanFragment
import com.basbas.lawanqfid.utama.ui.fragment.home_fragment.HomeFragment
import com.basbas.lawanqfid.utama.ui.fragment.map.MapsWorldFragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main_two.*

class HomeActivity : AppCompatActivity() {
    //implements BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_two)
        //BottomNavigationView navigation = findViewById(R.id.navigation);
// navigation.setOnNavigationItemSelectedListener(this);
//navigation.setOnClickMenuListener{\}

        loadFragment(HomeFragment())
        navigation.add(MeowBottomNavigation.Model(1,R.drawable.ic_home_black_24dp))
        navigation.add(MeowBottomNavigation.Model(2,R.drawable.ic_public_black_24dp))
        navigation.add(MeowBottomNavigation.Model(3,R.drawable.ic_bantuan))
        navigation.setOnClickMenuListener {
            when(it.id){
                1 -> loadFragment(HomeFragment())
                2 -> loadFragment(MapsWorldFragment())
                3 -> loadFragment(BantuanFragment())
                else -> null
            }
        }
        navigation.show(1)
    getFcmId()
    }


    private fun getFcmId() {
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.e("TAG", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token
                    // Log and toast


                    Log.e("TAG", "fcm token $token ")


                })

    }

    private fun loadFragment(fragment: Fragment?): Boolean { //switching fragment
        if (fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            return true
        }
        return false
    } //    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Fragment fragment = null;
//
//        switch (item.getItemId()) {
//            case R.id.nav_menu_kasus:
//                fragment = new HomeFragment();
//                break;
//            case R.id.nav_menu_informasi:
//                fragment = new MapFragment();
//                break;
//            case R.id.nav_menu_bantuan:
//                fragment = new BantuanFragment();
//                break;
//        }
//        return loadFragment(fragment);
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
}