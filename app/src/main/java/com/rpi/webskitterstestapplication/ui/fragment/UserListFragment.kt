package com.rpi.webskitterstestapplication.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.rpi.webskitterstestapplication.ui.activity.MainActivity
import com.rpi.webskitterstestapplication.utils.NetworkUtility
import com.rpi.webskitterstestapplication.R
import com.rpi.webskitterstestapplication.ui.adapter.UserListAdapter
import com.rpi.webskitterstestapplication.model.UserInfoModel
import com.rpi.webskitterstestapplication.model.UserListResponseModel
import com.rpi.webskitterstestapplication.network.ReqResAPIClient
import com.rpi.webskitterstestapplication.network.ReqResAPIService
import com.rpi.webskitterstestapplication.ui.activity.UserDetailsActivity
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListFragment : Fragment(),
    UserListAdapter.UserRowClickListener {
    private var rv_user_list: RecyclerView? = null

    private lateinit var mContext: MainActivity
    private var mRootView: View? = null
    private var tv_no_data_found: TextView? = null
    private lateinit var mUserModelList: ArrayList<UserInfoModel>
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private lateinit var mUserListAdapter: RecyclerView.Adapter<*>

    private lateinit var reqResAPIService: ReqResAPIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_user_list, container, false)
        initUIElements()
        initVariable()
        initUserListAdapter()
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callUserListData()
    }

    override fun onResume() {
        super.onResume()
        mContext.setActionBarTitle("User List")
    }

    private fun initUIElements() {
        rv_user_list = mRootView?.findViewById(R.id.rv_user_list);
    }

    private fun initVariable() {
        mContext = activity as MainActivity
        mUserModelList = ArrayList<UserInfoModel>()
        reqResAPIService = ReqResAPIClient.reqResAPIService

        mLinearLayoutManager = LinearLayoutManager(mContext)
        rv_user_list?.layoutManager = mLinearLayoutManager
    }

    private fun initUserListAdapter() {
        mUserListAdapter =
            UserListAdapter(
                context,
                mUserModelList,
                this
            )
        rv_user_list?.adapter = mUserListAdapter
    }

    private fun callUserListData() {
        if (NetworkUtility.isOnline(mContext)) {
            getUserListAPIService()
        } else {
            Snackbar.make(cl_loginRoot, "Check your internet connection", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun setUserListData() {
        //mPickupRequestAdapter = new PickupRequestAdapter(PickupRequestActivity.this, mPickupRequestList, this);
        mUserListAdapter.notifyDataSetChanged()
        if (mUserListAdapter.itemCount == 0) {
            rv_user_list?.visibility = View.GONE
            tv_no_data_found?.visibility = View.VISIBLE
        } else {
            rv_user_list?.visibility = View.VISIBLE
            tv_no_data_found?.visibility = View.GONE
        }
    }

    private fun resetUserList() {
        mUserModelList.clear()
        mUserListAdapter.notifyDataSetChanged()
    }

    private fun getUserListAPIService() {
        val userListCallback = reqResAPIService.apiUserListRequest(2)
        userListCallback.enqueue(object : Callback<UserListResponseModel> {
            override fun onFailure(call: Call<UserListResponseModel>, t: Throwable) {
                Snackbar.make(cl_loginRoot, "Something went wrong", Snackbar.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<UserListResponseModel>,
                response: Response<UserListResponseModel>
            ) {
                if (response.code() == 200) {
                    val userList: List<UserInfoModel>? = response.body()?.userList

                    userList?.let { userInfoList ->
                        mUserModelList.addAll(userInfoList)
                        setUserListData()
                    }
                } else {
                    Snackbar.make(cl_loginRoot, "Something went wrong", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

        })
    }

    companion object {
        const val TAG = "UserListFrag"

        @JvmStatic
        fun newInstance() =
            UserListFragment().apply {
                arguments = Bundle()
            }
    }

    override fun onUserRowClicked(userInfoModel: UserInfoModel?) {
        userInfoModel?.let {userInfo ->
            val intent = Intent(mContext, UserDetailsActivity::class.java)
            intent.putExtra(UserDetailsActivity.INTENT_KEY_USER_ID, userInfo.id)
            startActivity(intent)
        }
    }
}