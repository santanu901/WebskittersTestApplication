package com.rpi.webskitterstestapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rpi.webskitterstestapplication.R
import com.rpi.webskitterstestapplication.ui.activity.MainActivity

class ProfileFragment : Fragment() {
    private lateinit var mContext: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mContext.setActionBarTitle("User Profile")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initVariable()
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private fun initVariable() {
        mContext = activity as MainActivity
    }

    companion object {
        const val TAG = "ProfileFrag"

        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle()
            }
    }
}