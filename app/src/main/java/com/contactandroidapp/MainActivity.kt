package com.contactandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import com.contactandroidapp.databinding.ActivityMainBinding
import com.contactandroidapp.Fragments.Contact_Frag
import com.contactandroidapp.Fragments.Message_Frag
import java.util.*

class MainActivity : AppCompatActivity(){

    lateinit var bindview : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindview= DataBindingUtil.setContentView(this,R.layout.activity_main);

        bindview.pager.adapter = SectionsPagerAdapter(getSupportFragmentManager())
        bindview.tabLayout.setupWithViewPager(bindview. pager)
    }

    inner class SectionsPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm!!) {
        override fun getItemPosition(`object`: Any): Int {
            return super.getItemPosition(`object`)
            //            return POSITION_NONE;
        }

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            //Assign tab fragment view
            return when (position) {
                0 -> Contact_Frag()
                1 -> Message_Frag()
                else -> Contact_Frag()
            }
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            val l = Locale.getDefault()
            when (position) {
                0 -> return getString(R.string.title_section1)
                1 -> return getString(R.string.title_section2)
            }
            return null
        }
    }
}